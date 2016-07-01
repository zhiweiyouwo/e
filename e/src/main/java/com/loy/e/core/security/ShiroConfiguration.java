package com.loy.e.core.security;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.loy.e.core.log.LoyLogService;
import com.loy.e.sys.domain.entity.ResourceEntity;
import com.loy.e.sys.repository.ResourceRepository;  
 
  
/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@Configuration  
public class ShiroConfiguration implements ApplicationContextAware {  
  
    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();  
    private static ShiroFilterFactoryBean shiroFilterFactoryBean = null;
    ApplicationContext applicationContext = null;
   
    
    public ShiroConfiguration(){
    	
    }
    
    @Bean  
    public LoyPasswordService passwordService() {  
    	LoyPasswordService passwordService = new LoyPasswordService(); 
        return passwordService;  
    }  
    
    @Bean 
    public ShiroRealmImpl shiroRealm() {

    	PasswordMatcher passwordMatcher = new PasswordMatcher();
    	passwordMatcher.setPasswordService(passwordService());
        return new ShiroRealmImpl(shiroEhcacheManager(),passwordMatcher);  
    } 
   
    @Bean 
    public EhCacheManager shiroEhcacheManager() {  
        EhCacheManager em = new EhCacheManager();  
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");  
        return em;  
    }  
  
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {  
        return new LifecycleBeanPostProcessor();  
    }  
  
    @Bean  
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {  
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();  
        daap.setProxyTargetClass(true);  
        return daap;  
    }  
  
    @Bean
    public DefaultWebSecurityManager securityManager() {  
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();  
        dwsm.setRealm(shiroRealm());  
        dwsm.setCacheManager(shiroEhcacheManager()); 
        dwsm.setRememberMeManager(null);
        return dwsm;  
    }  
    
    @Bean  
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {  
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();  
        aasa.setSecurityManager(securityManager());  
        return new AuthorizationAttributeSourceAdvisor();  
    }  
  
    @Bean(name = "shiroFilter")  
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {  
        shiroFilterFactoryBean = new ShiroFilterFactoryBean();  
        shiroFilterFactoryBean.setSecurityManager(securityManager());  
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        LoyLogService loyLogService = (LoyLogService)applicationContext.getBean("loyLogService");
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter(loyLogService);
        filters.put("authc", formAuthenticationFilter);
        
        PermissionsAuthorizationFilter permissionsAuthorizationFilter = new PermissionsAuthorizationFilter(loyLogService);    
        MessageSource messageSource = (MessageSource)applicationContext.getBean("messageSource");
        permissionsAuthorizationFilter.messageSource = messageSource;
        filters.put("perms", permissionsAuthorizationFilter);
        
       
        
   	    //filterChainDefinitionMap.put("/user/save", "perms[user:save]");  //perms
        
        shiroFilterFactoryBean.setLoginUrl("/index.html");  
        shiroFilterFactoryBean.setSuccessUrl("/home.html"); 
        filterChainDefinitionMap.put("/weixin**", "anon");
        filterChainDefinitionMap.put("/i18n/**", "anon");
        filterChainDefinitionMap.put("/**.html", "anon");
        filterChainDefinitionMap.put("/**.css", "anon");
        filterChainDefinitionMap.put("/**.js", "anon");
        filterChainDefinitionMap.put("/**.map", "anon");
        filterChainDefinitionMap.put("/**.woff", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/metrics", "anon");
        //filterChainDefinitionMap.put("/metrics**", "anon");
        ResourceRepository resourceRepository = (ResourceRepository)applicationContext.getBean("resourceRepository");
        List<ResourceEntity> resources = resourceRepository.findAll();
		if(resources != null){
			for(ResourceEntity r : resources){
				String accessCode = r.getAccessCode();
				String url = r.getUrl();
				if(StringUtils.isNotEmpty(accessCode) && StringUtils.isNotEmpty(url)){
					filterChainDefinitionMap.put("/"+r.getUrl(), "perms["+accessCode+"]"); 
				}
			}
		}
        filterChainDefinitionMap.put("/**", "authc");  //authc
        
        
        //filterChainDefinitionMap.put("/user/update", "perms"); 
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);  
        return shiroFilterFactoryBean;  
    }  
  /**
   * anon	org.apache.shiro.web.filter.authc.AnonymousFilter
authc	org.apache.shiro.web.filter.authc.FormAuthenticationFilter
authcBasic	
org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
perms	
org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
port	
org.apache.shiro.web.filter.authz.PortFilter
rest	
org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
roles	
org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
ssl	org.apache.shiro.web.filter.authz.SslFilter
user	org.apache.shiro.web.filter.authc.UserFilter
   */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
	}
}  