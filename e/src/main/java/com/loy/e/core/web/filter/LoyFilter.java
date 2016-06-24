package com.loy.e.core.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@Component("loyFilter") 
@Order(value=-100)
public class LoyFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain){
		try{
			HttpServletRequest req = (HttpServletRequest)request;
			chain.doFilter(request, response);
		}catch (Exception e){
			e.printStackTrace();
		}	
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}