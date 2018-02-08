package com.tanyouping.weixiao.domain;

import com.tanyouping.weixiao.domain.base.BaseBelongEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tan Youping on 2018/1/9
 * 评论
 */
public class Comment extends BaseBelongEntity {

    private Integer likes;
    private Integer hates;
    private byte[] text;
    private Integer moodId;
    private List<Subreview> subreviews = new ArrayList<>();

    public void init(){
        likes = 0;
        hates = 0;
        super.init();
    }

    public byte[] getText() {
        return text;
    }

    public void setText(byte[] text) {
        this.text = text;
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

    public List<Subreview> getSubreviews() {
        return subreviews;
    }

    public void setSubreviews(List<Subreview> subreviews) {
        this.subreviews = subreviews;
    }

    public Integer getMoodId() {
        return moodId;
    }

    public void setMoodId(Integer moodId) {
        this.moodId = moodId;
    }
}
