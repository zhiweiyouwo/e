package com.loy.e.core.security;

import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.format.HashFormat;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class LoyHashFormat implements HashFormat{

	@Override
	public String format(Hash hash) {
		 if (hash == null) {
	            return null;
	        }
	        return hash.toBase64();
	}
}