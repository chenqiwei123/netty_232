package com.example.netty.netty.Socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.netty.netty.Client.WsTest;
import com.example.netty.netty.Controller.HelloController;
import com.example.netty.netty.Controller.SendAjaxInfo;
import com.example.netty.netty.Entity.*;
import com.example.netty.netty.JPA.TestUserDao;
import com.example.netty.netty.NettyApplication;
import com.example.netty.netty.Runnable.SendOldSYSListData;
import com.example.netty.netty.Socket.Parsing.DataDeal;
import com.example.netty.netty.Socket.Parsing.PackDealImpl;
import com.example.netty.netty.dao.UserLoginMapper;
import com.example.netty.netty.service.Impl.UserServiceImpl;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.apache.tomcat.util.digester.ArrayStack;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.netty.netty.Socket.Parsing.PackDealImpl.C_SiteName;
import static com.example.netty.netty.Socket.Parsing.PackDealImpl.GetQN;
import static com.example.netty.netty.Socket.SimpleServer.mapPointInfo;

/**
 * 服务端自定义handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static Logger logger = LoggerFactory.getLogger(NettyApplication.class);
    static Map<String, StringBuilder> map = new HashMap<>();
//    static List<Map> mapList=new ArrayList<>();
    /**
     * 读取实际数据(这里我们可以读取客户端发送的消息)
     *
     * @param ctx 上下文对象,含有管道pipeline,通道channel ,地址
     * @param msg 客户端发送的内容
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws ParseException, IOException {
        //设置保护机制,防止内存泄漏
        ByteBuf buf = (ByteBuf) msg;
        try {
            StringBuilder clientInfo = new StringBuilder(buf.toString(CharsetUtil.UTF_8));
//            System.out.println("名称：" + Thread.currentThread().getName());
            logger.info("ID：" + Thread.currentThread().getId());
            System.out.println("客户端发送: " + clientInfo);
            logger.info("客户端发送: " + clientInfo);
            Map<String, String> infoMap = new HashMap<>();
//            System.out.println("客户端地址为:" + ctx.channel().localAddress());
            String clientIP1 = ctx.channel().localAddress().toString();
            String clientIP = clientIP1.substring(clientIP1.indexOf(":") + 1);
            String Portname = String.valueOf(PackDealImpl.ht_site.get(clientIP));
            if (map.get(clientIP) == null) {
                map.put(clientIP, new StringBuilder());
            }
            map.put(clientIP, new StringBuilder(map.get(clientIP).toString() + clientInfo));
            String[] clientInfoArray = map.get(clientIP).toString().split("##");
            int check = clientInfoArray[clientInfoArray.length - 1].length() - clientInfoArray[clientInfoArray.length - 1].lastIndexOf("&&");
            boolean checkDataString = clientInfoArray[clientInfoArray.length - 1].substring(clientInfoArray[clientInfoArray.length - 1].length() - 4).equals("Data");
            if (check == 6 && !checkDataString) {//处理断续报文
                for (int jj = 1; jj < clientInfoArray.length; jj++) {
//                    clientInfo = "##" + clientInfoArray[jj];
                    CacheMsgData(clientIP);
                    dealSocketInfo("##" + clientInfoArray[jj], clientIP, ctx, Portname);
                }
                map.put(clientIP, new StringBuilder());
            } else {
                for (int jj = 1; jj < clientInfoArray.length - 1; jj++) {
//                    clientInfo = "##" + clientInfoArray[jj];
                    CacheMsgData(clientIP);
                    dealSocketInfo("##" + clientInfoArray[jj], clientIP, ctx, Portname);
                }
                map.put(clientIP, new StringBuilder("##" + clientInfoArray[clientInfoArray.length - 1]));
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            buf.release();//防止内存堆栈泄露异常
        }
    }

    /**
     * 保存接收的数据并显示界面
     *
     * @param clientIP
     * @param
     * @param
     * @param
     */
    public void CacheMsgData(String clientIP) {

        if (HelloController.infoListShow.get(clientIP) != null) {
            int infoList_clientIP_size = HelloController.infoListShow.get(clientIP).size();
            if (infoList_clientIP_size > 50) {
                Map map1 = HelloController.infoListShow.get(clientIP).get(infoList_clientIP_size - 1);
                Map map2 = HelloController.infoListShow.get(clientIP).get(infoList_clientIP_size - 2);
                Map map3 = HelloController.infoListShow.get(clientIP).get(infoList_clientIP_size - 3);
                Map map4 = HelloController.infoListShow.get(clientIP).get(infoList_clientIP_size - 4);
                Map map5 = HelloController.infoListShow.get(clientIP).get(infoList_clientIP_size - 5);
                HelloController.infoListShow.clear();
                if (HelloController.infoListShow.get(clientIP) == null) {
                    HelloController.infoListShow.put(clientIP, new ArrayList<>());
                }
                HelloController.infoListShow.get(clientIP).add(map5);
                HelloController.infoListShow.get(clientIP).add(map4);
                HelloController.infoListShow.get(clientIP).add(map3);
                HelloController.infoListShow.get(clientIP).add(map2);
                HelloController.infoListShow.get(clientIP).add(map1);
            }
        }
    }

    /**
     * 解析数据并上传
     *
     * @param info
     * @param clientIP
     * @param
     * @param ctx
     * @param Portname
     * @throws Exception
     */
    public void dealSocketInfo(String info, String clientIP, ChannelHandlerContext ctx, String Portname) throws Exception {
        String[][] _getData = new String[0][];
        Map<String, String> infoMap=new HashMap<>();
        try {
            _getData = DataDeal.DealPack(info.replace("\r","").replace("\n",""), "BaoGang", clientIP);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
//        mapList.add(infoMap);
        infoMap.put("time", sdf.format(new Date()));
        infoMap.put("ip", clientIP);
        infoMap.put("name", Portname);
        infoMap.put("msg", info);
        infoMap.put("UploadData", StringArray2String(_getData[1]));
//        HelloController.infoListShow.put(clientIP,mapList);
        if (HelloController.infoListShow.get(clientIP) == null) {
            HelloController.infoListShow.put(clientIP, new ArrayList<>());
        }
        HelloController.infoListShow.get(clientIP).add(infoMap);
        mapPointInfo.get(clientIP).get(0).put("time", sdf.format(new Date()));
//        Info info = new Info();
//        info.setTime(sdf.format(new Date()));
//        info.setIp(ctx.channel().remoteAddress().toString());
//        info.setContent(buf.toString(CharsetUtil.UTF_8));
//        HelloController helloController=new HelloController();
//        helloController.addInfo(info);
//        if (UserServiceImpl.list2.size()>500){
//            print(UserServiceImpl.list2);
//            remove2(UserServiceImpl.list2,clientIP);
//        }
//            HelloController.hello();
        //String cc = buf.toString(CharsetUtil.UTF_8);
        //String[] split1 = cc.split("##");
        //String End_Char = cc.substring(cc.lastIndexOf("&&") + 2);
        //String newTurnData = dojoin(_getData[0]).substring(0, dojoin(_getData[0]).lastIndexOf(End_Char));
        //String id = String.valueOf(Thread.currentThread().getId());
        //int portListIndex = getPortListIndex(clientIP);
        if (_getData[0] != null) {
            String[] _returnstr = _getData[0];//返回数据的第一组为应答报文
            if (_returnstr != null) {
                for (String rs : _returnstr) {
                    //发送检验数据
                    //String Client2ServiceCheck = WsTest.Client2ServiceCheck("Client2ServiceCheck", newTurnData);
                    //if (Client2ServiceCheck.equals("true")) {
                    //System.out.println("服务器已接收并判断CRC传输无误!!");
                    //发送原始数据
                    //String downWriteValueString2 = WsTest.Client2Service("OldDataString", clientIP, cc, id);
                    //}
                    //if (Client2ServiceCheck.equals("") || Client2ServiceCheck == null) {
                    //System.out.println("发送应答报文:" + rs + "失败!");
                    //} else {
                    ChannelFuture channelFuture = ctx.writeAndFlush(Unpooled.copiedBuffer(rs, CharsetUtil.UTF_8));
                    if (channelFuture.isSuccess()) {
                        logger.info("发送应答报文:" + rs + "成功!");
                    } else {
                        logger.info("发送应答报文:" + rs + "失败!");
                    }
                    logger.info("发送应答报文:" + rs + "成功!");
                    //}
                }
            }
        }
        /**
         *     private String nodeId = "";   //站点的nodeId
         *     private String tagId = "";   // 因子tagId
         *     private String deviceInfo = "";
         *     private String dataTime  = ""; //因子日期
         *     private String val = "";    //因子值
         *     private String state  = ""; //数据状态位 数据时间力度 1|2;6001;144001;400001...
         *     private String oPState = "";//数据操作标志位：1为入缓存，2位入库，3入库并缓存  2小时前3，2小时后2
         */
        //发送监测数据
        ArrayList<HashMap> list = new ArrayList<>();
        logger.info("上传31服务器-数据中心的数据:" + Arrays.toString(_getData[1]));
        logger.debug(_getData[1].toString());
        String[][] final_getData = _getData;
//        SendOldSYSListData sendOldSYSListData=new SendOldSYSListData(final_getData[1]);
//        sendOldSYSListData.run();
        if (_getData[1] != null) {
            List<String> stringList=new ArrayList<>();
            String ss = "";
            for (String _s : _getData[1]) {
                System.out.println(_s);
                String[] split = _s.split(",");
                String replace = split[4].replace(";", "");
                HashMap map = new HashMap();
                map.put("dataTime", DealTimeError(replace));
                map.put("val", split[2]);
                if (isNumeric2(split[3])&&split[3].length()<7){
                    map.put("state", split[3]);
                }else {
                    map.put("state", 2);
                }
                if (sdf.parse(replace).compareTo(sdf.parse(dateRoll())) >= 0) {
                    map.put("oPState", 3);
                } else {
                    map.put("oPState", 2);
                }
                PackDealImpl.nodeId.get("");
                DataInfo dateInfo = HelloController.findDateInfo(split[1]);
                map.put("nodeId", dateInfo.getNodeId());
                map.put("tagId", dateInfo.getTagId());
                if (dateInfo.getTagId() == 0) {
                    continue;
                }
                list.add(map);
                stringList.add(_s);

//                    if (!downWriteValueString.equals("") || downWriteValueString != null) {
//                        System.out.println("数据入数据中心,返回：" + downWriteValueString);
//                    }
                //Console.WriteLine("上传数据22222222" + r);
//                this.Invoke(new Action<string, bool>(ShowLog), "数据入数据中心,返回：" + r.ToString(), true);
//                this.Invoke(new Action<string>(ShowUpdate), new string[] { _s });
            }

//                for (Map<String, String> stringStringMap : list) {
//                    System.out.println(stringStringMap.toString());
//                }
            Map map = new HashMap();
            map.put("dataList", list);
            //System.out.println("发送的数据:"+ list.get(0));
            JSONObject json = new JSONObject(map);
            SendAjaxInfo.sendPost(HelloController.senPost, String.valueOf(json));
            logger.info(Portname + "上传31服务器数据:");
            logger.debug(String.valueOf(json));
            //List<TV> downWriteValueString = WsTest.DownWriteValueOver("DownWriteValueOver", clientIP);
            //System.out.println("22222");
            //HelloController.SaveInfo(downWriteValueString);
//            Console.WriteLine("上传的数据为:"+ss);

            //List collect = UserServiceImpl.list2.stream().filter((DataCenterInfo d) -> clientIP.equals(d.getKey())).collect(Collectors.toList());
//            DataCenterInfo dataCenterInfo = new DataCenterInfo();
//            dataCenterInfo.setKey(clientIP);
//            dataCenterInfo.setOldInfo(buf.toString(CharsetUtil.UTF_8));
//            dataCenterInfo.setNewInfo(String.valueOf(dojoin(_getData[1])));
//            dataCenterInfo.setDataInfo(clientIP);
//            dataCenterInfo.setHeaderInfo(String.valueOf(dojoin(_getData[0])));
//            dataCenterInfo.setTime(sdf.format(new Date()));
            //collect.add(data_info);
//            if (portListIndex==-1){
//                UserServiceImpl.list2.add(dataCenterInfo);
//            } else if (UserServiceImpl.list2.get(portListIndex).getData_info().size()>500) {
//                UserServiceImpl.list2.get(portListIndex).setData_info(new ArrayList());
//            }else {
//                UserServiceImpl.list2.get(portListIndex).setData_info(collect);
//            }
//            UserServiceImpl.list2.add(dataCenterInfo);
//            int portNumberListIndex = getPortNumberListIndex(clientIP);
//            if (portNumberListIndex==-1){
//                NumberEntity numberEntity = new NumberEntity();
//                numberEntity.setPort(clientIP);
//                numberEntity.setPortNumber(1);
//                UserServiceImpl.listNumber.add(numberEntity);
//            }else {
//                UserServiceImpl.listNumber.get(portNumberListIndex).setPortNumber(collect.size());
//            }
        } else {
            System.out.printf("无数据！！！！！！！！！！！！！");
        }
    }

    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");
    public static boolean isNumeric2(String str) {
        return str != null && NUMBER_PATTERN.matcher(str).matches();
    }

    /**
     * String ss="2021-09-30 05:10:0";
     *
     * @param ss
     * @return
     */
    public String DealTimeError(String ss) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String parse1="";
        try {
            Date parse = sdf1.parse(ss);
            parse1= sdf1.format(parse);
        }catch (Exception s){
            Date parse = sdf.parse(ss);
            parse1= sdf.format(parse);
        }
        return parse1;
    }

    public String StringArray2String(String[] arr) {
        StringBuffer str5 = new StringBuffer();
        for (String s : arr) {
            str5.append(s);
        }
        return str5.toString();
    }

    /**
     * 获取前30分钟时间
     *
     * @return
     */
    public String dateRoll() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //（1）获取当前时间
        LocalDateTime date = LocalDateTime.now();
        //（2）获取当前时间的前几小时时间
        LocalDateTime localDateTime = date.minusHours(2);
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * map 2 String
     *
     * @param map
     * @return
     */
    public static String getMapToString(Map<String, Object> map) {
        Set<String> keySet = map.keySet();
        //将set集合转换为数组
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        //给数组排序(升序)
        Arrays.sort(keyArray);
        //因为String拼接效率会很低的，所以转用StringBuilder
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            // 参数值为空，则不参与签名 这个方法trim()是去空格
            if ((String.valueOf(map.get(keyArray[i]))).trim().length() > 0) {
                sb.append(keyArray[i]).append(":").append(String.valueOf(map.get(keyArray[i])).trim());
            }
            if (i != keyArray.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }


    /**
     * 读取完成后
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好,客户端", CharsetUtil.UTF_8));
        super.channelReadComplete(ctx);
    }


    /**
     * 处理异常,一般是关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    public String dojoin(String[] strings) {
        String result = String.join("", strings);
        return result;
    }//字符串数组转字符串字符串


    public int getPortListIndex(String port) {
        for (int i = 0; i < UserServiceImpl.list2.size(); i++) {
            if (UserServiceImpl.list2.get(i).equals(port)) {
                return i;
            }
        }
        return -1;
    }

    public int getPortNumberListIndex(String port) {
        for (int i = 0; i < UserServiceImpl.listNumber.size(); i++) {
            if (UserServiceImpl.list2.get(i).equals(port)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 清空部分list的数据
     *
     * @param list
     */
    public static void remove2(List<DataCenterInfo> list, String port) {
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).getKey().equals(port)) {
                list.remove(i);
            }
        }
    }

    public void print(List<DataCenterInfo> list) {
        try {
            File outFile1 = new File("E:/Test" + sdf.format(new Date()) + ".txt");
            if (!outFile1.exists()) {
                outFile1.mkdirs();
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile1, true), "utf-8"), 102400);
            for (int i = 0; i < list.size(); i++) {
                out.write(list.get(i) + "\r\n");
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ParseException {

    }

}
