package com.skjanyou.desktop.application.cicd.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {
	/**
	 * 获得字符串中满足匹配正则的部分
	 * @param regex 正则
	 * @param words	匹配值
	 * @return	匹配结果
	 */
	public static String  getGenString(String regex,String words,Map<String,String> data)
	{
		String result = new String();
        Pattern pattern = Pattern.compile (regex);
        Matcher matcher = pattern.matcher (words);
        StringBuffer sb = new StringBuffer();
        while (matcher.find ())
        {
        	String key = matcher.group ();
        	String value = data.get(key);
        	System.out.println(key + "," + value);
        	
        	matcher.appendReplacement(sb, value);
        	//用数据填充变量
        	System.out.println(sb);
        }
        matcher.appendTail(sb);
        return sb.toString();
	}
	
	
	public static List<String> getMatcherStringList(String regex,String words){
		List<String> result = new ArrayList<String>();
        Pattern pattern = Pattern.compile (regex);
        Matcher matcher = pattern.matcher (words);
        StringBuffer sb = new StringBuffer();
        while (matcher.find ())
        {
            System.out.println (matcher.group ());
            System.out.println(matcher.start() + "," + matcher.end());
            matcher.appendReplacement(sb, "aaaa");
            result.add(matcher.group());
        }
        System.out.println(sb);
        System.out.println(words);
		return result;
	}
	
}
