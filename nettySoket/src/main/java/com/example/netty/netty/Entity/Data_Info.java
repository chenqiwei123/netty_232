package com.example.netty.netty.Entity;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Data_Info")
@XmlSeeAlso({Data_Info.class})
public class Data_Info {
    private String newInfo;
    private String oldInfo;
    private String headerInfo;
    private String dataInfo;
    private String time;

    @Override
    public String toString() {
        return "Data_Info{" +
                "newInfo='" + newInfo + '\'' +
                ", oldInfo='" + oldInfo + '\'' +
                ", headerInfo='" + headerInfo + '\'' +
                ", dataInfo='" + dataInfo + '\'' +
                '}';
    }

    public Data_Info() {
    }

    public Data_Info(String newInfo, String oldInfo, String headerInfo, String dataInfo, String time) {
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

    public String getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(String headerInfo) {
        this.headerInfo = headerInfo;
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

    public String getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
    }
}