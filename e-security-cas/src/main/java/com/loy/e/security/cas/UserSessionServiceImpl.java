package com.loy.e.security.cas;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.loy.e.common.Constants;
import com.loy.e.common.vo.SessionUser;
import com.loy.e.security.service.UserSessionService;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

public class UserSessionServiceImpl implements UserSessionService {

    @Override
    public SessionUser getSessionUser() {

        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            return (SessionUser) subject.getSession().getAttribute(Constants.SESSION_KEY);
        }
        return null;
    }

    @Override
    public void setSessionUser(SessionUser sessionUser) {
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute(Constants.SESSION_KEY, sessionUser);
    }

}
