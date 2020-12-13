package com.skjanyou.desktop.application.generator.java;

import java.text.MessageFormat;
import java.util.Map;

import com.skjanyou.desktop.application.generator.interfaces.DBGenerator;
import com.skjanyou.desktop.application.generator.util.Util;


/**
 * DBJavaMyBatisGenerator的包装类,提供快捷的生成代码方法</br>
 * 目录结构如下:</br>
 * <div style="background:white">
 * 		{root}/{package}/entity/{fixName}.java</br>
 * 		{root}/{package}/mapper/{fixName}Mapper.xml</br>
 * 		{root}/{package}/mapper/{fixName}Mapper.java</br>
 * 		{root}/{package}/service/{fixName}Service.java</br>
 * 		{root}/{package}/service/impl/{fixName}ServiceImpl.java</br>
 * </div>
 * */
public class DBJavaMyBatisGeneratorWapper {
	private DBGenerator dbGenerator = null;
	private static String ENTITY = "entity";
	private static String MAPPER = "mapper";
	private static String SERVICE = "service";
	private static String SERVICEIMPL = "service/impl";
	
	private static String JAVA = ".java";
	private static String XML = ".xml";
	
	public DBJavaMyBatisGeneratorWapper(DBGenerator dbGenerator){
		this.dbGenerator = dbGenerator;
	}
	
	public void init(Map<String , Object> parameter){
		this.dbGenerator.init(parameter);
	}

	public void generateBean(String templateFile,String root,String pack,String fixName){
		if(this.dbGenerator != null){
			String path = MessageFormat.format("{0}/{1}/{2}/{3}{4}", root,Util.packagePathConvert(pack),ENTITY,fixName,JAVA);
			this.dbGenerator.write(templateFile, path, false);
		}else {
			throw new RuntimeException("代码生成器dbGenerator为空");
		}
	}
	
	public void generateJavaMapper(String templateFile,String root,String pack,String fixName){
		if(this.dbGenerator != null){
			String path = MessageFormat.format("{0}/{1}/{2}/{3}Mapper{4}", root,Util.packagePathConvert(pack),MAPPER,fixName,JAVA);
			this.dbGenerator.write(templateFile, path, false);
		}else {
			throw new RuntimeException("代码生成器dbGenerator为空");
		}
	}
	
	public void generateXMLMapper(String templateFile,String root,String pack,String fixName){
		if(this.dbGenerator != null){
			String path = MessageFormat.format("{0}/{1}/{2}/{3}Mapper{4}", root,Util.packagePathConvert(pack),MAPPER,fixName,XML);
			this.dbGenerator.write(templateFile, path, false);
		}else {
			throw new RuntimeException("代码生成器dbGenerator为空");
		}
	}
	
	public void generateService(String templateFile,String root,String pack,String fixName){
		if(this.dbGenerator != null){
			String path = MessageFormat.format("{0}/{1}/{2}/{3}Service{4}", root,Util.packagePathConvert(pack),SERVICE,fixName,JAVA);
			this.dbGenerator.write(templateFile, path, false);
		}else {
			throw new RuntimeException("代码生成器dbGenerator为空");
		}
	}
	
	public void generateServiceImpl(String templateFile,String root,String pack,String fixName){
		if(this.dbGenerator != null){
			String path = MessageFormat.format("{0}/{1}/{2}/{3}ServiceImpl{4}", root,Util.packagePathConvert(pack),SERVICEIMPL,fixName,JAVA);
			this.dbGenerator.write(templateFile, path, false);
		}else {
			throw new RuntimeException("代码生成器dbGenerator为空");
		}
	}
	
}
