package com.loy.e.security.form;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.loy.e.security.service.SecurityUserService;
import com.loy.e.security.service.SystemKeyService;
import com.loy.e.security.vo.Permission;
import com.loy.e.security.vo.User;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    SecurityUserService securityUserService;
    @Autowired
    SystemKeyService systemKeyService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String loginName = (String) super.getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> permissions = new HashSet<String>();
        List<Permission> ps = securityUserService.findPermissionsByUsername(loginName,
                systemKeyService.getSystemCode());
        if (ps != null) {
            for (Permission r : ps) {
                String accessCode = r.getAccessCode();
                if (StringUtils.isNotEmpty(accessCode)) {
                    permissions.add(accessCode);
                }
            }
        }
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = securityUserService.findByUsername(username);
        if (user != null) {
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                    user.getUsername(), user.getPassword(), getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }
}
