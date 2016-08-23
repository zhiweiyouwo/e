package com.loy.e.security.vo;

import java.io.Serializable;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class User implements Serializable{
	private static final long serialVersionUID = 7542071784874821800L;
	
	String id;
	String name;
	String username;
	String password;
	Boolean photo = false;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getPhoto() {
		return photo;
	}
	public void setPhoto(Boolean photo) {
		this.photo = photo;
	}
	
	
}
