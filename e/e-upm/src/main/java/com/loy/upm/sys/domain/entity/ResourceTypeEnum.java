package com.loy.upm.sys.domain.entity;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public enum ResourceTypeEnum {

    MENU("菜单"),
    BUTTON("按钮");

    private final String info;

    private ResourceTypeEnum(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}