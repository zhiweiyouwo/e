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
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.common.tree.TreeNode;
import com.loy.e.common.vo.SuccessResponse;
import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.query.MapQueryParam;
import com.loy.e.core.util.TableToExcelUtil;
import com.loy.upm.sys.domain.RoleQueryParam;
import com.loy.upm.sys.domain.entity.MenuI18nEntity;
import com.loy.upm.sys.domain.entity.ResourceEntity;
import com.loy.upm.sys.domain.entity.RoleEntity;
import com.loy.upm.sys.domain.entity.SystemEntity;
import com.loy.upm.sys.repository.MenuI18nRepository;
import com.loy.upm.sys.repository.ResourceRepository;
import com.loy.upm.sys.repository.RoleRepository;

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
@RequestMapping(value = "**/role",method={RequestMethod.POST,RequestMethod.GET})
@Transactional
@SuppressWarnings("rawtypes")

@Api(value="角色管理",description="角色管理")
public class RoleServiceImpl {

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ResourceRepository resourceRepository;
	
	@Autowired
	MessageSource messageSource;
	@Autowired
	MenuI18nRepository menuI18nRepository;
	@ControllerLogExeTime(description="获取角色树",log=false)
	@RequestMapping("/all")
	
	
	@ApiOperation(value="获取角色",notes="获取角色",httpMethod="GET")
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
	
	@ApiOperation(value="获取角色权限",notes="获取角色权限树",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="roleId" ,value="员工编号" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="lang" ,value="国家语言 :zh-CN" ,paramType="form",dataType="string"),
	})
	public List<TreeNode>  resource(String roleId,String lang){
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
					MenuI18nEntity menuI18nEntity = menuI18nRepository.findByKeyAndLang(lableKey,lang);
					if(menuI18nEntity == null && !lang.equals("")){
						menuI18nEntity = menuI18nRepository.findByKeyAndLang(lableKey,"");
					}
					if(menuI18nEntity != null) {
						String value = menuI18nEntity.getValue();
						if(StringUtils.isNotEmpty(value)){
							name = value;
						}
					}
				}
				treeNode.setText(name);
				treeNode.setParentId(resource.getParentId());
				if(temp.contains(resource.getId())){
					treeNode.setSelected(true);
				}
				treeList.add(treeNode);
			}
			
			Set<String> set = new HashSet<String>();
			List<TreeNode> roots = new ArrayList<TreeNode>();
			for(TreeNode treeNode : treeList) {
			    String parentId = treeNode.getParentId();
			    if(StringUtils.isEmpty(parentId)){
			    	String id = treeNode.getId();
			    	ResourceEntity resource = resourceRepository.get(id);
					SystemEntity system = resource.getSystem();
					String systemId = system.getId();
					if(set.contains(systemId)){
						treeNode.setParentId(systemId);
					}else{
						TreeNode tree = new TreeNode();
				    	String name = system.getName();
				    	String lableKey = system.getLableKey();
				    	if(StringUtils.isNotEmpty(lableKey)){
							MenuI18nEntity menuI18nEntity = menuI18nRepository.findByKeyAndLang(lableKey,lang);
							if(menuI18nEntity == null && !lang.equals("")){
								menuI18nEntity = menuI18nRepository.findByKeyAndLang(lableKey,"");
							}
							if(menuI18nEntity != null) {
								String value = menuI18nEntity.getValue();
								if(StringUtils.isNotEmpty(value)){
									name = value;
								}
							}
						}
				    	
						tree.setText(name);
						
						tree.setId(system.getId());
						treeNode.setParentId(systemId);
						set.add(systemId);
						roots.add(tree);
					}	
			    }
			}
			treeList.addAll(roots);
		}
		return treeList;
	}
	
	@ControllerLogExeTime(description="分页查询角色",log=false)
	@RequestMapping(value="/page")
	

	@ApiOperation(value="角色查询",notes="角色查询",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="name" ,value="角色名称" ,paramType="form",dataType="string"),
		
		@ApiImplicitParam(name="pageNumber" ,value="页号" ,paramType="form",dataType="int"),
		@ApiImplicitParam(name="pageSize" ,value="页的大小" ,paramType="form",dataType="int")
	})
	public Page<RoleEntity>  queryPage(@ApiIgnore RoleQueryParam roleQueryParam,@ApiIgnore Pageable pageable){
		Page<RoleEntity> page = roleRepository.findPage( new MapQueryParam(roleQueryParam), pageable);
		return page;
	}
	
	@ControllerLogExeTime(description="保存角色")
	@RequestMapping(value="/save",method={RequestMethod.POST})
	
	@ApiOperation(value="保存角色",notes="保存角色",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="name" ,value="名称" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="description" ,value="描述" ,paramType="form",dataType="string"),
	})
	public SuccessResponse  save(@ApiIgnore RoleEntity roleEntity){
		roleRepository.save(roleEntity);
		return SuccessResponse.newInstance();
	}
	
	@ControllerLogExeTime(description="修改角色")
	@RequestMapping(value="/update",method={RequestMethod.POST,RequestMethod.PUT})
	
	@ApiOperation(value="修改角色",notes="修改角色",httpMethod="PUT")
	@ApiImplicitParams({
		@ApiImplicitParam(name="id" ,value="ID" ,paramType="form",dataType="string",required=true),
		@ApiImplicitParam(name="name" ,value="名称" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="description" ,value="描述" ,paramType="form",dataType="string"),
	})
	public void  update(@ApiIgnore RoleEntity roleEntity){
		String id = roleEntity.getId();
		RoleEntity role = roleRepository.get(id);
		role.setName(roleEntity.getName());
		role.setDescription(roleEntity.getDescription());
		
		roleRepository.save(role);
		
	}
	
	@ControllerLogExeTime(description="删除角色")
	@RequestMapping(value="/del",method={RequestMethod.POST,RequestMethod.DELETE})
	
	@ApiOperation(value="删除角色",notes="删除角色  多个角色ID用,号分隔",httpMethod="DELETE")
	@ApiImplicitParam(name="id" ,value="员工IDS" ,paramType="form",required=true,dataType="string")
	public void  del(String id){
		
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
	}
	
	@ControllerLogExeTime(description="角色授权")
	@RequestMapping(value="/authority",method={RequestMethod.POST})
	
	@ApiOperation(value="角色授权",notes="角色授权",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="roleId" ,value="角色ID" ,paramType="form",dataType="string"),
		@ApiImplicitParam(name="resourceIds" ,value="资源IDS 多个资源ID用,号分隔" ,paramType="form",dataType="string"),
	})
	
	public void  authority(String roleId,String resourceIds){
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
	}
	
	@RequestMapping(value="/excel",method={RequestMethod.POST})
	@ControllerLogExeTime(description="导出角色",log=false)
	@ApiIgnore
    public void  excel(String html ,HttpServletResponse response) throws IOException{
		response.setContentType("application/msexcel;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=roles.xls");
		OutputStream out = response.getOutputStream();
		TableToExcelUtil.createExcelFormTable("role", html, 1, out);
		out.flush();
		out.close();
	}
}