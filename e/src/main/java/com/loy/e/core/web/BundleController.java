package com.loy.e.core.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */

@RestController
@RequestMapping(value="/",method={RequestMethod.POST,RequestMethod.GET})
public class BundleController {
	protected final Log LOGGER = LogFactory.getLog(BundleController.class); 
	@RequestMapping(value="/i18n/**")
	public void file(HttpServletRequest request,HttpServletResponse response){
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		uri = uri.replaceFirst(contextPath, "");
		uri = uri.replaceFirst("\\/", "");
		try {
			response.setCharacterEncoding("UTF-8");
			File file = ResourceUtils.getFile("classpath:"+uri);
			BufferedReader bf = new BufferedReader(new    InputStreamReader(new FileInputStream(file))); 
			FileCopyUtils.copy(bf, response.getWriter());
		} catch (Exception e) {
			//LOGGER.error("", e);
		}
	}
}
