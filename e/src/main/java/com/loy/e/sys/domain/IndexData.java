package com.loy.e.sys.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.loy.e.core.data.TreeNode;
import com.loy.e.core.web.LocaleVO;
import com.loy.e.core.web.SimpleUser;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class IndexData {

	private String version;
	private SimpleUser simpleUser;
	
	List<TreeNode> menuData;

	private boolean photo;
	private Map<String,Boolean> accessCodes = new HashMap<String,Boolean>();
	private long notReadNotice = 0;
	private List<LocaleVO> supportLocales = new ArrayList<LocaleVO>();
	public List<TreeNode> getMenuData() {
		return menuData;
	}

	public void setMenuData(List<TreeNode> menuData) {
		this.menuData = menuData;
	}

	public SimpleUser getSimpleUser() {
		return simpleUser;
	}

	public void setSimipleUser(SimpleUser simpleUser) {
		this.simpleUser = simpleUser;
	}

	public Map<String, Boolean> getAccessCodes() {
		return accessCodes;
	}

	public void setAccessCodes(Map<String, Boolean> accessCodes) {
		this.accessCodes = accessCodes;
	}

	public boolean isPhoto() {
		return photo;
	}

	public void setPhoto(boolean photo) {
		this.photo = photo;
	}

	public long getNotReadNotice() {
		return notReadNotice;
	}

	public void setNotReadNotice(long notReadNotice) {
		this.notReadNotice = notReadNotice;
	}

	public List<LocaleVO> getSupportLocales() {
		return supportLocales;
	}

	public void setSupportLocales(List<LocaleVO> supportLocales) {
		this.supportLocales = supportLocales;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	
}