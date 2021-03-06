package com.example.netty.netty.Socket.Parsing;

import com.alibaba.fastjson.JSON;
import com.example.netty.netty.Client.WsTest;
import com.example.netty.netty.Controller.ConfigBeanValue;
import com.example.netty.netty.Socket.Entity.Tag;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class PackDealImpl implements PackDeal {
    @Value("${baosight.configxml}")
    public String baosight_configxml;
    public static String C_EndChar = "";
    public static String C_HeaderChar = "";
    public static String C_Port = "";
    public static String C_SiteName = "";
    public static String C_DataTime = "";
    public static HashMap ht_stlist = new HashMap<String, String>();
    public static HashMap ht_cnlist = new HashMap<String, String>();
    public static HashMap ht_eclist = new HashMap<String, String>();
    public static HashMap ht_datalist = new HashMap<String, String>();
    public static HashMap ht_dtlist = new HashMap<String, String>();
    public static HashMap ht_site = new HashMap<String, String>();
    public static HashMap nodeId = new HashMap<String, String>();
    public static HashMap removePort = new HashMap<String, String>();
    public List<String> l_returnstr = new ArrayList<String>();
    public List<String> l_callbackstr = new ArrayList<String>();
    public static Map<String, DataLimit> dic_Limits = new Hashtable<String, DataLimit>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static class DataLimit {
        public String ElementType;//????????????
        public double HiLimit;//??????
        public double LowLimit;//??????
        public double StanderVal;//?????????

        //        @Autowired
//        public DataLimit(ConfigBeanValue configBeanValue) {
//            DataLimit.configBeanValue = configBeanValue;
//        }
        public String getElementType() {
            return ElementType;
        }

        public void setElementType(String elementType) {
            ElementType = elementType;
        }

        public double getHiLimit() {
            return HiLimit;
        }

        public void setHiLimit(double hiLimit) {
            HiLimit = hiLimit;
        }

        public double getLowLimit() {
            return LowLimit;
        }

        public void setLowLimit(double lowLimit) {
            LowLimit = lowLimit;
        }

        public double getStanderVal() {
            return StanderVal;
        }

        public void setStanderVal(double standerVal) {
            StanderVal = standerVal;
        }
    }
    public static void IniLimit()
    {
        dic_Limits.put("a21026", GetLimitStr("SO2", 0, -0.014, 0.003));
        dic_Limits.put("a21003", GetLimitStr("NO", 0, -0.010, 0.002));
        dic_Limits.put("a21004", GetLimitStr("NO2", 0, -0.010, 0.002));
        dic_Limits.put("a21002", GetLimitStr("NOX", 0, -0.010, 0.002));
        dic_Limits.put("a21005", GetLimitStr("CO", 0, -1, 0.3));
        dic_Limits.put("a05024", GetLimitStr("O3", 0, -0.010, 0.002));
        dic_Limits.put("a34002", GetLimitStr("PM10", 0, -0.005, 0.002));
        dic_Limits.put("a34050", GetLimitStr("PM2.5", 0, -0.005, 0.002));
    }
    public static DataLimit GetLimitStr(String typeName,double hi,double low,double stander)
    {
        DataLimit tmp = new DataLimit();
        tmp.ElementType = typeName;
        tmp.HiLimit = hi;
        tmp.LowLimit = low;
        tmp.StanderVal = stander;
        return tmp;
    }

    public List<String> GetWebStr(String sourcedata) {
        try {
            C_EndChar = sourcedata.substring(sourcedata.length() - 4).replace("\r","").replace("\n","");
        } catch (Exception s) {

        }
        ReadPack(sourcedata);
        //System.out.printf(l_returnstr.toArray(new String[ l_returnstr.size()])+"");
        return l_returnstr;
    }

    public String[] GetCallBackStr() {
        return l_callbackstr.toArray(new String[0]);
    }

    public void BaoGangData(String port) {
        PackDealImpl.C_Port = String.valueOf(PackDealImpl.nodeId.get(port));
        PackDealImpl.C_SiteName = (String) ht_site.get(port);
        System.out.printf(PackDealImpl.C_Port + ":" + PackDealImpl.C_SiteName);
    }

    //<Pack Header="##" Ender="\r\n" DataTime="DataTime"></Pack>
    public void IniProtocols(String xmlPath) {
        File f = new File(xmlPath);
        SAXReader reader = new SAXReader();
        Document document = null;
        try {                                 //D:\baoxin\project\nettySoket\src\main\java\com\example\netty\netty\Socket\Parsing\DataDeal.java
            document = reader.read(f);
        } catch (Exception ss) {
            System.out.printf(String.valueOf(ss));
        }

        /**
         * ???????????????????????????
         */

        //?????????????????????
        Element root1 = document.getRootElement();
        Element Protocols = root1.element("Protocols");
        Element Sites = root1.element("Sites");
        Element Client = Sites.element("Client");
        for (Iterator iterator = Client.elementIterator(); iterator.hasNext(); ) {
            Element sub = (Element) iterator.next();
            PackDealImpl.ht_site.put(sub.attributeValue("port"), sub.attributeValue("name"));
            PackDealImpl.nodeId.put(sub.attributeValue("port"), sub.attributeValue("cport"));
        }
        Element Root = Protocols.element("BaoGang");
        Element SiteElement = Root.element("SiteType");
        Element Header = Root.element("Pack");
        C_HeaderChar = Header.attribute("Header").toString();
        Element CommandElement = Root.element("CommandType");
        Element DataTypeElement = Root.element("DataType");
        Element DataElement = Root.element("Datas");
        Element Errorcode = Root.element("ErrorCode");
        Element Removes = Root.element("Removes");
        for (Iterator it = SiteElement.elementIterator(); it.hasNext(); ) {
            Element sub = (Element) it.next();
            PackDealImpl.ht_stlist.put(sub.attributeValue("name"), sub.attributeValue("value"));
        }
        System.out.printf(PackDealImpl.ht_stlist.toString());

        for (Iterator it = CommandElement.elementIterator(); it.hasNext(); ) {
            Element sub = (Element) it.next();
            PackDealImpl.ht_cnlist.put(sub.attributeValue("name"), sub.attributeValue("value"));
        }
        System.out.printf(PackDealImpl.ht_cnlist.toString());
        for (Iterator it = DataTypeElement.elementIterator(); it.hasNext(); ) {
            Element sub = (Element) it.next();
            PackDealImpl.ht_dtlist.put(sub.attributeValue("name"), sub.attributeValue("value"));
        }
        System.out.printf(PackDealImpl.ht_dtlist.toString());

        for (Iterator it = DataElement.elementIterator(); it.hasNext(); ) {
            Element sub = (Element) it.next();
            PackDealImpl.ht_datalist.put(sub.attributeValue("name"), sub.attributeValue("value"));
        }
        for (Iterator it = Removes.elementIterator(); it.hasNext(); ) {
            Element sub = (Element) it.next();
            PackDealImpl.removePort.put(sub.attributeValue("name"), sub.attributeValue("value"));
        }

        System.out.printf("" + PackDealImpl.ht_datalist.toString());
        //????????????????????????
        for (Iterator it = Errorcode.elementIterator(); it.hasNext(); ) {
            Element sub = (Element) it.next();
            String name = sub.attributeValue("ecodname");
            String code = sub.attributeValue("fxcode");
            if (name.equals("XYH")) {
                name = "<";
            } else if (name.equals("DYH")) {
                name = ">";
            }

            PackDealImpl.ht_eclist.put(name, code);
        }
        PackDealImpl.C_HeaderChar = Root.element("Pack").attributeValue("Header");
        PackDealImpl.C_DataTime = Root.element("Pack").attributeValue("DataTime");


        //????????????????????????????????????


        //?????????????????????????????????????????????
    }

    private void ReadPack(String sourcedata) {
        String s_getdata = sourcedata;
        int i_headerindex = -1;
        try {
            i_headerindex = s_getdata.indexOf(C_HeaderChar);
        } catch (Exception w) {
            System.out.println(w);
        }
        if (i_headerindex > -1) {
            s_getdata = s_getdata.substring(i_headerindex);
        } else {
            sourcedata = "";
            return;
        }
        int i_enderindex = -1;
        try {
            i_enderindex = s_getdata.indexOf(C_EndChar);
        } catch (Exception s) {
            System.out.println(s);
        }
        if (i_enderindex > -1) {
            s_getdata = s_getdata.substring(0, i_enderindex + C_EndChar.length());
            //????????????????????????????????????????????????????????????????????????
            String cn = GetCN(s_getdata);
            if (cn.equals("")){
                return;
            }
            if (ht_cnlist.containsKey(cn)) {
                AnalyPack(s_getdata);
            }
            else
            {
                String callbackstr = "";
                String r = WsTest.PackinDB2Service("PackinDB",s_getdata, C_SiteName, callbackstr);
                l_callbackstr.add(callbackstr);
            }
            sourcedata = sourcedata.substring(s_getdata.length());
            ReadPack(sourcedata);
        } else {
            sourcedata = s_getdata;
            return;
        }
    }

    private void AnalyPack(String Datastr) {
        int wei = 0;
        try {
            int i_receivedatal = Datastr.length() - C_HeaderChar.length() - C_EndChar.length() - 4 - 4;
            int i_senddatal = Integer.parseInt(Datastr.substring(2, 4));
            //??????????????????
            i_senddatal = i_receivedatal;

            if (i_receivedatal == i_senddatal) {
                //string str_datapart = Datastr.Substring(6, i_senddatal);
                //AddDataDic(str_datapart);
                String cn = GetCN(Datastr);
                String qn = GetQN(Datastr);
                String st = GetStieType(Datastr);

                //??????????????????
                String rstr = "";
                rstr += "ST=91;";
                rstr += "CN=" + cn + ";";
                rstr += "CP=&&";
                rstr += "QN=" + qn;
                rstr += "&&";
                CRC16 crc = new CRC16();//crc?????????????????????????????????????????????
                String crccode = Integer.toHexString(crc.CalculateCrc16(rstr.getBytes(StandardCharsets.UTF_8)));
                //String l = PackDealImpl.padLeft(String.valueOf(rstr.length()),4,'0');
                String l = String.format("%04d", rstr.length());
                rstr = C_HeaderChar + l + rstr + crccode.toUpperCase();
                rstr = rstr.replace("\r\n", "");
                l_callbackstr.add(rstr);
                Tag[] tags = GetDataTags(Datastr, cn, st);
                if (tags.length != 0) {
                    String[] s = ReadTags(tags);
                    for (int i = 0; i < s.length; i++) {
                        l_returnstr.add(s[i] + ";");
                    }
                }
                System.out.printf("sun::::" + l_returnstr.toString());
               /* if (tags != null)
                {

                    for (Array ss : tags.values())
                    {
                        l_returnstr.add(ss);
                    }
                }*/

                return;
            } else {

                return;
            }
        } catch (Exception ww) {
            return;
        }
    }

    private String[] ReadTags(Tag[] tags) {
        try {
            List<String> r = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for (Tag t : tags) {
                if (removePort.get(C_Port)!=null&&!t.getTagName().contains("w")){
                    t.setTagName("w"+t.getTagName());
                }
                String flag = t.getFlag();
                if (flag == null) {
                    flag = "1";
                } else if (flag.equals("N")) {
                    flag = "1";
                } else {
                    String f = t.getFlag();
                    flag = "";
                    char[] strArray = f.toCharArray();
                    for (char c : strArray) {
                        if (ht_eclist.get(String.valueOf(c))==null){
                            continue;
                        }
                        flag += ht_eclist.get(String.valueOf(c));
                    }
                    flag = "9" + flag + "2";
                    //>b ?????????1
                    //if (flag.Contains("926"))
                    //{
                    //    flag = "1";
                    //}
                }

                if (t.getRtd() != null) {
                    sb = new StringBuilder();
                    sb.append(t.getSt() + C_Port + ",");
                    sb.append(C_Port + t.getTimeType() + ht_datalist.get(t.getTagName()) + ht_dtlist.get("Rtd") + ",");
                    sb.append(t.getRtd() + ",");
                    sb.append(flag + ",");
                    sb.append(t.getDataTime());
                    r.add(sb.toString());
                }

                if (t.getAvg() != null) {
                    //????????????????????????????????????
                    double d_tmp = Double.parseDouble(t.getAvg());
                    if (!(d_tmp < 0.0 && t.getSt().equals("DQ"))) {
                        sb = new StringBuilder();
                        sb.append(t.getSt() + C_Port + ",");
                        sb.append(C_Port + t.getTimeType() + ht_datalist.get(t.getTagName()) + ht_dtlist.get("Avg") + ",");
                        sb.append(t.getAvg() + ",");
                        sb.append(flag + ",");
                        sb.append(t.getDataTime());
                        r.add(sb.toString());
                    }
                }

                if (t.getMax() != null) {
                    sb = new StringBuilder();
                    sb.append(t.getSt() + C_Port + ",");
                    sb.append(C_Port + t.getTimeType() + ht_datalist.get(t.getTagName()) + ht_dtlist.get("Max") + ",");
                    sb.append(t.getMax() + ",");
                    sb.append(flag + ",");
                    sb.append(t.getDataTime());
                    r.add(sb.toString());
                }

                if (t.getMin() != null) {
                    sb = new StringBuilder();
                    sb.append(t.getSt() + C_Port + ",");
                    sb.append(C_Port + t.getTimeType() + ht_datalist.get(t.getTagName()) + ht_dtlist.get("Min") + ",");
                    sb.append(t.getMin() + ",");
                    sb.append(flag + ",");
                    sb.append(t.getDataTime());
                    r.add(sb.toString());
                }

                if (t.getCou() != null) {
                    sb = new StringBuilder();
                    sb.append(t.getSt() + C_Port + ",");
                    sb.append(C_Port + t.getTimeType() + ht_datalist.get(t.getTagName()) + ht_dtlist.get("Cou") + ",");
                    sb.append(t.getCou() + ",");
                    sb.append(flag + ",");
                    sb.append(t.getDataTime());
                    r.add(sb.toString());
                }

                if (t.getZsAvg() != null) {
                    sb = new StringBuilder();
                    sb.append(t.getSt() + C_Port + ",");
                    sb.append(C_Port + t.getTimeType() + ht_datalist.get(t.getTagName()) + ht_dtlist.get("ZsAvg") + ",");
                    sb.append(t.getZsAvg() + ",");
                    sb.append(flag + ",");
                    sb.append(t.getDataTime());
                    r.add(sb.toString());
                }

                if (t.getZsMax() != null) {
                    sb = new StringBuilder();
                    sb.append(t.getSt() + C_Port + ",");
                    sb.append(C_Port + t.getTimeType() + ht_datalist.get(t.getTagName()) + ht_dtlist.get("ZsMax") + ",");
                    sb.append(t.getZsMax() + ",");
                    sb.append(flag + ",");
                    sb.append(t.getDataTime());
                    r.add(sb.toString());
                }

                if (t.getZsMin() != null) {
                    sb = new StringBuilder();
                    sb.append(t.getSt() + C_Port + ",");
                    sb.append(C_Port + t.getTimeType() + ht_datalist.get(t.getTagName()) + ht_dtlist.get("ZsMin") + ",");
                    sb.append(t.getZsMin() + ",");
                    sb.append(flag + ",");
                    sb.append(t.getDataTime());
                    r.add(sb.toString());
                }
            }

            //??????????????????2?????????
            String[] ss = r.toArray(new String[r.size()]);
//            for (int i=0;i<r.size();i++){
//                ss[i]=r.get(i);
//            }
            List<String> list=new ArrayList<>();
            for (String tmps : ss) {
                String[] t = tmps.split(",");
                if (sdf.parse(t[4]).compareTo(sdf.parse(SenddateRoll())) >= 0){
                    t[3] = "1";
                }else if (t[3].equals("1")) {
                    t[3] = "2";
                }
                String newstr = "";
                newstr=String.join(",",t);
                list.add(newstr);
                }
                return list.toArray(new String[list.size()]);
        } catch (Exception s) {

        }
        return null;
    }

    public String SenddateRoll() {//?????????????????????
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //???1?????????????????????
        LocalDateTime date = LocalDateTime.now();
        //???2??????????????????????????????????????????
        LocalDateTime localDateTime = date.minusHours(1);
        return dateTimeFormatter.format(localDateTime);
    }

    public static String removeCharAt(String s, int pos) {

        return s.substring(0, pos) + s.substring(pos + 1);// ??????substring()????????????0-pos??????????????????+pos??????????????????????????????????????????????????????????????????
    }

    private Tag[] GetDataTags(String sourcedata, String cn, String st)
    //private Tag[] GetDataTags(String sourcedata, String cn, String st)
    {
        //##0332ST=22;CN=2051;PW=123456;MN=88888880000001;CP=&&DataTime=20110513092300;QN=20110513092300001;a01001-Avg=23.4;a01002-Avg=32;a01005-Avg=-100,a01005-Flag=B;a01006-Avg=1011.65;a01007-Avg=0.575;a05024-Avg=0.0205;a06001-Avg=123.035;a21002-Avg=0.1496;a21003-Avg=0.055;a21004-Avg=0.0653;a21026-Avg=0.0873;a34004-Avg=0.0968;a34005-Avg=0.185&&9983

        try {
            Hashtable ht_tag = new Hashtable();

            String datastr = sourcedata.substring(sourcedata.indexOf("CP=&&"));
            datastr = datastr.substring(5);
            if (PackDealImpl.C_SiteName.contains("???")) {
                datastr = datastr.substring(0, datastr.length() - 2 - C_EndChar.length());
            } else if (PackDealImpl.C_SiteName.contains("#")) {
                //datastr = datastr.substring(0, datastr.length() - 6 - C_EndChar.length());//??????
                datastr = datastr.substring(0, datastr.indexOf("&&"));
            }
            String dttype = ht_cnlist.get(cn).toString();
            String[] tagstrs = datastr.split(";");
            StringBuffer datetime = new StringBuffer();
            for (String s : tagstrs) {
//                if (s.contains("a01001")){
//                    System.out.println("wwwwwwwwwwwwwww");
//                }
                s = s.replace("\r\n", "");
                if (s.contains(C_DataTime)) {
                    datetime.append(s.replace(C_DataTime + "=", "").replace("\r\n", ""));
                    datetime = datetime.insert(4, "-");
                    datetime = datetime.insert(7, "-");
                    datetime = datetime.insert(10, " ");
                    datetime = datetime.insert(13, ":");
                    datetime = datetime.insert(16, ":");
                    continue;
                }
                //?????????????????????????????????DATState???????????????5??????????????????????????????
                else if (s.contains("DATState=")) {
                    String state = s.replace("DATState=", "");
                    if (state.equals("3")) {
                        dttype = "60";
                    } else if (state.equals("2")) {
                        dttype = "05";
                    }
                } else if (s.contains("-")) {
                    String[] substr = s.split(",");
                    String flag = "N";//?????????????????????
                    String s_dataname = "";//????????????
                    //string datatype ="";
                    //string dataval ="";

                    for (String ss : substr) {
                        if (ss.contains("-Flag"))//???????????????
                        {
                            String s_Flag;
                            if (ss.contains("&&")) {
                                s_Flag = ss.substring(ss.indexOf("=") + 1, ss.indexOf("&&"));
                            } else {
                                s_Flag = ss.substring(ss.indexOf("=") + 1);
                            }
                            if (!s_Flag.equals("N")) {
                                if (s_Flag.contains(">") || s_Flag.contains("<")) {
                                    flag = s_Flag;
                                } else {
                                    flag = s_Flag == s_Flag.toUpperCase() ? ">" + s_Flag.toUpperCase() : "<" + s_Flag.toUpperCase();
                                }

                            }
                            continue;
                        }

                        if (ss.contains("-"))//????????????
                        {
                            String dataname = "";
                            String datatype = "";
                            String dataval = "";
                            try {
                                dataname = ss.substring(0, ss.indexOf("-"));
                                s_dataname = dataname;
                                datatype = ss.substring(ss.indexOf("-") + 1);
                                datatype = datatype.substring(0, datatype.indexOf("="));
                                dataval = ss.substring(ss.indexOf("=") + 1);
                            } catch (Exception d) {
                                continue;
                            }

                            //????????????3?????????
                            String s_Result = String.format("%.3f", Double.parseDouble(dataval));
                            double tmpd = Double.parseDouble(s_Result);
                            //dataval = tmpd.ToString("f3");


                            if (dataname.equals("a21005") || dataname.equals("a01005"))//CO??????1?????????
                            {
                                dataval = DataFormat(tmpd, 1);
                            } else//???????????? ??????3?????????
                            {
                                dataval = String.format("%.4f", tmpd);
                            }


                            if (ht_datalist.containsKey(dataname)||ht_datalist.containsKey("w"+dataname)) {
                                if (ht_tag.get(dataname) != null) {
                                    try {
                                        Tag t = (Tag) ht_tag.get(dataname);
                                        SetModeProperties(t, datatype, dataval);
                                        ht_tag.put(dataname, t);
                                    }catch (Exception sss){
                                        continue;
                                    }
                                } else {
                                    Tag t = new Tag();
                                    t.setDataTime(datetime.toString());
                                    t.setTagName(dataname);
                                    t.setSt(st);
                                    //t.getTimeType() = ht_cnlist[cn].ToString();
                                    t.setTimeType(dttype);
                                    /*Properties pi = t.GetType().GetProperty(datatype);
                                    pi.SetValue(t, dataval, null);
                                    ht_tag.Add(dataname, t);
*/                                  try {
                                        Field field = t.getClass().getDeclaredField(datatype);//?????????t????????????????????????
                                        field.setAccessible(true);
                                        field.set(t, dataval);
                                    }catch (Exception sws){
                                        continue;
                                    }

                                    ht_tag.put(dataname, t);
                                }
                            }
                            continue;
                        }
                    }

                    //2014-02-11
                    //?????????????????????

                    Tag t_Tag = (Tag) ht_tag.get(s_dataname);//?????????
                    if (t_Tag != null) {
                        String s_DataVal = t_Tag.getAvg();
                        double d_dataval = 0;
                        t_Tag.setFlag(flag);
                        if (s_DataVal == null) {
                        } else {
                            try {
                                Double.parseDouble(s_DataVal);
                                d_dataval = Double.parseDouble(s_DataVal);
                                try {
//                                    d_dataval = d_dataval;
                                    d_dataval = CheckVal(s_dataname, d_dataval);
                                    if (d_dataval != -9999) {
                                        s_DataVal = String.valueOf(d_dataval);
                                        t_Tag.setAvg(s_DataVal);
                                    } else//-9999????????????????????? ????????????????????????d
                                    {
                                        flag = "<D";
                                        t_Tag.setFlag(flag);
                                    }
                                } catch (Exception sws) {
                                    System.out.println(sws);
                                }
                            } catch (Exception d) {

                            }
                        }
                        ht_tag.put(s_dataname, t_Tag);

                    }
                }
            }
            //return ht_tag.get("").OfType<Tag>().ToArray();
            //System.out.printf("??????????????????!!!!!!!!!!"+ht_tag.toString());
            int len = ht_tag.size();
            Tag[] tags = new Tag[len];
            ObjectMapper objectMapper = new ObjectMapper();
            int i = 0;
            for (Iterator<String> iterator = ht_tag.keySet().iterator(); iterator.hasNext(); ) {
                String key = iterator.next();
                tags[i] = (Tag) ht_tag.get(key);
                i++;
            }
            return tags;
        } catch (Exception se) {
            System.out.printf("se" + se);
            return null;
        }

    }


    public Double CSharpMathRound(Double d, int num) {
        String aa = "%." + num + "f";
        Double dd;
        int a = (int) (d * 10 / 10);
        if (a % 2 == 0) {
            dd = Double.parseDouble(String.format(aa, d));
        } else {
            dd = Double.parseDouble(String.format(aa, d + 1));
        }
        return dd;
    }

    public double CheckVal(String type, double val) {
        double result = val;
        if (PackDealImpl.ht_datalist.get(type)!=null) {
            DataLimit limit = dic_Limits.get(type);
            if (val <= limit.HiLimit && val >= limit.LowLimit) {
                result = limit.StanderVal;
            } else if (val < limit.LowLimit)//??????????????? ??????-9999
            {
                result = -9999;
            }
            return result;
        } else//?????????????????????????????????
        {
            return result;
        }
    }



    public void SetModeProperties(Tag mode, String Properties, String Value) throws Exception {
        Field field = mode.getClass().getDeclaredField(Properties);
        field.setAccessible(true);
        field.set(mode, Value);
    }

    public String DataFormat(double SourceData, int count) {
        String s_Result = "";
        if (!String.valueOf(SourceData).contains(".")) {
            String test = "%." + count + "f";
            s_Result = String.format(test, Double.parseDouble(String.valueOf(SourceData)));
        } else {
            int l_Len = String.valueOf(SourceData).substring(String.valueOf(SourceData).indexOf('.') + 1).length();
            if (l_Len < (count + 1))//????????????????????????????????????????????????
            {
                s_Result = String.valueOf(SourceData);
            } else {
                int i_Mod = Integer.parseInt(String.valueOf(SourceData).substring(String.valueOf(SourceData).indexOf('.') + (count + 1), String.valueOf(SourceData).indexOf('.') + (count + 2)));//?????????X+1???
                if (i_Mod != 5)//?????????5??? ??????????????????
                {
                    String test = "%." + count + "f";
                    s_Result = String.format(test, SourceData);
                } else//??????5?????????????????????
                {
                    SourceData = SourceData * Math.pow(10, count);
                    SourceData = Math.ceil(SourceData);

                    SourceData = SourceData % 2 == 0 ? SourceData : SourceData - 1;
                    SourceData = SourceData / Math.pow(10, count);
                    String test = "%." + count + "f";
                    s_Result = String.format(test, SourceData);
                }
            }
        }

        return s_Result;
    }

    public static String padLeft(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
        for (int i = src.length(); i < len; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }

    public void PackinDBRequestBody() {
    }

    public static String GetCN(String sourcedata) {
        String substr="";
        String cn="";
        if (sourcedata.contains("CN=")) {
            substr = sourcedata.substring(sourcedata.indexOf("CN="));
        }
        try {
            substr = substr.substring(0, substr.indexOf(";"));
            cn= substr.substring(3);
        }catch (Exception ss){}
        return cn;
    }

    public static String GetQN(String sourcedata) {
        String substr="";
        try {
            substr = sourcedata.substring(sourcedata.indexOf("QN="));
        }catch (Exception s){
            return substr;
        }
        substr = substr.substring(0, substr.indexOf(";"));
        String qn = substr.substring(3);
        return qn;
    }

    public static String GetStieType(String sourcedata) {
        String substr = sourcedata.substring(sourcedata.indexOf("ST="));
        substr = substr.substring(0, substr.indexOf(";"));
        String st = substr.substring(3);
        return ht_stlist.get(st).toString();
    }
}
