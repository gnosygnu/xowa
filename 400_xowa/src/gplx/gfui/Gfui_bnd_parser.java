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
package gplx.gfui; import gplx.*;
import gplx.core.bits.*;
public class Gfui_bnd_parser {
	private Bry_bfr tmp_bfr = Bry_bfr_.Reset(32);
	private Hash_adp_bry
	  gfui_regy = Hash_adp_bry.ci_a7()
	, norm_regy = Hash_adp_bry.ci_a7()
	;
	private static final    Gfui_bnd_tkn
	  Itm_sym_plus		= new_sym_(Gfui_bnd_tkn.Tid_sym_plus	, new byte[] {Byte_ascii.Plus})
	, Itm_sym_pipe		= new_sym_(Gfui_bnd_tkn.Tid_sym_pipe	, new byte[] {Byte_ascii.Pipe})
	, Itm_sym_comma		= new_sym_(Gfui_bnd_tkn.Tid_sym_comma	, new byte[] {Byte_ascii.Comma})
//		, Itm_sym_ws		= new_sym_(Gfui_bnd_tkn.Tid_sym_ws		, Bry_.Empty)
	, Itm_sym_eos		= new_sym_(Gfui_bnd_tkn.Tid_sym_eos		, Bry_.Empty)
	;
	private static final    Gfui_bnd_tkn[] Mod_ary = new Gfui_bnd_tkn[] 
	{ null
	, new_mod_(Gfui_bnd_tkn.Tid_mod_c		, "mod.c"	, "Ctrl")
	, new_mod_(Gfui_bnd_tkn.Tid_mod_a		, "mod.a"	, "Alt")
	, new_mod_(Gfui_bnd_tkn.Tid_mod_ca		, "mod.ca"	, "Ctrl + Alt")
	, new_mod_(Gfui_bnd_tkn.Tid_mod_s		, "mod.s"	, "Shift")
	, new_mod_(Gfui_bnd_tkn.Tid_mod_cs		, "mod.cs"	, "Ctrl + Shift")
	, new_mod_(Gfui_bnd_tkn.Tid_mod_as		, "mod.as"	, "Alt + Shift")
	, new_mod_(Gfui_bnd_tkn.Tid_mod_cas		, "mod.cas"	, "Ctrl + Alt + Shift")
	};
	private byte[] src; private int src_len;
	private List_adp tkns = List_adp_.New(); private int mod_val = Mod_val_null;
	public String Xto_norm(String src_str) {return Convert(Bool_.Y, src_str);}
	public String Xto_gfui(String src_str) {return Convert(Bool_.N, src_str);}
	private String Convert(boolean src_is_gfui, String src_str) {			
		this.src = Bry_.new_u8(src_str); this.src_len = src.length;			
		tkns.Clear(); mod_val = Mod_val_null;
		int pos = 0; int itm_bgn = -1, itm_end = -1; boolean is_numeric = false;
		while (pos <= src_len) {			// loop over bytes and break up tkns by symbols
			byte b = pos == src_len ? Byte_ascii.Nl: src[pos];	// treat eos as "\n" for purpose of separating tokens
			Gfui_bnd_tkn sym_tkn = null;
			switch (b) {
				case Byte_ascii.Plus:		// simultaneous; EX: Ctrl + S
					sym_tkn = Itm_sym_plus;
					break;
				case Byte_ascii.Pipe:		// alternate; EX: Ctrl + S | Ctrl + Alt + s
					sym_tkn = Itm_sym_pipe;
					break;
				case Byte_ascii.Comma:		// chorded; EX: Ctrl + S, Ctrl + T
					sym_tkn = Itm_sym_comma;
					break;
				case Byte_ascii.Nl:	// eos: process anything in bfr
					sym_tkn = Itm_sym_eos;
					break;
				case Byte_ascii.Space:
					if (itm_bgn != -1)		// if word started, " " ends word; EX: "Ctrl + A"; " +" ends "Ctrl"
						itm_end = pos;
					++pos;
					continue;
				case Byte_ascii.Hash:
					if (is_numeric) throw Err_.new_wo_type("multiple numeric symbols in keycode");
					is_numeric = true;
					++pos;
					continue;
				default:					// letter / number; continue
					if (itm_bgn == -1)		// no word started; start it
						itm_bgn = pos;
					++pos;
					continue;
			}
			if (itm_end == -1)				// end not set by space; char before symbol is end
				itm_end = pos;
			Process_sym(src_is_gfui, is_numeric, sym_tkn, itm_bgn, itm_end);
			is_numeric = false;
			if (sym_tkn.Tid() == Gfui_bnd_tkn.Tid_sym_eos)
				break;
			else
				++pos;
			itm_bgn = itm_end = -1;
		}
		int tkns_len = tkns.Count();
		for (int i = 0; i < tkns_len; i++) {
			Gfui_bnd_tkn tkn = (Gfui_bnd_tkn)tkns.Get_at(i);
			tkn.Write(tmp_bfr, !src_is_gfui);
		}
		return tmp_bfr.To_str_and_clear();
	}
	private void Process_sym(boolean src_is_gfui, boolean is_numeric, Gfui_bnd_tkn sym_tkn, int itm_bgn, int itm_end) {
		Hash_adp_bry regy = src_is_gfui ? gfui_regy : norm_regy;
		Gfui_bnd_tkn tkn = null;
		if (is_numeric) {	// EX: "key.#10" or "#10"
			int tkn_bgn = itm_bgn;
			if (src_is_gfui) {	// remove "key." in "key.#10"
				tkn_bgn = Bry_find_.Move_fwd(src, Byte_ascii.Dot, itm_bgn, itm_end);
				if (tkn_bgn == -1) throw Err_.new_wo_type("invalid keycode.dot", "keycode", Bry_.Mid(src, tkn_bgn, itm_end));
				++tkn_bgn;		// skip #
			}
			int keycode = Bry_.To_int_or(src, tkn_bgn, itm_end, -1); if (keycode == -1) throw Err_.new_wo_type("invalid keycode", "keycode", Bry_.Mid(src, tkn_bgn, itm_end));
			tkn = new Gfui_bnd_tkn(Gfui_bnd_tkn.Tid_key, keycode, Bry_.Empty, Bry_.Empty);
		}
		else
			tkn = (Gfui_bnd_tkn)regy.Get_by_mid(src, itm_bgn, itm_end);
		if (tkn == null) return;
		int mod_adj = Mod_val_null;
		switch (tkn.Tid()) {
			case Gfui_bnd_tkn.Tid_mod_c:		mod_adj = Gfui_bnd_tkn.Tid_mod_c; break;
			case Gfui_bnd_tkn.Tid_mod_a:		mod_adj = Gfui_bnd_tkn.Tid_mod_a; break;
			case Gfui_bnd_tkn.Tid_mod_s:		mod_adj = Gfui_bnd_tkn.Tid_mod_s; break;
			case Gfui_bnd_tkn.Tid_mod_cs:		mod_adj = Gfui_bnd_tkn.Tid_mod_cs; break;
			case Gfui_bnd_tkn.Tid_mod_as:		mod_adj = Gfui_bnd_tkn.Tid_mod_as; break;
			case Gfui_bnd_tkn.Tid_mod_ca:		mod_adj = Gfui_bnd_tkn.Tid_mod_ca; break;
			case Gfui_bnd_tkn.Tid_mod_cas:		mod_adj = Gfui_bnd_tkn.Tid_mod_cas; break;
			case Gfui_bnd_tkn.Tid_key:			break;
			default: throw Err_.new_unhandled(tkn.Tid());
		}
		switch (sym_tkn.Tid()) {
			case Gfui_bnd_tkn.Tid_sym_plus:		// EX: Ctrl + A
				if (mod_adj != Mod_val_null) {	// if mod, just update mod_val and exit
					mod_val = Bitmask_.Flip_int(true, mod_val, mod_adj);
					return;
				}
				break;
		}
		if (mod_val != Mod_val_null) {			// modifier exists; add tkn
			tkns.Add(Mod_ary[mod_val]);
			tkns.Add(Itm_sym_plus);
			mod_val = Mod_val_null;
		}
		tkns.Add(tkn);							// add word
		if (sym_tkn.Tid() != Gfui_bnd_tkn.Tid_sym_eos)
			tkns.Add(sym_tkn);
	}
	private Gfui_bnd_parser Init_en() {
		Init_itm_mod(Gfui_bnd_tkn.Tid_mod_c);
		Init_itm_mod(Gfui_bnd_tkn.Tid_mod_a);
		Init_itm_mod(Gfui_bnd_tkn.Tid_mod_s);
		Init_itm_mod(Gfui_bnd_tkn.Tid_mod_ca);
		Init_itm_mod(Gfui_bnd_tkn.Tid_mod_cs);
		Init_itm_mod(Gfui_bnd_tkn.Tid_mod_as);
		Init_itm_mod(Gfui_bnd_tkn.Tid_mod_cas);
		Init_itm(Gfui_bnd_tkn.Tid_mod_c, "key.ctrl", "Ctrl");
		Init_itm(Gfui_bnd_tkn.Tid_mod_a, "key.alt", "Atl");
		Init_itm(Gfui_bnd_tkn.Tid_mod_s, "key.shift", "Shift");
		Init_itm("key.a", "A");
		Init_itm("key.b", "B");
		Init_itm("key.c", "C");
		Init_itm("key.d", "D");
		Init_itm("key.e", "E");
		Init_itm("key.f", "F");
		Init_itm("key.g", "G");
		Init_itm("key.h", "H");
		Init_itm("key.i", "I");
		Init_itm("key.j", "J");
		Init_itm("key.k", "K");
		Init_itm("key.l", "L");
		Init_itm("key.m", "M");
		Init_itm("key.n", "N");
		Init_itm("key.o", "O");
		Init_itm("key.p", "P");
		Init_itm("key.q", "Q");
		Init_itm("key.r", "R");
		Init_itm("key.s", "S");
		Init_itm("key.t", "T");
		Init_itm("key.u", "U");
		Init_itm("key.v", "V");
		Init_itm("key.w", "W");
		Init_itm("key.x", "X");
		Init_itm("key.y", "Y");
		Init_itm("key.z", "Z");
		Init_itm("key.d0", "0");
		Init_itm("key.d1", "1");
		Init_itm("key.d2", "2");
		Init_itm("key.d3", "3");
		Init_itm("key.d4", "4");
		Init_itm("key.d5", "5");
		Init_itm("key.d6", "6");
		Init_itm("key.d7", "7");
		Init_itm("key.d8", "8");
		Init_itm("key.d9", "9");
		Init_itm("key.f1", "F1");
		Init_itm("key.f2", "F2");
		Init_itm("key.f3", "F3");
		Init_itm("key.f4", "F4");
		Init_itm("key.f5", "F5");
		Init_itm("key.f6", "F6");
		Init_itm("key.f7", "F7");
		Init_itm("key.f8", "F8");
		Init_itm("key.f9", "F9");
		Init_itm("key.f10", "F10");
		Init_itm("key.f11", "F11");
		Init_itm("key.f12", "F12");
		Init_itm("key.none", "None");
		Init_itm("key.back", "Backspace");
		Init_itm("key.tab", "Tab");
		Init_itm("key.clear", "Clear");
		Init_itm("key.enter", "Enter");
		Init_itm("key.shiftKey", "ShiftKey");
		Init_itm("key.ctrlKey", "CtrlKey");
		Init_itm("key.altKey", "AltKey");
		Init_itm("key.pause", "Pause");
		Init_itm("key.capsLock", "CapsLock");
		Init_itm("key.escape", "Escape");
		Init_itm("key.space", "Space");
		Init_itm("key.pageUp", "PageUp");
		Init_itm("key.pageDown", "PageDown");
		Init_itm("key.end", "End");
		Init_itm("key.home", "Home");
		Init_itm("key.left", "Left");
		Init_itm("key.up", "Up");
		Init_itm("key.right", "Right");
		Init_itm("key.down", "Down");
		Init_itm("key.printScreen", "PrintScreen");
		Init_itm("key.insert", "Insert");
		Init_itm("key.delete", "Delete");
		Init_itm("key.numLock", "NumLock");
		Init_itm("key.scrollLock", "ScrollLock");
		Init_itm("key.semicolon", "Semicolon");
		Init_itm("key.equal", "Equal");
		Init_itm("key.comma", "Comma");
		Init_itm("key.minus", "Minus");
		Init_itm("key.period", "Period");
		Init_itm("key.slash", "Slash");
		Init_itm("key.tick", "Tick");
		Init_itm("key.openBracket", "OpenBracket");
		Init_itm("key.backslash", "Backslash");
		Init_itm("key.closeBracket", "CloseBracket");
		Init_itm("key.quote", "Quote");
		Init_itm("mouse.middle", "Middle Click");
		Init_itm("mouse.left", "Left Click");
		Init_itm("mouse.right", "Right Click");
		Init_itm("key.numpad_0", "Numpad 0");
		Init_itm("key.numpad_1", "Numpad 1");
		Init_itm("key.numpad_2", "Numpad 2");
		Init_itm("key.numpad_3", "Numpad 3");
		Init_itm("key.numpad_4", "Numpad 4");
		Init_itm("key.numpad_5", "Numpad 5");
		Init_itm("key.numpad_6", "Numpad 6");
		Init_itm("key.numpad_7", "Numpad 7");
		Init_itm("key.numpad_8", "Numpad 8");
		Init_itm("key.numpad_9", "Numpad 9");
		Init_itm("key.numpad_multiply", "Numpad Multiply");
		Init_itm("key.numpad_add", "Numpad Add");
		Init_itm("key.numpad_subtract", "Numpad Subtract");
		Init_itm("key.numpad_decimal", "Numpad Decimal");
		Init_itm("key.numpad_divide", "Numpad Divide");
		Init_itm("key.numpad_enter", "Numpad Enter");
		return this;
	}
	private void Init_itm(String gfui, String norm) {Init_itm(Gfui_bnd_tkn.Tid_key, gfui, norm);}
	private void Init_itm_mod(int tid) {
		Gfui_bnd_tkn itm = Mod_ary[tid];
		gfui_regy.Add(itm.Bry_gfui(), itm);
		norm_regy.Add(itm.Bry_norm(), itm);
	}
	private void Init_itm(byte tid, String gfui, String norm) {
		byte[] gfui_bry = Bry_.new_u8(gfui);
		byte[] norm_bry = Bry_.new_u8(norm);
		Gfui_bnd_tkn itm = new Gfui_bnd_tkn(tid, Gfui_bnd_tkn.Keycode_null, gfui_bry, norm_bry);
		gfui_regy.Add(gfui_bry, itm);
		norm_regy.Add_if_dupe_use_1st(norm_bry, itm);
	}
	private static final int Mod_val_null = 0;
	public static Gfui_bnd_parser new_en_() {return new Gfui_bnd_parser().Init_en();} Gfui_bnd_parser() {}
	private static Gfui_bnd_tkn new_sym_(byte tid, byte[] bry) {return new Gfui_bnd_tkn(tid, Gfui_bnd_tkn.Keycode_null, bry, bry);}
	private static Gfui_bnd_tkn new_mod_(byte tid, String gfui, String norm) {return new Gfui_bnd_tkn(tid, Gfui_bnd_tkn.Keycode_null, Bry_.new_a7(gfui), Bry_.new_a7(norm));}
}
class Gfui_bnd_tkn {
	public Gfui_bnd_tkn(byte tid, int keycode, byte[] gfui, byte[] norm) {
		this.tid = tid; this.keycode = keycode; ; this.bry_gfui = gfui; this.bry_norm = norm;
	}
	public byte Tid() {return tid;} private final    byte tid;
	public int Keycode() {return keycode;} private final    int keycode;
	public byte[] Bry_gfui() {return bry_gfui;} private final    byte[] bry_gfui;
	public byte[] Bry_norm() {return bry_norm;} private final    byte[] bry_norm;
	public void Write(Bry_bfr bfr, boolean src_is_gfui) {
		if (keycode != Gfui_bnd_tkn.Keycode_null) {
			if (src_is_gfui)
				bfr.Add(Bry_key_prefix);
			bfr.Add_byte(Byte_ascii.Hash).Add_int_variable(keycode);
			return;
		}
		byte[] bry = src_is_gfui ? bry_gfui : bry_norm;
		switch (tid) {
			case Tid_mod_c:		case Tid_mod_a:		case Tid_mod_s:
			case Tid_mod_ca:	case Tid_mod_cs:	case Tid_mod_as: case Tid_mod_cas:
				bfr.Add(bry);
				break;
			case Tid_sym_plus:
				if (!src_is_gfui)
					bfr.Add_byte_space();
				bfr.Add(bry);
				if (!src_is_gfui)
					bfr.Add_byte_space();
				break;
			case Tid_sym_pipe:
				if (!src_is_gfui)
					bfr.Add_byte_space();
				bfr.Add(bry);
				if (!src_is_gfui)
					bfr.Add_byte_space();
				break;
			case Tid_sym_comma:
				bfr.Add(bry);
				if (!src_is_gfui)
					bfr.Add_byte_space();
				break;
			case Tid_key:
				bfr.Add(bry);
				break;
		}
	}
	public static final byte 
	  Tid_mod_c		=  1	, Tid_mod_a		=  2	, Tid_mod_s		=  4
	, Tid_mod_ca	=  3	, Tid_mod_cs	=  5	, Tid_mod_as	=  6,	Tid_mod_cas	=  7
	, Tid_sym_plus	=  8	, Tid_sym_pipe	=  9	, Tid_sym_comma = 10,	Tid_sym_eos = 11
	, Tid_key		= 12
	;
	public static final int Keycode_null = 0;
	private static final    byte[] Bry_key_prefix = Bry_.new_a7("key.");
}
