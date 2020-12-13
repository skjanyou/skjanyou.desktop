package com.skjanyou.desktop.desktop.config;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;

import com.alibaba.fastjson.JSONObject;
import com.skjanyou.appl.AppUtil.LogUtil;
import com.skjanyou.desktop.desktop.javafunction.JavaFunction;

/**
 * 所有的JavaFunction应该都要交给这个类管理</br>
 * 在js中调用时,这个类会根据第一个字段的值来分发任务给对应的类</br>
 * 对应的java建议以J开头命名</br>
 * 2017年3月21日 23:21:57 这里是运行在UI线程当中,所以当运行费时操作时,会阻塞UI线程,导致程序无响应</br>
 * 一种解决方案是:java类里面用多线程,然后在费时任务完成之后,调用js函数</br>
 * <pre>
 * 
 * {
 * 		sys : {
 * 				code :　xx,				// 调用Java类的标识
 * 				error: xx,				// 错误码
 * 				msg : xx				// 错误信息
 * 		},
 * 		data : {}						// 数据
 * }
 * 
 * 
 * </pre>
 * */
public class JavaFunctionManager {
	private static JavaFunction javaFunction;
	private static Map<String,Browser> browserMap = new HashMap<String, Browser>();
	private static LogUtil log = new LogUtil(JavaFunctionManager.class);
	private static final String JAVA = "JAVA";
	
	
	// 报文要素
	private static final String[] _1 = {"sys","data"};
	private static final String[] _2 = {"code","error","msg"};
	
	
	public static void bind(String browserName,Browser browser){
		browserMap.put(browserName, browser);
		new ComplexFunction(browser, JAVA);
	}
	
	public static void regist(JavaFunction javaFunction){
		String funName = javaFunction.getClass().getSimpleName();
		JavaFunctionManager.javaFunction = javaFunction;
		log.debug(MessageFormat.format("注册Java函数:{0}", funName));
	}
	static class ComplexFunction extends BrowserFunction{

		public ComplexFunction(Browser browser, String name) {
			super(browser, name);
		}
		@Override
		public Object function(Object[] arguments) {
			//2017年3月20日 23:27:37
			//这一块先定义一个通用型json,属性有 code,error,success,errormsg,content
			//数据填充到content部分,然后变成json字符串返回给html页面
			JSONObject responseJson = new JSONObject();
			JSONObject requestJson = null;
			//返回部分
			String code = "";
			String error = "";
			String errormsg = "";
			String content = "";
			//参数部分
			Browser browser = null;
			try{
				String jsonString = arguments[0].toString();
				log.debug(MessageFormat.format("Java函数管理器接收到报文:{0}", arguments));
				requestJson = JSONObject.parseObject(jsonString);
				code = requestJson.getJSONObject(_1[0]).getString(_2[0]);
			} catch (Throwable t){
				error = "-1";
				errormsg = "上送参数有误!";
			}
			try{
				//有可能没有browser这个字段,不报错
				browser = browserMap.get(requestJson.getJSONObject("sys").getString("browser"));
				requestJson.remove("browser");
			} catch (Throwable t){
			}
			try{
				Object obj = javaFunction.function(requestJson,browser);
				if(obj != null){
					content = obj.toString();
				}
				error = "0";
			} catch (RuntimeException e){	//手动抛出运行时异常
				error = "-1";
				errormsg = e.getMessage();
				log.error(errormsg, e);
			} catch (Exception e) {			//程序内的其他非手动抛出异常
				error = "-1";
				errormsg = "上送参数有误!";
				log.error(errormsg, e);
			}
			
			JSONObject sysObj = new JSONObject();
			sysObj.put(_2[0], code);
			sysObj.put(_2[1], error);
			sysObj.put(_2[2], errormsg);
			responseJson.put(_1[0], sysObj);
			responseJson.put(_1[1],content);
//			responseJson.put("code", code)
//						.put("error", error)
//						.put("success", success)
//						.put("errormsg", errormsg)
//						.put("successmsg", successmsg)
//						.put("content", content);
			log.debug(MessageFormat.format("Java函数管理器发送报文:{0}", responseJson.toString()));
			return responseJson.toString();
		}
		
	}
	
}
