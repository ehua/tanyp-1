package com.tanyouping.weixiao.domain;

import com.tanyouping.weixiao.domain.base.BaseBelongEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tan Youping on 2018/1/9
 * 用户心情
 */
public class Mood extends BaseBelongEntity {

    private String title;
    private Integer classify;
    private byte[] text;
    private String tags;
    private Integer likes;
    private Integer hates;
    private Integer reads;
    private Integer status;
    private List<Comment> comments = new ArrayList<>();

    public void init(){
        this.likes = 0;
        this.hates = 0;
        this.reads = 0;
        this.status = 1;
        this.classify = 1;
        super.init();
    }

    public byte[] getText() {
        return text;
    }

    public void setText(byte[] text) {
        this.text = text;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getHates() {
        return hates;
    }

    public void setHates(Integer hates) {
        this.hates = hates;
    }

    public Integer getReads() {
        return reads;
    }

    public void setReads(Integer reads) {
        this.reads = reads;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }
}
