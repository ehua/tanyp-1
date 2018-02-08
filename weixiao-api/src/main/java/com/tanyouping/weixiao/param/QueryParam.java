package com.tanyouping.weixiao.param;

import java.io.Serializable;

public class QueryParam implements Serializable{

    protected Integer start;
    protected Integer limit;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
