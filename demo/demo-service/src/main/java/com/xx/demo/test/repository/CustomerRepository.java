package com.xx.demo.test.repository;

import org.springframework.data.jpa.repository.Query;
import com.loy.e.core.query.annotation.DynamicQuery;
import com.xx.demo.test.domain.entity.CustomerEntity;
import org.springframework.data.domain.Pageable;
import com.xx.demo.test.domain.CustomerQueryParam;
import org.springframework.data.domain.Page;
import com.loy.e.core.repository.GenericRepository;

/**
 * 
 * @author Loy Fu qq群 540553957 website = http://www.17jee.com
 */
public interface CustomerRepository extends GenericRepository<CustomerEntity, String> {

    @Query(value=" from CustomerEntity x where  1=1  <notEmpty name='name'> and x.name like  '%${name}%' </notEmpty><notEmpty name='sexId'> and x.sex.id =  :sexId </notEmpty><notEmpty name='vip'> and x.vip =  :vip </notEmpty><notEmpty name='dobStart'> and x.dob &gt;=  :dobStart </notEmpty><notEmpty name='dobEnd'> and x.dob &lt;=  :dobEnd </notEmpty><notEmpty name='phone'> and x.phone like  '%${phone}%' </notEmpty>")
    @DynamicQuery
    Page<CustomerEntity> findCustomerPage(CustomerQueryParam customerQueryParam, Pageable pageable);

}