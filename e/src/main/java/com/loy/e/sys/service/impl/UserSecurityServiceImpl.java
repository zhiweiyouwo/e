package com.loy.e.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loy.e.core.security.UserSecurityService;
import com.loy.e.sys.domain.entity.UserEntity;
import com.loy.e.sys.repository.UserRepository;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */


@Service(value="userSecurityService")
@Transactional
public class UserSecurityServiceImpl implements UserSecurityService{

	@Autowired
	UserRepository userRepository;
	@Override
	public UserEntity findByUsername(String username) {
		UserEntity user = userRepository.findByUsername(username);
		return user;
	}

}