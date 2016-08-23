package com.loy.upm.sys.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="loy_menu_i18n")
public class MenuI18nEntity extends BaseEntity{

	private static final long serialVersionUID = -4472987462963633072L;
	@Column(length = 100,name="key_")
	private String key;
	@Column(length = 20,name="lang_")
	private String lang;
	@Column(length = 50)
	private String systemCode;
	@Column(length = 100,name="value_")
	private String value;
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
