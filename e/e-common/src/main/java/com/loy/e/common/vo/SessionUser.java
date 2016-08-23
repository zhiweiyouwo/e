package com.loy.e.common.vo;

import java.io.Serializable;
import java.util.Locale;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class SessionUser implements Serializable {
	private static final long serialVersionUID = 8781604052997403922L;
	public String id;
	public String username;
	public String name;
	public Boolean photo;
	public Locale locale = Locale.CHINA;
	public String lang;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public Boolean getPhoto() {
		return photo;
	}
	public void setPhoto(Boolean photo) {
		this.photo = photo;
	}
	
	
}
