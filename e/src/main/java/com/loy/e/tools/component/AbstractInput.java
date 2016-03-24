package com.loy.e.tools.component;

import org.apache.commons.lang.StringUtils;

import com.loy.e.core.annotation.Op;
import com.loy.e.tools.model.EntityInfo;

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
	private EntityInfo entityInfo;
	public AbstractInput(EntityInfo entityInfo){
		this.entityInfo = entityInfo;
	}
	public abstract String getHtml();
	public abstract String getConditionHtml();
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
	
	public String getSearchQueryId(){
		String fieldName = this.getFieldName();
		fieldName.replace("\\.", "_");
		String eName = this.getEntityName();
		eName = firstCharLower(eName);
		eName = deleteEntity(eName);
		String searchQueryId = eName+"QueryParam_"+fieldName;
		return searchQueryId;
	}
	public static String firstCharLower(String s){
		char[] chars = s.toCharArray();
		if(chars[0]<='Z' &&  chars[0]>='A'){
			chars[0] = (char) (chars[0]+32);
			return new String(chars);
		}
		return s;
	}
	public static String firstCharUpper(String s){
		char[] chars = s.toCharArray();
		if(chars[0]<='z' &&  chars[0]>='a'){
			chars[0] = (char) (chars[0]-32);
			return new String(chars);
		}
		return s;
	}
	public static String deleteEntity(String s){
		s = s.replaceAll("Entity","");
		return s;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCombineFieldName(){
		String[] temp = this.fieldName.split("\\.");
		for(int i=0;i<temp.length;i++){
			temp[i] = firstCharUpper(temp[i]);
		}
		String s = StringUtils.join(temp);
		s = firstCharLower(s);
		return s;
	}
	
	public String getEntityNameFirstCharLower(){
		return firstCharLower(this.getEntityName());
	}
	
	public String getI18nKey(){
		return this.entityInfo.getModelName()+"."+deleteEntity(getEntityNameFirstCharLower())+"."+this.fieldName;
	}
	
	public String getInputId(){
		return fieldName.replace("\\.", "_");
	}
	public String getInputName(){
		return fieldName;
	}
}
