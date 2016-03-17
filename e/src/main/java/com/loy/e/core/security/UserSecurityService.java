package com.loy.e.core.security;

import com.loy.e.sys.domain.entity.UserEntity;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public interface UserSecurityService {
	
	UserEntity findByUsername(String username);
	
}