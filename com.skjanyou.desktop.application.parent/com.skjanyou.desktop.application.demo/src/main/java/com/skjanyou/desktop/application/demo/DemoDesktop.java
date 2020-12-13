package com.skjanyou.desktop.application.demo;

import java.util.Map;

import com.skjanyou.appl.AppAnnotationloader.ApiRoute;
import com.skjanyou.appl.AppAnnotationloader.Resources;
import com.skjanyou.desktop.api.SkjanyouDesktop;

@Resources()
public class DemoDesktop implements SkjanyouDesktop {

	@Override
	public Map<String, String> invoke(Map<String, String> map) {
		return null;
	}
	
	@ApiRoute(value= "/say")
	public Object say(String o){
		System.out.println("我是子类,我输出父类传过来的信息:" + o.toString());
		
		return "我是子类,我传输信息给父类";
	}	
	
}
