package com.tanyouping.weixiao.param;

import java.io.Serializable;

/**
 * Created by Tan Youping on 2018/1/10
 */
public class CommentParam implements Serializable{

    private String moodId;
    private String text;

    public String getMoodId() {
        return moodId;
    }

    public void setMoodId(String moodId) {
        this.moodId = moodId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
