package com.loy.app.common.service.impl;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.app.common.repository.PerformanceRepository;
import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.util.TableToExcelUtil;

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
@RequestMapping(value = "**/monitor", method = { RequestMethod.POST, RequestMethod.GET })
@Transactional
@SuppressWarnings("rawtypes")
@Api(value = "方法运行情况", description = "方法运行情况")
public class MonitorServiceImpl {
    @Autowired
    PerformanceRepository performanceRepository;

    @RequestMapping(value = "/performance")

    @ApiOperation(value = "查方法运行情况", httpMethod = "GET", notes = "查方法运行情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页号", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页的大小", paramType = "form", dataType = "int")
    })
    public Page queryPerformancePage(@ApiIgnore Pageable pageable) {
        Page page = performanceRepository.findPage("sys.monitor.queryPerformancePage",
                "sys.monitor.queryPerformanceCount", null, pageable);
        return page;

    }

    @RequestMapping(value = "/excel", method = { RequestMethod.POST })
    @ControllerLogExeTime(description = "导出方法执行时间", log = false)
    @ApiIgnore
    public void excel(String html, HttpServletResponse response) throws IOException {
        response.setContentType("application/msexcel;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=exetimes.xls");
        OutputStream out = response.getOutputStream();
        TableToExcelUtil.createExcelFormTable("exetime", html, 1, out);
        out.flush();
        out.close();
    }
}