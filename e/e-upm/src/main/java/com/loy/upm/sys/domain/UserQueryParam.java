package com.loy.upm.sys.domain;

import com.loy.e.core.annotation.ConditionParam;
import com.loy.e.core.annotation.Order;
import com.loy.e.core.query.Op;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class UserQueryParam {

    @ConditionParam(name = "username")
    @Order(name = "username")
    private String username;

    @ConditionParam(name = "name", op = Op.like)
    private String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}