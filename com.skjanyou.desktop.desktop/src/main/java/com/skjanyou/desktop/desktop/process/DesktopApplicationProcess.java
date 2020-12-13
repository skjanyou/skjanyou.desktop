package com.skjanyou.desktop.desktop.process;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


import com.skjanyou.appl.AppAnnotationloader.ApiRoute;
import com.skjanyou.appl.AppAnnotationloader.util.AnnotationUtil;
import com.skjanyou.appl.AppUtil.LogUtil;
import com.skjanyou.appl.Application.application.ApplicationContext;
import com.skjanyou.appl.Application.application.ApplicationProcess;
import com.skjanyou.desktop.desktop.bean.Executor;
import com.skjanyou.desktop.desktop.bean.Menu;
import com.skjanyou.desktop.desktop.config.Config;

/**
 * 时间:2018年5月10日 23:23:53</br>
 * 作用:加载桌面插件,用于桌面菜单以及菜单功能</br>
 * @author skjanyou
 *
 */
public class DesktopApplicationProcess implements ApplicationProcess{
	private static final String PLUGINDIRTORY = "desktop";
	private static final String JAR = ".jar";
	private static final String PROPERTIES = ".view.properties";
	
	private static LogUtil log = new LogUtil(DesktopApplicationProcess.class);
	
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
	
	@Override
	public ApplicationContext<?> process() {
		log.debug("插件处理器启动,开始加载插件");
		
		List<Menu> allPlugin = new ArrayList<Menu>();
//		List<Menu> allResourcePlugin = readClassPathPlugin();
		List<Menu> allFilePlugin = readFilePlugin(PLUGINDIRTORY);
//		allPlugin.addAll(allResourcePlugin);
		allPlugin.addAll(allFilePlugin);

		log.debug("所有插件加载完成");
		return new ApplicationContext<List<Menu>>(allPlugin);
	}
	
	private List<Menu> readClassPathPlugin(){
		log.debug("加载classpath中的资源");
		
		List<Menu> resultList = new ArrayList<Menu>();
		String clasPath = DesktopApplicationProcess.class.getResource("/").getFile();
		File classPathFile = new File(clasPath);
		File[] pluginsFiles = classPathFile.listFiles();
		InputStream is = null;
		Properties properties = new Properties();
		boolean isMatch = false;
		try {
			for (File pluginsFile : pluginsFiles) {
				String name = pluginsFile.getName();
				if(name.endsWith(PROPERTIES)){
					is = DesktopApplicationProcess.class.getClassLoader().getResourceAsStream(name);
					properties.load(is);
					String action = properties.get("action").toString();
					String img = properties.get("img").toString();
					String onClick = properties.get("onClick").toString();
					String subClass = properties.get("subClass").toString();
					String title = properties.get("title").toString();
					Menu menu = new Menu();
					menu.setAction(action);
					menu.setImg(img);
					menu.setOnClick(onClick);
					Class<?> subClazz = Class.forName(subClass);
					menu.setSubClass(subClazz);
					
					List<Method> mList =  AnnotationUtil.getMethodByAnnotation(subClazz, ApiRoute.class);
					
					
					menu.setTitle(title);	
					resultList.add(menu);
					properties.clear();
				}			
			}	
		} catch (Exception e){
			log.error(e.getMessage(), e);
		} finally {
			if( is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		log.debug("加载classpath中的资源完成");
		return resultList;
	}
	
	/**
	 * 时间:2017年3月28日 15:42:48</br>
	 * 作用:加载插件目录plugins的所有插件文件jar里面的*.properties,</br>
	 *      并且将plugins目录下面的所有jar文件都添加进classpath目录</br>
	 */
	private List<Menu> readFilePlugin(String path){
		List<Menu> allPlugin = new ArrayList<Menu>();	
		File pluginsDirtory = new File(path);
		File[] pluginsFiles = pluginsDirtory.listFiles();
		for (File pluginsFile : pluginsFiles) {
			// jar文件
			if(pluginsFile.isFile() && pluginsFile.getName().endsWith(JAR)){
				addToUrl(pluginsFile);
				Menu plugin = readJarFile(pluginsFile);
				if(plugin != null){
					allPlugin.add(plugin);
				}
			}
		}
		return allPlugin;
	}
	
	private Menu readJarFile(File jarFile){
		log.debug(MessageFormat.format("开始加载插件:{0}", jarFile.getAbsolutePath()));
		Menu menu = new Menu();	//这里不为空
		InputStream is = null;
		try{
			Properties properties = new Properties();
			JarFile jar = new JarFile(jarFile);
			Enumeration<JarEntry> jarEntries = jar.entries();
			boolean isMatch = false;
			while(jarEntries.hasMoreElements()){	//寻找.properties,找到第一个就返回
				JarEntry jarEntry = jarEntries.nextElement();
				String name = jarEntry.getName();
				if(name.endsWith(PROPERTIES)){
					is = DesktopApplicationProcess.class.getClassLoader().getResourceAsStream(name);
					properties.load(is);
					isMatch = true;
					break;
				}
			}
			if(isMatch){
				String action = properties.get("action").toString();
				String img = properties.get("img").toString();
				String onClick = properties.get("onClick").toString();
				String subClass = properties.get("subClass").toString();
				String title = properties.get("title").toString();
				menu.setAction(action);
				menu.setImg(img);
				menu.setOnClick(onClick);
				menu.setSubClass(Class.forName(subClass));
				menu.setTitle(title);
				
				Class<?> subClazz = Class.forName(subClass);
				
				List<Method> mList =  AnnotationUtil.getMethodByAnnotation(subClazz, ApiRoute.class);
				mList.forEach( method -> {
					ApiRoute ar = AnnotationUtil.getMethodAnnotation(method, ApiRoute.class);
					String urlMapping = ar.value();
					Executor e = new Executor();
					e.setClazz(subClazz);
					e.setMethod(method);
					
					Config.urlMapping.put(urlMapping, e);
				});					
				
			}
			log.debug(MessageFormat.format("插件{0}加载成功!", jarFile.getAbsolutePath()));
		}catch(Exception e){
			String msg = MessageFormat.format("文件:{0}配置文件获取失败!", jarFile.getAbsolutePath());
			log.error(msg, e);
			return null;
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return menu;
	}
	
	private void addToUrl(File jarFile){
		try {
			method.invoke(classLoader, new Object[]{jarFile.toURI().toURL()});
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void destory() {
		System.out.println(this.getClass().getSimpleName());
	}

}
