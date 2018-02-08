package com.tanyouping.weixiao.exception;


import com.tanyouping.weixiao.util.StringUtils;

public class MessageUtil {

	public static String getMessage(String message, Object... params){
		if(StringUtils.isNotEmpty(message)
				&& message.startsWith("{") 
				&& message.endsWith("}")){
			return message;
		}else{
			return message;
		}
	}
	
}
