package me.tanyp.param;

import java.io.Serializable;

/**
 * Created by tanyp on 2018/8/12
 */
public class HelloParam implements Serializable {

    private String hello;
    private String world;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }
}
