package me.tanyp.entity;

import java.io.Serializable;

/**
 * Created by tanyp on 2018/8/12
 */
public class Hello implements Serializable {

    private String helloId;
    private String helloName;

    public String getHelloId() {
        return helloId;
    }

    public void setHelloId(String helloId) {
        this.helloId = helloId;
    }

    public String getHelloName() {
        return helloName;
    }

    public void setHelloName(String helloName) {
        this.helloName = helloName;
    }
}
