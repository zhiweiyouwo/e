package com.loy.e.sys.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.conf.Settings;
import com.loy.e.core.data.SuccessResponse;
import com.loy.e.core.data.TreeNode;
import com.loy.e.core.query.MapQueryParam;
import com.loy.e.core.util.Assert;
import com.loy.e.core.util.TableToExcelUtil;
import com.loy.e.core.util.TreeUtil;
import com.loy.e.core.util.UserUtils;
import com.loy.e.core.web.LocaleVO;
import com.loy.e.core.web.SimpleUser;
import com.loy.e.personnel.repository.NoticeRepository;
import com.loy.e.sys.domain.IndexData;
import com.loy.e.sys.domain.UserQueryParam;
import com.loy.e.sys.domain.entity.ResourceEntity;
import com.loy.e.sys.domain.entity.RoleEntity;
import com.loy.e.sys.domain.entity.UserEntity;
import com.loy.e.sys.repository.ResourceRepository;
import com.loy.e.sys.repository.RoleRepository;
import com.loy.e.sys.repository.UserRepository;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@RestController
@RequestMapping(value = "/user",method={RequestMethod.POST,RequestMethod.GET})
@Transactional
public class UserServiceImpl{
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	ResourceRepository resourceRepository; 
	
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	NoticeRepository noticeRepository;
	@Autowired 
	Settings settings;
	@RequestMapping("/indexData")
	@ControllerLogExeTime(description="主页数据",log=false)
	public IndexData getIndexData(){
		IndexData indexData = new IndexData();	
		String username = UserUtils.getUsername();
		UserEntity user = userRepository.findByUsername(username);
		SimpleUser simipleUser = new SimpleUser();
		simipleUser.setUsername(user.getUsername());
		simipleUser.setName(user.getName());
		simipleUser.setId(user.getId());
		String country = UserUtils.getLocale().getCountry();
		String language = UserUtils.getLocale().getLanguage();
		String lang = language+"_"+country;
		simipleUser.setLang(lang);
		indexData.setSimipleUser(simipleUser);
		indexData.setPhoto(user.getPhoto());
		
		String supportLocales = settings.getSupportLocales();
		String[] sls= supportLocales.split(",");
		List<LocaleVO>  supportLocalesList = indexData.getSupportLocales();
		for(String lan:sls){
			String[] temp = lan.split("_");
			Locale locale = new Locale(temp[0], temp[1]);
			String displayName = locale.getDisplayLanguage(UserUtils.getLocale());
			LocaleVO localeVO = new LocaleVO();
			localeVO.setCountry(temp[1]);
			localeVO.setLanguage(temp[0]);
			localeVO.setDisplayName(displayName);
			supportLocalesList.add(localeVO);
		}
		List<ResourceEntity> list = resourceRepository.findMenuByUserId(user.getId());
		List<TreeNode> menuData   = TreeUtil.build(list);
		Map<String,Boolean> access = indexData.getAccessCodes();
		List<ResourceEntity> resources = resourceRepository.findResourceByUsername(username);
		List<ResourceEntity> all = resourceRepository.findAll();
		if(all != null){
			for(ResourceEntity r : all){
				String accessCode = r.getAccessCode();
				if(StringUtils.isNotEmpty(accessCode)){
					access.put(accessCode, false);
				}
			}
		}
		
		if(resources != null){
			for(ResourceEntity r : resources){
				String accessCode = r.getAccessCode();
				if(StringUtils.isNotEmpty(accessCode)){
					access.put(accessCode,true);
				}
			}
		}
		indexData.setMenuData(menuData);
		
		long notReadNotice = noticeRepository.findNotReadNoticeCount(user.getId());
		indexData.setNotReadNotice(notReadNotice);
		return indexData;
	}

	@RequestMapping(value="/page")
	@ControllerLogExeTime(description="查询用户",log=false)
	public Page<UserEntity>  queryPage(UserQueryParam userQueryParam,Pageable pageable){
		
		Page<UserEntity> page = userRepository.findPage(new MapQueryParam(userQueryParam), pageable);
		List<UserEntity> list = page.getContent();
		if(list != null){
			for(UserEntity ue : list){
				ue.buildRoleIdAnadName();
			}
		}
		return page;
	}
	
//	@RequestMapping(value="/save",method={RequestMethod.POST})
//	@ControllerLogExeTime(description="新增用户")
//	public SuccessResponse  save(UserEntity userEntity){
//		
//		UserEntity u = userRepository.findByUsername(userEntity.getUsername());
//		if(u != null){
//			Assert.throwException("sys.user.username_repeat");
//		}
//		userEntity.setPassword("123456");
//		String roleIds = userEntity.getRoleIds();
//		if(StringUtils.isNotEmpty(roleIds)){
//			String[] ids = roleIds.split(",");
//			if(ids != null){
//				Set<RoleEntity> roles = new HashSet<RoleEntity>();
//				userEntity.setRoles(roles);
//				for(String roleId : ids){
//					RoleEntity roleEntity = roleRepository.get(roleId);
//					roles.add(roleEntity);
//				}
//			}
//		}
//		userRepository.save(userEntity);
//		return SuccessResponse.newInstance();
//	}
	
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ControllerLogExeTime(description="修改用户")
	public SuccessResponse  update(UserEntity userEntity){
		
		UserEntity u = userRepository.findByUsername(userEntity.getUsername());
		
		if(u != null){
			if(!u.getId().equals(userEntity.getId())){
				Assert.throwException("sys.user.username_repeat");
			}
		}
		UserEntity oldUserEntity = userRepository.get(userEntity.getId());

		String name = userEntity.getName();
		String email = userEntity.getEmail();
		String phone = userEntity.getPhone();
		oldUserEntity.setName(name);
		oldUserEntity.setEmail(email);
		oldUserEntity.setPhone(phone);
		Set<RoleEntity> roles = new HashSet<RoleEntity>();
		oldUserEntity.setRoles(roles);
		String roleIds = userEntity.getRoleIds();
		if(StringUtils.isNotEmpty(roleIds)){
			String[] ids = roleIds.split(",");
			if(ids != null){
				for(String roleId : ids){
					RoleEntity roleEntity = roleRepository.get(roleId);
					roles.add(roleEntity);
				}
			}
		}
		userRepository.save(oldUserEntity);
		//Assert.throwException("user_password_error");
		return SuccessResponse.newInstance();
	}
	
	@RequestMapping(value="/del",method={RequestMethod.POST})
	@ControllerLogExeTime(description="删除用户")
	public SuccessResponse  del(String id){
		if(StringUtils.isNotEmpty(id)){
			String[] idsArr = id.split(",");
			List<String> list = new ArrayList<String>();
		
			if(idsArr != null){
				for(String idd : idsArr){
					list.add(idd);
				}
				userRepository.delete(list);
			}
		}
		return SuccessResponse.newInstance();
	}
	
	@RequestMapping(value="/excel",method={RequestMethod.POST})
	@ControllerLogExeTime(description="导出用户",log=false)
    public void  excel(String html ,HttpServletResponse response) throws IOException{
		response.setContentType("application/msexcel;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=users.xls");
		OutputStream out = response.getOutputStream();
		TableToExcelUtil.createExcelFormTable("user", html, 1, out);
		out.flush();
		out.close();
	}
	
}