package com.loy.app.common.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.loy.e.core.entity.AbstractEntity;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */

@Entity
@Table(name = "loy_exception")
public class ExceptionEntity extends AbstractEntity<String> {

    private static final long serialVersionUID = 1881617371678000672L;
    @Column(length = 200)
    private String exceptionName;
    @Lob
    private String stackTraceMsg;
    @Column(length = 50)
    String systemCode;
    private Date executeTime = new Date();

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getStackTraceMsg() {
        return stackTraceMsg;
    }

    public void setStackTraceMsg(String stackTraceMsg) {
        this.stackTraceMsg = stackTraceMsg;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

}