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
package gplx.xowa.htmls.portal;

import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.BryBfrMkr;
import gplx.types.custom.brys.fmts.fmtrs.BryFmtr;
import gplx.types.custom.brys.fmts.fmtrs.BryFmtrEvalMgr;
import gplx.langs.htmls.encoders.Gfo_url_encoder;
import gplx.langs.htmls.encoders.Gfo_url_encoder_;
import gplx.xowa.Xoa_page;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoa_url_;
import gplx.xowa.Xow_wiki;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.addons.htmls.sidebars.Xoh_sidebar_mgr;
import gplx.xowa.apps.apis.xowa.html.Xoapi_toggle_itm;
import gplx.xowa.htmls.hrefs.Xoh_href_;
import gplx.xowa.htmls.hrefs.Xoh_href_wtr;
import gplx.xowa.htmls.portal.vnts.Vnt_mnu_grp_fmtr;
import gplx.xowa.langs.Xol_lang_itm;
import gplx.xowa.langs.msgs.Xow_msg_mgr;
import gplx.xowa.langs.vnts.Xol_vnt_mgr;
import gplx.xowa.wikis.domains.Xow_domain_tid;
import gplx.xowa.wikis.domains.Xow_domain_tid_;
import gplx.xowa.wikis.nss.Xow_ns;
import gplx.xowa.wikis.nss.Xow_ns_;
import gplx.xowa.wikis.nss.Xow_ns_mgr;
import gplx.xowa.wikis.pages.Xopg_view_mode_;
public class Xow_portal_mgr implements Gfo_invk {
	private Xowe_wiki wiki; private boolean lang_is_rtl; private Xoapi_toggle_itm toggle_itm;
	private final Vnt_mnu_grp_fmtr vnt_menu_fmtr = new Vnt_mnu_grp_fmtr();
	private final Gfo_url_encoder fsys_lnx_encoder = Gfo_url_encoder_.New__fsys_lnx().Make();
	private boolean sidebar_enabled;
	private boolean indicators_pagesource_enabled = false;
	private byte[]
	  indicators_pagesource_wtxt = BryUtl.NewA7("<ul><li>WIKITEXT</li></ul>")
	, indicators_pagesource_html = BryUtl.NewA7("<ul><li>HTML</li></ul>")
	;
	public Xow_portal_mgr(Xowe_wiki wiki) {
		this.wiki = wiki;
		this.sidebar_mgr = new Xoh_sidebar_mgr(wiki);
		this.missing_ns_cls = BryLni.Eq(wiki.Domain_bry(), Xow_domain_tid_.Bry__home) ? Missing_ns_cls_hide : null;	// if home wiki, set missing_ns to application default; if any other wiki, set to null; will be overriden during init
	}
	public byte[] Missing_ns_cls() {return missing_ns_cls;} public Xow_portal_mgr Missing_ns_cls_(byte[] v) {missing_ns_cls = v; return this;} private byte[] missing_ns_cls;	// NOTE: must be null due to Init check above
	public Xoh_sidebar_mgr Sidebar_mgr() {return sidebar_mgr;} private Xoh_sidebar_mgr sidebar_mgr;
	public BryFmtr Div_home_fmtr() {return div_home_fmtr;} private BryFmtr div_home_fmtr = BryFmtr.New("");
	public Xow_portal_mgr Init_assert() {if (init_needed) Init(); return this;}
	public byte[] Div_jump_to() {return div_jump_to;} private byte[] div_jump_to = BryUtl.Empty;
	public void Init_by_lang(Xol_lang_itm lang) {
		lang_is_rtl = !lang.Dir_ltr();
	}
	public void Init_by_wiki(Xowe_wiki wiki) {
		wiki.App().Cfg().Bind_many_wiki(this, wiki
			, Cfg__divs__footer, Cfg__missing_class
			, Cfg__sidebar_enabled__desktop, Cfg__sidebar_enabled__server
			, Cfg__hdumps_indicators_enabled, Cfg__hdumps_indicators_wtxt, Cfg__hdumps_indicators_html
			);
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
		BryFmtrEvalMgr eval_mgr = wiki.Eval_mgr();
		BryWtr tmp_bfr = wiki.Utl__bfr_mkr().GetB512();
		Init_fmtr(tmp_bfr, eval_mgr, div_view_fmtr);
		Init_fmtr(tmp_bfr, eval_mgr, div_ns_fmtr);

		// logo
		byte[] main_page_href_bry = tmp_bfr.Add(Xoh_href_.Bry__site).Add(wiki.Domain_bry()).Add(Xoh_href_.Bry__wiki).ToBryAndClear();	// NOTE: build /site/en.wikipedia.org/wiki/ href; no Main_Page, as that will be inserted by Xoh_href_parser
		Io_url wiki_css_dir = wiki.Appe().Usere().Fsys_mgr().Wiki_root_dir().GenSubDir_nest(wiki.Domain_str(), "html");
		div_logo_day = Init_fmtr(tmp_bfr, eval_mgr, div_logo_fmtr, main_page_href_bry, fsys_lnx_encoder.Encode_to_file_protocol(wiki_css_dir.GenSubFil_nest("logo.png")));

		// night-mode logo; check if wiki-version exists, else use app-version
		Io_url night_logo = wiki.App().Fsys_mgr().Url_finder().Find_by_css_or(wiki.Domain_str(), "logo_night.png", StringUtl.Ary("bin", "any", "xowa", "html", "css", "nightmode"), true);
		div_logo_night = Init_fmtr(tmp_bfr, eval_mgr, div_logo_fmtr, main_page_href_bry, fsys_lnx_encoder.Encode_to_file_protocol(night_logo));

		div_home_bry = Init_fmtr(tmp_bfr, eval_mgr, div_home_fmtr);
		div_wikis_fmtr.EvalMgrSet(eval_mgr);
		Xow_msg_mgr msg_mgr = wiki.Msg_mgr();
		div_jump_to = Div_jump_to_fmtr.BldToBryMany(tmp_bfr, msg_mgr.Val_by_key_obj("jumpto"), msg_mgr.Val_by_key_obj("jumptonavigation"), msg_mgr.Val_by_key_obj("jumptosearch"));
		tmp_bfr.MkrRls();
		sidebar_mgr.Init_by_wiki();
	}	private boolean init_needed = true;
	private byte[] Init_fmtr(BryWtr tmp_bfr, BryFmtrEvalMgr eval_mgr, BryFmtr fmtr, Object... fmt_args) {
		fmtr.EvalMgrSet(eval_mgr);
		fmtr.BldToBfrMany(tmp_bfr, fmt_args);
		return tmp_bfr.ToBryAndClear();
	}

