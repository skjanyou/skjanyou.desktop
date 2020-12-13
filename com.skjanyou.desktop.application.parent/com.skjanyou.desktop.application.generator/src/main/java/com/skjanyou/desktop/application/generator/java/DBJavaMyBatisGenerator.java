package com.skjanyou.desktop.application.generator.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.skjanyou.desktop.application.generator.interfaces.DBGenerator;
import com.skjanyou.desktop.application.generator.sqc.ConvertUtil;
import com.skjanyou.desktop.application.generator.util.Util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 *
 * 单表基于主键的增删改查</br>
 * 生成Mybatis的bean、mapper、service文件 </br>
 * @author 谭康
 *
 */
public class DBJavaMyBatisGenerator extends DBGenerator {
	protected Map<String, Object> map = new HashMap<String, Object>();		//最终传入到freemark中的Map
	@SuppressWarnings("rawtypes")									
	protected List<Map> inList = new ArrayList<Map>();						//对应结构体中的in
	@SuppressWarnings("rawtypes")
	protected List<Map> pkList = new ArrayList<Map>();						//主键字段
	@SuppressWarnings("rawtypes")
	protected List<Map> notPkList = new ArrayList<Map>();					//非主键字段
	
	protected String tableName = "";										//表名
	
	public DBJavaMyBatisGenerator(String url, String user, String password) {
		super(url, user, password);
	}

	@Override
	public void init(Map<String,Object> parameter) {
		if(parameter != null){
			tableName = parameter.get("tableName").toString();
		}else{
			throw new RuntimeException("参数中没有tableName字段");
		}
		map.putAll(parameter);
		
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(url, user, password);

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
				String columnName = metaData.getColumnName(i).toLowerCase();
				String columnRawType = metaData.getColumnTypeName(i);
				String columnType = ConvertUtil.sqlType2JavaType((metaData.getColumnType(i)));
				String columnJdbcType = Util.sqlTypeConvert(metaData.getColumnType(i));
				String columnClass = metaData.getColumnClassName(i);
				String columnScale = metaData.getScale(i)+"";
				String columnPrecision = metaData.getPrecision(i)+"";
				
				colMap.put("columnName", columnName);
				colMap.put("columnRawType", columnRawType);
				colMap.put("columnType", columnType);
				colMap.put("columnJdbcType", columnJdbcType);
				colMap.put("columnClass", columnClass);
				colMap.put("columnScale", columnScale);
				colMap.put("columnPrecision", columnPrecision);
				
				inList.add(colMap);
				if(kset.contains(columnName)){
					pkList.add(colMap);
				}else{
					notPkList.add(colMap);
				}
				
			}
			map.put("tablename", tableName);
			map.put("fixName", Util.fixName(tableName));
			map.put("list", inList);
			
			
			map.put("pk", pkList);
			map.put("notpk", notPkList);
			System.out.println(map);
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public File write(String templateFile,String file,boolean append) {
		File f = null;
		Writer out = null;
		try{
			Configuration config = new Configuration();

			config.setObjectWrapper(new DefaultObjectWrapper());
			Template template = config.getTemplate(templateFile);
			
			f = new File(file);
			if(!f.getParentFile().exists()){
				f.getParentFile().mkdirs();
			}
			out = new FileWriter(f,append);
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
		return f;
	}

}
