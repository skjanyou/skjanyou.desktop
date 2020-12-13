package com.skjanyou.desktop.application.cicd.bean;

import java.util.HashMap;
import java.util.Map;

public class Computer {
	private String ip;
	private int    port;
	private String hostname;
	private String username;
	private String password;
	private Map<String,String> para = new HashMap<String, String>();
	public Computer(String ip, int port){
		this.ip = ip;
		this.port = port;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void putPara(String key,String value){
		this.para.put(key, value);
	}
	public String getPara(String key){
		return this.para.get(key);
	}
	
}
