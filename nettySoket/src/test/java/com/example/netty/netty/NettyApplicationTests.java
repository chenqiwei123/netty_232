package com.example.netty.netty;

import com.alibaba.fastjson.JSONObject;

import com.example.netty.netty.Entity.DataInfo;
import com.example.netty.netty.Entity.Data2Info;
import com.example.netty.netty.Entity.RedisValue;
import com.example.netty.netty.Entity.TVTable;
import com.example.netty.netty.dao.UserLoginMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//
////import org.junit.jupiter.api.Test;

//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.example.netty.netty.Controller.ConfigBeanValue;
//import com.example.netty.netty.Controller.RedisConnect;
//import com.example.netty.netty.Entity.DataInfo;
//import com.example.netty.netty.Entity.VT;
//import com.example.netty.netty.service.VTService;
//import org.apache.cxf.endpoint.Client;
//import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
@SpringBootTest
class NettyApplicationTests {
    @Autowired
    UserLoginMapper userLoginMapper;
    @Autowired
    public static RedisTemplate<String, Object> template;
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static HashMap<String, Data2Info> data2Map=new HashMap<>();

    @Test
    public void toTest() throws ParseException {
        List<DataInfo> dataInfos = userLoginMapper.queryAll();
        for (DataInfo dataInfo : dataInfos) {
            Data2Info data2Info = new Data2Info(String.valueOf(dataInfo.getNodeId()),String.valueOf(dataInfo.getTagId()));
            data2Map.put(dataInfo.getDeviceInfo(),data2Info);
        }
    }



    @Autowired
    private StringRedisTemplate redisClient;
    public String test(List<RedisValue> para) throws Exception{
        redisClient.opsForValue().set("TV40025_8809", JSONObject.toJSONString(para));
        String str = redisClient.opsForValue().get("TV40025_8809");
        System.out.printf("Redis 的返回结果:"+str);
        return str;
    }

    @RequestMapping("getjson")
    @ResponseBody
    public String  getJson(){
        RedisValue user1 = new RedisValue();
        user1.setDate(new Date());
        user1.setValue("24.1");
//        redisClient.opsForValue().set("user", JsonUtils.objectToJson(user1));
//        String str = redisClient.opsForValue().get("user");
//        String u1  = JsonUtils.objectToJson(user1);
        return "str";
    }
//    //    @Autowired
////    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    VTService vtService;
//    @Autowired
//    private ConfigBeanValue configBeanValue;
//    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//    @Test
//    void contextLoads() {
//        // 创建动态客户端
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//        Client client = dcf.createClient("http://" + configBeanValue.baosight_ip + ":" + configBeanValue.baosight_Port + "/UserServiceImpl/dao?wsdl");
//        Object[] objects = new Object[0];
//        try {
//            //按照各自的文档传入参数
//            objects = client.invoke("getBaoGang", "111");
//            //  JSONObject.parse(objects[0].toString())
//            JSONArray objects1 = (JSONArray) JSONObject.parseArray(objects[0].toString()).get(1);
//            for (int i = 0; i < objects1.size(); i++) {
//                String vt = objects1.get(i).toString();
//                String[] split = vt.split(",");
//                Float val = Float.valueOf(0);
//                if (split[2] != null || !split[2].equals("")) {
//                    val = Float.valueOf(split[2]);
//                }
//                VT vt1 = new VT(simpleDateFormat.parse(split[4].replace(".", "").replace("\r\n", "")), Integer.parseInt(split[3]));
//                //String sql = "SELECT * from DataInfo WHERE DEVICEINFO like '%" + split[1] + "%'";
//                String sss = "%4022051501%";
////                String sql = "SELECT * from DataInfo WHERE DEVICEINFO like '%" + split[1] + "%'";
////                vtService.getDataInfo(split[1])
////                List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
//                DataInfo dataInfo = vtService.getDataInfo("%" + split[1] + "%");
//                if (dataInfo == null) {
//                    continue;
//                }
//                String table = "VT" + dataInfo.getNodeId() + "_" + dataInfo.getTagId();
//                System.out.println("TABLE:!!!!!!!!!!!" + table);
////                String tableSql = "CREATE TABLE IF NOT EXISTS `" + table + "`  (\n" +
////                        "  `DT` datetime NOT NULL,\n" +
////                        "  `VAL` float(255, 0) NULL DEFAULT NULL,\n" +
////                        "  `STATUS` int(255) NOT NULL\n" +
////                        ")";
////                System.out.printf(tableSql);
////                jdbcTemplate.execute(tableSql);
//                vtService.createNewTable(table);
//                int i1 = vtService.create(table, simpleDateFormat.parse(split[4].replace(".", "").replace("\r\n", "")), val, Integer.parseInt(split[3]));
////
//                System.out.println("插入结果::" + i1);
////                String insertTable = "INSERT INTO `" + table + "` (`DT`, `VAL`, `STATUS`) VALUES (?, ?, ?);";
////                jdbcTemplate.update(insertTable, simpleDateFormat.parse(split[4].replace(".", "").replace("\r\n", "")), val, Integer.parseInt(split[3]));
//            }
//            System.out.println("返回数据:" + JSONObject.toJSONString(objects[0]));
//        } catch (java.lang.Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void sssss() {
//        // System.out.println(RedisConnect.getRedisConnect().get("name"));
//    }
//




}
