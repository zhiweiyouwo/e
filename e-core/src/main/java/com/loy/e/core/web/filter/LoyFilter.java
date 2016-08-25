package com.loy.e.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

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
@Order(value = -100)
public class LoyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //HttpServletResponse resp = (HttpServletResponse)response;
        //HttpServletRequest req =(HttpServletRequest)request;
        //System.out.println(req.getRequestURL().toString());
        //resp.addHeader("Access-Control-Allow-Origin","http://localhost:29090/example");
        //resp.addHeader("Access-Control-Allow-Credentials", "true");
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }

}