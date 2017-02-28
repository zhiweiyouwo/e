package com.xx.demo.test.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.loy.e.core.entity.BaseEntity;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * 
 * @author Loy Fu qq群 540553957 website = http://www.17jee.com
 */

@Entity
@Table(name = "demo_company")
public class CompanyEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column( nullable=true)
    private String name;
	@Column( nullable=true)
    private Integer registeredCapital;
	@Column( nullable=true)
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date registerDate;
	@Column( nullable=true)
    private String contactName;
	@Column( nullable=true)
    private String phone;
	@Column( nullable=true)
    private String address;
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	public Integer getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(Integer registeredCapital) {
        this.registeredCapital = registeredCapital;
    }
	public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
	public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
	public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}