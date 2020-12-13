package com.skjanyou.desktop.entity;

import java.io.Serializable;
import java.util.Properties;


/**
 * 时间:2017年3月28日 16:38:58
 * 作用:插件类
 * @author skjanyou
 *
 */
public class Plugin implements Serializable{
	private static final long serialVersionUID = "Plugin".hashCode();
	private String pluginID;		//插件ID
	private int pluginIndex;		//插件索引,由Process生成
	private String pluginName;		//插件名称
	private String pluginDescripte; //插件描述
	private Properties pluginProperties;//插件属性
	//下面2个必须有
	
	public Plugin(){}

	public Plugin(String pluginID, int pluginIndex, String pluginName,
			String pluginDescripte, Properties pluginProperties) {
		super();
		this.pluginID = pluginID;
		this.pluginIndex = pluginIndex;
		this.pluginName = pluginName;
		this.pluginDescripte = pluginDescripte;
		this.pluginProperties = pluginProperties;
	}

	public String getPluginID() {
		return pluginID;
	}

	public void setPluginID(String pluginID) {
		this.pluginID = pluginID;
	}

	public int getPluginIndex() {
		return pluginIndex;
	}

	public void setPluginIndex(int pluginIndex) {
		this.pluginIndex = pluginIndex;
	}

	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

	public String getPluginDescripte() {
		return pluginDescripte;
	}

	public void setPluginDescripte(String pluginDescripte) {
		this.pluginDescripte = pluginDescripte;
	}

	public Properties getPluginProperties() {
		return pluginProperties;
	}

	public void setPluginProperties(Properties pluginProperties) {
		this.pluginProperties = pluginProperties;
	}


	@Override
	public String toString() {
		return "Plugin [pluginId=" + pluginID + ", pluginIndex=" + pluginIndex
				+ ", pluginName=" + pluginName + ", pluginDescripte="
				+ pluginDescripte + ", pluginProperties=" + pluginProperties
				+ "]";
	}

}
