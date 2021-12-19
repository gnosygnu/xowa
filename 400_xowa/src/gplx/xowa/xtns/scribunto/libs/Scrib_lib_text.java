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
package gplx.xowa.xtns.scribunto.libs;
import gplx.core.bits.Bitmask_;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.TypeIds;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.langs.msgs.Xow_msg_mgr;
import gplx.xowa.xtns.scribunto.Scrib_core;
import gplx.xowa.xtns.scribunto.Scrib_lib;
import gplx.xowa.xtns.scribunto.Scrib_lua_mod;
import gplx.xowa.xtns.scribunto.Scrib_lua_proc;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_args;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_mgr;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_rslt;
public class Scrib_lib_text implements Scrib_lib {
	private final Scrib_lib_text__json_util json_util = new Scrib_lib_text__json_util();
	private final Scrib_lib_text__nowiki_util nowiki_util = new Scrib_lib_text__nowiki_util();
	private final Scrib_core core;
	private final Btrie_slim_mgr trie;
	public Scrib_lib_text(Scrib_core core) {
		this.core = core;
		this.trie = nowiki_util.Make_trie(gplx.xowa.parsers.xndes.Xop_xnde_tag_.Tag__nowiki.Name_bry());
	}
	public String Key() {return "mw.text";}
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Scrib_lib_text(core);}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.text.lua"));
		notify_wiki_changed_fnc = mod.Fncs_get_by_key("notify_wiki_changed");
		return mod;
	}	private Scrib_lua_proc notify_wiki_changed_fnc;
	public Scrib_proc_mgr Procs() {return procs;} private final Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_unstrip:							return Unstrip(args, rslt);
			case Proc_unstripNoWiki:					return UnstripNoWiki(args, rslt);
			case Proc_killMarkers:						return KillMarkers(args, rslt);
			case Proc_getEntityTable:					return GetEntityTable(args, rslt);
			case Proc_init_text_for_wiki:				return Init_text_for_wiki(args, rslt);
			case Proc_jsonEncode:						return JsonEncode(args, rslt);
			case Proc_jsonDecode:						return JsonDecode(args, rslt);
			default: throw ErrUtl.NewUnhandled(key);
		}
	}
	private static final int Proc_unstrip = 0, Proc_unstripNoWiki = 1, Proc_killMarkers = 2, Proc_getEntityTable = 3, Proc_init_text_for_wiki = 4, Proc_jsonEncode = 5, Proc_jsonDecode = 6;
	public static final String Invk_unstrip = "unstrip", Invk_unstripNoWiki = "unstripNoWiki", Invk_killMarkers = "killMarkers", Invk_getEntityTable = "getEntityTable"
	, Invk_init_text_for_wiki = "init_text_for_wiki", Invk_jsonEncode = "jsonEncode", Invk_jsonDecode = "jsonDecode";
	private static final String[] Proc_names = StringUtl.Ary(Invk_unstrip, Invk_unstripNoWiki, Invk_killMarkers, Invk_getEntityTable, Invk_init_text_for_wiki, Invk_jsonEncode, Invk_jsonDecode);
	public boolean Unstrip(Scrib_proc_args args, Scrib_proc_rslt rslt)			{return rslt.Init_obj(args.Pull_str(0));}	// NOTE: XOWA does not use MediaWiki strip markers; just return original; DATE:2015-01-20
	public boolean UnstripNoWiki(Scrib_proc_args args, Scrib_proc_rslt rslt)	{
		// NOTE: XOWA does not use MediaWiki strip markers; just return original; DATE:2015-01-20
		byte[] src = args.Pull_bry(0);
		return rslt.Init_obj(nowiki_util.Strip_tag(core.Page().Url_bry_safe(), src, trie));
	}
	public boolean KillMarkers(Scrib_proc_args args, Scrib_proc_rslt rslt)		{return rslt.Init_obj(args.Pull_str(0));}	// NOTE: XOWA does not use MediaWiki strip markers; just return original; DATE:2015-01-20
	public boolean GetEntityTable(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		if (html_entities == null) html_entities = Scrib_lib_text_html_entities.new_();
		return rslt.Init_obj(html_entities);
	}	private static KeyVal[] html_entities;
	public boolean JsonEncode(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Object itm = args.Pull_obj(0);

		// check if json is primitive, and return that; see NOTE below; PAGE:en.w:Template:Format_TemplateData ISSUE#:301; DATE:2019-01-13
		int itm_type_id = TypeIds.ToIdByObj(itm);
		switch (itm_type_id) {
			case TypeIds.IdBool:
			case TypeIds.IdByte:
			case TypeIds.IdShort:
			case TypeIds.IdInt:
			case TypeIds.IdLong:
			case TypeIds.IdFloat:
			case TypeIds.IdDouble:
			case TypeIds.IdChar:
			case TypeIds.IdStr:
			case TypeIds.IdBry:
			case TypeIds.IdDate:
			case TypeIds.IdDecimal:
				return rslt.Init_obj(itm);
		}

		// try to determine if node or array; EX: {a:1, b:2} vs [a:1, b:2]
		KeyVal[] itm_as_nde = null;
		Object itm_as_ary = null;	
		Class<?> itm_type = itm.getClass();
		boolean itm_is_nde = ClassUtl.Eq(itm_type, KeyVal[].class);

		// additional logic to classify "[]" as ary, not nde; note that this is done by checking len of itm_as_nde
		if (itm_is_nde) {
			itm_as_nde = (KeyVal[])itm;
			int itm_as_nde_len = itm_as_nde.length;
			if (itm_as_nde_len == 0) {	// Keyval[0] could be either "[]" or "{}"; for now, classify as "[]" per TextLibraryTests.lua; 'json encode, empty table (could be either [] or {}, but change should be announced)'; DATE:2016-08-01
				itm_as_nde = null;
				itm_is_nde = false;
			}					
		}
		if	(!itm_is_nde)
			itm_as_ary = ArrayUtl.Cast(itm);

		// reindex ndes unless preserve_keys
		int flags = args.Cast_int_or(1, 0);
		if (	itm_is_nde
			&&	!Bitmask_.Has_int(flags, Scrib_lib_text__json_util.Flag__preserve_keys)
			) {
			Scrib_lib_text__reindex_data reindex_data = new Scrib_lib_text__reindex_data();
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
		if (rv == null) throw ErrUtl.NewArgs("mw.text.jsonEncode: Unable to encode value");
		return rslt.Init_obj(rv);
	}
	public boolean JsonDecode(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// init
		byte[] json = args.Pull_bry(0);

		// check if json is primitive, and return that; see NOTE below; PAGE:en.w:Template:Format_TemplateData ISSUE#:301; DATE:2019-01-13
		int json_len = json.length;
		boolean is_json_like = false;
		boolean is_numeric = true;
		for (int i = 0; i < json_len; i++) {
			byte json_byte = json[i];
			switch (json_byte) {
				case AsciiByte.BrackBgn:
				case AsciiByte.BrackEnd:
				case AsciiByte.CurlyBgn:
				case AsciiByte.CurlyEnd:
					is_json_like = true;
					is_numeric = false;
					i = json_len;
					break;
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					break;
				default:
					is_numeric = false;
					break;
			}
		}
		if (!is_json_like) {
			if (is_numeric) {
				return rslt.Init_obj(BryUtl.ToInt(json));
			}
			else {
				if (BryLni.Eq(json, BoolUtl.TrueBry))
					return rslt.Init_obj(true);
				else if (BryLni.Eq(json, BoolUtl.FalseBry))
					return rslt.Init_obj(false);
				else {
					return rslt.Init_obj(json);
				}
			}
		}			

		int flags = args.Cast_int_or(1, 0);
		int opts = Scrib_lib_text__json_util.Opt__force_assoc;
		if (Bitmask_.Has_int(flags, Scrib_lib_text__json_util.Flag__try_fixing))
			opts = Bitmask_.Add_int(opts, Scrib_lib_text__json_util.Flag__try_fixing);

		KeyVal[] rv = JsonDecodeStatic(args, core, json_util, json, opts, flags);
		return rslt.Init_obj(rv);
	}
	public static KeyVal[] JsonDecodeStatic
		( Scrib_proc_args args, Scrib_core core, Scrib_lib_text__json_util json_util
		, byte[] json, int opts, int flags) {
		// decode json to Object; note that BoolUtl.Y means ary and BoolUtl.N means ary
		byte rv_tid = json_util.Decode(core.App().Utl__json_parser(), json, opts);
		if (rv_tid == BoolUtl.NullByte) throw ErrUtl.NewArgs("mw.text.jsonEncode: Unable to decode String " + StringUtl.NewU8(json));
		if (rv_tid == BoolUtl.YByte) {
			KeyVal[] rv_as_kvy = (KeyVal[])json_util.Decode_rslt_as_nde();

			// reindex unless preserve_keys passed
			if (!(Bitmask_.Has_int(flags, Scrib_lib_text__json_util.Flag__preserve_keys))) {
				Scrib_lib_text__reindex_data reindex_data = new Scrib_lib_text__reindex_data();
				json_util.Reindex_arrays(reindex_data, rv_as_kvy, false);
				rv_as_kvy = reindex_data.Rv_is_kvy() ? (KeyVal[])reindex_data.Rv_as_kvy() : (KeyVal[])reindex_data.Rv_as_ary();
			}					
			return rv_as_kvy;
		}
		else
			return json_util.Decode_rslt_as_ary();
	}

	public void Notify_wiki_changed() {if (notify_wiki_changed_fnc != null) core.Interpreter().CallFunction(notify_wiki_changed_fnc.Id(), KeyValUtl.AryEmpty);}
	public boolean Init_text_for_wiki(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Xow_msg_mgr msg_mgr = core.Wiki().Msg_mgr();
		KeyVal[] rv = new KeyVal[4];
		rv[0] = KeyVal.NewStr("comma", Init_lib_text_get_msg(msg_mgr, "comma-separator"));
		rv[1] = KeyVal.NewStr("and", Init_lib_text_get_msg(msg_mgr, "and") + Init_lib_text_get_msg(msg_mgr, "word-separator"));
		rv[2] = KeyVal.NewStr("ellipsis", Init_lib_text_get_msg(msg_mgr, "ellipsis"));
		rv[3] = KeyVal.NewStr("nowiki_protocols", KeyValUtl.AryEmpty);	// NOTE: code implemented, but waiting for it to be used; DATE:2014-03-20
		return rslt.Init_obj(rv);
	}
	private String Init_lib_text_get_msg(Xow_msg_mgr msg_mgr, String msg_key) {
		return StringUtl.NewU8(msg_mgr.Val_by_key_obj(BryUtl.NewU8(msg_key)));
	}
}
/*
jsonDecode

NOTE: this code is adhoc; MW calls PHP's jsonDecode
jsonDecode has very liberal rules for decoding which seems to include
* auto-converting bools and ints from strings
* throwing syntax errors if text looks like JSON but is not

This code emulates some of the above rules

REF: http://php.net/manual/en/function.json-decode.php
REF: https://doc.wikimedia.org/mediawiki-core/master/php/FormatJson_8php_source.html
*/
