package com.loy.upm.sys.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

import com.loy.e.common.util.Assert;
import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.query.MapQueryParam;
import com.loy.e.core.util.TableToExcelUtil;
import com.loy.e.security.service.SecurityUserService;
import com.loy.e.security.service.SystemKeyService;
import com.loy.e.security.service.UserSessionService;
import com.loy.upm.sys.domain.UserQueryParam;
import com.loy.upm.sys.domain.entity.RoleEntity;
import com.loy.upm.sys.domain.entity.UserEntity;
import com.loy.upm.sys.repository.RoleRepository;
import com.loy.upm.sys.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@RestController
@RequestMapping(value = "**/user",method={RequestMethod.POST,RequestMethod.GET})
@Transactional

@Api(value="用户管理",description="用户管理")
public class UserServiceImpl{
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserSessionService userSessionService;
	@Autowired
	SecurityUserService securityUserService;
	@Autowired
	SystemKeyService systemKeyService;
	

	@RequestMapping(value="/page")
	@ControllerLogExeTime(description="查询用户",log=false)
	
	@ApiOperation(value="查询用户",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="username" ,value="用户名" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="name" ,value="姓名" ,paramType="form",dataType="string"),

		@ApiImplicitParam(name="pageNumber" ,value="页号" ,paramType="form",dataType="int"),
		@ApiImplicitParam(name="pageSize" ,value="页的大小" ,paramType="form",dataType="int")
	})
	
	public Page<UserEntity>  queryPage(@ApiIgnore UserQueryParam userQueryParam,@ApiIgnore Pageable pageable){
		
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
	
	@RequestMapping(value="/update",method={RequestMethod.POST,RequestMethod.PUT})
	@ControllerLogExeTime(description="修改用户")
	
	
	@ApiOperation(value="修改用户信息",notes="修改用户信息",httpMethod="PUT")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id" ,value="ID" ,paramType="form",required=true,dataType="string"),
		@ApiImplicitParam(name="name" ,value="姓名" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="email" ,value="邮箱" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="phone" ,value="电话" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="roleIds" ,value="角色IDS 多个ID用,号分隔" ,paramType="form",dataType="string"),
		
		
	})
	public void  update(@ApiIgnore UserEntity userEntity){
		
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
	
	}
	
	@RequestMapping(value="/del",method={RequestMethod.POST,RequestMethod.DELETE})
	@ControllerLogExeTime(description="删除用户")
	
	@ApiOperation(value="删除用户",notes="删除用户  多个用户ID用,号分隔",httpMethod="DELETE")
	@ApiImplicitParam(name="id" ,value="员工IDS" ,paramType="form",required=true,dataType="string")
	public void  del(String id){
		if(StringUtils.isNotEmpty(id)){
			String[] idsArr = id.split(",");
			List<String> list = new ArrayList<String>();
			if(idsArr != null){
				for(String idd : idsArr){
					if(!idd.equals("ADMIN")){
						list.add(idd);
					}
				}
				userRepository.delete(list);
			}
		}
	}
	
	@RequestMapping(value="/excel",method={RequestMethod.POST})
	@ControllerLogExeTime(description="导出用户",log=false)
	@ApiIgnore
    public void  excel(String html ,HttpServletResponse response) throws IOException{
		response.setContentType("application/msexcel;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=users.xls");
		OutputStream out = response.getOutputStream();
		TableToExcelUtil.createExcelFormTable("user", html, 1, out);
		out.flush();
		out.close();
	}
	
}