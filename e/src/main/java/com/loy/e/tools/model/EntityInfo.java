package com.loy.e.tools.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.annotation.AnnotationUtils;

import com.loy.e.core.annotation.LoyColumn;
import com.loy.e.core.annotation.LoyEntity;
import com.loy.e.sys.domain.entity.UserEntity;

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
	
	public EntityInfo(Class clazz){
		build(clazz);
	}
	
	List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
	
	public String fullQlPackage(){
		return this.modelPackageName +"."+QL;
	}
	
	public String fullRepositoryPackage(){
		return this.modelPackageName +"."+REPOSITOR;
	}
	
	public String fullServiceImplPackage(){
		return this.modelPackageName +"."+SERVICE_IMPL;
	}
	public String fullDomainPackage(){
		return this.modelPackageName +"."+DOMAIN;
	}
	
	public void build(Class clazz){
		String packageName = clazz.getPackage().getName();
		this.modelPackageName = packageName.replaceFirst(".domain.entity", "");
		LoyEntity loyEntity = AnnotationUtils.findAnnotation(clazz, LoyEntity.class);
		if(loyEntity != null){
			this.name = loyEntity.name();
		}
		Field[] fields = clazz.getFields(); 
		for(int i=0;i<fields.length;i++) { 
			LoyColumn loyColumn = AnnotationUtils.findAnnotation(fields[i], LoyColumn.class);
			if(loyColumn != null){
				ColumnInfo columnInfo = new ColumnInfo(loyColumn);
				String fieldName = fields[i].getName();
				columnInfo.setFieldName(fieldName);
				this.columns.add(columnInfo);
			}
	    } 
	}
	
	public static void  main(String[] args){
		EntityInfo entityInfo = new EntityInfo(UserEntity.class);
		//entityInfo.build(UserEntity.class);
	}
}
