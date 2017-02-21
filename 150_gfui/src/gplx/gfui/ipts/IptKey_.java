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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
import java.awt.event.KeyEvent;
import gplx.core.primitives.*; import gplx.core.stores.*; import gplx.core.bits.*;
public class IptKey_ {
	private static EnmMgr enm_mgr = EnmMgr.new_().BitRngBgn_(65536).BitRngEnd_(262144).Prefix_("key.");
	public static IptKey[] Ary(IptKey... ary) {return ary;}
	public static final    IptKey[] Ary_empty = new IptKey[0];
	public static IptKey as_(Object obj) {return obj instanceof IptKey ? (IptKey)obj : null;}
	public static IptKey cast(Object obj) {try {return (IptKey)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, IptKey.class, obj);}}
	public static IptKey add_(IptKey... ary) {
		if (ary.length == 0) return IptKey_.None;
		int newVal = ary[0].Val();
		for (int i = 1; i < ary.length; i++)
			newVal = Bitmask_.Flip_int(true, newVal, ary[i].Val());
		return get_or_new_(newVal);
	}
	public static IptKey api_(int val) {
		IptKey rv = (IptKey)enm_mgr.Get(val);
		return (rv == null) ? new_(val, "key_" + Int_.To_str(val)) : rv;
	}
	public static IptKey parse(String raw) {return get_or_new_(enm_mgr.GetVal(raw));}
	public static IptKey rdr_or_(DataRdr rdr, String key, IptKey or) {
		String val = rdr.ReadStrOr(key, ""); // NOTE: "" cannot be null, b/c nullRdr returns String.empty
		return (String_.Eq(val, "")) ? or : parse(val);
	}
	public static List_adp printableKeys2_(IptKey[] add, IptKey[] del) {
		List_adp list = List_adp_.New();
		for (IptKey key : add)
			list.Add(key);

		// add keypad numbers
		list.Add_many(IptKey_.Numpad_0, IptKey_.Numpad_1, IptKey_.Numpad_2, IptKey_.Numpad_3, IptKey_.Numpad_4);
		list.Add_many(IptKey_.Numpad_5, IptKey_.Numpad_6, IptKey_.Numpad_7, IptKey_.Numpad_8, IptKey_.Numpad_9);

		IptKeyStrMgr.Instance.XtoIptKeyAry(list);
		for (IptKey key : del)
			list.Del(key);
		return list;
	}
	public static IptKey[] printableKeys_(IptKey[] add, IptKey[] del) {
		List_adp list = List_adp_.New();

		// add keypad numbers
		list.Add_many(IptKey_.Numpad_0, IptKey_.Numpad_1, IptKey_.Numpad_2, IptKey_.Numpad_3, IptKey_.Numpad_4);
		list.Add_many(IptKey_.Numpad_5, IptKey_.Numpad_6, IptKey_.Numpad_7, IptKey_.Numpad_8, IptKey_.Numpad_9);

		for (IptKey key : add)
			list.Add(key);
		IptKeyStrMgr.Instance.XtoIptKeyAry(list);
		for (IptKey key : del)
			list.Del(key);
		return (IptKey[])list.To_ary(IptKey.class);
	}
	private static IptKey get_or_new_(int val) {
		IptKey rv = (IptKey)enm_mgr.Get(val);
		return (rv == null) ? new_(val, enm_mgr.GetStr(val)) : rv;
	}
	static IptKey new_(int val, String name) {
		IptKey rv = new IptKey(val, String_.Has_at_bgn(name, "key.") ? name : "key." + name);
		enm_mgr.RegObj(val, name, rv);
		return rv;
	}
	public static final int KeyCode_Shift = 65536, KeyCode_Ctrl = 131072, KeyCode_Alt = 262144;
	public static final    IptKey
	// NOTE: integer values represent .NET keycodes; NOTE: SWT keycodes are converted to SWING keycodes in Swt_core_lnrs
	// none
	  None			= new_(  0, "none")

	// numbers
	, D0 = new_(48, "d0"), D1 = new_(49, "d1"), D2 = new_(50, "d2"), D3 = new_(51, "d3"), D4 = new_(52, "d4")
	, D5 = new_(53, "d5"), D6 = new_(54, "d6"), D7 = new_(55, "d7"), D8 = new_(56, "d8"), D9 = new_(57, "d9")

	// letters
	, A = new_(65, "a"), B = new_(66, "b"), C = new_(67, "c"), D = new_(68, "d"), E = new_(69, "e")
	, F = new_(70, "f"), G = new_(71, "g"), H = new_(72, "h"), I = new_(73, "i"), J = new_(74, "j")
	, K = new_(75, "k"), L = new_(76, "l"), M = new_(77, "m"), N = new_(78, "n"), O = new_(79, "o")
	, P = new_(80, "p"), Q = new_(81, "q"), R = new_(82, "r"), S = new_(83, "s"), T = new_(84, "t")
	, U = new_(85, "u"), V = new_(86, "v"), W = new_(87, "w"), X = new_(88, "x"), Y = new_(89, "y"), Z = new_(90, "z")

	// numpad numbers
	, Numpad_0				= new_(KeyEvent.VK_NUMPAD0, "numpad_0")				
	, Numpad_1				= new_(KeyEvent.VK_NUMPAD1, "numpad_1")				
	, Numpad_2				= new_(KeyEvent.VK_NUMPAD2, "numpad_2")				
	, Numpad_3				= new_(KeyEvent.VK_NUMPAD3, "numpad_3")				
	, Numpad_4				= new_(KeyEvent.VK_NUMPAD4, "numpad_4")				
	, Numpad_5				= new_(KeyEvent.VK_NUMPAD5, "numpad_5")				
	, Numpad_6				= new_(KeyEvent.VK_NUMPAD6, "numpad_6")				
	, Numpad_7				= new_(KeyEvent.VK_NUMPAD7, "numpad_7")				
	, Numpad_8				= new_(KeyEvent.VK_NUMPAD8, "numpad_8")				
	, Numpad_9				= new_(KeyEvent.VK_NUMPAD9, "numpad_9")				

	// numpad keys
	, Numpad_multiply		= new_(KeyEvent.VK_MULTIPLY, "numpad_multiply")		
	, Numpad_add			= new_(KeyEvent.VK_ADD, "numpad_add")			
	, Numpad_subtract		= new_(KeyEvent.VK_SUBTRACT, "numpad_subtract")		
	, Numpad_decimal		= new_(KeyEvent.VK_DECIMAL, "numpad_decimal")		
	, Numpad_divide			= new_(KeyEvent.VK_DIVIDE, "numpad_divide")		
	// NOTE: mapping numpad_enter to enter b/c don't want to define two sets of enters for every binding
	//, Numpad_enter			= new_(KeyEvent.VK_ENTER, "numpad_enter")			
	// NOTE: using same value as SWT; SWING value is not available
	, Numpad_enter			= new_(16777296, "numpad_enter")

	// function keys; not supporting f13-f24 b/c only for IBM 3270 keyboards and can't test; also, note that codes differ between .net and swing
	, F1					= new_(KeyEvent.VK_F1, "f1")					
	, F2					= new_(KeyEvent.VK_F2, "f2")					
	, F3					= new_(KeyEvent.VK_F3, "f3")					
	, F4					= new_(KeyEvent.VK_F4, "f4")					
	, F5					= new_(KeyEvent.VK_F5, "f5")					
	, F6					= new_(KeyEvent.VK_F6, "f6")					
	, F7					= new_(KeyEvent.VK_F7, "f7")					
	, F8					= new_(KeyEvent.VK_F8, "f8")					
	, F9					= new_(KeyEvent.VK_F9, "f9")					
	, F10					= new_(KeyEvent.VK_F10, "f10")					
	, F11					= new_(KeyEvent.VK_F11, "f11")					
	, F12					= new_(KeyEvent.VK_F12, "f12")					

	// whitespace
	, Tab					= new_(KeyEvent.VK_TAB, "tab")					
	, Enter					= new_(KeyEvent.VK_ENTER, "enter")				
	, Space					= new_(KeyEvent.VK_SPACE, "space")				

	// delete
	, Back					= new_(KeyEvent.VK_BACK_SPACE, "back")					
	, Clear					= new_(KeyEvent.VK_CLEAR, "clear")				
	, Insert				= new_(KeyEvent.VK_INSERT, "insert")				
	, Delete				= new_(KeyEvent.VK_DELETE, "delete")				

	// meta
	, Escape				= new_(KeyEvent.VK_ESCAPE, "escape")				
	, Pause					= new_(KeyEvent.VK_PAUSE, "pause")				
	, PrintScreen			= new_(KeyEvent.VK_PRINTSCREEN, "printScreen")			

	// navigation: page / home
	, PageUp				= new_(KeyEvent.VK_PAGE_UP, "pageUp")				
	, PageDown				= new_(KeyEvent.VK_PAGE_DOWN, "pageDown")				
	, End					= new_(KeyEvent.VK_END, "end")					
	, Home					= new_(KeyEvent.VK_HOME, "home")					

	// navigation: lr / ud
	, Left					= new_(KeyEvent.VK_LEFT, "left")					
	, Up					= new_(KeyEvent.VK_UP, "up")					
	, Right					= new_(KeyEvent.VK_RIGHT, "right")				
	, Down					= new_(KeyEvent.VK_DOWN, "down")					

	// locks
	, CapsLock				= new_(KeyEvent.VK_CAPS_LOCK, "capsLock")				
	, NumLock				= new_(KeyEvent.VK_NUM_LOCK, "numLock")				
	, ScrollLock			= new_(KeyEvent.VK_SCROLL_LOCK, "scrollLock")			

	// symbols
	, Semicolon				= new_(KeyEvent.VK_SEMICOLON, "semicolon")			
	, Equal					= new_(KeyEvent.VK_EQUALS, "equal")				
	, Comma					= new_(KeyEvent.VK_COMMA, "comma")				
	, Minus					= new_(KeyEvent.VK_MINUS, "minus")				
	, Period				= new_(KeyEvent.VK_PERIOD, "period")				
	, Slash					= new_(KeyEvent.VK_SLASH, "slash")				
	, Tick					= new_(KeyEvent.VK_BACK_QUOTE, "tick")					
	, OpenBracket			= new_(KeyEvent.VK_OPEN_BRACKET, "openBracket")			
	, Backslash				= new_(KeyEvent.VK_BACK_SLASH, "backslash")			
	, CloseBracket			= new_(KeyEvent.VK_CLOSE_BRACKET, "closeBracket")			
	, Quote					= new_(222, "quote")

	// modifiers
	, Shift					= new_(KeyCode_Shift, "shift")
	, Ctrl					= new_(KeyCode_Ctrl, "ctrl")
	, Alt					= new_(KeyCode_Alt, "alt")
	, ShiftKey				= new_(16, "shiftKey")			, CtrlKey = new_(17, "ctrlKey")		, AltKey = new_(18, "altKey")	// NOTE: used for .NET NPI
	;
	private static Ordered_hash ui_str_hash;
	public static Ordered_hash Ui_str_hash() {
		if (ui_str_hash == null) {
			ui_str_hash = Ordered_hash_.New();
			All_add(ui_str_hash
			, IptKey_.Back, IptKey_.Tab, IptKey_.Clear, IptKey_.Enter
			, IptKey_.Pause, IptKey_.CapsLock, IptKey_.Escape, IptKey_.Space
			, IptKey_.PageUp, IptKey_.PageDown, IptKey_.End, IptKey_.Home
			, IptKey_.Left, IptKey_.Up, IptKey_.Right, IptKey_.Down
			, IptKey_.PrintScreen, IptKey_.Insert, IptKey_.Delete
			, IptKey_.D0, IptKey_.D1, IptKey_.D2, IptKey_.D3, IptKey_.D4
			, IptKey_.D5, IptKey_.D6, IptKey_.D7, IptKey_.D8, IptKey_.D9
			, IptKey_.A, IptKey_.B, IptKey_.C, IptKey_.D, IptKey_.E
			, IptKey_.F, IptKey_.G, IptKey_.H, IptKey_.I, IptKey_.J
			, IptKey_.K, IptKey_.L, IptKey_.M, IptKey_.N, IptKey_.O
			, IptKey_.P, IptKey_.Q, IptKey_.R, IptKey_.S, IptKey_.T
			, IptKey_.U, IptKey_.V, IptKey_.W, IptKey_.X, IptKey_.Y
			, IptKey_.Z
			, IptKey_.F1, IptKey_.F2, IptKey_.F3, IptKey_.F4, IptKey_.F5, IptKey_.F6
			, IptKey_.F7, IptKey_.F8, IptKey_.F9, IptKey_.F10, IptKey_.F11, IptKey_.F12
			, IptKey_.NumLock, IptKey_.ScrollLock
			, IptKey_.Semicolon, IptKey_.Equal, IptKey_.Comma, IptKey_.Minus, IptKey_.Period, IptKey_.Slash, IptKey_.Tick
			, IptKey_.OpenBracket, IptKey_.Backslash, IptKey_.CloseBracket, IptKey_.Quote
			, IptKey_.Numpad_0, IptKey_.Numpad_1, IptKey_.Numpad_2, IptKey_.Numpad_3, IptKey_.Numpad_4
			, IptKey_.Numpad_5, IptKey_.Numpad_6, IptKey_.Numpad_7, IptKey_.Numpad_8, IptKey_.Numpad_9
			, IptKey_.Numpad_multiply, IptKey_.Numpad_add, IptKey_.Numpad_subtract, IptKey_.Numpad_decimal, IptKey_.Numpad_divide
			, IptKey_.Numpad_enter
			);
		}
		return ui_str_hash;
	}
	private static void All_add(Ordered_hash hash, IptKey... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			IptKey key = ary[i];
			hash.Add_if_dupe_use_nth(Int_obj_ref.New(key.Val()), key);
		}
	}
	public static String To_str(int orig_val) {
		String mod_str = "", rv = "";
		int temp_val = orig_val;
		boolean mod_c = Bitmask_.Has_int(temp_val, IptKey_.Ctrl.Val());	if (mod_c) {mod_str += "c"; temp_val = Bitmask_.Flip_int(Bool_.N, temp_val, IptKey_.Ctrl.Val());}
		boolean mod_a = Bitmask_.Has_int(temp_val, IptKey_.Alt.Val());		if (mod_a) {mod_str += "a"; temp_val = Bitmask_.Flip_int(Bool_.N, temp_val, IptKey_.Alt.Val());}
		boolean mod_s = Bitmask_.Has_int(temp_val, IptKey_.Shift.Val());	if (mod_s) {mod_str += "s"; temp_val = Bitmask_.Flip_int(Bool_.N, temp_val, IptKey_.Shift.Val());}
		if (String_.Len_gt_0(mod_str)) {
			rv = "mod." + mod_str;
			// handle modifiers only, like "mod.cs"; else will be "mod.cs+key.#0"
            if (temp_val == 0) return rv;
			rv += "+";
		}
		IptKey key = (IptKey)IptKey_.Ui_str_hash().Get_by(Int_obj_ref.New(temp_val));
		String key_str = key == null ? "key.#" + Int_.To_str(temp_val) : key.Key();
		// Tfds.Write(rv + key_str, orig_val, temp_val, mod_c, mod_a, mod_s);
		return rv + key_str;
	}
}
