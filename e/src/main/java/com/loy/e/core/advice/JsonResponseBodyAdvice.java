package com.loy.e.core.advice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.loy.e.core.annotation.Converter;
import com.loy.e.core.data.ErrorResponseData;
import com.loy.e.core.data.Response;
import com.loy.e.core.data.SuccessResponse;
import com.loy.e.core.data.SuccessResponseData;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@ControllerAdvice
public class JsonResponseBodyAdvice implements ResponseBodyAdvice<Object>{
	protected final Log logger = LogFactory.getLog(JsonResponseBodyAdvice.class);
	
	@Autowired
	private MessageSource messageSource;
	public Object beforeBodyWrite(Object object, MethodParameter methodParameter,
			MediaType mediaType, Class clazz, 
			ServerHttpRequest request,
			ServerHttpResponse response) {
		if(object instanceof Response){
			return object;
		}
		if(object == null){
			return SuccessResponse.newInstance(); 
		}
		
		Converter converterAnnotation = methodParameter.getMethodAnnotation(Converter.class);
		if(converterAnnotation != null){
			com.loy.e.core.converter.Converter newInstance= null;
			Class<? extends com.loy.e.core.converter.Converter> converter = converterAnnotation.value();
			try {
				newInstance = converter.newInstance();
				object = newInstance.converter(object);
			} catch (InstantiationException | IllegalAccessException e) {
				logger.error("数据转换错误", e);
				ErrorResponseData rrrorResponse = new ErrorResponseData();
				messageSource.getMessage("data_converter_error", null, LocaleContextHolder.getLocale());
				return rrrorResponse;
			}
		}
		
		SuccessResponseData data = new SuccessResponseData(object);
		return data;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean supports(MethodParameter methodParameter, Class clazz) {
		if(clazz.isAssignableFrom(MappingJackson2HttpMessageConverter.class)){
			return true;
		}
		return false;
	}

}