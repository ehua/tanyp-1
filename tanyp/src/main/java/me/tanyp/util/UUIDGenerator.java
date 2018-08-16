package me.tanyp.util;

public class UUIDGenerator {

	public static String generate(String prev){
		return generate(prev, false);
	}
	
	public static String generate(String prev, boolean linked){
		if(linked){
			return StringUtils.isEmpty(prev)?UUID.randomUUID().linked().toString():prev + '-' + UUID.randomUUID().linked().toString();
		}else{
			return StringUtils.isEmpty(prev)?UUID.randomUUID().toString():prev + '-' + UUID.randomUUID().toString();
		}
	}
	
}
