package com.skjanyou.desktop.application.generator.sqc;


public enum Java_Type {
	STRING("java.lang.String"),BIGDECIMAL("java.math.bigDecimal"),DATE("java.sql.Date"),TIMESTAMP("java.sql.Timestamp");
	private String value;
	Java_Type(String type){
		this.value = type;
	}
	
	public String getValue(){
		return value;
	}
}
