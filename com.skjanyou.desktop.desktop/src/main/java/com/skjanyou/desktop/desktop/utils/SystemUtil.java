package com.skjanyou.desktop.desktop.utils;

import java.util.Properties;

import com.skjanyou.desktop.desktop.config.Config;

public class SystemUtil {
	
	public static Properties getSystemProperties(){
		Properties properties = (Properties)Config.application.getApplicationContexts().get("PropertiesLoaderProcess").getContext();
		return properties;
	}
	
}
