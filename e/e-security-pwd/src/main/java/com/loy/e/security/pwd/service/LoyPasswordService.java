package com.loy.e.security.pwd.service;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.format.DefaultHashFormatFactory;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */

public class LoyPasswordService extends DefaultPasswordService{
	 public LoyPasswordService() {
		
	        DefaultHashService hashService = new DefaultHashService();
	        hashService.setGeneratePublicSalt(false);
	        this.setHashService(hashService);
	        this.setHashFormat(new LoyHashFormat());
	        this.setHashFormatFactory(new DefaultHashFormatFactory());
	 }
	
}