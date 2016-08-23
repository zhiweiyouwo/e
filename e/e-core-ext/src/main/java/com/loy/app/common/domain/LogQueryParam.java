package com.loy.app.common.domain;

import com.loy.e.core.annotation.ConditionParam;
import com.loy.e.core.query.Op;
import com.loy.e.core.query.SortQueryParam;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class LogQueryParam extends SortQueryParam{

	@ConditionParam(name="operator",op=Op.like)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
