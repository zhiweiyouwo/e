package com.loy.e.security.service;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public interface LoyLogService {

	public void log(String systemCode,String userId,String name,String opName,Object... args);
	
	public void record(String systemCode, String url,String method,long useTime,String opName);
	
	public void exception(String systemCode,String exceptionName, String stackTraceMsg);
}