package com.example.netty.netty.Entity;

import com.example.netty.netty.service.Impl.UserServiceImpl;

import javax.swing.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

//@XmlRootElement(name = "DataCenterInfo")
//@XmlSeeAlso({DataCenterInfo.class,Data_Info.class, UserServiceImpl.class})
public class DataCenterInfo {
    public String key;
    private String newInfo;
    private String oldInfo;
    private String headerInfo;
    private String dataInfo;
    private String time;

    @Override
    public String toString() {
        return "DataCenterInfo{" +
                "key='" + key + '\'' +
                ", newInfo='" + newInfo + '\'' +
                ", oldInfo='" + oldInfo + '\'' +
                ", headerInfo='" + headerInfo + '\'' +
                ", dataInfo='" + dataInfo + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public DataCenterInfo(String key, String newInfo, String oldInfo, String headerInfo, String dataInfo, String time) {
        this.key = key;
        this.newInfo = newInfo;
        this.oldInfo = oldInfo;
        this.headerInfo = headerInfo;
        this.dataInfo = dataInfo;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DataCenterInfo() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNewInfo() {
        return newInfo;
    }

    public void setNewInfo(String newInfo) {
        this.newInfo = newInfo;
    }

    public String getOldInfo() {
        return oldInfo;
    }

    public void setOldInfo(String oldInfo) {
        this.oldInfo = oldInfo;
    }

    public String getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(String headerInfo) {
        this.headerInfo = headerInfo;
    }

    public String getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
    }
}
