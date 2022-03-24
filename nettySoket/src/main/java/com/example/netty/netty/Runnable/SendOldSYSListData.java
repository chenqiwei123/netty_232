package com.example.netty.netty.Runnable;

import com.example.netty.netty.Client.WsTest;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SendOldSYSListData{
    private String[] name;

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public SendOldSYSListData(String[] name) {
        this.name = name;
    }


    public void run() {
            List<String> dc=new ArrayList<>();
            for (String ss:name) {
                String[] split = ss.split(",");
                String join = "";
                if (split[1].equals("891660a0502401")||split[1].equals("891605a0502401")||split[1].equals("891601a0502401")){
                        split[3] = "9262";
                        join = StringUtils.join(split, ",");
                        dc.add(join.replace(";", ""));
                }else {
                    if (split[3].equals("1")) {
                        split[3] = "2";
                        join = StringUtils.join(split, ",");
                        dc.add(join.replace(";", ""));
                    }
                    dc.add(ss.replace(";", ""));
                }
                //System.out.println("sc:" + _s.replace(";", ""));
                //String downWriteValueString = WsTest.Client2Service("DownWriteValueString", _s, clientIP);
            }
//            List<Object[]> list = WsTest.cccClient2Service("DownWriteValueString", dc);
//            for (int i=0;i<list.size();i++){
//                System.out.println(list.get(i)[0]);
//            }
    }
}
