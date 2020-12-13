package com.skjanyou.desktop.desktop.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/***
 * 
 * 图片的加工处理，包括变大，变小，变灰。
 * expand(BufferedImage image,float resizeTimes).表示将图片放大。resizeTimes表示倍数，浮点型。
 * contract(),表示图片收缩，不带参用于上传头像压缩,带参用于聊天时图片压缩
 * pale(),图片变灰，用于用户下线时头像的变化。
 * 
 */

public abstract  class ImageUtil {
	
	/**
	 * 实现图片的大小变换
	 * 2015年10月5日 21:25:24
	 * @param filePath
	 * @param toHeight
	 * @param toWidth
	 * @return ImageIcon
	 */
	public static ImageIcon getIcon(String filePath,int toWidth,int toHeight){
		return new ImageIcon((Image)contract(getImage(filePath),toWidth,toHeight));
	}
	/**
	 * 实现网络图片的下载以及大小变换
	 * 2015年10月5日 21:25:31
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public static ImageIcon getIcon(URL url,int toWidth,int toHeight) throws Exception{
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("Referer", url.toString());
		InputStream is = conn.getInputStream();
		BufferedImage img = ImageIO.read(is);
		
		return new ImageIcon((Image)contract(img,toWidth,toHeight));
	}
	public static ImageIcon getIcon(Image img,int toWidth,int toHeight) throws Exception{
		if(img.getWidth(null) == -1 || img.getHeight(null) == -1){
			throw new Exception("加载失败");
		}
		BufferedImage bImg = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bImg.createGraphics();
		g.drawImage(img,null,null);
		return new ImageIcon((Image)contract(bImg,toWidth,toHeight));
	}
	public static ImageIcon getIcon(String filePath)
	{
		return new ImageIcon((Image)getImage(filePath));
	}
	
	public static ImageIcon getHeadIcon(String filePath)
	{
		BufferedImage image = getImage(filePath);
		image = contract(image,69,54);
		return new ImageIcon((Image)image);
		
	}
	
	public static ImageIcon getPaleHeadIcon(String filePath)
	{
		BufferedImage image = getImage(filePath);
		image = contract(image,69,54);
		image = pale(image);
		return new ImageIcon((Image)image);
		
	}
	
	public static BufferedImage getImage(String filePath)
	{
		BufferedImage result = null;
		try {
			result = ImageIO.read(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 图片放大功能
	 * @param image：所需要处理的图片流
	 * @param resizeTimes：相对于原图片的倍数，float型，大于1时为放大。
	 * @return 新的bufferedImage类型
	 */

	public static BufferedImage expand(BufferedImage image,float resizeTimes)
	{
		BufferedImage resultImage = null;
		int width = image.getWidth();
		int height = image.getHeight();
		
		int toWidth = (int) (width * resizeTimes);  
        int toHeight = (int) (height * resizeTimes);
        
        resultImage = new BufferedImage(toWidth,toHeight,BufferedImage.TYPE_INT_BGR);
		
        resultImage.getGraphics().drawImage(  
                image.getScaledInstance(toWidth, toHeight,  
                        java.awt.Image.SCALE_SMOOTH), 0, 0, null);  
		return resultImage;
	}
	/**
	 * 图片的固定倍数0.5缩小，用于做头像
	 * @param image：用于处理的图片流
	 * @return 新的图片流
	 */
	
	public static BufferedImage contract(BufferedImage image)
	{
		BufferedImage resultImage = null;
		int width = image.getWidth();
		int height = image.getHeight();
		
		float resizeTimes = 0.5f;
		
		int toWidth = (int) (width * resizeTimes);  
        int toHeight = (int) (height * resizeTimes);
        
        resultImage = new BufferedImage(toWidth,toHeight,BufferedImage.TYPE_INT_BGR);
		
        resultImage.getGraphics().drawImage(  
                image.getScaledInstance(toWidth, toHeight,  
                        java.awt.Image.SCALE_SMOOTH), 0, 0, null);  
		return resultImage;
	}
	
	/**
	 * 可以自由指定宽高的图片缩小功能，可用于聊天窗口中图片的缩小
	 * @param image：要进行处理的图片流
	 * @param toHeight：改变后的高
	 * @param toWidth：改变后的宽
	 * @return 新的图片流
	 */
	public static BufferedImage contract(BufferedImage image,int toWidth,int toHeight)
	{
		BufferedImage resultImage = null;

        resultImage = new BufferedImage(toWidth,toHeight,BufferedImage.TYPE_INT_BGR);
		
        resultImage.getGraphics().drawImage(  
                image.getScaledInstance(toWidth, toHeight,  
                        java.awt.Image.SCALE_SMOOTH), 0, 0, null); 
		return resultImage;
	}
	
	/**
	 * 图片的灰色处理
	 * @param image：要进行处理的图片流
	 * @return 新的图片流
	 */
	public static BufferedImage pale(BufferedImage image)
	{
		int width = image.getWidth();
		int height = image.getHeight();
		
		
		BufferedImage resultImage = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		ColorConvertOp colorConvertOp = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY),null);
		colorConvertOp.filter(image, resultImage);
		return resultImage;
	}
	
	/**
	 * 保存图片文件到硬盘上
	 * @param image：进行保存的图片流
	 * @param savePath：保存路径
	 * @param name：文件名字，要求以png格式保存
	 * @return 是否保存成功
	 */
	public static boolean saveImage(BufferedImage image,String format,String savePath,String name) { 
		boolean result = true;
        try {  
            File f = new File(savePath + name);
            ImageIO.write(image,format, f);
        } catch (Exception e) {  
            result = false;  
        }  
        
        return result;
        
    }  
	/**
	 * 2015年10月10日 23:48:17
	 * @param image
	 * @param savePath
	 * @param name
	 * @return
	 */
	public static boolean saveImage(ImageIcon image,String format,String savePath,String name){
		boolean result = true;
        try {  
            File f = new File(savePath + "\\" + name + "." + format);
            Image img = image.getImage();
            BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bi.createGraphics();
            g2d.drawImage(img,0,0,null);
            g2d.dispose();
            ImageIO.write(bi,format,f);
        } catch (Exception e) {  
            result = false;  
        }  
       
        return result;
	}
	
}
