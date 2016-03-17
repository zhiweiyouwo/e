package com.loy.e.sys.repository;

import com.loy.e.core.repository.GenericRepository;
import com.loy.e.sys.domain.entity.UserEntity;


/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public interface UserRepository extends GenericRepository<UserEntity,String>{
	
	  UserEntity findByUsername(String username); 

}