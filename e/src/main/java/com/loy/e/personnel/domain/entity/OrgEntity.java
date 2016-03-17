package com.loy.e.personnel.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.loy.e.core.data.Tree;
import com.loy.e.core.entity.BaseEntity;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

@Entity
@Table(name="loy_org",indexes={@Index(columnList = "code",unique=true,name="index_org_code")})

public class OrgEntity extends BaseEntity implements Tree{

	@Column(length = 100)
	private String name;
	@Column(length = 36)
	private String parentId;
	@Column(length = 255)
	private String description;
	@Column(length = 100)
	private String code;
	
	private Integer sortNum;
	
	@Transient
	private String parentName;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public Object getData() {
		return this.name;
	}

	@Override
	public String getParentId() {
		return parentId;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}