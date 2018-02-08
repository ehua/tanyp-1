package com.tanyouping.weixiao.domain;

import com.tanyouping.weixiao.domain.base.BaseBelongEntity;

/**
 * Created by Tan Youping on 2018/1/9
 * 收藏
 */
public class CollectionRS extends BaseBelongEntity {

    private String moodId;

    public String getMoodId() {
        return moodId;
    }

    public void setMoodId(String moodId) {
        this.moodId = moodId;
    }
}
