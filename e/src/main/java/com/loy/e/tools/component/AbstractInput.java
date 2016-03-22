package com.loy.e.tools.component;

import com.loy.e.tools.model.InputClazz;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public abstract class AbstractInput {
	private String fieldName;
	private String labelName;
	
	abstract String getHtml();
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	
	public static AbstractInput newInput(InputClazz inputClazz){
		if(inputClazz == InputClazz.TEXT){
			return new TextInput();
		}
		return null;
	}
}
