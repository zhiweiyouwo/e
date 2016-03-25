package com.loy.e.sys.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

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
@Entity
@Table(name="loy_test")
public class TestEntity extends BaseEntity{
	
	 @LoyColumn(description="用户",column="name",
	    		lists={@LoyField(description="姓名",fieldName="name"),
	    			  @LoyField(description="邮箱",fieldName="email")})
	    @ManyToOne
	    private UserEntity user;
	 
	 
	@ConditionParam(op=Op.eq, name = "name")
    @LoyColumn(description="姓名")
	private String name;
    
	@DateTimeFormat(pattern="yyyy-MM-dd")
    @LoyColumn(description="日期")
	@ConditionParam(op=Op.eq, name = "date",count=2)
	private Date date;
    
	@ConditionParam(op=Op.eq, name = "ll")
	 @LoyColumn(description="整数")
	private Long ll;
	
	@LoyColumn(description="小数")
	private Float ff;
	 
	@LoyColumn(description="长字符串")
	@Column(length=500)
	private String longString;
	
   
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Long getLl() {
		return ll;
	}

	public void setLl(Long ll) {
		this.ll = ll;
	}

	public Float getFf() {
		return ff;
	}

	public void setFf(Float ff) {
		this.ff = ff;
	}

	public String getLongString() {
		return longString;
	}

	public void setLongString(String longString) {
		this.longString = longString;
	}
	
	
	
	
}
