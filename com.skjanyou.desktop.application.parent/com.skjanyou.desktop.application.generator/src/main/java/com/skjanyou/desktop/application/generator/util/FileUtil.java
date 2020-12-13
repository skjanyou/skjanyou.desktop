package com.skjanyou.desktop.application.generator.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

public class FileUtil {
	/**
	 * 
	 * @param file 传入文件
	 * @param src  要被替换的字符
	 * @param dest 替换的字符
	 */
	public static void replaceString(File file,String src,String dest){
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		String jspPage = null;
		try{
			is = new FileInputStream(file);
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[4 * 1024];
			int count = -1;
			while((count = is.read(buffer)) != -1){
				baos.write(buffer, 0, count);
			}
			
			byte[] bytes = baos.toByteArray();
			jspPage = new String(bytes,"UTF-8");
			jspPage = jspPage.replaceAll(src, dest);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(baos != null){
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		OutputStream os = null;
		if(jspPage != null){
			try{
				os = new FileOutputStream(file);
				os.write(jspPage.getBytes());
				os.flush();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void replaceString(File file , Map<String,String> map){
		Set<String> keys = map.keySet();
		for (String key : keys) {
			replaceString(file,key,map.get(key));
		}
	}
	
}
