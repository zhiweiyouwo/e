package com.loy.e.sys.service.impl;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.data.SuccessResponse;
import com.loy.e.core.util.Assert;
import com.loy.e.core.util.UserUtils;
import com.loy.e.personnel.domain.entity.EmployeeEntity;
import com.loy.e.personnel.repository.EmployeeRepository;
import com.loy.e.sys.domain.entity.UserEntity;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping(value = "/profile",method={RequestMethod.POST,RequestMethod.GET})
@Transactional
public class MyProfileServiceImpl {
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PasswordService passwordService;
	
	@ControllerLogExeTime(description="上传个人图片")
	@RequestMapping(value="/upload",method={RequestMethod.POST})
    public SuccessResponse  upload(@RequestParam MultipartFile avatar) throws IOException{
		byte[] photoData = avatar.getBytes(); 
		EmployeeEntity employeeEntity = employeeRepository.get(UserUtils.getUserId());
		employeeEntity.setPhotoData(photoData);
		employeeRepository.save(employeeEntity);
	    return SuccessResponse.newInstance();
	}
	
	@ControllerLogExeTime(description="获取个人图片",log=false)
	@RequestMapping(value="/photo",method={RequestMethod.GET})
    public void  photo(String id,HttpServletResponse response) throws IOException{
		EmployeeEntity employeeEntity = employeeRepository.get(id);
		OutputStream out = response.getOutputStream();
		if(employeeEntity.getPhotoData() != null){
			out.write(employeeEntity.getPhotoData());
		}
		out.flush();
		out.close();
	}
	
	@ControllerLogExeTime(description="编辑个人资料",log=false)
	@RequestMapping(value="/edit",method={RequestMethod.POST})
	public UserEntity  get(){
		String id = UserUtils.getSimipleUser().getId();
		EmployeeEntity user = employeeRepository.get(id);
		user.buildRoleIdAnadName();
		return user;
	}
	
	@ControllerLogExeTime(description="修改个人资料")
	@RequestMapping(value="/update",method={RequestMethod.POST})
	public EmployeeEntity  update(EmployeeEntity user){
		String id = UserUtils.getSimipleUser().getId();
		EmployeeEntity employeeEntity = employeeRepository.get(id);
		employeeEntity.setName(user.getName());
		employeeEntity.setEmail(user.getEmail());
		employeeEntity.setPhone(user.getPhone());
		employeeRepository.save(employeeEntity);
		return user;
	}
	
	@ControllerLogExeTime(description="修改个人密码")
	@RequestMapping(value="/password",method={RequestMethod.POST})
	public SuccessResponse  updatePassword(String oldPassword,String newPassword){
		String id = UserUtils.getSimipleUser().getId();
		UserEntity user = employeeRepository.get(id);
		String password = user.getPassword();
		if(password.equals(oldPassword)){
			String enPassword = passwordService.encryptPassword(newPassword);
			user.setPassword(enPassword);
			user.setPassword(newPassword);
		}else{
			Assert.throwException("sys.user.old_password");
		}
		return SuccessResponse.newInstance();
	}
}
