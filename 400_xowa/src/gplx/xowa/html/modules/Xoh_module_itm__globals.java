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
package gplx.xowa.html.modules; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.xowa.langs.numbers.*;
public class Xoh_module_itm__globals implements Xoh_module_itm {
	public byte[] Key() {return Key_const;} private static final byte[] Key_const = Bry_.new_ascii_("globals");
	public boolean Enabled() {return enabled;} public void Enabled_n_() {enabled = false;} public void Enabled_y_() {enabled = true;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled;
	public void Clear() {enabled = false;}
	public void Write_css_include(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr) {}
	public void Write_css_script(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr) {}
	public void Write_js_include(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr) {
		if (!enabled) return;
		if (Url_core == null) Url_core = app.Fsys_mgr().Bin_any_dir().GenSubFil_nest("xowa", "html", "modules", "xowa.core", "xowa.core.js").To_http_file_bry();
		wtr.Write_js_include(Url_core);
	}
	public void Write_js_head_script(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr) {
		if (!enabled) return;
		wtr.Write_js_var(Var_xowa_root_dir			, Bool_.Y, app.Fsys_mgr().Root_dir().To_http_file_bry());
		wtr.Write_js_var(Var_xowa_mode_is_server	, Bool_.N, app.Tcp_server().Running() ? Bool_.True_bry : Bool_.False_bry);
	}
	public void Write_js_tail_script(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr) {}
	public void Write_js_head_global(Xoa_app app, Xow_wiki wiki, Xoa_page page, Xoh_module_wtr wtr) {
		if (!enabled) return;
		wtr.Write_js_global_ini_atr_msg(wiki, Key_sort_ascending);
		wtr.Write_js_global_ini_atr_msg(wiki, Key_sort_descending);
		Xol_lang lang = wiki.Lang(); Xow_msg_mgr msg_mgr = wiki.Msg_mgr();
		Bry_bfr bfr = lang.App().Utl_bry_bfr_mkr().Get_b512();
		byte[] months_long = Html_js_table_months(bfr, msg_mgr, Xol_msg_itm_.Id_dte_month_name_january, Xol_msg_itm_.Id_dte_month_name_december);
		byte[] months_short = Html_js_table_months(bfr, msg_mgr, Xol_msg_itm_.Id_dte_month_abrv_jan, Xol_msg_itm_.Id_dte_month_abrv_dec);
		byte[] num_format_separators = Html_js_table_num_format_separators(bfr, lang.Num_mgr().Separators_mgr());
		bfr.Mkr_rls();
		wtr.Write_js_global_ini_atr_val(Key_wgContentLanguage			, lang.Key_bry());
		wtr.Write_js_global_ini_atr_obj(Key_wgSeparatorTransformTable	, num_format_separators);
		wtr.Write_js_global_ini_atr_obj(Key_wgDigitTransformTable		, Num_format_digits);
		wtr.Write_js_global_ini_atr_val(Key_wgDefaultDateFormat			, Date_format_default);
		wtr.Write_js_global_ini_atr_obj(Key_wgMonthNames				, months_long);
		wtr.Write_js_global_ini_atr_obj(Key_wgMonthNamesShort			, months_short);
	}
	private static final byte[] 
	  Key_sort_descending				= Bry_.new_ascii_("sort-descending")
	, Key_sort_ascending				= Bry_.new_ascii_("sort-ascending")
	, Key_wgContentLanguage				= Bry_.new_ascii_("wgContentLanguage")
	, Key_wgSeparatorTransformTable		= Bry_.new_ascii_("wgSeparatorTransformTable")
	, Key_wgDigitTransformTable			= Bry_.new_ascii_("wgDigitTransformTable")
	, Key_wgDefaultDateFormat			= Bry_.new_ascii_("wgDefaultDateFormat")
	, Key_wgMonthNames					= Bry_.new_ascii_("wgMonthNames")
	, Key_wgMonthNamesShort				= Bry_.new_ascii_("wgMonthNamesShort")
	;
	private static byte[] Html_js_table_months(Bry_bfr bfr, Xow_msg_mgr msg_mgr, int january_id, int december_id) {// ['', 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
		bfr.Add_byte(Byte_ascii.Brack_bgn).Add_byte(Byte_ascii.Apos).Add_byte(Byte_ascii.Apos);
		for (int i = january_id; i <= december_id; i++) {
			bfr.Add_byte(Byte_ascii.Comma).Add_byte(Byte_ascii.Space).Add_byte(Byte_ascii.Apos);
			bfr.Add(msg_mgr.Val_by_id(i));
			bfr.Add_byte(Byte_ascii.Apos);
		}
		bfr.Add_byte(Byte_ascii.Brack_end);
		return bfr.XtoAryAndClear();
	}
	private static byte[] Html_js_table_num_format_separators(Bry_bfr bfr, Xol_transform_mgr separator_mgr) {
		byte[] dec_spr = separator_mgr.Get_val_or_self(Xol_num_mgr.Separators_key__dec);
		bfr.Add_byte(Byte_ascii.Brack_bgn)							.Add_byte(Byte_ascii.Apos).Add(dec_spr).Add_byte(Byte_ascii.Tab).Add_byte(Byte_ascii.Dot).Add_byte(Byte_ascii.Apos);
		byte[] grp_spr = separator_mgr.Get_val_or_self(Xol_num_mgr.Separators_key__grp);
		bfr.Add_byte(Byte_ascii.Comma).Add_byte(Byte_ascii.Space)	.Add_byte(Byte_ascii.Apos).Add(grp_spr).Add_byte(Byte_ascii.Tab).Add_byte(Byte_ascii.Comma).Add_byte(Byte_ascii.Apos);
		bfr.Add_byte(Byte_ascii.Brack_end);
		return bfr.XtoAryAndClear();
	}
	private static final byte[]
	  Date_format_default			= Bry_.new_ascii_("dmy")
	, Num_format_digits				= Bry_.new_ascii_("['', '']")
	, Var_xowa_root_dir				= Bry_.new_ascii_("xowa_root_dir")
	, Var_xowa_mode_is_server		= Bry_.new_ascii_("xowa_mode_is_server")
	;
	private static byte[] Url_core;
}
