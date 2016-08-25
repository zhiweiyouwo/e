package com.loy.cas;

import org.jasig.cas.authentication.handler.PasswordEncoder;

import com.loy.e.security.pwd.service.LoyPasswordService;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

public class LoyPasswordEncoder implements PasswordEncoder {

    private LoyPasswordService passwordService;

    public void setPasswordService(LoyPasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    public String encode(String plaintext) {
        return passwordService.encryptPassword(plaintext);
    }

}
