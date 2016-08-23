package com.loy.e.core.conf;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.loy.e.core.aop.LoyAspect;
import com.loy.e.core.web.dispatch.DefaultDispatchServlet;


/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@Configuration 

public class WebConfig extends WebMvcConfigurerAdapter{  //WebMvcConfigurationSupport
	
	protected final Log LOGGER = LogFactory.getLog(WebConfig.class); 

	@Value("${spring.jackson.date-format}")
	private String dateFormat; 
	@Autowired
	private Settings settings;
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		for(HttpMessageConverter<?> c : converters){
			if(c instanceof MappingJackson2HttpMessageConverter){
				MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = (MappingJackson2HttpMessageConverter)c;
				ObjectMapper objectMapper= mappingJackson2HttpMessageConverter.getObjectMapper();
				objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
				if(this.dateFormat != null){
					DateFormat myDateFormat = new SimpleDateFormat(dateFormat);
					objectMapper.setDateFormat(myDateFormat);
				}
			}
		}
	}
	@Bean
    public SessionLocaleResolver localeResolver() {
		SessionLocaleResolver  localeResolver = new SessionLocaleResolver();
        return localeResolver;
    }
	
	@Bean
    public MessageSource messageSource() {
		
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        Set<String> basenamesList = new HashSet<String>();
        String[] fileNames = {"classpath*:/i18n/*.properties","classpath*:/i18n/**/*.properties"}; 
        ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
        
        for (String file : fileNames) {  
            try {
				Resource[] resources = resourceLoader.getResources(file);
				if(resources != null){
					for(Resource resource : resources){
						String filename = resource.getURI().toString();
						int begin = filename.indexOf("i18n");
						filename = filename.substring(begin, filename.length());
						filename = filename.replaceFirst(".properties", "");
						filename = "classpath:"+filename.split("_")[0];
						basenamesList.add(filename);
					}
				}
				
			} catch (IOException e) {
				LOGGER.error("i18n resource error", e);
			}  
        }  
        String[] basenames = new String[basenamesList.size()];
        basenamesList.toArray(basenames);
        messageSource.setResourceLoader(resourceLoader);
        messageSource.setBasenames(basenames);
        messageSource.setCacheSeconds(5);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
	
	@Bean
    public DefaultDispatchServlet dispatcherServlet() {
		DefaultDispatchServlet dispatchServlet = new DefaultDispatchServlet();
        return dispatchServlet;
    }
	
	@Bean
    public LoyAspect logAspect() {
         return new LoyAspect();
    }
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Map<String,List<String>> mappings = this.settings.getStaticMappings();
		if(mappings != null){
			for(Map.Entry<String, List<String>> e: mappings.entrySet()){
				List<String> list = e.getValue();
				if(list != null && !list.isEmpty()){
					String[] temp = new String[list.size()];
					list.toArray(temp);
					registry.addResourceHandler(e.getKey()).addResourceLocations(temp);
				}
			}
		}
	}
}
