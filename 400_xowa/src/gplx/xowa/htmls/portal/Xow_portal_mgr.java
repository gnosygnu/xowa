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
package gplx.xowa.htmls.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.brys.*; import gplx.core.brys.fmtrs.*; import gplx.core.brys.fmts.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.guis.*; import gplx.xowa.addons.htmls.sidebars.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.domains.*; 
import gplx.xowa.htmls.core.htmls.*; import gplx.langs.htmls.encoders.*; import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.apps.apis.xowa.html.*;
import gplx.xowa.langs.vnts.*; import gplx.xowa.htmls.portal.vnts.*;
import gplx.xowa.parsers.amps.*;
public class Xow_portal_mgr implements Gfo_invk {
	private Xowe_wiki wiki; private boolean lang_is_rtl; private Xoapi_toggle_itm toggle_itm;
	private final    Vnt_mnu_grp_fmtr vnt_menu_fmtr = new Vnt_mnu_grp_fmtr();
	private final    Gfo_url_encoder fsys_lnx_encoder = Gfo_url_encoder_.New__fsys_lnx().Make();
	private boolean sidebar_enabled;
	public Xow_portal_mgr(Xowe_wiki wiki) {
		this.wiki = wiki;
		this.sidebar_mgr = new Xoh_sidebar_mgr(wiki);
		this.missing_ns_cls = Bry_.Eq(wiki.Domain_bry(), Xow_domain_tid_.Bry__home) ? Missing_ns_cls_hide : null;	// if home wiki, set missing_ns to application default; if any other wiki, set to null; will be overriden during init
	}
	public byte[] Missing_ns_cls() {return missing_ns_cls;} public Xow_portal_mgr Missing_ns_cls_(byte[] v) {missing_ns_cls = v; return this;} private byte[] missing_ns_cls;	// NOTE: must be null due to Init check above
	public Xoh_sidebar_mgr Sidebar_mgr() {return sidebar_mgr;} private Xoh_sidebar_mgr sidebar_mgr;
	public Bry_fmtr Div_home_fmtr() {return div_home_fmtr;} private Bry_fmtr div_home_fmtr = Bry_fmtr.new_("");
	public Xow_portal_mgr Init_assert() {if (init_needed) Init(); return this;}
	public byte[] Div_jump_to() {return div_jump_to;} private byte[] div_jump_to = Bry_.Empty;
	public void Init_by_lang(Xol_lang_itm lang) {
		lang_is_rtl = !lang.Dir_ltr();
	}
	public void Init_by_wiki(Xowe_wiki wiki) {
		wiki.App().Cfg().Bind_many_wiki(this, wiki, Cfg__divs__footer, Cfg__missing_class, Cfg__sidebar_enabled__desktop, Cfg__sidebar_enabled__server);
	}
	private void Sidebar_enabled_(boolean is_desktop, boolean val) {
		// set sidebar_enabled if (a) is_gui and is_desktop; or (b) is_server and !is_desktop
		if (wiki.App().Mode().Tid_is_gui()) {
			if (is_desktop)
				this.sidebar_enabled = val;
		}
		else {
			if (!is_desktop)
				this.sidebar_enabled = val;
		}
	}
	public void Init() {
		init_needed = false;
		if (missing_ns_cls == null)	{// if missing_ns_cls not set for wiki, use the home wiki's
			Missing_ns_cls_(wiki.Appe().Usere().Wiki().Html_mgr().Portal_mgr().Missing_ns_cls());
		}
		Bry_fmtr_eval_mgr eval_mgr = wiki.Eval_mgr();
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		Init_fmtr(tmp_bfr, eval_mgr, div_view_fmtr);
		Init_fmtr(tmp_bfr, eval_mgr, div_ns_fmtr);
		byte[] wiki_user_name = wiki.User().Name();
		div_personal_bry = Init_fmtr(tmp_bfr, eval_mgr, div_personal_fmtr, Bry_.Add(Xoh_href_.Bry__wiki, wiki.Ns_mgr().Ids_get_or_null(Xow_ns_.Tid__user).Name_db_w_colon(), wiki_user_name), wiki_user_name, Ns_cls_by_id(wiki.Ns_mgr(), Xow_ns_.Tid__user), Bry_.Add(Xoh_href_.Bry__wiki, wiki.Ns_mgr().Ids_get_or_null(Xow_ns_.Tid__user_talk).Name_db_w_colon(), wiki_user_name), Ns_cls_by_id(wiki.Ns_mgr(), Xow_ns_.Tid__user_talk));
		byte[] main_page_href_bry = tmp_bfr.Add(Xoh_href_.Bry__site).Add(wiki.Domain_bry()).Add(Xoh_href_.Bry__wiki).To_bry_and_clear();	// NOTE: build /site/en.wikipedia.org/wiki/ href; no Main_Page, as that will be inserted by Xoh_href_parser

		// logo
		Io_url wiki_css_dir = wiki.Appe().Usere().Fsys_mgr().Wiki_root_dir().GenSubDir_nest(wiki.Domain_str(), "html");
		div_logo_day = Init_fmtr(tmp_bfr, eval_mgr, div_logo_fmtr, main_page_href_bry, fsys_lnx_encoder.Encode_to_file_protocol(wiki_css_dir.GenSubFil_nest("logo.png")));

		// night-mode logo; check if wiki-version exists, else use app-version
		Io_url night_logo = wiki.App().Fsys_mgr().Url_finder().Find_by_css_or(wiki.Domain_str(), "logo_night.png", String_.Ary("bin", "any", "xowa", "html", "css", "nightmode"), true);
		div_logo_night = Init_fmtr(tmp_bfr, eval_mgr, div_logo_fmtr, main_page_href_bry, fsys_lnx_encoder.Encode_to_file_protocol(night_logo));

		div_home_bry = Init_fmtr(tmp_bfr, eval_mgr, div_home_fmtr);
		div_wikis_fmtr.Eval_mgr_(eval_mgr);
		Xow_msg_mgr msg_mgr = wiki.Msg_mgr();
		div_jump_to = Div_jump_to_fmtr.Bld_bry_many(tmp_bfr, msg_mgr.Val_by_key_obj("jumpto"), msg_mgr.Val_by_key_obj("jumptonavigation"), msg_mgr.Val_by_key_obj("comma-separator"), msg_mgr.Val_by_key_obj("jumptosearch"));
		tmp_bfr.Mkr_rls();
		sidebar_mgr.Init_by_wiki();
	}	private boolean init_needed = true;
	private byte[] Init_fmtr(Bry_bfr tmp_bfr, Bry_fmtr_eval_mgr eval_mgr, Bry_fmtr fmtr, Object... fmt_args) {
		fmtr.Eval_mgr_(eval_mgr);
		fmtr.Bld_bfr_many(tmp_bfr, fmt_args);
		return tmp_bfr.To_bry_and_clear();
	}