	// div_footer
	private BryFmtr div_footer_fmtr = BryFmtr.NewKeys("page_modified_on_msg", "app_version", "app_build_date");
	private byte[] div_footer_bry = BryUtl.Empty;
	public byte[] Div_footer(byte[] page_modified_on_msg, String app_version, String app_build_date) {
		BryWtr tmp_bfr = BryWtr.New();
		div_footer_bry = Init_fmtr(tmp_bfr, wiki.Eval_mgr(), div_footer_fmtr, page_modified_on_msg, app_version, app_build_date);
		return div_footer_bry;
	}

	public byte[] Div_personal_bry(boolean from_hdump) {
		BryWtr tmp_bfr = wiki.Utl__bfr_mkr().GetB512();
		Xoh_href_wtr href_wtr = wiki.Html__href_wtr();
		byte[] wiki_user_name = wiki.User().Name();
		byte[] user_href = href_wtr.Build_to_bry(wiki, BryUtl.Add(wiki.Ns_mgr().Ids_get_or_null(Xow_ns_.Tid__user).Name_db_w_colon(), wiki_user_name));
		byte[] talk_href = href_wtr.Build_to_bry(wiki, BryUtl.Add(wiki.Ns_mgr().Ids_get_or_null(Xow_ns_.Tid__user_talk).Name_db_w_colon(), wiki_user_name));

		byte[] rv = Init_fmtr(tmp_bfr, wiki.Eval_mgr(), div_personal_fmtr
			, user_href
			, wiki_user_name
			, Ns_cls_by_id(wiki.Ns_mgr(), Xow_ns_.Tid__user)
			, talk_href
			, Ns_cls_by_id(wiki.Ns_mgr(), Xow_ns_.Tid__user_talk)
			, indicators_pagesource_enabled
				? (from_hdump ? indicators_pagesource_html : indicators_pagesource_wtxt)
				: BryUtl.Empty
			);
		tmp_bfr.MkrRls();
		return rv;
	}
	public byte[] Div_ns_bry(BryBfrMkr bfr_mkr, Xoa_ttl ttl, Xow_ns_mgr ns_mgr) {
		Xow_ns ns = ttl.Ns();
		byte[] subj_cls = Ns_cls_by_ord(ns_mgr, ns.Ord_subj_id()), talk_cls = Ns_cls_by_ord(ns_mgr, ns.Ord_talk_id());
		if		(ns.Id_is_talk())
			talk_cls = Xow_portal_mgr.Cls_selected_y;
		else
			subj_cls = Xow_portal_mgr.Cls_selected_y;
		BryBfrArg vnt_menu = null;
		Xol_vnt_mgr vnt_mgr = wiki.Lang().Vnt_mgr();	// VNT; DATE:2015-03-03
		if (vnt_mgr.Enabled()) {
			vnt_menu_fmtr.Init(vnt_mgr.Regy(), wiki.Domain_bry(), ttl.Full_db(), vnt_mgr.Cur_itm().Key());
			vnt_menu = wiki.Lang().Vnt_mgr().Enabled() ? vnt_menu_fmtr : null;
		}

		// NOTE: need to escape args href for Search page b/c user can enter in quotes and apos; EX:localhost:8080/en.wikipedia.org/wiki/Special:XowaSearch?search=title:(%2Breturn%20%2B"abc") ; DATE:2017-07-16
		Xoh_href_wtr href_wtr = wiki.Html__href_wtr();
		BryWtr tmp_bfr = bfr_mkr.GetK004();
		byte[] subj_href = href_wtr.Build_to_bry(wiki, ttl.Subj_txt());
		byte[] talk_href = href_wtr.Build_to_bry(wiki, ttl.Talk_txt());

		div_ns_fmtr.BldToBfrMany(tmp_bfr, subj_href, subj_cls, talk_href, talk_cls, vnt_menu);
		return tmp_bfr.ToBryAndRls();
	}
	private byte[] Ns_cls_by_ord(Xow_ns_mgr ns_mgr, int ns_ord) {
		Xow_ns ns = ns_mgr.Ords_get_at(ns_ord);
		return ns == null || ns.Exists() ? BryUtl.Empty : missing_ns_cls;
	}
	private byte[] Ns_cls_by_id(Xow_ns_mgr ns_mgr, int ns_id) {
		Xow_ns ns = ns_mgr.Ids_get_or_null(ns_id);
		return ns == null || ns.Exists() ? BryUtl.Empty : missing_ns_cls;
	}
	public byte[] Div_view_bry(BryBfrMkr bfr_mkr, byte output_tid, byte[] search_text, Xoa_ttl ttl) {
		// generate url-encoded ttls for view / edit / html; ISSUE#:572 PAGE:en.w:.07%; DATE:2020-03-28
		BryWtr tmp_bfr = bfr_mkr.GetK004();
		Xoh_href_wtr href_wtr = wiki.Html__href_wtr();
		byte[] view_ttl = href_wtr.Build_to_bry(tmp_bfr, wiki, ttl);
		byte[] edit_ttl = href_wtr.Build_to_bry_w_qargs(tmp_bfr, wiki, ttl, Xoa_url_.Qarg__action, Xoa_url_.Qarg__action__edit);
		byte[] html_ttl = href_wtr.Build_to_bry_w_qargs(tmp_bfr, wiki, ttl, Xoa_url_.Qarg__action, Xoa_url_.Qarg__action__html);

		byte[] read_cls = BryUtl.Empty, edit_cls = BryUtl.Empty, html_cls = BryUtl.Empty;
		switch (output_tid) {
			case Xopg_view_mode_.Tid__read: read_cls = Cls_selected_y; break;
			case Xopg_view_mode_.Tid__edit: edit_cls = Cls_selected_y; break;
			case Xopg_view_mode_.Tid__html: html_cls = Cls_selected_y; break;
		}

		div_view_fmtr.BldToBfrMany(tmp_bfr, read_cls, edit_cls, html_cls, search_text
			, view_ttl
			, edit_ttl
			, html_ttl
			, wiki.Props().Site_name()
			);

		return tmp_bfr.ToBryAndRls();
	}	public static final byte[] Cls_selected_y = BryUtl.NewA7("selected"), Cls_new = BryUtl.NewA7("new"), Cls_display_none = BryUtl.NewA7("xowa_display_none");
	public byte[] Div_logo_bry(boolean nightmode) {return nightmode ? div_logo_night : div_logo_day;} private byte[] div_logo_day = BryUtl.Empty, div_logo_night = BryUtl.Empty;
	public byte[] Div_home_bry() {return sidebar_enabled ? div_home_bry : BryUtl.Empty;} private byte[] div_home_bry = BryUtl.Empty;
	public byte[] Div_sync_bry(BryWtr tmp_bfr, boolean manual_enabled, Xow_wiki wiki, Xoa_page page) {
		// only show update_html if wmf; DATE:2016-08-31
		if (	wiki.Domain_itm().Domain_type().Src() == Xow_domain_tid.Src__wmf
			&&	manual_enabled) {
			div_sync_fmtr.BldToBfrMany(tmp_bfr, page.Ttl().Full_url());
			return tmp_bfr.ToBryAndClear();
		}
		return BryUtl.Empty;
	}
	public byte[] Div_wikis_bry(BryBfrMkr bfr_mkr) {
		if (toggle_itm == null)	// TEST:lazy-new b/c Init_by_wiki
			toggle_itm = wiki.Appe().Api_root().Html().Page().Toggle_mgr().Get_or_new("offline-wikis").Init(BryUtl.NewA7("Wikis"));
		BryWtr tmp_bfr = bfr_mkr.GetK004();
		div_wikis_fmtr.BldToBfrMany(tmp_bfr, toggle_itm.Html_toggle_btn(), toggle_itm.Html_toggle_hdr());
		return tmp_bfr.ToBryAndRls();
	}
	private final BryFmtr
	  div_personal_fmtr = BryFmtr.New("~{portal_personal_subj_href};~{portal_personal_subj_text};~{portal_personal_talk_cls};~{portal_personal_talk_href};~{portal_personal_talk_cls};~{portal_indicators_pagesource}", "portal_personal_subj_href", "portal_personal_subj_text", "portal_personal_subj_cls", "portal_personal_talk_href", "portal_personal_talk_cls", "portal_indicators_pagesource")
	, div_ns_fmtr = BryFmtr.New("~{portal_ns_subj_href};~{portal_ns_subj_cls};~{portal_ns_talk_href};~{portal_ns_talk_cls};~{portal_div_vnts}", "portal_ns_subj_href", "portal_ns_subj_cls", "portal_ns_talk_href", "portal_ns_talk_cls", "portal_div_vnts")
	, div_view_fmtr = BryFmtr.New("", "portal_view_read_cls", "portal_view_edit_cls", "portal_view_html_cls", "search_text", "portal_view_read_href", "portal_view_edit_href", "portal_view_html_href", "sitename")
	, div_logo_fmtr = BryFmtr.New("", "portal_nav_main_href", "portal_logo_url")
	, div_sync_fmtr = BryFmtr.New("", "page_url")
	, div_wikis_fmtr = BryFmtr.New("", "toggle_btn", "toggle_hdr")
	;
	public BryFmtr Div_logo_fmtr() {return div_logo_fmtr;} // TEST:
	public BryFmtr Div_view_fmtr() {return div_view_fmtr;} // TEST:
	public BryFmtr Div_ns_fmtr() {return div_ns_fmtr;} // TEST:
	public BryFmtr Div_personal_fmtr() {return div_personal_fmtr;} // TEST:
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_div_personal_))					div_personal_fmtr.FmtSet(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_div_ns_))						div_ns_fmtr.FmtSet(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_div_view_))						div_view_fmtr.FmtSet(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_div_logo_))						div_logo_fmtr.FmtSet(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_div_home_))						div_home_fmtr.FmtSet(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_div_sync_))						div_sync_fmtr.FmtSet(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_div_wikis_))						div_wikis_fmtr.FmtSet(m.ReadBry("v"));

		else if (ctx.Match(k, Cfg__missing_class))					missing_ns_cls = m.ReadBry("v");
		else if (ctx.Match(k, Cfg__sidebar_enabled__desktop))		Sidebar_enabled_(BoolUtl.Y, m.ReadYn("v"));
		else if (ctx.Match(k, Cfg__sidebar_enabled__server))		Sidebar_enabled_(BoolUtl.N, m.ReadYn("v"));
		else if (ctx.Match(k, Cfg__divs__footer))					div_footer_fmtr.FmtSet(m.ReadBry("v"));
		else if (ctx.Match(k, Cfg__hdumps_indicators_enabled))      indicators_pagesource_enabled = m.ReadYn("v");
		else if (ctx.Match(k, Cfg__hdumps_indicators_wtxt))         indicators_pagesource_wtxt = m.ReadBry("v");
		else if (ctx.Match(k, Cfg__hdumps_indicators_html))         indicators_pagesource_html = m.ReadBry("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_div_personal_ = "div_personal_", Invk_div_view_ = "div_view_", Invk_div_ns_ = "div_ns_", Invk_div_home_ = "div_home_"
	, Invk_div_sync_ = "div_sync_", Invk_div_wikis_ = "div_wikis_";
	public static final String Invk_div_logo_ = "div_logo_";
	private static final byte[] Missing_ns_cls_hide = BryUtl.NewA7("xowa_display_none");

	// NOTE: emulate recent change but support backward compatibility; ISSUE#:394; REF.MW:https://phabricator.wikimedia.org/source/Vector/browse/master/includes/templates/index.mustache DATE:2019-03-20
	// TODO: use "vector-jumptosearch", but need to update language.gfs files
	private static final BryFmtr Div_jump_to_fmtr = BryFmtr.New(StringUtl.Concat
	( "\n    <div id=\"jump-to-nav\" class=\"mw-jump\">" // NOTE:class=mw-jump is for backward compatibility
	, "\n    <a class=\"mw-jump-link\" href=\"#mw-head\">~{jumpto}~{jumptonavigation}</a>"
	, "\n    <a class=\"mw-jump-link\" href=\"#p-search\">~{jumpto}~{jumptosearch}</a>"
	, "\n    </div>" // NOTE: </div> is for backward compatibility; current MW places right after jump-to-nav
	), "jumpto", "jumptonavigation", "jumptosearch");

	private static final String
	  Cfg__missing_class				= "xowa.html.portal.missing_class"
	, Cfg__sidebar_enabled__desktop		= "xowa.html.portal.sidebar_enabled_desktop"
	, Cfg__sidebar_enabled__server		= "xowa.html.portal.sidebar_enabled_server"
	, Cfg__divs__footer					= "xowa.html.divs.footer"
	, Cfg__hdumps_indicators_enabled    = "xowa.wiki.hdumps.indicators.enabled"
	, Cfg__hdumps_indicators_wtxt       = "xowa.wiki.hdumps.indicators.html_if_wtxt"
	, Cfg__hdumps_indicators_html       = "xowa.wiki.hdumps.indicators.html_if_html"
	;
}
