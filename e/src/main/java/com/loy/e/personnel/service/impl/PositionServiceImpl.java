package com.loy.e.personnel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.data.SuccessResponse;
import com.loy.e.core.data.TreeNode;
import com.loy.e.core.util.TreeUtil;
import com.loy.e.personnel.domain.entity.EmployeeEntity;
import com.loy.e.personnel.domain.entity.OrgEntity;
import com.loy.e.personnel.domain.entity.PositionEntity;
import com.loy.e.personnel.repository.EmployeeRepository;
import com.loy.e.personnel.repository.PositionRepository;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping(value = "position",method={RequestMethod.POST,RequestMethod.GET})
@Transactional
public class PositionServiceImpl {

	@Autowired
	PositionRepository positionRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	
	@ControllerLogExeTime(description="获取岗位树",log=false)
	@RequestMapping("/all")
	public List<TreeNode>  all(){
		List<TreeNode> treeList= null;
		List<PositionEntity> list = positionRepository.findAll();
		treeList = TreeUtil.build(list);
		return treeList;
	}
	
	@ControllerLogExeTime(description="获取岗位信息",log=false)
	@RequestMapping(value="/get")
	public PositionEntity  get(String id){
		PositionEntity orgEntity = positionRepository.get(id);
		if(orgEntity != null){
			String parentId = orgEntity.getParentId();
			if(StringUtils.isNotEmpty(parentId)){
				PositionEntity parentEntity = positionRepository.get(parentId);
				orgEntity.setParentName(parentEntity.getName());
			}
		}
		return orgEntity;
	}
	
	@ControllerLogExeTime(description="删除岗位信息")
	@RequestMapping(value="/del")
	public SuccessResponse  del(String id){
		if(StringUtils.isNotEmpty(id)){
			PositionEntity positionEntity = positionRepository.get(id);
			String code = positionEntity.getCode();
			//设置所有用户引用些org为空
			List<EmployeeEntity> list = employeeRepository.findByOrgCode(code);
			if(list != null){
				for(EmployeeEntity e : list){
					e.setPosition(null);
					employeeRepository.save(e);
				}
			}
			//删除其子
			List<PositionEntity> olist = positionRepository.findByLikeCode(code);
			if(olist != null){
				for(PositionEntity o : olist){
					positionRepository.delete(o);
				}
			}
		}
		return SuccessResponse.newInstance();
	}
	
	@ControllerLogExeTime(description="保存岗位信息")
	@RequestMapping(value="/save")
	public PositionEntity  save(PositionEntity positionEntity){
		String parentId = positionEntity.getParentId();
		if(StringUtils.isNotEmpty(parentId)){
			PositionEntity parentEntity = positionRepository.get(parentId);
			if(parentEntity == null){
				positionEntity.setParentId(null);
				positionEntity.setCode("000");
			}else{
				String pcode = parentEntity.getCode();
				String ccode = positionRepository.maxCodeByParentId(parentId);
				if(StringUtils.isEmpty(ccode)){
					ccode = pcode+"000";
				}else{
					//最ccode 后三位
					int len = ccode.length();
					String last = ccode.substring(len-3, len);
					int num = Integer.valueOf(last)+1;
					ccode = ccode.substring(0, len-3)+String.format("%03d", num);	
				}
				positionEntity.setCode(ccode);
			}
		}else{
			positionEntity.setParentId(null);
			positionEntity.setCode("000");
		}
		positionRepository.save(positionEntity);
		return positionEntity;
	}
	
	@ControllerLogExeTime(description="修改岗位信息")
	@RequestMapping(value="/update")
	public SuccessResponse  update(PositionEntity positionEntity){
		String id = positionEntity.getId();
		PositionEntity org = positionRepository.get(id);
		org.setName(positionEntity.getName());
		org.setSortNum(positionEntity.getSortNum());
		org.setDescription(positionEntity.getDescription());
		positionRepository.save(org);
		return SuccessResponse.newInstance();
	}
}