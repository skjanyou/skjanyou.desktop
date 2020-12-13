package com.skjanyou.desktop.application.generator.interfaces;

public abstract class DBGenerator implements Generator{
	//数据库相关
	protected String url = null;
	protected String user = null;
	protected String password = null;
	public DBGenerator(String url, String user, String password) {
		super();
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
}
