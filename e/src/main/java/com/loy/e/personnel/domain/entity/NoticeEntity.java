package com.loy.e.personnel.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loy.e.core.entity.BaseEntity;
import com.loy.e.sys.domain.entity.AttachmentEntity;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@Entity
@Table(name="loy_notice")
public class NoticeEntity extends BaseEntity{

	@Column(length=255)
	private String subject;
	@Lob
	private String content;
	
	private Date sendTime;
	@Column(length=100)
	private String sender;
	
	@Enumerated(EnumType.STRING)
	NoticeStatus noticeStatus = NoticeStatus.DRAFT;

	@Transient
	List<AttachmentEntity> attachments;
	@Transient
	@JsonIgnore
	List<MultipartFile> uploadAttachments;
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public NoticeStatus getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(NoticeStatus noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public List<AttachmentEntity> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<AttachmentEntity> attachments) {
		this.attachments = attachments;
	}

	public List<MultipartFile> getUploadAttachments() {
		return uploadAttachments;
	}

	public void setUploadAttachments(List<MultipartFile> uploadAttachments) {
		this.uploadAttachments = uploadAttachments;
	}

	
}