package com.skjanyou.desktop.application.cicd;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import com.alibaba.fastjson.JSONObject;
import com.skjanyou.database.dao.DefaultDao;
import com.skjanyou.database.dao.SkjanyouDao;
import com.skjanyou.database.util.DbUtil;
import com.skjanyou.desktop.application.cicd.bean.Computer;
import com.skjanyou.desktop.application.cicd.bean.Operation;
import com.skjanyou.desktop.application.cicd.util.PatternUtil;

public class Test {
	public static void main(String[] args) {
		DbUtil.getConfig().addAliasRegistry("com.skjanyou.desktop.application.cicd.bean");
		DbUtil.getConfig().addMapperRegistry("com.skjanyou.desktop.application.cicd.mapper");
		DbUtil.getConfig().setAutoCommit(false);
//		testOperation();
//		testDoShell();
		String words = "ls {dir} xxxx";
		String regex = "(?<=\\{)(\\S+)(?=\\})";
		Map<String,String> data = new HashMap<String, String>();
		data.put("dir","/suncard");
		String list = PatternUtil.getGenString(regex, words, data);
		System.out.println(list);
		
	}
	
	public static void testComputerDB(){
		SkjanyouDao<Computer> skjanyou = new DefaultDao<Computer>(){};		
		Computer c = new Computer("localhost",100);
		List<Computer> list = skjanyou.query(c);
		list.forEach( cc -> {
			System.out.println(cc.getIp());
		});
	}
	
	public static void testOperation(){
		SkjanyouDao<Operation> skjanyou = new DefaultDao<Operation>(){};
		
		Collection<String> coll = DbUtil.getMappedStatementNames();
		coll.forEach( string -> {
			System.out.println(string);
		});
		Operation op = new Operation();
		op.setOprid("ls");
		op.setDesc("查看命令");
		
		String sql = DbUtil.getSql("com.skjanyou.desktop.application.cicd.mapper.OperationMapper.insert", op);
		System.out.println(sql);
		boolean result = skjanyou.insert(op);
		Assert.assertTrue(result);
		
		Operation s = skjanyou.detail(op);
		Assert.assertNotNull(s);

		List<Operation> list = skjanyou.query(s);
		list.forEach( o -> {
			System.out.println(o);
		});
		System.out.println("-----------------------");
		List<Operation> list2 = skjanyou.queryByPage(s, 3, 5);
		list2.forEach( o -> {
			System.out.println(o.getOprid());
		});
		
		int r = skjanyou.update(s);
		Assert.assertEquals(1, r);
		
//		int r2 = skjanyou.delete(s);
//		Assert.assertEquals(1, r2);

	}
	
	public static void testDoShell(){
		JSONObject json = new JSONObject();
		json.put("cip", "1");
		json.put("cport", "1");
		json.put("comid", "ls");
		CICDController cicd = new CICDController();
		cicd.doShell2(json);
	}
}
