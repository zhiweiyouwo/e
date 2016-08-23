package com.loy.app.common.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */


public class UserDetail implements Serializable{
	
	private static final long serialVersionUID = -2353342143410777624L;

	private String id;
	
	private String username;
	
    private String name;
 
	private String email;

	private String phone;
	
    private Boolean deleted = Boolean.FALSE; 
   
    private String salt;
    
    //private byte[]  photoData;
   
    private String roleNames;
    
    private String employeeNo;

	private SexEnum sex;

	private Date dob;
	
	private String orgId ;
	
	private String orgName ;

	private String positionId;
	
	private String positionName;
	
	private UserStatus userStatus;
	
	private Boolean photo;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public Boolean getPhoto() {
		return photo;
	}

	public void setPhoto(Boolean photo) {
		this.photo = photo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
