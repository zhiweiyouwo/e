package com.loy.e.security.form;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@ConfigurationProperties(prefix = "e.form")
public class FormProperties {
    public String successUrl = "home.html#";
    public String loginUrl = "index.html";
    
    public Map<String,String> anons = new  LinkedHashMap<String, String>();
    
	public Map<String, String> getAnons() {
		return anons;
	}
	public void setAnons(Map<String, String> anons) {
		this.anons = anons;
	}
	public String getSuccessUrl() {
		return successUrl;
	}
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}
	public String getLoginUrl() {
		return loginUrl;
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	
	
	
	
}
