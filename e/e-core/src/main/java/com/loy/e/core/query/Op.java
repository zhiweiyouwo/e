package com.loy.e.core.query;


/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public enum Op {

	eq("="), ne("<>"), like("like"), gt(">"), lt("<"), gte(">="), lte("<=");

	private final String info;
	
	private Op(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}