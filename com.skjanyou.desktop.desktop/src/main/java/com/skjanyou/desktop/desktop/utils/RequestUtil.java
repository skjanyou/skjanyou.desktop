package com.skjanyou.desktop.desktop.utils;


import tuuzed.lib.microhttpd.HttpRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

public class RequestUtil {
	public static String getBody(HttpRequest request){
		String words = "";
		try {
			byte[] buff = request.body().bytes();
			words = new String(buff,"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return words;
	}
	
	public static JSONObject getBodyObject(HttpRequest request){
		String body = getBody(request);
		JSONObject jsonObject = JSONObject.parseObject(body);
		return jsonObject;
	}
	
	public static<T> T jsonStringtoJavaBean(String jsonString){
		T result = JSON.parseObject(jsonString, new TypeReference<T>(){});
		return result;
	} 
}
