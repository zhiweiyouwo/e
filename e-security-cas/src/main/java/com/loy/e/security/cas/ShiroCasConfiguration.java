package com.loy.e.security.cas;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.loy.e.security.pwd.service.LoyPasswordService;
import com.loy.e.security.service.LoyLogService;
import com.loy.e.security.service.SecurityUserService;
import com.loy.e.security.service.SystemKeyService;
import com.loy.e.security.service.UserSessionService;
import com.loy.e.security.vo.Permission;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@Configuration
@EnableConfigurationProperties(CasProperties.class)
public class ShiroCasConfiguration {

    @Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }

    @Bean
    public LoyPasswordService passwordService() {
        LoyPasswordService passwordService = new LoyPasswordService();
        return passwordService;
    }

    @Bean(name = "myShiroCasRealm")
    public LoyShiroCasRealm loyShiroCasRealm(EhCacheManager cacheManager) {
        LoyShiroCasRealm realm = new LoyShiroCasRealm();
        realm.setCacheManager(cacheManager);
        return realm;
    }

    @Bean
    UserSessionService userSessionService() {
        return new UserSessionServiceImpl();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
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
    public DefaultWebSecurityManager getDefaultWebSecurityManager(
            LoyShiroCasRealm loyShiroCasRealm) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(loyShiroCasRealm);
        dwsm.setCacheManager(getEhCacheManager());
        dwsm.setRememberMeManager(null);
        dwsm.setSubjectFactory(new CasSubjectFactory());
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
            CasProperties casProperties) {

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        filterChainDefinitionMap.putAll(casProperties.anons);

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
            LoyLogService loyLogService,
            SystemKeyService systemKeyService,
            CasProperties casProperties,
            ServerProperties serverProperties,
            MessageSource messageSource) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Locale locale = LocaleContextHolder.getLocale();
        String loginUrl = casProperties.getLoginUrl();
        if (locale != null) {
            String country = locale.getCountry();
            String language = locale.getLanguage();
            String lang = language + "_" + country;
            loginUrl = loginUrl + "&locale=" + lang;
        }
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        shiroFilterFactoryBean.setSuccessUrl(casProperties.successUrl);
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        Map<String, Filter> filters = new HashMap<>();

        CasFilter casFilter = new LoyCasFilter(casProperties, serverProperties,
                securityUserService);
        LoyPermissionsAuthorizationFilter permFilter = new LoyPermissionsAuthorizationFilter(
                loyLogService, systemKeyService, messageSource);
        filters.put("authc", casFilter);
        filters.put("perms", permFilter);

        shiroFilterFactoryBean.setFilters(filters);

        loadShiroFilterChain(shiroFilterFactoryBean, securityUserService, systemKeyService,
                casProperties);
        return shiroFilterFactoryBean;
    }

}