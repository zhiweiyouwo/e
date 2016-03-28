package com.loy.e.tools.util;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class ToolStringUtils {
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
	
	
	public static String getCombineFieldName(String fieldName){
		String[] temp = fieldName.split("\\.");
		for(int i=0;i<temp.length;i++){
			temp[i] = ToolStringUtils.firstCharUpper(temp[i]);
		}
		String s = StringUtils.join(temp);
		s = ToolStringUtils.firstCharLower(s);
		return s;
	}
}
