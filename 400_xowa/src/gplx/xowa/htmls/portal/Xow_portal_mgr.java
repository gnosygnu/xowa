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
package gplx.xowa.htmls.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.brys.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.guis.*; import gplx.xowa.htmls.sidebar.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.domains.*; 
import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.apps.apis.xowa.html.*; import gplx.xowa.apps.apis.xowa.html.skins.*;
import gplx.xowa.langs.vnts.*; import gplx.xowa.htmls.portal.vnts.*;
public class Xow_portal_mgr implements GfoInvkAble {
	private Xowe_wiki wiki; private boolean lang_is_rtl; private Xoapi_toggle_itm toggle_itm;
	private final Vnt_mnu_grp_fmtr vnt_menu_fmtr = new Vnt_mnu_grp_fmtr();
	public Xow_portal_mgr(Xowe_wiki wiki) {
		this.wiki = wiki;
		this.sidebar_mgr = new Xowh_sidebar_mgr(wiki);
		this.missing_ns_cls = Bry_.Eq(wiki.Domain_bry(), Xow_domain_tid_.Bry__home) ? Missing_ns_cls_hide : null;	// if home wiki, set missing_ns to application default; if any other wiki, set to null; will be overriden during init
	}
	public void Init_by_lang(Xol_lang_itm lang) {
		lang_is_rtl = !lang.Dir_ltr();
	}
	private Xoapi_skin_app_base api_skin;
	public Xowh_sidebar_mgr Sidebar_mgr() {return sidebar_mgr;} private Xowh_sidebar_mgr sidebar_mgr;
	public Bry_fmtr Div_home_fmtr() {return div_home_fmtr;} private Bry_fmtr div_home_fmtr = Bry_fmtr.new_("");
	public Xow_portal_mgr Init_assert() {if (init_needed) Init(); return this;}
	public byte[] Div_jump_to() {return div_jump_to;} private byte[] div_jump_to = Bry_.Empty;
	public void Init() {
		init_needed = false;
		if (missing_ns_cls == null)	// if missing_ns_cls not set for wiki, use the home wiki's
			Missing_ns_cls_(wiki.Appe().Usere().Wiki().Html_mgr().Portal_mgr().Missing_ns_cls());
		Xoapi_skins skins = wiki.Appe().Api_root().Html().Skins();
		api_skin = wiki.App().Mode().Tid_is_gui() ? skins.Desktop() : skins.Server();
		Bry_fmtr_eval_mgr eval_mgr = wiki.Eval_mgr();
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		Init_fmtr(tmp_bfr, eval_mgr, div_view_fmtr);
		Init_fmtr(tmp_bfr, eval_mgr, div_ns_fmtr);
		byte[] wiki_user_name = wiki.User().Name();
		div_personal_bry = Init_fmtr(tmp_bfr, eval_mgr, div_personal_fmtr, Bry_.Add(Xoh_href_.Bry__wiki, wiki.Ns_mgr().Ids_get_or_null(Xow_ns_.Tid__user).Name_db_w_colon(), wiki_user_name), wiki_user_name, Ns_cls_by_id(wiki.Ns_mgr(), Xow_ns_.Tid__user), Bry_.Add(Xoh_href_.Bry__wiki, wiki.Ns_mgr().Ids_get_or_null(Xow_ns_.Tid__user_talk).Name_db_w_colon(), wiki_user_name), Ns_cls_by_id(wiki.Ns_mgr(), Xow_ns_.Tid__user_talk));
		byte[] main_page_href_bry = tmp_bfr.Add(Xoh_href_.Bry__site).Add(wiki.Domain_bry()).Add(Xoh_href_.Bry__wiki).To_bry_and_clear();	// NOTE: build /site/en.wikipedia.org/wiki/ href; no Main_Page, as that will be inserted by Xoh_href_parser
		div_logo_bry = Init_fmtr(tmp_bfr, eval_mgr, div_logo_fmtr, main_page_href_bry, gplx.langs.htmls.encoders.Gfo_url_encoder_.Fsys_lnx.Encode_to_file_protocol(wiki.Appe().Usere().Fsys_mgr().Wiki_root_dir().GenSubFil_nest(wiki.Domain_str(), "html", "logo.png")));
		div_home_bry = Init_fmtr(tmp_bfr, eval_mgr, div_home_fmtr);
		div_wikis_fmtr.Eval_mgr_(eval_mgr);
		Xow_msg_mgr msg_mgr = wiki.Msg_mgr();
		div_jump_to = Div_jump_to_fmtr.Bld_bry_many(tmp_bfr, msg_mgr.Val_by_key_obj("jumpto"), msg_mgr.Val_by_key_obj("jumptonavigation"), msg_mgr.Val_by_key_obj("comma-separator"), msg_mgr.Val_by_key_obj("jumptosearch"));
		tmp_bfr.Mkr_rls();
		sidebar_mgr.Init();
	}	private boolean init_needed = true;
	private byte[] Init_fmtr(Bry_bfr tmp_bfr, Bry_fmtr_eval_mgr eval_mgr, Bry_fmtr fmtr, Object... fmt_args) {
		fmtr.Eval_mgr_(eval_mgr);
		fmtr.Bld_bfr_many(tmp_bfr, fmt_args);
		return tmp_bfr.To_bry_and_clear();
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
		Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
		div_ns_fmtr.Bld_bfr_many(tmp_bfr, Bry_.Add(Xoh_href_.Bry__wiki, ttl.Subj_txt()), subj_cls, Bry_.Add(Xoh_href_.Bry__wiki, ttl.Talk_txt()), talk_cls, vnt_menu);
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
	}	public static final byte[] Cls_selected_y = Bry_.new_a7("selected"), Cls_new = Bry_.new_a7("new"), Cls_display_none = Bry_.new_a7("xowa_display_none");
	public byte[] Div_logo_bry() {return div_logo_bry;} private byte[] div_logo_bry = Bry_.Empty;
	public byte[] Div_home_bry() {return api_skin != null && api_skin.Sidebar_home_enabled() ? div_home_bry : Bry_.Empty;} private byte[] div_home_bry = Bry_.Empty;
	public byte[] Div_wikis_bry(Bry_bfr_mkr bfr_mkr) {
		if (toggle_itm == null)	// TEST:lazy-new b/c Init_by_wiki
			toggle_itm = wiki.Appe().Api_root().Html().Page().Toggle_mgr().Get_or_new("offline-wikis").Init(Bry_.new_a7("Wikis"));
		Bry_bfr tmp_bfr = bfr_mkr.Get_k004();
		div_wikis_fmtr.Bld_bfr_many(tmp_bfr, toggle_itm.Html_toggle_btn(), toggle_itm.Html_toggle_hdr());
		return tmp_bfr.To_bry_and_rls();
	}
	public byte[] Missing_ns_cls() {return missing_ns_cls;} public Xow_portal_mgr Missing_ns_cls_(byte[] v) {missing_ns_cls = v; return this;} private byte[] missing_ns_cls;	// NOTE: must be null due to Init check above
	private final Bry_fmtr div_personal_fmtr = Bry_fmtr.new_("~{portal_personal_subj_href};~{portal_personal_subj_text};~{portal_personal_talk_cls};~{portal_personal_talk_href};~{portal_personal_talk_cls};", "portal_personal_subj_href", "portal_personal_subj_text", "portal_personal_subj_cls", "portal_personal_talk_href", "portal_personal_talk_cls");
	private final Bry_fmtr div_ns_fmtr = Bry_fmtr.new_("~{portal_ns_subj_href};~{portal_ns_subj_cls};~{portal_ns_talk_href};~{portal_ns_talk_cls};~{portal_div_vnts}", "portal_ns_subj_href", "portal_ns_subj_cls", "portal_ns_talk_href", "portal_ns_talk_cls", "portal_div_vnts");
	private final Bry_fmtr div_view_fmtr = Bry_fmtr.new_("", "portal_view_read_cls", "portal_view_edit_cls", "portal_view_html_cls", "search_text");
	private final Bry_fmtr div_logo_fmtr = Bry_fmtr.new_("", "portal_nav_main_href", "portal_logo_url");
	private final Bry_fmtr div_wikis_fmtr = Bry_fmtr.new_("", "toggle_btn", "toggle_hdr");
	private byte[] Reverse_li(byte[] bry) {
		return lang_is_rtl ? Xoh_rtl_utl.Reverse_li(bry) : bry;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_div_personal_))					div_personal_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_div_ns_))						div_ns_fmtr.Fmt_(Reverse_li(m.ReadBry("v")));
		else if	(ctx.Match(k, Invk_div_view_))						div_view_fmtr.Fmt_(Reverse_li(m.ReadBry("v")));
		else if	(ctx.Match(k, Invk_div_logo_))						div_logo_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_div_home_))						div_home_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_div_wikis_))						div_wikis_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_missing_ns_cls))					return String_.new_u8(missing_ns_cls);
		else if	(ctx.Match(k, Invk_missing_ns_cls_))				missing_ns_cls = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_missing_ns_cls_list))			return Options_missing_ns_cls_list;
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_div_personal_ = "div_personal_", Invk_div_view_ = "div_view_", Invk_div_ns_ = "div_ns_", Invk_div_home_ = "div_home_", Invk_div_wikis_ = "div_wikis_"
	, Invk_missing_ns_cls = "missing_ns_cls", Invk_missing_ns_cls_ = "missing_ns_cls_", Invk_missing_ns_cls_list = "missing_ns_cls_list"
	;
	public static final String Invk_div_logo_ = "div_logo_";
	private static Keyval[] Options_missing_ns_cls_list = Keyval_.Ary(Keyval_.new_("", "Show as blue link"), Keyval_.new_("new", "Show as red link"), Keyval_.new_("xowa_display_none", "Hide")); 
	private static final byte[] Missing_ns_cls_hide = Bry_.new_a7("xowa_display_none");
	private static final Bry_fmtr Div_jump_to_fmtr = Bry_fmtr.new_
	( "\n    <div id=\"jump-to-nav\" class=\"mw-jump\">~{jumpto}<a href=\"#mw-navigation\">~{jumptonavigation}</a>~{comma-separator}<a href=\"#p-search\">~{jumptosearch}</a></div>"
	, "jumpto", "jumptonavigation", "comma-separator", "jumptosearch");
}
