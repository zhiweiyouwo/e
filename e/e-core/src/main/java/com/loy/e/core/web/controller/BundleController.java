package com.loy.e.core.web.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */

@RestController
@RequestMapping(value="/",method={RequestMethod.GET})

@ApiIgnore

public class BundleController {
	protected final Log LOGGER = LogFactory.getLog(BundleController.class); 
	@RequestMapping(value="**/i18n/**/**.properties")
	
	@ApiOperation(value="获取国际化资源",httpMethod="GET")
	public void file(HttpServletRequest request,HttpServletResponse response){
		String uri = request.getRequestURI();
		int begin = uri.indexOf("i18n");
		uri = uri.substring(begin, uri.length());
		try {
			response.setCharacterEncoding("UTF-8");
			ClassPathResource classPathResource =  new ClassPathResource(uri); 
			BufferedReader br = new BufferedReader(new InputStreamReader(classPathResource.getInputStream(),"UTF-8"));
			FileCopyUtils.copy(br, response.getWriter());
		} catch (Exception e) {
			
		}
	}
}
