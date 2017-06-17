package com.xx.demo.test.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.common.util.DateUtil;
import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.query.Direction;
import com.loy.e.core.util.TableToExcelUtil;
import com.loy.e.data.permission.annotation.DataPermission;
import com.xx.demo.test.domain.CompanyQueryParam;
import com.xx.demo.test.domain.entity.CompanyEntity;
import com.xx.demo.test.repository.CompanyRepository;
/**
 * 
 * @author Loy Fu qq群 540553957 website = http://www.17jee.com
 */
@RestController
@RequestMapping(value={"**/company"}, method={RequestMethod.POST, RequestMethod.GET})
@Transactional
public class CompanyServiceImpl{

    @Autowired
    CompanyRepository companyRepository;
    @RequestMapping({ "/page" })
    @ControllerLogExeTime(description = "分页查询公司", log = false)
    @DataPermission(uniqueKey="CompanyServiceImpl.queryPage",findAll=true)
    public Page<CompanyEntity> queryPage(CompanyQueryParam companyQueryParam, Pageable pageable) {
        if (companyQueryParam != null) {
            processSort(companyQueryParam);
            Date registerDateEnd = companyQueryParam.getRegisterDateEnd();
            if (registerDateEnd != null) {
                registerDateEnd = DateUtil.addOneDay(registerDateEnd);
                companyQueryParam.setRegisterDateEnd(registerDateEnd);
            }
        }
        Page<CompanyEntity> page = this.companyRepository.findCompanyPage((companyQueryParam), pageable);
        return page;
    }
  
  
	@ControllerLogExeTime(description="获取公司", log=false)
	@RequestMapping({"/get"})
	public CompanyEntity get(String id) {
		CompanyEntity companyEntity = (CompanyEntity)this.companyRepository.get(id);
		return companyEntity;
	}
	@ControllerLogExeTime(description="查看公司", log=false)
	@RequestMapping({"/detail"})
	public CompanyEntity detail(String id) {
		CompanyEntity companyEntity = (CompanyEntity)this.companyRepository.get(id);
		return companyEntity;
	}
	@ControllerLogExeTime(description="删除公司")
	@RequestMapping({"/del"})
	public void del(String id) {
		if (StringUtils.isNotEmpty(id)) {
			String[] idsArr = id.split(",");
			if (idsArr != null) {
				for (String idd : idsArr) {
					CompanyEntity companyEntity = (CompanyEntity)this.companyRepository.get(idd);
					if (companyEntity != null) {
						this.companyRepository.delete(companyEntity);
					}
				}
			}
		}
	}

	@RequestMapping({"/save"})
	@ControllerLogExeTime(description="保存公司")
	public CompanyEntity save(CompanyEntity companyEntity) {
	companyEntity.setId(null);
		this.companyRepository.save(companyEntity);
		return companyEntity;
	}
	@RequestMapping({"/update"})
	@ControllerLogExeTime(description="修改公司")
	public void update(CompanyEntity companyEntity) {
		this.companyRepository.save(companyEntity);
	}
  
  
	@RequestMapping(value={"/excel"}, method={RequestMethod.POST})
	@ControllerLogExeTime(description="导出公司", log=false)
	public void excel(String html, HttpServletResponse response) throws IOException {
		response.setContentType("application/msexcel;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=companys.xls");
		OutputStream out = response.getOutputStream();
		TableToExcelUtil.createExcelFormTable("company", html, 1, out);
		out.flush();
		out.close();
	}
	private void processSort(CompanyQueryParam companyQueryParam){
		String orderProperity = companyQueryParam.getOrderProperty();
		if (StringUtils.isNotEmpty(orderProperity)) {
			String[] orderProperties = {"registerDate"};
			if (ArrayUtils.contains(orderProperties, orderProperity)) {
				String direction = companyQueryParam.getDirection();
				if ((!Direction.ASC.getInfo().equalsIgnoreCase(direction)) && 
					(!Direction.DESC.getInfo().equalsIgnoreCase(direction))) {
					  companyQueryParam.setDirection(Direction.DESC.getInfo());
				}
			}
		}else {
			companyQueryParam.setOrderProperty("");
			companyQueryParam.setDirection("");
		}
	}
}