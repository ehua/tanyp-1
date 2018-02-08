package com.tanyouping.weixiao.domain;

import com.tanyouping.weixiao.domain.base.BaseEntity;

/**
 * Created by Tan Youping on 2018/1/9
 * 登录记录
 */
public class LoginRecord extends BaseEntity {

    private String loginIP;
    private Integer userId;

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
