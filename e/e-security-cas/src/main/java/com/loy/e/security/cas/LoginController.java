package com.loy.e.security.cas;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@Controller
@ApiIgnore
public class LoginController {
	@Autowired
	CasProperties casProperties;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login() {
		return "redirect:" + casProperties.getLoginUrl();
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "redirect:" + casProperties.getLoginUrl();
	}

	@RequestMapping(value = "/logout", method = {RequestMethod.GET,RequestMethod.POST})
	public String logout() {
		 SecurityUtils.getSubject().logout(); 
		return "redirect:"+casProperties.getCasLogoutUrl();
	}
}
