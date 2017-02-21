/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.gfui.controls.gxws; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.eclipse.swt.widgets.Display;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import gplx.gfui.draws.*; import gplx.gfui.ipts.*; import gplx.gfui.gfxs.*; import gplx.gfui.imgs.*; import gplx.gfui.kits.swts.*;
public class GxwWin_lang extends JFrame implements GxwWin, WindowListener  {
	public void ShowWin() 		{this.setVisible(true);}
	public void ShowWinModal() {}
	public void HideWin() 		{this.setVisible(false);}
	public boolean Minimized()	{return this.getState() == Frame.ICONIFIED;} public void Minimized_(boolean v) {this.setState(v ? Frame.ICONIFIED : Frame.NORMAL);} 
	public boolean Maximized() 	{return this.getState() == Frame.MAXIMIZED_BOTH;} public void Maximized_(boolean v) {this.setState(v ? Frame.MAXIMIZED_BOTH : Frame.NORMAL);}
	public void CloseWin() 		{this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); this.dispose();}
	public boolean Pin() 		{return pin;}
	public void Pin_set(boolean val) {
		this.setAlwaysOnTop(val); pin = val;} boolean pin = false;
	public IconAdp IconWin() {return icon;} IconAdp icon;
	public void IconWin_set(IconAdp i) {
		if (i == null) return;
		icon = i;
		this.setIconImage(i.XtoImage());
	}
	public void OpenedCmd_set(Gfo_invk_cmd v) {whenLoadedCmd = v;} Gfo_invk_cmd whenLoadedCmd = Gfo_invk_cmd.Noop;	
	public GxwCore_base Core() {return ctrlMgr;} GxwCore_base ctrlMgr;
	public GxwCbkHost Host() {return host;} public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host = GxwCbkHost_.Null;
	public String TextVal() {return this.getTitle();} public void TextVal_set(String v) {this.setTitle(v);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return this;}
	public void SendKeyDown(IptKey key)				{}
	public void SendMouseMove(int x, int y)			{}
	public void SendMouseDown(IptMouseBtn btn)		{}
	public void windowActivated(WindowEvent e) 		{}
	public void windowClosed(WindowEvent e) 		{}
	public void windowClosing(WindowEvent e) 		{host.DisposeCbk();}
	public void windowDeactivated(WindowEvent e) 	{}
	public void windowDeiconified(WindowEvent e) 	{host.SizeChangedCbk();}
	public void windowIconified(WindowEvent e) 		{host.SizeChangedCbk();}
	public void windowOpened(WindowEvent e) 		{whenLoadedCmd.Exec();}
	@Override public void processKeyEvent(KeyEvent e) 					{if (GxwCbkHost_.ExecKeyEvent(host, e)) 	super.processKeyEvent(e);}
	@Override public void processMouseEvent(MouseEvent e) 				{if (GxwCbkHost_.ExecMouseEvent(host, e)) 	super.processMouseEvent(e);}
	@Override public void processMouseWheelEvent(MouseWheelEvent e) 	{if (GxwCbkHost_.ExecMouseWheel(host, e)) 	super.processMouseWheelEvent(e);}
	@Override public void processMouseMotionEvent(MouseEvent e)			{if (host.MouseMoveCbk(IptEvtDataMouse.new_(IptMouseBtn_.None, IptMouseWheel_.None, e.getX(), e.getY()))) super.processMouseMotionEvent(e);}
	@Override public void paint(Graphics g) {
		if (host.PaintCbk(PaintArgs.new_(GfxAdpBase.new_((Graphics2D)g), RectAdp_.Zero)))	// ClipRect not used by any clients; implement when necessary	
			super.paint(g);
	}
	public void EnableDoubleBuffering() {}
	public void TaskbarVisible_set(boolean val) {} 	public void TaskbarParkingWindowFix(GxwElem form) {}
	public static GxwWin_lang new_() {
		GxwWin_lang rv = new GxwWin_lang();
		rv.ctor_GxwForm();
		return rv;
	}	GxwWin_lang() {}
	void ctor_GxwForm() {
		ctrlMgr = GxwCore_form.new_(this);
		this.setLayout(null);										// use gfui layout
		this.ctrlMgr.BackColor_set(ColorAdp_.White);				// default form backColor to white
		this.setUndecorated(true);									// remove icon, titleBar, minimize, maximize, close, border
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	// JAVA: cannot cancel alt+f4; set Close to noop, and manually control closing by calling this.CloseForm
		enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
		this.addWindowListener(this);
		GxwBoxListener lnr = new GxwBoxListener(this);
		this.addComponentListener(lnr);
		this.addFocusListener(lnr);	
	}	
}
class GxwWin_jdialog extends JDialog implements GxwWin, WindowListener  {
	public void ShowWin() {this.setVisible(true);}
	public void HideWin() {this.setVisible(false);}
	public void CloseWin() {this.dispose();}//this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); this.dispose();
	public boolean Maximized() {return false;} public void Maximized_(boolean v) {}
	public boolean Minimized() {return false;} public void Minimized_(boolean v) {} //this.setState(Frame.ICONIFIED);
	public boolean Pin() {return pin;} public void Pin_set(boolean val) {
		this.setAlwaysOnTop(val); pin = val;} boolean pin = false;
	public IconAdp IconWin() {return icon;} IconAdp icon;
	public void IconWin_set(IconAdp i) {
		if (i == null) return;
		icon = i;
		this.setIconImage(i.XtoImage());
	}
	public void OpenedCmd_set(Gfo_invk_cmd v) {whenLoadedCmd = v;} Gfo_invk_cmd whenLoadedCmd = Gfo_invk_cmd.Noop;	
	public GxwCore_base Core() {return ctrlMgr;} GxwCore_base ctrlMgr;
	public GxwCbkHost Host() {return host;} public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host = GxwCbkHost_.Null;
	public String TextVal() {return "";} public void TextVal_set(String v) {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return this;}
	public void SendKeyDown(IptKey key)				{}
	public void SendMouseMove(int x, int y)			{}
	public void SendMouseDown(IptMouseBtn btn)		{}
	public void windowActivated(WindowEvent e) 		{}
	public void windowClosed(WindowEvent e) 		{}
	public void windowClosing(WindowEvent e) 		{host.DisposeCbk();}
	public void windowDeactivated(WindowEvent e) 	{}
	public void windowDeiconified(WindowEvent e) 	{host.SizeChangedCbk();}
	public void windowIconified(WindowEvent e) 		{host.SizeChangedCbk();}
	public void windowOpened(WindowEvent e) 		{whenLoadedCmd.Exec();}
	@Override public void processKeyEvent(KeyEvent e) 					{if (GxwCbkHost_.ExecKeyEvent(host, e)) 	super.processKeyEvent(e);}
	@Override public void processMouseEvent(MouseEvent e) 				{if (GxwCbkHost_.ExecMouseEvent(host, e)) 	super.processMouseEvent(e);}
	@Override public void processMouseWheelEvent(MouseWheelEvent e) 	{if (GxwCbkHost_.ExecMouseWheel(host, e))	super.processMouseWheelEvent(e);}
	@Override public void processMouseMotionEvent(MouseEvent e)			{if (host.MouseMoveCbk(IptEvtDataMouse.new_(IptMouseBtn_.None, IptMouseWheel_.None, e.getX(), e.getY()))) super.processMouseMotionEvent(e);}
	@Override public void paint(Graphics g) {
		if (host.PaintCbk(PaintArgs.new_(GfxAdpBase.new_((Graphics2D)g), RectAdp_.Zero)))	// ClipRect not used by any clients; implement when necessary	
			super.paint(g);
	}
	public void EnableDoubleBuffering() {}
	public void TaskbarVisible_set(boolean val) {} public void TaskbarParkingWindowFix(GxwElem form) {}
	public static GxwWin new_(GxwWin owner) {
		Window ownerWindow = owner instanceof Window ? (Window)owner : null; 
		GxwWin_jdialog rv = new GxwWin_jdialog(ownerWindow);
		rv.ctor_GxwForm();
		return rv;
	}	GxwWin_jdialog(Window owner) {super(owner);}
	void ctor_GxwForm() {
		ctrlMgr = GxwCore_form.new_(this);
		this.setLayout(null);										// use gfui layout
		this.ctrlMgr.BackColor_set(ColorAdp_.White);				// default form backColor to white
		this.setUndecorated(true);									// remove icon, titleBar, minimize, maximize, close, border
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	// JAVA: cannot cancel alt+f4; set CloseOp to noop, and manually control closing by calling this.CloseForm
		enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
		this.addWindowListener(this);
		GxwBoxListener lnr = new GxwBoxListener(this);
		this.addComponentListener(lnr);
		this.addFocusListener(lnr);	
	}
}
class GxwWin_jwindow extends JWindow implements GxwWin, WindowListener  {
	public void ShowWin() {this.setVisible(true);}
	public void ShowWinModal() {}
	public void HideWin() {this.setVisible(false);}
	public void CloseWin() {} //this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); this.dispose();
	public boolean Maximized() {return false;} public void Maximized_(boolean v) {}
	public boolean Minimized() {return false;} public void Minimized_(boolean v) {} 
	public boolean Pin() {return pin;} public void Pin_set(boolean val) {this.setAlwaysOnTop(val); pin = val;} boolean pin = false;
	public IconAdp IconWin() {return icon;} IconAdp icon;
	public void IconWin_set(IconAdp i) {
		if (i == null) return;
		icon = i;
		this.setIconImage(i.XtoImage());
	}
	public void OpenedCmd_set(Gfo_invk_cmd v) {whenLoadedCmd = v;} Gfo_invk_cmd whenLoadedCmd = Gfo_invk_cmd.Noop;	
	public GxwCore_base Core() {return ctrlMgr;} GxwCore_base ctrlMgr;
	public GxwCbkHost Host() {return host;} public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host = GxwCbkHost_.Null;
	public String TextVal() {return "";} public void TextVal_set(String v) {}// this.setTitle(v);
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return this;}
	public void SendKeyDown(IptKey key)				{}
	public void SendMouseMove(int x, int y)			{}
	public void SendMouseDown(IptMouseBtn btn)		{}
	public void windowActivated(WindowEvent e) 		{}
	public void windowClosed(WindowEvent e) 		{}
	public void windowClosing(WindowEvent e) 		{host.DisposeCbk();}
	public void windowDeactivated(WindowEvent e) 	{}
	public void windowDeiconified(WindowEvent e) 	{host.SizeChangedCbk();}
	public void windowIconified(WindowEvent e) 		{host.SizeChangedCbk();}
	public void windowOpened(WindowEvent e) 		{whenLoadedCmd.Exec();}
	@Override public void processKeyEvent(KeyEvent e) 					{if (GxwCbkHost_.ExecKeyEvent(host, e)) 	super.processKeyEvent(e);}
	@Override public void processMouseEvent(MouseEvent e) 				{if (GxwCbkHost_.ExecMouseEvent(host, e))	super.processMouseEvent(e);}
	@Override public void processMouseWheelEvent(MouseWheelEvent e) 	{if (GxwCbkHost_.ExecMouseWheel(host, e))	super.processMouseWheelEvent(e);}
	@Override public void processMouseMotionEvent(MouseEvent e)			{if (host.MouseMoveCbk(IptEvtDataMouse.new_(IptMouseBtn_.None, IptMouseWheel_.None, e.getX(), e.getY()))) super.processMouseMotionEvent(e);}
	@Override public void paint(Graphics g) {
		if (host.PaintCbk(PaintArgs.new_(GfxAdpBase.new_((Graphics2D)g), RectAdp_.Zero)))	// ClipRect not used by any clients; implement when necessary	
			super.paint(g);
	}
	public void EnableDoubleBuffering() {}
	public void TaskbarVisible_set(boolean val) {} public void TaskbarParkingWindowFix(GxwElem form) {}
	public static GxwWin new_(GxwWin owner) {
//		Window ownerWindow = owner instanceof Window ? (Window)owner : null; 
//		GxwWin_jwindow rv = new GxwWin_jwindow((Window)owner);
		GxwWin_jwindow rv = new GxwWin_jwindow();
		rv.ctor_GxwForm();
//		rv.setUndecorated(true);
		return rv;
	}	GxwWin_jwindow(Window owner) {super(owner);}
	GxwWin_jwindow() {}
	public static GxwWin owner_(GxwWin owner) {
		GxwWin_jwindow rv = new GxwWin_jwindow((Window)owner);
		rv.setFocusable(true);
		rv.setFocusableWindowState(true);
		rv.ctor_GxwForm();
		return rv;
	}
	public void ctor_GxwForm() {
		ctrlMgr = GxwCore_form.new_(this);
		this.setLayout(null);										// use gfui layout
		this.ctrlMgr.BackColor_set(ColorAdp_.White);				// default form backColor to white
//		this.setUndecorated(true);									// remove icon, titleBar, minimize, maximize, close, border
//		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	// JAVA: cannot cancel alt+f4; set CloseOp to noop, and manually control closing by calling this.CloseForm
		enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
		this.addWindowListener(this);
		GxwBoxListener lnr = new GxwBoxListener(this);
		this.addComponentListener(lnr);
		this.addFocusListener(lnr);	
	}	
}
class GxwCore_form extends GxwCore_lang {
	@Override public void Zorder_front() {
		Window frame = (Window)this.control;
		frame.toFront();
	}
	@Override public void Zorder_back() {
		Window frame = (Window)this.control;
		frame.toBack();
	}
    public static GxwCore_form new_(Component control) {
    	GxwCore_form rv = new GxwCore_form();
		rv.control = control;
		return rv;
	}	GxwCore_form() {}
}

class GxwElemFactory_swt extends GxwElemFactory_base {
	public GxwElemFactory_swt(org.eclipse.swt.widgets.Display display) {
		this.display = display;
	}	Display display;
	public GxwElem control_() {return null;}
	public GxwWin win_app_() {
		return new Swt_win(display);
	}
	public GxwWin win_tool_(Keyval_hash ctorArgs)	{
		return null;
	}
	public GxwWin win_toaster_(Keyval_hash ctorArgs)	{
		return null;
	}
	public GxwElem lbl_() {return null;}
	public GxwTextFld text_fld_() {return null;}
	public GxwTextFld text_memo_() {return null;}
	public GxwTextHtml text_html_() {return null;}
	public GxwCheckListBox checkListBox_(Keyval_hash ctorArgs) {throw Err_.new_unimplemented();}
	public GxwComboBox comboBox_() {return null;}
	public GxwListBox listBox_() {return null;}
}
//#}