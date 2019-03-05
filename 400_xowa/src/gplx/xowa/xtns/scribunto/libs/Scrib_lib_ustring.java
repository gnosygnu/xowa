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
		// get args
		String text_str	       = args.Xstr_str_or_null(0);
		String find_str        = args.Pull_str(1);
		int bgn_as_codes_base1 = args.Cast_int_or(2, 1);
		boolean plain             = args.Cast_bool_or_n(3);

		// init text vars
		byte[] text_bry = Bry_.new_u8(text_str);
		int text_bry_len = text_bry.length;
		Utf16_mapper text_map = new Utf16_mapper(text_str, text_bry, text_bry_len); // NOTE: must count codes for supplementaries; PAGE:en.d:iglesia DATE:2017-04-23

		// convert bgn from base_1 to base_0
		int bgn_as_codes = To_java_by_lua(bgn_as_codes_base1, text_map.Len_in_codes());

		/*
		int offset = 0;
		if (bgn_as_codes > 0) { // NOTE: MW.BASE
			// $offset = strlen( mb_substr( $s, 0, $init - 1, 'UTF-8' ) );
		}
		else {
			bgn_as_codes_base1 = 0; // NOTE: MW.BASE1
			offset = 0; // -1?
		}
		*/

		// find_str of "" should return (bgn, bgn - 1) regardless of whether plain is true or false;
		// NOTE: do not include surrogate calc; PAGE:en.d:佻 DATE:2017-04-24
		// NOTE: not in MW; is this needed? DATE:2019-02-24
		if (String_.Len_eq_0(find_str))
			return rslt.Init_many_objs(bgn_as_codes_base1, bgn_as_codes_base1 - 1);

		// if plain, just do literal match of find and exit
		if (plain) {
			// find pos by literal match
			byte[] find_bry = Bry_.new_u8(find_str);
			int pos = Bry_find_.Find_fwd(text_bry, find_bry, text_map.Get_byte_for_code_or_fail(bgn_as_codes));

			// nothing found; return empty
			if (pos == Bry_find_.Not_found)
				return rslt.Init_ary_empty();

			// bgn: convert pos from bytes back to codes; also adjust for base1
			int bgn = text_map.Get_code_for_byte_or_fail(pos) + Base1;

			// end: add find.Len_in_codes and adjust end for PHP/LUA
			Utf16_mapper find_map = new Utf16_mapper(find_str, find_bry, find_bry.length);
			int end = bgn + find_map.Len_in_codes() - End_adj;

			return rslt.Init_many_objs(bgn, end);
		}

		// run regex
		Scrib_regx_converter regx_converter = new Scrib_regx_converter();
		Regx_match[] regx_rslts = Run_regex_or_null(text_map, regx_converter, find_str, bgn_as_codes);
		if (regx_rslts.length == 0) return rslt.Init_ary_empty();

		// add to tmp_list
		Regx_match match = regx_rslts[0]; // NOTE: take only 1st result; DATE:2014-08-27
		List_adp tmp_list = List_adp_.New();
		tmp_list.Add(text_map.Get_code_for_char_or_fail(match.Find_bgn()) + Scrib_lib_ustring.Base1);
		tmp_list.Add(text_map.Get_code_for_char_or_fail(match.Find_end()) + Scrib_lib_ustring.Base1 - Scrib_lib_ustring.End_adj);
		AddCapturesFromMatch(tmp_list, match, text_str, regx_converter.Capt_ary(), false);
		return rslt.Init_many_list(tmp_list);
	}
	public boolean Match(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// get args
		String text_str        = args.Xstr_str_or_null(0); // Module can pass raw ints; PAGE:en.w:Budget_of_the_European_Union; DATE:2015-01-22
		String find_str        = args.Cast_str_or_null(1);
		int bgn_as_codes_base1 = args.Cast_int_or(2, 1);

		// validate / adjust
		if (text_str == null) // if no text_str is passed, do not fail; return empty; EX:d:changed; DATE:2014-02-06 
			return rslt.Init_many_list(List_adp_.Noop);
		byte[] text_bry = Bry_.new_u8(text_str); int text_bry_len = text_bry.length;
		Utf16_mapper text_map = new Utf16_mapper(text_str, text_bry, text_bry_len); // NOTE: must count codes for supplementaries; PAGE:en.d:iglesia DATE:2017-04-23
		int bgn_as_codes = To_java_by_lua(bgn_as_codes_base1, text_map.Len_in_codes());

		// run regex
		Scrib_regx_converter regx_converter = new Scrib_regx_converter();
		Regx_match[] regx_rslts = Run_regex_or_null(text_map, regx_converter, find_str, bgn_as_codes);
		if (regx_rslts.length == 0) return rslt.Init_null();	// return null if no matches found; EX:w:Mount_Gambier_(volcano); DATE:2014-04-02; confirmed with en.d:民; DATE:2015-01-30

		// TOMBSTONE: add 1st match only; do not add all; PAGE:en.d:действительное_причастие_настоящего_времени DATE:2017-04-23
		regx_rslts = regx_converter.Adjust_balanced(regx_rslts);
		List_adp tmp_list = List_adp_.New();
		AddCapturesFromMatch(tmp_list, regx_rslts[0], text_str, regx_converter.Capt_ary(), true);
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
	private int To_java_by_lua(int bgn_as_codes_base1, int len_in_codes) {
		// convert bgn from base_1 to base_0
		int bgn_as_codes = bgn_as_codes_base1;
		if (bgn_as_codes > 0) 
			bgn_as_codes -= Scrib_lib_ustring.Base1;
		// TOMBSTONE: do not adjust negative numbers for base1; fails tests
		// else if (bgn_as_codes < 0) bgn_as_codes += Scrib_lib_ustring.Base1;

		// adjust bgn for negative-numbers and large positive-numbers
		// NOTE: MW uses mb_strlen which returns len of mb chars as 1; REF.PHP: http://php.net/manual/en/function.mb-strlen.php
		// NOTE: MW does additional +1 for PHP.base_1. This is not needed for JAVA; noted below as IGNORE_BASE_1_ADJ
		if      (bgn_as_codes < 0)               // negative number means search from rear of String
			bgn_as_codes += len_in_codes;        // NOTE:IGNORE_BASE_1_ADJ
		else if (bgn_as_codes > len_in_codes)    // bgn_as_codes > text_len; confine to text_len; NOTE:IGNORE_BASE_1_ADJ
			bgn_as_codes = len_in_codes;         // NOTE:IGNORE_BASE_1_ADJ

		// will be negative if Abs(bgn_as_codes) > text.length; ISSUE#:366; DATE:2019-02-23
		if (bgn_as_codes < 0)
			bgn_as_codes = 0;
		return bgn_as_codes;
	}
	private Regx_match[] Run_regex_or_null(Utf16_mapper text_map, Scrib_regx_converter regx_converter, String find_str, int bgn_as_codes) {
		// convert regex from lua to java
		find_str = regx_converter.patternToRegex(Bry_.new_u8(find_str), Scrib_regx_converter.Anchor_G);

		// run regex
		Regx_adp regx_adp = Scrib_lib_ustring.RegxAdp_new_(core.Ctx(), find_str);
		return regx_adp.Match_all(text_map.Src_str(), text_map.Get_char_for_code_or_fail(bgn_as_codes));	// NOTE: MW calculates an offset to handle mb strings. however, java's regex always takes offset in chars (not bytes like PHP preg_match); DATE:2014-03-04
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
	private static final int
	  Base1 = 1
	, End_adj = 1;	// lua / php uses "end" as <= not <; EX: "abc" and bgn=0, end= 1; for XOWA, this is "a"; for MW / PHP it is "ab"
}
