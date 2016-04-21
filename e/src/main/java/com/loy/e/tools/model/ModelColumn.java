package com.loy.e.tools.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class ModelColumn extends ColumnInfo{

	boolean list= true;
	boolean edit=  false;
	boolean detail=  false;
	Map<String,String> properties = new HashMap<String,String>();
	public ModelColumn(EntityInfo entityInfo) {
		super(entityInfo);
	}
	
	
	public ModelColumn(ColumnInfo columnInfo) {
		super(columnInfo.entityInfo);
		super.description = columnInfo.description;
		super.fieldName = columnInfo.fieldName;
		super.formatter = columnInfo.formatter;
		super.sortable  = columnInfo.sortable;
		super.validate = columnInfo.validate;
		
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


	public Map<String, String> getProperties() {
		return properties;
	}


	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

}
