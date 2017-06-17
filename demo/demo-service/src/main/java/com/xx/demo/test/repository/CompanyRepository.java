package com.xx.demo.test.repository;

import org.springframework.data.jpa.repository.Query;
import com.loy.e.core.query.annotation.DynamicQuery;
import com.xx.demo.test.domain.entity.CompanyEntity;
import com.xx.demo.test.domain.CompanyQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.loy.e.core.repository.GenericRepository;

/**
 * 
 * @author Loy Fu qq群 540553957 website = http://www.17jee.com
 */
public interface CompanyRepository extends GenericRepository<CompanyEntity, String> {

    @Query(value=" from CompanyEntity x where  1=1  ${permissionQL} <notEmpty name='name'> and x.name like  '%${name}%' </notEmpty><notEmpty name='registerDateStart'> and x.registerDate &gt;=  :registerDateStart </notEmpty><notEmpty name='registerDateEnd'> and x.registerDate &lt;=  :registerDateEnd </notEmpty><notEmpty name='phone'> and x.phone like  '%${phone}%' </notEmpty><notEmpty name='orderProperty'>   order by x.${orderProperty} ${direction} </notEmpty>")
    @DynamicQuery
    Page<CompanyEntity> findCompanyPage(CompanyQueryParam companyQueryParam, Pageable pageable);

}