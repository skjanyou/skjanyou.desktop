package com.skjanyou.desktop.application.generator.util;

import java.sql.Types;

public class Util {
	//��������е��»��߼���������
	public static String fixName(String name) {
		StringBuilder builder = new StringBuilder();
		boolean upper = true;
		for (int i = 0; i < name.length(); i++) {
			boolean isLine = name.substring(i, i+1).equals("_");
			if (isLine) {
				upper = true;
				continue;
			} else if (upper) {
				builder.append(name.substring(i, i+1).toUpperCase());
				upper = false;
			} else {
				builder.append(name.substring(i, i+1));
				upper = false;
			}
		}
		return builder.toString();
	}
	
	//处理类型转换
	public static String sqlTypeConvert(int type) {
		switch (type) {
		case Types.VARCHAR : return "VARCHAR";
		case Types.CHAR : return "CHAR";
		case Types.CLOB : return "CLOB";
		case Types.INTEGER : return "NUMERIC";
		case Types.NUMERIC : return "NUMERIC";
		case Types.FLOAT : return "FLOAT";
		case Types.DOUBLE : return "DOUBLE";
		case Types.DECIMAL : return "NUMERIC";
		case Types.DATE : return "DATE";
		case Types.TIMESTAMP : return "TIMESTAMP";
		default : return "unsupported";
		}
	}

	//包路径变成普通路径
	public static String packagePathConvert(String packagePath){
		return packagePath.replace(".", "/");
	}
}
