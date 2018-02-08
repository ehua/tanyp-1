package com.tanyouping.weixiao.domain;

import com.tanyouping.weixiao.domain.base.BaseBelongEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tan Youping on 2018/1/9
 * 用户评论
 */
public class MarkComment extends BaseBelongEntity {

    private List<Comment> comment = new ArrayList<>();
    private String moodId;

    public String getMoodId() {
        return moodId;
    }

    public void setMoodId(String moodId) {
        this.moodId = moodId;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
}
