package com.loy.upm.personnel.domain;

import com.loy.e.core.annotation.ConditionParam;
import com.loy.e.core.annotation.Order;
import com.loy.e.core.query.Direction;
import com.loy.e.core.query.Op;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class EmployeeQueryParam {
    @ConditionParam(name = "employeeNo")
    @Order(direction = Direction.DESC, name = "employeeNo")
    private String employeeNo;

    @ConditionParam(name = "name", op = Op.like)
    private String name;

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}