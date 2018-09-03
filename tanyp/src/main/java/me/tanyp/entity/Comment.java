package me.tanyp.entity;

import me.tanyp.entity.base.BaseEntity;

import java.util.List;

/**
 * Created by tanyp on 2018/8/16
 */
public class Comment extends BaseEntity {

    private String parentId;
    private String comment;
    private List<Like> likes;
    private String postsId;

    public void init(){
        super.init();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public String getPostsId() {
        return postsId;
    }

    public void setPostsId(String postsId) {
        this.postsId = postsId;
    }
}
