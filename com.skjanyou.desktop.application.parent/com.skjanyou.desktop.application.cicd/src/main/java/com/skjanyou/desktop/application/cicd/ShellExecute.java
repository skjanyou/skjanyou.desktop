package com.skjanyou.desktop.application.cicd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ShellExecute {
	public int execute(final String command,Shell shell) {
		List<String> stdout = new ArrayList<String>();
		BufferedReader input = null;
		try{
			Session session = shell.getSession();
			//打开通道，设置通道类型，和执行的命令
			Channel channel = session.openChannel("exec");
			ChannelExec channelExec = (ChannelExec)channel;
			channelExec.setCommand(command);

			channelExec.setInputStream(null);
			input = new BufferedReader(new InputStreamReader
					(channelExec.getInputStream()));

			channelExec.connect();
			System.out.println("The remote command is :" + command);

			//接收远程服务器执行命令的结果
			String line;
			while ((line = input.readLine()) != null) {  
				stdout.add(line);  
			}  


			// 关闭通道
			channelExec.disconnect();
			//关闭session
			session.disconnect();			 
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSchException e) {
			e.printStackTrace();
		} finally{
			if(input != null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
}
