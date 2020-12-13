package com.skjanyou.desktop.desktop.bean;

public class Menu {
	private String img;
	private String title;
	private String action;
	private String onClick;
	private Class<?> subClass;
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getOnClick() {
		return onClick;
	}
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
	public Class<?> getSubClass() {
		return subClass;
	}
	public void setSubClass(Class<?> subClass) {
		this.subClass = subClass;
	}
}
