package com.loy.e.core.conf;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.loy.e.core.aop.LoyAspect;
import com.loy.e.core.ql.DynamicQlStatementBuilder;
import com.loy.e.core.ql.impl.DefaultDynamicQlStatementBuilder;
import com.loy.e.core.web.dispatch.DefaultDispatchServlet;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@Configuration 


public class WebConfig extends WebMvcConfigurerAdapter{  //WebMvcConfigurationSupport
	@Value("${spring.jackson.date-format}")
	private String dateFormat; 
	@Bean  
	public DynamicQlStatementBuilder dynamicQlStatementBuilder(){
		DynamicQlStatementBuilder dynamicQlStatementBuilder = new DefaultDynamicQlStatementBuilder();
		try {
			dynamicQlStatementBuilder.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  dynamicQlStatementBuilder;
	}
	
//	@Bean  
//    public FilterRegistrationBean filterRegistrationBean(LoyFilter loyFilter){  
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();  
//        filterRegistrationBean.setFilter(loyFilter);  
//        filterRegistrationBean.setEnabled(true);  
//        filterRegistrationBean.addUrlPatterns("/**");  
//        return filterRegistrationBean;  
//    }    
//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		System.out.println(converters);
//	}
//	public void addInterceptors(InterceptorRegistry registry) {  
//		//registry.addInterceptor(new LocaleChangeInterceptor()).addPathPatterns("/**");
//        //registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns("/**");  
//        
//        
//    }  
//	public class UserSecurityInterceptor implements HandlerInterceptor {  
//		  
//	    @Override  
//	    public boolean preHandle(HttpServletRequest request,  
//	            HttpServletResponse response, Object handler) throws Exception {  
//	          
//	        return true;  
//	    }  
//	  
//	    @Override  
//	    public void postHandle(HttpServletRequest request,  
//	            HttpServletResponse response, Object handler,  
//	            ModelAndView modelAndView) throws Exception {  
//	    	
//	    	System.out.println(modelAndView);
//	    }  
//	  
//	    @Override  
//	    public void afterCompletion(HttpServletRequest request,  
//	            HttpServletResponse response, Object handler, Exception ex)  
//	            throws Exception {  
//	  
//	    }  
//	  
//	}  
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		for(HttpMessageConverter<?> c : converters){
			if(c instanceof MappingJackson2HttpMessageConverter){
				MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = (MappingJackson2HttpMessageConverter)c;
				ObjectMapper objectMapper= mappingJackson2HttpMessageConverter.getObjectMapper();
				objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
				if(this.dateFormat != null){
					DateFormat myDateFormat = new SimpleDateFormat(dateFormat);
					objectMapper.setDateFormat(myDateFormat);
				}
			}
		}
	}
	@Bean
    public SessionLocaleResolver localeResolver() {
		SessionLocaleResolver  localeResolver = new SessionLocaleResolver();
        return localeResolver;
    }
	
	@Bean
    public MessageSource messageSource() {
		
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        String[] basenames = {"classpath:i18n/message",
        		"classpath:i18n/sys/user",
        		"classpath:i18n/personnel/org"};
        messageSource.setBasenames(basenames);
        messageSource.setCacheSeconds(5);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
	
	@Bean
    public DefaultDispatchServlet dispatcherServlet() {
		DefaultDispatchServlet dispatchServlet = new DefaultDispatchServlet();
        return dispatchServlet;
    }
	
	@Bean
    public LoyAspect logAspect() {
         return new LoyAspect();
    }
}