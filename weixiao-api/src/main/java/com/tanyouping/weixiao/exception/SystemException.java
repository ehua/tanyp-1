package com.tanyouping.weixiao.exception;


import com.tanyouping.weixiao.util.StringUtils;

public class SystemException extends RuntimeException {

	private static final long serialVersionUID = 8625701017296671195L;
	
	private static final String CODE = "S";
	
	public SystemException(Throwable t) {
		super(t);
	}

	public SystemException(String message) {
		super(MessageUtil.getMessage(message, (Object[])null));
	}

	public SystemException(String message, Throwable t) {
		super(MessageUtil.getMessage(message, (Object[])null), t);
	}
	
	public SystemException(String message, Throwable t, Object... params) {
		super(MessageUtil.getMessage(message, params), t);
	}

	public final String getCode(){
		String subCode = getSubCode();
		if(StringUtils.isEmpty(subCode) && !this.getClass().isAssignableFrom(SystemException.class)){
			throw new RuntimeException("SystemException子类" + this.getClass().getName() + "应覆盖有效的getSubCode()方法并返回有效的异常代码");
		}
		if(StringUtils.isEmpty(subCode)){
			return CODE;
		} else {
			return CODE + subCode;
		}
	}
	
	/**
	 * 子类应该覆盖此方法
	 * @return
	 */
	protected String getSubCode(){
		return null;
	}
	
}
