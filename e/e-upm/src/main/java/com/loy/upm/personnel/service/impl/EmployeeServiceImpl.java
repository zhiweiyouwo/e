package com.loy.upm.personnel.service.impl;

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

import com.loy.app.common.vo.SexEnum;
import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.query.MapQueryParam;
import com.loy.e.core.util.TableToExcelUtil;
import com.loy.upm.personnel.domain.EmployeeQueryParam;
import com.loy.upm.personnel.domain.entity.EmployeeEntity;
import com.loy.upm.personnel.domain.entity.OrgEntity;
import com.loy.upm.personnel.domain.entity.PositionEntity;
import com.loy.upm.personnel.repository.EmployeeRepository;
import com.loy.upm.sys.domain.entity.UserEntity;
import com.loy.upm.sys.service.SequenceService;

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
@RequestMapping(value = "**/employee",method={RequestMethod.POST,RequestMethod.GET})
@Transactional

@Api(value="员工信息服务",description="员工信息服务")
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
	
	@ApiOperation(value="查询员工",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="employeeNo" ,value="员工编号" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="name" ,value="姓名" ,paramType="form",dataType="string"),

		@ApiImplicitParam(name="pageNumber" ,value="页号" ,paramType="form",dataType="int"),
		@ApiImplicitParam(name="pageSize" ,value="页的大小" ,paramType="form",dataType="int")
	})
	public Page<EmployeeEntity>  queryPage(
			@ApiIgnore EmployeeQueryParam employeeQueryParam,
			@ApiIgnore Pageable pageable){
		
		Page<EmployeeEntity> page = employeeRepository.findPage( new MapQueryParam(employeeQueryParam), pageable);
		return page;
	}
	
	@RequestMapping(value="/get")
	@ControllerLogExeTime(description="获取员工",log = false)
	
	@ApiOperation(value="获取员工信息",httpMethod="GET")
	@ApiImplicitParams({
	   @ApiImplicitParam(name="id" ,value="员工ID" ,paramType="query",dataType="string")
	})
	public UserEntity  get(String id){
		EmployeeEntity employeeEntity = employeeRepository.get(id);
		return employeeEntity;
	}
	@RequestMapping(value="/save",method={RequestMethod.POST})
	@ControllerLogExeTime(description="新增员工")
	
	@ApiOperation(value="新增员工",notes="新增员工",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="name" ,value="姓名" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="email" ,value="邮箱" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="phone" ,value="电话" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="sex" ,value="性别" ,paramType="form",dataType="string",allowableValues="MALE,FEMALE"),
		@ApiImplicitParam(name="dob" ,value="生日" ,paramType="form",dataType="date"),
		
		@ApiImplicitParam(name="organization.id" ,value="组织机构ID" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="position.id" ,value="职位ID" ,paramType="form",dataType="string"),
	})
	public void  save( @ApiIgnore EmployeeEntity employeeEntity){
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

	}
	
	@RequestMapping(value="/update",method={RequestMethod.POST,RequestMethod.PUT})
	@ControllerLogExeTime(description="修改员工信息")
	
	@ApiOperation(value="修改员工信息",notes="修改员工信息",httpMethod="PUT")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id" ,value="ID" ,paramType="form",required=true,dataType="string"),
		@ApiImplicitParam(name="name" ,value="姓名" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="email" ,value="邮箱" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="phone" ,value="电话" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="sex" ,value="性别" ,paramType="form",dataType="string",allowableValues="MALE,FEMALE"),
		@ApiImplicitParam(name="dob" ,value="生日" ,paramType="form",dataType="date"),
		
		@ApiImplicitParam(name="organization.id" ,value="组织机构ID" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="position.id" ,value="职位ID" ,paramType="form",dataType="string"),
	})
	
	public void  update(@ApiIgnore EmployeeEntity employeeEntity){
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
	}
	@RequestMapping(value="/del",method={RequestMethod.POST,RequestMethod.DELETE})
	@ControllerLogExeTime(description="删除员工")
	
	@ApiOperation(value="删除员工",notes="删除员工  多个员工ID用,号分隔",httpMethod="DELETE")
	@ApiImplicitParams({
	   @ApiImplicitParam(name="id" ,value="员工IDS" ,paramType="form",required=true,dataType="string")
	})
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
				employeeRepository.delete(list);
			}
		}
	}
	
	@RequestMapping(value="/excel",method={RequestMethod.POST})
	@ControllerLogExeTime(description="导出员工",log=false)
	@ApiIgnore
    public void  excel(String html ,HttpServletResponse response) throws IOException{
		response.setContentType("application/msexcel;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=employees.xls");
		OutputStream out = response.getOutputStream();
		TableToExcelUtil.createExcelFormTable("employee", html, 1, out);
		out.flush();
		out.close();
		
	}
}