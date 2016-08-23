package com.loy.e.core.web.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.common.tree.TreeNode;
import com.loy.e.common.vo.IndexData;
import com.loy.e.common.vo.LocaleVO;
import com.loy.e.common.vo.SessionUser;
import com.loy.e.common.vo.System;
import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.conf.Settings;
import com.loy.e.security.service.SecurityUserService;
import com.loy.e.security.service.SystemKeyService;
import com.loy.e.security.service.SystemService;
import com.loy.e.security.service.UserSessionService;
import com.loy.e.security.vo.Permission;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping(value="/",method={RequestMethod.POST,RequestMethod.GET})
@SuppressWarnings("rawtypes")


@Api(value="主页相关数据",description="主页相关数据")
public class IndexController { 
	
	@Autowired
	SecurityUserService securityUserService;
	@Autowired
	UserSessionService userSessionService;
	
    @Autowired
    SystemKeyService systemKeyService;
    @Autowired
    SystemService systemService;
	@Autowired 
	Settings settings;
	
	@ApiIgnore
	@RequestMapping(value="/lang") 
	public void lang(String lang) { 
		SessionUser user = userSessionService.getSessionUser();
		String[] temp = lang.split("_");
		Locale locale = new Locale(temp[0], temp[1]);
		user.setLocale(locale);
		userSessionService.setSessionUser(user);
	}
	
	
	
	@RequestMapping("/indexData")
	@ControllerLogExeTime(description="主页数据",log=false)
	
	@ApiOperation(value="获取主面数据",notes="获取主面数据",httpMethod="GET")
	public IndexData getIndexData(){
		IndexData indexData = new IndexData();	
		String defaultPage = settings.getDefaultPage();
		if(StringUtils.isNotEmpty(defaultPage)){
			indexData.setDefaultPage(defaultPage);
		}
		
		SessionUser simipleUser = userSessionService.getSessionUser();
	
		indexData.setPhoto(simipleUser.getPhoto());
		String country = LocaleContextHolder.getLocale().getCountry();
		String language = LocaleContextHolder.getLocale().getLanguage();
		String lang = language+"_"+country;
		simipleUser.setLang(lang);
		indexData.setSimipleUser(simipleUser);
		//indexData.setPhoto(user.getPhoto());
		

		List<String> sls= settings.getSupportLocales();
		List<LocaleVO>  supportLocalesList = indexData.getSupportLocales();
		for(String lan:sls){
			String[] temp = lan.split("_");
			Locale locale = new Locale(temp[0], temp[1]);
			String displayName = locale.getDisplayLanguage(LocaleContextHolder.getLocale());
			LocaleVO localeVO = new LocaleVO();
			localeVO.setCountry(temp[1]);
			localeVO.setLanguage(temp[0]);
			localeVO.setDisplayName(displayName);
			supportLocalesList.add(localeVO);
		}
		
		List<TreeNode> menuData   = securityUserService.getMenuByUsername(userSessionService.getSessionUser().getId(),systemKeyService.getSystemCode(),lang);
		Map<String,Boolean> access = indexData.getAccessCodes();
		List<Permission> resources = securityUserService.findPermissionsByUsername(userSessionService.getSessionUser().getUsername(),systemKeyService.getSystemCode());
		List<Permission> all = securityUserService.getAllPermissions();
		if(all != null){
			for(Permission r : all){
				String accessCode = r.getAccessCode();
				if(StringUtils.isNotEmpty(accessCode)){
					access.put(accessCode, false);
				}
			}
		}
		
		if(resources != null){
			for(Permission r : resources){
				String accessCode = r.getAccessCode();
				if(StringUtils.isNotEmpty(accessCode)){
					access.put(accessCode,true);
				}
			}
		}
		List<System> systems = systemService.getMySystem(simipleUser.getUsername());
		//去掉自身系统
		if(systems != null){
			Iterator<System> it = systems.iterator();
			String sysKey = systemKeyService.getSystemCode();
			while(it.hasNext()){
				System system = it.next();
				if(sysKey.equals(system.getCode())){
					it.remove();
					break;
				}
			}
		}
		indexData.setMySystems(systems);
		indexData.setMenuData(menuData);
		return indexData;
	}
}
