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
package gplx.xowa.xtns.scribunto.lib; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import gplx.xowa.net.*;
public class Scrib_lib_text implements Scrib_lib {
	public Scrib_lib_text(Scrib_core core) {this.core = core;} private Scrib_core core;
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.text.lua"));
		notify_wiki_changed_fnc = mod.Fncs_get_by_key("notify_wiki_changed");
		return mod;
	}	private Scrib_lua_proc notify_wiki_changed_fnc;
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_unstrip:							return Unstrip(args, rslt);
			case Proc_unstripNoWiki:					return UnstripNoWiki(args, rslt);
			case Proc_killMarkers:						return KillMarkers(args, rslt);
			case Proc_getEntityTable:					return GetEntityTable(args, rslt);
			case Proc_init_text_for_wiki:				return Init_text_for_wiki(args, rslt);
			default: throw Err_.unhandled(key);
		}
	}
	private static final int Proc_unstrip = 0, Proc_unstripNoWiki = 1, Proc_killMarkers = 2, Proc_getEntityTable = 3, Proc_init_text_for_wiki = 4;
	public static final String Invk_unstrip = "unstrip", Invk_unstripNoWiki = "unstripNoWiki", Invk_killMarkers = "killMarkers", Invk_getEntityTable = "getEntityTable", Invk_init_text_for_wiki = "init_text_for_wiki";
	private static final String[] Proc_names = String_.Ary(Invk_unstrip, Invk_unstripNoWiki, Invk_killMarkers, Invk_getEntityTable, Invk_init_text_for_wiki);
	public boolean Unstrip(Scrib_proc_args args, Scrib_proc_rslt rslt)			{return rslt.Init_obj(args.Pull_str(0));}	// NOTE: XOWA does not use MediaWiki strip markers; just return original; DATE:2015-01-20
	public boolean UnstripNoWiki(Scrib_proc_args args, Scrib_proc_rslt rslt)	{return rslt.Init_obj(args.Pull_str(0));}	// NOTE: XOWA does not use MediaWiki strip markers; just return original; DATE:2015-01-20
	public boolean KillMarkers(Scrib_proc_args args, Scrib_proc_rslt rslt)		{return rslt.Init_obj(args.Pull_str(0));}	// NOTE: XOWA does not use MediaWiki strip markers; just return original; DATE:2015-01-20
	public boolean GetEntityTable(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		if (Html_entity_ == null) Html_entity_ = Scrib_lib_text_html_entities.new_();
		return rslt.Init_obj(Html_entity_);
	}	private static KeyVal[] Html_entity_;
	public void Notify_wiki_changed() {if (notify_wiki_changed_fnc != null) core.Interpreter().CallFunction(notify_wiki_changed_fnc.Id(), KeyVal_.Ary_empty);}
	public boolean Init_text_for_wiki(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Xow_msg_mgr msg_mgr = core.Wiki().Msg_mgr();
		KeyVal[] rv = new KeyVal[4];
		rv[0] = KeyVal_.new_("comma", Init_lib_text_get_msg(msg_mgr, "comma-separator"));
		rv[1] = KeyVal_.new_("and", Init_lib_text_get_msg(msg_mgr, "and") + Init_lib_text_get_msg(msg_mgr, "word-separator"));
		rv[2] = KeyVal_.new_("ellipsis", Init_lib_text_get_msg(msg_mgr, "ellipsis"));
		rv[3] = KeyVal_.new_("nowiki_protocols", KeyVal_.Ary_empty);	// NOTE: code implemented, but waiting for it to be used; DATE:2014-03-20
		return rslt.Init_obj(rv);
	}
	private String Init_lib_text_get_msg(Xow_msg_mgr msg_mgr, String msg_key) {
		return String_.new_utf8_(msg_mgr.Val_by_key_obj(Bry_.new_utf8_(msg_key)));
	}
}
class Scrib_lib_text_ {
	public static KeyVal[] Init_nowiki_protocols(Xow_wiki wiki) {
		Bry_bfr bfr = wiki.Utl_bry_bfr_mkr().Get_b128();
		OrderedHash protocols = Xoo_protocol_itm.Regy;
		int len = protocols.Count();
		ListAdp rv = ListAdp_.new_();
		for (int i = 0; i < len; i++) {
			Xoo_protocol_itm itm = (Xoo_protocol_itm)protocols.FetchAt(i);
			if (itm.Text_ends_w_colon()) {	// To convert the protocol into a case-insensitive Lua pattern, we need to replace letters with a character class like [Xx] and insert a '%' before various punctuation.
				KeyVal kv = Init_nowiki_protocols_itm(bfr, itm);
				rv.Add(kv);
			}
		}
		bfr.Mkr_rls();
		return (KeyVal[])rv.Xto_ary(KeyVal.class);
	}
	private static KeyVal Init_nowiki_protocols_itm(Bry_bfr bfr, Xoo_protocol_itm itm) {
		byte[] key = itm.Key_wo_colon_bry();
		int end = key.length - 1;	// -1 to ignore final colon
		for (int i = 0; i < end; i++) {
			byte b = key[i];
			switch (b) {
				case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
				case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
				case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
				case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
				case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
					bfr.Add_byte(Byte_ascii.Brack_bgn).Add_byte(b).Add_byte(Byte_ascii.Case_lower(b)).Add_byte(Byte_ascii.Brack_end);	// [Aa]
					break;
				case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
				case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
				case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
				case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
				case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
					bfr.Add_byte(Byte_ascii.Brack_bgn).Add_byte(Byte_ascii.Case_upper(b)).Add_byte(b).Add_byte(Byte_ascii.Brack_end);	// [Aa]
					break;
				case Byte_ascii.Paren_bgn: case Byte_ascii.Paren_end: case Byte_ascii.Pow: case Byte_ascii.Dollar: case Byte_ascii.Percent: case Byte_ascii.Dot:
				case Byte_ascii.Brack_bgn: case Byte_ascii.Brack_end: case Byte_ascii.Asterisk: case Byte_ascii.Plus: case Byte_ascii.Question: case Byte_ascii.Dash:
					bfr.Add_byte(Byte_ascii.Percent).Add_byte(b);	// regex is '/([a-zA-Z])|([()^$%.\[\]*+?-])/'
					break;
				default:	// ignore
					break;
			}
		}
		bfr.Add(Colon_encoded);
		return KeyVal_.new_(itm.Key_wo_colon_str(), bfr.Xto_str_and_clear());
	}	private static final byte[] Colon_encoded = Bry_.new_ascii_("&#58;");
}
