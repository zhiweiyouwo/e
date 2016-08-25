package com.loy.upm.personnel.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.common.tree.TreeNode;
import com.loy.e.common.tree.TreeUtil;
import com.loy.e.common.vo.SuccessResponse;
import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.upm.personnel.domain.entity.EmployeeEntity;
import com.loy.upm.personnel.domain.entity.OrgEntity;
import com.loy.upm.personnel.repository.EmployeeRepository;
import com.loy.upm.personnel.repository.OrgRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@RequestMapping(value = "**/org", method = { RequestMethod.POST, RequestMethod.GET })
@Transactional
@Api(value = "机构管理", description = "机构管理")
@SuppressWarnings("rawtypes")
public class OrgServiceImpl {

    @Autowired
    OrgRepository orgRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @ControllerLogExeTime(description = "获取机构树", log = false)
    @RequestMapping("/all")
    @ApiOperation(value = "获取机构", notes = "获取机构树", httpMethod = "GET")
    public List<TreeNode> all() {
        List<TreeNode> treeList = null;
        List<OrgEntity> list = orgRepository.findAll();
        treeList = TreeUtil.build(list);
        return treeList;
    }

    @ControllerLogExeTime(description = "获取机构", log = false)
    @RequestMapping(value = "/get")

    @ApiOperation(value = "获取机构信息", notes = "获取机构信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "机构ID", paramType = "form", required = true, dataType = "string"),
    })
    public OrgEntity get(String id) {
        OrgEntity orgEntity = orgRepository.get(id);
        if (orgEntity != null) {
            String parentId = orgEntity.getParentId();
            if (StringUtils.isNotEmpty(parentId)) {
                OrgEntity parentEntity = orgRepository.get(parentId);
                orgEntity.setParentName(parentEntity.getName());
            }
        }
        return orgEntity;
    }

    @ControllerLogExeTime(description = "删除机构")
    @RequestMapping(value = "/del", method = { RequestMethod.POST, RequestMethod.DELETE })

    @ApiOperation(value = "删除机构", notes = "删除机构", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "ID", paramType = "form", required = true, dataType = "string")
    public SuccessResponse del(String id) {
        if (StringUtils.isNotEmpty(id)) {
            OrgEntity orgEntity = orgRepository.get(id);
            String code = orgEntity.getCode();
            //设置所有用户引用些org为空
            List<EmployeeEntity> list = employeeRepository.findByOrgCode(code);
            if (list != null) {
                for (EmployeeEntity e : list) {
                    e.setOrganization(null);
                    employeeRepository.save(e);
                }
            }
            //删除其子

            List<OrgEntity> olist = orgRepository.findByLikeCode(code);
            if (olist != null) {
                for (OrgEntity o : olist) {
                    orgRepository.delete(o);
                }
            }
        }
        return SuccessResponse.newInstance();
    }

    @ControllerLogExeTime(description = "保存机构")
    @RequestMapping(value = "/save")

    @ApiOperation(value = "保存机构", notes = "保存机构", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", paramType = "form", required = true, dataType = "string"),
            @ApiImplicitParam(name = "description", value = "描述", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "parentId", value = "上级ID", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "sortNum", value = "排序数", paramType = "form", dataType = "int"),
    })
    public OrgEntity save(@ApiIgnore OrgEntity orgEntity) {
        String parentId = orgEntity.getParentId();
        if (StringUtils.isNotEmpty(parentId)) {
            OrgEntity parentEntity = orgRepository.get(parentId);
            if (parentEntity == null) {
                orgEntity.setParentId(null);
                orgEntity.setCode("000");
            } else {
                String pcode = parentEntity.getCode();
                String ccode = orgRepository.maxCodeByParentId(parentId);
                if (StringUtils.isEmpty(ccode)) {
                    ccode = pcode + "000";
                } else {
                    //最ccode 后三位
                    int len = ccode.length();
                    String last = ccode.substring(len - 3, len);
                    int num = Integer.valueOf(last) + 1;
                    ccode = ccode.substring(0, len - 3) + String.format("%03d", num);
                }
                orgEntity.setCode(ccode);
            }
        } else {
            orgEntity.setParentId(null);
            orgEntity.setCode("000");
        }
        orgRepository.save(orgEntity);
        return orgEntity;
    }

    @ControllerLogExeTime(description = "修改机构")
    @RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.PUT })

    @ApiOperation(value = "修改机构", notes = "修改机构", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", paramType = "form", required = true, dataType = "string"),
            @ApiImplicitParam(name = "name", value = "名称", paramType = "form", required = true, dataType = "string"),
            @ApiImplicitParam(name = "description", value = "描述", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "sortNum", value = "排序数", paramType = "form", dataType = "int"),
    })
    public SuccessResponse update(@ApiIgnore OrgEntity orgEntity) {
        String id = orgEntity.getId();
        OrgEntity org = orgRepository.get(id);
        org.setName(orgEntity.getName());
        org.setSortNum(orgEntity.getSortNum());
        org.setDescription(orgEntity.getDescription());
        orgRepository.save(org);
        return SuccessResponse.newInstance();
    }
}