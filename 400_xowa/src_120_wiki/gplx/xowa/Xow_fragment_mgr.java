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
	public Xow_fragment_mgr(Xowe_wiki wiki) {this.wiki = wiki;} private Xowe_wiki wiki;
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
		if		(ctx.Match(k, Invk_html_js_edit_toolbar_fmt_))	html_js_edit_toolbar_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_html_js_edit_toolbar))		return html_js_edit_toolbar;
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static final String Invk_html_js_edit_toolbar_fmt_ = "html_js_edit_toolbar_fmt_", Invk_html_js_edit_toolbar = "html_js_edit_toolbar";
	public void Evt_lang_changed(Xol_lang lang) {
		Bry_bfr bfr = Xoa_app_.Utl__bfr_mkr().Get_b512();
		Xow_msg_mgr msg_mgr = wiki.Appe().Usere().Msg_mgr();
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
}
