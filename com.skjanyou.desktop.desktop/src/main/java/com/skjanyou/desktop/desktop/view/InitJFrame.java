package com.skjanyou.desktop.desktop.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;

import com.sun.awt.AWTUtilities;

public class InitJFrame extends JFrame {

	public InitJFrame(){
		this.setSize(580, 400);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		JLabel jlb = new JLabel(new ImageIcon("image/loading.jpg"));
		this.add(jlb);
		this.setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false);
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		this.setVisible(true);
	}
	
}
