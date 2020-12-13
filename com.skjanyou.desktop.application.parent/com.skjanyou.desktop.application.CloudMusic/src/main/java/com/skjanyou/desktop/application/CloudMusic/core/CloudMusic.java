package com.skjanyou.desktop.application.CloudMusic.core;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSONObject;
import com.skjanyou.desktop.application.CloudMusic.netease.Api;
import com.skjanyou.desktop.application.CloudMusic.netease.UrlParamPair;
import com.skjanyou.desktop.application.CloudMusic.secret.JSSecret;

public class CloudMusic {
	public static void searchBySonger(String songer){
        UrlParamPair upp = Api.SearchMusicList(songer,"1");
        String req_str = upp.getParas().toJSONString();
        String body = "{}";
        try{
            Connection.Response response = Jsoup.connect("http://music.163.com/weapi/cloudsearch/get/web?csrf_token=")
            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:57.0) Gecko/20100101 Firefox/57.0")
            .header("Accept", "*/*")
            .header("Cache-Control", "no-cache")
            .header("Connection", "keep-alive")
            .header("Host", "music.163.com")
            .header("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
            .header("DNT", "1")
            .header("Pragma", "no-cache")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .data(JSSecret.getDatas(req_str))
            .method(Connection.Method.POST)
            .ignoreContentType(true)
            .timeout(10000)
            .execute();
            body = response.body();
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        JSONObject jsonObj = JSONObject.parseObject(body);
        System.out.println(jsonObj);
        
	}
}
