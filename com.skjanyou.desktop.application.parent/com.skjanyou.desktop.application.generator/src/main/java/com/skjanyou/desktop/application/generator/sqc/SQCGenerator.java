package com.skjanyou.desktop.application.generator.sqc;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class SQCGenerator {
	
	public static void write(Map<String,Object> map) throws Exception{
		Configuration config = new Configuration();

		config.setDirectoryForTemplateLoading(new File(SQCGenerator.class.getResource("/").getFile()));
		config.setObjectWrapper(new DefaultObjectWrapper());
		Template template = config.getTemplate("c/h.ftl");
		
		Writer out = new FileWriter(new File("gen/1.sqc"),false);
		template.process(map, out);
		out.flush();
	}
	
	
	public static void main(String[] args) throws Throwable{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		//数据库相关
		String url = "jdbc:oracle:thin:@32.114.63.60:1521:orcl";
		String user = "jncard";
		String password = "sunline";
		
		//表名称
		String tableName = "ics_bach";
		
		
		/**  {function=Test,in={字段名={columnName=,....}}},out={字段名={....}}  **/
		Map<String, Object> map = new HashMap<String, Object>();		//最终传入到freemark中的Map
		@SuppressWarnings("rawtypes")									
		List<Map> inList = new ArrayList<Map>();						//对应结构体中的in
		@SuppressWarnings("rawtypes")
		List<Map> outList = new ArrayList<Map>();						//对应结构体中的out
		@SuppressWarnings("rawtypes")
		List<Map> pkList = new ArrayList<Map>();						//主键字段
		@SuppressWarnings("rawtypes")
		List<Map> notPkList = new ArrayList<Map>();						//非主键字段
		
		
		Connection conn = DriverManager.getConnection(url, user, password);

		//查询条件,获得主键
		DatabaseMetaData dbMeta = conn.getMetaData();
		ResultSet pk = dbMeta.getPrimaryKeys(null, null, tableName.toUpperCase());
		Set<String> kset = new HashSet<String>();
		while(pk.next()) {
			kset.add(pk.getString(4).toLowerCase());
		}
		
		
		
		
		
		//查询结果
		PreparedStatement statement = (PreparedStatement) conn.prepareCall("select * from " + tableName);
		ResultSet result = statement.executeQuery();
		ResultSetMetaData metaData = result.getMetaData();
		int cnt = metaData.getColumnCount();
		for (int i = 1; i <= cnt; i++) {
			Map<String,String> colMap = new HashMap<String, String>();

			String columnName = metaData.getColumnName(i).toLowerCase();	//字段名
			int columnJdbcType = metaData.getColumnType(i);					//字段类型代码
			String columnScale = metaData.getScale(i) + "";					//不明
			int columnPrecision = metaData.getPrecision(i);					//长度
			
//			String columnClass = metaData.getColumnClassName(i);			//对应的java类
//			String columnRawType = metaData.getColumnTypeName(i);			//字段类型名
			
			colMap.put("columnName", columnName);	//字段名字
			colMap.put("columnRawType", ConvertUtil.sqlType2CType(columnJdbcType) );		//sql字段类型
			

			colMap.put("columnScale", columnScale);			//
			colMap.put("columnPrecision", columnPrecision + "");	//长度
			
			if(kset.contains(columnName)){
				pkList.add(colMap);
				inList.add(colMap);
			}else{
				notPkList.add(colMap);
			}
			
			outList.add(colMap);
		}
		map.put("tablename", tableName);
		map.put("function", "Test");
		map.put("inList", inList);
		map.put("outList", outList);
		
		
		map.put("pk", pkList);
		map.put("notpk", notPkList);
		System.out.println(map);
		//开始写文件
		write(map);		
		
		
	}
}
