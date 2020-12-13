package com.skjanyou.desktop.desktop.process;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.skjanyou.appl.AppUtil.LogUtil;
import com.skjanyou.appl.Application.application.ApplicationContext;
import com.skjanyou.appl.Application.application.ApplicationProcess;
import com.skjanyou.util.CommUtil;

/**
 * 时间:2017年3月27日 13:59:07</br>
 * 作用:加载.properties文件用的处理器</br>
 * @author skjanyou
 *
 */
public class PropertiesLoaderProcess implements ApplicationProcess {
	private static LogUtil log = new LogUtil(PropertiesLoaderProcess.class);
	private static final String DEFAULT_CHARSET = "UTF-8";
	@Override
	public ApplicationContext<? extends Object> process() {
		log.debug("开始加载系统配置文件");
		Properties properties = new Properties();
		InputStream is = null;
		try {
			is = PropertiesLoaderProcess.class.getClassLoader().getResourceAsStream("system.properties");
			properties.load(new InputStreamReader(is,DEFAULT_CHARSET));
			log.debug("系统配置文件加载成功");
		} catch (IOException e) {
			log.error("配置文件加载出错",e);
		} finally {
			CommUtil.close(is);
		}
		return new ApplicationContext<Properties>(properties);
	}
	public void destory() {
		log.info(this.getClass().getSimpleName() + " destory()");
	}

}
