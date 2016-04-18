package com.loy.e.tools.component;

import org.apache.commons.lang.StringUtils;

import com.loy.e.core.query.Op;
import com.loy.e.tools.model.EntityInfo;
import com.loy.e.tools.util.ToolStringUtils;

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
	private Op op;
	private int count = 1;
	private boolean often = false;
	EntityInfo entityInfo;
	private String returnClazz = "String";
	
	public AbstractInput(EntityInfo entityInfo){
		this.entityInfo = entityInfo;
	}
	public abstract String getHtml();
	public abstract String getConditionHtml();
	public abstract String getType();
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
	public Op getOp() {
		return op;
	}
	public void setOp(Op op) {
		this.op = op;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getEntityName() {
		return this.entityInfo.getEntityName();
	}
	
	public boolean isOften() {
		return often;
	}
	public void setOften(boolean often) {
		this.often = often;
	}
	public String getSearchQueryId(){
		String fieldName = this.getCombineFieldName();
		fieldName.replace("\\.", "_");
		String searchQueryId = "queryParam_"+fieldName;
		return searchQueryId;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getCombineFieldName(){
		String[] temp = this.fieldName.split("\\.");
		for(int i=0;i<temp.length;i++){
			temp[i] = ToolStringUtils.firstCharUpper(temp[i]);
		}
		String s = StringUtils.join(temp);
		s = ToolStringUtils.firstCharLower(s);
		return s;
	}
	
	public String getEntityNameFirstCharLower(){
		return ToolStringUtils.firstCharLower(this.getEntityName());
	}
	
	public String getI18nKey(){
		return this.entityInfo.getPreI18n()+"."+this.getCombineFieldName();
	}
	
	public String getInputId(){
		return fieldName.replace("\\.", "_");
	}
	public String getInputName(){
		return fieldName;
	}
	
	public String getFormatter(){
		return "";
	}
	public String getReturnClazz() {
		return returnClazz;
	}
	public void setReturnClazz(String returnClazz) {
		this.returnClazz = returnClazz;
	}
	public EntityInfo getEntityInfo() {
		return entityInfo;
	}
	
	
}
