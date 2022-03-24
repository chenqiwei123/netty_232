package com.example.netty.netty.service;

import com.example.netty.netty.Entity.DataCenterInfo;
import com.example.netty.netty.Entity.User;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Map;


@WebService(targetNamespace = "IUserService.service.netty.netty.example.com")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IUserService {
    // 获取登录信息
//    @WebMethod
//    int getUserByName(@WebParam(name = "username") String username, @WebParam(name = "userpwd") String userpwd, @WebParam(name = "usertype") String usertype) throws Exception;

 @WebMethod
 User getUserInfo(String username, String userpwd, String usertype) throws Exception;

 @WebMethod
 List getBaoGang(String Port,int number) throws Exception;

 @WebMethod
 int getInfoNumber(String port) throws Exception;


}