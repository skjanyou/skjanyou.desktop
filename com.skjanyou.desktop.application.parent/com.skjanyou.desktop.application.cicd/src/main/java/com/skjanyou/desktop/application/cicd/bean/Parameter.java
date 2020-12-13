package com.skjanyou.desktop.application.cicd.bean;

/**
 * 参数
 * @author skjanyou
 * 时间 : 2018年7月10日
 * 作用 :
 */
public class Parameter {
	private String oprid;
	private String pid;
	private String key;
	private String value;
	
	public Parameter(){}
	public Parameter(String pid){
		this.pid = pid;
	}
	public Parameter(String pid, String key, String value) {
		super();
		this.pid = pid;
		this.key = key;
		this.value = value;
	}
	public String getOprid() {
		return oprid;
	}
	public void setOprid(String oprid) {
		this.oprid = oprid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
