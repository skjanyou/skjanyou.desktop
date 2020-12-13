package com.skjanyou.desktop.desktop.process;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import com.skjanyou.appl.AppUtil.LogUtil;
import com.skjanyou.appl.Application.application.ApplicationContext;
import com.skjanyou.appl.Application.application.ApplicationProcess;

/**
 * 配置classpath. </br>
 * 1.在配置文件中配置的文件夹和路径. </br>
 * 2.desktop目录.	</br>
 * 3.plugins目录.	</br>
 * @author skjanyou
 * 时间 : 2018年6月24日
 * 作用 :
 */
public class ClasspathProcess implements ApplicationProcess {
	private static final String PLUGINSDIRTORY = "plugins";
	private static final String DESKTOPDIRTORY = "desktop";
	private static final String JAR = ".jar";
	private static LogUtil log = new LogUtil(ClasspathProcess.class);

	public ApplicationContext<?> process() {
		setClassPathByConfig();
		setClassPathByDesktop();
		setClassPathByPlugins();
		return null;
	}

	//自己配置的classpath
	private static void setClassPathByConfig(){
		//TODO
	}
	
	//应用级别classpath
	private static void setClassPathByDesktop(){
		//	
		File desktopDirtory = new File(DESKTOPDIRTORY);
		resolveFile(desktopDirtory);
	}
	
	//插件级别classpath
	private static void setClassPathByPlugins(){
		File pluginsDirtory = new File(PLUGINSDIRTORY);
		resolveFile(pluginsDirtory);		
	}
	
	public void destory() {

	}

	
	private static void resolveFile(File file){
		if(file.isDirectory()){
			File[] subFileList = file.listFiles();
			for (File subFile : subFileList) {
				resolveFile(subFile);
			}
		}else if(file.isFile() && file.getName().endsWith(JAR)){
			addToUrl(file);
		}
	}
	
	
	
	
	//以下部分为添加到classpath中的代码
	private static URLClassLoader classLoader = null;
	private static Method method = null;	
	static{
		try {
			classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
			method.setAccessible(true);
		} catch (SecurityException e) {
			log.error(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private static void addToUrl(File jarFile){
		try {
			method.invoke(classLoader, new Object[]{jarFile.toURI().toURL()});
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(), e);
		}
	}
}
