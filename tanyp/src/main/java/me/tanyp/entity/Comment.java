package me.tanyp.entity;

import me.tanyp.entity.base.BaseEntity;

import java.util.List;

/**
 * Created by tanyp on 2018/8/16
 */
public class Comment extends BaseEntity {

    private String parentId;
    private String content;
    private List<Praise> praises;

    public void init(){
        super.init();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Praise> getPraises() {
        return praises;
    }

    public void setPraises(List<Praise> praises) {
        this.praises = praises;
    }
}
