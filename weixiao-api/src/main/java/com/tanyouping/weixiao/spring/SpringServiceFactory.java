package com.tanyouping.weixiao.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;

public class SpringServiceFactory implements ApplicationContextAware {

	private static volatile SpringServiceFactory factory;

	private ApplicationContext ctx;
	
	private final List<ApplicationContext> children = new ArrayList<>();
	
	public static SpringServiceFactory get(){
		return factory;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T getService(Class clazz) {
		return (T) get().ctx.getBean(clazz.getName());
	}

	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> clazz, String name) {
		return (T) get().ctx.getBean(name);
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		factory = this;
		this.ctx = ctx;
	}
	
	public ApplicationContext getApplicationContext(){
		return get().ctx;
	}
	public List<ApplicationContext> getChildrenApplicationContext(){
		return get().children;
	}
	public void addChildrenApplicationContext(ApplicationContext child){
		get().children.add(child);
	}

}
