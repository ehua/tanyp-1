package com.tanyouping.weixiao.exception;

/**
 * @author duhui
 * 2017年6月20日 
 */
public class UserException extends Exception {

	private static final long serialVersionUID = 1L;

	private static final String CODE = "U";
	
	public UserException() {
		super("您的用户在其他地方已经登录");
	}

	public UserException(String message) {
		super(MessageUtil.getMessage(message, (Object[])null));
	}

	public static String getCode() {
		return CODE;
	}
}
