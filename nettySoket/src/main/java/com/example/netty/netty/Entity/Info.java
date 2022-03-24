package com.example.netty.netty.Entity;

public class Info {
    private String time;
    private String ip;
    private String name;
    private String content;
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Info() {
    }

    public Info(String time, String ip, String name, String content) {
        this.time = time;
        this.ip = ip;
        this.name = name;
        this.content = content;
    }
}
