package com.example.netty.netty.Entity;

import java.io.Serializable;

public class User  implements Serializable {
    private int id;
    private String stu_num;
    private String stu_name;
    private String stu_pwd;
    private String login_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStu_num() {
        return stu_num;
    }

    public void setStu_num(String stu_num) {
        this.stu_num = stu_num;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getStu_pwd() {
        return stu_pwd;
    }

    public void setStu_pwd(String stu_pwd) {
        this.stu_pwd = stu_pwd;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }


    public User() {
    }

    public User(int id, String stu_num, String stu_name, String stu_pwd, String login_type) {
        this.id = id;
        this.stu_num = stu_num;
        this.stu_name = stu_name;
        this.stu_pwd = stu_pwd;
        this.login_type = login_type;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", stu_num='" + stu_num + '\'' +
                ", stu_name='" + stu_name + '\'' +
                ", stu_pwd='" + stu_pwd + '\'' +
                ", login_type='" + login_type + '\'' +
                '}';
    }
}