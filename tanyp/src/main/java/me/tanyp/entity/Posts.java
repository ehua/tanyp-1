package me.tanyp.entity;

import me.tanyp.entity.base.BaseEntity;
import me.tanyp.util.SpringServiceFactory;
import me.tanyp.util.UserManager;

import java.util.List;

/**
 * Created by tanyp on 2018/8/16
 */
public class Posts extends BaseEntity {

    private String author;
    private String content;
    private String tags;
    private List<String> medias;
    private List<Like> likes;
    private List<Comment> comments;

    public void init(){
        UserManager userManager = SpringServiceFactory.get().getService(UserManager.class);
        User user = userManager.getCurrentUser();
        this.setAuthor(user.getId());
        super.init();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getMedias() {
        return medias;
    }

    public void setMedias(List<String> medias) {
        this.medias = medias;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
