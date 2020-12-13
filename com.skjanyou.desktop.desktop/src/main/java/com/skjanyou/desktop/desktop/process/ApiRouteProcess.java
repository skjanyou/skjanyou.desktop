package com.skjanyou.desktop.desktop.process;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;




import tuuzed.lib.microhttpd.MicroHTTPD;
import tuuzed.lib.microhttpd.core.HttpResponses;
import tuuzed.lib.microhttpd.core.RouteHttpRequestDispatcher;

import com.skjanyou.appl.AppUtil.LogUtil;
import com.skjanyou.appl.Application.application.ApplicationContext;
import com.skjanyou.appl.Application.application.ApplicationProcess;
import com.skjanyou.desktop.desktop.config.Config;
import com.skjanyou.desktop.desktop.route.UrlRoute;

public class ApiRouteProcess implements ApplicationProcess {

	private static LogUtil log = new LogUtil(ApiRouteProcess.class);
	private static MicroHTTPD httpd = null;
	@Override
	public ApplicationContext<?> process() {
		log.info("添加Api路由开始.");

		RouteHttpRequestDispatcher dispatcher = new RouteHttpRequestDispatcher()
		// 	获取所有菜单
		.addHandler(Pattern.compile("^/menu/"), req -> {
			return UrlRoute.menu(req);
		})
		// 	JSON类请求
		.addHandler(Pattern.compile("^/json/"), req -> {
			return UrlRoute.json(req);
		//	文件类操作,获取文件	
		}).addHandler(Pattern.compile("^/file/"), req -> {
			return UrlRoute.fileRequest();
		})
		//	静态文件请求 css、js、html、image等资源
		.addHandler(Pattern.compile("^/static/"), req -> {
			return UrlRoute.staticResource(req);		
		})		
		.addHandler(Pattern.compile("^/redirect/"), req -> {
			return HttpResponses.redirect_301("//www.baidu.com");
		}).addHandler(Pattern.compile("^/forward/"), req -> {
			return HttpResponses.redirect_301("/mimetypes");
		});
		httpd = MicroHTTPD.builder()
		.setListenPort(2726)
		.addHttpRequestDispatcher(dispatcher)
		//.useStaticFileHttpRequestDispatcher("/static/", new File("/skjanyou/workdir/superms-ui"))
		.setTimeout(1000, TimeUnit.MILLISECONDS)
		.build();

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				httpd.start();	
			}
		};
		
		Config.threadPool.submit(runnable);
		return new ApplicationContext<MicroHTTPD>(httpd);
	}
	public void destory() {
		if(httpd != null){
			httpd.stop();
		}
	}

}
