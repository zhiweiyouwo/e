package com.loy.e.core.ql.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.hibernate.internal.util.xml.MappingReader;
import org.hibernate.internal.util.xml.OriginImpl;
import org.hibernate.internal.util.xml.XmlDocument;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import com.loy.e.core.exception.LoyException;
import com.loy.e.core.ql.DynamicQlStatementBuilder;
import com.loy.e.core.ql.DynamicStatementDTDEntityResolver;
import com.loy.e.core.ql.NotEmpty;
import com.loy.e.core.ql.StatementTemplate;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */

public class DefaultDynamicQlStatementBuilder implements DynamicQlStatementBuilder,ResourceLoaderAware  {
	protected final Log LOGGER = LogFactory.getLog(DefaultDynamicQlStatementBuilder.class);  
    private Map<String, String> namedHQLQueries;  
    private Map<String, String> namedSQLQueries;  
    private String[] fileNames = {"classpath*:/**/*-dynamic.xml"};  
    private ResourceLoader resourceLoader = new PathMatchingResourcePatternResolver();  
    private EntityResolver entityResolver = new DynamicStatementDTDEntityResolver();  
    protected Map<String, StatementTemplate> templateCache; 
    private Set<String> nameCache = new HashSet<String>();  
  
    public void setFileNames(String[] fileNames) {  
        this.fileNames = fileNames;  
    }  
  
    
    public Map<String, String> getNamedHQLQueries() {  
        return namedHQLQueries;  
    }  
  
   
    public Map<String, String> getNamedSQLQueries() {  
        return namedSQLQueries;  
    }  
  
    @Override  
    public void init() throws IOException {  
        namedHQLQueries = new HashMap<String, String>();  
        namedSQLQueries = new HashMap<String, String>();  
        boolean flag = this.resourceLoader instanceof ResourcePatternResolver;  
        for (String file : fileNames) {  
            if (flag) {  
                Resource[] resources = ((ResourcePatternResolver) this.resourceLoader).getResources(file);  
                buildMap(resources);  
            } else {  
                Resource resource = resourceLoader.getResource(file);  
                buildMap(resource);  
            }  
        }  
        nameCache.clear(); 
        
        templateCache = new HashMap<String, StatementTemplate>();  
        
        Configuration configuration = new Configuration();  
        configuration.setNumberFormat("#");
        NotEmpty notEmpty = new NotEmpty();
        configuration.setSharedVariable("notEmpty", notEmpty);
        StringTemplateLoader stringLoader = new StringTemplateLoader();  
        for(Entry<String, String> entry : namedHQLQueries.entrySet()){  
            stringLoader.putTemplate(entry.getKey(), entry.getValue());  
            templateCache.put(entry.getKey(), new StatementTemplate(StatementTemplate.TYPE.HQL,new Template(entry.getKey(),new StringReader(entry.getValue()),configuration)));  
        }  
        for(Entry<String, String> entry : namedSQLQueries.entrySet()){  
            stringLoader.putTemplate(entry.getKey(), entry.getValue());  
            templateCache.put(entry.getKey(), new StatementTemplate(StatementTemplate.TYPE.SQL,new Template(entry.getKey(),new StringReader(entry.getValue()),configuration)));  
        }  
        configuration.setTemplateLoader(stringLoader);  
    }  
  
    @Override  
    public void setResourceLoader(ResourceLoader resourceLoader) {  
        this.resourceLoader = resourceLoader;  
    }  
  
    private void buildMap(Resource[] resources) throws IOException {  
        if (resources == null) {  
            return;  
        }  
        for (Resource resource : resources) {  
            buildMap(resource);  
        }  
    }  
  
    @SuppressWarnings({ "rawtypes" })  
    private void buildMap(Resource resource) {  
        InputSource inputSource = null;  
        try {  
            inputSource = new InputSource(resource.getInputStream());  
            XmlDocument metadataXml = MappingReader.INSTANCE.readMappingDocument(entityResolver, inputSource,  
                    new OriginImpl("file", resource.getFilename()));  
            if (isDynamicStatementXml(metadataXml)) {  
                final Document doc = metadataXml.getDocumentTree();  
                final Element dynamicHibernateStatement = doc.getRootElement();  
                Iterator rootChildren = dynamicHibernateStatement.elementIterator();  
                while (rootChildren.hasNext()) {  
                    final Element element = (Element) rootChildren.next();  
                    final String elementName = element.getName();  
                    if ("sql-query".equals(elementName)) {  
                        putStatementToCacheMap(resource, element, namedSQLQueries);  
                    } else if ("hql-query".equals(elementName)) {  
                        putStatementToCacheMap(resource, element, namedHQLQueries);  
                    }  
                }  
            }  
        } catch (Exception e) {   
        	LOGGER.error("", e);
        	throw new LoyException("system_error"); 
        } finally {  
            if (inputSource != null && inputSource.getByteStream() != null) {  
                try {  
                    inputSource.getByteStream().close();  
                } catch (IOException e) {   
                	LOGGER.error("解析ql文件出错", e);
                	throw new LoyException("system_error"); 
                }  
            }  
        }  
  
    }  
  
    private void putStatementToCacheMap(Resource resource, final Element element, Map<String, String> statementMap)  
            throws IOException {  
        String sqlQueryName = element.attribute("name").getText();  
        Validate.notEmpty(sqlQueryName);  
        if (nameCache.contains(sqlQueryName)) { 
        	LOGGER.error("重复的sql-query/hql-query语句定义在文件:" + resource.getURI() + "中，必须保证name的唯一.");
        	throw new LoyException("system_error"); 
        }  
        nameCache.add(sqlQueryName);  
        String queryText = element.getText();  
        statementMap.put(sqlQueryName, queryText);  
    }  
  
    private static boolean isDynamicStatementXml(XmlDocument xmlDocument) {  
        return "dynamic-ql-statement".equals(xmlDocument.getDocumentTree().getRootElement().getName());  
    }


	@Override
	public StatementTemplate get(String key) {
		return this.templateCache.get(key);
	}  
}  