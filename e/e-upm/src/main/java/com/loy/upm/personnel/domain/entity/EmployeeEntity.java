package com.loy.upm.personnel.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.loy.app.common.vo.SexEnum;
import com.loy.upm.sys.domain.entity.UserEntity;
/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@Entity
@Table(name="loy_employee" ,indexes={@Index(columnList = "employeeNo",unique=true,name="index_employee_no")}) 

public class EmployeeEntity extends UserEntity{

	private static final long serialVersionUID = 52362436310211443L;
	@Column(length = 10)
	private String employeeNo;
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private SexEnum sex;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dob;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private OrgEntity organization;
	@ManyToOne(fetch = FetchType.EAGER)
	PositionEntity position;
	public OrgEntity getOrganization() {
		return organization;
	}
	public void setOrganization(OrgEntity organization) {
		this.organization = organization;
	}
	public PositionEntity getPosition() {
		return position;
	}
	public void setPosition(PositionEntity position) {
		this.position = position;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public SexEnum getSex() {
		return sex;
	}
	public void setSex(SexEnum sex) {
		this.sex = sex;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	
	
}