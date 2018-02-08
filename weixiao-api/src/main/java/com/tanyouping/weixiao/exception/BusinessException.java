package com.tanyouping.weixiao.exception;

import com.tanyouping.weixiao.util.StringUtils;

public class BusinessException extends RuntimeException{

    private static final String CODE = "B";

    public BusinessException(Throwable t) {
        super(t);
    }

    public BusinessException(String message) {
        super(MessageUtil.getMessage(message, (Object[])null));
    }

    public BusinessException(String message, Throwable t) {
        super(MessageUtil.getMessage(message, (Object[])null), t);
    }

    public BusinessException(String message, Throwable t, Object... params) {
        super(MessageUtil.getMessage(message, params), t);
    }

    public final String getCode(){
        String subCode = getSubCode();
        if(StringUtils.isEmpty(subCode) && !this.getClass().isAssignableFrom(BusinessException.class)){
            throw new RuntimeException("BusinessException子类" + this.getClass().getName() + "应覆盖有效的getSubCode()方法并返回有效的异常代码");
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

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
