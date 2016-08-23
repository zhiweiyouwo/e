package com.loy.e.security.cas;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class LoyHostnameVerifier implements HostnameVerifier{

	@Override
	public boolean verify(String urlHostName, SSLSession session) {
		return true;
	}

}
