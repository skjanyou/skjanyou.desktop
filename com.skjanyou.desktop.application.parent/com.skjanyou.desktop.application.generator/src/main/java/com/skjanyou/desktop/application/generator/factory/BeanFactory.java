package com.skjanyou.desktop.application.generator.factory;

public abstract class BeanFactory {
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String clazz,Class<T> parent){
		T result = null;
		try {
			Class<T> cl = (Class<T>) Class.forName(clazz);
			result = cl.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}
}
