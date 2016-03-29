package com.loy.e.sys.service.impl;

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

import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.query.MapQueryParam;
import com.loy.e.core.util.TableToExcelUtil;
import com.loy.e.sys.domain.LogQueryParam;
import com.loy.e.sys.domain.entity.OperatorLogEntity;
import com.loy.e.sys.repository.OperatorLogRepository;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */

@RestController
@RequestMapping(value = "/log",method={RequestMethod.POST,RequestMethod.GET})
@Transactional
public class OperatorLogImplService {
	@Autowired
	OperatorLogRepository operatorLogRepository;
	
	@RequestMapping(value="/page")
	public Page<OperatorLogEntity>  queryPage(LogQueryParam logQueryParam,Pageable pageable){
		Page<OperatorLogEntity> page = operatorLogRepository.findPage(new MapQueryParam(logQueryParam), pageable);
		return page;
	}
	
	@RequestMapping(value="/excel",method={RequestMethod.POST})
	@ControllerLogExeTime(description="导出操作日志",log=false)
    public void  excel(String html ,HttpServletResponse response) throws IOException{
		response.setContentType("application/msexcel;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=logs.xls");
		OutputStream out = response.getOutputStream();
		TableToExcelUtil.createExcelFormTable("log", html, 1, out);
		out.flush();
		out.close();
	}
}