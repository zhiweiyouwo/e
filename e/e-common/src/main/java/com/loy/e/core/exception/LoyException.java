package com.loy.e.core.exception;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class LoyException extends  RuntimeException{
	
	private static final long serialVersionUID = -6706307799181451578L;
	private String errorKey;
	private String errorMsg;
	
	private Object[] params;
	public LoyException(String errorKey){
		this.errorKey = errorKey;
	}
	public LoyException(String errorCode,Object... params){
		this.errorKey = errorCode;
		this.params = params;
	}
	public String getErrorKey() {
		return errorKey;
	}
	public void setErrorKey(String errorKey) {
		this.errorKey = errorKey;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Object[] getParams() {
		return params;
	}
	public void setParams(Object[] params) {
		this.params = params;
	}

//	@Override
//	public  Throwable fillInStackTrace() {
//		return this;
//	}
	
}