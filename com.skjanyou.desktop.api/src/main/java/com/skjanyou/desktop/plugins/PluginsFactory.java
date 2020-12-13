package com.skjanyou.desktop.plugins;


import com.skjanyou.desktop.entity.Plugin;

/**
 * 时间:2017年3月28日 16:37:30</br>
 * 作用:获得插件,需要在子类实现getPlugin()方法</br>
 * @author skjanyou
 *
 */
public interface PluginsFactory {
	Plugin getPlugin();
}
