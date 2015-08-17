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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
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
			case Proc_jsonEncode:						return JsonEncode(args, rslt);
			case Proc_jsonDecode:						return JsonDecode(args, rslt);
			default: throw Err_.new_unhandled(key);
		}
	}
	private static final int Proc_unstrip = 0, Proc_unstripNoWiki = 1, Proc_killMarkers = 2, Proc_getEntityTable = 3, Proc_init_text_for_wiki = 4, Proc_jsonEncode = 5, Proc_jsonDecode = 6;
	public static final String Invk_unstrip = "unstrip", Invk_unstripNoWiki = "unstripNoWiki", Invk_killMarkers = "killMarkers", Invk_getEntityTable = "getEntityTable"
	, Invk_init_text_for_wiki = "init_text_for_wiki", Invk_jsonEncode = "jsonEncode", Invk_jsonDecode = "jsonDecode";
	private static final String[] Proc_names = String_.Ary(Invk_unstrip, Invk_unstripNoWiki, Invk_killMarkers, Invk_getEntityTable, Invk_init_text_for_wiki, Invk_jsonEncode, Invk_jsonDecode);
	public boolean Unstrip(Scrib_proc_args args, Scrib_proc_rslt rslt)			{return rslt.Init_obj(args.Pull_str(0));}	// NOTE: XOWA does not use MediaWiki strip markers; just return original; DATE:2015-01-20
	public boolean UnstripNoWiki(Scrib_proc_args args, Scrib_proc_rslt rslt)	{return rslt.Init_obj(args.Pull_str(0));}	// NOTE: XOWA does not use MediaWiki strip markers; just return original; DATE:2015-01-20
	public boolean KillMarkers(Scrib_proc_args args, Scrib_proc_rslt rslt)		{return rslt.Init_obj(args.Pull_str(0));}	// NOTE: XOWA does not use MediaWiki strip markers; just return original; DATE:2015-01-20
	public boolean GetEntityTable(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		if (Html_entity_ == null) Html_entity_ = Scrib_lib_text_html_entities.new_();
		return rslt.Init_obj(Html_entity_);
	}	private static KeyVal[] Html_entity_;
	public boolean JsonEncode(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		throw Err_.new_unimplemented();
//			KeyVal[] kv_ary = args.Pull_kv_ary(0);
//			int flags = args.Cast_int_or(1, 0);
//			if (!(flags & Scrib_lib_text__json_util.Flag__preserve_keys)) {	// && is_array( $value )
//				text = Scrib_lib_text__json_util.Reindex_arrays(text, true);
//			}
//			byte[] rv = Scrib_lib_text__json_util.Encode(kv_ary, flags && Scrib_lib_text__json_util.Flag__pretty, Scrib_lib_text__json_util.Skip__all);
//			if (rv == null) throw Err_.new_("scribunto",  "mw.text.jsonEncode: Unable to encode value");
//			return rslt.Init_obj(rv);
	}
	public boolean JsonDecode(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		throw Err_.new_unimplemented();
//			return false;
	}
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
		return String_.new_u8(msg_mgr.Val_by_key_obj(Bry_.new_u8(msg_key)));
	}
}
class Scrib_lib_text__json_util {
	public static KeyVal[] Reindex_arrays(KeyVal[] kv_ary, boolean is_encoding) {
		return kv_ary;
	}
	public static final int
	  Flag__preserve_keys	= 1
	, Flag__try_fixing		= 2
	, Flag__pretty			= 4
	;
	public static final int
	  Skip__utf8	= 1
	, Skip__xml		= 2
	, Skip__all		= 3
	;
}
