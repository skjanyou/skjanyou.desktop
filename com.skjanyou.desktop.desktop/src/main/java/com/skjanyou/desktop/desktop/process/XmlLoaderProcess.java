package com.skjanyou.desktop.desktop.process;

import java.util.HashMap;
import java.util.Map;

import com.skjanyou.appl.Application.application.ApplicationContext;
import com.skjanyou.appl.Application.application.ApplicationProcess;


/**
 * 时间:2017年3月27日 14:21:30</br>
 * 作用:加载.Xml文件用的处理器</br>
 * @author skjanyou
 *
 */
public class XmlLoaderProcess implements ApplicationProcess {

	@Override
	public ApplicationContext<? extends Object> process() {
		Map<String,Object> map = new HashMap<String,Object>();
		
		return new ApplicationContext<Map<String,Object>>(map);
	}

	public void destory() {
		System.out.println(this.getClass().getSimpleName());
	}

}
