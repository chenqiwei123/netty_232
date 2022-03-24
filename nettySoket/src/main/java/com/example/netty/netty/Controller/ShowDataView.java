package com.example.netty.netty.Controller;

import java.util.List;

public class ShowDataView {
    public String id;//站点编号
    public String name;//站点名称
    public String protocol;//协议
    public String ports;//端口
    public String status;//状态
    public String type;//type
    public String time;//最近通信时间
    public String Thread;//线程ID号

    public ShowDataView() {
    }

    public ShowDataView(String id, String name, String protocol, String ports, String status, String type, String time, String thread) {
        this.id = id;
        this.name = name;
        this.protocol = protocol;
        this.ports = ports;
        this.status = status;
        this.type = type;
        this.time = time;
        Thread = thread;
    }

    @Override
    public String toString() {
        return "ShowDataView{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", protocol='" + protocol + '\'' +
                ", ports='" + ports + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", Thread='" + Thread + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getThread() {
        return Thread;
    }

    public void setThread(String thread) {
        Thread = thread;
    }
}
