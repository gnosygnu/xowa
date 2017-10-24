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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import gplx.gfui.draws.*;
import gplx.gfui.ipts.*; import gplx.gfui.gfxs.*; import gplx.gfui.controls.elems.*;
public class GxwTextBox_lang extends JTextArea implements GxwTextFld {
	public Object UnderElem() {return this;}
	public GxwCore_base Core() {return ctrlMgr;} GxwCore_base ctrlMgr;
	public GxwCbkHost Host() {return host;} public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host = GxwCbkHost_.Null;
	public int SelBgn() {return this.getSelectionStart();} public void SelBgn_set(int v) {this.setSelectionStart(v); this.setCaretPosition(v);}
	public int SelLen() {return this.getSelectionEnd() - this.getSelectionStart();} public void SelLen_set(int v) {this.setSelectionEnd(this.SelBgn() + v);}
	public String TextVal() {return this.getText();} public void TextVal_set(String v) {this.setText(v);}
	public void AlignH_(GfuiAlign val) {} // TODO
	//@#if !plat_wce
	public int BorderWidth() {return borderOn ? 2 : 0;}
	public boolean Border_on() {return borderOn;} boolean borderOn = true;
	public void Border_on_(boolean v) {
		borderOn = v;
		Border border = v ? BorderFactory.createLineBorder(Color.BLACK, 1) : null;
		this.setBorder(border);
	}
	public ColorAdp Border_color() {return border_color;} public void Border_color_(ColorAdp v) {border_color = v;} private ColorAdp border_color;
	public void Margins_set(int left, int top, int right, int bot) {}
	public boolean OverrideTabKey() {return overrideTabKey;} boolean overrideTabKey;
	public void OverrideTabKey_(boolean val) {
		overrideTabKey = val;
		if (val)
			GxwTextBox_overrideKeyCmd.new_((GfuiElem)host, this, "TAB", "\t", false);
		else
			GxwTextBox_overrideKeyCmd.focus_((GfuiElem)host, this, "TAB");			
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return this;
	}
	public void SendKeyDown(IptKey key)								{}
	public void SendMouseMove(int x, int y)							{}
	public void SendMouseDown(IptMouseBtn btn)						{}
	public void SendMouseWheel(IptMouseWheel direction)				{}
	@Override public void processKeyEvent(KeyEvent e) 					{
		if (overrideTabKey && e.getKeyCode() == KeyEvent.VK_TAB) {
			super.processKeyEvent(e); return;}
		if (GxwCbkHost_.ExecKeyEvent(host, e)) 
			super.processKeyEvent(e);
	}
	@Override public void processMouseEvent(MouseEvent e) 				{if (GxwCbkHost_.ExecMouseEvent(host, e))	super.processMouseEvent(e);}
	@Override public void processMouseWheelEvent(MouseWheelEvent e) 	{if (GxwCbkHost_.ExecMouseWheel(host, e))	super.processMouseWheelEvent(e);}
	@Override public void processMouseMotionEvent(MouseEvent e)			{if (host.MouseMoveCbk(IptEvtDataMouse.new_(IptMouseBtn_.None, IptMouseWheel_.None, e.getX(), e.getY()))) super.processMouseMotionEvent(e);}
	@Override public void paint(Graphics g) {
		if (host.PaintCbk(PaintArgs.new_(GfxAdpBase.new_((Graphics2D)g), RectAdp_.Zero)))	// ClipRect not used by any clients; implement when necessary
			super.paint(g);	// Reevaluate if necessary: super.paint might need to (a) always happen and (b) go before PaintCbk (had issues with drawing text on images) 
	}
	public GxwTextBox_lang(){ctor_();}
	void ctor_() {
		ctrlMgr = GxwCore_lang.new_(this);
		this.setTabSize(4);
	}
	public void EnableDoubleBuffering() {}	// eliminates flickering during OnPaint
	public void CreateControlIfNeeded() {}
	@gplx.Virtual public void ctor_MsTextBox_() {
		ctor_();
	}
}
class GxwTextFld_cls_lang extends JTextField implements GxwTextFld {
	public Object UnderElem() {return this;}
	public void AlignH_(GfuiAlign val) {
		int align = JTextField.LEFT;
		if 		(val.Val() == GfuiAlign_.Mid.Val()) align = JTextField.CENTER;
		else if (val.Val() == GfuiAlign_.Right.Val()) align = JTextField.RIGHT;		
		this.setHorizontalAlignment(align);
	}
	public int SelBgn() {return this.getSelectionStart();}
	public void SelBgn_set(int v) {
//		if (v >= this.getSelectionStart() && v < this.getSelectionEnd())
		try {
			this.setSelectionStart(v);
		} catch (Exception e) {
			Err_.Noop(e);
			} // NOTE: sometimes fails when skipping ahead in dvd player; v = 0, and start/end = 0
	}
	public int SelLen() {return this.getSelectionEnd() - this.getSelectionStart();} public void SelLen_set(int v) {this.setSelectionEnd(this.SelBgn() + v);}
	@gplx.Virtual public void ctor_MsTextBox_() {
		ctor_();
	}
	//@#if !plat_wce
	public int BorderWidth() {return borderOn ? 2 : 0;}
	public boolean Border_on() {return borderOn;}
//	@Override public void setBorder(Border border) {
//		if (borderOn) super.setBorder(border);
//		else {
//			super.setBorder(null);
//		}
//		// NO!
//	}
	public void Border_on_(boolean v) {
		borderOn = v;
		Border border = v ? BorderFactory.createLineBorder(Color.BLACK) : null;
		this.setBorder(border);			
	} boolean borderOn = true;
	public ColorAdp Border_color() {return border_color;} public void Border_color_(ColorAdp v) {border_color = v;} private ColorAdp border_color;
	public void Margins_set(int left, int top, int right, int bot) {}
	public boolean OverrideTabKey() {return overrideTabKey;}
	public void OverrideTabKey_(boolean val) {
		overrideTabKey = val;
		GxwTextBox_overrideKeyCmd.new_((GfuiElem)host, this, "TAB", "\t", false);
	} 	boolean overrideTabKey;
	void ctor_() {
		ctrlMgr = GxwCore_lang.new_(this);
		this.enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK);
		this.Border_on_(true);
		GxwBoxListener lnr = new GxwBoxListener(this);
//		this.setFocusTraversalKeysEnabled(false);
		this.addComponentListener(lnr);
		this.addFocusListener(lnr);
	}
	public GxwCore_base Core() {return ctrlMgr;} GxwCore_base ctrlMgr;
	public GxwCbkHost Host() {return host;} public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host = GxwCbkHost_.Null;
	public String TextVal() {return this.getText();} public void TextVal_set(String v) {
		this.setText(v);
		this.SelBgn_set(0); this.SelLen_set(0);	// otherwise will set cursor to end; want to see text start
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if (ctx.Match(k, GxwElem_lang.AlignH_cmd))  AlignH_(GfuiAlign_.cast(m.CastObj("v")));
		return this;
	}
	public void SendKeyDown(IptKey key)								{}
	public void SendMouseMove(int x, int y)							{}
	public void SendMouseDown(IptMouseBtn btn)						{}
	public void SendMouseWheel(IptMouseWheel direction)				{}
	@Override public void processKeyEvent(KeyEvent e) 					{
		if (overrideTabKey && e.getKeyCode() == KeyEvent.VK_TAB) {
			super.processKeyEvent(e); return;}
		if (GxwCbkHost_.ExecKeyEvent(host, e)) 
			super.processKeyEvent(e);
		}
	@Override public void processMouseEvent(MouseEvent e) 				{if (GxwCbkHost_.ExecMouseEvent(host, e))	super.processMouseEvent(e);}
	@Override public void processMouseWheelEvent(MouseWheelEvent e) 	{
		if (GxwCbkHost_.ExecMouseWheel(host, e))	super.processMouseWheelEvent(e);}
	@Override public void processMouseMotionEvent(MouseEvent e)			{if (host.MouseMoveCbk(IptEvtDataMouse.new_(IptMouseBtn_.None, IptMouseWheel_.None, e.getX(), e.getY()))) super.processMouseMotionEvent(e);}
	@Override public void paint(Graphics g) {
		if (host.PaintCbk(PaintArgs.new_(GfxAdpBase.new_((Graphics2D)g), RectAdp_.Zero)))	// ClipRect not used by any clients; implement when necessary	
			super.paint(g);	// Reevaluate if necessary: super.paint might need to (a) always happen and (b) go before PaintCbk (had issues with drawing text on images) 
	}
	public void EnableDoubleBuffering() {	// eliminates flickering during OnPaint
	}
	public void CreateControlIfNeeded() {
	}
}
class GxwTextBox_overrideKeyCmd extends AbstractAction {
	public void actionPerformed(ActionEvent e) {
		if (focus) {
			int z = owner.OwnerWin().FocusMgr().SubElems().Idx_of(owner);
			owner.OwnerWin().FocusMgr().Focus(focusDir, z);
			return;
		}
		if (overrideText == null) return;
		textBox.replaceSelection(overrideText);
	}		
	public GxwTextBox_overrideKeyCmd(JTextComponent textBox, String overrideText) {this.textBox = textBox; this.overrideText = overrideText;}
	public static GxwTextBox_overrideKeyCmd focus_(GfuiElem owner, JTextComponent textBox, String keyName) {return GxwTextBox_overrideKeyCmd.new_(owner, textBox, keyName, null, true);}
	public static GxwTextBox_overrideKeyCmd focusPrv_(GfuiElem owner, JTextComponent textBox, String keyName) {
		GxwTextBox_overrideKeyCmd rv = GxwTextBox_overrideKeyCmd.new_(owner, textBox, keyName, null, true);
		rv.focusDir = false;
		return rv;
	}
	public static GxwTextBox_overrideKeyCmd noop_(GfuiElem owner, JTextComponent textBox, String keyName) {return GxwTextBox_overrideKeyCmd.new_(owner, textBox, keyName, null, false);}
	public static GxwTextBox_overrideKeyCmd new_(GfuiElem owner, JTextComponent textBox, String keyName, String overrideText, boolean focus) {
		KeyStroke tabKey = KeyStroke.getKeyStroke(keyName);
		GxwTextBox_overrideKeyCmd action = new GxwTextBox_overrideKeyCmd(textBox, overrideText);
		action.focus = focus;
		action.owner = owner;
		textBox.getInputMap().remove(tabKey);
		textBox.getInputMap().put(tabKey, action);
		return action;
	}
	JTextComponent textBox; String overrideText; GfuiElem owner; boolean focus; boolean focusDir = true;		
}
class GxwTextBox_lang_ {
		public static GxwTextFld fld_() {
		GxwTextFld_cls_lang rv = new GxwTextFld_cls_lang();
		rv.ctor_MsTextBox_();
		return rv;
	}	
	public static GxwTextMemo_lang memo_() {
		GxwTextMemo_lang rv = new GxwTextMemo_lang();
		rv.ctor_MsTextBoxMultiline_();
		return rv;
	}	
	}
