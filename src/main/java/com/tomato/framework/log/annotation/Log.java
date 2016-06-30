/**
 * 
 */
package com.tomato.framework.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang.StringUtils;

/**
 * @author Hunhun
 *
 * 上午10:54:26
 */
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)  
public @interface Log {

	/**
	 * ignore if @LogModule.value() is not nulls
	 */
	String module() default StringUtils.EMPTY; 
	
	/**
	 * what the log record in business
	 */
	String descr();
	
	/**
	 * whether record before method 
	 */
	boolean before() default true;
	
	/**
	 * special params, only json format util
	 */
	String extension() default StringUtils.EMPTY;
	
}
