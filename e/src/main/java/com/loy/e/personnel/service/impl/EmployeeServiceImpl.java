package com.loy.e.personnel.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.annotation.Converter;
import com.loy.e.core.converter.DefaultPageConverter;
import com.loy.e.core.data.SuccessResponse;
import com.loy.e.core.query.MapQueryParam;
import com.loy.e.core.util.TableToExcelUtil;
import com.loy.e.personnel.domain.EmployeeQueryParam;
import com.loy.e.personnel.domain.entity.EmployeeEntity;
import com.loy.e.personnel.domain.entity.OrgEntity;
import com.loy.e.personnel.domain.entity.PositionEntity;
import com.loy.e.personnel.domain.entity.SexEnum;
import com.loy.e.personnel.repository.EmployeeRepository;
import com.loy.e.sys.domain.entity.UserEntity;
import com.loy.e.sys.service.SequenceService;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@RestController
@RequestMapping(value = "/employee",method={RequestMethod.POST,RequestMethod.GET})
@Transactional
public class EmployeeServiceImpl{
	
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	SequenceService sequenceService;
	@Autowired
	PasswordService passwordService;
	
	@RequestMapping(value="/page")
	@ControllerLogExeTime(description="查询员工",log = false)
	//@Converter(value=DefaultPageConverter.class)
	public Page<EmployeeEntity>  queryPage(EmployeeQueryParam employeeQueryParam,Pageable pageable){
		Page<EmployeeEntity> page = employeeRepository.findPage( new MapQueryParam(employeeQueryParam), pageable);
		return page;
	}
	
	@RequestMapping(value="/get")
	@ControllerLogExeTime(description="获取员工",log = false)
	public UserEntity  get(String id){
		EmployeeEntity employeeEntity = employeeRepository.get(id);
		return employeeEntity;
	}
	@RequestMapping(value="/save",method={RequestMethod.POST})
	@ControllerLogExeTime(description="新增员工")
	public SuccessResponse  save(EmployeeEntity employeeEntity){
		String employeeNo = sequenceService.getEmployeeNo();
		employeeEntity.setEmployeeNo(employeeNo);
		employeeEntity.setUsername(employeeNo);
		String enPassword = passwordService.encryptPassword("123456");
		employeeEntity.setPassword(enPassword);
		
		OrgEntity org = employeeEntity.getOrganization();
		if(org != null){
			if(StringUtils.isEmpty(org.getId())){
				org = null;
			}
		}
		PositionEntity pos = employeeEntity.getPosition();
		if(pos != null){
			if(StringUtils.isEmpty(pos.getId())){
				pos = null;
			}
		}
		employeeEntity.setOrganization(org);
		employeeEntity.setPosition(pos);
		
		employeeRepository.save(employeeEntity);
		return SuccessResponse.newInstance();
	}
	
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ControllerLogExeTime(description="修改员工信息")
	public SuccessResponse  update(EmployeeEntity employeeEntity){
		EmployeeEntity oldEmployeeEntity = employeeRepository.get(employeeEntity.getId());
		String name = employeeEntity.getName();
		String email = employeeEntity.getEmail();
		String phone = employeeEntity.getPhone();
		SexEnum sex = employeeEntity.getSex();
		Date dob = employeeEntity.getDob();
		
		oldEmployeeEntity.setName(name);
		oldEmployeeEntity.setEmail(email);
		oldEmployeeEntity.setPhone(phone);
		oldEmployeeEntity.setSex(sex);
		oldEmployeeEntity.setDob(dob);
		OrgEntity org = employeeEntity.getOrganization();
		if(org != null){
			if(StringUtils.isEmpty(org.getId())){
				org = null;
			}
		}
		PositionEntity pos = employeeEntity.getPosition();
		if(pos != null){
			if(StringUtils.isEmpty(pos.getId())){
				pos = null;
			}
		}
		oldEmployeeEntity.setOrganization(org);
		oldEmployeeEntity.setPosition(pos);
		employeeRepository.save(oldEmployeeEntity);
		return SuccessResponse.newInstance();
	}
	@RequestMapping(value="/del",method={RequestMethod.POST})
	@ControllerLogExeTime(description="删除员工")
	public SuccessResponse  del(String id){
		if(StringUtils.isNotEmpty(id)){
			String[] idsArr = id.split(",");
			List<String> list = new ArrayList<String>();
		
			if(idsArr != null){
				for(String idd : idsArr){
					list.add(idd);
				}
				employeeRepository.delete(list);
			}
		}
		return SuccessResponse.newInstance();
	}
	
	@RequestMapping(value="/excel",method={RequestMethod.POST})
	@ControllerLogExeTime(description="导出员工",log=false)
    public void  excel(String html ,HttpServletResponse response) throws IOException{
		response.setContentType("application/msexcel;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=employees.xls");
		OutputStream out = response.getOutputStream();
		TableToExcelUtil.createExcelFormTable("employee", html, 1, out);
		out.flush();
		out.close();
		
	}
}