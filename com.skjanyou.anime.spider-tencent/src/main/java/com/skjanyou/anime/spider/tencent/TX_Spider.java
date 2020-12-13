package com.skjanyou.anime.spider.tencent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.Scriptable;

import com.skjanyou.anime.api.Page;
import com.skjanyou.anime.api.Web;
import com.skjanyou.anime.api.abs.AbstractSpider;
import com.skjanyou.anime.entity.Anime;
import com.skjanyou.anime.entity.Chapter;
import com.skjanyou.appl.AppUtil.LogUtil;

public class TX_Spider extends AbstractSpider {
	private String urlPrefix = "http://ac.qq.com";
	private LogUtil log = new LogUtil(this);
	private Web web = null;
	protected HttpClient httpClient = HttpClientBuilder.create().build();
	public TX_Spider(){}
	
	public TX_Spider(Web web){
		this.web = web;
	}
	@Override
	public Page<?> doWork(String url) {
		if(url.contains("ComicView")){
			return processChapter(url);
		}else if(url.contains("Comic")){
			return processAnime(url);
		}else{
			return new TX_Page(web);
		}
	}
	
	private Page<Anime> processAnime(String url){
		TX_Page<Anime> animePage = new TX_Page<Anime>(web);
		Anime anime = new Anime();
		//正式抓取过程
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String html = EntityUtils.toString(entity);
			Document document = Jsoup.parse(html);
			//Jsoup.parse(new URL(url), 5000);	//用这种方式获取的html页面不同,得不到想要的参数
			//String animeUrl = document.select(".works-cover>a").attr("href");//章节地址存放处
			String animeCover = document.select(".works-cover>a>img").attr("src");//漫画封面存放处
			String animeName = document.select(".works-cover>a").attr("title");//漫画名字
			String animeDescription = document.select(".works-intro-short").html();//漫画描述
			String author = document.select(".works-intro-digi>span").first().select("em").html().replace("&nbsp;", "");//漫画作者
			Date startTime = null;//连载开始时间
			Date endTime = null;//连载结束时间
			boolean isEnd = false;//是否完结
			
			//加载章节
			Elements chapters = document.select(".works-chapter-item>a");
			for(Element chapterItem : chapters){
				String chapterName = chapterItem.attr("title");
				String chapterUrl = chapterItem.attr("href");
				anime.add(new Chapter(chapterName,urlPrefix + chapterUrl));
			}
			
			//填充数据
			anime.setAnimeCover(animeCover);
			anime.setAnimeDescription(animeDescription);
			anime.setAnimeName(animeName);
			anime.setAuthor(author);
			anime.setEnd(isEnd);
			anime.setUrl(url);
			anime.setStartTime(startTime);
			anime.setEndTime(endTime);
			
			//准备返回
			animePage.setCurrentContent(anime);
			log.info("抓取到漫画数据:" + anime.toString());
		} catch (ClientProtocolException e) {  //应当要自定义异常抛给上一级
			log.error("", e);
		} catch (ParseException e) {
			log.error("", e);
		} catch (IOException e) {
			log.error("", e);
		} catch (Throwable e){
			log.error("", e);
		}finally{

		}
		return animePage;
	}
	
	private Page<Chapter> processChapter(String url){
		TX_Page<Chapter> chapterPage = new TX_Page<Chapter>(web);
		Chapter chapter = new Chapter();
		try{
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String html = EntityUtils.toString(entity);
//			log.info(html);
			Document document = Jsoup.parse(html);
//			String chapterUrl = document.select("#chapter").attr("href");//这个是漫画的地址
			String chapterName = document.select("#chapter").html();
			String title_comicHeading = document.select(".title-comicHeading").html(); //章节数
			String chapterDescription = null;//没有描述
			String chapterCover = null;//没有封面
			Date updateTime = null;//没有更新时间
			//这部分是加密的图片地址,要用js脚本进行解密,获取方式有点简单粗暴,可能会出错
			String data = document.select("script").toString();
			data = data.substring(data.indexOf("DATA")).split("'")[1].split("'")[0];
			List<String> imgs = getImagesPath(data);
			
			chapter.setChapterCover(chapterCover);
			chapter.setChapterDescription(chapterDescription);
			chapter.setChapterName(chapterName + " " + title_comicHeading);
			chapter.setImgs(imgs);
			chapter.setUrl(url);
			chapter.setUpdateTime(updateTime);
			
			chapterPage.setCurrentContent(chapter);
			log.info("抓取到漫画章节 " + chapter.toString());
		} catch (ClientProtocolException e) {  //应当要自定义异常抛给上一级
			log.error("", e);
		} catch (ParseException e) {
			log.error("", e);
		} catch (IOException e) {
			log.error("", e);
		} catch (Throwable e){
			log.error("", e);
		}finally{

		}
		return chapterPage;
	}

	
	//使用js解释器来获得图片的地址
	private List<String> getImagesPath(String str){
		List<String> imgPathList = new ArrayList<String>();
		str = str.substring(1);
		Context cx = Context.enter();
		Scriptable scope = cx.initStandardObjects(null);
		InputStream is = null;
		try {
			is = TX_Spider.class.getClassLoader().getResourceAsStream("com/skjanyou/anime/spider/tencent/tx_decode.js");
			cx.evaluateReader(scope, new InputStreamReader(is), "<cmd>", 1, null);
			Object result = cx.evaluateString(scope, "B.decode('" + str + "');", null, 1, null);
			String s = Context.toString(result);
			if(s == null){
				return imgPathList;
			}
			//2017年3月31日 16:43:59 这里分为两种情况
			if(s.contains("ori")){			//以ori结尾的
				oriEndImage(imgPathList,s);				
			}else if(s.contains("jpg")){    //以jpg结尾的
				jpgEndImage(imgPathList,s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JavaScriptException e) {
			e.printStackTrace();
		} finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return imgPathList;
	}
	//以ori结尾的图片获取
	private void oriEndImage(List<String> imgPathList,String s){
		String[] sr = s.split("url");
		for(int i = 1;i < sr.length - 2;i++){
			try{
				String subStr = sr[i].substring(3, sr[i].indexOf("ori") + 3);
				String imagePath = subStr.replace("\\", "");
				imgPathList.add(imagePath);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//以jpg结尾的图片获取
	void jpgEndImage(List<String> imgPathList,String s){
		JSONObject jsonObj = new JSONObject(s);
		JSONArray jsonArray = (JSONArray) jsonObj.get("picture");
		int length = jsonArray.length();
		for(int index = 0;index < length;index ++){
			try{
				JSONObject picture = (JSONObject) jsonArray.get(index);
				String imageUrl = picture.getString("url");
				imgPathList.add(imageUrl);
			} catch(Exception e){
				continue;
			}
		}
	}	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public<T> List<Page<T>> search(T t) {
		List<Page<T>> pageList = new ArrayList<Page<T>>();
		if(t instanceof Anime){
			Anime anime = (Anime) t;
			List<Page> animesList = searchAnimes(anime,web);
			for(Page page : animesList){
				pageList.add(page);
			}
		}else if(t instanceof Chapter){
			Chapter chapter = (Chapter) t;
			List<Page> chapterList = searchChapters(chapter);
			for(Page page : chapterList){
				pageList.add(page);
			}
		}else{
			
		}
		return pageList;
	}
	
	private List<Page> searchAnimes(Anime anime,Web web){
		List<Page> pageList = new ArrayList<Page>();
		String searchURL = "http://ac.qq.com/Comic/searchList/search/";
		String animeURLPrefix = "http://ac.qq.com";
		String animeName = anime.getAnimeName();
		try{
			HttpGet httpGet = new HttpGet(searchURL + animeName);
			HttpResponse response = httpClient.execute(httpGet);	
			HttpEntity entity = response.getEntity();
			String html = EntityUtils.toString(entity);
			httpGet.releaseConnection();
			Document document = Jsoup.parse(html);
			Elements elements = document.getElementsByClass("mod_book_cover");
			for (Element element : elements) {
				String url = animeURLPrefix + element.attr("href");
				Page<Anime> page = processAnime(url);	//这里其实还有分页
				pageList.add(page);
			}
		} catch(Throwable t){
			log.error("", t);
		}
		
		return pageList;
	}
	private List<Page> searchChapters(Chapter chapter){
		return null;
	}
}
