package com.loy.e.security.cas;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jasig.cas.client.validation.Saml11TicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.loy.e.security.service.SecurityUserService;
import com.loy.e.security.service.SystemKeyService;
import com.loy.e.security.vo.Permission;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class LoyShiroCasRealm extends CasRealm {

    @Autowired
    SecurityUserService securityUserService;
    @Autowired
    SystemKeyService systemKeyService;
    @Autowired
    CasProperties casProperties;

    @PostConstruct
    public void initProperty() {
        setCasServerUrlPrefix(casProperties.casServerUrlPrefix);
        setCasService(casProperties.shiroServerUrlPrefix + casProperties.casFilterUrlPattern);
    }

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

    protected TicketValidator createTicketValidator() {
        String urlPrefix = getCasServerUrlPrefix();
        if ("saml".equalsIgnoreCase(getValidationProtocol())) {
            return new Saml11TicketValidator(urlPrefix);
        }
        if (urlPrefix.startsWith("https")) {
            if (this.casProperties.trustAllHttps) {
                LoyCas20ServiceTicketValidator cas20ServiceTicketValidator = new LoyCas20ServiceTicketValidator(
                        urlPrefix);
                cas20ServiceTicketValidator.setHostnameVerifier(new LoyHostnameVerifier());
                return cas20ServiceTicketValidator;
            }
        }
        return super.createTicketValidator();

    }
}