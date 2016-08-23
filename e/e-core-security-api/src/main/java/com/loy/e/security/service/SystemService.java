package com.loy.e.security.service;

import java.util.List;
import com.loy.e.common.vo.System;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public interface SystemService {
	public List<System> getMySystem(String username);
}
