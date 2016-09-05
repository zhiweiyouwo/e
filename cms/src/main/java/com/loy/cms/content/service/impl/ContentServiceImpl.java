package com.loy.cms.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.cms.content.domain.ContentQueryParam;
import com.loy.cms.content.domain.entity.ContentEntity;
import com.loy.cms.content.repository.ContentRepository;
import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.query.MapQueryParam;

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
@RequestMapping(value = "**/content", method = { RequestMethod.POST, RequestMethod.GET })
@Transactional

@Api(value = "内容管理", description = "内容管理")
public class ContentServiceImpl {

	@Autowired
	ContentRepository contentRepository;

	@RequestMapping(value = "/page")
	@ControllerLogExeTime(description = "查询内容", log = false)

	@ApiOperation(value = "查询内容", httpMethod = "GET")
	@ApiImplicitParams({ @ApiImplicitParam(name = "title", value = "标题", paramType = "form", dataType = "string"),
			@ApiImplicitParam(name = "pageNumber", value = "页号", paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "页的大小", paramType = "form", dataType = "int") })

	public Page<ContentEntity> queryPage(@ApiIgnore ContentQueryParam contentQueryParam, @ApiIgnore Pageable pageable) {

		Page<ContentEntity> page = contentRepository.findPage(new MapQueryParam(contentQueryParam), pageable);
		
		return page;
	}
}
