package com.loy.e.sys.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.loy.e.core.entity.BaseEntity;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@Entity
@Table(name="loy_dictionary") 
public class DictionaryEntity extends BaseEntity{
	@Column(length = 100)
	private String i18nKey;
	@Column(length = 100)
	private String code;
	@Column(length = 255)
	private String defaultLable;
	
	
	public String getI18nKey() {
		return i18nKey;
	}
	public void setI18nKey(String i18nKey) {
		this.i18nKey = i18nKey;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDefaultLable() {
		return defaultLable;
	}
	public void setDefaultLable(String defaultLable) {
		this.defaultLable = defaultLable;
	}
}