package com.loy.e.sys.domain;

import com.loy.e.core.annotation.ConditionParam;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class RoleQueryParam {
	
	@ConditionParam(name="name")
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}