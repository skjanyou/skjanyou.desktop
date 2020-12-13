package com.skjanyou.anime.spider.tencent;

import java.util.List;

import com.skjanyou.anime.api.Page;
import com.skjanyou.anime.api.Spider;
import com.skjanyou.anime.api.abs.AbstractSpider;
import com.skjanyou.anime.api.abs.AbstractWeb;


public class TX_Web extends AbstractWeb {

	public TX_Web(){
		this("http://ac.qq.com/",null,null,false,false,null);
	}
	
	
	public TX_Web(String homeUrl, String currentUrl, String homeName,
			boolean authentication, boolean pickPoolf, Spider spider) {
		super(homeUrl, currentUrl, homeName, authentication, pickPoolf, spider);
	}

	@Override
	public Page<?> getPageContent(String url) {
		this.currentUrl = url;
		return spider.doWork(url);
	}


	@Override
	public <T> List<Page<T>> getPages(T t) {
		AbstractSpider absSpider = (AbstractSpider) this.spider;
		return absSpider.search(t);
	}

}
