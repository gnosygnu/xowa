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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*; import gplx.langs.htmls.*; import gplx.xowa.langs.vnts.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.skins.*; 
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*; import gplx.xowa.parsers.*; import gplx.xowa.xtns.wbases.*;
import gplx.xowa.xtns.pagebanners.*;
import gplx.xowa.apps.gfs.*; import gplx.xowa.htmls.portal.*;
import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.*;
public class Xoh_page_wtr_wkr {
	private final    Object thread_lock_1 = new Object(), thread_lock_2 = new Object();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(255); 
	private final    Xoh_page_wtr_mgr mgr; private final    byte page_mode;
	private final    Wdata_xwiki_link_wtr wdata_lang_wtr = new Wdata_xwiki_link_wtr();	// In other languages
	private Xoae_app app; private Xowe_wiki wiki; private Xoae_page page; private byte[] root_dir_bry;
	public Xoh_page_wtr_wkr(Xoh_page_wtr_mgr mgr, byte page_mode) {this.mgr = mgr; this.page_mode = page_mode;}		
	public Xoh_page_wtr_wkr Ctgs_enabled_(boolean v) {ctgs_enabled = v; return this;} private boolean ctgs_enabled = true;		
	public void Write_page(Bry_bfr rv, Xoae_page page, Xop_ctx ctx) {
		synchronized (thread_lock_1) {
			this.page = page; this.wiki = page.Wikie(); this.app = wiki.Appe();
			ctx.Page_(page); // HACK: must update page for toc_mgr; WHEN: Xoae_page rewrite
			Bry_fmtr fmtr = null;
			if (mgr.Html_capable()) {
				wdata_lang_wtr.Page_(page);
				byte view_mode = page_mode;
				switch (page_mode) {
					case Xopg_page_.Tid_edit:	fmtr = mgr.Page_edit_fmtr(); break;
					case Xopg_page_.Tid_html:	fmtr = mgr.Page_read_fmtr(); view_mode = Xopg_page_.Tid_read; break; // set view_mode to read, so that "read" is highlighted in HTML
					case Xopg_page_.Tid_read:	fmtr = mgr.Page_read_fmtr(); 
						// ctx.Page().Redlink_list().Clear();	// not sure if this is the best place to put it, but redlinks (a) must only fire once; (b) must fire before html generation; (c) cannot fire during edit (preview will handle separately); NOTE: probably put in to handle reusable redlink lists; redlink lists are now instantiated per page, so clear is not useful
						break;
				}
				Bry_bfr page_bfr = wiki.Utl__bfr_mkr().Get_m001();	// NOTE: get separate page rv to output page; do not reuse tmp_bfr b/c it will be used inside Fmt_do
				Xoh_wtr_ctx hctx = null;
				if (page_mode == Xopg_page_.Tid_html && wiki.App().Api_root().Wiki().Hdump().Html_mode().Tid_is_hdump_save()) {
					hctx = Xoh_wtr_ctx.Hdump;
					Write_body(page_bfr, ctx, hctx, page);
					Write_page_by_tid(ctx, hctx, page_mode, rv, mgr.Page_html_fmtr(), Gfh_utl.Escape_html_as_bry(page_bfr.To_bry_and_clear()));
				}
				else {
					hctx = Xoh_wtr_ctx.Basic;
					Write_body(page_bfr, ctx, hctx, page);
					Write_page_by_tid(ctx, hctx, view_mode, rv, fmtr, page_bfr.To_bry_and_rls());
					if (page_mode == Xopg_page_.Tid_html)	// if html, write page again, but wrap it in html skin this time
						Write_page_by_tid(ctx, hctx, page_mode, rv, mgr.Page_html_fmtr(), Gfh_utl.Escape_html_as_bry(rv.To_bry_and_clear()));
					wdata_lang_wtr.Page_(null);

					if (page_mode == Xopg_page_.Tid_read) { // EXPERIMENTAL
						new gplx.xowa.addons.htmls.includes.Xoh_include_mgr().Write(wiki, page);
					}
				}
			}
			else
				Write_body(rv, ctx, Xoh_wtr_ctx.Basic, page);
			this.page = null;
		}
	}
	private void Write_page_by_tid(Xop_ctx ctx, Xoh_wtr_ctx hctx, byte html_gen_tid, Bry_bfr bfr, Bry_fmtr fmtr, byte[] page_data) {
		// if custom_html, use it and exit; needed for Default_tab
		byte[] custom_html = page.Html_data().Custom_html();
		if (custom_html != null) {bfr.Add(custom_html); return;}
		// temp variables
		if (root_dir_bry == null) this.root_dir_bry = app.Fsys_mgr().Root_dir().To_http_file_bry();
		Xoa_ttl page_ttl = page.Ttl(); int page_ns_id = page_ttl.Ns().Id();
		byte page_tid = Xow_page_tid.Identify(wiki.Domain_tid(), page_ns_id, page_ttl.Page_db());
		DateAdp modified_on = page.Db().Page().Modified_on();
		byte[] modified_on_msg = wiki.Msg_mgr().Val_by_id_args(Xol_msg_itm_.Id_portal_lastmodified, modified_on.XtoStr_fmt_yyyy_MM_dd(), modified_on.XtoStr_fmt_HHmm());
		byte[] page_body_class = Xoh_page_body_cls.Calc(tmp_bfr, page_ttl, page_tid);
		byte[] html_content_editable = wiki.Gui_mgr().Cfg_browser().Content_editable() ? Content_editable_bry : Bry_.Empty;
		byte[] page_content_sub = Xoh_page_wtr_wkr_.Bld_page_content_sub(app, wiki, page, tmp_bfr);
		byte[] js_edit_toolbar_bry = html_gen_tid == Xopg_page_.Tid_edit ? wiki.Fragment_mgr().Html_js_edit_toolbar() : Bry_.Empty;
		Xol_vnt_mgr vnt_mgr = wiki.Lang().Vnt_mgr();
		if (vnt_mgr.Enabled()) {
			byte[] converted_title = vnt_mgr.Convert_lang().Converted_title();	// prefer converted title
			if (converted_title == null)	// converted title does not exist; use regular page title and convert it
				converted_title = vnt_mgr.Convert_lang().Auto_convert(vnt_mgr.Cur_itm(), page_ttl.Page_txt());
			page_ttl = Xoa_ttl.Parse(wiki, page_ttl.Ns().Id(), converted_title);
		}
		byte[] page_name = Xoh_page_wtr_wkr_.Bld_page_name(tmp_bfr, page_ttl, null);		// NOTE: page_name does not show display_title (<i>). always pass in null
		byte[] page_display_title = Xoh_page_wtr_wkr_.Bld_page_name(tmp_bfr, page_ttl, page.Html_data().Display_ttl());
		page.Html_data().Custom_tab_name_(page_name);	// set tab_name to page_name; note that if null, gui code will ignore and use Ttl.Page_txt; PAGE: zh.w:釣魚臺列嶼主權問題 DATE:2015-10-05
		Xow_portal_mgr portal_mgr = wiki.Html_mgr().Portal_mgr().Init_assert();
		fmtr.Bld_bfr_many(bfr
		, root_dir_bry, Xoa_app_.Version, Xoa_app_.Build_date, app.Tcp_server().Running_str()
		, page.Db().Page().Id(), page.Ttl().Full_db()
		, page_name, page.Html_data().Page_heading().Init(page.Html_data(), page_display_title)
		, modified_on_msg
		, mgr.Css_common_bry(), mgr.Css_wiki_bry(), page.Html_data().Head_mgr().Init(app, wiki, page).Init_dflts()
		, page.Lang().Dir_ltr_bry(), page.Html_data().Indicators(), page_content_sub, wiki.Html_mgr().Portal_mgr().Div_jump_to(), wiki.Xtn_mgr().Xtn_pgbnr().Write_html(page, ctx, hctx), page_body_class, html_content_editable
		, page_data, wdata_lang_wtr

		// sidebar divs
		, portal_mgr.Div_personal_bry()
		, portal_mgr.Div_ns_bry(wiki.Utl__bfr_mkr(), page_ttl, wiki.Ns_mgr())
		, portal_mgr.Div_view_bry(wiki.Utl__bfr_mkr(), html_gen_tid, page.Html_data().Xtn_search_text())
		, portal_mgr.Div_logo_bry(), portal_mgr.Div_home_bry(), new Xopg_xtn_skin_fmtr_arg(page, Xopg_xtn_skin_itm_tid.Tid_sidebar)
		, portal_mgr.Div_sync_bry(tmp_bfr, app.Api_root().Addon().Bldr().Sync().Manual_enabled(), wiki, page)
		, portal_mgr.Div_wikis_bry(wiki.Utl__bfr_mkr())
		, portal_mgr.Sidebar_mgr().Html_bry()
		, mgr.Edit_rename_div_bry(page_ttl), page.Html_data().Edit_preview_w_dbg(), js_edit_toolbar_bry			
		);
		Xoh_page_wtr_wkr_.Bld_head_end(bfr, tmp_bfr, page);	// add after </head>
		Xoh_page_wtr_wkr_.Bld_html_end(bfr, tmp_bfr, page);	// add after </html>			
	}
	public void Write_hdump(Bry_bfr bfr, Xop_ctx ctx, Xoh_wtr_ctx hctx, Xoae_page wpg) {
		if (wpg.Html_data().Xtn_pgbnr() != null) {
			ctx.Wiki().Xtn_mgr().Xtn_pgbnr().Write_html(wpg, ctx, hctx).Bfr_arg__add(bfr);	// if pgbnr exists, write to top of html
		}
		this.Write_body(bfr, ctx, hctx, wpg);
	}
	public void Write_body(Bry_bfr bfr, Xop_ctx ctx, Xoh_wtr_ctx hctx, Xoae_page page) {
		synchronized (thread_lock_2) {
			this.page = page; this.wiki = page.Wikie(); this.app = wiki.Appe();
			Xoa_ttl page_ttl = page.Ttl(); int page_ns_id = page_ttl.Ns().Id();
			byte page_tid = Xow_page_tid.Identify(wiki.Domain_tid(), page_ns_id, page_ttl.Page_db());	// NOTE: can't cache page_tid b/c Write_body is called directly; DATE:2014-10-02
			byte[] data_raw = page.Db().Text().Text_bry();
			int bfr_page_bgn = bfr.Len();
			boolean page_tid_uses_pre = false;
			if (page_mode == Xopg_page_.Tid_edit)
				Write_body_edit(bfr, data_raw, page_ns_id, page_tid);
			else {
				switch (page_tid) {
					case Xow_page_tid.Tid_msg:
					case Xow_page_tid.Tid_js:
					case Xow_page_tid.Tid_css:
					case Xow_page_tid.Tid_lua:		Write_body_pre			(bfr, app, wiki, hctx, data_raw, tmp_bfr); page_tid_uses_pre = true; break;
					case Xow_page_tid.Tid_json:		app.Wiki_mgr().Wdata_mgr().Write_json_as_html(bfr, page_ttl.Full_db(), data_raw); break;
					case Xow_page_tid.Tid_wikitext: Write_body_wikitext		(bfr, app, wiki, data_raw, ctx, hctx, page, page_tid, page_ns_id); break;
				}
			}
			if (	wiki.Domain_tid() != Xow_domain_tid_.Tid__home	// allow home wiki to use javascript
				&&  !page_tid_uses_pre) {							// if .js, .css or .lua, skip test; may have js fragments, but entire text is escaped and put in pre; don't show spurious warning; DATE:2013-11-21
				wiki.Html_mgr().Js_cleaner().Clean_bfr(wiki, page_ttl, bfr, bfr_page_bgn);
			}
		}
	}
	private void Write_body_wikitext(Bry_bfr bfr, Xoae_app app, Xowe_wiki wiki, byte[] data_raw, Xop_ctx ctx, Xoh_wtr_ctx hctx, Xoae_page page, byte page_tid, int ns_id) {
		// dump and exit if pre-generated html from html dumps
		byte[] hdump_data = page.Db().Html().Html_bry();
		if (Bry_.Len_gt_0(hdump_data)) {
			bfr.Add(hdump_data);
			return;
		}
		// dump and exit if MediaWiki message;
		if	(ns_id == Xow_ns_.Tid__mediawiki) {	// if MediaWiki and wikitext, must be a message; convert args back to php; DATE:2014-06-13
			bfr.Add(Gfs_php_converter.Xto_php(tmp_bfr, Bool_.N, data_raw));
			return;
		}
		// if [[File]], add boilerplate header; note that html is XOWA-generated so does not need to be tidied
		if (ns_id == Xow_ns_.Tid__file) app.Ns_file_page_mgr().Bld_html(wiki, ctx, page, bfr, page.Ttl(), wiki.Cfg_file_page(), page.File_queue());
		// get separate bfr; note that bfr already has <html> and <head> written to it, so this can't be passed to tidy; DATE:2014-06-11
		Bry_bfr tidy_bfr = wiki.Utl__bfr_mkr().Get_m001();
		// write wikitext
		if (page.Html_data().Skip_parse()) {
			tidy_bfr.Add(page.Html_data().Custom_body());
		}
		else {
			if (page.Root() != null) {	// NOTE: will be null if blank; occurs for one test: Logo_has_correct_main_page; DATE:2015-09-29
				page.Html_data().Toc_mgr().Clear();	// NOTE: always clear tocs before writing html; toc_itms added when writing html_hdr; DATE:2016-07-17
				wiki.Html_mgr().Html_wtr().Write_doc(tidy_bfr, ctx, hctx, page.Root().Data_mid(), page.Root());
				if (wiki.Html_mgr().Html_wtr().Cfg().Toc__show())
					gplx.xowa.htmls.core.wkrs.tocs.Xoh_toc_wtr.Write_toc(tidy_bfr, page, hctx);
			}
		}
		
		// if [[Category]], render rest of html (Subcategories; Pages; Files); note that a category may have other html which requires wikitext processing
		if (ns_id == Xow_ns_.Tid__category) wiki.Ctg__catpage_mgr().Write_catpage(tidy_bfr, page, hctx);

		// tidy html
		wiki.Html_mgr().Tidy_mgr().Exec_tidy(tidy_bfr, !hctx.Mode_is_hdump(), page.Url_bry_safe());
		
		// add back to main bfr
		bfr.Add_bfr_and_clear(tidy_bfr);
		tidy_bfr.Mkr_rls();

		// handle Categories at bottom of page; note that html is XOWA-generated so does not need to be tidied
		int ctgs_len = page.Wtxt().Ctgs__len();
		if (	ctgs_enabled
			&&	ctgs_len > 0						// skip if no categories found while parsing wikitext
			&&	!wiki.Html_mgr().Importing_ctgs()	// do not show categories if importing categories, page will wait for category import to be done; DATE:2014-10-15
			) {
			if (app.Mode().Tid_is_gui()) app.Usr_dlg().Prog_many("", "", "loading categories: count=~{0}", ctgs_len);
			Xoctg_pagebox_itm[] pagebox_itms = wiki.Ctg__pagebox_wtr().Get_catlinks_by_page(wiki, page);
			boolean hidden_enabled = wiki.App().Api_root().Addon().Wikis__ctgs__hidden_enabled();
			wiki.Ctg__pagebox_wtr().Write_pagebox(hidden_enabled, bfr, wiki, page, pagebox_itms);
		}

		// translate if variants are enabled
		Xol_vnt_mgr vnt_mgr = wiki.Lang().Vnt_mgr();
		if (vnt_mgr.Enabled()) bfr.Add(vnt_mgr.Convert_lang().Parse_page(vnt_mgr.Cur_itm(), page.Db().Page().Id(), bfr.To_bry_and_clear()));
	}
	private void Write_body_pre(Bry_bfr bfr, Xoae_app app, Xowe_wiki wiki, Xoh_wtr_ctx hctx, byte[] data_raw, Bry_bfr tmp_bfr) {
		Xoh_html_wtr_escaper.Escape(app.Parser_amp_mgr(), tmp_bfr, data_raw, 0, data_raw.length, false, false);
		if (hctx.Mode_is_hdump())
			bfr.Add(data_raw);
		else
			app.Html_mgr().Page_mgr().Content_code_fmtr().Bld_bfr_many(bfr, tmp_bfr);
		tmp_bfr.Clear();
	}
	private void Write_body_edit(Bry_bfr bfr, byte[] data_raw, int ns_id, byte page_tid) {
		if	(	ns_id == Xow_ns_.Tid__mediawiki			// if MediaWiki and wikitext, must be a message; convert args back to php; DATE:2014-06-13
			&&	page_tid == Xow_page_tid.Tid_wikitext
			)
			data_raw = Gfs_php_converter.Xto_php(tmp_bfr, Bool_.N, data_raw);
		int data_raw_len = data_raw.length;
		if (mgr.Html_capable())
			Xoh_html_wtr_escaper.Escape(page.Wikie().Appe().Parser_amp_mgr(), bfr, data_raw, 0, data_raw_len, false, false);	// NOTE: must escape; assume that browser will automatically escape (&lt;) (which Mozilla does)
		else
			bfr.Add(data_raw);
		if (data_raw_len > 0)		// do not add nl if empty String
			bfr.Add_byte_nl();		// per MW:EditPage.php: "Ensure there's a newline at the end, otherwise adding lines is awkward."
	}
	private static final    byte[] Content_editable_bry = Bry_.new_a7(" contenteditable=\"true\"");
}
