package com.loy.e.security.service;

import com.loy.e.common.vo.SessionUser;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public interface UserSessionService {
	
	SessionUser getSessionUser();
	void setSessionUser(SessionUser sessionUser);
}
