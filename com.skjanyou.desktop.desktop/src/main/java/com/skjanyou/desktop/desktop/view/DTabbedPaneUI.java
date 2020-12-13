package com.skjanyou.desktop.desktop.view;
/**
 * 
 * 2015年12月28日 22:50:42
 * skjanyou
 * 对TabbedPane进行一些界面上的处理
 * 
 * */
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class DTabbedPaneUI extends BasicTabbedPaneUI{
	//选定标签卡的颜色
	private Color bg_select = new Color(46,152,223);
	//未选定标签卡的颜色
	private Color bg_notSelect = Color.white;

	
    /*可以用来改变显示文字的字体，未来可能会用到*/
	@Override
	protected void paintText(Graphics g, int tabPlacement, Font font,
			FontMetrics metrics, int tabIndex, String title,
			Rectangle textRect, boolean isSelected) {
		super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect,
				isSelected);
	}

	/*去掉标签卡的边框*/
	@Override
	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
			int x, int y, int w, int h, boolean isSelected) {
//		super.paintTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
	}

	/*对绘制标签卡进行重写，改变选定前后的颜色与绘制方式*/
	@Override
	protected void paintTabBackground(Graphics g, int tabPlacement,
			int tabIndex, int x, int y, int w, int h, boolean isSelected) {
//		super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
		if(isSelected){
			g.setColor(bg_select);
			g.fillRect(x+1, y+1, w-3, h-1);
		}else{
			g.setColor(bg_notSelect);
			g.fillRect(x+1, y+1, w-3, h-1);
		}
	}

	/*取消选定时出现的框*/
	@Override
	protected void paintFocusIndicator(Graphics g, int tabPlacement,
			Rectangle[] rects, int tabIndex, Rectangle iconRect,
			Rectangle textRect, boolean isSelected) {
//		super.paintFocusIndicator(g, tabPlacement, rects, tabIndex, iconRect, textRect,
//				isSelected);
	}

}
