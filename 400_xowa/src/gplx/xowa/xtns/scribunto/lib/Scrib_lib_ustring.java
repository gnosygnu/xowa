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
import gplx.texts.*;
public class Scrib_lib_ustring implements Scrib_lib {
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
			default: throw Err_.unhandled(key);
		}
	}
	private static final int Proc_find = 0, Proc_match = 1, Proc_gmatch_init = 2, Proc_gmatch_callback = 3, Proc_gsub = 4;
	public static final String Invk_find = "find", Invk_match = "match", Invk_gmatch_init = "gmatch_init", Invk_gmatch_callback = "gmatch_callback", Invk_gsub = "gsub";
	private static final String[] Proc_names = String_.Ary(Invk_find, Invk_match, Invk_gmatch_init, Invk_gmatch_callback, Invk_gsub);
	public boolean Find(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String text = args.Pull_str(0);
		String regx = args.Pull_str(1);
		int bgn = args.Cast_int_or(2, 1);
		boolean plain = args.Cast_bool_or_n(3);
		bgn = Bgn_adjust(text, bgn);
		if (String_.Len_eq_0(regx))	// regx of "" should return (bgn, bgn - 1) regardless of plain = t / f
			return rslt.Init_many_objs(bgn + Scrib_lib_ustring.Base1, bgn + Scrib_lib_ustring.Base1 - 1);
		if (plain) {
			int pos = String_.FindFwd(text, regx, bgn);
			boolean found = pos != Bry_.NotFound;
			return found 
				? rslt.Init_many_objs(pos + Scrib_lib_ustring.Base1, pos + Scrib_lib_ustring.Base1 + String_.Len(regx) - Scrib_lib_ustring.End_adj)
				: rslt.Init_ary_empty()
				;
		}
		regx = regx_converter.Parse(Bry_.new_utf8_(regx), Scrib_regx_converter.Anchor_G);
		RegxAdp regx_adp = Scrib_lib_ustring.RegxAdp_new_(core.Ctx(), regx);
		RegxMatch[] regx_rslts = regx_adp.Match_all(text, bgn);	// NOTE: MW calculates an offset to handle mb strings. however, java's regex always takes offset in chars (not bytes like PHP preg_match); DATE:2014-03-04
		int len = regx_rslts.length;
		if (len == 0) return rslt.Init_ary_empty();
		ListAdp tmp_list = ListAdp_.new_();
		for (int i = 0; i < len; i++) {
			RegxMatch match = regx_rslts[i];
			tmp_list.Add(match.Find_bgn() + Scrib_lib_ustring.Base1);
			tmp_list.Add(match.Find_end() + Scrib_lib_ustring.Base1 - Scrib_lib_ustring.End_adj);
			AddCapturesFromMatch(tmp_list, match, text, regx_converter.Capt_ary(), false);
		}
		return rslt.Init_many_list(tmp_list);
	}
	private int Bgn_adjust(String text, int bgn) {	// adjust to handle bgn < 0 or bgn > len (which PHP allows)
//			if (bgn == 0) return 0;
		if (bgn > 0) bgn -= Scrib_lib_ustring.Base1;
		int text_len = String_.Len(text);
		if		(bgn < 0)			// negative number means search from rear of String
			bgn += text_len;		// NOTE: PHP has extra + 1 for Base 1
		else if (bgn > text_len)	// bgn > text_len; confine to text_len; NOTE: PHP has extra + 1 for Base 1
			bgn = text_len;			// NOTE: PHP has extra + 1 for Base 1
		return bgn;
	}
	public boolean Match(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		String text = args.Cast_str_or_null(0); if (text == null) return rslt.Init_many_list(ListAdp_.Null); // if no text is passed, do not fail; return empty; EX:d:changed; DATE:2014-02-06 
		String regx = regx_converter.Parse(args.Cast_bry_or_null(1), Scrib_regx_converter.Anchor_G);
		int bgn = args.Cast_int_or(2, 1);
		bgn = Bgn_adjust(text, bgn);
		RegxAdp regx_adp = Scrib_lib_ustring.RegxAdp_new_(core.Ctx(), regx);
		RegxMatch[] regx_rslts = regx_adp.Match_all(text, bgn);
		int len = regx_rslts.length;
		if (len == 0) return rslt.Init_null();	// return null if no matches found; EX:w:Mount_Gambier_(volcano); DATE:2014-04-02
		ListAdp tmp_list = ListAdp_.new_();
		for (int i = 0; i < len; i++) {
			RegxMatch match = regx_rslts[i];
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
		RegxAdp regx_adp = Scrib_lib_ustring.RegxAdp_new_(core.Ctx(), regx);
		RegxMatch[] regx_rslts = regx_adp.Match_all(text, pos);
		int len = regx_rslts.length;
		if (len == 0) return rslt.Init_many_objs(pos, KeyVal_.Ary_empty);
		RegxMatch match = regx_rslts[0];	// NOTE: take only 1st result
		ListAdp tmp_list = ListAdp_.new_();
		AddCapturesFromMatch(tmp_list, match, text, capt, true);	// NOTE: was incorrectly set as false; DATE:2014-04-23
		return rslt.Init_many_objs(match.Find_end(), Scrib_kv_utl_.base1_list_(tmp_list));
	}
	private void AddCapturesFromMatch(ListAdp tmp_list, RegxMatch rslt, String text, KeyVal[] capts, boolean op_is_match) {// NOTE: this matches behavior in UstringLibrary.php!addCapturesFromMatch
		RegxGroup[] grps = rslt.Groups();
		int grps_len = grps.length;
		int capts_len = capts == null ? 0 : capts.length;
		if (grps_len > 0) {
			for (int j = 0; j < grps_len; j++) {
				RegxGroup grp = grps[j];
				if (	j < capts_len				// bounds check	b/c null can be passed
					&&	Bool_.cast_(capts[j].Val())	// check if true; indicates that group is "()" or "anypos" see regex converter; DATE:2014-04-23
					)
					tmp_list.Add(Int_.XtoStr(grp.Bgn() + Scrib_lib_ustring.Base1));	// return index only for (); NOTE: always return as String; callers expect String, and may do operations like len(result), which will fail if int; DATE:2013-12-20
				else
					tmp_list.Add(grp.Val());		// return match
			}
		}
		else if (	op_is_match				// if op_is_match, and no captures, extract find_txt; note that UstringLibrary.php says "$arr[] = $m[0][0];" which means get the 1st match;
				&&	tmp_list.Count() == 0)	// only add match once; EX: "aaaa", "a" will have four matches; get 1st; DATE:2014-04-02
			tmp_list.Add(String_.Mid(text, rslt.Find_bgn(), rslt.Find_end()));
	}
	public static RegxAdp RegxAdp_new_(Xop_ctx ctx, String regx) {
		RegxAdp rv = RegxAdp_.new_(regx);
		if (rv.Pattern_is_invalid()) {
			ctx.App().Usr_dlg().Warn_many("", "", "regx is invalid: regx=~{0} page=~{1}", regx, String_.new_utf8_(ctx.Cur_page().Ttl().Page_db()));
		}
		return rv;
	}
	static final int Base1 = 1, End_adj = 1;
}
class Scrib_lib_ustring_gsub_mgr {
	private Scrib_regx_converter regx_converter;
	public Scrib_lib_ustring_gsub_mgr(Scrib_core core, Scrib_regx_converter regx_converter) {this.core = core; this.regx_converter = regx_converter;} private Scrib_core core; 
	private byte tmp_repl_tid = Repl_tid_null; private byte[] tmp_repl_bry = null;
	private HashAdp repl_hash = null; private Scrib_lua_proc repl_func = null;
	private int repl_count = 0;
	public boolean Exec(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Object text_obj = args.Cast_obj_or_null(0);
		String text = String_.as_(text_obj);
		if (text == null) text = Object_.Xto_str_strict_or_empty(text_obj);
		String regx = args.Cast_str_or_null(1);
		if (args.Len() == 2) return rslt.Init_obj(text);	// if no replace arg, return self; EX:enwikt:'orse; DATE:2013-10-13
		Object repl_obj = args.Cast_obj_or_null(2);
		regx = regx_converter.Parse(Bry_.new_utf8_(regx), Scrib_regx_converter.Anchor_pow);
		int limit = args.Cast_int_or(3, -1);
		repl_count = 0;
		Identify_repl(repl_obj);
		String repl = Exec_repl(tmp_repl_tid, tmp_repl_bry, text, regx, limit);
		return rslt.Init_many_objs(repl, repl_count);
	}
	private void Identify_repl(Object repl_obj) {
		Class<?> repl_type = repl_obj.getClass();
		if		(Object_.Eq(repl_type, String.class)) {
			tmp_repl_tid = Repl_tid_string;
			tmp_repl_bry = Bry_.new_utf8_((String)repl_obj);
		}
		else if	(Object_.Eq(repl_type, KeyVal[].class)) {
			tmp_repl_tid = Repl_tid_table;
			KeyVal[] repl_tbl = (KeyVal[])repl_obj;
			if (repl_hash == null) 
				repl_hash = HashAdp_.new_();
			else
				repl_hash.Clear();
			int repl_tbl_len = repl_tbl.length;
			for (int i = 0; i < repl_tbl_len; i++) {
				KeyVal repl_itm = repl_tbl[i];
				String repl_itm_val = repl_itm.Val_to_str_or_empty();
				repl_hash.Add(repl_itm.Key(), Bry_.new_utf8_(repl_itm_val));
			}
		}
		else if	(Object_.Eq(repl_type, Scrib_lua_proc.class)) {
			tmp_repl_tid = Repl_tid_luacbk;
			repl_func = (Scrib_lua_proc)repl_obj;
		}
		else throw Err_.unhandled(ClassAdp_.NameOf_type(repl_type));
	}
	private String Exec_repl(byte repl_tid, byte[] repl_bry, String text, String regx, int limit) {
		RegxAdp regx_mgr = Scrib_lib_ustring.RegxAdp_new_(core.Ctx(), regx);
		RegxMatch[] rslts = regx_mgr.Match_all(text, 0);
		if (rslts.length == 0) return text;	// PHP: If matches are found, the new subject will be returned, otherwise subject will be returned unchanged.; http://php.net/manual/en/function.preg-replace-callback.php
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		int len = rslts.length;
		int pos = 0;
		for (int i = 0; i < len; i++) {
			if (limit > -1 && repl_count == limit) break;
			RegxMatch rslt = rslts[i];
			tmp_bfr.Add_str(String_.Mid(text, pos, rslt.Find_bgn()));	// NOTE: regx returns char pos (not bry); must add as String, not bry; DATE:2013-07-17
			Exec_repl_itm(tmp_bfr, repl_tid, repl_bry, text, rslt);
			pos = rslt.Find_end();
			++repl_count;
		}
		int text_len = String_.Len(text);
		if (pos < text_len)
			tmp_bfr.Add_str(String_.Mid(text, pos, text_len));			// NOTE: regx returns char pos (not bry); must add as String, not bry; DATE:2013-07-17
		return tmp_bfr.XtoStrAndClear();
	}
	private void Exec_repl_itm(Bry_bfr tmp_bfr, byte repl_tid, byte[] repl_bry, String text, RegxMatch match) {
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
										int idx = b - Byte_ascii.Num_0 - ListAdp_.Base1;
										if (idx < match.Groups().length) {	// retrieve numbered capture; TODO: support more than 9 captures
											RegxGroup grp = match.Groups()[idx];
											tmp_bfr.Add_str(String_.Mid(text, grp.Bgn(), grp.End()));	// NOTE: grp.Bgn() / .End() is for String pos (bry pos will fail for utf8 strings)
										}
										else {
											tmp_bfr.Add_byte(Byte_ascii.Percent);
											tmp_bfr.Add_byte(b);
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
				RegxGroup[] grps = match.Groups();
				if (grps.length == 0) {
					match_bgn = match.Find_bgn();
					match_end = match.Find_end();
				}
				else {	// group exists, take first one (logic matches Scribunto); PAGE:en.w:Bannered_routes_of_U.S._Route_60; DATE:2014-08-15
					RegxGroup grp = grps[0];
					match_bgn = grp.Bgn();
					match_end = grp.End();
				}
				String find_str = String_.Mid(text, match_bgn, match_end);	// NOTE: rslt.Bgn() / .End() is for String pos (bry pos will fail for utf8 strings)
				Object actl_repl_obj = repl_hash.Fetch(find_str);
				if (actl_repl_obj == null)			// match found, but no replacement specified; EX:"abc", "[ab]", "a:A"; "b" in regex but not in tbl; EX:d:DVD; DATE:2014-03-31
					tmp_bfr.Add_str(find_str);
				else
					tmp_bfr.Add((byte[])actl_repl_obj);					
				break;
			}
			case Repl_tid_luacbk: {
				KeyVal[] luacbk_args = null;
				RegxGroup[] grps = match.Groups();
				int grps_len = grps.length;
				if (grps_len == 0) {	// no match; use original String
					String find_str = String_.Mid(text, match.Find_bgn(), match.Find_end());
					luacbk_args = Scrib_kv_utl_.base1_obj_(find_str);
				}
				else {					// match; build ary of matches; (see UStringLibrary.php)
					luacbk_args = new KeyVal[grps_len];
					for (int i = 0; i < grps_len; i++) {
						RegxGroup grp = grps[i];
						String find_str = String_.Mid(text, grp.Bgn(), grp.End());
						luacbk_args[i] = KeyVal_.int_(i + Scrib_core.Base_1, find_str);
					}
				}
				KeyVal[] rslts = core.Interpreter().CallFunction(repl_func.Id(), luacbk_args);
				tmp_bfr.Add_str(Scrib_kv_utl_.Val_to_str(rslts, 0));
				break;
			}
			default: throw Err_.unhandled(repl_tid);
		}
	}
	static final byte Repl_tid_null = 0, Repl_tid_string = 1, Repl_tid_table = 2, Repl_tid_luacbk = 3;
}
