package com.tanyouping.weixiao.domain;

import com.tanyouping.weixiao.domain.base.BaseBelongEntity;

/**
 * Created by Tan Youping on 2018/1/10
 */
public class Subreview extends BaseBelongEntity{

    private Integer commentId;
    private Integer likes;
    private Integer hates;
    private String text;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }
}
