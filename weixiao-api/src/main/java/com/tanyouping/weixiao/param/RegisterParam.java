package com.tanyouping.weixiao.param;

import java.io.Serializable;

/**
 * Created by Tan Youping on 2018/1/9
 */
public class RegisterParam implements Serializable{

    private String name;
    private String pwd;
    private String mobile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
