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
package gplx.gfui.kits.swts; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import gplx.Bool_;
import gplx.Gfo_invk_cmd;
import gplx.GfoMsg;
import gplx.GfsCtx;
import gplx.Io_url_;
import gplx.gfui.controls.gxws.GxwCbkHost;
import gplx.gfui.controls.gxws.GxwCbkHost_;
import gplx.gfui.controls.gxws.GxwCore_base;
import gplx.gfui.controls.gxws.GxwElem;
import gplx.gfui.controls.gxws.GxwWin;
import gplx.gfui.imgs.IconAdp;
import gplx.gfui.ipts.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
public class Swt_win implements GxwWin, Swt_control {
	private Swt_lnr_resize resize_lnr; private Swt_lnr_show show_lnr;	// use ptr to dispose later
	void ctor(boolean window_is_dialog, Shell shell, Display display) {
		this.shell = shell;		
		this.display = display;
		this.ctrl_mgr = new Swt_core__basic(shell);
		this.show_lnr = new Swt_lnr_show(this);
		this.resize_lnr = new Swt_lnr_resize(this);
		shell.addListener(SWT.Show, show_lnr);
		shell.addListener(SWT.Resize, resize_lnr);
		if (window_is_dialog) {
			shell.addListener(SWT.Traverse, new Swt_lnr_traverse());
		}
	}
	public Display UnderDisplay() {return display;} private Display display;
	public Shell UnderShell() {return shell;} private Shell shell;
	@Override public Control Under_control() {return shell;}
	@Override public Composite Under_composite() {return shell;}
	@Override public Control Under_menu_control() {return shell;}
	public Swt_win(Shell owner) 		{ctor(Bool_.Y, new Shell(owner, SWT.RESIZE | SWT.DIALOG_TRIM), owner.getDisplay());}
	public Swt_win(Display display) 	{ctor(Bool_.N, new Shell(display), display);	}
	public void ShowWin() 		{shell.setVisible(true);}
	public void HideWin() 		{shell.setVisible(false);}
	public boolean Maximized() 	{return shell.getMaximized();} public void Maximized_(boolean v) {shell.setMaximized(v);}
	public boolean Minimized() 	{return shell.getMinimized();} public void Minimized_(boolean v) {shell.setMinimized(v);}
	public void CloseWin() 		{shell.close();}
	public boolean Pin() 		{return pin;}
	public void Pin_set(boolean val) {
	//	shell.setAlwaysOnTop(val);
		pin = val;
	} 	boolean pin = false;
	public IconAdp IconWin() {return icon;} IconAdp icon;
	public void IconWin_set(IconAdp i) {
		if (i == null || i.Url() == Io_url_.Empty) return;
		icon = i;
        Image image = null;
        try {
          image = new Image(display, new FileInputStream(i.Url().Xto_api()));
        } catch (FileNotFoundException e1) {e1.printStackTrace();}
		shell.setImage(image);
	}
	public void OpenedCmd_set(Gfo_invk_cmd v) {when_loaded_cmd = v;} private Gfo_invk_cmd when_loaded_cmd = Gfo_invk_cmd.Noop;
	public void Opened() {when_loaded_cmd.Exec();}
	public GxwCore_base Core() {return ctrl_mgr;} private GxwCore_base ctrl_mgr;
	public GxwCbkHost Host() {return host;} public void Host_set(GxwCbkHost host) {this.host = host;} private GxwCbkHost host = GxwCbkHost_.Null;
	public String TextVal() {return shell.getText();} 
	public void TextVal_set(String v) {shell.setText(v);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return this;}
	public void SendKeyDown(IptKey key)				{}
	public void SendMouseMove(int x, int y)			{}
	public void SendMouseDown(IptMouseBtn btn)		{}
	//public void windowActivated(WindowEvent e) 		{}
	//public void windowClosed(WindowEvent e) 		{}
	//public void windowClosing(WindowEvent e) 		{host.DisposeCbk();}
	//public void windowDeactivated(WindowEvent e) 	{}
	//public void windowDeiconified(WindowEvent e) 	{host.SizeChangedCbk();}
	//public void windowIconified(WindowEvent e) 		{host.SizeChangedCbk();}
	//public void windowOpened(WindowEvent e) 		{when_loaded_cmd.Invk();}
	//@Override public void processKeyEvent(KeyEvent e) 					{if (GxwCbkHost_.ExecKeyEvent(host, e)) 	super.processKeyEvent(e);}
	//@Override public void processMouseEvent(MouseEvent e) 				{if (GxwCbkHost_.ExecMouseEvent(host, e)) 	super.processMouseEvent(e);}
	//@Override public void processMouseWheelEvent(MouseWheelEvent e) 	{if (GxwCbkHost_.ExecMouseWheel(host, e)) 	super.processMouseWheelEvent(e);}
	//@Override public void processMouseMotionEvent(MouseEvent e)			{if (host.MouseMoveCbk(IptEvtDataMouse.new_(IptMouseBtn_.None, IptMouseWheel_.None, e.getX(), e.getY()))) super.processMouseMotionEvent(e);}
	//@Override public void paint(Graphics g) {
	//	if (host.PaintCbk(PaintArgs.new_(GfxAdpBase.new_((Graphics2D)g), RectAdp_.Zero)))	// ClipRect not used by any clients; implement when necessary	
	//		super.paint(g);
	//}
	public void EnableDoubleBuffering() {}
	public void TaskbarVisible_set(boolean val) {} 	public void TaskbarParkingWindowFix(GxwElem form) {}
	void ctor_GxwForm() {
	//	this.setLayout(null);										// use gfui layout
	//	this.ctrl_mgr.BackColor_set(ColorAdp_.White);				// default form backColor to white
	//	this.setUndecorated(true);									// remove icon, titleBar, minimize, maximize, close, border
	//	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	// JAVA: cannot cancel alt+f4; set Close to noop, and manually control closing by calling this.CloseForm
	//	enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
	//	this.addWindowListener(this);
	//	GxwBoxListener lnr = new GxwBoxListener(this);
	//	this.addComponentListener(lnr);
	//	this.addFocusListener(lnr);	
	}
}
