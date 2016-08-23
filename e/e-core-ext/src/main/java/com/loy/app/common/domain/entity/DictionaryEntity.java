package com.loy.app.common.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.loy.e.core.entity.AbstractEntity;

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
public class DictionaryEntity extends AbstractEntity <String>{

	private static final long serialVersionUID = 542162235729891334L;
	
	@Column(length = 100)
	private String i18nKey;
	@Column(length = 100)
	private String code;
	@Column(length = 255)
	private String name;
	@Column(length = 100,name="group_")
	private String group;
	
	
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
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
}