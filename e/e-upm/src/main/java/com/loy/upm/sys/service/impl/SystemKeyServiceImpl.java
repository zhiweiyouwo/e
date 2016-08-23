package com.loy.upm.sys.service.impl;

import org.springframework.stereotype.Service;

import com.loy.e.security.service.SystemKeyService;

@Service(value="systemKeyService")
public class SystemKeyServiceImpl implements SystemKeyService {

	
	@Override
	public String getSystemCode() {
		return "upm";
	}


}
