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
import com.loy.upm.personnel.domain.entity.PositionEntity;
import com.loy.upm.personnel.repository.EmployeeRepository;
import com.loy.upm.personnel.repository.PositionRepository;

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
@RequestMapping(value = "**/position", method = { RequestMethod.POST, RequestMethod.GET })
@Transactional
@SuppressWarnings("rawtypes")
@Api(value = "岗位管理", description = "岗位管理")
public class PositionServiceImpl {

    @Autowired
    PositionRepository positionRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @ControllerLogExeTime(description = "获取岗位树", log = false)
    @RequestMapping("/all")

    @ApiOperation(value = "获取岗位", notes = "获取岗位树", httpMethod = "GET")
    public List<TreeNode> all() {
        List<TreeNode> treeList = null;
        List<PositionEntity> list = positionRepository.findAll();
        treeList = TreeUtil.build(list);
        return treeList;
    }

    @ControllerLogExeTime(description = "获取岗位信息", log = false)
    @RequestMapping(value = "/get")

    @ApiOperation(value = "获取岗位信息", notes = "获取岗位信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "岗位ID", paramType = "form", required = true, dataType = "string"),
    })
    public PositionEntity get(String id) {
        PositionEntity orgEntity = positionRepository.get(id);
        if (orgEntity != null) {
            String parentId = orgEntity.getParentId();
            if (StringUtils.isNotEmpty(parentId)) {
                PositionEntity parentEntity = positionRepository.get(parentId);
                orgEntity.setParentName(parentEntity.getName());
            }
        }
        return orgEntity;
    }

    @ControllerLogExeTime(description = "删除岗位信息")
    @RequestMapping(value = "/del", method = { RequestMethod.POST, RequestMethod.DELETE })

    @ApiOperation(value = "删除岗位", notes = "删除岗位", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "ID", paramType = "form", required = true, dataType = "string")
    public SuccessResponse del(String id) {
        if (StringUtils.isNotEmpty(id)) {
            PositionEntity positionEntity = positionRepository.get(id);
            String code = positionEntity.getCode();
            //设置所有用户引用些org为空
            List<EmployeeEntity> list = employeeRepository.findByOrgCode(code);
            if (list != null) {
                for (EmployeeEntity e : list) {
                    e.setPosition(null);
                    employeeRepository.save(e);
                }
            }
            //删除其子
            List<PositionEntity> olist = positionRepository.findByLikeCode(code);
            if (olist != null) {
                for (PositionEntity o : olist) {
                    positionRepository.delete(o);
                }
            }
        }
        return SuccessResponse.newInstance();
    }

    @ControllerLogExeTime(description = "保存岗位信息")
    @RequestMapping(value = "/save")

    @ApiOperation(value = "保存岗位", notes = "保存岗位", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", paramType = "form", required = true, dataType = "string"),
            @ApiImplicitParam(name = "description", value = "描述", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "parentId", value = "上级ID", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "sortNum", value = "排序数", paramType = "form", dataType = "int"),
    })

    public PositionEntity save(@ApiIgnore PositionEntity positionEntity) {
        String parentId = positionEntity.getParentId();
        if (StringUtils.isNotEmpty(parentId)) {
            PositionEntity parentEntity = positionRepository.get(parentId);
            if (parentEntity == null) {
                positionEntity.setParentId(null);
                positionEntity.setCode("000");
            } else {
                String pcode = parentEntity.getCode();
                String ccode = positionRepository.maxCodeByParentId(parentId);
                if (StringUtils.isEmpty(ccode)) {
                    ccode = pcode + "000";
                } else {
                    //最ccode 后三位
                    int len = ccode.length();
                    String last = ccode.substring(len - 3, len);
                    int num = Integer.valueOf(last) + 1;
                    ccode = ccode.substring(0, len - 3) + String.format("%03d", num);
                }
                positionEntity.setCode(ccode);
            }
        } else {
            positionEntity.setParentId(null);
            positionEntity.setCode("000");
        }
        positionRepository.save(positionEntity);
        return positionEntity;
    }

    @ControllerLogExeTime(description = "修改岗位信息")
    @RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.PUT })

    @ApiOperation(value = "保存岗位", notes = "保存岗位", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", paramType = "form", required = true, dataType = "string"),
            @ApiImplicitParam(name = "name", value = "名称", paramType = "form", required = true, dataType = "string"),
            @ApiImplicitParam(name = "description", value = "描述", paramType = "form", dataType = "string"),
            @ApiImplicitParam(name = "sortNum", value = "排序数", paramType = "form", dataType = "int"),
    })
    public SuccessResponse update(@ApiIgnore PositionEntity positionEntity) {
        String id = positionEntity.getId();
        PositionEntity org = positionRepository.get(id);
        org.setName(positionEntity.getName());
        org.setSortNum(positionEntity.getSortNum());
        org.setDescription(positionEntity.getDescription());
        positionRepository.save(org);
        return SuccessResponse.newInstance();
    }
}