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
import gplx.core.brys.fmtrs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*; import gplx.xowa.langs.numbers.*;
public class Xow_fragment_mgr implements Gfo_invk {
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
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Invk_html_js_edit_toolbar_fmt_ = "html_js_edit_toolbar_fmt_", Invk_html_js_edit_toolbar = "html_js_edit_toolbar";
	public void Evt_lang_changed(Xol_lang_itm lang) {
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
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
