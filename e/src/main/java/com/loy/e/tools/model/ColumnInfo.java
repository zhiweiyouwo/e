package com.loy.e.tools.model;

import com.loy.e.core.annotation.ConditionParam;
import com.loy.e.core.annotation.LoyColumn;
import com.loy.e.core.annotation.Op;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class ColumnInfo {
	
	private boolean list = true;
	private boolean edit = true;
	private boolean detail = true;
	private String name;
	private String fieldName;
	private InputClazz inputType = InputClazz.TEXT;
	
	private boolean condition = false;
	private Op op = Op.eq;
	
	public ColumnInfo(LoyColumn loyColumn){
		build(loyColumn);
	}
	public boolean isList() {
		return list;
	}
	public void setList(boolean list) {
		this.list = list;
	}
	public boolean isEdit() {
		return edit;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	public boolean isDetail() {
		return detail;
	}
	public void setDetail(boolean detail) {
		this.detail = detail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public InputClazz getInputType() {
		return inputType;
	}
	public void setInputType(InputClazz inputType) {
		this.inputType = inputType;
	}
	public boolean isCondition() {
		return condition;
	}
	public void setCondition(boolean condition) {
		this.condition = condition;
	}
	public Op getOp() {
		return op;
	}
	public void setOp(Op op) {
		this.op = op;
	}
	
	void build(LoyColumn loyColumn){
		if(loyColumn != null){
			this.list = loyColumn.list();
			this.edit = loyColumn.edit();
			this.detail = loyColumn.detail();
			this.inputType = loyColumn.inputType();
			this.name = loyColumn.name();
			ConditionParam conditionParam = loyColumn.condition();
			if(conditionParam != null){
				this.condition = true;
				this.op = conditionParam.op();
			}
		}
	}
	
}
