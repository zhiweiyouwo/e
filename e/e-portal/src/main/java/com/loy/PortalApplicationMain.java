package com.loy;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
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
@ComponentScan(basePackages={"com.loy"})
@EnableConfigurationProperties(Settings.class)
@EnableJpaRepositories(repositoryFactoryBeanClass=DefaultRepositoryFactoryBean.class)

@ImportResource({"classpath:applicationConsumer.xml"})
public class PortalApplicationMain {

	static final Log logger = LogFactory.getLog(PortalApplicationMain.class);
    public static void main(String[] args) throws Exception { 
        SpringApplication.run(PortalApplicationMain.class, args);
    }
	
} 