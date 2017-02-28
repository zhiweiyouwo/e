package com.xx.demo.test.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.loy.e.core.entity.BaseEntity;
import javax.persistence.ManyToOne;
import com.xx.demo.test.domain.entity.CompanyEntity;
import java.util.Date;
import com.loy.e.basic.data.domain.entity.DictionaryEntity;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * 
 * @author Loy Fu qq群 540553957 website = http://www.17jee.com
 */

@Entity
@Table(name = "demo_customer")
public class CustomerEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@Column( nullable=true)
    private String name;
	@ManyToOne()
    private DictionaryEntity sex;
	@ManyToOne()
    private CompanyEntity company;
	@Column( nullable=true)
    private Boolean vip = false;
	@Column( nullable=true)
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dob;
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
	public DictionaryEntity getSex() {
        return sex;
    }

    public void setSex(DictionaryEntity sex) {
        this.sex = sex;
    }
	public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }
	public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }
	public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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