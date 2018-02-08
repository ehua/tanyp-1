package com.tanyouping.weixiao.param;

import java.util.Date;

/**
 * Created by Tan Youping on 2018/1/9
 */
public class UserQueryParam extends QueryParam{

    private String name;
    private Date startCreated;
    private Date endCreated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartCreated() {
        return startCreated;
    }

    public void setStartCreated(Date startCreated) {
        this.startCreated = startCreated;
    }

    public Date getEndCreated() {
        return endCreated;
    }

    public void setEndCreated(Date endCreated) {
        this.endCreated = endCreated;
    }
}
