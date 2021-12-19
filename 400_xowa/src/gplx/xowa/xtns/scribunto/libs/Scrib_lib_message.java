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
import gplx.langs.html.HtmlEntityCodes;
import gplx.langs.htmls.Gfh_tag_;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.wrappers.ByteVal;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.apps.gfs.Gfs_php_converter;
import gplx.xowa.langs.Xol_lang_itm;
import gplx.xowa.langs.msgs.Xol_msg_itm;
import gplx.xowa.langs.msgs.Xol_msg_itm_;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.xtns.scribunto.Scrib_core;
import gplx.xowa.xtns.scribunto.Scrib_lib;
import gplx.xowa.xtns.scribunto.Scrib_lua_mod;
import gplx.xowa.xtns.scribunto.Scrib_lua_proc;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_args;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_mgr;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_rslt;
public class Scrib_lib_message implements Scrib_lib {
	public Scrib_lib_message(Scrib_core core) {this.core = core;} private Scrib_core core;
	public String Key() {return "mw.message";}
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
			default: throw ErrUtl.NewUnhandled(key);
		}
	}
	private static final int Proc_plain = 0, Proc_check = 1, Proc_init_message_for_lang = 2;
	public static final String Invk_plain = "plain", Invk_check = "check", Invk_init_message_for_lang = "init_message_for_lang";
	private static final String[] Proc_names = StringUtl.Ary(Invk_plain, Invk_check, Invk_init_message_for_lang);
	public void Notify_lang_changed() {if (notify_lang_changed_fnc != null) core.Interpreter().CallFunction(notify_lang_changed_fnc.Id(), KeyValUtl.AryEmpty);}
	public boolean Plain(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte fmt_tid = Scrib_lib_message_data.Fmt_tid_plain;
		KeyVal[] data_kvary = args.Pull_kv_ary_safe(0);
		Scrib_lib_message_data msg_data = new Scrib_lib_message_data().Parse(data_kvary); 
		return rslt.Init_obj(StringUtl.NewU8(msg_data.Make_msg(core.Cur_lang(), core.Wiki(), core.Ctx(), true, fmt_tid)));
	}
	public boolean Check(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte chk_tid = Scrib_lib_message_data.parse_chk_(args.Pull_bry(0));
		KeyVal[] data_kvary = args.Pull_kv_ary_safe(1);
		Scrib_lib_message_data msg_data = new Scrib_lib_message_data().Parse(data_kvary);
		return rslt.Init_obj(msg_data.Chk_msg(core.Cur_lang(), core.Wiki(), core.Ctx(), false, chk_tid));
	}
	public boolean Init_message_for_lang(Scrib_proc_args args, Scrib_proc_rslt rslt) {return rslt.Init_obj(KeyVal.NewStr("lang", core.Wiki().Lang().Key_str()));}
}
class Scrib_lib_message_data {
	public boolean Use_db() {return use_db;} private boolean use_db;
	public byte[] Lang_key() {return lang_key;} private byte[] lang_key = BryUtl.Empty;
	public byte[] Title_bry() {return title_bry;} private byte[] title_bry;
	public byte[] Msg_key() {return msg_key;} private byte[] msg_key;
	public byte[] Raw_msg_key() {return raw_msg_key;} private byte[] raw_msg_key;
	public Object[] Args() {return args;} Object[] args;
	public Xoa_ttl Ttl() {return ttl;} public Scrib_lib_message_data Ttl_(Xoa_ttl v) {ttl = v; return this;}  Xoa_ttl ttl;
	public Scrib_lib_message_data Parse(KeyVal[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			KeyVal kv = ary[i];
			byte[] kv_key = BryUtl.NewA7(kv.KeyToStr());
			Object key_obj = key_hash.GetByOrNull(kv_key); if (key_obj == null) throw ErrUtl.NewArgs("msg_key is invalid", "key", kv_key);
			byte key_tid = ((ByteVal)key_obj).Val();
			switch (key_tid) {
				case Key_tid_keys:
					KeyVal[] keys_ary = (KeyVal[])kv.Val();
					msg_key = keys_ary[0].ValToBry();
					break; 
				case Key_tid_rawMessage:	raw_msg_key = kv.ValToBry(); break;
				case Key_tid_lang:			lang_key = kv.ValToBry(); break;
				case Key_tid_useDB:			use_db = BoolUtl.Cast(kv.Val()); break;
				case Key_tid_title:			title_bry = kv.ValToBry(); break;
				case Key_tid_params:
					KeyVal[] args_ary = (KeyVal[])kv.Val();
					int args_ary_len = args_ary.length;
					args = new String[args_ary_len];
					for (int j = 0; j < args_ary_len; j++)
						args[j] = args_ary[j].ValToStrOrEmpty();
					break; 
				default:					throw ErrUtl.NewUnhandled(key_tid);
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
			Xol_msg_itm raw_msg_itm = new Xol_msg_itm(-1, BryUtl.Empty);
			BryWtr tmp_bfr = BryWtr.New(); // wiki.Utl__bfr_mkr().Get_b512();
			byte[] raw_msg_val = Gfs_php_converter.To_gfs(tmp_bfr, raw_msg_key);
			Xol_msg_itm_.update_val_(raw_msg_itm, raw_msg_val);
			byte[] raw_msg_rv = wiki.Msg_mgr().Val_by_itm(tmp_bfr, raw_msg_itm, args);
			tmp_bfr.MkrRls();
			return raw_msg_rv;
		}
		if (msg_key == null) return BryUtl.Empty;
		
		if (BryLni.Eq(lang_key, cur_lang) || BryUtl.IsNullOrEmpty(lang_key))	// if lang_key == core_lang then use wiki.msg_mgr; also same if lang_key == blank (arg not given)
			return wiki.Msg_mgr().Val_by_key_args(msg_key, args);
		else {
			Xol_lang_itm lang = wiki.Appe().Lang_mgr().Get_by_or_load(lang_key);
			Xol_msg_itm msg_itm = lang.Msg_mgr().Itm_by_key_or_null(msg_key); if (msg_itm == null) return BryUtl.Empty;
			return msg_itm.Val();
		}
	}
	public boolean Chk_msg(byte[] cur_lang, Xowe_wiki wiki, Xop_ctx ctx, boolean exec_params, byte chk_tid) {
		byte[] msg_val = Fetch_msg(cur_lang, wiki, ctx, false);
		switch (chk_tid) {
			case Check_tid_exists		: return BryUtl.IsNotNullOrEmpty(msg_val);
			case Check_tid_isBlank		: return BryUtl.IsNullOrEmpty(msg_val);	// REF.MW: $message === false || $message === ''
			case Check_tid_isDisabled	: return BryUtl.IsNullOrEmpty(msg_val) || msg_val.length == 1 && msg_val[0] == AsciiByte.Dash;	// REF.MW: $message === false || $message === '' || $message === '-'
			default						: throw ErrUtl.NewUnhandled(chk_tid);
		}
	}
	public byte[] Make_msg(byte[] cur_lang, Xowe_wiki wiki, Xop_ctx ctx, boolean exec_params, byte fmt_tid) {
		byte[] msg_val = Fetch_msg(cur_lang, wiki, ctx, exec_params);
		if (	BryUtl.IsNullOrEmpty(msg_val)	// msg_key returned empty/null msg_val; assume not found
			&&	raw_msg_key == null		// ignore if raw_msg; note that raw_msg can generate empty String; EX:raw_msg={{empty}} -> ""; PAGE:it.w:L'Internazionale DATE:2015-02-25
			) {	
			BryWtr bfr = wiki.Utl__bfr_mkr().GetB512();
			bfr.Add(HtmlEntityCodes.LtBry).Add(msg_key).Add(HtmlEntityCodes.GtBry);	// NOTE: Message.php has logic that says: if plain, "< >", else "&lt; &gt;"; for now, always use escaped
			return bfr.ToBryAndRls();
		}
		switch (fmt_tid) {
			case Fmt_tid_parse:
			case Fmt_tid_text:			// NOTE: not sure what this does; seems to be a "lighter" parser
				break;
			case Fmt_tid_parseAsBlock:	// NOTE: MW passes msg_val through parser and strips <p> if tid==parse; XOWA does the opposite, so add <p> if parseAsBlock requested
				BryWtr bfr = wiki.Utl__bfr_mkr().GetB512();
				msg_val = bfr.Add(Gfh_tag_.P_lhs).Add(msg_val).Add(Gfh_tag_.P_rhs).ToBryAndRls();
				break;
			case Fmt_tid_escaped:
				msg_val = gplx.langs.htmls.Gfh_utl.Escape_html_as_bry(msg_val);
				break;
		}
		return msg_val;
	}
	static final byte Key_tid_keys = 1, Key_tid_rawMessage = 2, Key_tid_lang = 3, Key_tid_useDB = 4, Key_tid_title = 5, Key_tid_params = 6;
	private static final Hash_adp_bry key_hash = Hash_adp_bry.ci_a7()
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
		if (o == null) throw ErrUtl.NewArgs(fmt, "key", StringUtl.NewU8(key));
		return ((ByteVal)o).Val();
	}
	public static final byte Fmt_tid_parse = 1, Fmt_tid_text = 2, Fmt_tid_plain = 3, Fmt_tid_escaped = 4, Fmt_tid_parseAsBlock = 5;
	private static final Hash_adp_bry fmt_hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("parse", Fmt_tid_parse)
	.Add_str_byte("text", Fmt_tid_text)
	.Add_str_byte("plain", Fmt_tid_plain)
	.Add_str_byte("escaped", Fmt_tid_escaped)
	.Add_str_byte("parseAsBlock", Fmt_tid_parseAsBlock);
	public static final byte Check_tid_exists = 1, Check_tid_isBlank = 2, Check_tid_isDisabled = 3;
	private static final Hash_adp_bry check_hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("exists", Check_tid_exists)
	.Add_str_byte("isBlank", Check_tid_isBlank)
	.Add_str_byte("isDisabled", Check_tid_isDisabled);
}
