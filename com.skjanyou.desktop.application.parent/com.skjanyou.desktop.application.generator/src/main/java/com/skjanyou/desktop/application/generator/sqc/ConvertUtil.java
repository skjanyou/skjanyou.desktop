package com.skjanyou.desktop.application.generator.sqc;

import java.sql.Types;

public final class ConvertUtil {
	
	/**由sql类型转化为C基础类型**/
	public static String sqlType2CType(int type){
		switch (type) {
		case Types.VARCHAR : return C_Type.c_char.getValue();
		case Types.CHAR : return C_Type.c_char.getValue();
		case Types.CLOB : return C_Type.c_char.getValue();
		case Types.INTEGER : return C_Type.c_int.getValue();
		case Types.NUMERIC : return C_Type.c_double.getValue();
		case Types.FLOAT : return C_Type.c_float.getValue();
		case Types.DOUBLE : return C_Type.c_double.getValue();
		case Types.DECIMAL : return C_Type.c_long.getValue();
		case Types.DATE : return "";
		case Types.TIMESTAMP : return "";
		default : return "unsupported";
		}
	}
	
	/**由sql类型转化为Java基础类型**/
	public static String sqlType2JavaType(int type){
		switch (type) {
		case Types.VARCHAR : return Java_Type.STRING.getValue();
		case Types.CHAR : return Java_Type.STRING.getValue();
		case Types.CLOB : return Java_Type.STRING.getValue();
		case Types.INTEGER : return Java_Type.BIGDECIMAL.getValue();
		case Types.NUMERIC : return Java_Type.BIGDECIMAL.getValue();
		case Types.FLOAT : return Java_Type.BIGDECIMAL.getValue();
		case Types.DOUBLE : return Java_Type.BIGDECIMAL.getValue();
		case Types.DECIMAL : return Java_Type.BIGDECIMAL.getValue();
		case Types.DATE : return Java_Type.DATE.getValue();
		case Types.TIMESTAMP : return Java_Type.TIMESTAMP.getValue();
		default : return "unsupported";
		}
	}
}
