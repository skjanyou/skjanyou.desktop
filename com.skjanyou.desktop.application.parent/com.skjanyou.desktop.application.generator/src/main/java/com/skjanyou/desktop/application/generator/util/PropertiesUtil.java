package com.skjanyou.desktop.application.generator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtil {
	private static Properties properties = new Properties();
	private static PropertiesUtil pu = new PropertiesUtil();
	
	private PropertiesUtil(){}
	
	public static PropertiesUtil loadPropertiesByResource(String name){
		properties.clear();
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream(name);
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(is != null){
					is.close();
				}
			} catch (IOException e) {
			}
		}
		return pu;
	}
	
	public static PropertiesUtil loadProperties(String file){
		FileInputStream fis = null;
		properties.clear();
		try {
			fis = new FileInputStream(new File(file));
			properties.load(fis);
		} catch (Exception e) {
			throw new RuntimeException("读取配置文件出错");
		}finally{
			try {
				fis.close();
			} catch (IOException e) {
			}
		}
		return pu;
	}
	
	
	public static Properties getProperties(){
		return properties;
	}
	
//	public Map<String,String> getMap(){
//		Map<String,String> map = new LinkedHashMap<String, String>();
//		List<Object> keys = properties.getKeyList();
//		for (Object object : keys) {
//			String key = object.toString();
//			String value = properties.getProperty(key);
//			map.put(key, value);
//		}
//		return map;
//	}
}
