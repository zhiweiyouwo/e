package com.loy.e.core.web.dispatch;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import com.loy.e.common.vo.ErrorResponseData;
import com.loy.e.common.vo.SessionUser;
import com.loy.e.core.conf.Settings;
import com.loy.e.core.util.JsonUtil;
import com.loy.e.core.util.RequestUtil;
import com.loy.e.security.service.UserSessionService;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@SuppressWarnings("serial")
public class DefaultDispatchServlet extends DispatcherServlet{
	
	@Autowired
	private Settings settings;
	 
	@Autowired
	UserSessionService userSessionService;
	@Override
	protected LocaleContext buildLocaleContext(final HttpServletRequest request) {
		return new LocaleContext(){
			@Override
			public Locale getLocale() {
				
				SessionUser currentUser = userSessionService.getSessionUser();
				Locale locale = null;
				if(currentUser != null){
					locale = currentUser.getLocale();
				}
				if(locale == null){
					locale = request.getLocale();
				}
				if(locale != null){
					String c = locale.getCountry();
					String l = locale.getLanguage();
					c = l+"_"+c;
					boolean support = false;
					for(String lang : settings.supportLocales){
						if(lang.equals(c)){
							support = true;
							break;
						}
					}
					if(support){
						return locale;
					}
				}
				
				String[] temp = settings.defaultLocale.split("_");
				locale = new Locale(temp[0], temp[1]);
				if(currentUser != null){
					currentUser.setLocale(locale);
					userSessionService.setSessionUser(currentUser);
				}
				return  locale;
			}};
	}
	
	protected ModelAndView processHandlerException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) throws Exception {
		if(RequestUtil.isJson(request) && ex instanceof ServletException){
			ErrorResponseData error = new ErrorResponseData();
			error.setMsg(ex.getMessage());
			String errorJson = JsonUtil.json(error);
			response.getWriter().print(errorJson);
			return null;
		}else{
			return super.processHandlerException(request, response, handler, ex);
		}	
	}
	
	
}
