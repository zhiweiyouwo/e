package com.loy.e.security.cas;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class LoyTrustManager implements javax.net.ssl.TrustManager,javax.net.ssl.X509TrustManager{

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

}
