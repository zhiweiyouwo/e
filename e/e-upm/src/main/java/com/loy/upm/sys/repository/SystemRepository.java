package com.loy.upm.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.loy.e.core.repository.GenericRepository;
import com.loy.upm.sys.domain.entity.SystemEntity;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public interface SystemRepository extends GenericRepository<SystemEntity,String>{

	@Query("SELECT distinct re.system FROM UserEntity u join u.roles r join r.resources re "
			+ " where u.username = ?1  and re.resourceType = 'MENU'  order by re.system.sortNum asc")
	
	List<SystemEntity> querySystemByUsername(String username);
}
