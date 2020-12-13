package com.skjanyou.desktop.application.CloudMusic.core;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.skjanyou.desktop.application.CloudMusic.netease.Api;
import com.skjanyou.desktop.application.CloudMusic.netease.UrlParamPair;
import com.skjanyou.desktop.application.CloudMusic.secret.JSSecret;

public class Search {

	public static void main(String[] args) throws IOException {
        String music_name="林俊杰";
        CloudMusic.searchBySonger(music_name);
        
	}

}
