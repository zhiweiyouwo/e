package com.loy.app.common.service;

import java.io.IOException;

import com.loy.app.common.vo.UserDetail;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public interface MyProfileService {
	public void  upload(String username,byte[] avatar) throws IOException;

	public byte[] photo(String id) throws IOException;

	public UserDetail get(String username);

	public void update(UserDetail user);

	public void updatePassword(String username,String oldPassword, String newPassword);
}
