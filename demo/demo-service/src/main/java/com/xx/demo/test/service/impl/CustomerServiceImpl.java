package com.xx.demo.test.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.basic.data.domain.entity.DictionaryEntity;
import com.loy.e.basic.data.repository.DictionaryRepository;
import com.loy.e.common.util.DateUtil;
import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.util.TableToExcelUtil;
import com.xx.demo.test.domain.CustomerQueryParam;
import com.xx.demo.test.domain.entity.CompanyEntity;
import com.xx.demo.test.domain.entity.CustomerEntity;
import com.xx.demo.test.repository.CompanyRepository;
import com.xx.demo.test.repository.CustomerRepository;
/**
 * 
 * @author Loy Fu qq群 540553957 website = http://www.17jee.com
 */
@RestController
@RequestMapping(value={"**/customer"}, method={RequestMethod.POST, RequestMethod.GET})
@Transactional
public class CustomerServiceImpl{

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    DictionaryRepository dictionaryRepository;
    @RequestMapping({ "/page" })
    @ControllerLogExeTime(description = "分页查询客户", log = false)
   
    public Page<CustomerEntity> queryPage(CustomerQueryParam customerQueryParam, Pageable pageable) {
        if (customerQueryParam != null) {
            Date dobEnd = customerQueryParam.getDobEnd();
            if (dobEnd != null) {
                dobEnd = DateUtil.addOneDay(dobEnd);
                customerQueryParam.setDobEnd(dobEnd);
            }
        }
        Page<CustomerEntity> page = this.customerRepository.findCustomerPage((customerQueryParam), pageable);
        return page;
    }
  
  
	@ControllerLogExeTime(description="获取客户", log=false)
	@RequestMapping({"/get"})
	public CustomerEntity get(String id) {
		CustomerEntity customerEntity = (CustomerEntity)this.customerRepository.get(id);
		return customerEntity;
	}
	@ControllerLogExeTime(description="查看客户", log=false)
	@RequestMapping({"/detail"})
	public CustomerEntity detail(String id) {
		CustomerEntity customerEntity = (CustomerEntity)this.customerRepository.get(id);
		return customerEntity;
	}
	@ControllerLogExeTime(description="删除客户")
	@RequestMapping({"/del"})
	public void del(String id) {
		if (StringUtils.isNotEmpty(id)) {
			String[] idsArr = id.split(",");
			if (idsArr != null) {
				for (String idd : idsArr) {
					CustomerEntity customerEntity = (CustomerEntity)this.customerRepository.get(idd);
					if (customerEntity != null) {
						this.customerRepository.delete(customerEntity);
					}
				}
			}
		}
	}

	@RequestMapping({"/save"})
	@ControllerLogExeTime(description="保存客户")
	public CustomerEntity save(CustomerEntity customerEntity) {
		customerEntity.setId(null);
		DictionaryEntity sex = customerEntity.getSex();
		if (sex != null) {
			String sexId = (String)sex.getId();
			if (StringUtils.isEmpty(sexId)) {
				sex = null;
			}else {
				sex = (DictionaryEntity)this.dictionaryRepository.get(sexId);
			}
		}
		customerEntity.setSex(sex);
		CompanyEntity company = customerEntity.getCompany();
		if (company != null) {
			String companyId = (String)company.getId();
			if (StringUtils.isEmpty(companyId)) {
				company = null;
			}else {
				company = (CompanyEntity)this.companyRepository.get(companyId);
			}
		}
		customerEntity.setCompany(company);
		this.customerRepository.save(customerEntity);
		return customerEntity;
	}
	@RequestMapping({"/update"})
	@ControllerLogExeTime(description="修改客户")
	public void update(CustomerEntity customerEntity) {
		DictionaryEntity sex = customerEntity.getSex();
		if (sex != null) {
			String sexId = (String)sex.getId();
			if (StringUtils.isEmpty(sexId)) {
				sex = null;
			}else {
			    sex = (DictionaryEntity)this.dictionaryRepository.get(sexId);
			}
		}
		customerEntity.setSex(sex);
		CompanyEntity company = customerEntity.getCompany();
		if (company != null) {
			String companyId = (String)company.getId();
			if (StringUtils.isEmpty(companyId)) {
				company = null;
			}else {
			    company = (CompanyEntity)this.companyRepository.get(companyId);
			}
		}
		customerEntity.setCompany(company);
		this.customerRepository.save(customerEntity);
	}
  
  
	@RequestMapping(value={"/excel"}, method={RequestMethod.POST})
	@ControllerLogExeTime(description="导出客户", log=false)
	public void excel(String html, HttpServletResponse response) throws IOException {
		response.setContentType("application/msexcel;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=customers.xls");
		OutputStream out = response.getOutputStream();
		TableToExcelUtil.createExcelFormTable("customer", html, 1, out);
		out.flush();
		out.close();
	}
}