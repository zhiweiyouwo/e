package com.loy;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.loy.e.core.conf.Settings;
import com.loy.e.core.repository.impl.DefaultRepositoryFactoryBean;


/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableAutoConfiguration()
@ComponentScan
@EnableConfigurationProperties(Settings.class)
@EnableJpaRepositories(repositoryFactoryBeanClass=DefaultRepositoryFactoryBean.class)
public class ApplicationMain extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer, CommandLineRunner {

	static final Log logger = LogFactory.getLog(ApplicationMain.class);
    public static void main(String[] args) throws Exception { 
        SpringApplication.run(ApplicationMain.class, args);
    }  
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
           return application.sources(ApplicationMain.class);
    }
    
    public void run(String... args) throws Exception {
    	
    	
	}
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(80);
	}

} 