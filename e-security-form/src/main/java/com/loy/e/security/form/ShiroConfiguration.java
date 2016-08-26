package com.loy.e.security.form;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.loy.e.security.pwd.service.LoyPasswordService;
import com.loy.e.security.service.LoyLogService;
import com.loy.e.security.service.SecurityUserService;
import com.loy.e.security.service.SystemKeyService;
import com.loy.e.security.service.UserSessionService;
import com.loy.e.security.vo.Permission;

@Configuration
@EnableConfigurationProperties(FormProperties.class)
public class ShiroConfiguration {

    @Bean
    public CacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }

    @Bean(name = "myShiroRealm")
    public MyShiroRealm myShiroRealm(CacheManager cacheManager) {
        MyShiroRealm realm = new MyShiroRealm();
        PasswordMatcher passwordMatcher = new PasswordMatcher();
        passwordMatcher.setPasswordService(passwordService());
        realm.setCredentialsMatcher(passwordMatcher);
        realm.setCacheManager(cacheManager);
        return realm;
    }

    @Bean
    PasswordService passwordService() {
        return new LoyPasswordService();
    }

    @Bean
    UserSessionService userSessionService() {
        return new UserSessionServiceImpl();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //filterRegistration.addInitParameter("targetFilterLifecycle", "false");
        filterRegistration.setEnabled(true);

        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(MyShiroRealm myShiroRealm) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        //dwsm.setSessionManager(sessionManager());
        dwsm.setRealm(myShiroRealm);
        dwsm.setCacheManager(getEhCacheManager());
        return dwsm;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean,
            SecurityUserService securityUserService,
            SystemKeyService systemKeyService,
            FormProperties formProperties) {

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        filterChainDefinitionMap.putAll(formProperties.anons);

        List<Permission> permissions = securityUserService
                .getAllPermissions(systemKeyService.getSystemCode());
        if (permissions != null) {
            for (Permission r : permissions) {
                String accessCode = r.getAccessCode();
                String url = r.getUrl();
                if (StringUtils.isNotEmpty(accessCode) && StringUtils.isNotEmpty(url)) {
                    filterChainDefinitionMap.put("/**/" + r.getUrl(), "perms[" + accessCode + "]");
                }
            }
        }

        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            DefaultWebSecurityManager securityManager,
            SecurityUserService securityUserService,
            SystemKeyService systemKeyService,
            FormProperties formProperties,
            LoyLogService loyLogService,
            MessageSource messageSource) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();

        LoyFormAuthenticationFilter formFilter = new LoyFormAuthenticationFilter();

        LoyPermissionsAuthorizationFilter permFilter = new LoyPermissionsAuthorizationFilter(
                loyLogService, systemKeyService, messageSource);

        filters.put("authc", formFilter);
        filters.put("perms", permFilter);
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(formProperties.loginUrl);
        shiroFilterFactoryBean.setSuccessUrl(formProperties.successUrl);
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        loadShiroFilterChain(shiroFilterFactoryBean, securityUserService, systemKeyService,
                formProperties);
        return shiroFilterFactoryBean;
    }

}