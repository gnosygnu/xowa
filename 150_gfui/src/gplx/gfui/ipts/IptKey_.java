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
		IptKeyStrMgr.Instance.XtoIptKeyAry(list);
		for (IptKey key : del)
			list.Del(key);
		return list;
	}
	public static IptKey[] printableKeys_(IptKey[] add, IptKey[] del) {
		List_adp list = List_adp_.New();
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
	  None = new_(0, "none")
	, Back = new_(8, "back"), Tab = new_(9, "tab"), Clear = new_(12, "clear"), Enter = new_(KeyEvent.VK_ENTER, "enter")	
	, ShiftKey = new_(16, "shiftKey"), CtrlKey = new_(17, "ctrlKey"), AltKey = new_(18, "altKey")
	, Pause = new_(KeyEvent.VK_PAUSE, "pause")	
	, CapsLock = new_(20, "capsLock"), Escape = new_(27, "escape"), Space = new_(32, "space")
	, PageUp = new_(33, "pageUp"), PageDown = new_(34, "pageDown"), End = new_(35, "end"), Home = new_(36, "home")
	, Left = new_(37, "left"), Up = new_(38, "up"), Right = new_(39, "right"), Down = new_(40, "down")
	, PrintScreen = new_(44, "printScreen"), Insert = new_(45, "insert")
	, Delete = new_(KeyEvent.VK_DELETE, "delete")					
	, D0 = new_(48, "d0"), D1 = new_(49, "d1"), D2 = new_(50, "d2"), D3 = new_(51, "d3"), D4 = new_(52, "d4")
	, D5 = new_(53, "d5"), D6 = new_(54, "d6"), D7 = new_(55, "d7"), D8 = new_(56, "d8"), D9 = new_(57, "d9")
	, A = new_(65, "a"), B = new_(66, "b"), C = new_(67, "c"), D = new_(68, "d"), E = new_(69, "e")
	, F = new_(70, "f"), G = new_(71, "g"), H = new_(72, "h"), I = new_(73, "i"), J = new_(74, "j")
	, K = new_(75, "k"), L = new_(76, "l"), M = new_(77, "m"), N = new_(78, "n"), O = new_(79, "o")
	, P = new_(80, "p"), Q = new_(81, "q"), R = new_(82, "r"), S = new_(83, "s"), T = new_(84, "t")
	, U = new_(85, "u"), V = new_(86, "v"), W = new_(87, "w"), X = new_(88, "x"), Y = new_(89, "y"), Z = new_(90, "z")
	, F1 = new_(112, "f1"), F2 = new_(113, "f2"), F3 = new_(114, "f3"), F4 = new_(115, "f4"), F5 = new_(116, "f5"), F6 = new_(117, "f6")
	, F7 = new_(118, "f7"), F8 = new_(119, "f8"), F9 = new_(120, "f9"), F10 = new_(121, "f10"), F11 = new_(122, "f11"), F12 = new_(123, "f12")
	, NumLock = new_(144, "numLock"), ScrollLock = new_(145, "scrollLock")
	, Semicolon = new_(KeyEvent.VK_SEMICOLON, "semicolon")			
	, Equal = new_(KeyEvent.VK_EQUALS, "equal")					
	, Comma = new_(KeyEvent.VK_COMMA, "comma")					
	, Minus = new_(KeyEvent.VK_MINUS, "minus")					
	, Period = new_(KeyEvent.VK_PERIOD, "period")					
	, Slash = new_(KeyEvent.VK_SLASH, "slash")					
	, Tick = new_(KeyEvent.VK_BACK_QUOTE, "tick")						
	, OpenBracket = new_(219, "openBracket")
	, Backslash = new_(KeyEvent.VK_BACK_SLASH, "backslash")			
	, CloseBracket = new_(221, "closeBracket")
	, Quote = new_(222, "quote")
	, Shift = new_(KeyCode_Shift, "shift"), Ctrl = new_(KeyCode_Ctrl, "ctrl"), Alt = new_(KeyCode_Alt, "alt")
	, Keypad_enter = new_(16777296, "keypad_enter")
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
			, IptKey_.OpenBracket, IptKey_.Back, IptKey_.CloseBracket, IptKey_.Quote
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
	public static String To_str(int val) {
		String mod_str = "", rv = "";
		boolean mod_c = Bitmask_.Has_int(val, IptKey_.Ctrl.Val());	if (mod_c) {mod_str += "c"; val = Bitmask_.Flip_int(Bool_.N, val, IptKey_.Ctrl.Val());}
		boolean mod_a = Bitmask_.Has_int(val, IptKey_.Alt.Val());	if (mod_a) {mod_str += "a"; val = Bitmask_.Flip_int(Bool_.N, val, IptKey_.Alt.Val());}
		boolean mod_s = Bitmask_.Has_int(val, IptKey_.Shift.Val()); if (mod_s) {mod_str += "s"; val = Bitmask_.Flip_int(Bool_.N, val, IptKey_.Shift.Val());}
		if (String_.Len_gt_0(mod_str)) {
			rv = "mod." + mod_str;
            if (val == 0) return rv;	// handle modifiers only, like "mod.cs"; else will be "mod.cs+key.#0"
			rv += "+";
		}
		IptKey key = (IptKey)IptKey_.Ui_str_hash().Get_by(Int_obj_ref.New(val));
		String key_str = key == null ? "key.#" + Int_.To_str(val) : key.Key();
		return rv + key_str;
	}
}
