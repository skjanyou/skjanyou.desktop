package com.skjanyou.desktop.desktop.config;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;


public class ShellManager {
	private static ShellManager shellManager = new ShellManager();
	private static List<Shell> shellList = new ArrayList<Shell>();
	public static boolean stopFlag = false;
	private ShellManager(){}

	public static ShellManager regist(Shell shell){
		if(!shellList.contains(shell)){
			shellList.add(shell);			
		}
		return shellManager;
	}
	public static ShellManager unRegist(Shell shell){
		if(shellList.contains(shell)){
			shellList.remove(shell);
		}
		return shellManager;
	}
	public static ShellManager show(Shell shell){
		check(shell);
		if(!shell.isDisposed() && !shell.isVisible()){
			shell.open();
			shell.layout();
			shell.setVisible(true);
		}
		return shellManager;
	}
	public static ShellManager hide(Shell shell){
		check(shell);
		if(!shell.isDisposed()){
			shell.setVisible(false);
		}
		return shellManager;
	}
	
	private static void check(Shell shell){
		if(shell == null){
			throw new RuntimeException("shell is null!");
		}
		if(!shellList.contains(shell)){
			throw new RuntimeException("shell not regist!");
		}
	}
}
