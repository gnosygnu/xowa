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
package gplx.xowa; import gplx.*;
import gplx.xowa.langs.numbers.*;
public class Xow_fragment_mgr implements GfoInvkAble {
	private static final byte[] Date_format_default = Bry_.new_ascii_("dmy");
	private static final byte[] Num_format_digits	 = Bry_.new_ascii_("['', '']");
	public byte[] Html_js_table() {return html_js_table;} private byte[] html_js_table;
	public Xow_fragment_mgr(Xow_wiki wiki) {this.wiki = wiki;} private Xow_wiki wiki;
	private Bry_fmtr html_js_table_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl
		(	"  var xowa_global_values = {"
		,	"    'collapsible-collapse'         : '~{collapsible-collapse}',"
		,	"    'collapsible-expand'           : '~{collapsible-expand}',"
		,	"    'sort-descending'              : '~{sort-descending}',"
		,	"    'sort-ascending'               : '~{sort-ascending}',"
		,	"    'wgContentLanguage'            : '~{wgContentLanguage}',"
		,	"    'wgSeparatorTransformTable'    : ~{wgSeparatorTransformTable},"
		,	"    'wgDigitTransformTable'        : ~{wgDigitTransformTable},"
		,	"    'wgDefaultDateFormat'          : '~{wgDefaultDateFormat}',"
		,	"    'wgMonthNames'                 : ~{wgMonthNames},"
		,	"    'wgMonthNamesShort'            : ~{wgMonthNamesShort},"
		,	"  };"
		),	"collapsible-collapse", "collapsible-expand", "sort-descending", "sort-ascending", "wgContentLanguage", "wgSeparatorTransformTable", "wgDigitTransformTable", "wgDefaultDateFormat", "wgMonthNames", "wgMonthNamesShort");
	public byte[] Html_js_edit_toolbar() {return html_js_edit_toolbar;} private byte[] html_js_edit_toolbar;
	private Bry_fmtr html_js_edit_toolbar_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl
		(	"  var xowa_edit_i18n = {"
		,	"    'bold_tip'             : '~{bold_tip}',"
		,	"    'bold_sample'          : '~{bold_sample}',"
		,	"    'italic_tip'           : '~{italic_tip}',"
		,	"    'italic_sample'        : '~{italic_sample}',"
		,	"    'link_tip'             : '~{link_tip}',"
		,	"    'link_sample'          : '~{link_sample}',"
		,	"    'headline_tip'         : '~{headline_tip}',"
		,	"    'headline_sample'      : '~{headline_sample}',"
		,	"    'ulist_tip'            : '~{ulist_tip}',"
		,	"    'ulist_sample'         : '~{ulist_sample}',"
		,	"    'olist_tip'            : '~{olist_tip}',"
		,	"    'olist_sample'         : '~{olist_sample}'"
		,	"  };"
		),	"bold_tip", "bold_sample", "italic_tip", "italic_sample", "link_tip", "link_sample", "headline_tip", "headline_sample", "ulist_tip", "ulist_sample", "olist_tip", "olist_sample");
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_html_js_table_fmt_))			html_js_table_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_html_js_table))				return html_js_table;
		else if	(ctx.Match(k, Invk_html_js_edit_toolbar_fmt_))	html_js_edit_toolbar_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_html_js_edit_toolbar))		return html_js_edit_toolbar;
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static final String Invk_html_js_table_fmt_ = "html_js_table_fmt_", Invk_html_js_table = "html_js_table", Invk_html_js_edit_toolbar_fmt_ = "html_js_edit_toolbar_fmt_", Invk_html_js_edit_toolbar = "html_js_edit_toolbar";
	public void Evt_lang_changed(Xol_lang lang) {
		Bry_bfr bfr = lang.App().Utl_bry_bfr_mkr().Get_b512();
		Xow_msg_mgr msg_mgr = wiki.Msg_mgr();
		byte[] months_long = Html_js_table_months(bfr, msg_mgr, Xol_msg_itm_.Id_dte_month_name_january, Xol_msg_itm_.Id_dte_month_name_december);
		byte[] months_short = Html_js_table_months(bfr, msg_mgr, Xol_msg_itm_.Id_dte_month_abrv_jan, Xol_msg_itm_.Id_dte_month_abrv_dec);
		byte[] num_format_separators = Html_js_table_num_format_separators(bfr, lang.Num_mgr().Separators_mgr());
		html_js_table = html_js_table_fmtr.Bld_bry_many(bfr
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_js_tables_collapsible_collapse)
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_js_tables_collapsible_expand)
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_js_tables_sort_descending)
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_js_tables_sort_ascending)
			, lang.Key_bry()
			, num_format_separators
			, Num_format_digits
			, Date_format_default
			, months_long
			, months_short
			);
		msg_mgr = wiki.App().User().Msg_mgr();
		html_js_edit_toolbar = html_js_edit_toolbar_fmtr.Bld_bry_many(bfr
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_edit_toolbar_bold_tip)
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_edit_toolbar_bold_sample)
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_edit_toolbar_italic_tip)
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_edit_toolbar_italic_sample)
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_edit_toolbar_link_tip)
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_edit_toolbar_link_sample)
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_edit_toolbar_headline_tip)
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_edit_toolbar_headline_sample)
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_edit_toolbar_ulist_tip)
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_edit_toolbar_ulist_sample)
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_edit_toolbar_olist_tip)
			, msg_mgr.Val_by_id(Xol_msg_itm_.Id_edit_toolbar_olist_sample)
			);
		bfr.Mkr_rls();
	}
	byte[] Html_js_table_months(Bry_bfr bfr, Xow_msg_mgr msg_mgr, int january_id, int december_id) {
		// ['', 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
		bfr.Add_byte(Byte_ascii.Brack_bgn).Add_byte(Byte_ascii.Apos).Add_byte(Byte_ascii.Apos);
		for (int i = january_id; i <= december_id; i++) {
			bfr.Add_byte(Byte_ascii.Comma).Add_byte(Byte_ascii.Space).Add_byte(Byte_ascii.Apos);
			bfr.Add(msg_mgr.Val_by_id(i));
			bfr.Add_byte(Byte_ascii.Apos);
		}
		bfr.Add_byte(Byte_ascii.Brack_end);
		return bfr.XtoAryAndClear();
	}
	private byte[] Html_js_table_num_format_separators(Bry_bfr bfr, Xol_transform_mgr separator_mgr) {
		byte[] dec_spr = separator_mgr.Get_val_or_self(Xol_num_mgr.Separators_key__dec);
		bfr.Add_byte(Byte_ascii.Brack_bgn)							.Add_byte(Byte_ascii.Apos).Add(dec_spr).Add_byte(Byte_ascii.Tab).Add_byte(Byte_ascii.Dot).Add_byte(Byte_ascii.Apos);
		byte[] grp_spr = separator_mgr.Get_val_or_self(Xol_num_mgr.Separators_key__grp);
		bfr.Add_byte(Byte_ascii.Comma).Add_byte(Byte_ascii.Space)	.Add_byte(Byte_ascii.Apos).Add(grp_spr).Add_byte(Byte_ascii.Tab).Add_byte(Byte_ascii.Comma).Add_byte(Byte_ascii.Apos);
		bfr.Add_byte(Byte_ascii.Brack_end);
		return bfr.XtoAryAndClear();
	}
}
