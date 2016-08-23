package com.loy.e.security.service;

import java.util.List;

import com.loy.e.common.tree.TreeNode;
import com.loy.e.security.vo.Permission;
import com.loy.e.security.vo.User;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@SuppressWarnings("rawtypes")
public interface SecurityUserService {

	User  findByUsername(String username);
	List<Permission> getAllPermissions();
	List<Permission> getAllPermissions(String systemCode);
	List<Permission> findPermissionsByUsername(String username,String systemCode);
	List<Permission> findPermissionsByUsername(String username);
	List<TreeNode>  getMenuByUsername(String userId,String systemCode,String lang);
	List<TreeNode>  getMenuByUsername(String userId,String lang);
}
