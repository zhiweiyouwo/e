package com.pm.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.common.util.DateUtil;
import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.query.Direction;
import com.loy.e.core.util.TableToExcelUtil;
import com.loy.e.data.permission.annotation.DataPermission;
import com.pm.domain.ProjectQueryParam;
import com.pm.domain.entity.ProjectEntity;
import com.pm.repository.ProjectRepository;
/**
 * 
 * @author Loy Fu qq群 540553957 website = http://www.17jee.com
 */
@RestController
@RequestMapping(value={"**/project"}, method={RequestMethod.POST, RequestMethod.GET})
@Transactional
public class ProjectServiceImpl{

    @Autowired
    ProjectRepository projectRepository;
    @RequestMapping({ "/page" })
    @ControllerLogExeTime(description = "分页查询项目", log = false)
    @DataPermission(uniqueKey="ProjectServiceImpl.queryPage",findAll=true)
    public Page<ProjectEntity> queryPage(ProjectQueryParam projectQueryParam, Pageable pageable) {
        if (projectQueryParam != null) {
            processSort(projectQueryParam);
            Date registerDateEnd = projectQueryParam.getRegisterDateEnd();
            if (registerDateEnd != null) {
                registerDateEnd = DateUtil.addOneDay(registerDateEnd);
                projectQueryParam.setRegisterDateEnd(registerDateEnd);
            }
        }
        Page<ProjectEntity> page = this.projectRepository.findProjectPage((projectQueryParam), pageable);
        return page;
    }
  
  
	@ControllerLogExeTime(description="获取项目", log=false)
	@RequestMapping({"/get"})
	public ProjectEntity get(String id) {
		ProjectEntity projectEntity = (ProjectEntity)this.projectRepository.get(id);
		return projectEntity;
	}
	@ControllerLogExeTime(description="查看项目", log=false)
	@RequestMapping({"/detail"})
	public ProjectEntity detail(String id) {
		ProjectEntity projectEntity = (ProjectEntity)this.projectRepository.get(id);
		return projectEntity;
	}
	@ControllerLogExeTime(description="删除项目")
	@RequestMapping({"/del"})
	public void del(String id) {
		if (StringUtils.isNotEmpty(id)) {
			String[] idsArr = id.split(",");
			if (idsArr != null) {
				for (String idd : idsArr) {
					ProjectEntity projectEntity = (ProjectEntity)this.projectRepository.get(idd);
					if (projectEntity != null) {
						this.projectRepository.delete(projectEntity);
					}
				}
			}
		}
	}

	@RequestMapping({"/save"})
	@ControllerLogExeTime(description="保存项目")
	public ProjectEntity save(ProjectEntity projectEntity) {
	projectEntity.setId(null);
		this.projectRepository.save(projectEntity);
		return projectEntity;
	}
	@RequestMapping({"/update"})
	@ControllerLogExeTime(description="修改项目")
	public void update(ProjectEntity projectEntity) {
		this.projectRepository.save(projectEntity);
	}
  
  
	@RequestMapping(value={"/excel"}, method={RequestMethod.POST})
	@ControllerLogExeTime(description="导出项目", log=false)
	public void excel(String html, HttpServletResponse response) throws IOException {
		response.setContentType("application/msexcel;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=projects.xls");
		OutputStream out = response.getOutputStream();
		TableToExcelUtil.createExcelFormTable("project", html, 1, out);
		out.flush();
		out.close();
	}
	private void processSort(ProjectQueryParam projectQueryParam){
		String orderProperity = projectQueryParam.getOrderProperty();
		if (StringUtils.isNotEmpty(orderProperity)) {
			String[] orderProperties = {"registerDate"};
			if (ArrayUtils.contains(orderProperties, orderProperity)) {
				String direction = projectQueryParam.getDirection();
				if ((!Direction.ASC.getInfo().equalsIgnoreCase(direction)) && 
					(!Direction.DESC.getInfo().equalsIgnoreCase(direction))) {
					  projectQueryParam.setDirection(Direction.DESC.getInfo());
				}
			}
		}else {
			projectQueryParam.setOrderProperty("");
			projectQueryParam.setDirection("");
		}
	}
}