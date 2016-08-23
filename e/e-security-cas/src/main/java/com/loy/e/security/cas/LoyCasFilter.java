package com.loy.e.security.cas;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import com.loy.e.common.Constants;
import com.loy.e.common.vo.SessionUser;
import com.loy.e.security.service.SecurityUserService;
import com.loy.e.security.vo.User;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class LoyCasFilter extends CasFilter {
	
	@Autowired(required = false)
	CasProperties casProperties;
	@Autowired(required = false)
	ServerProperties serverProperties;
	@Autowired(required = false)
	SecurityUserService securityUserService;
    public LoyCasFilter(CasProperties casProperties,
    		ServerProperties serverProperties,
    		SecurityUserService securityUserService){
    	this.casProperties = casProperties;
    	this.serverProperties =serverProperties;
    	this.securityUserService = securityUserService;
    }
    public LoyCasFilter(){
    	
    }
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		SessionUser sessionUser = new SessionUser();
		String loginName = subject.getPrincipal().toString();
		User user = securityUserService.findByUsername(loginName);
		sessionUser.setUsername(loginName);
		sessionUser.setName(user.getName());
		sessionUser.setId(user.getId());
		sessionUser.setPhoto(user.getPhoto());
		subject.getSession().setTimeout(30 * 60 * 1000);
		subject.getSession().setAttribute(Constants.SESSION_KEY, sessionUser);
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		String uri = httpServletRequest.getRequestURI();
		String temp = serverProperties.getContextPath()+casProperties.casFilterUrlPattern;
		String sessionId = subject.getSession().getId().toString();
		if(uri.equals(temp)){
			 HttpServletResponse httpServletResponse = (HttpServletResponse)response;
			 Cookie cookie = new Cookie("JSESSIONID", sessionId); 
			 httpServletResponse.addCookie(cookie);
			issueSuccessRedirect(request, response);
			return false;
		}else{
			return true;
		}
	}


	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		String ticket = request.getParameter("ticket");
		if(ticket != null){
			request.setAttribute("ticket", ticket);
			return super.executeLogin(request, response);
		}
		HttpGet httpGet = new HttpGet(casProperties.getLoginUrl());
		HttpServletRequest req = (HttpServletRequest) request;
		Cookie[] cookies = req.getCookies();
		List<String> cookiess = new ArrayList<String>();
		boolean hasCASTGC = false;
		String casTGC = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				String name = c.getName();
				String value = c.getValue();
				value = name + "=" + value;
				cookiess.add(value);
				if (name.equals("CASTGC")) {
					hasCASTGC = true;
					casTGC = c.getValue();
				}
			}
		}
		if (!hasCASTGC) {
			this.redirectToLogin(request, response);
			return false;
		}
		
		String cookiesStr = StringUtils.join(cookiess, ";");
		if (StringUtils.isNotBlank(cookiesStr)) {
			httpGet.addHeader(new BasicHeader("Cookie", cookiesStr));
		}
		CloseableHttpClient httpClient = HttpClients.createMinimal();
		HttpResponse httpResponse = httpClient.execute(httpGet);
		StatusLine statusLine = httpResponse.getStatusLine();
		if (statusLine.getStatusCode() == 302) {
			Header locationHeader = httpResponse.getLastHeader(HttpHeaders.LOCATION);
			String locStr = locationHeader.getValue();
			int index = locStr.indexOf('?') + 1;
			ticket = locStr.substring(index, locStr.length());
			String[] tickets = ticket.split("=");
			if(tickets[0].equals("ticket")){
				request.setAttribute(tickets[0], tickets[1]);
			}else{
				 HttpServletResponse httpServletResponse = (HttpServletResponse)response;
				 Cookie cookie = new Cookie("CASTGC", casTGC); 
				 httpServletResponse.addCookie(cookie);
				 WebUtils.issueRedirect(request, response, casProperties.getLoginUrl());
				return false;
			}

		} else if (statusLine.getStatusCode() == 200) {
			this.redirectToLogin(request, response);
			return false;
		}
		return super.executeLogin(request, response);
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String ticket = (String) httpRequest.getAttribute("ticket");
		return new CasToken(ticket);
	}

	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		Subject subject = getSubject(request, response);
		return subject.isAuthenticated();
	}

	
}
