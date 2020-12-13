package com.skjanyou.desktop.desktop.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;

import com.skjanyou.desktop.desktop.config.Config;


/**
 * 
 * @author skjanyou
 * 2015年9月12日 09:20:31
 * 提供网络中文件的下载
 * src为网络文件的真实地址，dec为保存在本地的目录
 */
public class DownloadUtil {

	/**
	 * 
	 * @param src  文件的真实路径，来自网站，列如http://coldpic.sfacg.com/Pic/OnlineComic4/QHDZD/009/040_9918.png
	 * @param dec  文件的存放路径，例如c:/
	 */
	public void saveFile(String src,String dec){
//		System.out.println(src);
		// 构造URL  
		URL url = null;
		try {
			url = new URL(src);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}  
		// 打开连接  
		URLConnection con = null;
		try {
			con = url.openConnection();
		} catch (IOException e1) {
			//			e1.printStackTrace();
			System.out.println("连接不上");
			return;
		}  
		//设置请求超时为5s  
		con.setConnectTimeout(Config.waitTime * 1000);  
		// 输入流  
		InputStream is = null;
		try {
			is = con.getInputStream();
		} catch (IOException e1) {
			//			e1.printStackTrace();
			System.out.println("连接不上");
			return ;
		}
		String fileName = src.substring(src.lastIndexOf("/") + 1,src.length());
		File decFile = new File(dec);
		if(!decFile.exists()){
			decFile.mkdirs();
		}
		File file = new File(dec + "/" + fileName);
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			byte[] buffer = new byte[1024 * 10];
			int count = -1;
			while((count = is.read(buffer)) != -1){
				bos.write(buffer,0,count);
			}
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			return;
			//			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			return;
			//			e.printStackTrace();
		}finally{
			try {
				if(bos != null){
					bos.close();
				}
				if(fos != null){
					fos.close();
				}
			} catch (IOException e) {
				//				error++;
				return;
				//				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @param src  文件的真实路径，来自网站，列如http://coldpic.sfacg.com/Pic/OnlineComic4/QHDZD/009/040_9918.png
	 * @param dec  文件的存放路径，例如c:/
	 * @param index 给图片序号，列如1.png等
	 * @throws Exception 
	 */
	public void saveFile(String src,String dec,int index) throws Exception{
//		System.out.println(src);
		// 验证后缀是否为合法的图片文件BMP、GIF、JPG、PNG
		String postfix = src.substring(src.lastIndexOf(".") ,src.length());
		if(!".bmp".equalsIgnoreCase(postfix) && !"gif".equalsIgnoreCase(postfix)&& !"jpg".equalsIgnoreCase(postfix)
			&& !"png".equalsIgnoreCase(postfix)){
			postfix = ".jpg";
		}
		String fileName = index + postfix;
		File decFile = new File(dec);
		if(!decFile.exists()){
			decFile.mkdirs();
		}
		try {
			HttpHelper.connect(src)  
			.referrer(src).timeout(Config.waitTime * 1000).get().toFile(dec + "/" + fileName);
		} catch (IOException e) {
//			e.printStackTrace();
			throw new Exception("保存失败");
		}
//		throw new Exception(src);
	}
	
	/**
	 * 
	 * @param src  文件的真实路径，来自网站，列如http://coldpic.sfacg.com/Pic/OnlineComic4/QHDZD/009/040_9918.png
	 * @param dec  文件的存放路径，例如c:/
	 * @param index 给图片序号，列如1.png等
	 * @throws Exception 
	 */
	public void saveFile(String src,String dec,String fileName) throws Exception{
		File decFile = new File(dec);
		if(!decFile.exists()){
			decFile.mkdirs();
		}
		try {
			HttpHelper.connect(src)  
			.referrer(src).timeout(Config.waitTime * 1000).get().toFile(dec + "/" + fileName);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("保存失败");
		}
	}
	/**
	 * 
	 * @param map 存放图片文件的集合，类似<漫画名,<章节,{1.png,2.png}>>
	 * @param dec  文件的存放路径，例如c:/
	 * @throws Exception 
	 */
	public void saveFile(HashMap<String, HashMap<String, List<String>>> map,String dec) throws Exception{
		if(dec.charAt(dec.length() - 1) != '/'){
			dec += "/";
		}
		for (Map.Entry<String, HashMap<String, List<String>>> entry : map.entrySet()) {
			String animeName = entry.getKey();
			HashMap<String,List<String>> map2 = entry.getValue();
			for(Map.Entry<String, List<String>> entry2:map2.entrySet()){
				String listName = entry2.getKey();
				List<String> imgPath = entry2.getValue();
//				for(String str:imgPath){
//					DownloadUtil.saveFile(str, dec + animeName + "/" + listName);
//				}
				for(int i = 0;i < imgPath.size();i++){
					saveFile(imgPath.get(i),dec + animeName + "/" + listName,i + 1);
				}
				System.out.println("**********" + listName + "下载完成***********");
			}
		}
	}

	public void saveFile(String dec,String animeName,LinkedHashMap<String,List<String>> map) throws Exception{
		if(dec.charAt(dec.length() - 1) != '/'){
			dec += "/";
		}
		Iterator iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String, List<String>> entity = (Entry<String, List<String>>) iter.next();
			String chapter = entity.getKey();
			List<String> animePathList = entity.getValue();
			int size = animePathList.size();
			for(int i = 0;i < size;i++){
				saveFile(animePathList.get(i),dec + animeName + "/" + chapter,i + 1);
			}
			
		}
	}
	/**
	 * 多线程用
	 * @param dec
	 * @param animeName
	 * @param chapter
	 * @param list
	 */
	public void saveFile(String dec,String animeName,String chapter,List<String> list) throws Exception{
		if(dec.charAt(dec.length() - 1) != '/'){
			dec += "/";
		}
		int size = list.size();
		for(int i = 0;i < size;i ++){
			saveFile(list.get(i),dec + animeName + "/" + chapter,i + 1);
		}
		System.out.println("**********" + animeName + "-" + chapter + "下载完成***********");
	}
	
	/**
	 * 多线程用
	 * @param dec
	 * @param animeName
	 * @param chapter
	 * @param list
	 */
	public void saveFile(String dec,String animeName,String chapter,List<String> list,JLabel lb_process){
		if(dec.charAt(dec.length() - 1) != '/'){
			dec += "/";
		}
		int size = list.size();
		for(int i = 0;i < size;i ++){
			try {
				saveFile(list.get(i),dec + animeName + "/" + chapter,i + 1);
			} catch (Exception e) {
				lb_process.setText(e.getMessage());
			}
		}
		System.out.println("**********" + animeName + "-" + chapter + "下载完成***********");
	}

}