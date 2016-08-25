package com.loy.e.security.form;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.loy.e.common.vo.NotLoginResponse;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

public class LoyFormAuthenticationFilter extends FormAuthenticationFilter {

    protected void redirectToLogin(ServletRequest request, ServletResponse response)
            throws IOException {

        if (isJson((HttpServletRequest) request)) {
            NotLoginResponse notLoginResponse = new NotLoginResponse();
            response.getWriter().write(notLoginResponse.toJson());
            return;
        } else {
            super.redirectToLogin(request, response);
        }
    }

    boolean isJson(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        if (accept == null) {
            return false;
        }
        String[] arr = accept.split(",");
        if (arr == null) {
            return false;
        }
        for (String a : arr) {
            if ("application/json".equalsIgnoreCase(a)) {
                return true;
            }
        }
        return false;
    }
}