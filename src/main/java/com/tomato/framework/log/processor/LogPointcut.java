/**
 * 
 */
package com.tomato.framework.log.processor;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Hunhun
 *
 * 下午1:58:54
 */
@Aspect
public class LogPointcut {

	@Pointcut("@annotation(com.tomato.framework.log.annotation.Log)")
	public void log(){}
	
}
