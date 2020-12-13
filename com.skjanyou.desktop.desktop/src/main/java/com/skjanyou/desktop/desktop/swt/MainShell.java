package com.skjanyou.desktop.desktop.swt;

import java.io.File;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.AuthenticationEvent;
import org.eclipse.swt.browser.AuthenticationListener;
import org.eclipse.swt.browser.CloseWindowListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.wb.swt.SWTResourceManager;

import com.skjanyou.appl.Application.webkit.AbstractShell;
import com.skjanyou.desktop.desktop.config.JavaFunctionManager;
import com.skjanyou.desktop.desktop.config.ShellManager;
import com.skjanyou.desktop.desktop.utils.SystemUtil;


public class MainShell extends AbstractShell {
	//获取系统配置
	private Properties properties = SystemUtil.getSystemProperties();
	
	public MainShell(Display display) {
		super(display, SWT.SHELL_TRIM | SWT.BORDER);
		createContents();	//创建窗体
		initComponents();	//初始化组件
		initListeners();	//初始化监听器
	}

	public void createContents() {
		setText(properties.getProperty("title"));
		setMinimumSize(new Point(1000, 800));
		setImage(SWTResourceManager.getImage("image/icon.png"));
		setLayout(new FillLayout());		//窗体内组件会填充满整个窗体,这里的组件是browser
	}
	
	public void initComponents(){
		//设置页面
		String url = properties.getProperty("mainshell.url");
		String inner_switch = properties.getProperty("INNER_SWITCH"); 
		if("OFF".equals(inner_switch)){
			browser.setUrl(new File(url).toURI().toString());
		}else if("ON".equals(inner_switch)){
			browser.setUrl(url);
		}
		
		//Java函数的绑定
		JavaFunctionManager.bind("main.browser",browser);
	}
	public void initListeners(){
		addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
			}
		});
		addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent e) {
				ShellManager.stopFlag = true;
			}
		});
		this.browser.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				System.out.println("DisposeListener");
			}
		});
		this.browser.addAuthenticationListener(new AuthenticationListener() {
			@Override
			public void authenticate(AuthenticationEvent event) {
				System.out.println("AuthenticationListener");
			}
		});
		this.browser.addCloseWindowListener(new CloseWindowListener() {
			@Override
			public void close(WindowEvent event) {
				System.out.println("CloseWindowListener");
			}
		});
		this.browser.addControlListener(new ControlListener() {
			@Override
			public void controlResized(ControlEvent e) {
				System.out.println("ControlListener.controlResized");
			}
			
			@Override
			public void controlMoved(ControlEvent e) {
				System.out.println("ControlListener.controlMoved");
			}
		});
		this.browser.addDragDetectListener(new DragDetectListener() {
			@Override
			public void dragDetected(DragDetectEvent e) {
				System.out.println("DragDetectListener");
			}
		});
		this.browser.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("FocusListener.focusLost");
			}
			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("FocusListener.focusGained");
			}
		});
		this.browser.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println("KeyListener.keyReleased");
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("KeyListener.keyPressed");
			}
		});
		this.browser.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("FocusListener.focusLost");
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("FocusListener.focusGained");
			}
		});
		this.browser.addOpenWindowListener(new OpenWindowListener() {
			@Override
			public void open(WindowEvent event) {
				System.out.println("OpenWindowListener.open");
				// 防止新开浏览器窗口
				event.browser = browser;
				System.out.println(event.data);
			}
		});
		this.browser.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				System.out.println("TraverseListener.keyTraversed");
			}
		});
		this.browser.addHelpListener(new HelpListener() {
			@Override
			public void helpRequested(HelpEvent e) {
				System.out.println("HelpListener.helpRequested");
			}
		});
		this.addShellListener(new ShellListener() {
			@Override
			public void shellIconified(ShellEvent e) {
				System.out.println("ShellListener.shellIconified");
			}
			
			@Override
			public void shellDeiconified(ShellEvent e) {
				System.out.println("ShellListener.shellDeiconified");
			}
			
			@Override
			public void shellDeactivated(ShellEvent e) {
				System.out.println("ShellListener.shellDeactivated");
			}
			
			@Override
			public void shellClosed(ShellEvent e) {
				System.out.println("ShellListener.shellClosed");
			}
			
			@Override
			public void shellActivated(ShellEvent e) {
				System.out.println("ShellListener.shellActivated");
			}
		});
		
		this.browser.addProgressListener(new ProgressListener() {
			@Override
			public void completed(ProgressEvent event) {
				System.out.println("ProgressListener.completed");
				System.out.println(event);
			}
			
			@Override
			public void changed(ProgressEvent event) {
				System.out.println("ProgressListener.changed");
			}
		});		
	}

	@Override
	protected void checkSubclass() {
	}
}
