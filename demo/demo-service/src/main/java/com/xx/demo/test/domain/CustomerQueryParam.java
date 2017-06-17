package com.xx.demo.test.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.loy.e.core.query.data.QueryParam;
import com.loy.e.data.permission.data.DefaultDataPermissionQueryParam;

/**
 * 
 * @author Loy Fu qq群 540553957 website = http://www.17jee.com
 */
public class CustomerQueryParam  extends DefaultDataPermissionQueryParam implements QueryParam {
    private String name;
    private String sexId;
    private Boolean vip;
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dobStart;
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dobEnd;
    private String phone;
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	
	public String getSexId() {
        return sexId;
    }

    public void setSexId(String sexId) {
        this.sexId = sexId;
    }
	
	public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }
	
	public Date getDobStart() {
        return dobStart;
    }

    public void setDobStart(Date dobStart) {
        this.dobStart = dobStart;
    }
	
	public Date getDobEnd() {
        return dobEnd;
    }

    public void setDobEnd(Date dobEnd) {
        this.dobEnd = dobEnd;
    }
	
	public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}