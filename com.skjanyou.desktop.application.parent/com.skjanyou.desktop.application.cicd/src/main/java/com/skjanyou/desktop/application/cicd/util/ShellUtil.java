package com.skjanyou.desktop.application.cicd.util;

import java.util.HashMap;
import java.util.Map;

import com.skjanyou.util.BeanUtil;

public class ShellUtil {
	private final static String regex = "(?<=\\{)(\\S+)(?=\\})";
	
	public static<T> String createShellCommand(String words,T t ){
        Map<String,Object> bean = BeanUtil.bean2Map(t);
        Map<String,String> data = new HashMap<String, String>();
        bean.forEach((key,value)->{
        	data.put(key, value.toString());
        });
		
		return PatternUtil.getGenString(regex, words, data)
				.replaceAll("{", "")
				.replaceAll("}", "");
	}
}
