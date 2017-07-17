package com.pm.repository;

import org.springframework.data.jpa.repository.Query;
import com.loy.e.core.query.annotation.DynamicQuery;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.loy.e.core.repository.GenericRepository;
import com.pm.domain.ProjectQueryParam;
import com.pm.domain.entity.ProjectEntity;

/**
 * 
 * @author Loy Fu qq群 540553957 website = http://www.17jee.com
 */
public interface ProjectRepository extends GenericRepository<ProjectEntity, String> {

    @Query(value=" from ProjectEntity x where  1=1  ${permissionQL} <notEmpty name='name'> and x.name like  '%${name}%' </notEmpty><notEmpty name='registerDateStart'> and x.registerDate &gt;=  :registerDateStart </notEmpty><notEmpty name='registerDateEnd'> and x.registerDate &lt;=  :registerDateEnd </notEmpty><notEmpty name='phone'> and x.phone like  '%${phone}%' </notEmpty><notEmpty name='orderProperty'>   order by x.${orderProperty} ${direction} </notEmpty>")
    @DynamicQuery
    Page<ProjectEntity> findProjectPage(ProjectQueryParam companyQueryParam, Pageable pageable);

}