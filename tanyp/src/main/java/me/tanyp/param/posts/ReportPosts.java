package me.tanyp.param.posts;

import java.io.Serializable;

/**
 * Created by tanyp on 2018/9/2
 */
public class ReportPosts implements Serializable {

    private String remark;
    private String postsId;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPostsId() {
        return postsId;
    }

    public void setPostsId(String postsId) {
        this.postsId = postsId;
    }
}
