package me.tanyp.entity;


import me.tanyp.entity.base.BaseEntity;

/**
 * Created by tanyp on 2018/8/16
 */
public class LoginRecord extends BaseEntity {

    private String browser;
    private String loginIP;
    private String userId;
    public void init(){
        super.init();
    }
    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
