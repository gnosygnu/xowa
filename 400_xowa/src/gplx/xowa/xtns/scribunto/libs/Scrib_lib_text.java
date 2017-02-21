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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import gplx.core.bits.*;
import gplx.xowa.langs.msgs.*;
import gplx.xowa.xtns.scribunto.procs.*;
public class Scrib_lib_text implements Scrib_lib {
	private final    Scrib_lib_text__json_util json_util = new Scrib_lib_text__json_util();
	private final    Scrib_lib_text__reindex_data reindex_data = new Scrib_lib_text__reindex_data();
	public Scrib_lib_text(Scrib_core core) {this.core = core;} private Scrib_core core;
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Scrib_lib_text(core);}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.text.lua"));
		notify_wiki_changed_fnc = mod.Fncs_get_by_key("notify_wiki_changed");
		return mod;
	}	private Scrib_lua_proc notify_wiki_changed_fnc;
	public Scrib_proc_mgr Procs() {return procs;} private final    Scrib_proc_mgr procs = new Scrib_proc_mgr();
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
	private static final    String[] Proc_names = String_.Ary(Invk_unstrip, Invk_unstripNoWiki, Invk_killMarkers, Invk_getEntityTable, Invk_init_text_for_wiki, Invk_jsonEncode, Invk_jsonDecode);
	public boolean Unstrip(Scrib_proc_args args, Scrib_proc_rslt rslt)			{return rslt.Init_obj(args.Pull_str(0));}	// NOTE: XOWA does not use MediaWiki strip markers; just return original; DATE:2015-01-20
	public boolean UnstripNoWiki(Scrib_proc_args args, Scrib_proc_rslt rslt)	{return rslt.Init_obj(args.Pull_str(0));}	// NOTE: XOWA does not use MediaWiki strip markers; just return original; DATE:2015-01-20
	public boolean KillMarkers(Scrib_proc_args args, Scrib_proc_rslt rslt)		{return rslt.Init_obj(args.Pull_str(0));}	// NOTE: XOWA does not use MediaWiki strip markers; just return original; DATE:2015-01-20
	public boolean GetEntityTable(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		if (html_entities == null) html_entities = Scrib_lib_text_html_entities.new_();
		return rslt.Init_obj(html_entities);
	}	private static Keyval[] html_entities;
	public boolean JsonEncode(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Object itm = args.Pull_obj(0);

		// try to determine if node or array; EX: {a:1, b:2} vs [a:1, b:2]
		Keyval[] itm_as_nde = null;
		Object itm_as_ary = null;	
		Class<?> itm_type = itm.getClass();
		boolean itm_is_nde = Type_adp_.Eq(itm_type, Keyval[].class);

		// additional logic to classify "[]" as ary, not nde; note that this is done by checking len of itm_as_nde
		if (itm_is_nde) {
			itm_as_nde = (Keyval[])itm;
			int itm_as_nde_len = itm_as_nde.length;
			if (itm_as_nde_len == 0) {	// Keyval[0] could be either "[]" or "{}"; for now, classify as "[]" per TextLibraryTests.lua; 'json encode, empty table (could be either [] or {}, but change should be announced)'; DATE:2016-08-01
				itm_as_nde = null;
				itm_is_nde = false;
			}					
		}
		if	(!itm_is_nde)
			itm_as_ary = Array_.cast(itm);

		// reindex ndes unless preserve_keys
		int flags = args.Cast_int_or(1, 0);
		if (	itm_is_nde
			&&	!Bitmask_.Has_int(flags, Scrib_lib_text__json_util.Flag__preserve_keys)
			) {
			json_util.Reindex_arrays(reindex_data, itm_as_nde, true);
			if (reindex_data.Rv_is_kvy()) {
				itm_as_nde = reindex_data.Rv_as_kvy();
				itm_as_ary = null;
			}
			else {
				itm_as_ary = reindex_data.Rv_as_ary();
				itm_as_nde = null;
				itm_is_nde = false;
			}
		}

		// encode and return 
		byte[] rv = itm_is_nde
			? json_util.Encode_as_nde(itm_as_nde, flags & Scrib_lib_text__json_util.Flag__pretty, Scrib_lib_text__json_util.Skip__all)
			: json_util.Encode_as_ary(itm_as_ary, flags & Scrib_lib_text__json_util.Flag__pretty, Scrib_lib_text__json_util.Skip__all)
			;
		if (rv == null) throw Err_.new_("scribunto",  "mw.text.jsonEncode: Unable to encode value");
		return rslt.Init_obj(rv);
	}
	public boolean JsonDecode(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// init
		byte[] json = args.Pull_bry(0);
		int flags = args.Cast_int_or(1, 0);
		int opts = Scrib_lib_text__json_util.Opt__force_assoc;
		if (Bitmask_.Has_int(flags, Scrib_lib_text__json_util.Flag__try_fixing))
			opts = Bitmask_.Add_int(opts, Scrib_lib_text__json_util.Flag__try_fixing);

		// decode json to Object; note that Bool_.Y means ary and Bool_.N means ary
		byte rv_tid = json_util.Decode(core.App().Utl__json_parser(), json, opts);
		if (rv_tid == Bool_.__byte) throw Err_.new_("scribunto",  "mw.text.jsonEncode: Unable to decode String " + String_.new_u8(json));
		if (rv_tid == Bool_.Y_byte) {
			Keyval[] rv_as_kvy = (Keyval[])json_util.Decode_rslt_as_nde();

			// reindex unless preserve_keys passed
			if (!(Bitmask_.Has_int(flags, Scrib_lib_text__json_util.Flag__preserve_keys))) {
				json_util.Reindex_arrays(reindex_data, rv_as_kvy, false);
				rv_as_kvy = reindex_data.Rv_is_kvy() ? (Keyval[])reindex_data.Rv_as_kvy() : (Keyval[])reindex_data.Rv_as_ary();
			}					
			return rslt.Init_obj(rv_as_kvy);
		}
		else
			return rslt.Init_obj(json_util.Decode_rslt_as_ary());
	}
	public void Notify_wiki_changed() {if (notify_wiki_changed_fnc != null) core.Interpreter().CallFunction(notify_wiki_changed_fnc.Id(), Keyval_.Ary_empty);}
	public boolean Init_text_for_wiki(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Xow_msg_mgr msg_mgr = core.Wiki().Msg_mgr();
		Keyval[] rv = new Keyval[4];
		rv[0] = Keyval_.new_("comma", Init_lib_text_get_msg(msg_mgr, "comma-separator"));
		rv[1] = Keyval_.new_("and", Init_lib_text_get_msg(msg_mgr, "and") + Init_lib_text_get_msg(msg_mgr, "word-separator"));
		rv[2] = Keyval_.new_("ellipsis", Init_lib_text_get_msg(msg_mgr, "ellipsis"));
		rv[3] = Keyval_.new_("nowiki_protocols", Keyval_.Ary_empty);	// NOTE: code implemented, but waiting for it to be used; DATE:2014-03-20
		return rslt.Init_obj(rv);
	}
	private String Init_lib_text_get_msg(Xow_msg_mgr msg_mgr, String msg_key) {
		return String_.new_u8(msg_mgr.Val_by_key_obj(Bry_.new_u8(msg_key)));
	}
}
