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
import gplx.core.intls.*; import gplx.langs.regxs.*;
import gplx.xowa.parsers.*;
import gplx.xowa.xtns.scribunto.procs.*;
public class Scrib_lib_ustring implements Scrib_lib {
	private final    String_surrogate_utl surrogate_utl = new String_surrogate_utl();
	public Scrib_lib_ustring(Scrib_core core) {this.core = core;} private Scrib_core core;
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public int String_len_max() {return string_len_max;} public Scrib_lib_ustring String_len_max_(int v) {string_len_max = v; return this;} private int string_len_max = Xoa_page_.Page_len_max;
	public int Pattern_len_max() {return pattern_len_max;} public Scrib_lib_ustring Pattern_len_max_(int v) {pattern_len_max = v; return this;} private int pattern_len_max = 10000;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Scrib_lib_ustring(core);}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.ustring.lua")
			, Keyval_.new_("stringLengthLimit", string_len_max)
			, Keyval_.new_("patternLengthLimit", pattern_len_max)
			);
		return mod;
	}
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_find:									return Find(args, rslt);
			case Proc_match:								return Match(args, rslt);
			case Proc_gmatch_init:							return Gmatch_init(args, rslt);
			case Proc_gmatch_callback:						return Gmatch_callback(args, rslt);
			case Proc_gsub:									return Gsub(args, rslt);
			default: throw Err_.new_unhandled(key);
		}
	}
	private static final int Proc_find = 0, Proc_match = 1, Proc_gmatch_init = 2, Proc_gmatch_callback = 3, Proc_gsub = 4;
	public static final String Invk_find = "find", Invk_match = "match", Invk_gmatch_init = "gmatch_init", Invk_gmatch_callback = "gmatch_callback", Invk_gsub = "gsub";
	private static final    String[] Proc_names = String_.Ary(Invk_find, Invk_match, Invk_gmatch_init, Invk_gmatch_callback, Invk_gsub);
	public boolean Find(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String text_str		= args.Xstr_str_or_null(0);
		String regx			= args.Pull_str(1);
		int bgn_char_idx	= args.Cast_int_or(2, 1);
		boolean plain			= args.Cast_bool_or_n(3);
		synchronized (surrogate_utl) {
			byte[] text_bry = Bry_.new_u8(text_str); int text_bry_len = text_bry.length;
			bgn_char_idx = Bgn_adjust(text_str, bgn_char_idx);

			// regx of "" should return (bgn, bgn - 1) regardless of whether plain is true or false;
			// NOTE: do not include surrogate calc; PAGE:en.d:佻 DATE:2017-04-24
			if (String_.Len_eq_0(regx))	// regx of "" should return (bgn, bgn - 1) regardless of whether plain is true or false
				return rslt.Init_many_objs(bgn_char_idx + Scrib_lib_ustring.Base1, bgn_char_idx + Scrib_lib_ustring.Base1 - 1);

			// NOTE: adjust for 2-len chars (surrogates); PAGE:en.d:iglesia DATE:2017-04-23
			int bgn_adj = surrogate_utl.Count_surrogates__char_idx(text_bry, text_bry_len, 0, bgn_char_idx);		// NOTE: convert from lua / php charidx to java regex codepoint; PAGE:zh.w:南北鐵路 (越南) DATE:2014-08-27
			int bgn_codepoint_idx = bgn_char_idx + bgn_adj;
			int bgn_byte_pos = surrogate_utl.Byte_pos();
			if (plain) {
				int pos = String_.FindFwd(text_str, regx, bgn_codepoint_idx);
				boolean found = pos != Bry_find_.Not_found;
				return found 
					? rslt.Init_many_objs(pos + Scrib_lib_ustring.Base1, pos + Scrib_lib_ustring.Base1 + String_.Len(regx) - Scrib_lib_ustring.End_adj)
					: rslt.Init_ary_empty()
					;
			}
			Scrib_regx_converter regx_converter = new Scrib_regx_converter();
			regx = regx_converter.patternToRegex(Bry_.new_u8(regx), Scrib_regx_converter.Anchor_G);
			Regx_adp regx_adp = Scrib_lib_ustring.RegxAdp_new_(core.Ctx(), regx);
			Regx_match[] regx_rslts = regx_adp.Match_all(text_str, bgn_codepoint_idx);	// NOTE: MW calculates an offset to handle mb strings. however, java's regex always takes offset in chars (not bytes like PHP preg_match); DATE:2014-03-04
			int len = regx_rslts.length;
			if (len == 0) return rslt.Init_ary_empty();
			List_adp tmp_list = List_adp_.New();
			Regx_match match = regx_rslts[0];					// NOTE: take only 1st result; DATE:2014-08-27
			int match_find_bgn_codepoint = match.Find_bgn();	// NOTE: java regex returns results in codepoint; PAGE:zh.w:南北鐵路 (越南) DATE:2014-08-27
			int match_find_bgn_adj = -surrogate_utl.Count_surrogates__codepoint_idx1(text_bry, text_bry_len, bgn_byte_pos, match_find_bgn_codepoint - bgn_codepoint_idx); // NOTE: convert from java regex codepoint to lua / php char_idx; PAGE:zh.w:南北鐵路 (越南) DATE:2014-08-27
			tmp_list.Add(match_find_bgn_codepoint + match_find_bgn_adj + -bgn_adj + Scrib_lib_ustring.Base1);
			tmp_list.Add(match.Find_end()		  + match_find_bgn_adj + -bgn_adj + Scrib_lib_ustring.Base1 - Scrib_lib_ustring.End_adj);
			//Tfds.Dbg  (match_find_bgn_codepoint + match_find_bgn_adj + -bgn_adj + Scrib_lib_ustring.Base1
			//			,match.Find_end()		  + match_find_bgn_adj + -bgn_adj + Scrib_lib_ustring.Base1 - Scrib_lib_ustring.End_adj);
			AddCapturesFromMatch(tmp_list, match, text_str, regx_converter.Capt_ary(), false);
			return rslt.Init_many_list(tmp_list);
		}
	}
	private int Bgn_adjust(String text, int bgn) {	// adjust to handle bgn < 0 or bgn > len (which PHP allows)			
		if (bgn > 0) bgn -= Scrib_lib_ustring.Base1;
		int text_len = String_.Len(text);
		if		(bgn < 0)			// negative number means search from rear of String
			bgn += text_len;		// NOTE: PHP has extra + 1 for Base 1
		else if (bgn > text_len)	// bgn > text_len; confine to text_len; NOTE: PHP has extra + 1 for Base 1
			bgn = text_len;			// NOTE: PHP has extra + 1 for Base 1
		return bgn;
	}
	public boolean Match(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String text = args.Xstr_str_or_null(0);		// Module can pass raw ints; PAGE:en.w:Budget_of_the_European_Union; DATE:2015-01-22
		if (text == null) return rslt.Init_many_list(List_adp_.Noop); // if no text is passed, do not fail; return empty; EX:d:changed; DATE:2014-02-06 
		Scrib_regx_converter regx_converter = new Scrib_regx_converter();
		String regx = regx_converter.patternToRegex(args.Cast_bry_or_null(1), Scrib_regx_converter.Anchor_G);
		int bgn = args.Cast_int_or(2, 1);
		bgn = Bgn_adjust(text, bgn);
		Regx_adp regx_adp = Scrib_lib_ustring.RegxAdp_new_(core.Ctx(), regx);
		Regx_match[] regx_rslts = regx_adp.Match_all(text, bgn);
		int len = regx_rslts.length;
		if (len == 0) return rslt.Init_null();	// return null if no matches found; EX:w:Mount_Gambier_(volcano); DATE:2014-04-02; confirmed with en.d:民; DATE:2015-01-30

		// TOMBSTONE: add 1st match only; do not add all; PAGE:en.d:действительное_причастие_настоящего_времени DATE:2017-04-23
		regx_rslts = regx_converter.Adjust_balanced(regx_rslts);
		List_adp tmp_list = List_adp_.New();
		AddCapturesFromMatch(tmp_list, regx_rslts[0], text, regx_converter.Capt_ary(), true);
		return rslt.Init_many_list(tmp_list);
	}
	public boolean Gsub(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Scrib_lib_ustring_gsub_mgr gsub_mgr = new Scrib_lib_ustring_gsub_mgr(core, new Scrib_regx_converter());
		return gsub_mgr.Exec(args, rslt);
	}
	public boolean Gmatch_init(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// String text = Scrib_kv_utl_.Val_to_str(values, 0);
		byte[] regx = args.Pull_bry(1);
		Scrib_regx_converter regx_converter = new Scrib_regx_converter();
		String pcre = regx_converter.patternToRegex(regx, Scrib_regx_converter.Anchor_null);
		return rslt.Init_many_objs(pcre, regx_converter.Capt_ary());
	}
	public boolean Gmatch_callback(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String text = args.Xstr_str_or_null(0); // NOTE: UstringLibrary.php!ustringGmatchCallback calls preg_match directly; $s can be any type, and php casts automatically; 
		String regx = args.Pull_str(1);
		Keyval[] capt = args.Cast_kv_ary_or_null(2);
		int pos = args.Pull_int(3);
		Regx_adp regx_adp = Scrib_lib_ustring.RegxAdp_new_(core.Ctx(), regx);
		Regx_match[] regx_rslts = regx_adp.Match_all(text, pos);
		int len = regx_rslts.length;
		if (len == 0) return rslt.Init_many_objs(pos, Keyval_.Ary_empty);
		Regx_match match = regx_rslts[0];	// NOTE: take only 1st result
		List_adp tmp_list = List_adp_.New();
		AddCapturesFromMatch(tmp_list, match, text, capt, true);	// NOTE: was incorrectly set as false; DATE:2014-04-23
		return rslt.Init_many_objs(match.Find_end(), Scrib_kv_utl_.base1_list_(tmp_list));
	}
	private void AddCapturesFromMatch(List_adp tmp_list, Regx_match rslt, String text, Keyval[] capts, boolean op_is_match) {// NOTE: this matches behavior in UstringLibrary.php!addCapturesFromMatch
		int capts_len = capts == null ? 0 : capts.length;
		if (capts_len > 0) { // NOTE: changed from "grps_len > 0"; PAGE:en.w:Portal:Constructed_languages/Intro DATE:2018-07-02
			Regx_group[] grps = rslt.Groups();
			int grps_len = grps.length;
			for (int j = 0; j < grps_len; j++) {
				Regx_group grp = grps[j];
				if (	j < capts_len				// bounds check	b/c null can be passed
					&&	Bool_.Cast(capts[j].Val())	// check if true; indicates that group is "()" or "anypos" see regex converter; DATE:2014-04-23
					)
					tmp_list.Add(grp.Bgn() + Scrib_lib_ustring.Base1);	// return index only for "()"; NOTE: do not return as String; callers expect int and will fail typed comparisons; DATE:2016-01-21
				else
					tmp_list.Add(grp.Val());		// return match
			}
		}
		else if (	op_is_match				// if op_is_match, and no captures, extract find_txt; note that UstringLibrary.php says "$arr[] = $m[0][0];" which means get the 1st match;
				&&	tmp_list.Count() == 0)	// only add match once; EX: "aaaa", "a" will have four matches; get 1st; DATE:2014-04-02
			tmp_list.Add(String_.Mid(text, rslt.Find_bgn(), rslt.Find_end()));
	}
	public static Regx_adp RegxAdp_new_(Xop_ctx ctx, String regx) {
		Regx_adp rv = Regx_adp_.new_(regx);
		if (rv.Pattern_is_invalid()) {
			// try to identify [z-a] errors; PAGE:https://en.wiktionary.org/wiki/Module:scripts/data;  DATE:2017-04-23
			Exception exc = rv.Pattern_is_invalid_exception();
			ctx.App().Usr_dlg().Log_many("", "", "regx is invalid: regx=~{0} page=~{1} exc=~{2}", regx, ctx.Page().Ttl().Page_db(), Err_.Message_gplx_log(exc));
		}
		return rv;
	}
	private static final int Base1 = 1
	, End_adj = 1;	// lua / php uses "end" as <= not <; EX: "abc" and bgn=0, end= 1; for XOWA, this is "a"; for MW / PHP it is "ab"
}
