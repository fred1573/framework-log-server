package com.tomato.framework.log.processor;


import com.alibaba.fastjson.JSON;
import com.tomato.framework.log.annotation.Log;
import com.tomato.framework.log.annotation.LogModule;
import com.tomato.framework.log.model.SysLog;
import com.tomato.framework.log.util.SystemConfig;
import com.tomato.framework.log.util.UserInfoContext;
import com.tomato.mq.client.produce.MQProducer;
import com.tomato.mq.client.support.MQClientBuilder;
import com.tomato.mq.support.core.TextMessage;
import com.tomato.mq.support.message.MessageType;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author Hunhun
 *
 * 下午3:55:09
 */
@Aspect
public class LogProcessor {

	@Around("execution(* *.*(..)) && @annotation(com.tomato.framework.log.annotation.Log)")
	public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {
		Object result = pjp.proceed();
		Class clazz = pjp.getTarget().getClass();
		String methodName = pjp.getSignature().getName();
		Class[] parameterTypes = ((MethodSignature)pjp.getSignature()).getMethod().getParameterTypes();
		Method method = clazz.getMethod(methodName, parameterTypes);
		Log log = method.getAnnotation(Log.class);
		if(log == null){
			return result;
		}
		String logInfo;
		MQProducer producer = MQClientBuilder.build();
		TextMessage textMessage;
		String module = StringUtils.EMPTY;
		LogModule logModule = (LogModule) clazz.getAnnotation(LogModule.class);
		if (logModule != null && StringUtils.isNotBlank(logModule.value())) {
			module = logModule.value();
		} else if(StringUtils.isNotBlank(log.module())){
			module = log.module();
		}
		SysLog vo = new SysLog(SystemConfig.PROPERTIES.get(SystemConfig.PROJECT), module, log.descr(), clazz.getCanonicalName(),
				pjp.getSignature().toString(), UserInfoContext.currentUserID(), log.extension());
		logInfo = JSON.toJSONString(vo);
		System.out.println(logInfo);
		System.out.println(System.getProperty("mq.profiles.active"));
		if (log.before()) {
			textMessage = new TextMessage(logInfo, MessageType.SYS_LOG, "SYS_LOG_CLIENT");
			producer.send(textMessage);
		}else{
			textMessage = new TextMessage(logInfo, MessageType.SYS_LOG, "SYS_LOG_CLIENT");
			producer.send(textMessage);
		}
		return result;
	}

}
