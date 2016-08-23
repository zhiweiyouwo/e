package com.loy.upm.personnel.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.loy.e.core.query.MapQueryParam;
import com.loy.e.core.repository.GenericRepository;
import com.loy.upm.personnel.domain.entity.EmployeeEntity;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957 
 * @since 1.7
 * @version 1.0.0
 *
 */
public interface EmployeeRepository extends GenericRepository<EmployeeEntity,String>{
	 
	  public Page<EmployeeEntity> findPage(MapQueryParam param,Pageable pageable);
	  //@Modifying
	  @Query("from  EmployeeEntity  x  where x.organization.code like ?1%")
	  public List<EmployeeEntity> findByOrgCode(String code);
	  
	  @Query("from  EmployeeEntity  x  where x.username = ?1")
	  public EmployeeEntity findByUsername(String username);
}
