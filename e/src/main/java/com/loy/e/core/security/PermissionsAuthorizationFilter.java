package com.loy.e.core.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loy.e.core.data.ErrorResponseData;
import com.loy.e.core.log.LoyLogService;
import com.loy.e.core.util.ExceptionUtil;
import com.loy.e.core.util.UserUtils;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class PermissionsAuthorizationFilter extends org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter{
	 private static final Logger log = LoggerFactory.getLogger(PermissionsAuthorizationFilter.class);

	 private LoyLogService loyLogService;
		
	public PermissionsAuthorizationFilter(LoyLogService loyLogService){
			this.loyLogService = loyLogService;
	}
	public MessageSource messageSource;
	
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

	        Subject subject = getSubject(request, response);
	        if (subject.getPrincipal() == null) {
	            saveRequestAndRedirectToLogin(request, response);
	        } else {
	        	ObjectMapper objectMapper = new ObjectMapper();
	            try {
	            	JsonGenerator  jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
	            	ErrorResponseData error = new ErrorResponseData();
	            	error.setErrorCode("not_permission");
	            	String msg = messageSource.getMessage("no_permission",null, UserUtils.getSimipleUser().getLocale());
	            	error.setMsg(msg);
	            	jsonGenerator.writeObject(error);   
	            } catch (IOException e) {
	            	String stackTraceMsg = ExceptionUtil.exceptionStackTrace(e);
	            	loyLogService.exception(e.getClass().getName(), stackTraceMsg);
	            	throw e;
	            }
	        }
	        return false;
	 }
}