package com.loy.e.core.web;

import java.util.Locale;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.util.UserUtils;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping(value="/",method={RequestMethod.POST,RequestMethod.GET})

public class IndexController { 
	
	@ControllerLogExeTime(description="登入")
	@RequestMapping(value="/login") 
	public void login(String username,
			 String password) { 
		 AuthenticationToken token = new UsernamePasswordToken(username, password);
		 Subject currentUser = SecurityUtils.getSubject();
		 currentUser.login(token);
	}
	
	
	@ControllerLogExeTime(description="登出")
	@RequestMapping(value="/logout") 
	public void logout() { 
		 Subject currentUser = SecurityUtils.getSubject();
		 currentUser.logout();
	}
	
	
	@RequestMapping(value="/lang") 
	public void lang(String lang) { 
		SimpleUser simpleUser = UserUtils.getSimipleUser();
		String[] temp = lang.split("_");
		Locale locale = new Locale(temp[0], temp[1]);
		simpleUser.setLocale(locale);
	}
}
