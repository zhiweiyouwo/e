package com.loy.app.common.vo;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957 
 * @since 1.7
 * @version 1.0.0
 *
 */
public enum SexEnum {
	MALE("男"), 
	FEMALE("女");

    private final String info;

    private SexEnum(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
