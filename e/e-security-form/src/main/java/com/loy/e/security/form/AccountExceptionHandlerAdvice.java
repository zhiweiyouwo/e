package com.loy.e.security.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.loy.e.common.vo.ErrorResponseData;
import com.loy.e.security.service.SystemKeyService;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@ControllerAdvice
public class AccountExceptionHandlerAdvice  {
	protected final Log logger = LogFactory.getLog(AccountExceptionHandlerAdvice.class);
	@Autowired
	private MessageSource messageSource;
	@Autowired
	SystemKeyService systemKeyService;
	
	
	
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(IncorrectCredentialsException.class)
	@ResponseBody 
	ErrorResponseData handleBadRequest(HttpServletRequest req, IncorrectCredentialsException ex) {
		ErrorResponseData data = new ErrorResponseData();
		String errorCode = "user_password_error";
		data.setErrorCode(errorCode);
		String msg = messageSource.getMessage(errorCode,null, LocaleContextHolder.getLocale());
		data.setMsg(msg);
		return data;
	 } 
	
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(UnknownAccountException.class)
	@ResponseBody 
	ErrorResponseData handleBadRequest(HttpServletRequest req, UnknownAccountException ex) {
		ErrorResponseData data = new ErrorResponseData();
		String errorCode = "user_password_error";
		data.setErrorCode(errorCode);
		String msg = messageSource.getMessage(errorCode,null, LocaleContextHolder.getLocale());
		data.setMsg(msg);
		return data;
	 } 
	
	
}
