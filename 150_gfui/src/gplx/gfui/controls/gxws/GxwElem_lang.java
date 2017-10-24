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
import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JComponent;
import gplx.gfui.ipts.*; import gplx.gfui.gfxs.*;
public class GxwElem_lang extends JComponent implements GxwElem {
	public static final String AlignH_cmd = "AlignH";
	public GxwCbkHost Host() {return host;} public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host = GxwCbkHost_.Null;
	public String TextVal() {return text;} public void TextVal_set(String v) {text = v;} String text;	// JAVA: JComponent does not support Text (.NET Control does)
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return this;}	// noop: intended for AlignH
	@Override public void processKeyEvent(KeyEvent e) 					{if (GxwCbkHost_.ExecKeyEvent(host, e)) 	super.processKeyEvent(e);}
	@Override public void processMouseEvent(MouseEvent e) 				{if (GxwCbkHost_.ExecMouseEvent(host, e))	super.processMouseEvent(e);}
	@Override public void processMouseWheelEvent(MouseWheelEvent e) 	{if (GxwCbkHost_.ExecMouseWheel(host, e))	super.processMouseWheelEvent(e);}
	@Override public void processMouseMotionEvent(MouseEvent e)			{
		if (host.MouseMoveCbk(IptEvtDataMouse.new_(IptMouseBtn_.None, IptMouseWheel_.None, e.getX(), e.getY()))) super.processMouseMotionEvent(e);}
	@Override public void paint(Graphics g) {
		// always paint background color; must happen before any controlPaint; extract to separate method if override needed 
        Graphics2D g2 = (Graphics2D)g;
        g2.setBackground(this.getBackground());
        RectAdp clipRect = GxwCore_lang.XtoRectAdp(g2.getClipBounds()); 
        g2.clearRect(clipRect.X(), clipRect.Y(), clipRect.Width(), clipRect.Height());
		if (host.PaintCbk(PaintArgs.new_(GfxAdpBase.new_(g2), clipRect)))	// ClipRect not used by any clients; implement when necessary	
			super.paint(g);	// Reevaluate if necessary: super.paint might need to (a) always happen and (b) go before PaintCbk (had issues with drawing text on images) 
	}
	// todo: call Dispose when ownerForm is disposed 
//	@Override protected void Dispose(boolean disposing)					{if (host.DisposeCbk()) super.Dispose(disposing);}
	public void ctor_GxwElem() {
		ctrlMgr = GxwCore_lang.new_(this);	
		GxwBoxListener lnr = new GxwBoxListener(this);
		//this.setFocusTraversalKeysEnabled(false);
		this.addComponentListener(lnr);
		this.addFocusListener(lnr);
		this.enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK);
	}
	public GxwCore_base Core() {return ctrlMgr;} GxwCore_base ctrlMgr;
	public void SendKeyDown(IptKey key)								{}
	public void SendMouseMove(int x, int y)							{}
	public void SendMouseDown(IptMouseBtn btn)						{
	}
	public void EnableDoubleBuffering() {}	// eliminates flickering during OnPaint
	JComponent comp = null;
	public GxwElem_lang() {this.ctor_GxwElem();}
}
