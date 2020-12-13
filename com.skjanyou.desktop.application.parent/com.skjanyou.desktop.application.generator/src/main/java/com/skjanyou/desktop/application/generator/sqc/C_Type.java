package com.skjanyou.desktop.application.generator.sqc;

public enum C_Type {
	c_char("char"),c_long("long"),c_int("int"),c_struct("struct"),c_double("double"),c_float("float"),c_short("short"),c_enum("enum");
	
	private String value;
	
	C_Type(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
	
}
