package com.loy.e.core.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import com.loy.e.core.web.SimpleUser;
import com.loy.e.sys.domain.entity.ResourceEntity;
import com.loy.e.sys.domain.entity.UserEntity;
import com.loy.e.sys.repository.ResourceRepository;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class ShiroRealmImpl extends AuthorizingRealm{

	@Autowired
	UserSecurityService userSecurityService;
	@Autowired
	ResourceRepository resourceRepository;
	@Autowired
	PasswordService passwordService;
	public ShiroRealmImpl(CacheManager cacheManager) {
        super(cacheManager, null);
    }
	public ShiroRealmImpl(CacheManager cacheManager,CredentialsMatcher credentialsMatcher) {
        super(cacheManager, credentialsMatcher);
    }
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleUser simpleUser = (SimpleUser)principals.getPrimaryPrincipal();
		String username  = simpleUser.getUsername();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//authorizationInfo.setRoles(userService.findRoles(username));
		Set<String> permissions = new HashSet<String>();
		//permissions.add("user:save");
		List<ResourceEntity> resources = resourceRepository.findResourceByUsername(username);
		if(resources != null){
			for(ResourceEntity r : resources){
				String accessCode = r.getAccessCode();
				if(StringUtils.isNotEmpty(accessCode)){
					permissions.add(accessCode);
				}
			}
		}
		authorizationInfo.setStringPermissions(permissions);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken authtoken = (UsernamePasswordToken) token;
		String username = authtoken.getUsername();
		//交给 AuthenticatingRealm 使用 CredentialsMatcher 进行密码匹配
		UserEntity user = userSecurityService.findByUsername(username);
		if(user == null){
			return null;
		}
		
		SimpleUser simpleUser = new SimpleUser();
		simpleUser.setId(user.getId());
		simpleUser.setUsername(username);
		simpleUser.setName(user.getName());
		simpleUser.setLocale(LocaleContextHolder.getLocale());
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(simpleUser,user.getPassword(),getName());
		SecurityUtils.getSubject().getSession().setTimeout(30*60*1000L);
		return authenticationInfo;
	}

}