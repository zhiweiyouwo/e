package com.loy.e.tools.component;

import com.loy.e.tools.model.EntityInfo;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class FloatInput extends IntegerInput{

	public FloatInput(EntityInfo entityInfo) {
		super(entityInfo);
	}
	public String getType() {
		return "float";
	}
}
