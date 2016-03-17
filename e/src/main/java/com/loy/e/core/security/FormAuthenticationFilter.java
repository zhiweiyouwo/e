package com.loy.e.core.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loy.e.core.data.ErrorResponseData;
import com.loy.e.core.log.LoyLogService;
import com.loy.e.core.util.ExceptionUtil;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */


public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {
	
	private static final Logger log = LoggerFactory.getLogger(FormAuthenticationFilter.class);
	private LoyLogService loyLogService;
	
	public FormAuthenticationFilter(LoyLogService loyLogService){
		this.loyLogService = loyLogService;
	}
	@SuppressWarnings("deprecation")
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
	
		ObjectMapper objectMapper = new ObjectMapper();
        try {
        	JsonGenerator  jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
        	ErrorResponseData error = new ErrorResponseData();
        	error.setErrorCode("not_login");
        	jsonGenerator.writeObject(error);   
        } catch (IOException e) {
        	String stackTraceMsg = ExceptionUtil.exceptionStackTrace(e);
        	loyLogService.exception(e.getClass().getName(), stackTraceMsg);
        	throw e;
        }
    }
}