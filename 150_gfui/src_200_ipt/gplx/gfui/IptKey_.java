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
package gplx.gfui; import gplx.*;
import java.awt.event.KeyEvent;
public class IptKey_ {
	private static EnmMgr enmMgr = EnmMgr.new_().BitRngBgn_(65536).BitRngEnd_(262144).Prefix_("key.");
	public static IptKey[] Ary(IptKey... ary) {return ary;}
	public static IptKey as_(Object obj) {return obj instanceof IptKey ? (IptKey)obj : null;}
	public static IptKey cast_(Object obj) {try {return (IptKey)obj;} catch(Exception exc) {throw Err_.type_mismatch_exc_(exc, IptKey.class, obj);}}
	public static IptKey add_(IptKey... ary) {
		if (ary.length == 0) return IptKey_.None;
		int newVal = ary[0].Val();
		for (int i = 1; i < ary.length; i++)
			newVal = Enm_.FlipInt(true, newVal, ary[i].Val());
		return getOrNew_(newVal);
	}
	public static IptKey api_(int val) {
		IptKey rv = (IptKey)enmMgr.Get(val);
		return (rv == null) ? new_(val, "key_" + Int_.Xto_str(val)) : rv;
	}
	public static IptKey parse_(String raw) {return getOrNew_(enmMgr.GetVal(raw));}
	public static IptKey rdr_or_(DataRdr rdr, String key, IptKey or) {
		String val = rdr.ReadStrOr(key, ""); // NOTE: "" cannot be null, b/c nullRdr returns String.empty
		return (String_.Eq(val, "")) ? or : parse_(val);
	}
	public static ListAdp printableKeys2_(IptKey[] add, IptKey[] del) {
		ListAdp list = ListAdp_.new_();
		for (IptKey key : add)
			list.Add(key);
		IptKeyStrMgr._.XtoIptKeyAry(list);
		for (IptKey key : del)
			list.Del(key);
		return list;
	}
	public static IptKey[] printableKeys_(IptKey[] add, IptKey[] del) {
		ListAdp list = ListAdp_.new_();
		for (IptKey key : add)
			list.Add(key);
		IptKeyStrMgr._.XtoIptKeyAry(list);
		for (IptKey key : del)
			list.Del(key);
		return (IptKey[])list.XtoAry(IptKey.class);
	}
	static IptKey getOrNew_(int val) {
		IptKey rv = (IptKey)enmMgr.Get(val);
		return (rv == null) ? new_(val, enmMgr.GetStr(val)) : rv;
	}
	static IptKey new_(int val, String name) {
		IptKey rv = new IptKey(val, String_.HasAtBgn(name, "key.") ? name : "key." + name);
		enmMgr.RegObj(val, name, rv);
		return rv;
	}
	public static final int KeyCode_Shift = 65536, KeyCode_Ctrl = 131072, KeyCode_Alt = 262144;
	public static final IptKey 
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
	;
}
