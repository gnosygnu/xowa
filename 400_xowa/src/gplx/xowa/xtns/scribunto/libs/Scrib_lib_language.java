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
import gplx.xowa.langs.*;
import gplx.xowa.xtns.pfuncs.times.*; import gplx.xowa.langs.numbers.*; import gplx.xowa.xtns.pfuncs.numbers.*; import gplx.xowa.langs.durations.*;
import gplx.xowa.xtns.scribunto.procs.*;
public class Scrib_lib_language implements Scrib_lib {
	public Scrib_lib_language(Scrib_core core) {this.core = core;} private Scrib_core core;
	public Scrib_lua_mod Mod() {return mod;} private Scrib_lua_mod mod;
	public Scrib_lib Init() {procs.Init_by_lib(this, Proc_names); return this;}
	public Scrib_lib Clone_lib(Scrib_core core) {return new Scrib_lib_language(core);}
	public Scrib_lua_mod Register(Scrib_core core, Io_url script_dir) {
		Init();
		mod = core.RegisterInterface(this, script_dir.GenSubFil("mw.language.lua"));
		notify_lang_changed_fnc = mod.Fncs_get_by_key("notify_lang_changed");
		return mod;
	}	private Scrib_lua_proc notify_lang_changed_fnc;
	public Scrib_proc_mgr Procs() {return procs;} private Scrib_proc_mgr procs = new Scrib_proc_mgr();
	public boolean Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt) {
		switch (key) {
			case Proc_getContLangCode:								return GetContLangCode(args, rslt);
			case Proc_isSupportedLanguage:							return IsSupportedLanguage(args, rslt);
			case Proc_isKnownLanguageTag:							return IsKnownLanguageTag(args, rslt);
			case Proc_isValidCode:									return IsValidCode(args, rslt);
			case Proc_isValidBuiltInCode:							return IsValidBuiltInCode(args, rslt);
			case Proc_fetchLanguageName:							return FetchLanguageName(args, rslt);
			case Proc_fetchLanguageNames:							return FetchLanguageNames(args, rslt);
			case Proc_getFallbacksFor:								return GetFallbacksFor(args, rslt);
			case Proc_lcfirst:										return Lcfirst(args, rslt);
			case Proc_ucfirst:										return Ucfirst(args, rslt);
			case Proc_lc:											return Lc(args, rslt);
			case Proc_uc:											return Uc(args, rslt);
			case Proc_caseFold:										return CaseFold(args, rslt);
			case Proc_formatNum:									return FormatNum(args, rslt);
			case Proc_formatDate:									return FormatDate(args, rslt);
			case Proc_formatDuration:								return FormatDuration(args, rslt);
			case Proc_getDurationIntervals:							return GetDurationIntervals(args, rslt);
			case Proc_parseFormattedNumber:							return ParseFormattedNumber(args, rslt);
			case Proc_convertPlural:								return ConvertPlural(args, rslt);
			case Proc_convertGrammar:								return ConvertGrammar(args, rslt);
			case Proc_gender:										return gender(args, rslt);
			case Proc_isRTL:										return IsRTL(args, rslt);
			default: throw Err_.new_unhandled(key);
		}
	}
	private static final int
	  Proc_getContLangCode = 0, Proc_isSupportedLanguage = 1, Proc_isKnownLanguageTag = 2
	, Proc_isValidCode = 3, Proc_isValidBuiltInCode = 4, Proc_fetchLanguageName = 5, Proc_fetchLanguageNames = 6, Proc_getFallbacksFor = 7
	, Proc_lcfirst = 8, Proc_ucfirst = 9, Proc_lc = 10, Proc_uc = 11, Proc_caseFold = 12
	, Proc_formatNum = 13, Proc_formatDate = 14, Proc_formatDuration = 15, Proc_getDurationIntervals = 16, Proc_parseFormattedNumber = 17
	, Proc_convertPlural = 18, Proc_convertGrammar = 19, Proc_gender = 20, Proc_isRTL = 21
	;
	public static final String 
	  Invk_getContLangCode = "getContLangCode", Invk_isSupportedLanguage = "isSupportedLanguage", Invk_isKnownLanguageTag = "isKnownLanguageTag"
	, Invk_isValidCode = "isValidCode", Invk_isValidBuiltInCode = "isValidBuiltInCode"
	, Invk_fetchLanguageName = "fetchLanguageName", Invk_fetchLanguageNames = "fetchLanguageNames", Invk_getFallbacksFor = "getFallbacksFor"
	, Invk_lcfirst = "lcfirst", Invk_ucfirst = "ucfirst", Invk_lc = "lc", Invk_uc = "uc", Invk_caseFold = "caseFold"
	, Invk_formatNum = "formatNum", Invk_formatDate = "formatDate", Invk_formatDuration = "formatDuration", Invk_getDurationIntervals = "getDurationIntervals", Invk_parseFormattedNumber = "parseFormattedNumber"
	, Invk_convertPlural = "convertPlural", Invk_convertGrammar = "convertGrammar", Invk_gender = "gender", Invk_isRTL = "isRTL"
	;
	private static final    String[] Proc_names = String_.Ary
	( Invk_getContLangCode, Invk_isSupportedLanguage, Invk_isKnownLanguageTag
	, Invk_isValidCode, Invk_isValidBuiltInCode, Invk_fetchLanguageName, Invk_fetchLanguageNames, Invk_getFallbacksFor
	, Invk_lcfirst, Invk_ucfirst, Invk_lc, Invk_uc, Invk_caseFold
	, Invk_formatNum, Invk_formatDate, Invk_formatDuration, Invk_getDurationIntervals, Invk_parseFormattedNumber
	, Invk_convertPlural, Invk_convertGrammar, Invk_gender, Invk_isRTL
	);
	public void Notify_lang_changed() {if (notify_lang_changed_fnc != null) core.Interpreter().CallFunction(notify_lang_changed_fnc.Id(), Keyval_.Ary_empty);}
	public boolean GetContLangCode(Scrib_proc_args args, Scrib_proc_rslt rslt)		{return rslt.Init_obj(core.Ctx().Lang().Key_str());}
	public boolean IsSupportedLanguage(Scrib_proc_args args, Scrib_proc_rslt rslt)	{return IsKnownLanguageTag(args, rslt);}// NOTE: checks if "MessagesXX.php" exists; note that xowa has all "MessagesXX.php"; for now, assume same functionality as IsKnownLanguageTag (worst case is that a small wiki depends on a lang not being there; will need to put in a "wiki.Langs()" then)
	public boolean IsKnownLanguageTag(Scrib_proc_args args, Scrib_proc_rslt rslt) {	// NOTE: checks if in languages/Names.php
		String lang_code = args.Cast_str_or_null(0);
		boolean exists = false;
		if (	lang_code != null									// null check; protecting against Module passing in nil from lua
			&&	String_.Eq(lang_code, String_.Lower(lang_code))		// must be lower-case; REF.MW: $code === strtolower( $code )
			&&	Xol_lang_stub_.Exists(Bry_.new_a7(lang_code))
			)
			exists = true;
		return rslt.Init_obj(exists);
	}
	public boolean IsValidCode(Scrib_proc_args args, Scrib_proc_rslt rslt) {	// REF.MW: Language.php!isValidCode
		byte[] lang_code = args.Pull_bry(0);
		boolean valid = Xoa_ttl.Parse(core.Wiki(), lang_code) != null;	// NOTE: MW calls Title::getTitleInvalidRegex()
		if (valid) {
			int len = lang_code.length;
			for (int i = 0; i < len; i++) {
				byte b = lang_code[i];
				switch (b) {	// NOTE: snippet from MW follows; also \000 assumed to be Nil --> :/\\\000&<>'\" 
					case Byte_ascii.Colon: case Byte_ascii.Slash: case Byte_ascii.Backslash: case Byte_ascii.Null: case Byte_ascii.Amp: case Byte_ascii.Lt: case Byte_ascii.Gt: case Byte_ascii.Apos: case Byte_ascii.Quote:
						valid = false;
						i = len;
						break;
				}
			}
		}
		return rslt.Init_obj(valid);
	}
	public boolean IsValidBuiltInCode(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		byte[] lang_code = args.Pull_bry(0);
		int len = lang_code.length;
		boolean valid = true;
		for (int i = 0; i < len; i++) {	// REF.MW: '/^[a-z0-9-]+$/i'
			byte b = lang_code[i];
			if (b == Byte_ascii.Dash) {}
			else {
				byte tid = Xol_lang_itm_.Char_tid(b);
				switch (tid) {
					case Xol_lang_itm_.Char_tid_ltr_l: case Xol_lang_itm_.Char_tid_ltr_u: case Xol_lang_itm_.Char_tid_num:
						break;
					default:
						valid = false;
						i = len;
						break;
				}
			}
		}
		return rslt.Init_obj(valid);
	}
	public boolean FetchLanguageName(Scrib_proc_args args, Scrib_proc_rslt rslt) {	
		byte[] lang_code = args.Pull_bry(0);
		// byte[] trans_code = args.Get_bry_or_null(1);	// TODO_OLD: FetchLanguageName("en", "fr") -> Anglais; WHEN: needs global database of languages; cldr
		Xol_lang_stub lang_itm = Xol_lang_stub_.Get_by_key_or_null(lang_code);
		String rv = null;

		// unknown item; return 1st three letters; DATE:2017-04-01
		if (lang_itm == null) {
			int lang_code_len = lang_code.length;
			int rv_len = lang_code_len < 3 ? lang_code_len : 3;
			rv = String_.new_u8(Bry_.Mid(lang_code, 0, rv_len));
		}
		// known item; return canonical name
		else {
			rv = String_.new_u8(lang_itm.Canonical_name());
		}
		return rslt.Init_obj(rv);
	}
	public boolean FetchLanguageNames(Scrib_proc_args args, Scrib_proc_rslt rslt) {	
		// byte[] lang_code = args.Cast_bry_or_null(0);
		// byte[] include = args.Form_bry_or(1, FetchLanguageNames_mw);
		return rslt.Init_obj(core.Wiki().Cache_mgr().Scrib_lang_names()); // NOTE: return current language only; MW iterates over langs in wiki; XO is more complicated as technically all langs are available; need to map subset of langs per wiki, which is not trivial
	}
	public boolean GetFallbacksFor(Scrib_proc_args args, Scrib_proc_rslt rslt) {	
		byte[] lang_code = args.Pull_bry(0);
		Xol_lang_itm lang = core.App().Lang_mgr().Get_by(lang_code); if (lang == null) return rslt.Init_many_empty();	// lang is not valid; return empty array per MW;
		return rslt.Init_bry_ary(lang.Fallback_bry_ary());
	}
	public boolean Lcfirst(Scrib_proc_args args, Scrib_proc_rslt rslt) {return Case_1st(args, rslt, Bool_.N);}
	public boolean Ucfirst(Scrib_proc_args args, Scrib_proc_rslt rslt) {return Case_1st(args, rslt, Bool_.Y);}
	private boolean Case_1st(Scrib_proc_args args, Scrib_proc_rslt rslt, boolean upper) {
		Xol_lang_itm lang = lang_(args);
		byte[] word = args.Pull_bry(1);
		Bry_bfr bfr = core.Wiki().Utl__bfr_mkr().Get_b128();
		try {
			return rslt.Init_obj(lang.Case_mgr().Case_build_1st(bfr, upper, word, 0, word.length));
		} finally {bfr.Mkr_rls();}
	}
	public boolean Lc(Scrib_proc_args args, Scrib_proc_rslt rslt) {return Case_all(args, rslt, Bool_.N);}
	public boolean Uc(Scrib_proc_args args, Scrib_proc_rslt rslt) {return Case_all(args, rslt, Bool_.Y);}
	private boolean Case_all(Scrib_proc_args args, Scrib_proc_rslt rslt, boolean upper) {
		Xol_lang_itm lang = lang_(args);
		byte[] word = args.Pull_bry(1);
		return rslt.Init_obj(lang.Case_mgr().Case_build(upper, word, 0, word.length));
	}
	public boolean CaseFold(Scrib_proc_args args, Scrib_proc_rslt rslt) {return Uc(args, rslt);}	// REF.MW:Language.php!caseFold; http://www.w3.org/International/wiki/Case_folding
	public boolean FormatNum(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Xol_lang_itm lang = lang_(args);
		byte[] num = args.Xstr_bry_or_null(1);
		boolean skip_commafy = false;
		if (num != null) {	// MW: if num present, check options table for noCommafy arg;
			Keyval[] kv_ary = args.Cast_kv_ary_or_null(2);
			if (kv_ary != null) {
				Object skip_commafy_obj = Keyval_.Ary_get_by_key_or_null(kv_ary, "noCommafy");
				if (skip_commafy_obj != null)
					skip_commafy = Bool_.Cast(skip_commafy_obj);
			}
		}
		byte[] rv = lang.Num_mgr().Format_num(num, skip_commafy);
		return rslt.Init_obj(rv);
	}
	public boolean FormatDate(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Xol_lang_itm lang = lang_(args);
		byte[] fmt_bry = args.Pull_bry(1);
		byte[] date_bry = args.Cast_bry_or_empty(2);	// NOTE: optional empty is required b/c date is sometimes null; use Bry_.Empty b/c this is what Pft_func_time.ParseDate takes; DATE:2013-04-05
		boolean utc = args.Cast_bool_or_n(3);
		Xowe_wiki wiki = core.Wiki();
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		Pft_fmt_itm[] fmt_ary = Pft_fmt_itm_.Parse(core.Ctx(), fmt_bry);
		DateAdp date = null;
		if (Bry_.Len_eq_0(date_bry))
			date = Datetime_now.Get();
		else {				
			if (date_bry[0] == Byte_ascii.Plus) {				// detect wikidata-style dates; EX: +00000002010-05-01T00:00:00Z; PAGE:en.w:Mountain_Province; DATE:2015-07-29
				int date_bry_len = date_bry.length;
				if (	date_bry[date_bry_len -  1] == Byte_ascii.Ltr_Z
					&&	date_bry[date_bry_len - 10] == Byte_ascii.Ltr_T) {
					int year_bgn = Bry_find_.Find_fwd_while(date_bry, 1, date_bry_len, Byte_ascii.Num_0);
					date_bry = Bry_.Mid(date_bry, year_bgn);	// lop off beginning "+000000..."
				}
			}
			date = Pft_func_time.ParseDate(date_bry, utc, tmp_bfr);	// NOTE: not using java's datetime parse b/c it is more strict; not reconstructing PHP's datetime parse b/c it is very complicated (state machine); re-using MW's parser b/c it is inbetween; DATE:2015-07-29
		}
		if (date == null || tmp_bfr.Len() > 0) {tmp_bfr.Mkr_rls(); return rslt.Init_fail("bad argument #2 to 'formatDate' (not a valid timestamp)");}
		
		wiki.Parser_mgr().Date_fmt_bldr().Format(tmp_bfr, wiki, lang, date, fmt_ary);
		byte[] rv = tmp_bfr.To_bry_and_rls();
		return rslt.Init_obj(rv);
	}
	public boolean ParseFormattedNumber(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Xol_lang_itm lang = lang_(args);
		byte[] num = args.Xstr_bry_or_null(1);
		if (num == null) return rslt.Init_null(); // ParseFormattedNumber can sometimes take 1 arg ({'en'}), or null arg ({'en', null}); return null (not ""); DATE:2014-01-07
		byte[] rv = lang.Num_mgr().Raw(num);
		return rslt.Init_obj(rv);
	}
	public boolean FormatDuration(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Xol_lang_itm lang = lang_(args);
		long seconds = args.Pull_long(1);
		Keyval[] intervals_kv_ary = args.Cast_kv_ary_or_null(2);
		Xol_duration_itm[] intervals = Xol_duration_itm_.Xto_itm_ary(intervals_kv_ary);
		byte[] rv = lang.Duration_mgr().Format_durations(core.Ctx(), seconds, intervals);
		return rslt.Init_obj(rv);
	}
	public boolean GetDurationIntervals(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Xol_lang_itm lang = lang_(args);
		long seconds = args.Pull_long(1);
		Keyval[] intervals_kv_ary = args.Cast_kv_ary_or_null(2);
		Xol_duration_itm[] intervals = Xol_duration_itm_.Xto_itm_ary(intervals_kv_ary);
		Xol_interval_itm[] rv = lang.Duration_mgr().Get_duration_intervals(seconds, intervals);
		return rslt.Init_obj(Xol_interval_itm.Xto_kv_ary(rv));
	}
	public boolean ConvertPlural(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Xol_lang_itm lang = lang_(args);
		int count = args.Pull_int(1);
		byte[][] words = args.Cast_params_as_bry_ary_or_rest_of_ary(2);
		byte[] rv = lang.Plural().Plural_eval(lang, count, words);
		return rslt.Init_obj(rv);
	}
	public boolean ConvertGrammar(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Xol_lang_itm lang = lang_(args);
		byte[] word = args.Pull_bry(1);
		byte[] type = args.Pull_bry(2);
		Bry_bfr bfr = core.Wiki().Utl__bfr_mkr().Get_b512();
		lang.Grammar().Grammar_eval(bfr, lang, word, type);
		return rslt.Init_obj(bfr.To_str_and_rls());
	}
	public boolean gender(Scrib_proc_args args, Scrib_proc_rslt rslt) {throw Err_.new_unimplemented();}
	public boolean IsRTL(Scrib_proc_args args, Scrib_proc_rslt rslt) {
		Xol_lang_itm lang = lang_(args);
		return rslt.Init_obj(!lang.Dir_ltr());
	}
	private Xol_lang_itm lang_(Scrib_proc_args args) {
		byte[] lang_code = args.Cast_bry_or_null(0);
		Xol_lang_itm lang = lang_code == null ? null : core.App().Lang_mgr().Get_by_or_load(lang_code);
		if (lang == null) throw Err_.new_wo_type("lang_code is not valid", "lang_code", String_.new_u8(lang_code));
		return lang;
	}
}
