package com.loy.portal.service.impl;

import org.springframework.stereotype.Service;

import com.loy.e.security.service.SystemKeyService;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@Service(value="systemKeyService")
public class SystemKeyServiceImpl implements SystemKeyService{

	@Override
	public String getSystemCode() {
		return "portal";
	}

}
