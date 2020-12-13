package com.skjanyou.desktop.desktop.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.skjanyou.desktop.desktop.utils.SystemUtil;

/**
 * 开发环境,依赖desktop.application.*,其中的资源通过这个类进行加载
 * @author skjanyou
 * 时间 : 2018年6月28日
 * 作用 :
 */
class DevEnvironment implements Environment {

	@Override
	public InputStream getInputStream(String path) {
		InputStream is = getIsInMainResource(path);
		return is;
	}
	
	
	private InputStream getIsInMainResource(String path){
		InputStream is = DevEnvironment.class.getResourceAsStream(path);
		return is;
	}

}
