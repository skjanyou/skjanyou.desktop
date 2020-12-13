package com.skjanyou.desktop.application.cicd;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skjanyou.appl.AppAnnotationloader.ApiRoute;
import com.skjanyou.database.dao.DefaultDao;
import com.skjanyou.database.dao.SkjanyouDao;
import com.skjanyou.database.util.DbUtil;
import com.skjanyou.desktop.application.cicd.bean.Command;
import com.skjanyou.desktop.application.cicd.bean.Computer;
import com.skjanyou.desktop.application.cicd.bean.Operation;
import com.skjanyou.desktop.application.cicd.bean.Parameter;

public class CICDController {
	
	@ApiRoute(value = "/cicd/shell/add")
	public Object addShell(){
		return null;
	}
	
	@ApiRoute(value = "/cicd/shell/del")
	public Object delShell(){
		return null;
	}
	
	@ApiRoute(value = "/cicd/shell/modify")
	public Object modifyShell(){
		return null;
	}
	
	@ApiRoute(value = "/cicd/shell/detail")
	public Object detailShell(){
		return null;
	}
	
	@ApiRoute(value = "/cicd/shell/query")
	public Object queryShell(){
		return null;
	}

	@ApiRoute(value = "/cicd/shell/testmenu")
	public JSONArray testMenu(JSONObject json){
		//{name:"张三",zhiye:"程序员",sex:"性别",age:18,role:"打工的",status:"在职"}
		JSONArray root = new JSONArray();
		
		for(int i = 0; i < 10; i++){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("name", "测试数据" + i);
			jsonObj.put("zhiye", "测试职业" + i);
			jsonObj.put("sex", "男");
			jsonObj.put("age", "18");
			jsonObj.put("role", "自由职业人");
			jsonObj.put("status", "离职");
			
			root.add(jsonObj);
		}
		
		return root;
	}
	
	@ApiRoute(value = "/cicd/shell/menu")
	public Object menu(JSONObject json){
		JSONArray root = new JSONArray();
		JSONObject level_1 = new JSONObject();
		level_1.put("id", "0");
		level_1.put("title", "持续交付");
		level_1.put("icon", "layui-icon");
		JSONArray children = new JSONArray();
		
		JSONObject c_01 = new JSONObject();
		c_01.put("id", "01");
		c_01.put("title", "主机管理");
		c_01.put("icon", "&#xe612");
		c_01.put("url", "html/zhuji.html");
		
		JSONObject c_02 = new JSONObject();
		c_02.put("id", "01");
		c_02.put("title", "命令管理");
		c_02.put("icon", "&#xe612");
		c_02.put("url", "html/mingling.html");
		
		JSONObject c_03 = new JSONObject();
		c_03.put("id", "01");
		c_03.put("title", "操作管理");
		c_03.put("icon", "&#xe612");
		c_03.put("url", "html/caozuo.html");		
		
		children.add(c_01);
		children.add(c_02);
		children.add(c_03);
		
		level_1.put("children", children);
		root.add(level_1);
		
		return root;
	}
	
	@ApiRoute(value = "/cicd/shell/do")
	public Object doShell(JSONObject jsonObject){
		String cmd = jsonObject.getString("cmd");
		Shell shell = new Shell("140.143.29.17","skjanyou","tan2501707830");
		shell.execute(cmd);
		List<String> list = shell.getStandardOutput();
		for (String string : list) {
			System.out.println(string);
		}
		return list;
	} 
	
	@SuppressWarnings("unchecked")
	@ApiRoute(value = "/cicd/shell/do")
	public Object doShell2(JSONObject jsonObject){
		
		String cip = jsonObject.getString("cip");
		String cport = jsonObject.getString("cport");
		String comId = jsonObject.getString("comid");
		String pid = jsonObject.getString("pid");	
		String oid = jsonObject.getString("oid");
		
		SkjanyouDao skjanyou = new DefaultDao(){};
		Computer condition1 = new Computer(cip,Integer.parseInt(cport));
		Computer computer = (Computer) skjanyou.detail(condition1);
		Command condition2 = new Command(comId);
		Command command = (Command) skjanyou.detail(condition2);
		Parameter condition3 = new Parameter(pid);
		Parameter parameter = (Parameter) skjanyou.detail(condition3);
		
		System.out.println("开始组装ssh命令:");
		
		
		System.out.println("组装的ssh命令为:");
		Shell shell = new Shell(computer.getIp(), computer.getUsername(), computer.getPassword());
		
		
		return null;
	}

}
