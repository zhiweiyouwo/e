package com.loy.e.core.security;

import org.apache.shiro.crypto.hash.format.HashFormat;
import org.apache.shiro.crypto.hash.format.HashFormatFactory;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class LoyHashFormatFactory implements HashFormatFactory{
	 public HashFormat getInstance(String token){
		 return new LoyHashFormat();
	 }
}