	// div_footer
	private Bry_fmtr div_footer_fmtr = Bry_fmtr.keys_("page_modified_on_msg", "app_version", "app_build_date");
	private byte[] div_footer_bry = Bry_.Empty;
	public byte[] Div_footer(byte[] page_modified_on_msg, String app_version, String app_build_date) {
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		div_footer_bry = Init_fmtr(tmp_bfr, wiki.Eval_mgr(), div_footer_fmtr, page_modified_on_msg, app_version, app_build_date);
		return div_footer_bry;
	}

	public byte[] Div_personal_bry() {return div_personal_bry;} private byte[] div_personal_bry = Bry_.Empty;
	public byte[] Div_ns_bry(Bry_bfr_mkr bfr_mkr, Xoa_ttl ttl, Xow_ns_mgr ns_mgr) {
		Xow_ns ns = ttl.Ns();
		byte[] subj_cls = Ns_cls_by_ord(ns_mgr, ns.Ord_subj_id()), talk_cls = Ns_cls_by_ord(ns_mgr, ns.Ord_talk_id());
		if		(ns.Id_is_talk())
			talk_cls = Xow_portal_mgr.Cls_selected_y;
		else
			subj_cls = Xow_portal_mgr.Cls_selected_y;
		Bfr_arg vnt_menu = null;
		Xol_vnt_mgr vnt_mgr = wiki.Lang().Vnt_mgr();	// VNT; DATE:2015-03-03
		if (vnt_mgr.Enabled()) {
			vnt_menu_fmtr.Init(vnt_mgr.Regy(), wiki.Domain_bry(), ttl.Full_db(), vnt_mgr.Cur_itm().Key());
			vnt_menu = wiki.Lang().Vnt_mgr().Enabled() ? vnt_menu_fmtr : null;
		}

		// NOTE: need to escape args href for Search page b/c user can enter in quotes and apos; EX:localhost:8080/en.wikipedia.org/wiki/Special:XowaSearch?search=title:(%2Breturn%20%2B"abc") ; DATE:2017-07-16
		Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
		byte[] subj_href = Xoh_html_wtr_escaper.Escape(Xop_amp_mgr.Instance, tmp_bfr, Bry_.Add(Xoh_href_.Bry__wiki, ttl.Subj_txt()));
		byte[] talk_href = Xoh_html_wtr_escaper.Escape(Xop_amp_mgr.Instance, tmp_bfr, Bry_.Add(Xoh_href_.Bry__wiki, ttl.Talk_txt()));

		div_ns_fmtr.Bld_bfr_many(tmp_bfr, subj_href, subj_cls, talk_href, talk_cls, vnt_menu);
		return tmp_bfr.To_bry_and_rls();
	}
	private byte[] Ns_cls_by_ord(Xow_ns_mgr ns_mgr, int ns_ord) {
		Xow_ns ns = ns_mgr.Ords_get_at(ns_ord);
		return ns == null || ns.Exists() ? Bry_.Empty : missing_ns_cls;
	}
	private byte[] Ns_cls_by_id(Xow_ns_mgr ns_mgr, int ns_id) {
		Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id);
		return ns == null || ns.Exists() ? Bry_.Empty : missing_ns_cls;			
	}
	public byte[] Div_view_bry(Bry_bfr_mkr bfr_mkr, byte output_tid, byte[] search_text) {
		Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
		byte[] read_cls = Bry_.Empty, edit_cls = Bry_.Empty, html_cls = Bry_.Empty;
		switch (output_tid) {
			case Xopg_page_.Tid_read: read_cls = Cls_selected_y; break;
			case Xopg_page_.Tid_edit: edit_cls = Cls_selected_y; break;
			case Xopg_page_.Tid_html: html_cls = Cls_selected_y; break;
		}
		div_view_fmtr.Bld_bfr_many(tmp_bfr, read_cls, edit_cls, html_cls, search_text);
		return tmp_bfr.To_bry_and_rls();
	}	public static final    byte[] Cls_selected_y = Bry_.new_a7("selected"), Cls_new = Bry_.new_a7("new"), Cls_display_none = Bry_.new_a7("xowa_display_none");
	public byte[] Div_logo_bry(boolean nightmode) {return nightmode ? div_logo_night : div_logo_day;} private byte[] div_logo_day = Bry_.Empty, div_logo_night = Bry_.Empty;
	public byte[] Div_home_bry() {return sidebar_enabled ? div_home_bry : Bry_.Empty;} private byte[] div_home_bry = Bry_.Empty;
	public byte[] Div_sync_bry(Bry_bfr tmp_bfr, boolean manual_enabled, Xow_wiki wiki, Xoa_page page) {
		// only show update_html if wmf; DATE:2016-08-31
		if (	wiki.Domain_itm().Domain_type().Src() == Xow_domain_tid.Src__wmf
			&&	manual_enabled) {
			div_sync_fmtr.Bld_bfr_many(tmp_bfr, page.Ttl().Full_url());
			return tmp_bfr.To_bry_and_clear();
		}
		return Bry_.Empty;
	}
	public byte[] Div_wikis_bry(Bry_bfr_mkr bfr_mkr) {
		if (toggle_itm == null)	// TEST:lazy-new b/c Init_by_wiki
			toggle_itm = wiki.Appe().Api_root().Html().Page().Toggle_mgr().Get_or_new("offline-wikis").Init(Bry_.new_a7("Wikis"));
		Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
		div_wikis_fmtr.Bld_bfr_many(tmp_bfr, toggle_itm.Html_toggle_btn(), toggle_itm.Html_toggle_hdr());
		return tmp_bfr.To_bry_and_rls();
	}
	private final    Bry_fmtr 
	  div_personal_fmtr = Bry_fmtr.new_("~{portal_personal_subj_href};~{portal_personal_subj_text};~{portal_personal_talk_cls};~{portal_personal_talk_href};~{portal_personal_talk_cls};", "portal_personal_subj_href", "portal_personal_subj_text", "portal_personal_subj_cls", "portal_personal_talk_href", "portal_personal_talk_cls")
	, div_ns_fmtr = Bry_fmtr.new_("~{portal_ns_subj_href};~{portal_ns_subj_cls};~{portal_ns_talk_href};~{portal_ns_talk_cls};~{portal_div_vnts}", "portal_ns_subj_href", "portal_ns_subj_cls", "portal_ns_talk_href", "portal_ns_talk_cls", "portal_div_vnts")
	, div_view_fmtr = Bry_fmtr.new_("", "portal_view_read_cls", "portal_view_edit_cls", "portal_view_html_cls", "search_text")
	, div_logo_fmtr = Bry_fmtr.new_("", "portal_nav_main_href", "portal_logo_url")
	, div_sync_fmtr = Bry_fmtr.new_("", "page_url")
	, div_wikis_fmtr = Bry_fmtr.new_("", "toggle_btn", "toggle_hdr")
	;
	public Bry_fmtr Div_logo_fmtr() {return div_logo_fmtr;} // TEST:
	private byte[] Reverse_li(byte[] bry) {
		return lang_is_rtl ? Xoh_rtl_utl.Reverse_li(bry) : bry;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_div_personal_))					div_personal_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_div_ns_))						div_ns_fmtr.Fmt_(Reverse_li(m.ReadBry("v")));
		else if	(ctx.Match(k, Invk_div_view_))						div_view_fmtr.Fmt_(Reverse_li(m.ReadBry("v")));
		else if	(ctx.Match(k, Invk_div_logo_))						div_logo_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_div_home_))						div_home_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_div_sync_))						div_sync_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_div_wikis_))						div_wikis_fmtr.Fmt_(m.ReadBry("v"));

		else if (ctx.Match(k, Cfg__missing_class))					missing_ns_cls = m.ReadBry("v");
		else if (ctx.Match(k, Cfg__sidebar_enabled__desktop))		Sidebar_enabled_(Bool_.Y, m.ReadYn("v"));
		else if (ctx.Match(k, Cfg__sidebar_enabled__server))		Sidebar_enabled_(Bool_.N, m.ReadYn("v"));
		else if (ctx.Match(k, Cfg__divs__footer))					div_footer_fmtr.Fmt_(m.ReadBry("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_div_personal_ = "div_personal_", Invk_div_view_ = "div_view_", Invk_div_ns_ = "div_ns_", Invk_div_home_ = "div_home_"
	, Invk_div_sync_ = "div_sync_", Invk_div_wikis_ = "div_wikis_";
	public static final String Invk_div_logo_ = "div_logo_";
	private static final    byte[] Missing_ns_cls_hide = Bry_.new_a7("xowa_display_none");
	private static final    Bry_fmtr Div_jump_to_fmtr = Bry_fmtr.new_
	( "\n    <div id=\"jump-to-nav\" class=\"mw-jump\">~{jumpto}<a href=\"#mw-navigation\">~{jumptonavigation}</a>~{comma-separator}<a href=\"#p-search\">~{jumptosearch}</a></div>"
	, "jumpto", "jumptonavigation", "comma-separator", "jumptosearch");

	private static final String
	  Cfg__missing_class				= "xowa.html.portal.missing_class"
	, Cfg__sidebar_enabled__desktop		= "xowa.html.portal.sidebar_enabled_desktop"
	, Cfg__sidebar_enabled__server		= "xowa.html.portal.sidebar_enabled_server"
	, Cfg__divs__footer					= "xowa.html.divs.footer"
	;
}
