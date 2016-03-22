package com.loy.e.sys.domain.entity;

import com.loy.e.core.annotation.ConditionParam;
import com.loy.e.core.annotation.LoyColumn;
import com.loy.e.core.annotation.LoyEntity;
import com.loy.e.core.annotation.Op;
import com.loy.e.core.entity.BaseEntity;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

@LoyEntity(name="测试实体")
public class TestEntity extends BaseEntity{
	
    @LoyColumn(name="姓名", condition = @ConditionParam(op=Op.eq, name = "name"))
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
