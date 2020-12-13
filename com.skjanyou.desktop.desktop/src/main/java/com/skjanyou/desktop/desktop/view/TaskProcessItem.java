package com.skjanyou.desktop.desktop.view;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

import com.skjanyou.desktop.desktop.utils.DownloadUtil;


public class TaskProcessItem extends JProgressBar implements Runnable{
	private TaskProcessItem tpi = this;
	private Timer timer = new Timer(); 
	private MyTimerTask myTimerTask = new MyTimerTask();
	private int size = 0;
	private String dec = null;
	private String animeName = null;
	private String chapter = null;
	private List<String> list = null;
	private JLabel lb_process = null;
	
	public TaskProcessItem() {
		super();
	}
	public TaskProcessItem(int min, int max,String dec,String animeName,String chapter,List<String> list,JLabel lb_process){
		this(min,max);
		this.size = max;
		this.dec = dec;
		this.animeName = animeName;
		this.chapter = chapter;
		this.list = list;
		this.lb_process = lb_process;
	}
	public TaskProcessItem(int min, int max) {
		super(min, max);
		addMouseListener(new MouseAdapter() {
			private Border b = tpi.getBorder();
			private int i = 0;
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == e.BUTTON1){
					if(tpi.getValue() == 100){
						return;
					}
					if(i % 2 == 0){
						myTimerTask.setPause(true);
					}else{
						myTimerTask.setPause(false);
					}
					i++;
				}else if(e.getButton() == e.BUTTON3){
				}

			}
			@Override
			public void mouseEntered(MouseEvent e) {
				tpi.setCursor(new Cursor(Cursor.HAND_CURSOR));
				tpi.setBorder(BorderFactory.createEmptyBorder(-2, -2, -2, -2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tpi.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				tpi.setBorder(b);
			}
		});
	}

	@Override
	public void run() {
		timer.schedule(myTimerTask, 1 * 1000,1 * 1000);
	}



	class MyTimerTask extends TimerTask{
		private boolean pause = false;
		@Override
		public void run() {
			if(!pause){
				if(dec.charAt(dec.length() - 1) != '/'){
					dec += "/";
				}
				int value = tpi.getValue();
				try {
					new DownloadUtil().saveFile(list.get(value),dec + animeName + "/" + chapter,value + 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				value += 1;
				if(value >= size){
					timer.cancel();
					Container c = tpi.getParent();
					c.remove(tpi);
					c.validate();
					c.repaint();
				}
				tpi.setValue(value);
				tpi.setString("正在下载" + animeName + "-" + chapter + ",已完成" +  value + "/" + size);
				
			}else{
				tpi.setString("已暂停");
			}
		}
		public void setPause(boolean pause) {
			this.pause = pause;
		}	
	}

}
