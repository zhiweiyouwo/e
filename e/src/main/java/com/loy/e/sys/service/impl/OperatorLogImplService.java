package com.loy.e.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.core.query.MapQueryParam;
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
}