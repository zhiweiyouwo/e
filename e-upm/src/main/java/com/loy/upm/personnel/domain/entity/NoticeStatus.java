package com.loy.upm.personnel.domain.entity;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public enum NoticeStatus {
    DRAFT("草稿"),
    SENT("已发送");

    private final String info;

    private NoticeStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}