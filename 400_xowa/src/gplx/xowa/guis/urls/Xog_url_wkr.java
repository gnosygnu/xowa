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
package gplx.xowa.guis.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.core.envs.*;
import gplx.gfui.controls.standards.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*;
import gplx.langs.htmls.encoders.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.doms.*;
import gplx.xowa.guis.views.*;
public class Xog_url_wkr {
	private final    Xoa_url tmp_url = Xoa_url.blank();
	private Xoae_app app; private Xog_win_itm win; private Xowe_wiki wiki; private Xoae_page page;
	public Xoa_url Parse(Xog_win_itm win, String href_str) {
		if (href_str == null) return tmp_url;	// text is not link; return;
		byte[] href_bry = Bry_.new_u8(href_str);
		this.win = win; this.app = win.App(); 
		this.page = win.Active_page();
		this.wiki = win.Active_tab().Wiki();
		app.Html__href_parser().Parse_as_url(tmp_url, href_bry, wiki, page.Ttl().Page_url());
		return tmp_url;
	}
	public void Init(Xowe_wiki wiki) {	// TEST:
		this.wiki = wiki;
	}
	public Xoa_url Exec_url(Xoa_url url) {
		switch (url.Tid()) {
			case Xoa_url_.Tid_unknown:		return Xoa_url.Null;										// unknown; return null which will become a noop
			case Xoa_url_.Tid_inet:			return Exec_url_http(app);									// http://site.org
			case Xoa_url_.Tid_anch:			return Exec_url_anchor(win);								// #anchor
			case Xoa_url_.Tid_xcmd:			return Exec_url_xowa(app);									// xowa:app.version or /xcmd/app.version
			case Xoa_url_.Tid_file:			return Exec_url_file(app, wiki, page, win, url.Raw());		// file:///xowa/A.png
			case Xoa_url_.Tid_page:			return Exec_url_page(wiki, url.Orig());						// /wiki/Page
			default:						throw Err_.new_unhandled(url.Tid());
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
		Xog_url_wkr__file wkr = new Xog_url_wkr__file(app, cur_wiki, page);
		wkr.Extract_data(win.Active_html_box().Html_js_eval_proc_as_str(Xog_js_procs.Doc__root_html_get), href_bry);
		wkr.Download_and_run();
		return Rslt_handled;
	}
	private Xoa_url Exec_url_page(Xowe_wiki wiki, byte[] href_bry) {	// EX: "Page"; "/wiki/Page"; // rewritten; DATE:2014-01-19
		return wiki.Utl__url_parser().Parse(href_bry);
	}
	public static Xoa_url Rslt_handled = null;
	public static Xoa_url Exec_url(Xog_win_itm win, String href_str) {
		Xog_url_wkr url_wkr = new Xog_url_wkr();
		Xoa_url url = url_wkr.Parse(win, href_str);
		return url_wkr.Exec_url(url);
	}
	public static void Get_href_url() {
	}
}
