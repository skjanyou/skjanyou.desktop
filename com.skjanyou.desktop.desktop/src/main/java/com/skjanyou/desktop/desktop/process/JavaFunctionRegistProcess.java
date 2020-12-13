package com.skjanyou.desktop.desktop.process;

import com.skjanyou.appl.Application.application.ApplicationContext;
import com.skjanyou.appl.Application.application.ApplicationProcess;
import com.skjanyou.desktop.desktop.config.JavaFunctionManager;
import com.skjanyou.desktop.desktop.javafunction.DistributeFunction;

public class JavaFunctionRegistProcess implements ApplicationProcess {

	@Override
	public ApplicationContext<?> process() {
		JavaFunctionManager.regist(new DistributeFunction());
		return null;
	}

	public void destory() {
		System.out.println("destory");
	}

}
