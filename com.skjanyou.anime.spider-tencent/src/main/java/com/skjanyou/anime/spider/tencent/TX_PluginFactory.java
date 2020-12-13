package com.skjanyou.anime.spider.tencent;

import com.skjanyou.anime.api.Spider;
import com.skjanyou.anime.api.Web;
import com.skjanyou.anime.entity.Plugin;
import com.skjanyou.anime.plugins.PluginsFactory;

/***
 * 时间:2017年3月28日 16:52:19
 * 作用:获取插件的工厂类
 * @author skjanyou
 *
 */
public class TX_PluginFactory implements PluginsFactory {

	@Override
	public Plugin getPlugin() {
		Web web = new TX_Web();
		Spider spider = new TX_Spider();
		web.setSpider(spider);
		Plugin plugin = new Plugin("", 0 ,"腾讯漫画","TX",null, web,spider);
		return plugin;
	}

}
