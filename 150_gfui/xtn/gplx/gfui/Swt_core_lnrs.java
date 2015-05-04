/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.gfui;
import gplx.Byte_ascii;
import gplx.Enm_;
import gplx.Err_;
import gplx.GfoEvMgr_;
import gplx.GfoInvkAble_;
import gplx.GfoMsg_;
import gplx.GfsCtx;
import gplx.String_;
import gplx.Tfds;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolItem;
class Swt_lnr_show implements Listener {
	boolean shown = false;
	@Override public void handleEvent(Event ev) {
		if (shown) return;
		win.Opened();
	}
	public Swt_lnr_show(Swt_win win) {this.win = win;} Swt_win win;
}
class Swt_lnr_resize implements Listener {
	@Override public void handleEvent(Event ev) {
//		win.Host().SizeChangedCbk();
		GfoEvMgr_.Pub((GfuiWin)win.Host(), Gfui_html.Evt_win_resized);
	}
	public Swt_lnr_resize(Swt_win win) {this.win = win;} Swt_win win;
}
class Swt_lnr_traverse implements Listener {
	@Override public void handleEvent(Event e) {
		if (e.detail == SWT.TRAVERSE_ESCAPE)
			e.doit = false;
    }
}
class Swt_lnr_key implements KeyListener {
	public Swt_lnr_key(GxwElem elem) {this.elem = elem;} GxwElem elem;
//	static int counter = 0;
	@Override public void keyPressed(KeyEvent ev) 	{
		IptEvtDataKey ipt_data = XtoKeyData(ev);
		if (!elem.Host().KeyDownCbk(ipt_data) || ipt_data.Handled())
			ev.doit = false;
	}
	@Override public void keyReleased(KeyEvent ev) 	{
		IptEvtDataKey ipt_data = XtoKeyData(ev);
		if (!elem.Host().KeyUpCbk(ipt_data) || ipt_data.Handled())
			ev.doit = false;
	}
	IptEvtDataKey XtoKeyData(KeyEvent ev) {
		int val = ev.keyCode;
		switch (val) {
			case Byte_ascii.CarriageReturn:	val = 10; break; // enter key is 13 whereas .net/swing is 10
			case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
			case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
			case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
			case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
			case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
				val -= 32;	// lowercase keys are transmitted as ascii value, instead of key value; EX: "a" is 97 instead of 65 
				break;
			case 16777217:	val = IptKey_.Up.Val(); break;				
			case 16777218:	val = IptKey_.Down.Val(); break;				
			case 16777219:	val = IptKey_.Left.Val(); break;
			case 16777220:	val = IptKey_.Right.Val(); break;
			case 16777221:	val = IptKey_.PageUp.Val(); break;
			case 16777222:	val = IptKey_.PageDown.Val(); break;
			case 16777223:	val = IptKey_.Home.Val(); break;
			case 16777224:	val = IptKey_.End.Val(); break;
			case 16777226:	val = IptKey_.F1.Val(); break;
			case 16777227:	val = IptKey_.F2.Val(); break;
			case 16777228:	val = IptKey_.F3.Val(); break;
			case 16777229:	val = IptKey_.F4.Val(); break;
			case 16777230:	val = IptKey_.F5.Val(); break;
			case 16777231:	val = IptKey_.F6.Val(); break;
			case 16777232:	val = IptKey_.F7.Val(); break;
			case 16777233:	val = IptKey_.F8.Val(); break;
			case 16777234:	val = IptKey_.F9.Val(); break;
			case 16777235:	val = IptKey_.F10.Val(); break;
			case 16777236:	val = IptKey_.F11.Val(); break;
			case 16777237:	val = IptKey_.F12.Val(); break;
			case 16777259:  val = IptKey_.Equal.Val(); break;
			case 16777261:  val = IptKey_.Minus.Val(); break;
			case 16777300:	val = IptKey_.ScrollLock.Val(); break;
			case 16777301:	val = IptKey_.Pause.Val(); break;
			case 327680: 	val = IptKey_.Insert.Val(); break;
		}
		if (Has_ctrl(ev.stateMask)) 							val |= IptKey_.KeyCode_Ctrl;
		if (Enm_.HasInt(ev.stateMask, IptKey_.KeyCode_Shift))	val |= IptKey_.KeyCode_Alt;
		if (Enm_.HasInt(ev.stateMask, IptKey_.KeyCode_Ctrl))	val |= IptKey_.KeyCode_Shift;
//		Tfds.Write(String_.Format("val={4} keyCode={0} stateMask={1} keyLocation={2} character={3}", ev.keyCode, ev.stateMask, ev.keyLocation, ev.character, val));
		return IptEvtDataKey.int_(val);		
	}
	public static boolean Has_ctrl(int val) {return Enm_.HasInt(val, IptKey_.KeyCode_Alt);}	// NOTE:SWT's ctrl constant is different from SWING's 
}
class Swt_lnr_mouse implements MouseListener {
	public Swt_lnr_mouse(GxwElem elem) {this.elem = elem;} GxwElem elem;
	@Override public void mouseDown(MouseEvent ev) {elem.Host().MouseDownCbk(XtoMouseData(ev));}
	@Override public void mouseUp(MouseEvent ev) {elem.Host().MouseUpCbk(XtoMouseData(ev));}
	@Override public void mouseDoubleClick(MouseEvent ev) {}
	IptEvtDataMouse XtoMouseData(MouseEvent ev) {
		IptMouseBtn btn = null;
		switch (ev.button) {
			case 1: btn = IptMouseBtn_.Left; break;
			case 2: btn = IptMouseBtn_.Middle; break;
			case 3: btn = IptMouseBtn_.Right; break;
			case 4: btn = IptMouseBtn_.X1; break;
			case 5: btn = IptMouseBtn_.X2; break;
		}
		return IptEvtDataMouse.new_(btn, IptMouseWheel_.None, ev.x, ev.y);		
	}
//	private static int X_to_swing(int v) {
//		switch (v) {
//			case gplx.gfui.IptMouseBtn_.Tid_left	: return java.awt.event.InputEvent.BUTTON1_MASK;
//			case gplx.gfui.IptMouseBtn_.Tid_middle	: return java.awt.event.InputEvent.BUTTON2_MASK;
//			case gplx.gfui.IptMouseBtn_.Tid_right	: return java.awt.event.InputEvent.BUTTON3_MASK;
//			default									: throw Err_.unhandled(v);
//		}
//	}
}
class Swt_lnr_toolitem implements Listener {
	public Swt_lnr_toolitem(ToolItem itm, GxwElem elem) {this.itm = itm; this.elem = elem;} ToolItem itm; GxwElem elem;
	@Override public void handleEvent(Event arg0) {
		Rectangle rect = itm.getBounds();
		elem.Host().MouseUpCbk(IptEvtDataMouse.new_(IptMouseBtn_.Left, IptMouseWheel_.None, rect.x, rect.y));
	}	
}