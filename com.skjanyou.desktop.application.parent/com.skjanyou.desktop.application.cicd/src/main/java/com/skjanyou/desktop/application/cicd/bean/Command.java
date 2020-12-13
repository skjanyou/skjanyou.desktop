package com.skjanyou.desktop.application.cicd.bean;

public class Command {
	private String commandid;;
	private String cmd;
	private String desc;
	
	public Command(String commandid){
		this.commandid = commandid;
	}
	public String getCommandid() {
		return commandid;
	}
	public void setCommandid(String commandid) {
		this.commandid = commandid;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
