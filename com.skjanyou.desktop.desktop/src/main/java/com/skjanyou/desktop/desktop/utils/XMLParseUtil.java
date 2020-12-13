package com.skjanyou.desktop.desktop.utils;

import java.io.File;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * 
 * @author skjanyou
 * 2015年10月26日 19:00:57
 * xml解析工具，提供xml读写
 *
 */
public final class XMLParseUtil {
	private static DocumentBuilderFactory factory = null;
	private static DocumentBuilder builder = null;
	private static Document doc = null;
	private static String xmlPath = "config/config.xml";
	static{
		try{
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(xmlPath));		
		}catch (Exception e){
//			JOptionPane.showMessageDialog(null, "配置文件读取错误，程序即将推出！");
//			System.exit(-1);
		}
	}

	/**
	 * 
	 * 以下部分只供内部调用
	 *
	 */
	/**
	 * 提供对xml文件的修改
	 * @param nodeName 要修改的节点
	 * @param value 值
	 * @return  true为成功，false为失败
	 * @throws Exception
	 */
	private static boolean modifyXML(String nodeName,String value) throws Exception{
		boolean result = false;
		NodeList n = doc.getElementsByTagName(nodeName);
		for(int i = 0; i < n.getLength(); i ++){
			n.item(i).setTextContent(value);
		}
		result = doc2XmlFile(doc, xmlPath);
		return result;
	}

	/**
	 * 
	 * @param document 文档对象，xml的读写，都是基于这个对象
	 * @param filename 保存的文件名（包括路径及名字）
	 * @return 
	 * @throws Exception
	 */
	private static boolean doc2XmlFile(Document document,String filename) throws Exception   
	{   
		boolean flag = true;   
		try   
		{   
			/** 将document中的内容写入文件中   */   
			TransformerFactory tFactory = TransformerFactory.newInstance();      
			Transformer transformer = tFactory.newTransformer();    
			/** 编码 */   
			transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312");   
			DOMSource source = new DOMSource(document);    
			StreamResult result = new StreamResult(new File(filename));      
			transformer.transform(source, result);    
		}catch(Exception ex)   
		{   
			flag = false;   
			throw ex; 
		}   
		return flag;         
	} 
	
	/**
	 * 
	 * 以下部分可以供外部直接调用
	 * 
	 */
	
	/**
	 * 对多线程数量进行修改
	 * @param num 多线程数量
	 * @return true为成功，false为失败
	 */
	public static boolean modifyThreadQuantity(int num){
		boolean result = true;
		try {
			modifyXML("多线程数",num + "");
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	/**
	 * 对漫画存放目录进行修改
	 * @param path 漫画存放路径
	 * @return true为成功，false为失败
	 */
	public static boolean modifySavePath(String path){
		boolean result = true;
		try {
			modifyXML("存放目录",path);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	/**
	 * 对连接时间进行修改
	 * @param num 
	 * @return
	 */
	public static boolean modifyWaitTime(int num){
		boolean result = true;
		try {
			modifyXML("连接等待时间",num + "");
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	/**
	 * 改变退出方式
	 * @param type
	 * @return
	 */
	public static boolean modifyExitType(String type){
		boolean result = true;
		try {
			modifyXML("退出方式",type);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
	public static boolean modifyNotFind(String path){
		boolean result = true;
		try {
			modifyXML("NotFind",path);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	public static boolean modifyPIcon(String path){
		boolean result = true;
		try {
			modifyXML("程序图标",path);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	public static boolean modifyTrayIcon(String path){
		boolean result = true;
		try {
			modifyXML("托盘图标",path);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	public static boolean modifyBackground(String path){
		boolean result = true;
		try {
			modifyXML("背景",path);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	//**********************
	// get方法
	//**********************
	
	/**
	 * 获得保存路径
	 * @return
	 */
	public static String getPath(){
		String result = null;
		NodeList n = doc.getElementsByTagName("存放目录");
		result = n.item(0).getTextContent();
		return result; 
	}
	/**
	 * 获得多线程数量
	 * @return
	 */
	public static int getThreadQuantity(){
		int result = 0;
		NodeList n = doc.getElementsByTagName("多线程数");
		result = Integer.parseInt(n.item(0).getTextContent());
		return result;
	}
	/**
	 * 获得连接等待时间
	 * @return
	 */
	public static int getWaitTime(){
		int result = 0;
		NodeList n = doc.getElementsByTagName("连接等待时间");
		result = Integer.parseInt(n.item(0).getTextContent());
		return result;
	}
	/**
	 * 获得退出方式
	 * @return
	 */
	public static String getExitType(){
		String result = null;
		NodeList n = doc.getElementsByTagName("退出方式");
		result = n.item(0).getTextContent();
		return result; 
	}
	/**
	 * 获得没有连接成功时，展示的图片路径
	 * @return
	 */
	public static String getNotFind(){
		String result = null;
		NodeList n = doc.getElementsByTagName("NotFind");
		result = n.item(0).getTextContent();
		return result; 
	}
	/**
	 * 获得程序图标路径
	 * @return
	 */
	public static String getPIcon(){
		String result = null;
		NodeList n = doc.getElementsByTagName("程序图标");
		result = n.item(0).getTextContent();
		return result; 
	}
	/**
	 * 获得程序托盘图标路径
	 * @return
	 */
	public static String getTrayIcon(){
		String result = null;
		NodeList n = doc.getElementsByTagName("托盘图标");
		result = n.item(0).getTextContent();
		return result; 
	}
	public static String getBackground(){
		String result = null;
		NodeList n = doc.getElementsByTagName("背景");
		result = n.item(0).getTextContent();
		return result; 
	}
	
}
