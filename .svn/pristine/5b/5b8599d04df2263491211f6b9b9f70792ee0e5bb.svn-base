package com.example.netty.netty.Entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@Data
public class CheckInfo {
    public String IP;
    public String CheckHeader;
    public String DataInfo;

    public CheckInfo(String IP, String checkHeader, String dataInfo) {
        this.IP = IP;
        CheckHeader = checkHeader;
        DataInfo = dataInfo;
    }

    public CheckInfo() {
    }

    public String getIP() {
        return IP==null?"":IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getCheckHeader() {
        return CheckHeader==null?"":CheckHeader;
    }

    public void setCheckHeader(String checkHeader) {
        CheckHeader = checkHeader;
    }

    public String getDataInfo() {
        return DataInfo==null?"":DataInfo;
    }

    public void setDataInfo(String dataInfo) {
        DataInfo = dataInfo;
    }
}
