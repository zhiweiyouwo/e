package com.loy.e.tools.model;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class ColumnInfo {
	
	private String description;
	private String fieldName;
	
	private EntityInfo entityInfo;
	
	public ColumnInfo(EntityInfo entityInfo){
		this.entityInfo = entityInfo;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	
	public String getEntityName() {
		return this.entityInfo.getEntityName();
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
}
