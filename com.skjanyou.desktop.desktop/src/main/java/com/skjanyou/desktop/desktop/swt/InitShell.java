package com.skjanyou.desktop.desktop.swt;


import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.AuthenticationEvent;
import org.eclipse.swt.browser.AuthenticationListener;
import org.eclipse.swt.browser.CloseWindowListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import com.skjanyou.appl.Application.webkit.AbstractShell;



public class InitShell extends AbstractShell {

	public InitShell(Display display) {
		super(display, SWT.NONE);
	}

	public void createContents() {
		setText("程序初始化");
		Image background = SWTResourceManager.getImage("image/loading.jpg");
		setImage(SWTResourceManager.getImage("image/icon.png"));
		setBackgroundImage(background);
		setSize(background.getBounds().width, background.getBounds().height);
	}
	
	public void initComponents(){}
	public void initListeners(){
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
	}

	@Override
	protected void checkSubclass() {
	}
}
