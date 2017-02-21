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
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import gplx.gfui.ipts.*; import gplx.gfui.gfxs.*;
public class GxwCbkHost_ { 
	public static final    GxwCbkHost Null = new GfuiHost_cls_null();
		public static final boolean ExecKeyEvent(GxwCbkHost host, KeyEvent e) {
		boolean rv = true; int id = e.getID(), val = e.getKeyCode();
		boolean isAltF4 = false;
		if (e.isAltDown() && val == IptKey_.F4.Val() ) {
			isAltF4 = true;
		}
		if	(id == KeyEvent.KEY_TYPED) {
			IptEvtDataKeyHeld keyHeldData = IptEvtDataKeyHeld.char_(e.getKeyChar());
			rv = host.KeyHeldCbk(keyHeldData);
			if (keyHeldData.Handled()) rv = false;
		}
		else {
			if (e.isShiftDown()) 		val |= IptKey_.Shift.Val();
			if (e.isControlDown())		val |= IptKey_.Ctrl.Val();
			if (e.isAltDown())			val |= IptKey_.Alt.Val();
			IptEvtDataKey keyData = IptEvtDataKey.int_(val);
//			Tfds.Write(e.getKeyChar(), e.getKeyCode(), val, id);
			if 		(id == KeyEvent.KEY_PRESSED)	rv = host.KeyDownCbk(keyData);
			else if	(id == KeyEvent.KEY_RELEASED)	rv = host.KeyUpCbk(keyData);
			if (keyData.Handled()) rv = false;	// was false
		}
		if (isAltF4) {
			e.consume();			
		}
		if (e.getKeyCode() == KeyEvent.VK_ALT) e.consume(); // force consume of alt-keys; occurs when alt-f4ing out of video app (though not audio app)
		return rv;		
	}
	public static final boolean ExecMouseEvent(GxwCbkHost host, MouseEvent e) {
		int button = e.getButton(), val = 0;
		if (button == MouseEvent.BUTTON1)
			val = IptMouseBtn_.Left.Val();
		if (button == MouseEvent.BUTTON2)	val |= IptMouseBtn_.Middle.Val();
		if (button == MouseEvent.BUTTON3)	val |= IptMouseBtn_.Right.Val();
		IptEvtDataMouse data = IptEvtDataMouse.new_(IptMouseBtn_.api_(val), IptMouseWheel_.None, e.getX(), e.getY());
		boolean rv = true;
		int id = e.getID();
		if 		(id == MouseEvent.MOUSE_PRESSED)	rv = host.MouseDownCbk(data);
		else if (id == MouseEvent.MOUSE_RELEASED)	rv = host.MouseUpCbk(data);
		return rv;
	}
	public static final boolean ExecMouseWheel(GxwCbkHost host, MouseWheelEvent e) {		
		IptMouseWheel wheel = e.getWheelRotation() < 0 ? IptMouseWheel_.Up : IptMouseWheel_.Down;
		return host.MouseWheelCbk(IptEvtDataMouse.new_(IptMouseBtn_.None, wheel, e.getX(), e.getY()));
	}
	}
class GfuiHost_cls_null implements GxwCbkHost {	// NOTE: return true by default so that super.proc() is always called; ex: SizeChangedCbk should return true so that super.OnResize will be called
	public boolean KeyDownCbk(IptEvtDataKey data) {return true;}
	public boolean KeyHeldCbk(IptEvtDataKeyHeld data) {return true;}
	public boolean KeyUpCbk(IptEvtDataKey data) {return true;}
	public boolean MouseDownCbk(IptEvtDataMouse data) {return true;}
	public boolean MouseUpCbk(IptEvtDataMouse data) {return true;}
	public boolean MouseMoveCbk(IptEvtDataMouse data) {return true;}
	public boolean MouseWheelCbk(IptEvtDataMouse data) {return true;}
	public boolean PaintCbk(PaintArgs paint) {return true;}
	public boolean PaintBackgroundCbk(PaintArgs paint) {return true;}
	public boolean DisposeCbk() {return true;}
	public boolean SizeChangedCbk() {return true;}
	public boolean FocusGotCbk() {return true;}
	public boolean FocusLostCbk() {return true;}
	public boolean VisibleChangedCbk() {return true;}
	//	public boolean WndProcCbk(WindowMessage windowMessage) {return true;}
	}
