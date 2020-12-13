package com.skjanyou.desktop.desktop.swt.event;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Shell;

import com.alibaba.fastjson.JSONObject;

public class ShellEvent {
	
	/**
	 * 窗口最大化
	 * @param shell
	 * @return
	 */
	@ShellFunction(value = "Max")
	public JSONObject max(Browser browser){
		Shell shell = browser.getShell();
		JSONObject resultObject = null;
		shell.setMaximized(true);
		shell.setFullScreen(true);
		//窗口前置,但是需要知道屏幕大小才能填充满
//		OS.SetWindowPos(shell.handle, OS.HWND_TOPMOST, 0, 0, 0,0,
//                SWT.NULL);
		return resultObject;
	}
	
	@ShellFunction(value = "Min")
	public JSONObject min(Browser browser){
		Shell shell = browser.getShell();
		shell.setMinimized(true);
		shell.setFullScreen(false);
		return null;
	}
	
	@ShellFunction(value = "Normal")
	public JSONObject normal(Browser browser){
		Shell shell = browser.getShell();
		shell.setFullScreen(false);
		return null;
	}
	
	@ShellFunction(value = "Exit")
	public JSONObject exit(Browser browser){
		System.exit(0);
		return null;
	}
}
