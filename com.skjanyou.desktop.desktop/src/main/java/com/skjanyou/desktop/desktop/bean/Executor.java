package com.skjanyou.desktop.desktop.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Executor {
	private Class<?> clazz;
	private Method method;
	
	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}




	public Object doSomeThing(Object o){
		Object result = null;
		
		try {
			Object sd = clazz.newInstance();
			result = method.invoke(sd, o);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return result;
	}

}
