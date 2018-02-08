package com.tanyouping.weixiao.domain;

import com.tanyouping.weixiao.domain.base.BaseBelongEntity;

/**
 * Created by Tan Youping on 2018/1/9
 * 用户心情关系
 */
public class MoodRS extends BaseBelongEntity {

    private Integer moodId;

    public Integer getMoodId() {
        return moodId;
    }

    public void setMoodId(Integer moodId) {
        this.moodId = moodId;
    }
}
