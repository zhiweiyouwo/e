package com.loy.springfox.conf;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(ApiInfoProperties.class)
public class SwaggerConfiguration {
	 @Bean
	    public Docket springfoxDocket(ApiInfoProperties apiInfo) {
	        Docket docket = new Docket(DocumentationType.SWAGGER_2); 
	        docket.ignoredParameterTypes(ApiIgnore.class);
	        docket.apiInfo(apiInfo(apiInfo));
		    docket.pathMapping("/").select().paths(regex("^.*(?<!error)$")).build();
	        return docket;
	 }
	 @Bean
	 ApiInfo apiInfo(ApiInfoProperties apiInfoProperties) {
		    Contact contact = apiInfoProperties.getContact();
		    ApiInfo apiInfo = new ApiInfo(
		    		apiInfoProperties.getTitle(),
		    		apiInfoProperties.getDescription(),
		    		apiInfoProperties.getVersion(),
		    		apiInfoProperties.getTermsOfServiceUrl(),
	                new springfox.documentation.service.Contact(contact.getName(), contact.getUrl(), contact.getEmail()),
	                apiInfoProperties.getLicense(),
	                apiInfoProperties.getLicenseUrl()
	        );

	        return apiInfo;
	}
}
