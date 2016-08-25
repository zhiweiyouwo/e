package com.loy.app.common.vo;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public enum UserStatus {

    NORMAL("正常状态"),
    DISABLED("禁用状态");

    private final String info;

    private UserStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}