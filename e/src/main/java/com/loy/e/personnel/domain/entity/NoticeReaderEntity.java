package com.loy.e.personnel.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
@Table(name="loy_notice_reader")
public class NoticeReaderEntity extends AbstractEntity<String>{

	@ManyToOne
	private NoticeEntity notice;
	@Column(length=36)
	private String readerId;
	@Column(length=100)
	private String readerName;
	
	private Date readerTime;

	private Boolean seen = Boolean.FALSE;
	
	public NoticeEntity getNotice() {
		return notice;
	}

	public void setNotice(NoticeEntity notice) {
		this.notice = notice;
	}

	public String getReaderId() {
		return readerId;
	}

	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}

	public String getReaderName() {
		return readerName;
	}

	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}

	public Date getReaderTime() {
		return readerTime;
	}

	public void setReaderTime(Date readerTime) {
		this.readerTime = readerTime;
	}

	public Boolean getSeen() {
		return seen;
	}

	public void setSeen(Boolean seen) {
		this.seen = seen;
	}
	
	
	
}