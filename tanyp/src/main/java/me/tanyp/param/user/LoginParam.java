package me.tanyp.param.user;

import java.io.Serializable;

/**
 * Created by tanyp on 2018/8/16
 */
public class LoginParam implements Serializable {

    private String email;
    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
