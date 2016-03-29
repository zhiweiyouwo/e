package com.loy.e.sys.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.query.MapQueryParam;
import com.loy.e.core.util.TableToExcelUtil;
import com.loy.e.sys.repository.PerformanceRepository;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping(value = "/monitor",method={RequestMethod.POST,RequestMethod.GET})
@Transactional
public class MonitorServiceImpl {
	@Autowired
	PerformanceRepository performanceRepository;
	
	@RequestMapping(value="/performance")
	public Page  queryPerformancePage(Pageable pageable){
		Page page = performanceRepository.findPage("sys.monitor.queryPerformancePage", 
				"sys.monitor.queryPerformanceCount",null, pageable);
		return page;
		
	}
	
	@RequestMapping(value="/excel",method={RequestMethod.POST})
	@ControllerLogExeTime(description="导出方法执行时间",log=false)
    public void  excel(String html ,HttpServletResponse response) throws IOException{
		response.setContentType("application/msexcel;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=exetimes.xls");
		OutputStream out = response.getOutputStream();
		TableToExcelUtil.createExcelFormTable("exetime", html, 1, out);
		out.flush();
		out.close();
	}
}