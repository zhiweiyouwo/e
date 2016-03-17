package com.loy.e.core.util;

import java.util.Locale;

import org.apache.shiro.subject.Subject;
import org.springframework.context.i18n.LocaleContextHolder;

import com.loy.e.core.web.SimpleUser;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class UserUtils {
	
	public static SimpleUser getSimipleUser(){
		SimpleUser simpleUser = null;
		Subject subject = org.apache.shiro.SecurityUtils.getSubject();
		if(subject != null){
			simpleUser = (SimpleUser) subject.getPrincipal();
		}
		return simpleUser;
	}
	public static String getUserId(){
		Subject currentUser = org.apache.shiro.SecurityUtils.getSubject();
		SimpleUser simpleUser = (SimpleUser) currentUser.getPrincipal();
		if(simpleUser != null){
			String id = simpleUser.getId();
			return id;
		}
		return null;
	}
   public static String getUsername(){	
	   Subject currentUser = org.apache.shiro.SecurityUtils.getSubject();
	   SimpleUser simpleUser = (SimpleUser) currentUser.getPrincipal();
	   return simpleUser.getUsername();
	}
   public static Locale getLocale() {
	   return LocaleContextHolder.getLocale();
   }
}