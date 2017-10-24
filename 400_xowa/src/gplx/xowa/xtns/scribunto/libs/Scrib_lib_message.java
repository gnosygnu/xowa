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
import gplx.core.primitives.*; import gplx.langs.htmls.*;
import gplx.xowa.apps.gfs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.parsers.*;
import gplx.xowa.xtns.scribunto.procs.*;
public class Scrib_lib_message implements Scrib_lib {
	public Scrib_lib_message(Scrib_core core) {this.core = core;} private Scrib_core core;
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Scrib_lib_message(core);}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.message.lua"));// NOTE: "lang" not passed in
		notify_lang_changed_fnc = mod.Fncs_get_by_key("notify_lang_changed");
		return mod;
	}	private Scrib_lua_proc notify_lang_changed_fnc;
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_plain:									return Plain(args, rslt);
			case Proc_check:									return Check(args, rslt);
			case Proc_init_message_for_lang:					return Init_message_for_lang(args, rslt);
			default: throw Err_.new_unhandled(key);
		}
	}
	private static final int Proc_plain = 0, Proc_check = 1, Proc_init_message_for_lang = 2;
	public static final String Invk_plain = "plain", Invk_check = "check", Invk_init_message_for_lang = "init_message_for_lang";
	private static final    String[] Proc_names = String_.Ary(Invk_plain, Invk_check, Invk_init_message_for_lang);
	public void Notify_lang_changed() {if (notify_lang_changed_fnc != null) core.Interpreter().CallFunction(notify_lang_changed_fnc.Id(), Keyval_.Ary_empty);}
	public boolean Plain(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte fmt_tid = Scrib_lib_message_data.Fmt_tid_plain;
		Keyval[] data_kvary = args.Pull_kv_ary_safe(0);
		Scrib_lib_message_data msg_data = new Scrib_lib_message_data().Parse(data_kvary); 
		return rslt.Init_obj(String_.new_u8(msg_data.Make_msg(core.Cur_lang(), core.Wiki(), core.Ctx(), true, fmt_tid)));
	}
	public boolean Check(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte chk_tid = Scrib_lib_message_data.parse_chk_(args.Pull_bry(0));
		Keyval[] data_kvary = args.Pull_kv_ary_safe(1);
		Scrib_lib_message_data msg_data = new Scrib_lib_message_data().Parse(data_kvary);
		return rslt.Init_obj(msg_data.Chk_msg(core.Cur_lang(), core.Wiki(), core.Ctx(), false, chk_tid));
	}
	public boolean Init_message_for_lang(Scrib_proc_args args, Scrib_proc_rslt rslt) {return rslt.Init_obj(Keyval_.new_("lang", core.Wiki().Lang().Key_str()));}
}
class Scrib_lib_message_data {
	public boolean Use_db() {return use_db;} private boolean use_db;
	public byte[] Lang_key() {return lang_key;} private byte[] lang_key = Bry_.Empty;
	public byte[] Title_bry() {return title_bry;} private byte[] title_bry;
	public byte[] Msg_key() {return msg_key;} private byte[] msg_key;
	public byte[] Raw_msg_key() {return raw_msg_key;} private byte[] raw_msg_key;
	public Object[] Args() {return args;} Object[] args;
	public Xoa_ttl Ttl() {return ttl;} public Scrib_lib_message_data Ttl_(Xoa_ttl v) {ttl = v; return this;}  Xoa_ttl ttl;
	public Scrib_lib_message_data Parse(Keyval[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Keyval kv = ary[i];
			byte[] kv_key = Bry_.new_a7(kv.Key());
			Object key_obj = key_hash.Get_by(kv_key); if (key_obj == null) throw Err_.new_wo_type("msg_key is invalid", "key", kv_key);
			byte key_tid = ((Byte_obj_val)key_obj).Val();
			switch (key_tid) {
				case Key_tid_keys:
					Keyval[] keys_ary = (Keyval[])kv.Val();
					msg_key = keys_ary[0].Val_to_bry();
					break; 
				case Key_tid_rawMessage:	raw_msg_key = kv.Val_to_bry(); break;
				case Key_tid_lang:			lang_key = kv.Val_to_bry(); break;
				case Key_tid_useDB:			use_db = Bool_.Cast(kv.Val()); break;
				case Key_tid_title:			title_bry = kv.Val_to_bry(); break;
				case Key_tid_params:
					Keyval[] args_ary = (Keyval[])kv.Val();
					int args_ary_len = args_ary.length;
					args = new String[args_ary_len];
					for (int j = 0; j < args_ary_len; j++)
						args[j] = args_ary[j].Val_to_str_or_empty();
					break; 
				default:					throw Err_.new_unhandled(key_tid);
			}
		}
		return this;
	}
	public byte[] Fetch_msg(byte[] cur_lang, Xowe_wiki wiki, Xop_ctx ctx, boolean exec_params) {
		if (exec_params) {
			byte[] data_ttl = title_bry;
			if (data_ttl == null)
				ttl = ctx.Page().Ttl();
			else
				ttl = Xoa_ttl.Parse(wiki, data_ttl);
		}
		if (raw_msg_key != null) {
			Xol_msg_itm raw_msg_itm = new Xol_msg_itm(-1, Bry_.Empty);
			Bry_bfr tmp_bfr = Bry_bfr_.New(); // wiki.Utl__bfr_mkr().Get_b512();
			byte[] raw_msg_val = Gfs_php_converter.To_gfs(tmp_bfr, raw_msg_key);
			Xol_msg_itm_.update_val_(raw_msg_itm, raw_msg_val);
			byte[] raw_msg_rv = wiki.Msg_mgr().Val_by_itm(tmp_bfr, raw_msg_itm, args);
			tmp_bfr.Mkr_rls();
			return raw_msg_rv;
		}
		if (msg_key == null) return Bry_.Empty;
		
		if (Bry_.Eq(lang_key, cur_lang) || Bry_.Len_eq_0(lang_key))	// if lang_key == core_lang then use wiki.msg_mgr; also same if lang_key == blank (arg not given)
			return wiki.Msg_mgr().Val_by_key_args(msg_key, args);
		else {
			Xol_lang_itm lang = wiki.Appe().Lang_mgr().Get_by_or_new(lang_key); lang.Init_by_load_assert();
			Xol_msg_itm msg_itm = lang.Msg_mgr().Itm_by_key_or_null(msg_key); if (msg_itm == null) return Bry_.Empty;
			return msg_itm.Val();
		}
	}
	public boolean Chk_msg(byte[] cur_lang, Xowe_wiki wiki, Xop_ctx ctx, boolean exec_params, byte chk_tid) {
		byte[] msg_val = Fetch_msg(cur_lang, wiki, ctx, false);
		switch (chk_tid) {
			case Check_tid_exists		: return Bry_.Len_gt_0(msg_val);
			case Check_tid_isBlank		: return Bry_.Len_eq_0(msg_val);	// REF.MW: $message === false || $message === ''
			case Check_tid_isDisabled	: return Bry_.Len_eq_0(msg_val) || msg_val.length == 1 && msg_val[0] == Byte_ascii.Dash;	// REF.MW: $message === false || $message === '' || $message === '-'
			default						: throw Err_.new_unhandled(chk_tid);
		}
	}
	public byte[] Make_msg(byte[] cur_lang, Xowe_wiki wiki, Xop_ctx ctx, boolean exec_params, byte fmt_tid) {
		byte[] msg_val = Fetch_msg(cur_lang, wiki, ctx, exec_params);
		if (	Bry_.Len_eq_0(msg_val)	// msg_key returned empty/null msg_val; assume not found 
			&&	raw_msg_key == null		// ignore if raw_msg; note that raw_msg can generate empty String; EX:raw_msg={{empty}} -> ""; PAGE:it.w:L'Internazionale DATE:2015-02-25
			) {	
			Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
			bfr.Add(gplx.langs.htmls.entitys.Gfh_entity_.Lt_bry).Add(msg_key).Add(gplx.langs.htmls.entitys.Gfh_entity_.Gt_bry);	// NOTE: Message.php has logic that says: if plain, "< >", else "&lt; &gt;"; for now, always use escaped
			return bfr.To_bry_and_rls();
		}
		switch (fmt_tid) {
			case Fmt_tid_parse:
			case Fmt_tid_text:			// NOTE: not sure what this does; seems to be a "lighter" parser
				break;
			case Fmt_tid_parseAsBlock:	// NOTE: MW passes msg_val through parser and strips <p> if tid==parse; XOWA does the opposite, so add <p> if parseAsBlock requested
				Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
				msg_val = bfr.Add(Gfh_tag_.P_lhs).Add(msg_val).Add(Gfh_tag_.P_rhs).To_bry_and_rls();
				break;
			case Fmt_tid_escaped:
				msg_val = gplx.langs.htmls.Gfh_utl.Escape_html_as_bry(msg_val);
				break;
		}
		return msg_val;
	}
	static final byte Key_tid_keys = 1, Key_tid_rawMessage = 2, Key_tid_lang = 3, Key_tid_useDB = 4, Key_tid_title = 5, Key_tid_params = 6;
	private static final    Hash_adp_bry key_hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("keys", Key_tid_keys)
	.Add_str_byte("rawMessage", Key_tid_rawMessage)
	.Add_str_byte("lang", Key_tid_lang)
	.Add_str_byte("useDB", Key_tid_useDB)
	.Add_str_byte("title", Key_tid_title)
	.Add_str_byte("params", Key_tid_params);
	public static byte parse_fmt_(byte[] key) {return parse_or_fail(fmt_hash, key, "invalid message format: {0}");}
	public static byte parse_chk_(byte[] key) {return parse_or_fail(check_hash, key, "invalid check arg: {0}");}
	public static byte parse_or_fail(Hash_adp_bry hash, byte[] key, String fmt) {
		Object o = hash.Get_by_bry(key);
		if (o == null) throw Err_.new_wo_type(fmt, "key", String_.new_u8(key)).Trace_ignore_add_1_();
		return ((Byte_obj_val)o).Val();
	}
	public static final byte Fmt_tid_parse = 1, Fmt_tid_text = 2, Fmt_tid_plain = 3, Fmt_tid_escaped = 4, Fmt_tid_parseAsBlock = 5;
	private static final    Hash_adp_bry fmt_hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("parse", Fmt_tid_parse)
	.Add_str_byte("text", Fmt_tid_text)
	.Add_str_byte("plain", Fmt_tid_plain)
	.Add_str_byte("escaped", Fmt_tid_escaped)
	.Add_str_byte("parseAsBlock", Fmt_tid_parseAsBlock);
	public static final byte Check_tid_exists = 1, Check_tid_isBlank = 2, Check_tid_isDisabled = 3;
	private static final    Hash_adp_bry check_hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("exists", Check_tid_exists)
	.Add_str_byte("isBlank", Check_tid_isBlank)
	.Add_str_byte("isDisabled", Check_tid_isDisabled);
}
