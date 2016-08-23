package com.loy.upm.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.loy.e.core.repository.GenericRepository;
import com.loy.upm.sys.domain.entity.ResourceEntity;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public interface ResourceRepository extends GenericRepository<ResourceEntity,String>{
	
	@Query("SELECT distinct re FROM UserEntity u join u.roles r join r.resources re "
			+ " where u.id = ?1 and re.system.code = ?2 and re.resourceType = 'MENU' order by  re.parentId ,re.sortNum")
	List<ResourceEntity> findMenuByUserIdAndCode(String userId,String code);
	
	@Query("SELECT distinct re FROM UserEntity u join u.roles r join r.resources re  left join re.system s "
			+ " where u.id = ?1  and re.resourceType = 'MENU' order by s.sortNum, re.parentId ,re.sortNum")
	List<ResourceEntity> findMenuByUserIdAndCode(String userId);
	
	@Query("SELECT distinct re FROM RoleEntity r join r.resources re"
			+ " where r.id = ?1  order by  re.parentId ,re.sortNum")
	List<ResourceEntity> findResourceByRoleId(String roleId);

	@Query("SELECT distinct re FROM UserEntity u join u.roles r join r.resources re"
			+ " where u.username = ?1")
	List<ResourceEntity> findResourceByUsername(String username);
	
	
	@Query("SELECT distinct re FROM UserEntity u join u.roles r join r.resources re"
			+ " where u.username = ?1 and re.system.code = ?2")
	List<ResourceEntity> findResourceByUsernameAndCode(String username,String code);
	
	
	@Query("SELECT r FROM ResourceEntity r "
			+ " where r.parentId is null  order by r.sortNum")
	List<ResourceEntity> findResourceByParentIdIsNull();
	@Query("SELECT r FROM ResourceEntity r "
			+ " where r.parentId = ?1  order by r.sortNum")
	List<ResourceEntity> findResourceByParentId(String parentId);
	
	
	@Query("SELECT r FROM ResourceEntity r ")
	List<ResourceEntity> findAllResource();
	
	
	@Query("SELECT r FROM ResourceEntity r "
			+ " where  r.system.code = ?1")
	List<ResourceEntity> findResourceBySystemCode(String code);
	
}