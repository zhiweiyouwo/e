package com.loy.e.sys.domain.entity;

import java.util.Date;

import javax.persistence.Entity;

import com.loy.e.core.annotation.ConditionParam;
import com.loy.e.core.annotation.LoyColumn;
import com.loy.e.core.annotation.LoyEntity;
import com.loy.e.core.annotation.Op;
import com.loy.e.core.entity.BaseEntity;
import com.loy.e.core.annotation.LoyField;
/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

@LoyEntity(name="测试实体")
@Entity(name="loy_test")
public class TestEntity extends BaseEntity{
	@ConditionParam(op=Op.eq, name = "name")
    @LoyColumn(description="姓名")
	private String name;
    
	
    @LoyColumn(description="日期")
	private Date date;
    
    @LoyColumn(description="用户",column="name",
    		lists={@LoyField(description="姓名",fieldName="name"),
    			  @LoyField(description="邮箱",fieldName="email")})
    private UserEntity user;
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
