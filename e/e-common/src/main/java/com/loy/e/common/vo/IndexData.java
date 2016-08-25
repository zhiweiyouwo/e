package com.loy.e.common.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.loy.e.common.tree.TreeNode;
import com.loy.e.common.vo.LocaleVO;
import com.loy.e.common.vo.SessionUser;
import com.loy.e.common.vo.System;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@SuppressWarnings("rawtypes")
public class IndexData implements Serializable {
    private static final long serialVersionUID = 385770670924715475L;

    private String version;
    private SessionUser simpleUser;

    List<TreeNode> menuData;
    List<System> mySystems;
    private boolean photo;

    String defaultPage = "restapi.html";

    private Map<String, Boolean> accessCodes = new HashMap<String, Boolean>();
    private long notReadNotice = 0;
    private List<LocaleVO> supportLocales = new ArrayList<LocaleVO>();

    public List<TreeNode> getMenuData() {
        return menuData;
    }

    public void setMenuData(List<TreeNode> menuData) {
        this.menuData = menuData;
    }

    public SessionUser getSimpleUser() {
        return simpleUser;
    }

    public void setSimipleUser(SessionUser simpleUser) {
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

    public List<System> getMySystems() {
        return mySystems;
    }

    public void setMySystems(List<System> mySystems) {
        this.mySystems = mySystems;
    }

    public String getDefaultPage() {
        return defaultPage;
    }

    public void setDefaultPage(String defaultPage) {
        this.defaultPage = defaultPage;
    }

}