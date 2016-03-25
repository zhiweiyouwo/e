package com.loy.e.tools.model;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;

import org.springframework.core.annotation.AnnotationUtils;

import com.loy.e.core.annotation.ConditionParam;
import com.loy.e.core.annotation.LoyColumn;
import com.loy.e.core.annotation.LoyEntity;
import com.loy.e.core.annotation.LoyField;
import com.loy.e.core.entity.Entity;
import com.loy.e.tools.component.AbstractInput;
import com.loy.e.tools.component.DateInput;
import com.loy.e.tools.component.FloatInput;
import com.loy.e.tools.component.IntegerInput;
import com.loy.e.tools.component.SearchInput;
import com.loy.e.tools.component.TextAreaInput;
import com.loy.e.tools.component.TextInput;
import com.loy.e.tools.util.ToolStringUtils;

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
	Set<String> importClassNames = new HashSet<String>();
	Set<String> importParamClassNames = new HashSet<String>();
	Map<String,String> i18ns = new HashMap<String,String>();
	
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
		i18ns.put(getPreI18n(), this.name);
		
		i18ns.put(getEditI18nKey(), "编辑"+this.name);
		i18ns.put(getDetailI18nKey(), "查看"+this.name);
		
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
			SearchInput input = new SearchInput(this);
			abstractInput = input;
			input.setTableName(tableName);
			String column = loyColumn.column();
			if(column.equals("")){
				column = "name";
			}
			input.setLabel(column);
            this.importClassNames.add("import "+type.getName());
			
		}else{
			if(type == String.class){
				Column columnAnnotation = field.getAnnotation(Column.class);
				if(columnAnnotation != null){
					int length = columnAnnotation.length();
					if(length>255){
						TextAreaInput input = new TextAreaInput(this);
						abstractInput = input;
					}else{
						TextInput input = new TextInput(this);
						abstractInput = input;
					}
				}else{
					TextInput input = new TextInput(this);
					abstractInput = input;
				}
			}else if(type == Date.class){
				DateInput input = new DateInput(this);
				abstractInput = input;
			}else if(type == Integer.class || type == Long.class || type==BigInteger.class || type==Short.class){
				IntegerInput input = new IntegerInput(this);
				abstractInput = input;
			}else if(type == Float.class || type == Double.class || type==BigDecimal.class){
				FloatInput input = new FloatInput(this);
				abstractInput = input;
			}
		}
		abstractInput.setFieldName(field.getName());
		abstractInput.setLabelName(loyColumn.description());
		i18ns.put(getPreI18n()+"."+field.getName(), loyColumn.description());
		this.editColumns.add(abstractInput);
	
	}
	private void buildDetail(LoyColumn loyColumn ,Field field){
		Class type = field.getType();
		boolean entityFlag = Entity.class.isAssignableFrom(type);
		if(entityFlag){
			LoyField[]  lists = loyColumn.details();
			if(lists.length>0){
				for(LoyField c : lists){
					ColumnInfo columnInfo = new ColumnInfo(this);
					String fName = field.getName();
					fName = fName+"."+c.fieldName();
					columnInfo.setFieldName(fName);
					columnInfo.setDescription(c.description());
					this.detailColumns.add(columnInfo);
					i18ns.put(getPreI18n()+"."+fName, c.description());
				}
			}
		}else{
			ColumnInfo columnInfo = new ColumnInfo(this);
			columnInfo.setFieldName(field.getName());
			columnInfo.setDescription(loyColumn.description());
			if(type == Date.class){
				columnInfo.setFormatter("date");
			}
			i18ns.put(getPreI18n()+"."+field.getName(), loyColumn.description());
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
					ColumnInfo columnInfo = new ColumnInfo(this);
					String fName = field.getName();
					fName = fName+"."+c.fieldName();
					columnInfo.setFieldName(fName);
					columnInfo.setDescription(c.description());
					i18ns.put(getPreI18n()+"."+fName, c.description());
					this.listColumns.add(columnInfo);
				}
			}
		}else{
			ColumnInfo columnInfo = new ColumnInfo(this);
			columnInfo.setFieldName(field.getName());
			columnInfo.setDescription(loyColumn.description());
			if(type == Date.class){
				columnInfo.setFormatter("date");
			}
			i18ns.put(getPreI18n()+"."+field.getName(), loyColumn.description());
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
						TextInput searchInput = new TextInput(this);
						String fName = field.getName();
						fName = fName+"."+c.fieldName();
						searchInput.setFieldName(fName);
						searchInput.setLabelName(c.description());
						searchInput.setOp(c.op());
						i18ns.put(getPreI18n()+"."+fName, c.description());
						this.conditionColumns.add(searchInput);
					}
				}else{
					TextInput searchInput = new TextInput(this);
					searchInput.setFieldName(conditionColumn.name());
					searchInput.setLabelName(loyColumn.description());
					searchInput.setOp(conditionColumn.op());
					i18ns.put(getPreI18n()+"."+field.getName(), loyColumn.description());
					this.conditionColumns.add(searchInput);
				}
			}else{
				AbstractInput searchInput = null;
				importParamClassNames.add("import "+field.getType().getName());
				int count = conditionColumn.count();
				if(type == Date.class){
					searchInput = new DateInput(this);
					searchInput.setCount(count);
					importParamClassNames.add("import org.springframework.format.annotation.DateTimeFormat");
					
				}else if(type == Integer.class || type == Long.class || type==BigInteger.class || type==Short.class){
						IntegerInput input = new IntegerInput(this);
						searchInput = input;
				}else if(type == Float.class || type == Double.class || type==BigDecimal.class){
					FloatInput input = new FloatInput(this);
					searchInput = input;
				}
				else{
				searchInput = new TextInput(this);
				}
				searchInput.setFieldName(conditionColumn.name());
				searchInput.setLabelName(loyColumn.description());
				searchInput.setOp(conditionColumn.op());
				if(count>1){
					i18ns.put(getPreI18n()+"."+field.getName()+"Start", loyColumn.description()+"开始");
					i18ns.put(getPreI18n()+"."+field.getName()+"End", loyColumn.description()+"结束");
				}else{
					i18ns.put(getPreI18n()+"."+field.getName(), loyColumn.description());
				}
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

	public Set<String> getImportClassNames() {
		return importClassNames;
	}

	public void setImportClassNames(Set<String> importClassNames) {
		this.importClassNames = importClassNames;
	}

	public Map<String, String> getI18ns() {
		return i18ns;
	}

	public void setI18ns(Map<String, String> i18ns) {
		this.i18ns = i18ns;
	}
    public String getEntityNameFirstLower(){
    	return ToolStringUtils.firstCharLower(entityName.replaceFirst("Entity", ""));
    }
    public String getPreI18n(){
    	return modelName+"."+getEntityNameFirstLower();
    }
    public String getEditI18nKey(){
    	return getPreI18n()+".edit"+entityName.replaceFirst("Entity", "");
    }
    public String getDetailI18nKey(){
    	return getPreI18n()+".detail"+entityName.replaceFirst("Entity", "");
    }

	public Set<String> getImportParamClassNames() {
		return importParamClassNames;
	}

	public void setImportParamClassNames(Set<String> importParamClassNames) {
		this.importParamClassNames = importParamClassNames;
	}
    
}
