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
package gplx.xowa.htmls.heads; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*; import gplx.xowa.langs.numbers.*;
public class Xoh_head_itm__globals extends Xoh_head_itm__base {
	private final    Xoh_head_wtr tmp_wtr = new Xoh_head_wtr();
	@Override public byte[] Key() {return Xoh_head_itm_.Key__globals;}
	@Override public int Flags() {return Flag__css_include | Flag__js_include | Flag__js_head_script | Flag__js_tail_script | Flag__js_head_global;}
	@Override public void Write_css_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		if (Url_core_css == null) Url_core_css = app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("xowa", "html", "res", "src", "xowa", "core", "core.css").To_http_file_bry();
		wtr.Write_css_include(Url_core_css);
	}
	@Override public void Write_js_include(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		if (Url_core_js == null) {
			Io_url core_dir = app.Fsys_mgr().Bin_any_dir().GenSubDir_nest("xowa", "html", "res", "src", "xowa", "core");
			Url_core_js				= core_dir.GenSubFil("core.js").To_http_file_bry();
			Url_exec_js				= core_dir.GenSubFil("exec.js").To_http_file_bry();
			Url_DOMContentLoaded_js = core_dir.GenSubFil("DOMContentLoaded.js").To_http_file_bry();
		}
		wtr.Write_js_include(Url_core_js);
		wtr.Write_js_include(Url_exec_js);
		wtr.Write_js_include(Url_DOMContentLoaded_js);
	}
	@Override public void Write_js_head_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_var(Var_xowa_root_dir			, Bool_.Y, app.Fsys_mgr().Root_dir().To_http_file_bry());
		wtr.Write_js_var(Var_xowa_mode_is_server	, Bool_.N, app.Tcp_server().Running() ? Bool_.True_bry : Bool_.False_bry);
	}
	@Override public void Write_js_tail_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_xowa_var(Key__app_mode, Bool_.Y, app.Mode().Name());
		wtr.Write_js_alias_var	(Page__alias, Page__key);
		wtr.Write_js_alias_kv	(Page__alias, Key__wiki		, page.Wiki().Domain_bry());
		wtr.Write_js_alias_kv	(Page__alias, Key__ttl		, page.Ttl().Page_db());
	}	private static final    byte[] Key__app_mode = Bry_.new_a7("xowa.app.mode"), Page__alias = Bry_.new_a7("x_p"), Page__key = Bry_.new_a7("xowa.page"), Key__wiki = Bry_.new_a7("wiki"), Key__ttl = Bry_.new_a7("ttl");
	@Override public void Write_js_head_global(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_global_ini_atr_val(Key_mode_is_gui			, app.Mode().Tid_is_gui());
		wtr.Write_js_global_ini_atr_val(Key_mode_is_http		, app.Mode().Tid_is_http());
		wtr.Write_js_global_ini_atr_val(Key_http_port			, app.Http_server().Port());
		wtr.Write_js_global_ini_atr_msg(wiki, Key_sort_ascending);
		wtr.Write_js_global_ini_atr_msg(wiki, Key_sort_descending);
		Xol_lang_itm lang = wiki.Lang(); Xow_msg_mgr msg_mgr = wiki.Msg_mgr();
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		tmp_wtr.Init(tmp_bfr);
		byte[] months_long = Html_js_table_months(tmp_wtr, msg_mgr, Xol_msg_itm_.Id_dte_month_name_january, Xol_msg_itm_.Id_dte_month_name_december);
		byte[] months_short = Html_js_table_months(tmp_wtr, msg_mgr, Xol_msg_itm_.Id_dte_month_abrv_jan, Xol_msg_itm_.Id_dte_month_abrv_dec);
		byte[] num_format_separators = Html_js_table_num_format_separators(tmp_wtr, lang.Num_mgr().Separators_mgr());
		tmp_bfr.Mkr_rls();
		wtr.Write_js_global_ini_atr_val(Key_wgContentLanguage			, lang.Key_bry());
		wtr.Write_js_global_ini_atr_obj(Key_wgSeparatorTransformTable	, num_format_separators);
		wtr.Write_js_global_ini_atr_obj(Key_wgDigitTransformTable		, Num_format_digits);
		wtr.Write_js_global_ini_atr_val(Key_wgDefaultDateFormat			, Date_format_default);
		wtr.Write_js_global_ini_atr_obj(Key_wgMonthNames				, months_long);
		wtr.Write_js_global_ini_atr_obj(Key_wgMonthNamesShort			, months_short);
	}
	public static final    byte[]	// NOTE: most of these are for the table-sorter
	  Key_mode_is_gui					= Bry_.new_a7("mode_is_gui")
	, Key_mode_is_http					= Bry_.new_a7("mode_is_http")
	, Key_http_port						= Bry_.new_a7("http-port")
	, Key_sort_descending				= Bry_.new_a7("sort-descending")
	, Key_sort_ascending				= Bry_.new_a7("sort-ascending")
	, Key_wgContentLanguage				= Bry_.new_a7("wgContentLanguage")
	, Key_wgSeparatorTransformTable		= Bry_.new_a7("wgSeparatorTransformTable")
	, Key_wgDigitTransformTable			= Bry_.new_a7("wgDigitTransformTable")
	, Key_wgDefaultDateFormat			= Bry_.new_a7("wgDefaultDateFormat")
	, Key_wgMonthNames					= Bry_.new_a7("wgMonthNames")
	, Key_wgMonthNamesShort				= Bry_.new_a7("wgMonthNamesShort")
	;
	private static byte[] Html_js_table_months(Xoh_head_wtr tmp_wtr, Xow_msg_mgr msg_mgr, int january_id, int december_id) {
		tmp_wtr.Write_js_ary_bgn();
		tmp_wtr.Write_js_ary_itm(Bry_.Empty);	// 1st month is always empty
		for (int i = january_id; i <= december_id; i++)
			tmp_wtr.Write_js_ary_itm(msg_mgr.Val_by_id(i));
		tmp_wtr.Write_js_ary_end();
		return tmp_wtr.Bfr().To_bry_and_clear();
	}
	private static byte[] Html_js_table_num_format_separators(Xoh_head_wtr tmp_wtr, Xol_transform_mgr separator_mgr) {
		byte[] dec_spr = separator_mgr.Get_val_or_self(Xol_num_mgr.Separators_key__dec);
		byte[] grp_spr = separator_mgr.Get_val_or_self(Xol_num_mgr.Separators_key__grp);
		tmp_wtr.Write_js_ary_bgn();
		tmp_wtr.Write_js_ary_itm(Bry_.Add(dec_spr, Byte_ascii.Tab_bry, Byte_ascii.Dot_bry));
		tmp_wtr.Write_js_ary_itm(Bry_.Add(grp_spr, Byte_ascii.Tab_bry, Byte_ascii.Comma_bry));
		tmp_wtr.Write_js_ary_end();
		return tmp_wtr.Bfr().To_bry_and_clear();
	}
	private static final    byte[]
	  Date_format_default			= Bry_.new_a7("dmy")
	, Num_format_digits				= Bry_.new_a7("['', '']")
	, Var_xowa_root_dir				= Bry_.new_a7("xowa_root_dir")
	, Var_xowa_mode_is_server		= Bry_.new_a7("xowa_mode_is_server")
	;
	private static byte[] Url_core_css, Url_core_js, Url_exec_js, Url_DOMContentLoaded_js;
}
