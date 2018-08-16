package me.tanyp.entity;

import me.tanyp.entity.base.BaseEntity;

import java.util.List;

/**
 * Created by tanyp on 2018/8/16
 */
public class Posts extends BaseEntity {

    private String content;
    private String belongId;
    private List<Praise> praises;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public List<Praise> getPraises() {
        return praises;
    }

    public void setPraises(List<Praise> praises) {
        this.praises = praises;
    }
}
