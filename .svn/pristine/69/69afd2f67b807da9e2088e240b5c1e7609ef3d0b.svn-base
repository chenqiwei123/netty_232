package com.example.netty.netty.Socket.Parsing;

import com.example.netty.netty.Socket.Entity.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.core4j.xml.XDocument;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.*;

import static org.dom4j.dom.DOMNodeHelper.getChildNodes;

public class DataDeal {
    @Autowired
    private ObjectMapper objectMapper;

    public static String[][] DealPack(String sourcedata, String protocol, String port) throws DocumentException {
        String s_source = sourcedata;
        PackDealImpl pd =new PackDealImpl();
        String[] result = null;
        String[] callback = null;
        switch (protocol)
        {
            case "BaoGang"://宝钢数据
                pd.BaoGangData(port);
                result = pd.GetWebStr(s_source).toArray(new String[0]);
                System.out.println("返回的长度:"+result.length);
                for (int i=0;i<result.length;i++){
                    System.out.printf(result[i]);
                }
                callback = pd.GetCallBackStr();
                break;
            default:
                break;
        }
        sourcedata = s_source;
        return new String[][] { callback, result };
    }


    //递归查询节点函数,输出节点名称
    private static void getChildNodes(Element elem) {
        //System.out.println("222"+elem.getName());
        Iterator<Node> it = elem.nodeIterator();
        while (it.hasNext()) {
            Node node = it.next();
            if (node instanceof Element) {
                Element e1 = (Element) node;
                getChildNodes(e1);
            }
        }
    }

}
