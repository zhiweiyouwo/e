package com.loy.e.personnel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.loy.e.core.repository.GenericRepository;
import com.loy.e.personnel.domain.entity.OrgEntity;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public interface OrgRepository extends GenericRepository<OrgEntity,String>{
	
	@Query("SELECT r FROM OrgEntity r "
			+ "   order by r.sortNum")
	List<OrgEntity> all();
	
	@Query("SELECT max(r.code)  FROM OrgEntity r  where r.parentId = ?1  ")
	String maxCodeByParentId(String parentId);

	@Query(" from  OrgEntity    where code like ?1%")
	List<OrgEntity> findByLikeCode(String code);
}