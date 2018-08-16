package me.tanyp.entity;

import me.tanyp.entity.base.BaseEntity;

/**
 * Created by tanyp on 2018/8/16
 */
public class Praise extends BaseEntity {

    private String postsId;
    private String belongId;

    public void init(){
        super.init();
    }

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public String getPostsId() {
        return postsId;
    }

    public void setPostsId(String postsId) {
        this.postsId = postsId;
    }
}
