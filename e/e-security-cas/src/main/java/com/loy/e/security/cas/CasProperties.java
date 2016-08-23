package com.loy.e.security.cas;

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
@ConfigurationProperties(prefix = "e.cas")
public class CasProperties {

	public String casServerUrlPrefix;
	
	public String shiroServerUrlPrefix;
	
    public  String casFilterUrlPattern = "/shiro-cas";
    
    public String successUrl = "home.html#";
    
    public boolean trustAllHttps = true;
    // 登录地址
    public String getLoginUrl(){
    	return this.getCasLoginUrl() + "?service=" + shiroServerUrlPrefix + casFilterUrlPattern;
    }
    public String getCasLoginUrl(){
    	return casServerUrlPrefix + "/login";
    }
    public String getCasLogoutUrl(){
    	return  casServerUrlPrefix + "/logout";
    }
    
    public Map<String,String> anons = new  LinkedHashMap<String, String>();
    
    
    
	public String getCasServerUrlPrefix() {
		return casServerUrlPrefix;
	}

	public void setCasServerUrlPrefix(String casServerUrlPrefix) {
		this.casServerUrlPrefix = casServerUrlPrefix;
	}

	public String getShiroServerUrlPrefix() {
		return shiroServerUrlPrefix;
	}

	public void setShiroServerUrlPrefix(String shiroServerUrlPrefix) {
		this.shiroServerUrlPrefix = shiroServerUrlPrefix;
	}
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
	public boolean isTrustAllHttps() {
		return trustAllHttps;
	}
	public void setTrustAllHttps(boolean trustAllHttps) {
		this.trustAllHttps = trustAllHttps;
	}
	
	
	
	
}
