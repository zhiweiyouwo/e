package com.loy.e.tools.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.annotation.AnnotationUtils;

import com.loy.e.core.annotation.ConditionParam;
import com.loy.e.core.annotation.LoyColumn;
import com.loy.e.core.annotation.LoyEntity;
import com.loy.e.core.annotation.LoyField;
import com.loy.e.core.entity.Entity;
import com.loy.e.tools.component.AbstractInput;
import com.loy.e.tools.component.DateInput;
import com.loy.e.tools.component.SearchInput;
import com.loy.e.tools.component.TextInput;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class EntityInfo {

	public static final String  QL = "ql";
	public static final String  REPOSITOR = "repository";
	public static final String  SERVICE_IMPL = "service.impl";
	public static final String  DOMAIN = "domain";
	public String modelPackageName;
	public String qlPackageName;
	public String repositoryPackageName;
	public String serviceImplPackageName;
	public String domainPackageName;
	public String name;
	public String entityName;
	public String modelName;
	public String left="<";
	
	public EntityInfo(Class clazz){
		build(clazz);
	}
	
	List<ColumnInfo> listColumns = new ArrayList<ColumnInfo>();
	List<AbstractInput> editColumns = new ArrayList<AbstractInput>();
	List<ColumnInfo> detailColumns = new ArrayList<ColumnInfo>();
	List<AbstractInput> conditionColumns = new ArrayList<AbstractInput>();
	
	
	public void build(Class clazz){
		String packageName = clazz.getPackage().getName();
		String simpleName = clazz.getSimpleName();
		this.entityName = simpleName;
		this.modelPackageName = packageName.replaceFirst(".domain.entity", "");
		int index = this.modelPackageName.lastIndexOf(".")+1;
		this.modelName = this.modelPackageName.substring(index, this.modelPackageName.length());
		LoyEntity loyEntity = AnnotationUtils.findAnnotation(clazz, LoyEntity.class);
		if(loyEntity != null){
			this.name = loyEntity.name();
		}
		Field[] fields = clazz.getDeclaredFields(); 
		for(int i=0;i<fields.length;i++) { 
			LoyColumn loyColumn = AnnotationUtils.findAnnotation(fields[i], LoyColumn.class);
			if(loyColumn != null){
				if(loyColumn.list()){
					buildList(loyColumn ,fields[i]);
				}
				if(loyColumn.edit()){
					buildEdit(loyColumn , fields[i]);
				}
				if(loyColumn.detail()){
					buildDetail(loyColumn , fields[i]);
				}
				buildCondition(loyColumn , fields[i]);
				
			}
	    } 
	}
	
	private void buildEdit(LoyColumn loyColumn ,Field field){

		AbstractInput abstractInput = null;
		InputClazz inputClazz = loyColumn.inputType();
		Class type = field.getType();
		boolean entityFlag = Entity.class.isAssignableFrom(type);
		if(entityFlag){
			String sName = type.getSimpleName();
			sName= sName.replaceFirst("Entity", "");
			String tableName = "loy_"+sName.toLowerCase();
			SearchInput input = new SearchInput();
			abstractInput = input;
			input.setTableName(tableName);
			String column = loyColumn.column();
			if(column.equals("")){
				column = "name";
			}
			input.setLabel(column);

			
		}else{
			if(type == String.class){
				TextInput input = new TextInput();
				abstractInput = input;
			}else if(type == Date.class){
				DateInput input = new DateInput();
				abstractInput = input;
			}
		}
		abstractInput.setFieldName(field.getName());
		abstractInput.setLabelName(loyColumn.description());
		this.editColumns.add(abstractInput);
	
	}
	private void buildDetail(LoyColumn loyColumn ,Field field){
		Class type = field.getType();
		boolean entityFlag = Entity.class.isAssignableFrom(type);
		if(entityFlag){
			LoyField[]  lists = loyColumn.details();
			if(lists.length>0){
				for(LoyField c : lists){
					ColumnInfo columnInfo = new ColumnInfo();
					String fName = field.getName();
					fName = fName+"."+c.fieldName();
					columnInfo.setFieldName(fName);
					columnInfo.setDescription(c.description());
					this.detailColumns.add(columnInfo);
				}
			}
		}else{
			ColumnInfo columnInfo = new ColumnInfo();
			columnInfo.setFieldName(field.getName());
			columnInfo.setDescription(loyColumn.description());
			this.detailColumns.add(columnInfo);
		}
	}
	
	private void buildList(LoyColumn loyColumn ,Field field){
		Class type = field.getType();
		boolean entityFlag = Entity.class.isAssignableFrom(type);
		if(entityFlag){
			LoyField[]  lists = loyColumn.lists();
			if(lists.length>0){
				for(LoyField c : lists){
					ColumnInfo columnInfo = new ColumnInfo();
					String fName = field.getName();
					fName = fName+"."+c.fieldName();
					columnInfo.setFieldName(fName);
					columnInfo.setDescription(c.description());
					this.listColumns.add(columnInfo);
				}
			}
		}else{
			ColumnInfo columnInfo = new ColumnInfo();
			columnInfo.setFieldName(field.getName());
			columnInfo.setDescription(loyColumn.description());
			this.listColumns.add(columnInfo);
		}
	}
	private void buildCondition(LoyColumn loyColumn ,Field field){
		ConditionParam conditionColumn = AnnotationUtils.findAnnotation(field, ConditionParam.class);
		if(conditionColumn != null){
			Class type = field.getType();
			boolean entityFlag = Entity.class.isAssignableFrom(type);
			if(entityFlag){
				LoyField[]  lists = conditionColumn.list();
				if(lists.length>0){
					for(LoyField c : lists){
						TextInput searchInput = new TextInput();
						String fName = field.getName();
						fName = fName+"."+c.fieldName();
						searchInput.setFieldName(fName);
						searchInput.setLabelName(c.description());
						searchInput.setOp(c.op());
						this.conditionColumns.add(searchInput);
					}
				}else{
					TextInput searchInput = new TextInput();
					searchInput.setFieldName(conditionColumn.name());
					searchInput.setLabelName(loyColumn.description());
					searchInput.setOp(conditionColumn.op());
					this.conditionColumns.add(searchInput);
				}
			}else{
				AbstractInput searchInput = null;
				int count = conditionColumn.count();
				if(type == Date.class){
					searchInput = new DateInput();
					searchInput.setCount(count);
				}else{
					searchInput = new TextInput();
				}
				searchInput.setFieldName(conditionColumn.name());
				searchInput.setLabelName(loyColumn.description());
				searchInput.setOp(conditionColumn.op());
				this.conditionColumns.add(searchInput);
			}
		}
	}
	public String getModelPackageName() {
		return modelPackageName;
	}

	public void setModelPackageName(String modelPackageName) {
		this.modelPackageName = modelPackageName;
	}

	public String getQlPackageName() {
		return this.modelPackageName +"."+QL;
	}

	public void setQlPackageName(String qlPackageName) {
		this.qlPackageName = qlPackageName;
	}

	public String getRepositoryPackageName() {
		return this.modelPackageName +"."+REPOSITOR;
	}

	public void setRepositoryPackageName(String repositoryPackageName) {
		this.repositoryPackageName = repositoryPackageName;
	}

	public String getServiceImplPackageName() {
		return this.modelPackageName +"."+SERVICE_IMPL;
	}

	public void setServiceImplPackageName(String serviceImplPackageName) {
		this.serviceImplPackageName = serviceImplPackageName;
	}

	public String getDomainPackageName() {
		return this.modelPackageName +"."+DOMAIN;
	}

	public void setDomainPackageName(String domainPackageName) {
		this.domainPackageName = domainPackageName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public List<ColumnInfo> getListColumns() {
		return listColumns;
	}

	public void setListColumns(List<ColumnInfo> listColumns) {
		this.listColumns = listColumns;
	}

	public List<AbstractInput> getEditColumns() {
		return editColumns;
	}

	public void setEditColumns(List<AbstractInput> editColumns) {
		this.editColumns = editColumns;
	}

	public List<ColumnInfo> getDetailColumns() {
		return detailColumns;
	}

	public void setDetailColumns(List<ColumnInfo> detailColumns) {
		this.detailColumns = detailColumns;
	}

	public List<AbstractInput> getConditionColumns() {
		return conditionColumns;
	}

	public void setConditionColumns(List<AbstractInput> conditionColumns) {
		this.conditionColumns = conditionColumns;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}
	
}
