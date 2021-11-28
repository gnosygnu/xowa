/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.scribunto.libs;

import gplx.Bool_;
import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Byte_ascii;
import gplx.Char_;
import gplx.Double_;
import gplx.Err_;
import gplx.Hash_adp;
import gplx.Hash_adp_;
import gplx.Int_;
import gplx.Keyval;
import gplx.Keyval_;
import gplx.List_adp_;
import gplx.Object_;
import gplx.String_;
import gplx.Type_;
import gplx.langs.regxs.Regx_group;
import gplx.langs.regxs.Regx_match;
import gplx.objects.strings.unicodes.Ustring_;
import gplx.xowa.xtns.scribunto.Scrib_core;
import gplx.xowa.xtns.scribunto.Scrib_kv_utl_;
import gplx.xowa.xtns.scribunto.Scrib_lua_proc;
import gplx.xowa.xtns.scribunto.libs.patterns.Scrib_pattern_matcher;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_args;
import gplx.xowa.xtns.scribunto.procs.Scrib_proc_rslt;

public class Scrib_lib_ustring_gsub_mgr { // THREAD.UNSAFE:LOCAL_VALUES
	private final Scrib_core core;
	private String src_str;
	private String pat_str;
	private int limit;
	private byte repl_tid;
	private byte[] repl_bry; private Hash_adp repl_hash; private Scrib_lua_proc repl_func;
	public int repl_count = 0;
	public Scrib_lib_ustring_gsub_mgr(Scrib_core core) {
		this.core = core;
	}
	public void Repl_count__add() {repl_count++;}
	public boolean Repl_count__done() {return repl_count == limit;}
	public boolean Exec(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		// get @src_str; NOTE: sometimes int; DATE:2013-11-06
		this.src_str = args.Xstr_str_or_null(0);
		if (args.Len() == 2) return rslt.Init_obj(src_str); // if no @replace, return @src_str; PAGE:en.d:'orse; DATE:2013-10-13

		// get @pattern; NOTE: sometimes int; PAGE:en.d:λύω; DATE:2014-09-02
		this.pat_str = args.Xstr_str_or_null(1);

		// get @repl
		Object repl_obj = args.Cast_obj_or_null(2);
		this.repl_tid = Identify_repl(repl_obj);

		// get @limit; reset repl_count
		this.limit = args.Cast_int_or(3, -1);

		// do repl
		String repl = Scrib_pattern_matcher.New(core.Page_url()).Gsub(this, Ustring_.New_codepoints(src_str), pat_str, 0);
		return rslt.Init_many_objs(repl, repl_count);
	}
	private byte Identify_repl(Object repl_obj) {
		byte repl_tid = Repl_tid_null;
		// @repl can be String, int, table, func
		Class<?> repl_type = repl_obj.getClass();
		if		(Object_.Eq(repl_type, String_.Cls_ref_type)) {
			repl_tid = Repl_tid_string;
			repl_bry = Bry_.new_u8((String)repl_obj);
		}
		else if	(Object_.Eq(repl_type, Int_.Cls_ref_type)) {	// NOTE:@replace sometimes int; PAGE:en.d:λύω; DATE:2014-09-02
			repl_tid = Repl_tid_string;
			repl_bry = Bry_.new_u8(Int_.To_str(Int_.Cast(repl_obj)));
		}
		else if	(Object_.Eq(repl_type, Keyval[].class)) {
			repl_tid = Repl_tid_table;
			repl_hash = Hash_adp_.New();
			Keyval[] kvs = (Keyval[])repl_obj;
			int kvs_len = kvs.length;
			for (int i = 0; i < kvs_len; i++) {
				Keyval kv = kvs[i];
				repl_hash.Add(kv.Key(), Bry_.new_u8(kv.Val_to_str_or_empty()));
			}
		}
		else if	(Object_.Eq(repl_type, Scrib_lua_proc.class)) {
			repl_tid = Repl_tid_luacbk;
			repl_func = (Scrib_lua_proc)repl_obj;
		}
		else if	(Object_.Eq(repl_type, Double_.Cls_ref_type)) {	// NOTE:@replace sometimes double; PAGE:de.v:Wikivoyage:Wikidata/Test_Modul:Wikidata2; DATE:2016-04-21
			repl_tid = Repl_tid_string;
			repl_bry = Bry_.new_u8(Double_.To_str(Double_.cast(repl_obj)));
		}
		else
			throw Err_.new_unhandled(Type_.Name(repl_type));
		return repl_tid;
	}
	public boolean Exec_repl_itm(Bry_bfr tmp_bfr, Scrib_regx_converter regx_converter, Regx_match match) {
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
										// REF.MW: https://github.com/wikimedia/mediawiki-extensions-Scribunto/blob/master/includes/engines/LuaCommon/UstringLibrary.php#L785-L796
										// NOTE: 0 means take result; REF.MW:if ($x === '0'); return $m[0]; PAGE:Wikipedia:Wikipedia_Signpost/Templates/Voter/testcases; DATE:2015-08-02
										if (idx == 0)
											tmp_bfr.Add_str_u8(String_.Mid(src_str, match.Find_bgn(), match.Find_end()));
										// NOTE: > 0 means get from groups if it exists; REF.MW:elseif (isset($m["m$x"])) return $m["m$x"]; PAGE:Wikipedia:Wikipedia_Signpost/Templates/Voter/testcases; DATE:2015-08-02
										else if (idx - 1 < match.Groups().length) {	// retrieve numbered capture; TODO_OLD: support more than 9 captures
											Regx_group grp = match.Groups()[idx - 1];
											tmp_bfr.Add_str_u8(grp.Val());	// NOTE: changed from String_.Mid(src_str, grp.Bgn(), grp.End()); DATE:2020-05-31
										}
										// NOTE: 1 per MW "Match undocumented Lua String.gsub behavior"; PAGE:en.d:Wiktionary:Scripts ISSUE#:393; DATE:2019-03-20
										else if (idx == 1) {
											tmp_bfr.Add_str_u8(String_.Mid(src_str, match.Find_bgn(), match.Find_end()));
										}
										else {
											throw Err_.new_wo_type("invalid capture index %" + Char_.To_str(b) + " in replacement String");
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
				Regx_group[] grps = match.Groups();
				String find_str = null;
				if (grps.length == 0) {
					find_str = String_.Mid(src_str, match.Find_bgn(), match.Find_end());	// NOTE: rslt.Bgn() / .End() is for String pos (bry pos will fail for utf8 strings)
				}
				else {	// group exists, take first one (logic matches Scribunto); PAGE:en.w:Bannered_routes_of_U.S._Route_60; DATE:2014-08-15
					Regx_group grp = grps[0];
					find_str = grp.Val();
				}
				Object actl_repl_obj = repl_hash.Get_by(find_str);
				if (actl_repl_obj == null)			// match found, but no replacement specified; EX:"abc", "[ab]", "a:A"; "b" in regex but not in tbl; EX:d:DVD; DATE:2014-03-31
					tmp_bfr.Add_str_u8(find_str);
				else
					tmp_bfr.Add((byte[])actl_repl_obj);					
				break;
			}
			case Repl_tid_luacbk: {
				Keyval[] luacbk_args = null;
				Regx_group[] grps = match.Groups();
				int grps_len = grps.length;
				// no grps; pass 1 arg based on @match: EX: ("ace", "[b-d]"); args -> ("c")
				if (grps_len == 0) {
					String find_str = String_.Mid(src_str, match.Find_bgn(), match.Find_end());
					luacbk_args = Scrib_kv_utl_.base1_obj_(find_str);
				}
				// grps exist; pass n args based on grp[n].match; EX: ("acfg", "([b-d])([e-g])"); args -> ("c", "f")
				else {
					// memoize any_pos args for loop
					boolean any_pos = regx_converter.Any_pos();
					Keyval[] capt_ary = regx_converter.Capt_ary();
					int capt_ary_len = capt_ary == null ? 0 : capt_ary.length; // capt_ary can be null b/c xowa_gsub will always create one group;

					// loop grps; for each grp, create corresponding arg in luacbk
					luacbk_args = new Keyval[grps_len];
					for (int i = 0; i < grps_len; i++) {
						Regx_group grp = grps[i];

						// anypos will create @offset arg; everything else creates a @match arg based on grp; FOOTNOTE:CAPTURES
						boolean anyposExists = any_pos && i < capt_ary_len && Bool_.Cast(capt_ary[i].Val());
						Object val = null;
						if (anyposExists) {
							// emptyCapture ("anypos" or `()`) must pass integer position; must normalize to base-1 b/c lua callbacks expect base-1 arguments, not base-0; ISSUE#:726; DATE:2020-05-17;
							val = (Object)(grp.Bgn() + List_adp_.Base1);
						}
						else {
							// standardCapture must pass string match
							val = grp.Val();
						}
						luacbk_args[i] = Keyval_.int_(i + Scrib_core.Base_1, val);
					}
				}

				// do callback
				Keyval[] rslts = core.Interpreter().CallFunction(repl_func.Id(), luacbk_args);

				// eval result
				if (rslts.length == 0) // will be 0 when gsub_proc returns nil; PAGE:en.d:tracer; DATE:2017-04-22
					return false;
				else {									// ArrayIndex check
					Object rslt_obj = rslts[0].Val();	// 0th idx has result
					tmp_bfr.Add_str_u8(Object_.Xto_str_strict_or_empty(rslt_obj));	// NOTE: always convert to String; rslt_obj can be int; PAGE:en.d:seven DATE:2016-04-27
				}
				break;
			}
			default: throw Err_.new_unhandled(repl_tid);
		}
		return true;
	}
	private static final byte Repl_tid_null = 0, Repl_tid_string = 1, Repl_tid_table = 2, Repl_tid_luacbk = 3;
	public static final Scrib_lib_ustring_gsub_mgr[] Ary_empty = new Scrib_lib_ustring_gsub_mgr[0];
}
/*
== FOOTNOTE:CAPTURES [ISSUE#:726; DATE:2020-05-17] ==
There are two types of captures:
* '''basicCaptures''': EX: given `abcd`, `a(bc)d` captureValues will be 1, 3 b/c `(bc)` captures the start / end of the match
* '''emptyCaptures''': EX: given `abcd`, `()bcd`  captureValues will be 1, 2 b/c `()`   captures the position of the match

The above captureValues are base0 b/c Str_find_mgr__xowa uses base0
* Keep in mind that XOWA is base0 b/c it works directly with byte arrays and need base0 to index into these 0-based arrays

In contrast, Lua is base1. However, this base1-ness is not exposed anywhere, except in gsubs's FunctionCallback.
Even then, it is only exposed for emptyCaptures, not basicCaptures due to how Lua passes parameters

For example, consider this code:
```
function p.test_726_anypos()
    mw.ustring.gsub("abcd", "a(bc)d", function(arg1)
        mw.log('basic', arg1); -- arg1 is the matched string or "bc"
    end)

    mw.ustring.gsub("abcd", "()bcd", function(arg1)
        mw.log('empty', arg1); -- arg1 is the position of the empty capture or "2"
    end)
end
```

SEE:FOOTNOTE:REGX_GROUP
*/