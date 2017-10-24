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
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import gplx.gfui.ipts.*; import gplx.gfui.gfxs.*;
class GxwListBox_lang extends JList implements GxwListBox {
	void ctor_() {
		ctrlMgr = GxwCore_lang.new_(this);
		this.enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK);
		GxwBoxListener lnr = new GxwBoxListener(this);
//		this.setFocusTraversalKeysEnabled(false);
		this.addComponentListener(lnr);
		this.addFocusListener(lnr);
		this.setFocusable(true);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	Vector<String> internalItems = new Vector<String>();
	public void Items_Add(Object item) {
		internalItems.add((String)item);
		this.setListData(internalItems);
		this.updateUI();
	}
	public void Items_Clear() {internalItems.clear();}
	public int Items_Count() {return internalItems.size();}
	public int Items_SelIdx() {return this.getSelectedIndex();} public void Items_SelIdx_set(int v) {this.setSelectedIndex(v);}
	public Object Items_SelObj() {return this.getSelectedValue();}

	public Object SelectedItm() {return this.getSelectedValue();} public void SelectedItm_set(Object v) {this.setSelectedValue(v, true);}
	public GxwCore_base Core() {return ctrlMgr;} GxwCore_base ctrlMgr;
	public GxwCbkHost Host() {return host;} public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host = GxwCbkHost_.Null;
	// need to validate, else dropDownArrow will not get redrawn in correct pos
	@Override public void setBounds(Rectangle r) {super.setBounds(r); this.validate();}
	@Override public void setSize(int w, int h) {super.setSize(w, h); this.validate();}
	@Override public void setSize(Dimension d) {super.setSize(d); this.validate();}
	public String TextVal() {return Object_.Xto_str_strict_or_empty(this.SelectedItm());} public void TextVal_set(String v) {this.SelectedItm_set(v);}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return this;
	}
	public void SendKeyDown(IptKey key)								{}
	public void SendMouseMove(int x, int y)							{}
	public void SendMouseDown(IptMouseBtn btn)						{}
	public void SendMouseWheel(IptMouseWheel direction)				{}
	@Override public void processKeyEvent(KeyEvent e) 					{
		if (GxwCbkHost_.ExecKeyEvent(host, e)){
			super.processKeyEvent(e);
		}
	}
	@Override public void processMouseEvent(MouseEvent e) 				{if (GxwCbkHost_.ExecMouseEvent(host, e))	super.processMouseEvent(e);}
	@Override public void processMouseWheelEvent(MouseWheelEvent e) 	{if (GxwCbkHost_.ExecMouseWheel(host, e))	super.processMouseWheelEvent(e);}
	@Override public void processMouseMotionEvent(MouseEvent e)			{if (host.MouseMoveCbk(IptEvtDataMouse.new_(IptMouseBtn_.None, IptMouseWheel_.None, e.getX(), e.getY()))) super.processMouseMotionEvent(e);}
	@Override public void paint(Graphics g) {
		if (host.PaintCbk(PaintArgs.new_(GfxAdpBase.new_((Graphics2D)g), RectAdp_.Zero)))	// ClipRect not used by any clients; implement when necessary	
			super.paint(g);	// Reevaluate if necessary: super.paint might need to (a) always happen and (b) go before PaintCbk (had issues with drawing text on images) 
	}
	public void EnableDoubleBuffering() {	// eliminates flickering during OnPaint
	}
	public void CreateControlIfNeeded() {
	}
	public static GxwListBox_lang new_() {
		GxwListBox_lang rv = new GxwListBox_lang();
		rv.ctor_();
		return rv;
	}	GxwListBox_lang() {}
}
