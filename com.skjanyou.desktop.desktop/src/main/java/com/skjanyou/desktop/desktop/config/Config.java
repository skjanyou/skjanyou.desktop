package com.skjanyou.desktop.desktop.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.skjanyou.appl.Application.application.Application;
import com.skjanyou.appl.Application.application.ApplicationManager;
import com.skjanyou.desktop.desktop.bean.Executor;


/**
 * @author skjanyou</br>
 * 时间:2017年3月27日 10:35:50
 * 作用:这个类改成默认的程序参数,在system.properties中没有获取的参数的情况下</br>
 * 会取这个类里面的参数
 * 
 * 
 * 时间:2015年10月28日 10:58:00</br>
 * 作用:这是一个配置类，在程序启动时应初始化，存储程序的一些参数</br>
 */

public abstract class Config {
	public static String APPLICATIONNAME = "DESKTOP";
	//程序所需要的参数在这个里面
	public static Application application = ApplicationManager.get(APPLICATIONNAME);
	//整个程序里面使用这个进程池
	public static ExecutorService threadPool = Executors.newCachedThreadPool();
	//URL Mapping
	public static Map<String,Executor> urlMapping = new HashMap<String, Executor>();
	
	/*公共属性*/
	//文件存放目录
	public static String dec = null;
	//多线程数量
	public static volatile int threadQuantity = 0;
	//连接等待时间
	public static int waitTime = 0; 
	//没有连接成功时，展示的图片路径
	public static String nofind = null;
    //程序图标
	public static String pIcon = null;
	//托盘图标
	public static String trayIcon = null;
	//背景图片
	public static String background = null;
	//退出方式
	public static String exitType = null;
	
	
}
