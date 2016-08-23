package com.loy.upm.personnel.domain;

import com.loy.upm.personnel.domain.entity.NoticeStatus;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class NoticeQueryParam {
	
	private NoticeStatus noticeStatus;
	private String subject;
	private String creatorId;
	private String readerId;
    private Boolean seen = null;
    
	public NoticeStatus getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(NoticeStatus noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getReaderId() {
		return readerId;
	}

	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}

	public Boolean getSeen() {
		return seen;
	}

	public void setSeen(Boolean seen) {
		this.seen = seen;
	}
	
	
}