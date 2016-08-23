package com.loy.e.core.converter;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.loy.e.common.util.Assert;
import com.loy.e.core.entity.Entity;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@SuppressWarnings("rawtypes")
public class DefaultPageConverter extends AbsPageConverter{
	protected final Log logger = LogFactory.getLog(DefaultPageConverter.class);
	@Override
	public Map convert(Object source) {
		if(source == null){
			return null;
		}
		Map<String,Object> target = new HashMap<String,Object>();
		try {
			 PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
             PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(source); 
             for (int i = 0; i < descriptors.length; i++) { 
                 String name = descriptors[i].getName(); 
                 if (!"class".equals(name)) { 
                	 Object childern = propertyUtilsBean.getNestedProperty(source, name);
                	 if(childern instanceof Entity){
                		 PropertyDescriptor[] childernDescriptors = propertyUtilsBean.getPropertyDescriptors(childern);
	                	 for (int j = 0; j < childernDescriptors.length; j++) { 
	    	                 String chilrenName = childernDescriptors[j].getName(); 
	    	                 if (!"class".equals(chilrenName)) { 
	    	                	 target.put(name+"_"+chilrenName, propertyUtilsBean.getNestedProperty(childern, chilrenName)); 
	    	                 }
	    	             } 
                	 }else{
                		 target.put(name, propertyUtilsBean.getNestedProperty(source, name)); 
                	 } 
                 }
             } 
		} catch (Throwable e) {
			logger.error(e);
			Assert.throwException();
		} 
		return target;
	}

}