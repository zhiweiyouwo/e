package com.loy.e.core.conf;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@Component
@ConfigurationProperties(prefix = "e.conf")
public class Settings {

    public String attachmentBaseDirectory;
    public String defaultLocale;
    public List<String> supportLocales = new ArrayList<String>();

    public Map<String, List<String>> staticMappings = new LinkedHashMap<String, List<String>>();

    public Boolean debugPageResult = false;
    public Boolean recordOperateLog = true;
    public String defaultPage = "restApi.html";

    public String getAttachmentBaseDirectory() {
        return attachmentBaseDirectory;
    }

    public void setAttachmentBaseDirectory(String attachmentBaseDirectory) {
        this.attachmentBaseDirectory = attachmentBaseDirectory;
    }

    public List<String> getSupportLocales() {
        return this.supportLocales;
    }

    public void setSupportLocales(List<String> supportLocales) {
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

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public Map<String, List<String>> getStaticMappings() {
        return staticMappings;
    }

    public void setStaticMappings(Map<String, List<String>> staticMappings) {
        this.staticMappings = staticMappings;
    }

    public String getDefaultPage() {
        return defaultPage;
    }

    public void setDefaultPage(String defaultPage) {
        this.defaultPage = defaultPage;
    }

}
