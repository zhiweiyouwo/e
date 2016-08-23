package com.loy.app.common.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.loy.e.core.entity.AbstractEntity;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@Entity
@Table(name="loy_sequence")

public class SequenceEntity extends AbstractEntity<String>{
	
	private static final long serialVersionUID = 1549263959356222521L;

	public static String EMPLOYEE_KEY = "EMPLOYEE_SEQ";
	
	private Integer v;
	public Integer getV() {
		return v;
	}

	public void setV(Integer v) {
		this.v = v;
	}
	
	

}