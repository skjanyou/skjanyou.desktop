package com.skjanyou.desktop.desktop.process;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.skjanyou.appl.AppAnnotationloader.util.AnnotationUtil;
import com.skjanyou.appl.AppUtil.LogUtil;
import com.skjanyou.appl.Application.application.ApplicationContext;
import com.skjanyou.appl.Application.application.ApplicationProcess;
import com.skjanyou.desktop.desktop.bean.Executor;
import com.skjanyou.desktop.desktop.bean.Menu;
import com.skjanyou.desktop.desktop.config.Config;
import com.skjanyou.desktop.desktop.utils.ScanUtil;

/**
 * 开发环境 </br>
 * 时间:2018年5月10日 23:23:53</br>
 * 作用:加载桌面插件,用于桌面菜单以及菜单功能</br>
 * @author skjanyou
 *
 */
public class DevDesktopApplicationProcess implements ApplicationProcess{
	//应用配置文件
	private static final String VIEWPROPERTIES = ".*\\.view\\.properties";
	private static LogUtil log = new LogUtil(DevDesktopApplicationProcess.class);
	
	@Override
	public ApplicationContext<?> process() {
		log.debug("应用配置处理器启动,开始加载应用");
		List<Menu> allPlugin = new ArrayList<Menu>();
		List<Menu> allResourcePlugin = readClassPathPlugin();
//		allPlugin.addAll(allResourcePlugin);
		allPlugin.addAll(allResourcePlugin);
		log.debug("所有应用加载完成");
		return new ApplicationContext<List<Menu>>(allPlugin);
	}
	
	private List<Menu> readClassPathPlugin(){
		log.debug("加载classpath中的应用配置");
		
		List<Menu> resultList = new ArrayList<Menu>();
		InputStream is = null;
		Properties properties = new Properties();
		try {
			//扫描classpath下面的*.view.properties文件
			Set<String> viewProperties = ScanUtil.scanResources(ScanUtil.ROOT, VIEWPROPERTIES, ScanUtil.ALL, ScanUtil.NONE);
			
			for(String p : viewProperties){
				URL url = DevDesktopApplicationProcess.class.getClassLoader().getResource(p);
				String file = URLDecoder.decode(url.getFile(), "UTF-8");
				File pluginsFile = new File(file);
				String name = pluginsFile.getName();
				is = DevDesktopApplicationProcess.class.getClassLoader().getResourceAsStream(name);
				properties.load(is);
				String action = properties.getProperty("action");
				String img = properties.getProperty("img");
				String onClick = properties.getProperty("onClick");
				String subClass = properties.getProperty("subClass");
				String title = properties.getProperty("title");
				Menu menu = new Menu();
				menu.setAction(action);
				menu.setImg(img);
				menu.setOnClick(onClick);
				Class<?> subClazz = Class.forName(subClass);
				menu.setSubClass(subClazz);
				menu.setTitle(title);
				
				List<Method> mList =  AnnotationUtil.getMethodByAnnotation(subClazz, com.skjanyou.appl.AppAnnotationloader.ApiRoute.class);
				mList.forEach( method -> {
					com.skjanyou.appl.AppAnnotationloader.ApiRoute ar = AnnotationUtil.getMethodAnnotation(method, com.skjanyou.appl.AppAnnotationloader.ApiRoute.class);
					String urlMapping = ar.value();
					Executor e = new Executor();
					e.setClazz(subClazz);
					e.setMethod(method);
					
					Config.urlMapping.put(urlMapping, e);
				});					
				
				resultList.add(menu);
				properties.clear();
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
		
		log.debug("加载classpath中的应用配置完成");
		return resultList;
	}
	
	
	public void destory() {
		System.out.println(this.getClass().getSimpleName());
	}

}
