package com.skjanyou.desktop.desktop;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import com.skjanyou.appl.AppAnnotationloader.util.AnnotationUtil;
import com.skjanyou.desktop.desktop.bean.Executor;
import com.skjanyou.desktop.desktop.swt.event.ShellEvent;
import com.skjanyou.desktop.desktop.swt.event.ShellFunction;
import com.skjanyou.desktop.desktop.utils.ScanUtil;

public class Test {

	public static void main(String[] args) {
		//		Properties p = PropertiesUtil.loadProperties("config/mimetypes.txt").getProperties();
		//		p.forEach((key,value)->{
		//			String msg = MessageFormat.format("{0}(\"{1}\"),", key.toString().toUpperCase(), value);
		//			System.out.print(msg);
		//		});


		//		Reflections reflections = new Reflections(".");
		//		System.out.println(reflections.toString());
		//		Set<String> set = reflections.getResources(Pattern.compile(".*\\.properties"));
		//		for (String string : set) {
		//			System.out.println(string);
		//		}

//		Predicate<String> filter = new FilterBuilder().include(".*\\.view\\.properties")
//
//				.exclude("META-INF//*");
//		Reflections reflections = new Reflections(new ConfigurationBuilder()
//		.filterInputsBy(filter)
//		.setScanners(new ResourcesScanner())
//		.setUrls(ClasspathHelper.forPackage(".")));
//
//		Set<String> str = reflections.getResources(Pattern.compile(".*\\.view\\.properties"));
//		for(String s : str){
//			System.out.println(s);
//		}
		
		
		Set<String> str = ScanUtil.scanResources(ScanUtil.ROOT, ".*\\.view\\.properties", ScanUtil.ALL, ScanUtil.NONE);
		for(String s : str){
			System.out.println(s);
		}
		
		List<Method> listMethod = AnnotationUtil.getMethodByAnnotation(ShellEvent.class, ShellFunction.class);
		listMethod.forEach( method -> {
			Executor e = new Executor();
			e.setMethod(method);
			e.setClazz(ShellEvent.class);
			
			ShellFunction sf = AnnotationUtil.getMethodAnnotation(method, ShellFunction.class);
		});
		System.out.println(listMethod);
	}

}
