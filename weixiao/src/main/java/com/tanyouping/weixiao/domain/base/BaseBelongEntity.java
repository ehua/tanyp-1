package com.tanyouping.weixiao.domain.base;

import com.tanyouping.weixiao.security.UserManager;
import com.tanyouping.weixiao.spring.SpringServiceFactory;

import java.io.Serializable;

/**
 * Created by Tan Youping on 2018/1/10
 */
public class BaseBelongEntity extends BaseEntity implements Serializable{

    private Integer userId;
    private String name;

    public void init(){
        UserManager userManager = SpringServiceFactory.get().getService(UserManager.class);
        this.userId = userManager.getCurrentUser().getId();
        this.name = userManager.getCurrentUser().getName();
        super.init();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
