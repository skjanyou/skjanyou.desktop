package com.skjanyou.desktop.desktop.javafunction;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.browser.Browser;

import com.alibaba.fastjson.JSONObject;
import com.skjanyou.appl.AppAnnotationloader.util.AnnotationUtil;
import com.skjanyou.desktop.desktop.bean.Executor;
import com.skjanyou.desktop.desktop.swt.event.ShellEvent;
import com.skjanyou.desktop.desktop.swt.event.ShellFunction;

public class DistributeFunction extends JavaFunction {
	private Map<String,Executor> eventMap = new HashMap<String, Executor>();
	
	public DistributeFunction(){
		List<Method> listMethod = AnnotationUtil.getMethodByAnnotation(ShellEvent.class, ShellFunction.class);
		listMethod.forEach( method -> {
			Executor e = new Executor();
			e.setMethod(method);
			e.setClazz(ShellEvent.class);
			
			ShellFunction sf = AnnotationUtil.getMethodAnnotation(method, ShellFunction.class);
			eventMap.put(sf.value(), e);
		});
	}

	@Override
	public Object function(JSONObject jsonObj, Browser browser) {
		String code = jsonObj.getJSONObject("sys").getString("code");
		Executor e = null;
		if((e = eventMap.get(code)) != null){
			e.doSomeThing(browser);
		}
		
		return null;
	}

}
