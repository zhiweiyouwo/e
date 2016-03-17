package com.loy.e.core.log;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public interface LoyLogService {

	public void log(String userId,String name,String opName,Object... args);
	
	public void record( String url,String method,long useTime,String opName);
	
	public void exception(String exceptionName, String stackTraceMsg);
}