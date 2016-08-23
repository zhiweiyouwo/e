package com.loy.e.core.query;


/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public enum Direction {

	ASC("ASC") ,DESC("DESC");
	
private final String info;
	
	private Direction(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}