package com.loy.e.common.vo;

import org.apache.commons.lang.StringUtils;

import com.loy.e.common.Constants;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class NotLoginResponse extends ErrorResponseData{

	private  String loginUrl = "login";
    public NotLoginResponse(){
    	super();
    	errorCode = Constants.NOT_LOGIN_CODE;
    }
	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	
	public String toJson(){
		StringBuilder sb = new StringBuilder("{\"errorCode\":");
		sb.append("\"");
		sb.append(errorCode);
		sb.append("\"");
		
		sb.append(",");
		sb.append("\"success\":");
		sb.append(this.success);
	
		
		if(StringUtils.isNotEmpty(this.loginUrl)){
			sb.append(",");
			sb.append("\"loginUrl\":");
			sb.append("\"");
			sb.append(this.loginUrl);
			sb.append("\"");
		}
		if(StringUtils.isNotEmpty(this.msg)){
			sb.append(",");
			sb.append("\"msg\":");
			sb.append("\"");
			sb.append(this.msg);
			sb.append("\"");
		}
		sb.append("}");
		return sb.toString();
	}
}
