package com.skjanyou.desktop.application.generator.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.skjanyou.desktop.application.generator.interfaces.Generator;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class V7FrontJSPGenerator implements Generator {
	public static final String SUFFIX = ".flowtrans.xml";
	protected Map<String, Object> map = new LinkedHashMap<String, Object>();		//最终传入到freemark中的Map
	
	private Map<String,Object> detail = new LinkedHashMap<String, Object>();		//明细
	private Map<String,Object> insert = new LinkedHashMap<String, Object>();		//新增
	private Map<String,Object> update = new LinkedHashMap<String, Object>();		//更新
	private Map<String,Object> delete = new LinkedHashMap<String, Object>();		//删除
	private Map<String,Object> query = new LinkedHashMap<String, Object>();		//查询
	
	//查询相关参数
	private Map<String,Object> queryList = new LinkedHashMap<String, Object>();					//查询列表
	private Map<String,String> queryList_in = new LinkedHashMap<String, String>();				//查询列表_查询条件
	private Map<String,String> queryList_out = new LinkedHashMap<String,String>();				//查询列表_查询结果
	
	//新增相关参数
	private Map<String,Object> insertList = new LinkedHashMap<String, Object>();
	private Map<String,String> insertList_in = new LinkedHashMap<String, String>();
	private Map<String,String> insertList_out = new LinkedHashMap<String, String>();
	
	//删除相关参数
	private Map<String,Object> deleteList = new LinkedHashMap<String, Object>();
	private Map<String,String> deleteList_in = new LinkedHashMap<String, String>();
	private Map<String,String> deleteList_out = new LinkedHashMap<String, String>();
	
	//修改相关参数
	private Map<String,Object> updateList = new LinkedHashMap<String, Object>();
	private Map<String,String> updateList_in = new LinkedHashMap<String, String>();
	private Map<String,String> updateList_out = new LinkedHashMap<String, String>();
	
	//明细相关参数
	private Map<String,Object> detailList = new LinkedHashMap<String, Object>();
	private Map<String,String> detailList_in = new LinkedHashMap<String, String>();
	private Map<String,String> detailList_out = new LinkedHashMap<String, String>();
	
	
	private Map<String,Object> publicMap = new LinkedHashMap<String, Object>();	//公共部分
	

	@Override
	public void init(Map<String, Object> parameter) {
		//1.读取XML文件,获得document对象              
        SAXReader reader = new SAXReader();    
        Document queryRoot = null;
        Document detailRoot = null;
        Document updateRoot = null;
        Document deleteRoot = null;
        Document insertRoot = null;
        
        File queryFile = (File) parameter.get("query");
        File detailFile = (File) parameter.get("detail");
        File insertFile = (File) parameter.get("insert");
        File updateFile = (File) parameter.get("update");
        File deleteFile = (File) parameter.get("delete");
        
        queryRoot = read(queryFile,reader);
        detailRoot = read(detailFile,reader);
        insertRoot = read(insertFile,reader);
        updateRoot = read(updateFile,reader);
        deleteRoot = read(deleteFile,reader);
        
        
        filterMap(parameter);
        if(queryRoot != null){
        	loadMapping(queryRoot, queryList_in, queryList_out, queryList, query);
        }
        if(detailRoot != null){
        	loadMapping(detailRoot, detailList_in, detailList_out, detailList, detail);
        }
        if(insertRoot != null){
        	loadMapping(insertRoot, insertList_in, insertList_out, insertList, insert);
        }
        if(updateRoot != null){
        	loadMapping(updateRoot, updateList_in, updateList_out, updateList, update);
        }
        if(deleteRoot != null){
        	loadMapping(deleteRoot, deleteList_in, deleteList_out, deleteList, delete);
        }
        
        
        publicMap.putAll(parameter);
        
        map.put("public", publicMap);
        map.put("detail", detail);
        map.put("insert", insert);
        map.put("query", query);
        map.put("update", update);
        map.put("delete", delete);
        
        System.out.println(map);
	}
	
	//从文件中读取document对象
	private Document read(File file,SAXReader reader){
		Document document = null;
        try {         	
        	if(file != null){
        		document = reader.read(file);
        	}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
        return document;
	}
	
	private void filterMap(Map<String,Object> parameter){
		Iterator<Entry<String, Object>> iter = parameter.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String,Object> entry = iter.next();
			String key = entry.getKey();
        	boolean delFlag = true;
    		if("insert".equals(key)){
    			loadPara1(insert,parameter,key);
//    			insert.put(key, parameter.get(key));
    		}else if("delete".equals(key)){
    			loadPara1(delete,parameter,key);
//    			delete.put(key, parameter.get(key));
    		}else if("update".equals(key)){
    			loadPara1(update,parameter,key);
//    			update.put(key, parameter.get(key));
    		}else if("detail".equals(key)){
    			loadPara1(detail,parameter,key);
//    			detail.put(key, parameter.get(key));
    		}else if("query".equals(key)){
    			loadPara1(query,parameter,key);
//    			query.put(key, parameter.get(key));
    		}else if("insert.list".equals(key)){
    			loadPara1(insertList,parameter,key);
//    			insertList.put(key, parameter.get(key));
    		}else if("delete.list".equals(key)){
    			loadPara1(deleteList,parameter,key);
//    			deleteList.put(key, parameter.get(key));
    		}else if("update.list".equals(key)){
    			loadPara1(updateList,parameter,key);
//    			updateList.put(key, parameter.get(key));
    		}else if("detail.list".equals(key)){
    			loadPara1(detailList,parameter,key);
//    			detailList.put(key, parameter.get(key));
    		}else if("query.list".equals(key)){
    			loadPara1(queryList,parameter,key);
//    			queryList.put(key, parameter.get(key));
    		}else if("insert.list.in".equals(key)){
    			loadPara2(insertList_in,parameter,key);
//    			insertList_in.put(key, parameter.get(key).toString());
    		}else if("insert.list.out".equals(key)){
    			loadPara2(insertList_out,parameter,key);
//    			insertList_out.put(key, parameter.get(key).toString());
    		}else if("delete.list.in".equals(key)){
    			loadPara2(deleteList_in,parameter,key);
//    			deleteList_in.put(key, parameter.get(key).toString());
    		}else if("delete.list.out".equals(key)){
    			loadPara2(deleteList_out,parameter,key);
//    			deleteList_out.put(key, parameter.get(key).toString());
    		}else if("update.list.in".equals(key)){
    			loadPara2(updateList_in,parameter,key);
//    			updateList_in.put(key, parameter.get(key).toString());
    		}else if("update.list.out".equals(key)){
    			loadPara2(updateList_out,parameter,key);
//    			updateList_out.put(key, parameter.get(key).toString());
    		}else if("detail.list.in".equals(key)){
    			loadPara2(detailList_in,parameter,key);
//    			detailList_in.put(key, parameter.get(key).toString());
    		}else if("detail.list.out".equals(key)){
    			loadPara2(detailList_out,parameter,key);
//    			detailList_out.put(key, parameter.get(key).toString());
    		}else if("query.list.in".equals(key)){
    			loadPara2(queryList_in,parameter,key);
//    			queryList_in.put(key, parameter.get(key).toString());
    		}else if("query.list.out".equals(key)){
    			loadPara2(queryList_out,parameter,key);
//    			queryList_out.put(key, parameter.get(key).toString());
    		}else{
    			delFlag = false;
    		}
        	if(delFlag){
        		iter.remove();
        	}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void loadPara1(Map<String,Object> map1 ,Map<String,Object> map2,String key){
		Object value = map2.get(key);
		if(value instanceof Map){
			map1.putAll((Map<String,String>)value);
		}else if(value instanceof String){
			map1.put(key, map2.get(key).toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void loadPara2(Map<String,String> map1 ,Map<String,Object> map2,String key){
		Object value = map2.get(key);
		if(value instanceof Map){
			map1.putAll((Map<String,String>)value);
		}else if(value instanceof String){
			map1.put(key, map2.get(key).toString());
		}
	}
	@SuppressWarnings("unchecked")
	private void loadMapping(Document root,Map<String,String> list_in,Map<String,String> list_out,Map<String,Object> list,Map<String,Object> map){
		Element rootElement = (Element) root.selectObject("flowtran");
		String name = rootElement.attributeValue("longname");
		String code = rootElement.attributeValue("id");
        List<Element> inList = new ArrayList<Element>();
        inList.addAll(root.selectNodes("flowtran/interface/input/field"));
        inList.addAll(root.selectNodes("flowtran/interface/input/fields/field"));
        for (Element element : inList) {
			System.out.println(MessageFormat.format("{0}-输入:[{1}:{2}]","新增参数", element.attributeValue("id"),element.attributeValue("longname")));
			list_in.put(element.attributeValue("id"), element.attributeValue("longname"));
		}
        List<Element> outList = new ArrayList<Element>();
        outList.addAll(root.selectNodes("flowtran/interface/output/field"));
        outList.addAll(root.selectNodes("flowtran/interface/output/fields/field"));
        for (Element element : outList) {
        	System.out.println(MessageFormat.format("{0}-输出:[{1}:{2}]","新增参数", element.attributeValue("id"),element.attributeValue("longname")));
        	list_out.put(element.attributeValue("id"), element.attributeValue("longname"));
		}

        list.put("in", list_in);
        list.put("out", list_out);
        
        map.put("code", code);
        map.put("name", name);
        map.put("list", list);
	}

	@Override
	public File write(String templateFile, String file, boolean append) {
		File resultFile = null;
		Writer out = null;
		try{
			Configuration config = new Configuration();
			config.setDefaultEncoding("UTF-8");
			config.setObjectWrapper(new DefaultObjectWrapper());
			Template template = config.getTemplate(templateFile);
			
			resultFile = new File(file);
			if(!resultFile.getParentFile().exists()){
				resultFile.getParentFile().mkdirs();
			}
			out = new FileWriter(resultFile,append);
			template.process(map, out);
			out.flush();
		} catch(Throwable t) {
			t.printStackTrace();
		} finally {
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultFile;
	}

}
