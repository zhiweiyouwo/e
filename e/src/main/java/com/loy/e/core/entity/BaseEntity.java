package com.loy.e.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

@MappedSuperclass
public abstract class BaseEntity extends AbstractEntity <String> implements Operator{
	
	@Column(updatable=false,name="created_time")
    private Date createdTime = new Date();
	
    private Date modifiedTime = new Date();
    @Column(updatable=false,length=36)
	private String creatorId;
    @Column(length=36)
	private String modifierId;
	
	
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getModifierId() {
		return modifierId;
	}
	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}
	@Override
	public void setOperator(String id) {
		this.modifierId = id;
	}
	@Override
	public String getOperator() {
		return this.modifierId;
	}
	
}