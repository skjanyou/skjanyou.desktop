package com.skjanyou.desktop.desktop.core;

import java.util.Properties;

import com.skjanyou.desktop.desktop.utils.SystemUtil;

public final class EnvironmentFactory {
	private static final String ENV_DEV = "dev";
	private static final String ENV_TEST = "test";
	private static final String ENV_RELEASE = "release";
	
	public static Environment getInstance (){
		Properties properties = SystemUtil.getSystemProperties();
		String env = properties.getProperty("env");
		Environment environment = null;
		switch (env) {
		case ENV_DEV:
			environment = new DevEnvironment();
			break;
		case ENV_TEST:
			environment = new TestEnvironment();
			break;
		case ENV_RELEASE:
			environment = new ReleaseEnvironment();
			break;
		default:
			break;
		}
		
		return environment;
	}
}
