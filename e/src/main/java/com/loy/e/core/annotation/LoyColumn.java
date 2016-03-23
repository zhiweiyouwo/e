package com.loy.e.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.loy.e.tools.model.InputClazz;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target({ElementType.FIELD})
@Documented
public @interface LoyColumn {
	boolean list() default true;
	boolean edit() default true;
	boolean detail() default true;
	String name() default "";
	String column() default "";
	InputClazz inputType() default InputClazz.NONE;
	
	String[] lists() default {};//格式如{"age,年龄"}
	String[] details() default {};
}
