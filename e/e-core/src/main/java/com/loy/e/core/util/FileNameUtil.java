package com.loy.e.core.util;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957 
 * @since 1.7
 * @version 1.0.0
 *
 */
public class FileNameUtil {

	public static String getFileSuffix(String fileName){
		if(fileName == null){
			return "";
		}
		int index = fileName.lastIndexOf(".");
		if(index>0){
			return fileName.substring(index,fileName.length());
		}else{
			return "";
		}
	}
	
}
