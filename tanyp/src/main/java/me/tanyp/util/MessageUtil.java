package me.tanyp.util;


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
