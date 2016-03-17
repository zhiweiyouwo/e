package com.loy.e.personnel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.loy.e.core.repository.GenericRepository;
import com.loy.e.personnel.domain.entity.OrgEntity;
import com.loy.e.personnel.domain.entity.PositionEntity;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public interface PositionRepository extends GenericRepository<PositionEntity,String>{
	
	@Query("SELECT r FROM PositionEntity r "
			+ "  order by r.sortNum")
	List<PositionEntity> all();
	
	@Query("SELECT max(r.code)  FROM PositionEntity r  where r.parentId = ?1  ")
	String maxCodeByParentId(String parentId);

	@Query(" from  PositionEntity    where code like ?1%")
	List<PositionEntity> findByLikeCode(String code);
}