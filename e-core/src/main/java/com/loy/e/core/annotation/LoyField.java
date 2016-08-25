package com.loy.e.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.loy.e.core.query.Op;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Documented
public @interface LoyField {

    public String fieldName();

    public boolean detail() default true;

    public String description();

    public Op op() default Op.eq;

    public int count() default 1;

    public boolean often() default false;

    boolean sortable() default false;
}
