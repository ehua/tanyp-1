package me.tanyp.util.basic;


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
