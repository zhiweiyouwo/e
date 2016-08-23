package com.loy.e.core.conf;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.loy.e.core.ql.DynamicQlStatementBuilder;
import com.loy.e.core.ql.impl.DefaultDynamicQlStatementBuilder;

@Configuration
public class QlConfig {
	protected final Log LOGGER = LogFactory.getLog(QlConfig.class);
	
	@Bean  
	public DynamicQlStatementBuilder dynamicQlStatementBuilder(){
		DynamicQlStatementBuilder dynamicQlStatementBuilder = new DefaultDynamicQlStatementBuilder();
		try {
			dynamicQlStatementBuilder.init();
		} catch (IOException e) {
			LOGGER.error("解析QL错误", e);
		}
		return  dynamicQlStatementBuilder;
	}
}
