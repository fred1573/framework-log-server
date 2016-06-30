package com.tomato.framework.log.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Hunhun
 *
 * 下午5:08:51
 */
public class SystemConfig {

	public static final String PROJECT = "project";
	public static final String LOG_SERVER = "log_server";
	public static final Map<String, String> PROPERTIES = new HashMap<>();

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemConfig.class);
	static{
		Properties p = new Properties();
		try {
			InputStream appLogFile = SystemConfig.class.getResourceAsStream("/app_log.properties");
			if(appLogFile != null) {
				p.load(appLogFile);
			}
			String logEnv = System.getProperty("log.profiles.active");
			logEnv = StringUtils.isNotBlank(logEnv) ? logEnv : "production";
			p.load(SystemConfig.class.getResourceAsStream("/" + logEnv + "/log_server.properties"));
		} catch (IOException e) {
			LOGGER.error("framework-log:SystemConfig properties load error");
		}
		if(p.get(PROJECT) != null) {
			PROPERTIES.put(PROJECT, p.get(PROJECT).toString());
		}
		PROPERTIES.put(LOG_SERVER, p.get(LOG_SERVER).toString());
	}
	
}
