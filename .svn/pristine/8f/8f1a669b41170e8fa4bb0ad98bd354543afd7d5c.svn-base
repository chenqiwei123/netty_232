package com.example.netty.netty.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.HttpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SendAjaxInfo {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    static CloseableHttpClient httpClient = HttpClients.createDefault();
    public static HttpPost post = null;

    /**
     * 发送post请求
     *
     * @param
     * @param
     * @return
     */
    public static String sendPost(String uri, String param) throws IOException {
        if (post==null){
            post=new HttpPost(HelloController.sendAjaxInfo);
        }
        post.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(param, StandardCharsets.UTF_8);
        post.setEntity(entity);
        HttpResponse response = httpClient.execute(post);

//        int statusCode = response.getStatusLine().getStatusCode();
//        if (statusCode != 200) {
//            throw new RuntimeException("http请求异常" + statusCode);
//        }
        //System.out.printf("sun"+EntityUtils.toString(response.getEntity(), "UTF-8"));
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

}
