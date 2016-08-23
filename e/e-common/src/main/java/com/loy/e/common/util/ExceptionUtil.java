package com.loy.e.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class ExceptionUtil {

	public static String exceptionStackTrace(Throwable e){
		StringWriter sw=new StringWriter();  
        PrintWriter pw=new PrintWriter(sw);  
        e.printStackTrace(pw);
        String stackTraceMsg = sw.toString();
        return stackTraceMsg;
	}
}
