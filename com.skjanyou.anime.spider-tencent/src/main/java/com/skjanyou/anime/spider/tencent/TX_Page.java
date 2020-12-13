package com.skjanyou.anime.spider.tencent;

import java.util.ArrayList;
import java.util.List;

import com.skjanyou.anime.api.Page;
import com.skjanyou.anime.api.Web;
import com.skjanyou.anime.api.abs.AbstractPage;
import com.skjanyou.appl.AppUtil.LogUtil;


final class TX_Page<T> extends AbstractPage<T> {
	private LogUtil log = new LogUtil(getClass());
	
	public TX_Page(Web web) {
		super(web);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<T> getContent(int begin, int end) {
		List list = new ArrayList();
		String currentUrl = web.getCurrentUrl();
		//其实这里可以只写一个currentUrl.contains("Comic")即可,但是为了以防以后url改变,这里还是写两个
		//如果是动漫或者动漫章节,url是这种处理,如果是图片处理不同
		if(currentUrl.contains("Comic") || currentUrl.contains("ComicView")){
			if(currentUrl != null){
				String cu = currentUrl.substring(0,currentUrl.lastIndexOf("/") + 1);
				for(int i = begin;i <= end;i++){
					String newUrl = cu + i;
					Object obj = web.getPageContent(newUrl).getCurrentPageContent();
					list.add(obj);
				}
			}
		}else{
			
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<T> prePage() {
		String currentUrl = web.getCurrentUrl();
		String url = "";
		//其实这里可以只写一个currentUrl.contains("Comic")即可,但是为了以防以后url改变,这里还是写两个
		//如果是动漫或者动漫章节,url是这种处理,如果是图片处理不同
		if(currentUrl.contains("Comic") || currentUrl.contains("ComicView")){
			if(currentUrl != null){
				String cu = currentUrl.substring(0,currentUrl.lastIndexOf("/") + 1);
				int index = Integer.parseInt(currentUrl.substring(currentUrl.lastIndexOf("/") + 1));
				index -= 1;
				url = cu + index;
			}
		}else{
			
		}
		return web.getPageContent(url);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<T> nextPage() {
		String currentUrl = web.getCurrentUrl();
		String url = "";
		//其实这里可以只写一个currentUrl.contains("Comic")即可,但是为了以防以后url改变,这里还是写两个
		//如果是动漫或者动漫章节,url是这种处理,如果是图片处理不同
		if(currentUrl.contains("Comic") || currentUrl.contains("ComicView")){
			if(currentUrl != null){
				String cu = currentUrl.substring(0,currentUrl.lastIndexOf("/") + 1);
				int index = Integer.parseInt(currentUrl.substring(currentUrl.lastIndexOf("/") + 1));
				index += 1;
				url = cu + index;
			}
		}else{
			
		}
		return web.getPageContent(url);
	}

	@Override
	public T getCurrentPageContent() {
		return this.currentContent;
	}
	
	@Override
	public Page<T> setPgae(int index) {
		String currentUrl = web.getCurrentUrl();
		String url = "";
		if(currentUrl.contains("Comic") || currentUrl.contains("ComicView")){
			if(currentUrl != null){
				String cu = currentUrl.substring(0,currentUrl.lastIndexOf("/") + 1);
				url = cu + index;
			}
		}else{
			
		}
		return web.getPageContent(url);
	}

}
