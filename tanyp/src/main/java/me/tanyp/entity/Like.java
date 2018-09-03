package me.tanyp.entity;

import me.tanyp.entity.base.BaseEntity;

/**
 * Created by tanyp on 2018/9/2
 */
public class Like extends BaseEntity {

    private String commentId;
    private String postsId;
    private String status; //0 喜欢 1 厌恶

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getPostsId() {
        return postsId;
    }

    public void setPostsId(String postsId) {
        this.postsId = postsId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
