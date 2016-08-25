package com.loy.e.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class RequestUtil {
    public static boolean isJson(HttpServletRequest request) {
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
