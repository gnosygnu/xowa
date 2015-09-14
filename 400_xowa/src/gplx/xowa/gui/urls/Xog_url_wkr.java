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
package gplx.xowa.gui.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
import gplx.core.net.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*;
import gplx.xowa.html.hrefs.*; import gplx.xowa.html.doms.*;
import gplx.xowa.gui.views.*;
public class Xog_url_wkr {
	private final Xoa_url tmp_url = Xoa_url.blank();
	private Xoae_app app; private Xog_win_itm win; private Xowe_wiki wiki; private Xoae_page page;
	private final Xof_img_size img_size = new Xof_img_size(); private final Xof_url_bldr url_bldr = Xof_url_bldr.new_v2();
	public Xog_url_wkr Parse(Xog_win_itm win, String href_str) {
		if (href_str == null) return this;	// text is not link; return;
		byte[] href_bry = Bry_.new_u8(href_str);
		this.win = win; this.app = win.App(); 
		this.page = win.Active_page();
		this.wiki = win.Active_tab().Wiki();
		app.Html__href_parser().Parse_as_url(tmp_url, href_bry, wiki, page.Ttl().Page_url());
		return this;
	}
	public Xoa_url Exec() {
		switch (tmp_url.Tid()) {
			case Xoa_url_.Tid_unknown:		return Xoa_url.Null;										// unknown; return null which will become a noop
			case Xoa_url_.Tid_inet:			return Exec_url_http(app);									// http://site.org
			case Xoa_url_.Tid_anch:			return Exec_url_anchor(win);								// #anchor
			case Xoa_url_.Tid_xcmd:			return Exec_url_xowa(app);									// xowa:app.version or /xcmd/app.version
			case Xoa_url_.Tid_file:			return Exec_url_file(app, wiki, page, win, tmp_url.Raw());	// file:///xowa/A.png
			case Xoa_url_.Tid_page:			return Exec_url_page(app, wiki, page, win, tmp_url.Raw());	// Page /wiki/Page
			default:						throw Err_.new_unhandled(tmp_url.Tid());
		}
	}
	private Xoa_url Exec_url_xowa(Xoae_app app) {		// EX: xowa:app.version
		// NOTE: must catch exception else it will bubble to SWT browser and raise secondary exception of xowa is not a registered protocol
		try {app.Gfs_mgr().Run_str(String_.new_u8(tmp_url.Page_bry()));}
		catch (Exception e) {app.Gui_mgr().Kit().Ask_ok("", "", Err_.Message_gplx_full(e));}
		return Rslt_handled;
	}
//		private Xoa_url Exec_url_xcmd(Xog_win_itm win) {		// EX: /xcmd/
//			byte[] xowa_href_bry = tmp_url.Page();
//			int xowa_href_bry_len = xowa_href_bry.length;
//			int slash_pos = Bry_finder.Find_fwd(xowa_href_bry, Byte_ascii.Slash); if (slash_pos == Bry_.NotFound) slash_pos = xowa_href_bry_len;
//			byte[] xowa_cmd_bry = Bry_.Mid(xowa_href_bry, 0, slash_pos);
//			String xowa_cmd_str = String_.new_u8(xowa_cmd_bry);
//			GfoMsg m = GfoMsg_.new_cast_(xowa_cmd_str);
//			if (String_.Eq(xowa_cmd_str, Xog_win_itm.Invk_eval))
//				m.Add("cmd", String_.new_u8(xowa_href_bry, slash_pos + 1, xowa_href_bry_len));
//			win.Invk(GfsCtx.new_(), 0, xowa_cmd_str, m);
//			return Rslt_handled;
//		}
	private Xoa_url Exec_url_http(Xoae_app app) {		// EX: http://a.org
		app.Prog_mgr().Exec_view_web(tmp_url.Raw());
		return Rslt_handled;
	}
	private Xoa_url Exec_url_anchor(Xog_win_itm win) {	// EX: #anchor
		win.Active_html_itm().Scroll_page_by_id_gui(tmp_url.Anch_str());	// NOTE: was originally directly; changed to call on thread; DATE:2014-05-03
		return Rslt_handled;
	}
	private Xoa_url Exec_url_file(Xoae_app app, Xowe_wiki cur_wiki, Xoae_page page, Xog_win_itm win, byte[] href_bry) {	// EX: file:///xowa/A.png
		Xowe_wiki wiki = (Xowe_wiki)page.Commons_mgr().Source_wiki_or(cur_wiki);
		Io_url href_url = Io_url_.http_any_(String_.new_u8(Xoa_app_.Utl__encoder_mgr().Http_url().Decode(href_bry)), Op_sys.Cur().Tid_is_wnt());
		gplx.gfui.Gfui_html html_box = win.Active_html_box();
		String xowa_ttl = wiki.Gui_mgr().Cfg_browser().Content_editable()
			? html_box.Html_js_eval_proc_as_str(Xog_js_procs.Selection__get_active_for_editable_mode, gplx.xowa.html.Xoh_consts.Atr_xowa_title_str, null)
			: Xoh_dom_.Title_by_href(href_bry, Bry_.new_u8(html_box.Html_js_eval_proc_as_str(Xog_js_procs.Doc__root_html_get)));
		byte[] lnki_ttl = Xoa_app_.Utl__encoder_mgr().Http_url().Decode(Xoa_ttl.Replace_spaces(Bry_.new_u8(xowa_ttl)));
		Xof_fsdb_itm fsdb = Xof_orig_file_downloader.Make_fsdb(wiki, lnki_ttl, img_size, url_bldr);
		if (!Io_mgr.I.ExistsFil(href_url)) {
//				if (!Xof_orig_file_downloader.Get_to_url(fsdb, href_url, wiki, lnki_ttl, url_bldr))
			if (!Xof_file_wkr.Show_img(fsdb, Xoa_app_.Usr_dlg(), wiki.File__bin_mgr(), wiki.File__mnt_mgr(), wiki.App().User().User_db_mgr().Cache_mgr(), wiki.File__repo_mgr(), gplx.xowa.files.gui.Xog_js_wkr_.Noop, img_size, url_bldr, page))
				return Rslt_handled;
		}
		gplx.ios.IoItmFil fil = Io_mgr.I.QueryFil(href_url);
		if (fil.Exists()) {
			ProcessAdp media_player = app.Prog_mgr().App_by_ext(href_url.Ext());
			media_player.Run(href_url);
			fsdb.File_size_(fil.Size());
			gplx.xowa.files.caches.Xou_cache_mgr cache_mgr = wiki.Appe().User().User_db_mgr().Cache_mgr();
			cache_mgr.Update(fsdb);
			cache_mgr.Db_save();
		}
		return Rslt_handled;
	}
	private Xoa_url Exec_url_page(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xog_win_itm win, byte[] href_bry) {	// EX: "Page"; "/wiki/Page"; // rewritten; DATE:2014-01-19
		Xoa_url rv = wiki.Utl__url_parser().Parse(href_bry);// needed for query_args
		Gfo_qarg_itm[] qargs = rv.Qargs_ary();
		int qargs_len = qargs.length;
		if (qargs_len > 0) {	// remove anchors from qargs; EX: "to=B#mw_pages"
			for (int i = 0; i < qargs_len; i++) {
				Gfo_qarg_itm arg = qargs[i];
				int anch_pos = Bry_finder.Find_bwd(arg.Val_bry(), Byte_ascii.Hash);	// NOTE: must .FindBwd to handle Category args like de.wikipedia.org/wiki/Kategorie:Begriffskl%C3%A4rung?pagefrom=#::12%20PANZERDIVISION#mw-pages; DATE:2013-06-18
				if (anch_pos != Bry_.NotFound)
					arg.Val_bry_(Bry_.Mid(arg.Val_bry(), 0, anch_pos));
			}				
		}
		return rv;
	}
//		private Xoa_url Exec_url_page(Xoae_app app, Xowe_wiki wiki, Xoae_page page, Xog_win_itm win, byte[] href_bry) {	// EX: "Page"; "/wiki/Page"; // rewritten; DATE:2014-01-19
//			Xoa_url rv = wiki.Utl__url_parser().Parse(href_bry);// needed for query_args
//			byte[] anchor_bry = href.Anchor();
//			byte[] page_bry = rv.Page_bry();
//			byte[][] segs_ary = rv.Mids_ary();
//			int segs_ary_len = segs_ary.length;
//			boolean use_main_page = false;
//			if (	segs_ary_len > 0						// handle "Special:Search/Earth" which creates segs[1] {"Special:Search"} and page="Earth"
//				||	href.Tid() == Xoh_href_.Tid_site) {		// NOTE: if site, must always (a) zap Segs_ary and (b) force correct page; see tests; DATE:2014-01-21
//				int segs_bgn = 0;
//				boolean segs_iterate = true;
//				if (href.Tid() == Xoh_href_.Tid_site) {		// site, handle multiple segs; EX: "home/wiki/", "home/wiki/Help:Contents"; DATE:2014-01-21
//					if (segs_ary_len < 2) {					// only 0 or 1 seg; usually occurs for logo and other xwiki links to Main_Page; EX: "/site/en.wikipedia.org/wiki/"; "/site/en.wikipedia.org/"
//						page_bry = wiki.Init_assert().Props().Main_page();	// use Main_page; DATE:2014-02-16
//						use_main_page = true;
//						segs_iterate = false;
//					}
//					else 
//						segs_bgn = 2;						// start from seg_2; seg_0="/en.wikipedia.org/" and seg_1="/wiki/"; note that > 2 segs possible; EX: "/site/en.wikipedia.org/wiki/A/B/C
//				}
//				if (segs_iterate) {
//					Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b128();
//					for (int i = segs_bgn; i < segs_ary_len; i++) {
//						tmp_bfr.Add(segs_ary[i]);
//						tmp_bfr.Add_byte(Byte_ascii.Slash);
//					}
//					tmp_bfr.Add(page_bry);
//					page_bry = tmp_bfr.To_bry_and_rls();
//				}
//				rv.Mids_ary_(Bry_.Ary_empty);
//			}
//			Gfo_qarg_itm[] qargs = rv.Qargs_ary();
//			int qargs_len = qargs.length;
//			if (qargs_len > 0) {	// remove anchors from qargs; EX: "to=B#mw_pages"
//				for (int i = 0; i < qargs_len; i++) {
//					Gfo_qarg_itm arg = qargs[i];
//					int anch_pos = Bry_finder.Find_bwd(arg.Val_bry(), Byte_ascii.Hash);	// NOTE: must .FindBwd to handle Category args like de.wikipedia.org/wiki/Kategorie:Begriffskl%C3%A4rung?pagefrom=#::12%20PANZERDIVISION#mw-pages; DATE:2013-06-18
//					if (anch_pos != Bry_.NotFound)
//						arg.Val_bry_(Bry_.Mid(arg.Val_bry(), 0, anch_pos));
//				}				
//			}
//			if (!Bry_.Eq(page.Wiki().Domain_bry(), href.Wiki())) {// xwiki; EX: "file:///site/en.wiktionary.org/wiki/a"; EX: (1) goto w:Anything; (2) click on "anything" in wikt; "anything" will be parsed by en.wiki's rules, not en.wikt; DATE:2013-01-30
//				wiki = app.Wiki_mgr().Get_by_key_or_make(href.Wiki()).Init_assert();	// get xwiki and set to wiki
//				if (use_main_page)
//					page_bry = wiki.Props().Main_page();								// get Main_page for new wiki; DATE:2014-02-23
//				Xoa_ttl tmp_ttl = Xoa_ttl.parse(wiki, page_bry);						// reparse ttl according to xwiki's case_match rules; NOTE: do not use rv.Page_bry() or else will lose sub_pages (A/B/C); DATE:2014-02-21
//				if (tmp_ttl != null)
//					page_bry = tmp_ttl.Full_db();
//			}
//			rv.Wiki_bry_(wiki.Domain_bry());	// needed b/c url_parser.Parse(href) will result in wiki of "wiki" for "/wiki/Page"
//			rv.Page_bry_(page_bry);
//			rv.Anch_bry_(anchor_bry);
//			return rv;
//		}
	public static Xoa_url Rslt_handled = null;
	public static Xoa_url Exec_url(Xog_win_itm win, String href_str) {
		Xog_url_wkr url_wkr = new Xog_url_wkr();
		url_wkr.Parse(win, href_str);
		return url_wkr.Exec();
	}
}
