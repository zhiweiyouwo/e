package com.loy.e.core.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@ConfigurationProperties(prefix = "com.loy.e.conf")
public class Settings {

	private String attachmentBaseDirectory;
    private String supportLocales;
    private Boolean debugPageResult = false;
    private Boolean recordOperateLog = true;
    
	public String getAttachmentBaseDirectory() {
		return attachmentBaseDirectory;
	}

	public void setAttachmentBaseDirectory(String attachmentBaseDirectory) {
		this.attachmentBaseDirectory = attachmentBaseDirectory;
	}

	public String getSupportLocales() {
		return supportLocales;
	}

	public void setSupportLocales(String supportLocales) {
		this.supportLocales = supportLocales;
	}

	public Boolean getDebugPageResult() {
		return debugPageResult;
	}

	public void setDebugPageResult(Boolean debugPageResult) {
		this.debugPageResult = debugPageResult;
	}

	public Boolean getRecordOperateLog() {
		return recordOperateLog;
	}

	public void setRecordOperateLog(Boolean recordOperateLog) {
		this.recordOperateLog = recordOperateLog;
	}
	
	
	
}
