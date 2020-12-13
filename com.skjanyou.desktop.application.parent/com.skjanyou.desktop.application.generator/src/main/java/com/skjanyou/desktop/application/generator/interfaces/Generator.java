package com.skjanyou.desktop.application.generator.interfaces;

import java.io.File;
import java.util.Map;

public interface Generator {
	/**
	 * 初始化数据操作
	 * @param parameter 初始化数据可能需要的参数
	 */
	public void init(Map<String,Object> parameter);
	/**
	 * 写文件
	 * @param file 要写入的目的文件
	 * @param append 是否以添加至末尾的方式写入文件
	 */
	public File write(String templateFile,String file,boolean append);
	
}
