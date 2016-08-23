package com.loy.e.security.vo;

import java.io.Serializable;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class Permission implements Serializable {
	

	private static final long serialVersionUID = -2293771119814383328L;
	String accessCode;
	String url;
	public String getAccessCode() {
		return accessCode;
	}
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
