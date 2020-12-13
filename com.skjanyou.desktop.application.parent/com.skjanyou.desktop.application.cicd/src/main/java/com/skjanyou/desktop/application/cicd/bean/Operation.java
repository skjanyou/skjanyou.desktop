package com.skjanyou.desktop.application.cicd.bean;

import java.util.List;

public class Operation {
	private String oprid;	//操作唯一标识
	private List<String> cmdList;		//命令
	private String desc;	//描述
	
	public List<String> getCmdList() {
		return cmdList;
	}
	public void setCmdList(List<String> cmdList) {
		this.cmdList = cmdList;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getOprid() {
		return oprid;
	}
	public void setOprid(String oprid) {
		this.oprid = oprid;
	}
	
}
