package com.loy.e.core.annotation;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public @interface LoyColumn {

	boolean list() default true;
	boolean edit() default true;
	boolean detail() default true;
	String name() default "";
	String inputType() default "text";
	ConditionParam condition(); 
}
