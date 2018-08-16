package me.tanyp.entity;

import me.tanyp.entity.base.BaseEntity;

/**
 * Created by tanyp on 2018/8/16
 * 用户
 */
public class User extends BaseEntity {
    //用户名
    private String name;
    //密码
    private String pwd;
    private String salt;
    //手机号
    private String mobile;
    //邮箱
    private String email;
    //简介
    private String profile;
    //违规次数
    private Integer illegal;
    //用户状态 1：正常 2：休眠 3：封号
    private Integer status;

    public void init(){

    }

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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getIllegal() {
        return illegal;
    }

    public void setIllegal(Integer illegal) {
        this.illegal = illegal;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
