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
@Table(name="loy_attachment") 
public class AttachmentEntity extends AbstractEntity<String>{

	private static final long serialVersionUID = 4910053112873181081L;

	@Column(length=100)
	private String fileShowName;
	
	@Column(length=100)
	private String fileName;
	
	@Column(length=36)
	private String targetId;
	
	public String getFileShowName() {
		return fileShowName;
	}
	public void setFileShowName(String fileShowName) {
		this.fileShowName = fileShowName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	
	
}