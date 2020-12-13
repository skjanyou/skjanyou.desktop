package com.skjanyou.desktop.desktop.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

/**
 * 用于在线观看漫画的显示窗口
 * 2015年10月6日 14:53:11
 * @author skjanyou
 *
 */
public class AnimeOnlineJFrame extends JFrame {
	private final int width = 700;
	private final int height = 700;
	private AnimeOnlineJFrame animeOnlieJFrame = null;
	private String animeName = null;
	private String chapterName = null;
	private String animeURL = null;
	private JLabel lb_img = null;
	private JLabel lb_prompt = null;
	private JLabel lb_pre = null;
	private JLabel lb_next = null;
	private JPanel center = null;
	private JPanel north = null;
	private JPanel west = null;
	private JPanel east = null;
	private JPanel south = null;
	private JPanel north_inner_jp = null;
	private JComboBox jcb_index = null;
	private JButton btn_download = null;
	
	//总共页数
	private int pageCount = 0;
	//当前页数
	private int currentPage = 0;
	//原图片
	private ImageIcon icon = null;
	//压缩后的图片
	private ImageIcon contractIcon = null;
	private List<String> list = null;
	public AnimeOnlineJFrame(String animeName,String chapterName,final String animeURL){
		this.chapterName = chapterName;
		this.animeURL = animeURL;
		this.setLayout(new BorderLayout());
		initComponents();
		addComponents();
		initListener();
		this.setSize(width, height);
		this.setTitle(animeName + chapterName + "-在线观看");	
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		animeOnlieJFrame = this;
		new Thread(
				new Runnable() {
					public void run() {
						if(list == null){
							return;
						}
						pageCount = list.size();
						//						for (String string : list) {
						//							System.out.println(string);
						//						}	
						try {
							icon = new ImageIcon(new URL(list.get(0)));
							Image img = icon.getImage();
//							contractIcon = ImageUtil.getIcon(img, width - 50, height - 40);
//							icon = ImageUtil.getIcon(new URL(list.get(0)), width - 50,height - 40);
						}catch (Exception e) {
							System.out.println("连接不上");
//							contractIcon = ImageUtil.getIcon(Config.nofind, width - 50, height - 40);
//							e.printStackTrace();
						}
//						System.out.println("数据开始加载");
						if(list.size() > 0){
							lb_prompt.setText("加载完成，总共有" + list.size() + "页");
							Integer[] array = new Integer[pageCount];
							for(int i = 0;i < pageCount;i++){
								array[i] = i + 1;
							}
							jcb_index = new JComboBox(array);
							jcb_index.addActionListener(new ActionListener() {								
								@Override
								public void actionPerformed(ActionEvent e) {
									final int selectIndex = jcb_index.getSelectedIndex();
									if(selectIndex != currentPage){
										new Thread(
												new Runnable() {
													public void run() {
														try {
															icon = new ImageIcon(new URL(list.get(selectIndex)));
															Image img = icon.getImage();
//															contractIcon = ImageUtil.getIcon(img, width - 50, height - 40);
															currentPage = selectIndex;
														}catch (Exception e1) {
//															System.out.println("连接不上");
															lb_prompt.setText("连接失败");
//															contractIcon = ImageUtil.getIcon(Config.nofind, width - 50, height - 40);
															jcb_index.setSelectedIndex(currentPage);
														}
														lb_img.setIcon(contractIcon);
													}
												}).start();
									}
									
								}
							});
							north_inner_jp.add(new JLabel(",当前第"));
							north_inner_jp.add(jcb_index);
							north_inner_jp.add(new JLabel("页"));
							btn_download = new JButton("下载本页");
							btn_download.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									JFileChooser jfc = new JFileChooser();
									File f = null;
									jfc.addChoosableFileFilter(new MyFileFilter(".jpg", "jpg 文件 (*.jpg)"));
									//				jfc.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
//									jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
									jfc.setCurrentDirectory(new File("c:/"));
									int op = jfc.showSaveDialog(null);
									if (op == jfc.APPROVE_OPTION) {
										File saveFile = jfc.getSelectedFile();
										String name = saveFile.getName();
										String path = saveFile.getPath().substring(0,saveFile.getPath().indexOf(name));										MyFileFilter myff = (MyFileFilter) jfc.getFileFilter();
										String ends = myff.getEnds();
										String format = ends.substring(1, ends.length());
//										System.out.println("format:" + format + ",path:" + path + ",name:" + name);
//										ImageUtil.saveImage(icon, format,path,name);
									}
								}
							});
							
							north_inner_jp.add(btn_download);
							lb_img.setIcon(contractIcon);
						}
//						System.out.println("数据加载完毕");
					}
				}).start();

	}

	public void initComponents(){
		lb_prompt = new JLabel("正在加载中..");
		lb_img = new JLabel();
		lb_pre = new JLabel("<html>上<br>一<br>页</html>");
		lb_next = new JLabel("<html>下<br>一<br>页</html>");
		north = new JPanel();
		center = new JPanel();
		east = new JPanel();
		west = new JPanel();
		south = new JPanel();
		north_inner_jp = new JPanel();
		north_inner_jp.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		east.setCursor(new Cursor(Cursor.HAND_CURSOR));
		west.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    
		north.setBackground(new Color(245,245,245));
//		lb_prompt.setPreferredSize(new Dimension(width - 300, 30));
		lb_img.setPreferredSize(new Dimension(width - 100,height - 50));
//		center.setBackground(Color.red);
		lb_pre.setPreferredSize(new Dimension(30, height - 50));
		east.setBackground(new Color(245,245,245));
		lb_next.setPreferredSize(new Dimension(30, height - 50));
		west.setBackground(new Color(245,245,245));
		
	}
	public void addComponents(){
		north_inner_jp.add(lb_prompt);
		north.add(north_inner_jp);
		center.add(lb_img);
		east.add(lb_next);
		west.add(lb_pre);
		this.add(north,BorderLayout.NORTH);
		this.add(center,BorderLayout.CENTER);
		this.add(east,BorderLayout.EAST);
		this.add(west,BorderLayout.WEST);
		this.add(south,BorderLayout.SOUTH);
	}
	public void initListener(){
		
		center.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(icon == null || icon.getIconHeight() == -1 || icon.getIconWidth() == -1){
					JOptionPane.showMessageDialog(null, "图片还没有加载完成，请稍候再试！");
					return;
				}
				new ImageDialog(animeOnlieJFrame,true,icon);
				
			}
		});
		west.addMouseListener(new MouseListener() {
			Color c = west.getBackground();
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				west.setBackground(c);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				west.setBackground(Color.red);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
//				System.out.println("上一页");
				if(currentPage <= 0){
					JOptionPane.showMessageDialog(null, "本章已经到开头了");
					return;
				}
				currentPage--;
				new Thread(
						new Runnable() {
							public void run() {
								ImageIcon contractIcon = null;
								try {
//									icon = ImageUtil.getIcon(new URL(list.get(currentPage)), width - 50,height - 40);
									icon = new ImageIcon(new URL(list.get(currentPage)));
									Image img = icon.getImage();
//									contractIcon = ImageUtil.getIcon(img, width - 50, height - 40);									
								}catch (Exception e1) {
									System.out.println("连接不上");
//									currentPage++;
//									contractIcon = ImageUtil.getIcon(Config.nofind, width - 50, height - 40);
								}
								lb_img.setIcon(contractIcon);
								jcb_index.setSelectedIndex(currentPage);
//								System.out.println(currentPage);

								jcb_index.repaint();
							}
						}).start();
			}
		});
		east.addMouseListener(new MouseListener() {
			Color c = east.getBackground();
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				east.setBackground(c);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				east.setBackground(Color.red);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
//				System.out.println("下一页");
				if(currentPage >= pageCount - 1){
					JOptionPane.showMessageDialog(null, "本章已经到末尾了。");
					return;
				}
				currentPage++;
				new Thread(
						new Runnable() {
							public void run() {
								try {
//									icon = ImageUtil.getIcon(new URL(list.get(currentPage)), width - 50,height - 40);
									icon = new ImageIcon(new URL(list.get(currentPage)));
									Image img = icon.getImage();
//									contractIcon = ImageUtil.getIcon(img, width - 50, height - 40);
								}catch (Exception e1) {
									System.out.println("连接不上");
//									currentPage--;
//									contractIcon = ImageUtil.getIcon(Config.nofind, width - 50, height - 40);
								}
								lb_img.setIcon(contractIcon);
								jcb_index.setSelectedIndex(currentPage);
//								System.out.println(currentPage);
								jcb_index.repaint();
							}
						}).start();
				
				
				
			}
		});

	}
	
	class MyFileFilter extends FileFilter {

		  String ends; // 文件后缀
		  String description; // 文件描述文字

		  public MyFileFilter(String ends, String description) { // 构造函数
		    this.ends = ends; // 设置文件后缀
		    this.description = description; // 设置文件描述文字
		  }

		  @Override
		  // 只显示符合扩展名的文件，目录全部显示
		  public boolean accept(File file) {
		    if (file.isDirectory()) return true;
		    String fileName = file.getName();
		    if (fileName.toUpperCase().endsWith(this.ends.toUpperCase())) return true;
		    return false;
		  }

		  @Override
		  // 返回这个扩展名过滤器的描述
		  public String getDescription() {
		    return this.description;
		  }
		  
		  // 返回这个扩展名过滤器的扩展名
		  public String getEnds() {
		    return this.ends;
		  }
		}
}
