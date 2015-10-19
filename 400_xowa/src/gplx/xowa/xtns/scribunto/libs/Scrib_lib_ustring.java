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
import gplx.langs.regxs.*; import gplx.core.intls.*;
import gplx.xowa.parsers.*;
public class Scrib_lib_ustring implements Scrib_lib {
	private final String_surrogate_utl surrogate_utl = new String_surrogate_utl();
	public Scrib_lib_ustring(Scrib_core core) {this.core = core; gsub_mgr = new Scrib_lib_ustring_gsub_mgr(core, regx_converter);} private Scrib_core core; Scrib_lib_ustring_gsub_mgr gsub_mgr;
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public int String_len_max() {return string_len_max;} public Scrib_lib_ustring String_len_max_(int v) {string_len_max = v; return this;} private int string_len_max = Xoa_page_.Page_len_max;
	public int Pattern_len_max() {return pattern_len_max;} public Scrib_lib_ustring Pattern_len_max_(int v) {pattern_len_max = v; return this;} private int pattern_len_max = 10000;
	private Scrib_regx_converter regx_converter = new Scrib_regx_converter();
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.ustring.lua")
			, KeyVal_.new_("stringLengthLimit", string_len_max)
			, KeyVal_.new_("patternLengthLimit", pattern_len_max)
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
	private static final String[] Proc_names = String_.Ary(Invk_find, Invk_match, Invk_gmatch_init, Invk_gmatch_callback, Invk_gsub);
	public boolean Find(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String text_str		= args.Xstr_str_or_null(0);
		String regx			= args.Pull_str(1);
		int bgn_char_idx	= args.Cast_int_or(2, 1);
		boolean plain			= args.Cast_bool_or_n(3);
		synchronized (surrogate_utl) {
			byte[] text_bry = Bry_.new_u8(text_str); int text_bry_len = text_bry.length;
			bgn_char_idx = Bgn_adjust(text_str, bgn_char_idx);
			int bgn_adj = surrogate_utl.Count_surrogates__char_idx(text_bry, text_bry_len, 0, bgn_char_idx);		// NOTE: convert from lua / php charidx to java regex codepoint; PAGE:zh.w:南北鐵路 (越南) DATE:2014-08-27
			int bgn_codepoint_idx = bgn_char_idx + bgn_adj;
			int bgn_byte_pos = surrogate_utl.Byte_pos();
			if (String_.Len_eq_0(regx))	// regx of "" should return (bgn, bgn - 1) regardless of whether plain is true or false
				return rslt.Init_many_objs(bgn_codepoint_idx + Scrib_lib_ustring.Base1, bgn_codepoint_idx + Scrib_lib_ustring.Base1 - 1);
			if (plain) {
				int pos = String_.FindFwd(text_str, regx, bgn_codepoint_idx);
				boolean found = pos != Bry_.NotFound;
				return found 
					? rslt.Init_many_objs(pos + Scrib_lib_ustring.Base1, pos + Scrib_lib_ustring.Base1 + String_.Len(regx) - Scrib_lib_ustring.End_adj)
					: rslt.Init_ary_empty()
					;
			}
			regx = regx_converter.Parse(Bry_.new_u8(regx), Scrib_regx_converter.Anchor_G);
			Regx_adp regx_adp = Scrib_lib_ustring.RegxAdp_new_(core.Ctx(), regx);
			Regx_match[] regx_rslts = regx_adp.Match_all(text_str, bgn_codepoint_idx);	// NOTE: MW calculates an offset to handle mb strings. however, java's regex always takes offset in chars (not bytes like PHP preg_match); DATE:2014-03-04
			int len = regx_rslts.length;
			if (len == 0) return rslt.Init_ary_empty();
			List_adp tmp_list = List_adp_.new_();
			Regx_match match = regx_rslts[0];					// NOTE: take only 1st result; DATE:2014-08-27
			int match_find_bgn_codepoint = match.Find_bgn();	// NOTE: java regex returns results in codepoint; PAGE:zh.w:南北鐵路 (越南) DATE:2014-08-27
			int match_find_bgn_adj = -surrogate_utl.Count_surrogates__codepoint_idx1(text_bry, text_bry_len, bgn_byte_pos, match_find_bgn_codepoint - bgn_codepoint_idx); // NOTE: convert from java regex codepoint to lua / php char_idx; PAGE:zh.w:南北鐵路 (越南) DATE:2014-08-27
			tmp_list.Add(match_find_bgn_codepoint + match_find_bgn_adj + -bgn_adj + Scrib_lib_ustring.Base1);
			tmp_list.Add(match.Find_end()		  + match_find_bgn_adj + -bgn_adj + Scrib_lib_ustring.Base1 - Scrib_lib_ustring.End_adj);
			//Tfds.Write(match_find_bgn_codepoint + match_find_bgn_adj + -bgn_adj + Scrib_lib_ustring.Base1
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
		String regx = regx_converter.Parse(args.Cast_bry_or_null(1), Scrib_regx_converter.Anchor_G);
		int bgn = args.Cast_int_or(2, 1);
		bgn = Bgn_adjust(text, bgn);
		Regx_adp regx_adp = Scrib_lib_ustring.RegxAdp_new_(core.Ctx(), regx);
		Regx_match[] regx_rslts = regx_adp.Match_all(text, bgn);
		int len = regx_rslts.length;
		if (len == 0) return rslt.Init_null();	// return null if no matches found; EX:w:Mount_Gambier_(volcano); DATE:2014-04-02; confirmed with en.d:民; DATE:2015-01-30
		List_adp tmp_list = List_adp_.new_();
		for (int i = 0; i < len; i++) {
			Regx_match match = regx_rslts[i];
			AddCapturesFromMatch(tmp_list, match, text, regx_converter.Capt_ary(), true);
		}
		return rslt.Init_many_list(tmp_list);
	}
	public boolean Gsub(Scrib_proc_args args, Scrib_proc_rslt rslt) {return gsub_mgr.Exec(args, rslt);}
	public boolean Gmatch_init(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// String text = Scrib_kv_utl_.Val_to_str(values, 0);
		byte[] regx = args.Pull_bry(1);
		String pcre = regx_converter.Parse(regx, Scrib_regx_converter.Anchor_null);
		return rslt.Init_many_objs(pcre, regx_converter.Capt_ary());
	}
	public boolean Gmatch_callback(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String text = args.Xstr_str_or_null(0); // NOTE: UstringLibrary.php!ustringGmatchCallback calls preg_match directly; $s can be any type, and php casts automatically; 
		String regx = args.Pull_str(1);
		KeyVal[] capt = args.Cast_kv_ary_or_null(2);
		int pos = args.Pull_int(3);
		Regx_adp regx_adp = Scrib_lib_ustring.RegxAdp_new_(core.Ctx(), regx);
		Regx_match[] regx_rslts = regx_adp.Match_all(text, pos);
		int len = regx_rslts.length;
		if (len == 0) return rslt.Init_many_objs(pos, KeyVal_.Ary_empty);
		Regx_match match = regx_rslts[0];	// NOTE: take only 1st result
		List_adp tmp_list = List_adp_.new_();
		AddCapturesFromMatch(tmp_list, match, text, capt, true);	// NOTE: was incorrectly set as false; DATE:2014-04-23
		return rslt.Init_many_objs(match.Find_end(), Scrib_kv_utl_.base1_list_(tmp_list));
	}
	private void AddCapturesFromMatch(List_adp tmp_list, Regx_match rslt, String text, KeyVal[] capts, boolean op_is_match) {// NOTE: this matches behavior in UstringLibrary.php!addCapturesFromMatch
		Regx_group[] grps = rslt.Groups();
		int grps_len = grps.length;
		int capts_len = capts == null ? 0 : capts.length;
		if (grps_len > 0) {
			for (int j = 0; j < grps_len; j++) {
				Regx_group grp = grps[j];
				if (	j < capts_len				// bounds check	b/c null can be passed
					&&	Bool_.cast(capts[j].Val())	// check if true; indicates that group is "()" or "anypos" see regex converter; DATE:2014-04-23
					)
					tmp_list.Add(Int_.To_str(grp.Bgn() + Scrib_lib_ustring.Base1));	// return index only for (); NOTE: always return as String; callers expect String, and may do operations like len(result), which will fail if int; DATE:2013-12-20
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
			ctx.App().Usr_dlg().Warn_many("", "", "regx is invalid: regx=~{0} page=~{1}", regx, String_.new_u8(ctx.Cur_page().Ttl().Page_db()));
		}
		return rv;
	}
	private static final int Base1 = 1
	, End_adj = 1;	// lua / php uses "end" as <= not <; EX: "abc" and bgn=0, end= 1; for XOWA, this is "a"; for MW / PHP it is "ab"
}
class Scrib_lib_ustring_gsub_mgr {
	private Scrib_regx_converter regx_converter;
	public Scrib_lib_ustring_gsub_mgr(Scrib_core core, Scrib_regx_converter regx_converter) {this.core = core; this.regx_converter = regx_converter;} private Scrib_core core; 
	private byte tmp_repl_tid = Repl_tid_null; private byte[] tmp_repl_bry = null;
	private Hash_adp repl_hash = null; private Scrib_lua_proc repl_func = null;
	private int repl_count = 0;
	public boolean Exec(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Object text_obj = args.Cast_obj_or_null(0);
		String text = String_.as_(text_obj);
		if (text == null) text = Object_.Xto_str_strict_or_empty(text_obj);
		String regx = args.Xstr_str_or_null(1);				// NOTE: @pattern sometimes int; PAGE:en.d:λύω; DATE:2014-09-02
		if (args.Len() == 2) return rslt.Init_obj(text);	// if no replace arg, return self; PAGE:en.d:'orse; DATE:2013-10-13
		Object repl_obj = args.Cast_obj_or_null(2);
		regx = regx_converter.Parse(Bry_.new_u8(regx), Scrib_regx_converter.Anchor_pow);
		int limit = args.Cast_int_or(3, -1);
		repl_count = 0;
		Identify_repl(repl_obj);
		String repl = Exec_repl(tmp_repl_tid, tmp_repl_bry, text, regx, limit);
		return rslt.Init_many_objs(repl, repl_count);
	}
	private void Identify_repl(Object repl_obj) {
		Class<?> repl_type = repl_obj.getClass();
		if		(Object_.Eq(repl_type, String_.Cls_ref_type)) {
			tmp_repl_tid = Repl_tid_string;
			tmp_repl_bry = Bry_.new_u8((String)repl_obj);
		}
		else if	(Object_.Eq(repl_type, Int_.Cls_ref_type)) {	// NOTE:@replace sometimes int; PAGE:en.d:λύω; DATE:2014-09-02
			tmp_repl_tid = Repl_tid_string;
			tmp_repl_bry = Bry_.new_u8(Int_.To_str(Int_.cast(repl_obj)));
		}
		else if	(Object_.Eq(repl_type, KeyVal[].class)) {
			tmp_repl_tid = Repl_tid_table;
			KeyVal[] repl_tbl = (KeyVal[])repl_obj;
			if (repl_hash == null) 
				repl_hash = Hash_adp_.new_();
			else
				repl_hash.Clear();
			int repl_tbl_len = repl_tbl.length;
			for (int i = 0; i < repl_tbl_len; i++) {
				KeyVal repl_itm = repl_tbl[i];
				String repl_itm_val = repl_itm.Val_to_str_or_empty();
				repl_hash.Add(repl_itm.Key(), Bry_.new_u8(repl_itm_val));
			}
		}
		else if	(Object_.Eq(repl_type, Scrib_lua_proc.class)) {
			tmp_repl_tid = Repl_tid_luacbk;
			repl_func = (Scrib_lua_proc)repl_obj;
		}
		else throw Err_.new_unhandled(Type_adp_.NameOf_type(repl_type));
	}
	private String Exec_repl(byte repl_tid, byte[] repl_bry, String text, String regx, int limit) {
		Regx_adp regx_mgr = Scrib_lib_ustring.RegxAdp_new_(core.Ctx(), regx);
		Regx_match[] rslts = regx_mgr.Match_all(text, 0);
		if (	rslts.length == 0				// PHP: If matches are found, the new subject will be returned, otherwise subject will be returned unchanged.; http://php.net/manual/en/function.preg-replace-callback.php
			||	regx_mgr.Pattern_is_invalid()	// NOTE: invalid patterns should return self; EX:[^]; DATE:2014-09-02
			) return text;	
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		int len = rslts.length;
		int pos = 0;
		for (int i = 0; i < len; i++) {
			if (limit > -1 && repl_count == limit) break;
			Regx_match rslt = rslts[i];
			tmp_bfr.Add_str(String_.Mid(text, pos, rslt.Find_bgn()));	// NOTE: regx returns char pos (not bry); must add as String, not bry; DATE:2013-07-17
			Exec_repl_itm(tmp_bfr, repl_tid, repl_bry, text, rslt);
			pos = rslt.Find_end();
			++repl_count;
		}
		int text_len = String_.Len(text);
		if (pos < text_len)
			tmp_bfr.Add_str(String_.Mid(text, pos, text_len));			// NOTE: regx returns char pos (not bry); must add as String, not bry; DATE:2013-07-17
		return tmp_bfr.To_str_and_clear();
	}
	private void Exec_repl_itm(Bry_bfr tmp_bfr, byte repl_tid, byte[] repl_bry, String text, Regx_match match) {
		switch (repl_tid) {
			case Repl_tid_string:
				int len = repl_bry.length;
				for (int i = 0; i < len; i++) {
					byte b = repl_bry[i];
					switch (b) {
						case Byte_ascii.Percent: {
							++i;
							if (i == len)	// % at end of stream; just add %;
								tmp_bfr.Add_byte(b);							
							else {
								b = repl_bry[i];
								switch (b) {
									case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
									case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
										int idx = b - Byte_ascii.Num_0;
										if (idx == 0)	// NOTE: 0 means take result; REF.MW:if ($x === '0'); return $m[0]; PAGE:Wikipedia:Wikipedia_Signpost/Templates/Voter/testcases; DATE:2015-08-02
											tmp_bfr.Add_str_u8(String_.Mid(text, match.Find_bgn(), match.Find_end()));											
										else {			// NOTE: > 0 means get from groups if it exists; REF.MW:elseif (isset($m["m$x"])) return $m["m$x"]; PAGE:Wikipedia:Wikipedia_Signpost/Templates/Voter/testcases; DATE:2015-08-02
											idx -= List_adp_.Base1;
											if (idx < match.Groups().length) {	// retrieve numbered capture; TODO: support more than 9 captures
												Regx_group grp = match.Groups()[idx];
												tmp_bfr.Add_str(String_.Mid(text, grp.Bgn(), grp.End()));	// NOTE: grp.Bgn() / .End() is for String pos (bry pos will fail for utf8 strings)
											}
											else {
												tmp_bfr.Add_byte(Byte_ascii.Percent);
												tmp_bfr.Add_byte(b);
											}
										}
										break;
									case Byte_ascii.Percent:
										tmp_bfr.Add_byte(Byte_ascii.Percent);
										break;
									default:	// not a number; add literal
										tmp_bfr.Add_byte(Byte_ascii.Percent);
										tmp_bfr.Add_byte(b);	
										break;
								}
							}
							break;
						}
						default:
							tmp_bfr.Add_byte(b);
							break;
					}
				}
				break;
			case Repl_tid_table: {
				int match_bgn = -1, match_end = -1;
				Regx_group[] grps = match.Groups();
				if (grps.length == 0) {
					match_bgn = match.Find_bgn();
					match_end = match.Find_end();
				}
				else {	// group exists, take first one (logic matches Scribunto); PAGE:en.w:Bannered_routes_of_U.S._Route_60; DATE:2014-08-15
					Regx_group grp = grps[0];
					match_bgn = grp.Bgn();
					match_end = grp.End();
				}
				String find_str = String_.Mid(text, match_bgn, match_end);	// NOTE: rslt.Bgn() / .End() is for String pos (bry pos will fail for utf8 strings)
				Object actl_repl_obj = repl_hash.Get_by(find_str);
				if (actl_repl_obj == null)			// match found, but no replacement specified; EX:"abc", "[ab]", "a:A"; "b" in regex but not in tbl; EX:d:DVD; DATE:2014-03-31
					tmp_bfr.Add_str(find_str);
				else
					tmp_bfr.Add((byte[])actl_repl_obj);					
				break;
			}
			case Repl_tid_luacbk: {
				KeyVal[] luacbk_args = null;
				Regx_group[] grps = match.Groups();
				int grps_len = grps.length;
				if (grps_len == 0) {	// no match; use original String
					String find_str = String_.Mid(text, match.Find_bgn(), match.Find_end());
					luacbk_args = Scrib_kv_utl_.base1_obj_(find_str);
				}
				else {					// match; build ary of matches; (see UStringLibrary.php)
					luacbk_args = new KeyVal[grps_len];
					for (int i = 0; i < grps_len; i++) {
						Regx_group grp = grps[i];
						String find_str = String_.Mid(text, grp.Bgn(), grp.End());
						luacbk_args[i] = KeyVal_.int_(i + Scrib_core.Base_1, find_str);
					}
				}
				KeyVal[] rslts = core.Interpreter().CallFunction(repl_func.Id(), luacbk_args);
				tmp_bfr.Add_str(Scrib_kv_utl_.Val_to_str(rslts, 0));
				break;
			}
			default: throw Err_.new_unhandled(repl_tid);
		}
	}
	static final byte Repl_tid_null = 0, Repl_tid_string = 1, Repl_tid_table = 2, Repl_tid_luacbk = 3;
}
