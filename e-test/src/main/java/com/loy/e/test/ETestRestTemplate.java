package com.loy.e.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;

import com.loy.e.common.vo.LoginSuccessResponse;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

@Component
public class ETestRestTemplate extends TestRestTemplate {

    private String sessionId;
    private String CASTGC;
    private String loginUrl = "http://localhost:19090/upm/login";
    private String username = "admin";
    private String password = "123456";
    private String url="http://localhost:19090/upm";

    protected <T> T doExecute(URI url,
            HttpMethod method,
            RequestCallback requestCallback,
            ResponseExtractor<T> responseExtractor) throws RestClientException {

        Assert.notNull(loginUrl, "'url' must not be null");
        Assert.notNull(method, "'method' must not be null");
        ClientHttpResponse response = null;
        try {
            ClientHttpRequest request = createRequest(url, method);
            HttpHeaders httpHeaders = request.getHeaders();
            if (StringUtils.isNotEmpty(sessionId)) {
                httpHeaders.add("Cookie:", "JSESSIONID=" + this.sessionId);
            }
            if (StringUtils.isNotEmpty(CASTGC)) {
                httpHeaders.add("Cookie:", "CASTGC=" + this.CASTGC);
            }
            if (requestCallback != null) {
                requestCallback.doWithRequest(request);
            }
            response = request.execute();
            handleResponse(url, method, response);
            if (responseExtractor != null) {
                return responseExtractor.extractData(response);
            } else {
                return null;
            }
        } catch (IOException ex) {
            throw new ResourceAccessException("I/O error on " + method.name() +
                    " request for \"" + url + "\": " + ex.getMessage(), ex);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public void login() {
        try {
            Class.forName("com.loy.e.security.form.FormProperties");
        } catch (ClassNotFoundException e) {
            loginUrl = "http://localhost:18080/cas/login";
            try {
                this.loginCas();
            } catch (IOException e1) {
            }
            return;
        }

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("username", this.username);
        map.add("password", this.password);
        ResponseEntity<LoginSuccessResponse> result = postForEntity(loginUrl, map,
                LoginSuccessResponse.class);

        LoginSuccessResponse response = result.getBody();
        if (response.getSuccess()) {
            HttpHeaders httpHeaders = result.getHeaders();
            if (httpHeaders.containsKey("Set-Cookie")) {
                List<String> list = httpHeaders.get("Set-Cookie");
                for (String v : list) {
                    String[] arr = v.split(";");
                    for (String temp : arr) {
                        String[] ar = temp.split("=");
                        if (ar[0].equals("JSESSIONID")) {
                            setSessionId(ar[1]);
                            break;
                        }
                    }
                }
            }
        }
    }

    void loginCas() throws IOException {
        HttpGet httpGet = new HttpGet(loginUrl);
        CloseableHttpClient httpClient = HttpClients.createMinimal();
        HttpResponse httpResponse = httpClient.execute(httpGet);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpResponse.getEntity().getContent()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String content = response.toString();
        String formStart = "<form id=\"fm1\" action=\"/cas/login;";
        int start = content.indexOf(formStart) + formStart.length();
        int end = content.indexOf("\" method=\"post\">");
        String jsessionid = content.substring(start, end);

        String ltStart = "<input type=\"hidden\" name=\"lt\" value=\"";
        start = content.indexOf(ltStart) + ltStart.length();
        content = content.substring(start, content.length());

        end = content.indexOf("\" />");
        String ltValue = content.substring(0, end);

        HttpPost httpPost = new HttpPost(loginUrl + ";" + jsessionid);
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("username", username));
        list.add(new BasicNameValuePair("password", password));
        list.add(new BasicNameValuePair("lt", ltValue));
        list.add(new BasicNameValuePair("execution", "e1s1"));
        list.add(new BasicNameValuePair("submit", "LOGIN"));
        list.add(new BasicNameValuePair("_eventId", "submit"));

        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
        httpPost.setEntity(uefEntity);
        httpResponse = httpClient.execute(httpPost);

        Header[] headers = httpResponse.getHeaders("Set-Cookie");
        for (Header header : headers) {
            String values = header.getValue();
            String[] value = values.split(";");
            for (String v : value) {
                String[] kv = v.split("=");
                if (kv[0].equals("CASTGC")) {
                    CASTGC = kv[1];
                    break;
                }
            }
        }

    }
    protected void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

   

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
