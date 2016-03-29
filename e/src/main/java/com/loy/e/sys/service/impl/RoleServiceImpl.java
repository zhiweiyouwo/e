package com.loy.e.sys.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.data.SuccessResponse;
import com.loy.e.core.data.TreeNode;
import com.loy.e.core.query.MapQueryParam;
import com.loy.e.core.util.TableToExcelUtil;
import com.loy.e.core.util.UserUtils;
import com.loy.e.sys.domain.RoleQueryParam;
import com.loy.e.sys.domain.entity.ResourceEntity;
import com.loy.e.sys.domain.entity.RoleEntity;
import com.loy.e.sys.repository.ResourceRepository;
import com.loy.e.sys.repository.RoleRepository;


/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@RestController
@RequestMapping(value = "/role",method={RequestMethod.POST,RequestMethod.GET})
@Transactional
public class RoleServiceImpl {

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ResourceRepository resourceRepository;
	
	@Autowired
	MessageSource messageSource;
	@ControllerLogExeTime(description="获取角色树",log=false)
	@RequestMapping("/all")
	public List<TreeNode>  all(){
		 List<TreeNode> treeList= null;
		List<RoleEntity> list = roleRepository.findAll();
		if(list != null){
			treeList = new ArrayList<TreeNode>();
			for(RoleEntity role : list){
				TreeNode treeNode = new TreeNode();
				treeNode.setId(role.getId());
				treeNode.setText(role.getName());
				treeNode.setType("item");
				treeList.add(treeNode);
			}
		}
		return treeList;
	}
	@ControllerLogExeTime(description="角色权限",log=false)
	@RequestMapping("/resource")
	public List<TreeNode>  resource(String roleId){
		List<TreeNode> treeList= null;
		List<ResourceEntity> roleResourceList =  resourceRepository.findResourceByRoleId(roleId);
		Set<String> temp = new HashSet<String>();
		if(roleResourceList != null){
			for(ResourceEntity r: roleResourceList){
				temp.add(r.getId());
			}
		}
		List<ResourceEntity> list = resourceRepository.findAll();
		if(list != null){
			treeList = new ArrayList<TreeNode>();
			for(ResourceEntity resource : list){
				TreeNode treeNode = new TreeNode();
				treeNode.setId(resource.getId());
				String name = resource.getName();
				String lableKey = resource.getLableKey();
				if(StringUtils.isNotEmpty(lableKey)){
					String value = messageSource.getMessage(lableKey, null, UserUtils.getLocale());
					if(StringUtils.isNotEmpty(value)){
						name = value;
					}
				}
				treeNode.setText(name);
				treeNode.setParentId(resource.getParentId());
				if(temp.contains(resource.getId())){
					treeNode.setSelected(true);
				}
				treeList.add(treeNode);
			}
		}
		return treeList;
	}
	
	@ControllerLogExeTime(description="分页查询角色",log=false)
	@RequestMapping(value="/page")
	public Page<RoleEntity>  queryPage(RoleQueryParam roleQueryParam,Pageable pageable){
		Page<RoleEntity> page = roleRepository.findPage( new MapQueryParam(roleQueryParam), pageable);
		return page;
	}
	
	@ControllerLogExeTime(description="保存角色")
	@RequestMapping(value="/save",method={RequestMethod.POST})
	public SuccessResponse  save(RoleEntity roleEntity){
		roleRepository.save(roleEntity);
		return SuccessResponse.newInstance();
	}
	
	@ControllerLogExeTime(description="修改角色")
	@RequestMapping(value="/update",method={RequestMethod.POST})
	public SuccessResponse  update(RoleEntity roleEntity){
		String id = roleEntity.getId();
		RoleEntity role = roleRepository.get(id);
		
		role.setName(roleEntity.getName());
		role.setDescription(roleEntity.getDescription());
		
		roleRepository.save(role);
		return SuccessResponse.newInstance();
	}
	
	@ControllerLogExeTime(description="删除角色")
	@RequestMapping(value="/del",method={RequestMethod.POST})
	public SuccessResponse  del(String id){
		
		if(StringUtils.isNotEmpty(id)){
			String[] idsArr = id.split(",");
			List<String> list = new ArrayList<String>();
		
			if(idsArr != null){
				for(String idd : idsArr){
					list.add(idd);
				}
				roleRepository.delete(list);
			}
		}
		return SuccessResponse.newInstance();
	}
	
	@ControllerLogExeTime(description="角色授权")
	@RequestMapping(value="/authority",method={RequestMethod.POST})
	public SuccessResponse  authority(String roleId,String resourceIds){
		RoleEntity roleEntity = roleRepository.get(roleId);
		Set<ResourceEntity> resources = new HashSet<ResourceEntity>();
		
		if(StringUtils.isNotEmpty(resourceIds)){
			String[] resIds = resourceIds.split(",");
			for(String resourceId : resIds){
				ResourceEntity resourceEntity = this.resourceRepository.get(resourceId);
				if(resourceEntity != null){
					resources.add(resourceEntity);
				}
			}
		}
		roleEntity.setResources(resources);
		roleRepository.save(roleEntity);
		return SuccessResponse.newInstance();
	}
	
	@RequestMapping(value="/excel",method={RequestMethod.POST})
	@ControllerLogExeTime(description="导出角色",log=false)
    public void  excel(String html ,HttpServletResponse response) throws IOException{
		response.setContentType("application/msexcel;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=roles.xls");
		OutputStream out = response.getOutputStream();
		TableToExcelUtil.createExcelFormTable("role", html, 1, out);
		out.flush();
		out.close();
	}
}