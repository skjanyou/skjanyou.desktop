package com.skjanyou.desktop.desktop.route;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import tuuzed.lib.microhttpd.Headers;
import tuuzed.lib.microhttpd.HttpRequest;
import tuuzed.lib.microhttpd.HttpResponse;
import tuuzed.lib.microhttpd.RequestLine;
import tuuzed.lib.microhttpd.core.HttpResponses;
import tuuzed.lib.microhttpd.internal.MimeType;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skjanyou.desktop.desktop.bean.Executor;
import com.skjanyou.desktop.desktop.bean.Menu;
import com.skjanyou.desktop.desktop.config.Config;
import com.skjanyou.desktop.desktop.core.EnvironmentFactory;
import com.skjanyou.desktop.desktop.utils.RequestUtil;



public class UrlRoute {

	//静态资源
	public static final HttpResponse staticResource (HttpRequest req){
		HttpResponse response = null;
		//获取请求头部信息
		Headers headers = req.headers();
		String accept = headers.get("Accept");

		//获取响应正文
		RequestLine line = req.requestLine();
		String resourcePath = line.url();		
		
		InputStream is = EnvironmentFactory.getInstance().getInputStream(resourcePath);
		
		//页面
		if(accept.contains("text/html")){
			String result = new BufferedReader(new InputStreamReader(is))
			.lines().parallel().collect(Collectors.joining("\n"));
			response = HttpResponses.html(result);
		//css	
		}else if(accept.contains("text/css")){
			String result = new BufferedReader(new InputStreamReader(is))
			.lines().parallel().collect(Collectors.joining("\n"));
			response = HttpResponses.css(result);
		//js	
		}else if(accept.contains("*/*") ){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buff = new byte[4 * 1024];
			int len = -1;
			try {
				while((len = is.read(buff)) != -1){
					baos.write(buff, 0, len);
				}
				baos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte[] resultBytes = baos.toByteArray();
			int index = resourcePath.lastIndexOf(".");
			String minmeType = resourcePath.substring(index);
			String contentType = MimeType.getInstance().get(minmeType);
			response = HttpResponses.bytes(resultBytes, contentType);
		}
		
		return response;		
	}
	
	//普通请求
	public static final HttpResponse normalRequest () {
		return null;
	}
	
	//文件请求
	public static final HttpResponse fileRequest (){
		return null;
	}
	
	//获取其他网站资源
	public static final HttpResponse otherWebResource(){
		return null;
	}

	//JSON类请求
	@SuppressWarnings("unchecked")
	public static HttpResponse menu(HttpRequest req) {
		JSONArray jsonArray = new JSONArray();
		
		List<Menu> menus = (List<Menu>) Config.application.getApplicationContexts().get("DevDesktopApplicationProcess").getContext();
		
		menus.forEach( menu -> {
			JSONObject jsonObject = (JSONObject)JSON.toJSON(menu);
			jsonArray.add(jsonObject);
		});
		
		return HttpResponses.json(jsonArray.toString());
	}

	public static HttpResponse json(HttpRequest req) {
		String result = "{}";
		String url = req.requestLine().url();
		JSONObject jsonObj = RequestUtil.getBodyObject(req);
		url = url.split("json")[1];
		Executor e = Config.urlMapping.get(url);
		if(e != null){
			Object o = e.doSomeThing(jsonObj);
			if(o instanceof JSONObject){
				JSONObject resultJsonObject = (JSONObject) o;
				result = resultJsonObject.toString();
			}else if(o instanceof JSONArray){
				JSONArray resultJsonArray = (JSONArray) o;
				result = resultJsonArray.toString();
			}else if(o instanceof List){
				List<?> list = (List<?>) o;
				JSONArray resultJsonArray = (JSONArray) JSON.toJSON(list);
				result = resultJsonArray.toString();
			}else if(o instanceof Map){
				JSONObject resultJsonObject = (JSONObject) JSON.toJSON(o);
				result = resultJsonObject.toString();
			}else {
				JSONObject resultJsonObject = (JSONObject) JSON.toJSON(o);
				result = resultJsonObject.toString();
			}
		}
		return HttpResponses.json(result);
	}
	
}
