package com.loy.cms.content.domain.entity;

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
@Table(name = "cms_content")

public class ContentEntity extends BaseEntity{

	private static final long serialVersionUID = 7320735445474101370L;
	
	private String title;
	private String content;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
