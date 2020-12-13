package com.skjanyou.desktop.desktop.view;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 以图片原有的尺寸观看
 * 可以拖拽，单击可以关闭窗口
 * 2015年10月7日 15:25:27
 * @author skjanyou
 *
 */
public class ImageDialog extends Dialog {
	private ImageDialog dialog;
	private int width;
	private int height;
	private boolean isMove = false;   //是否可以移动
	private Point pre_point = null;  //用来记录鼠标移动前点击的坐标
	private Point end_point = null;  //用来记录鼠标移动后点击的坐标
	private JLabel lb_img = null;
	private ImageIcon icon = null;

	public ImageDialog(Frame owner, boolean modal) {
		super(owner, modal);
	}
	public ImageDialog(Frame owner,boolean modal,ImageIcon icon){
		this(owner,modal);
		dialog = this;
		this.icon = icon;
		this.width = icon.getIconWidth();
		this.height = icon.getIconHeight();
		initComponents();
		addComponents();
		initListener();
		this.setSize(width, height);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);


	}
	private void initComponents() {
		lb_img = new JLabel(icon);

	}
	private void addComponents(){
		this.add(lb_img);
	}
	private void initListener() {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				isMove = false;

			}

			@Override
			public void mousePressed(MouseEvent e) {
				isMove = true;
				pre_point = new Point(e.getX(),e.getY());

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				dialog.dispose();				
			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(isMove == true)
				{
					end_point = new Point((int)(dialog.getX() + e.getX() - pre_point.getX()),(int)(dialog.getY() + e.getY() - pre_point.getY()));
					dialog.setLocation(end_point);
				}
				
			}
		});

	}

}
