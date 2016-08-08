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
package gplx.xowa.guis.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.core.envs.*;
import gplx.gfui.controls.standards.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*;
import gplx.langs.htmls.encoders.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.doms.*;
import gplx.xowa.guis.views.*;
public class Xog_url_wkr {
	private final    Xoa_url tmp_url = Xoa_url.blank();
	private Xoae_app app; private Xog_win_itm win; private Xowe_wiki wiki; private Xoae_page page;
	private final    Xof_img_size img_size = new Xof_img_size(); private final    Xof_url_bldr url_bldr = Xof_url_bldr.new_v2();
	private final    Gfo_url_encoder fsys_lnx_encoder = Gfo_url_encoder_.New__fsys_lnx().Make();
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
		Io_url href_url = Io_url_.http_any_(String_.new_u8(Gfo_url_encoder_.Http_url.Decode(href_bry)), Op_sys.Cur().Tid_is_wnt());
		Gfui_html html_box = win.Active_html_box();
		byte[] href_bry_encoded = fsys_lnx_encoder.Encode(href_bry);	// encode to href_bry; note must encode to same href_bry as Xof_url_bldr, which uses Gfo_url_encoder_.Fsys_lnx; PAGE:en.w:File:Volc�n_Chimborazo,_"El_Taita_Chimborazo".jpg DATE:2015-12-06
		String xowa_ttl = wiki.Gui_mgr().Cfg_browser().Content_editable()
			? html_box.Html_js_eval_proc_as_str(Xog_js_procs.Selection__get_active_for_editable_mode, gplx.xowa.htmls.Xoh_consts.Atr_xowa_title_str, null)
			: Xoh_dom_.Title_by_href(href_bry_encoded, Bry_.new_u8(html_box.Html_js_eval_proc_as_str(Xog_js_procs.Doc__root_html_get)));
		if (xowa_ttl == null) {
			app.Gui_mgr().Kit().Ask_ok("", "", "could not find anchor with href in html: href=~{0}", href_bry);
			return Rslt_handled;
		}
		byte[] lnki_ttl = gplx.langs.htmls.encoders.Gfo_url_encoder_.Http_url.Decode(Xoa_ttl.Replace_spaces(Bry_.new_u8(xowa_ttl)));
		Xof_fsdb_itm fsdb = Xof_orig_file_downloader.Make_fsdb(wiki, lnki_ttl, img_size, url_bldr);
		if (!Io_mgr.Instance.ExistsFil(href_url)) {
//				if (!Xof_orig_file_downloader.Get_to_url(fsdb, href_url, wiki, lnki_ttl, url_bldr))
			if (!Xof_file_wkr.Show_img(fsdb, Xoa_app_.Usr_dlg(), wiki.File__bin_mgr(), wiki.File__mnt_mgr(), wiki.App().User().User_db_mgr().Cache_mgr(), wiki.File__repo_mgr(), gplx.xowa.guis.cbks.js.Xog_js_wkr_.Noop, img_size, url_bldr, page))
				return Rslt_handled;
		}
		gplx.core.ios.IoItmFil fil = Io_mgr.Instance.QueryFil(href_url);
		if (fil.Exists()) {
			Process_adp media_player = app.Prog_mgr().App_by_ext(href_url.Ext());
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
				int anch_pos = Bry_find_.Find_bwd(arg.Val_bry(), Byte_ascii.Hash);	// NOTE: must .FindBwd to handle Category args like de.wikipedia.org/wiki/Kategorie:Begriffskl%C3%A4rung?pagefrom=#::12%20PANZERDIVISION#mw-pages; DATE:2013-06-18
				if (anch_pos != Bry_find_.Not_found)
					arg.Val_bry_(Bry_.Mid(arg.Val_bry(), 0, anch_pos));
			}				
		}
		return rv;
	}
	public static Xoa_url Rslt_handled = null;
	public static Xoa_url Exec_url(Xog_win_itm win, String href_str) {
		Xog_url_wkr url_wkr = new Xog_url_wkr();
		url_wkr.Parse(win, href_str);
		return url_wkr.Exec();
	}
}
