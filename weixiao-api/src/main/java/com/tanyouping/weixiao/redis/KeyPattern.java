package com.tanyouping.weixiao.redis;

public interface KeyPattern {

	public boolean accept(String key);

}
