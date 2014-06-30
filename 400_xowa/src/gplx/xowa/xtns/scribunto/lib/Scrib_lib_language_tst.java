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
import org.junit.*;
import gplx.xowa.langs.numbers.*;
public class Scrib_lib_language_tst {
	@Before public void init() {
		fxt.Clear();
		fxt.Init_page("{{#invoke:Mod_0|Func_0}}");
		lib = fxt.Core().Lib_language().Init();
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	@Test  public void GetContLangCode() {
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_getContLangCode, Object_.Ary_empty, "en");
	}
	@Test  public void IsSupportedLanguage() {
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_isSupportedLanguage, Object_.Ary("fr"), "true");
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_isSupportedLanguage, Object_.Ary("qq"), "false");
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_isSupportedLanguage, Object_.Ary("EN"), "false");
	}
	@Test  public void IsKnownLanguageTag() {
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_isKnownLanguageTag, Object_.Ary("fr"), "true");
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_isKnownLanguageTag, Object_.Ary("qq"), "false");
	}
	@Test  public void IsValidCode() {
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_isValidCode, Object_.Ary("a,b"), "true");
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_isValidCode, Object_.Ary("a'b"), "false");
	}
	@Test  public void IsValidBuiltInCode() {
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_isValidBuiltInCode, Object_.Ary("e-N"), "true");
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_isValidBuiltInCode, Object_.Ary("e n"), "false");
	}
	@Test  public void FetchLanguageName() {
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_fetchLanguageName, Object_.Ary("en"), "English");
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_fetchLanguageName, Object_.Ary("fr"), "Français");
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_fetchLanguageName, Object_.Ary("enx"), "");
	}
	@Test  public void GetFallbacksFor() {
		Xol_lang other_lang = fxt.Core().App().Lang_mgr().Get_by_key_or_new(Bry_.new_ascii_("zh"));
		other_lang.Fallback_bry_(Bry_.new_ascii_("gan-hant, zh-hant, zh-hans"));
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_getFallbacksFor, Object_.Ary("zh"), "gan-hant;zh-hant;zh-hans;en");	// auto-add en
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_getFallbacksFor, Object_.Ary("unknown"), "");
	}
	@Test  public void FormatNum() {
		Xol_lang other_lang = fxt.Core().App().Lang_mgr().Get_by_key_or_new(Bry_.new_ascii_("de")).Init_by_load_assert();	// NOTE: must call Init_by_load_assert, else load will be called by scrib and sprs below will get overwritten during load;
		fxt.Parser_fxt().Init_lang_numbers_separators(other_lang, ".", ",");
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_formatNum, Object_.Ary("de", 1234), "1.234");		// german spr
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_formatNum, Object_.Ary("en", 1234), "1,234");		// english spr
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_formatNum, Object_.Ary("en", "1234"), "1,234");		// String passed (not int)
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_formatNum, Object_.Ary("en", "1234", KeyVal_.Ary(KeyVal_.new_("noCommafy", true)))	, "1234");		// noCommafy.y
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_formatNum, Object_.Ary("en", "1234", KeyVal_.Ary(KeyVal_.new_("noCommafy", false)))	, "1,234");		// noCommafy.n
	}
	@Test  public void FormatDate() {
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_formatDate, Object_.Ary("en", "Y-m-d", "2013-03-17", false), "2013-03-17");
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_formatDate, Object_.Ary("en", "Y-m-d"), DateAdp_.Now().XtoStr_fmt_yyyy_MM_dd());	// empty date should default to today;
	}
	@Test  public void FormatDate_date_omitted() {	// PURPOSE: some calls skip the date; retrieve arg_4 by int; EX: pl.w:L._Frank_Baum
		Tfds.Now_enabled_y_();
		Tfds.Now_set(DateAdp_.new_(2013, 12, 19, 1, 2, 3, 4));
		fxt.Test_lib_proc_kv(lib, Scrib_lib_language.Invk_formatDate, new KeyVal[] {KeyVal_.int_(1, "en"), KeyVal_.int_(2, "Y-m-d"), KeyVal_.int_(4, false)}, "2013-12-19");
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_formatDate, Object_.Ary("en", "Y-m-d", ""), "2013-12-19");// PURPOSE: '' should return today, not fail; EX: th.w:สถานีรถไฟตรัง
		Tfds.Now_enabled_n_();
	}
	@Test  public void Lc() {
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_lc, Object_.Ary("en", "ABC"), "abc");
	}
	@Test  public void Uc() {
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_uc, Object_.Ary("en", "abc"), "ABC");
	}
	@Test  public void LcFirst() {
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_lcfirst, Object_.Ary("en", "ABC"), "aBC");
	}
	@Test  public void UcFirst() {
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_uc, Object_.Ary("en", "abc"), "ABC");
	}
	@Test  public void ParseFormattedNumber() {
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_parseFormattedNumber, Object_.Ary("en", "1,234.56")	, "1234.56");			// formatted String
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_parseFormattedNumber, Object_.Ary("en", "1234")		, "1234");				// String
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_parseFormattedNumber, Object_.Ary("en", 1234)		, "1234");				// int
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_parseFormattedNumber, Object_.Ary("en", 1234.56)		, "1234.56");			// double
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_parseFormattedNumber, Object_.Ary("en"), Scrib_invoke_func_fxt.Null_rslt);	// PURPOSE: missing arg should not fail; EX: ru.w:Туйон DATE:2014-01-06
	}
	@Test  public void ConvertGrammar() {
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_convertGrammar, Object_.Ary("fi", "talo", "elative"), "talosta");
	}
	@Test  public void ConvertPlural() {
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_convertPlural, Object_.Ary("ru", 5, Kv_ary_("a", "b", "c")), "c");
	}
	@Test  public void IsRTL() {
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_isRTL, Object_.Ary("en"), "false");
		Xol_lang other_lang = fxt.Core().App().Lang_mgr().Get_by_key_or_new(Bry_.new_ascii_("ar"));
		GfoInvkAble_.InvkCmd_val(other_lang, Xol_lang.Invk_dir_rtl_, true);
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_isRTL, Object_.Ary("ar"), "true");
	}
	@Test  public void Format_duration() {
		Init_lang_durations(fxt.Core().Wiki());
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_formatDuration, Object_.Ary("en", 3723d, Kv_ary_("hours", "minutes", "seconds")), "1 hour, 2 minutes and 3 seconds");	// basic
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_formatDuration, Object_.Ary("en",  123d, Kv_ary_("hours", "minutes", "seconds")), "2 minutes and 3 seconds");			// omit hour since < 1
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_formatDuration, Object_.Ary("en",  123d, Kv_ary_("hours"))					   , "0 hours");							// handle fractional duration
	}
	@Test  public void Get_duration_intervals() {
		Init_lang_durations(fxt.Core().Wiki());
		fxt.Test_lib_proc(lib, Scrib_lib_language.Invk_getDurationIntervals, Object_.Ary("en", 3723d, Kv_ary_("hours", "minutes", "seconds")), "\n  1;2;3");
	}
	private static KeyVal[] Kv_ary_(String... ary) {
		int ary_len = ary.length;
		KeyVal[] rv = new KeyVal[ary_len];
		for (int i = 0; i < ary_len; i++) {
			rv[i] = KeyVal_.int_(i, ary[i]);
		}
		return rv;
	}
	public static void Init_lang_durations(Xow_wiki wiki) {
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
