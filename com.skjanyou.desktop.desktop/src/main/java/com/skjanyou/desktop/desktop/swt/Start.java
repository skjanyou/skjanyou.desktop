package com.skjanyou.desktop.desktop.swt;

import org.eclipse.swt.widgets.Display;

import com.skjanyou.desktop.desktop.config.Config;
import com.skjanyou.desktop.desktop.config.ShellManager;
import com.skjanyou.desktop.desktop.config.ShellView;
import com.skjanyou.desktop.desktop.process.ApiRouteProcess;
import com.skjanyou.desktop.desktop.process.ClasspathProcess;
import com.skjanyou.desktop.desktop.process.DevDesktopApplicationProcess;
import com.skjanyou.desktop.desktop.process.JavaFunctionRegistProcess;
import com.skjanyou.desktop.desktop.process.PropertiesLoaderProcess;

public class Start {
	public static void main(String args[]) {
		try {
			Config.application.put("PropertiesLoaderProcess", new PropertiesLoaderProcess())		//这个是系统的properties,必须放第一位,否则后面可能会有问题
							  .put("ClasspathProcess", new ClasspathProcess())
							  .put("DevDesktopApplicationProcess", new DevDesktopApplicationProcess())	//插件处理器
							  .put("JavaFunctionRegistProcess", new JavaFunctionRegistProcess())
							  .put("ApiRouteProcess", new ApiRouteProcess())
							//...其他处理器
							  .execute();
			

			Config.threadPool.submit(new Runnable() {
				@Override
				public void run() {
					ShellView.display = Display.getDefault();
					ShellView.initShell = new InitShell(ShellView.display);
					ShellView.mainShell = new MainShell(ShellView.display);
					
					ShellManager.regist(ShellView.initShell);
					ShellManager.regist(ShellView.mainShell);
					
					ShellManager.show(ShellView.mainShell);
					
					while (!ShellManager.stopFlag) {
						if (!ShellView.display.readAndDispatch()) {
							ShellView.display.sleep();
						}
					}
					Config.application.getApplicationProcesss().forEach((key,process)->{
						//process.destory();
					});
					
					Config.threadPool.shutdown();		//禁止再往线程池里面添加任务,程序会立即退出
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
