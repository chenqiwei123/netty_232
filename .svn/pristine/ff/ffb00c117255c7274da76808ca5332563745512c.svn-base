package com.example.netty.netty.service.Impl;

import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import com.example.netty.netty.Entity.CheckInfo;
import com.example.netty.netty.Entity.DataCenterInfo;
import com.example.netty.netty.Entity.NumberEntity;
import com.example.netty.netty.Entity.User;
import com.example.netty.netty.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@WebService(endpointInterface = "com.example.netty.netty.service.IUserService",
        targetNamespace = "IUserService.service.netty.netty.example.com",serviceName = "UserServiceImpl" ,portName = "dao")
@Component
public class UserServiceImpl implements IUserService {
    public static List<DataCenterInfo> list2=new ArrayList<>();
    public static List<NumberEntity> listNumber=new ArrayList<>();
    public static int Number=0;
//    @Autowired
//    UserDao userDao;
//
//    public int getUserByName(String username, String userpwd, String usertype) throws Exception {
//        int count = userDao.getUserCount(username, userpwd, usertype);
//
//        System.out.println("count------>" + count);
//        return count;
//    }

    public User getUserInfo(String username, String userpwd, String usertype) throws Exception {
//    User user = userDao.getUser(username, userpwd, usertype);
        User user1 = new User();
        user1.setLogin_type(username);
        user1.setStu_pwd(userpwd);
        user1.setStu_num(usertype);
        return user1;
    }

    @Override
    public List getBaoGang(@WebParam(name = "PORT") String ss, @WebParam(name = "Number")int number){
        List centerInfoList=new ArrayList<>();
        for (int j=0;j<list2.size();j++){
            if (String.valueOf(list2.get(j).getKey()).equals(ss)) {
                centerInfoList.add(list2.get(j).toString());
            }
        }
        return centerInfoList;
    }


    @Override
    public int getInfoNumber(String Port) throws Exception {
        if (Port.isEmpty()){
            return 0;
        }
        for (int i=0;i<listNumber.size();i++){
            if (listNumber.get(i).getPort().equals(Port)){
                return listNumber.get(i).getPortNumber();
            }
        }
        return 0;
    }


    public List getList(List list,int i,int j){
        List list1=new ArrayList<>();
        if (list==null){
            return list1;
        }
        if (i>j||j>list.size()){
            return getList(list,0,i);
        }
        for (int m=i;m<j;m++){
            list1.add(list.get(m));
        }
        return list1;
    }
}