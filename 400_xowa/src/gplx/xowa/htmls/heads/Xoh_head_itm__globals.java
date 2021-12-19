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
package gplx.xowa.htmls.heads;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.libs.files.Io_url;
import gplx.xowa.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*; import gplx.xowa.langs.numbers.*;
public class Xoh_head_itm__globals extends Xoh_head_itm__base {
	private final Xoh_head_wtr tmp_wtr = new Xoh_head_wtr();
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
		wtr.Write_js_var(Var_xowa_root_dir			, BoolUtl.Y, app.Fsys_mgr().Root_dir().To_http_file_bry());
		wtr.Write_js_var(Var_xowa_mode_is_server	, BoolUtl.N, app.Tcp_server().Running() ? BoolUtl.TrueBry : BoolUtl.FalseBry);
	}
	@Override public void Write_js_tail_script(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_xowa_var(Key__app_mode, BoolUtl.Y, app.Mode().Name());
		wtr.Write_js_alias_var	(Page__alias, Page__key);
		wtr.Write_js_alias_kv	(Page__alias, Key__wiki		, page.Wiki().Domain_bry());
		wtr.Write_js_alias_kv	(Page__alias, Key__ttl		, page.Ttl().Page_db());
		wtr.Write_js_alias_kv	(Page__alias, Key__guid		, BryUtl.NewA7(page.Page_guid().ToStr()));
	}	private static final byte[] Key__app_mode = BryUtl.NewA7("xowa.app.mode"), Page__alias = BryUtl.NewA7("x_p"), Page__key = BryUtl.NewA7("xowa.page"), Key__wiki = BryUtl.NewA7("wiki"), Key__ttl = BryUtl.NewA7("ttl"), Key__guid = BryUtl.NewA7("guid");
	@Override public void Write_js_head_global(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xoh_head_wtr wtr) {
		wtr.Write_js_global_ini_atr_val(Key_mode_is_gui			, app.Mode().Tid_is_gui());
		wtr.Write_js_global_ini_atr_val(Key_mode_is_http		, app.Mode().Tid_is_http());
		wtr.Write_js_global_ini_atr_val(Key_http_port			, app.Http_server().Port());
		wtr.Write_js_global_ini_atr_msg(wiki, Key_sort_ascending);
		wtr.Write_js_global_ini_atr_msg(wiki, Key_sort_descending);
		Xol_lang_itm lang = wiki.Lang(); Xow_msg_mgr msg_mgr = wiki.Msg_mgr();
		BryWtr tmp_bfr = wiki.Utl__bfr_mkr().GetB512();
		tmp_wtr.Init(tmp_bfr);
		byte[] months_long = Html_js_table_months(tmp_wtr, msg_mgr, Xol_msg_itm_.Id_dte_month_name_january, Xol_msg_itm_.Id_dte_month_name_december);
		byte[] months_short = Html_js_table_months(tmp_wtr, msg_mgr, Xol_msg_itm_.Id_dte_month_abrv_jan, Xol_msg_itm_.Id_dte_month_abrv_dec);
		byte[] num_format_separators = Html_js_table_num_format_separators(tmp_wtr, lang.Num_mgr().Separators_mgr());
		tmp_bfr.MkrRls();
		wtr.Write_js_global_ini_atr_val(Key_wgContentLanguage			, lang.Key_bry());
		wtr.Write_js_global_ini_atr_obj(Key_wgSeparatorTransformTable	, num_format_separators);
		wtr.Write_js_global_ini_atr_obj(Key_wgDigitTransformTable		, Num_format_digits);
		wtr.Write_js_global_ini_atr_val(Key_wgDefaultDateFormat			, Date_format_default);
		wtr.Write_js_global_ini_atr_obj(Key_wgMonthNames				, months_long);
		wtr.Write_js_global_ini_atr_obj(Key_wgMonthNamesShort			, months_short);
	}
	public static final byte[]	// NOTE: most of these are for the table-sorter
	  Key_mode_is_gui					= BryUtl.NewA7("mode_is_gui")
	, Key_mode_is_http					= BryUtl.NewA7("mode_is_http")
	, Key_http_port						= BryUtl.NewA7("http-port")
	, Key_sort_descending				= BryUtl.NewA7("sort-descending")
	, Key_sort_ascending				= BryUtl.NewA7("sort-ascending")
	, Key_wgContentLanguage				= BryUtl.NewA7("wgContentLanguage")
	, Key_wgSeparatorTransformTable		= BryUtl.NewA7("wgSeparatorTransformTable")
	, Key_wgDigitTransformTable			= BryUtl.NewA7("wgDigitTransformTable")
	, Key_wgDefaultDateFormat			= BryUtl.NewA7("wgDefaultDateFormat")
	, Key_wgMonthNames					= BryUtl.NewA7("wgMonthNames")
	, Key_wgMonthNamesShort				= BryUtl.NewA7("wgMonthNamesShort")
	;
	private static byte[] Html_js_table_months(Xoh_head_wtr tmp_wtr, Xow_msg_mgr msg_mgr, int january_id, int december_id) {
		tmp_wtr.Write_js_ary_bgn();
		tmp_wtr.Write_js_ary_itm(BryUtl.Empty);	// 1st month is always empty
		for (int i = january_id; i <= december_id; i++)
			tmp_wtr.Write_js_ary_itm(msg_mgr.Val_by_id(i));
		tmp_wtr.Write_js_ary_end();
		return tmp_wtr.Bfr().ToBryAndClear();
	}
	private static byte[] Html_js_table_num_format_separators(Xoh_head_wtr tmp_wtr, Xol_transform_mgr separator_mgr) {
		byte[] dec_spr = separator_mgr.Get_val_or_self(Xol_num_mgr.Separators_key__dec);
		byte[] grp_spr = separator_mgr.Get_val_or_self(Xol_num_mgr.Separators_key__grp);
		tmp_wtr.Write_js_ary_bgn();
		tmp_wtr.Write_js_ary_itm(BryUtl.Add(dec_spr, AsciiByte.TabBry, AsciiByte.DotBry));
		tmp_wtr.Write_js_ary_itm(BryUtl.Add(grp_spr, AsciiByte.TabBry, AsciiByte.CommaBry));
		tmp_wtr.Write_js_ary_end();
		return tmp_wtr.Bfr().ToBryAndClear();
	}
	private static final byte[]
	  Date_format_default			= BryUtl.NewA7("dmy")
	, Num_format_digits				= BryUtl.NewA7("['', '']")
	, Var_xowa_root_dir				= BryUtl.NewA7("xowa_root_dir")
	, Var_xowa_mode_is_server		= BryUtl.NewA7("xowa_mode_is_server")
	;
	private static byte[] Url_core_css, Url_core_js, Url_exec_js, Url_DOMContentLoaded_js;
}
