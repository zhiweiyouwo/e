package com.loy.e.security.cas;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class LoyCas20ServiceTicketValidator extends Cas20ServiceTicketValidator {

	public LoyCas20ServiceTicketValidator(String casServerUrlPrefix) {
		super(casServerUrlPrefix);
	}

	public Assertion validate(final String ticket, final String service) throws TicketValidationException {

		final String validationUrl = constructValidationUrl(ticket, service);
		if (log.isDebugEnabled()) {
			log.debug("Constructing validation url: " + validationUrl);
		}

		try {
			log.debug("Retrieving response from server.");
			try {
				trustAllHttpsCertificates();
			} catch (KeyManagementException e) {
				log.error(e);
				throw new TicketValidationException("The CAS KeyManagementException.");
			} catch (NoSuchAlgorithmException e) {
				log.error(e);
				throw new TicketValidationException("The CAS NoSuchAlgorithmException.");
			}
			final String serverResponse = retrieveResponseFromServer(new URL(validationUrl), ticket);

			if (serverResponse == null) {
				throw new TicketValidationException("The CAS server returned no response.");
			}

			if (log.isDebugEnabled()) {
				log.debug("Server response: " + serverResponse);
			}

			return parseResponseFromServer(serverResponse);
		} catch (final MalformedURLException e) {
			throw new TicketValidationException(e);
		}
	}

	private  void trustAllHttpsCertificates() throws NoSuchAlgorithmException, KeyManagementException {

		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new LoyTrustManager();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	}

}
