package com.xx.demo.test.domain;

import com.loy.e.core.query.data.SortQueryParam;
import java.util.Date;
import com.loy.e.core.query.data.QueryParam;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author Loy Fu qq群 540553957 website = http://www.17jee.com
 */
public class CompanyQueryParam  extends SortQueryParam implements QueryParam {
    private String name;
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date registerDateStart;
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date registerDateEnd;
    private String phone;
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	
	public Date getRegisterDateStart() {
        return registerDateStart;
    }

    public void setRegisterDateStart(Date registerDateStart) {
        this.registerDateStart = registerDateStart;
    }
	
	public Date getRegisterDateEnd() {
        return registerDateEnd;
    }

    public void setRegisterDateEnd(Date registerDateEnd) {
        this.registerDateEnd = registerDateEnd;
    }
	
	public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}