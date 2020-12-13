package com.skjanyou.desktop.application.generator.main;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import com.skjanyou.desktop.application.generator.interfaces.Generator;
import com.skjanyou.desktop.application.generator.java.V7FrontJSPGenerator;
import com.skjanyou.desktop.application.generator.util.FileUtil;
import com.skjanyou.desktop.application.generator.util.PropertiesUtil;


public class Main {
	public static void main(String[] args) {
		Properties properties = PropertiesUtil.loadProperties("config/mapping.properties").getProperties();
		Generator gen = new V7FrontJSPGenerator();
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,String> publicMap = new LinkedHashMap<String, String>();
		publicMap.put("org_id", "法人代码");
		
		map.put("query", new File(properties.getProperty("queryFile")));
		map.put("detail", new File(properties.getProperty("detailFile")));
		map.put("insert", new File(properties.getProperty("insertFile")));
		map.put("update", new File(properties.getProperty("updateFile")));
		map.put("delete", new File(properties.getProperty("deleteFile")));
		
		Map<String,String> replaceStringMap = new HashMap<String, String>();
		replaceStringMap.put("<input type=\"text\" class=\"form-control\" name=\"org_id\" style=\"width:50%\"></input>",
							"<select name=\"org_id\" class=\"form-control\" style=\"width:50%\"></select>");
		
		replaceStringMap.put("<input type=\"text\" class=\"form-control\" name=\"is_default\" style=\"width:50%\"></input>",
				"<select name=\"is_default\" class=\"form-control\" style=\"width:50%\"></select>");
		
		map.put("detail.list.in", publicMap);
		map.put("delete.list.in", publicMap);
		gen.init(map);
		gen.write("/ftl/V7JSP/query.ftl", "gen/java/query.jsp", false);
		gen.write("/ftl/V7JSP/detail.ftl", "gen/java/detail.jsp", false);
		gen.write("/ftl/V7JSP/delete.ftl", "gen/java/delete.jsp", false);
		gen.write("/ftl/V7JSP/update.ftl", "gen/java/update.jsp", false);
		gen.write("/ftl/V7JSP/insert.ftl", "gen/java/insert.jsp", false);
		gen.write("/ftl/V7JSP/scripte.ftl", "gen/java/scripte.jsp", false);
		File templateFile = gen.write("/ftl/V7JSP/template.ftl", "gen/java/template.jsp", false);
		
		FileUtil.replaceString(templateFile, replaceStringMap);
		
//		gen.write("/ftl/V7JSP/test.ftl", "gen/java/test.jsp", false);
	}
	
	
	
}
