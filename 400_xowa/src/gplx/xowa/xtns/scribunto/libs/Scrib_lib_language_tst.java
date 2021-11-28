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

import gplx.Bry_;
import gplx.DateAdp_;
import gplx.Datetime_now;
import gplx.Gfo_invk_;
import gplx.Io_mgr;
import gplx.Keyval;
import gplx.Keyval_;
import gplx.Object_;
import gplx.String_;
import gplx.langs.jsons.Json_doc;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.langs.Xol_lang_itm;
import gplx.xowa.langs.msgs.Xol_msg_mgr;
import gplx.xowa.xtns.cldrs.Cldr_name_loader_fxt;
import gplx.xowa.xtns.scribunto.Scrib_invoke_func_fxt;
import gplx.xowa.xtns.scribunto.Scrib_lib;
import org.junit.Before;
import org.junit.Test;

public class Scrib_lib_language_tst {
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = fxt.Core().Lib_language().Init();
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	@Test public void GetContLangCode() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_getContLangCode, Object_.Ary_empty, "en");
	}
	@Test public void IsSupportedLanguage() {
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_language.Invk_isSupportedLanguage, Object_.Ary("fr"), true);
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_language.Invk_isSupportedLanguage, Object_.Ary("qq"), false);
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_language.Invk_isSupportedLanguage, Object_.Ary("EN"), false);
	}
	@Test public void IsKnownLanguageTag() {
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_language.Invk_isKnownLanguageTag, Object_.Ary("fr"), true);
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_language.Invk_isKnownLanguageTag, Object_.Ary("qq"), false);
	}
	@Test public void IsKnownLanguageTag_cldr() {
		Io_mgr.Instance.InitEngine_mem();
		Cldr_name_loader_fxt.Create_file_w_langs(fxt.Core().Lang().Key_str(), Keyval_.Ary(Keyval_.new_("goh", "goh_name")));
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_language.Invk_isKnownLanguageTag, Object_.Ary("goh"), true);
	}
	@Test public void IsValidCode() {
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_language.Invk_isValidCode, Object_.Ary("a,b"), true);
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_language.Invk_isValidCode, Object_.Ary("a'b"), false);
	}
	@Test public void IsValidBuiltInCode() {
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_language.Invk_isValidBuiltInCode, Object_.Ary("e-N"), true);
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_language.Invk_isValidBuiltInCode, Object_.Ary("e n"), false);
	}
	@Test public void FetchLanguageName() {
		Io_mgr.Instance.SaveFilStr("mem/xowa/bin/any/xowa/cfg/lang/data/names.json", Json_doc.Make_str_by_apos
		( "["
		, "  {"
		, "    'code':'en'"
		, "  , 'name':'English'"
		, "  , 'note':'en_note'"
		, "  }"
		, ", {"
		, "    'code':'fr'"
		, "  , 'name':'Français'"
		, "  , 'note':'fr_note'"
		, "  }"
		, "]" 

		));		
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_fetchLanguageName, Object_.Ary("en"), "English");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_fetchLanguageName, Object_.Ary("fr"), "Français");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_fetchLanguageName, Object_.Ary("qz"), "");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_fetchLanguageName, Object_.Ary("459"), ""); // PAGE:en.w:United_States_Strategic_Bombing_Survey; DATE:2018-07-01
	}
	@Test public void GetFallbacksFor() {
		Xol_lang_itm other_lang = fxt.Core().App().Lang_mgr().Get_by_or_new(Bry_.new_a7("zh"));
		other_lang.Fallback_bry_(Bry_.new_a7("gan-hant, zh-hant, zh-hans"));
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_language.Invk_getFallbacksFor, Object_.Ary("zh"), String_.Concat_lines_nl
		( "1="
		, "  1=gan-hant"
		, "  2=zh-hant"
		, "  3=zh-hans"
		, "  4=en"		// auto-add en
		));
	}
	@Test public void GetFallbacksFor_unknown() {
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_language.Invk_getFallbacksFor, Object_.Ary("unknown"), String_.Concat_lines_nl
		( "1="
		, "  1=en" // use "en" if unknown; REF:/languages/Language.php|getFallbacksFor; ISSUE#:340; DATE:2019-02-01
		));
	}
	@Test public void FormatNum() {
		// init
		Xol_lang_itm other_lang = fxt.Core().App().Lang_mgr().Get_by_or_load(Bry_.new_a7("de"));	// NOTE: must call load now, else load will be called by scrib and sprs below will get overwritten during load;
		fxt.Parser_fxt().Init_lang_numbers_separators(other_lang, ".", ",");

		// test
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatNum, Object_.Ary("de", 1234), "1.234");		// german spr
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatNum, Object_.Ary("en", 1234), "1,234");		// english spr
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatNum, Object_.Ary("en", "1234"), "1,234");		// String passed (not int)
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatNum, Object_.Ary("en", "1234", Keyval_.Ary(Keyval_.new_("noCommafy", true)))		, "1234");		// noCommafy.y
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatNum, Object_.Ary("en", "1234", Keyval_.Ary(Keyval_.new_("noCommafy", false)))	, "1,234");		// noCommafy.n
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatNum, Object_.Ary("en", "1234", Keyval_.Ary(Keyval_.new_("noCommafy", "y")))		, "1234");		// noCommafy."y"; ISSUE#:372 DATE:2019-03-02
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatNum, Object_.Ary("en", 768d / 10000000d)		, "7.68E-5");	// ensure "e-05" -> "E-5" ISSUE#:697; DATE:2020-08-12
	}
	@Test public void FormatDate() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatDate, Object_.Ary("en", "Y-m-d", "2013-03-17", false), "2013-03-17");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatDate, Object_.Ary("en", "Y-m-d"), Datetime_now.Get().XtoStr_fmt_yyyy_MM_dd());	// empty date should default to today;
	}
	@Test  public void FormatDate__ymd_12() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatDate, Object_.Ary("en", "Y-m-d", "201603160102", false), "2016-03-16");		// handle long numeric date (12 digits); PAGE:en.w:Boron; DATE:2015-07-29
	}
	@Test  public void FormatDate__utc() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatDate, Object_.Ary("en", "Y-m-d", "+00000002010-05-01T00:00:00Z", false), "2010-05-01");		// handle Wikidata style dates; PAGE:en.w:Mountain_Province; DATE:2015-07-29
	}
	@Test  public void FormatDate__bce() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatDate, Object_.Ary("en", "Y-m-d", "+0065-12-08T00:00:00Z", false), "0065-12-08"); // ISSUE#:500
	}
	@Test public void FormatDate_date_omitted() {	// PURPOSE: some calls skip the date; retrieve arg_4 by int; EX: pl.w:L._Frank_Baum
		Datetime_now.Manual_y_();
		Datetime_now.Manual_(DateAdp_.new_(2013, 12, 19, 1, 2, 3, 4));
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatDate, Keyval_.Ary(Keyval_.int_(1, "en"), Keyval_.int_(2, "Y-m-d"), Keyval_.int_(4, false)), "2013-12-19");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatDate, Object_.Ary("en", "Y-m-d", ""), "2013-12-19");// PURPOSE: '' should return today, not fail; EX: th.w:สถานีรถไฟตรัง
		Datetime_now.Manual_n_();
	}
	@Test public void Lc() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_lc, Object_.Ary("en", "ABC"), "abc");
	}
	@Test public void Uc() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_uc, Object_.Ary("en", "abc"), "ABC");
	}
	@Test public void LcFirst() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_lcfirst, Object_.Ary("en", "ABC"), "aBC");
	}
	@Test public void UcFirst() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_uc, Object_.Ary("en", "abc"), "ABC");
	}
	@Test public void ParseFormattedNumber() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_parseFormattedNumber, Object_.Ary("en", "1,234.56")	, "1234.56");			// formatted String
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_parseFormattedNumber, Object_.Ary("en", "1234")		, "1234");				// String
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_parseFormattedNumber, Object_.Ary("en", 1234)			, "1234");				// int
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_parseFormattedNumber, Object_.Ary("en", 1234.56)		, "1234.56");			// double
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_parseFormattedNumber, Object_.Ary("en"), Scrib_invoke_func_fxt.Null_rslt);	// PURPOSE: missing arg should not fail; EX: ru.w:Туйон DATE:2014-01-06
	}
	@Test public void ConvertGrammar() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_convertGrammar, Object_.Ary("fi", "talo", "elative"), "talosta");
	}
	@Test public void ConvertPlural() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_convertPlural, Object_.Ary("ru", 5, Kv_ary_("a", "b", "c")), "c");			// forms in kv_ary
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_convertPlural, Object_.Ary("ru", 5, "a", "b", "c"), "c");					// forms as rest of ary; PAGE:ru.w:Ленин,_Владимир_Ильич DATE:2014-07-01
	}
	@Test public void IsRTL() {
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_language.Invk_isRTL, Object_.Ary("en"), false);
		Xol_lang_itm other_lang = fxt.Core().App().Lang_mgr().Get_by_or_new(Bry_.new_a7("ar"));
		Gfo_invk_.Invk_by_val(other_lang, Xol_lang_itm.Invk_dir_rtl_, true);
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_language.Invk_isRTL, Object_.Ary("ar"), true);
	}
	@Test public void Format_duration() {
		Init_lang_durations(fxt.Core().Wiki());
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatDuration, Object_.Ary("en", 3723d, Kv_ary_("hours", "minutes", "seconds")), "1 hour, 2 minutes and 3 seconds");	// basic
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatDuration, Object_.Ary("en",  123d, Kv_ary_("hours", "minutes", "seconds")), "2 minutes and 3 seconds");			// omit hour since < 1
		fxt.Test_scrib_proc_str(lib, Scrib_lib_language.Invk_formatDuration, Object_.Ary("en",  123d, Kv_ary_("hours"))					     , "0 hours");							// handle fractional duration
	}
	@Test public void Get_duration_intervals() {
		Init_lang_durations(fxt.Core().Wiki());
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_language.Invk_getDurationIntervals, Object_.Ary("en", 3723d, Kv_ary_("hours", "minutes", "seconds")), String_.Concat_lines_nl_skip_last
		( "1="
		, "  hours=1"
		, "  minutes=2"
		, "  seconds=3"
		));
	}
	public static Keyval[] Kv_ary_(String... ary) {
		int ary_len = ary.length;
		Keyval[] rv = new Keyval[ary_len];
		for (int i = 0; i < ary_len; i++) {
			rv[i] = Keyval_.int_(i, ary[i]);
		}
		return rv;
	}
	public static void Init_lang_durations(Xowe_wiki wiki) {
		Xol_msg_mgr msg_mgr = wiki.Lang().Msg_mgr();
		msg_mgr.Clear();
		msg_mgr.Itm_by_key_or_new("duration-millenia"	, "~{0} {{PLURAL:~{0}\u007Cmillennium\u007Cmillennia}}");
		msg_mgr.Itm_by_key_or_new("duration-centuries"	, "~{0} {{PLURAL:~{0}\u007Ccentury\u007Ccenturies}}");
		msg_mgr.Itm_by_key_or_new("duration-decades"	, "~{0} {{PLURAL:~{0}\u007Cdecade\u007Cdecades}}");
		msg_mgr.Itm_by_key_or_new("duration-years"		, "~{0} {{PLURAL:~{0}\u007Cyear\u007Cyears}}");
		msg_mgr.Itm_by_key_or_new("duration-weeks"		, "~{0} {{PLURAL:~{0}\u007Cweek\u007Cweeks}}");
		msg_mgr.Itm_by_key_or_new("duration-days"		, "~{0} {{PLURAL:~{0}\u007Cday\u007Cdays}}");
		msg_mgr.Itm_by_key_or_new("duration-hours"		, "~{0} {{PLURAL:~{0}\u007Chour\u007Chours}}");
		msg_mgr.Itm_by_key_or_new("duration-minutes"	, "~{0} {{PLURAL:~{0}\u007Cminute\u007Cminutes}}");
		msg_mgr.Itm_by_key_or_new("duration-seconds"	, "~{0} {{PLURAL:~{0}\u007Csecond\u007Cseconds}}");
		msg_mgr.Itm_by_key_or_new("and"					, "&#32;and");
		msg_mgr.Itm_by_key_or_new("word-separator"		, "&#32;");
		msg_mgr.Itm_by_key_or_new("comma-separator"		, ",&#32;");			
	}
}
