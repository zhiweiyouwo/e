package com.xx.demo.test.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.loy.e.data.permission.data.DefaultDataPermissionQueryParam;

/**
 * 
 * @author Loy Fu qq群 540553957 website = http://www.17jee.com
 */
public class CompanyQueryParam extends DefaultDataPermissionQueryParam {
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registerDateStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registerDateEnd;
	private String phone;

	String orderProperty = "";

	String direction = "";

	public String getOrderProperty() {
		return orderProperty;
	}

	public void setOrderProperty(String orderProperty) {
		this.orderProperty = orderProperty;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegisterDateStart() {
		return registerDateStart;
	}

	public void setRegisterDateStart(Date registerDateStart) {
		this.registerDateStart = registerDateStart;
	}

	public Date getRegisterDateEnd() {
		return registerDateEnd;
	}

	public void setRegisterDateEnd(Date registerDateEnd) {
		this.registerDateEnd = registerDateEnd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}