package com.loy.upm.sys.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.loy.e.core.entity.BaseEntity;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

@Entity
@Table(name="loy_system" ,indexes={@Index(columnList = "code",unique=true,name="index_system_code")}) 


public class SystemEntity extends BaseEntity {

	private static final long serialVersionUID = -5501157366093931017L;
	@Column(length = 100)
	private String  code;
	@Column(length = 200)
	private String url;
	@Column(length = 100)
	private String name;
	@Column(length = 30)
	private String prefixFlag;
	private Integer sortNum = 0;
	@Column(length = 100)
	private String lableKey;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public String getPrefixFlag() {
		return prefixFlag;
	}
	public void setPrefixFlag(String prefixFlag) {
		this.prefixFlag = prefixFlag;
	}
	public String getLableKey() {
		return lableKey;
	}
	public void setLableKey(String lableKey) {
		this.lableKey = lableKey;
	}
	
	
}
