package com.loy.e.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.loy.e.sys.domain.entity.TestEntity;
import com.loy.e.tools.model.EntityInfo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class CodeTool {
	public static String javaSrcPath = "D:\\git\\jee_framework\\e\\src\\main\\java";
	
	public static void main(String[] args) throws IOException, TemplateException {
		String templatePath = CodeTool.class.getResource("").getPath()+"template";
		Configuration cfg = new Configuration();
		File file = new File(templatePath);
		cfg.setDirectoryForTemplateLoading(file);
		
		EntityInfo entityInfo = new EntityInfo(TestEntity.class);
		
		
		Template t = cfg.getTemplate("queryParam.ftl"); 
		String packageName = entityInfo.getDomainPackageName();
		packageName = packageName.replaceAll(".", "/");
		packageName = javaSrcPath+packageName;
		File f = new File(packageName);
		if(!f.exists()){
			f.mkdirs();
		}
		String entityName = entityInfo.getEntityName();
		entityName = entityName.replaceFirst("Entity", "");
		String fileName = entityName+"QueryParam.java";
		f = new File(packageName,fileName);
        t.process(entityInfo, new OutputStreamWriter(System.out)); 
        t.process(entityInfo, new OutputStreamWriter(new FileOutputStream(f))); 
        
        
        t = cfg.getTemplate("ql.ftl"); 
		packageName = entityInfo.getQlPackageName();
		packageName = packageName.replaceAll(".", "/");
		packageName = javaSrcPath+packageName;
		f = new File(packageName);
		if(!f.exists()){
			f.mkdirs();
		}
		entityName = entityInfo.getEntityName();
		entityName = entityName.replaceFirst("Entity", "");
		fileName = entityName+"-dynamic.xml";
		f = new File(packageName,fileName);
        t.process(entityInfo, new OutputStreamWriter(System.out)); 
        t.process(entityInfo, new OutputStreamWriter(new FileOutputStream(f))); 
        
        
        t = cfg.getTemplate("repository.ftl"); 
		packageName = entityInfo.getQlPackageName();
		packageName = packageName.replaceAll(".", "/");
		packageName = javaSrcPath+packageName;
		f = new File(packageName);
		if(!f.exists()){
			f.mkdirs();
		}
		entityName = entityInfo.getEntityName();
		entityName = entityName.replaceFirst("Entity", "");
		fileName = entityName+"Repository.java";
		f = new File(packageName,fileName);
        t.process(entityInfo, new OutputStreamWriter(System.out)); 
        t.process(entityInfo, new OutputStreamWriter(new FileOutputStream(f))); 
        
        
        
        t = cfg.getTemplate("serviceImpl.ftl"); 
		packageName = entityInfo.getQlPackageName();
		packageName = packageName.replaceAll(".", "/");
		packageName = javaSrcPath+packageName;
		f = new File(packageName);
		if(!f.exists()){
			f.mkdirs();
		}
		entityName = entityInfo.getEntityName();
		entityName = entityName.replaceFirst("Entity", "");
		fileName = entityName+"ServiceImpl.java";
		f = new File(packageName,fileName);
        t.process(entityInfo, new OutputStreamWriter(System.out)); 
        t.process(entityInfo, new OutputStreamWriter(new FileOutputStream(f))); 
	}
	
	public static void generateCode(Class entityClass){
		
	}

}