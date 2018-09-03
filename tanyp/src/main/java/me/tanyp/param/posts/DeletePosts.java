package me.tanyp.param.posts;

import java.io.Serializable;

/**
 * Created by tanyp on 2018/9/2
 */
public class DeletePosts implements Serializable {

    private String postsId;

    public String getPostsId() {
        return postsId;
    }

    public void setPostsId(String postsId) {
        this.postsId = postsId;
    }
}
