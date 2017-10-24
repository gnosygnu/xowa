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
import gplx.Byte_ascii;

import gplx.Enm_;
import gplx.Gfo_evt_mgr_;
import gplx.Gfo_invk_;
import gplx.GfoMsg_;
import gplx.GfsCtx;
import gplx.String_;
import gplx.Tfds;
import gplx.core.bits.Bitmask_;
import gplx.core.envs.Op_sys;
import gplx.core.envs.Op_sys_;
import gplx.gfui.controls.gxws.GxwElem;
import gplx.gfui.controls.standards.Gfui_html;
import gplx.gfui.controls.windows.GfuiWin;
import gplx.gfui.ipts.*;

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
		Gfo_evt_mgr_.Pub((GfuiWin)win.Host(), Gfui_html.Evt_win_resized);
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
	public Swt_lnr_key(GxwElem elem) {this.elem = elem;} private GxwElem elem;
	@Override public void keyPressed(KeyEvent ev) 	{
		IptEvtDataKey ipt_data = To_gfui(ev, Bool_.Y);

		// cancel if handled; note order MUST be "delegate || ipt_data.Handled", not vice-versa
		if (!elem.Host().KeyDownCbk(ipt_data) || ipt_data.Handled())
			ev.doit = false;
	}
	@Override public void keyReleased(KeyEvent ev) 	{
		IptEvtDataKey ipt_data = To_gfui(ev, Bool_.N);

		// cancel if handled; note order MUST be "delegate || ipt_data.Handled", not vice-versa
		if (!elem.Host().KeyUpCbk(ipt_data) || ipt_data.Handled())
			ev.doit = false;
	}
	private IptEvtDataKey To_gfui(KeyEvent ev, boolean is_keydown) {
		// convert codes from SWT keycodes to SWING / .NET style; note that SWT uses keycode values similar to ASCII values
		int val = ev.keyCode;
		switch (val) {
			// letters; lowercase keys are transmitted as ascii value, instead of key value; EX: "a": SWT=97; SWING=65
			case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
			case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
			case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
			case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
			case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
				val -= 32; 
				break;

			//	// numpad numbers
			//	case SWT.KEYPAD_0:				val = IptKey_.Numpad_0.Val(); break;
			//	case SWT.KEYPAD_1:				val = IptKey_.Numpad_1.Val(); break;
			//	case SWT.KEYPAD_2:				val = IptKey_.Numpad_2.Val(); break;
			//	case SWT.KEYPAD_3:				val = IptKey_.Numpad_3.Val(); break;
			//	case SWT.KEYPAD_4:				val = IptKey_.Numpad_4.Val(); break;
			//	case SWT.KEYPAD_5:				val = IptKey_.Numpad_5.Val(); break;
			//	case SWT.KEYPAD_6:				val = IptKey_.Numpad_6.Val(); break;
			//	case SWT.KEYPAD_7:				val = IptKey_.Numpad_7.Val(); break;
			//	case SWT.KEYPAD_8:				val = IptKey_.Numpad_8.Val(); break;
			//	case SWT.KEYPAD_9:				val = IptKey_.Numpad_9.Val(); break;
			//	
			//	// numpad symbols
			//	case SWT.KEYPAD_MULTIPLY:		val = IptKey_.Numpad_multiply.Val(); break;
			//	case SWT.KEYPAD_ADD:			val = IptKey_.Numpad_add.Val(); break;
			//	case SWT.KEYPAD_SUBTRACT:  		val = IptKey_.Numpad_subtract.Val(); break;
			//	case SWT.KEYPAD_DECIMAL:  		val = IptKey_.Numpad_decimal.Val(); break;
			//	case SWT.KEYPAD_DIVIDE:  		val = IptKey_.Numpad_divide.Val(); break;
			//	case SWT.KEYPAD_CR:  			val = IptKey_.Numpad_enter.Val(); break;

			// numpad numbers
			case SWT.KEYPAD_0:				val = IptKey_.D0.Val(); break;
			case SWT.KEYPAD_1:				val = IptKey_.D1.Val(); break;
			case SWT.KEYPAD_2:				val = IptKey_.D2.Val(); break;
			case SWT.KEYPAD_3:				val = IptKey_.D3.Val(); break;
			case SWT.KEYPAD_4:				val = IptKey_.D4.Val(); break;
			case SWT.KEYPAD_5:				val = IptKey_.D5.Val(); break;
			case SWT.KEYPAD_6:				val = IptKey_.D6.Val(); break;
			case SWT.KEYPAD_7:				val = IptKey_.D7.Val(); break;
			case SWT.KEYPAD_8:				val = IptKey_.D8.Val(); break;
			case SWT.KEYPAD_9:				val = IptKey_.D9.Val(); break;

			// numpad symbols
			case SWT.KEYPAD_MULTIPLY:		val = IptKey_.Numpad_multiply.Val(); break;
			case SWT.KEYPAD_ADD:			val = IptKey_.Equal.Val(); break;
			case SWT.KEYPAD_SUBTRACT:  		val = IptKey_.Minus.Val(); break;
			case SWT.KEYPAD_DECIMAL:  		val = IptKey_.Period.Val(); break;
			case SWT.KEYPAD_DIVIDE:  		val = IptKey_.Numpad_divide.Val(); break;
			case SWT.KEYPAD_CR:  			val = IptKey_.Enter.Val(); break;
			
			// function keys
			case SWT.F1:					val = IptKey_.F1.Val(); break;
			case SWT.F2:					val = IptKey_.F2.Val(); break;
			case SWT.F3:					val = IptKey_.F3.Val(); break;
			case SWT.F4:					val = IptKey_.F4.Val(); break;
			case SWT.F5:					val = IptKey_.F5.Val(); break;
			case SWT.F6:					val = IptKey_.F6.Val(); break;
			case SWT.F7:					val = IptKey_.F7.Val(); break;
			case SWT.F8:					val = IptKey_.F8.Val(); break;
			case SWT.F9:					val = IptKey_.F9.Val(); break;
			case SWT.F10:					val = IptKey_.F10.Val(); break;
			case SWT.F11:					val = IptKey_.F11.Val(); break;
			case SWT.F12:					val = IptKey_.F12.Val(); break;

			// SWT=13; SWING=10; note that Cr maps to "enter key"
			case Byte_ascii.Cr:				val = IptKey_.Enter.Val(); break;

			case SWT.INSERT:				val = IptKey_.Insert.Val(); break;
			case 127:						val = IptKey_.Delete.Val(); break;
			
			// meta
			case SWT.PAUSE:					val = IptKey_.Pause.Val(); break;
			case SWT.PRINT_SCREEN: 			val = IptKey_.PrintScreen.Val(); break;

			// nav keys
			case SWT.ARROW_UP:				val = IptKey_.Up.Val(); break;				
			case SWT.ARROW_DOWN:			val = IptKey_.Down.Val(); break;				
			case SWT.ARROW_LEFT:			val = IptKey_.Left.Val(); break;
			case SWT.ARROW_RIGHT:			val = IptKey_.Right.Val(); break;
			case SWT.PAGE_UP:				val = IptKey_.PageUp.Val(); break;
			case SWT.PAGE_DOWN:				val = IptKey_.PageDown.Val(); break;
			case SWT.HOME:					val = IptKey_.Home.Val(); break;
			case SWT.END:					val = IptKey_.End.Val(); break;
			
			// locks
			case SWT.CAPS_LOCK:  			val = IptKey_.CapsLock.Val(); break;
			case SWT.NUM_LOCK:				val = IptKey_.NumLock.Val(); break;
			case SWT.SCROLL_LOCK:			val = IptKey_.ScrollLock.Val(); break;			
			
			// symbols; ASCII; no SWT const
			case 39:						val = IptKey_.Quote.Val(); break;
			case 44:						val = IptKey_.Comma.Val(); break;
			case 45:						val = IptKey_.Minus.Val(); break;
			case 46:						val = IptKey_.Period.Val(); break;
			case 47:						val = IptKey_.Slash.Val(); break;
			case 59:						val = IptKey_.Semicolon.Val(); break;
			case 61:						val = IptKey_.Equal.Val(); break;
			case 91:						val = IptKey_.OpenBracket.Val(); break;
			case 93:						val = IptKey_.CloseBracket.Val(); break;
			case 96:						val = IptKey_.Tick.Val(); break;

			// modifiers
			case SWT.CTRL:					val = IptKey_.Ctrl.Val(); break;
			case SWT.ALT:					val = IptKey_.Alt.Val(); break;
			case SWT.SHIFT:					val = IptKey_.Shift.Val(); break;
			
			// map Mac OS X cmd to Ctrl
			case SWT.COMMAND:				val = IptKey_.Ctrl.Val(); break;
		}

		// handle mod keys
		int swt_ctrl = Op_sys.Cur().Tid_is_osx() ? SWT.COMMAND : SWT.CTRL;
		val = Handle_modifier(ev, is_keydown, val, swt_ctrl		, IptKey_.Ctrl.Val());
		val = Handle_modifier(ev, is_keydown, val, SWT.ALT		, IptKey_.Alt.Val());
		val = Handle_modifier(ev, is_keydown, val, SWT.SHIFT	, IptKey_.Shift.Val());
		// Tfds.Write(String_.Format("val={0} keydown={1} keyCode={2} stateMask={3} keyLocation={4} character={5}", val, is_keydown, ev.keyCode, ev.stateMask, ev.keyLocation, ev.character));
		return IptEvtDataKey.int_(val);		
	}
	private static int Handle_modifier(KeyEvent ev, boolean is_keydown, int val, int swt, int swing) {
		// Conversion table for debugging
		// 
		// -------------------------
		// | code  |  SWT  | SWING |
		// |-------|-------|-------|
		// | 65536 | ALT   | SHIFT |
		// |131072 | SHIFT | CTRL  |
		// |262144 | CTRL  | ALT   |
		// -------------------------
		
		// Also, when debugging, note that ev.stateMask is always the value at the start of the event
		//
		// For example, if ctrl is pressed and nothing is held
		// * if is_keydown = y, then ev.stateMask is      0 (none)
		// * if is_keydown = n, then ev.stateMask is 262144 (ctrl)  
		// Note that ev.keyCode is 262144 (ctrl) in both examples
		// 
		// However, if ctrl is pressed and shift is already held:
		// * if is_keydown = y, then ev.stateMask is  65536 (shift)
		// * if is_keydown = n, then ev.stateMask is 327680 (shift + ctrl) 
		// Note that ev.keyCode is 262144 (ctrl) in both examples as well.

		// if SWT modifier is present, return val with SWING modifier; else, just return val
		return Bitmask_.Has_int(ev.stateMask, swt) ? val | swing : val;
	}
	public static boolean Has_ctrl(int val) {return Bitmask_.Has_int(val, SWT.CTRL);} 
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
