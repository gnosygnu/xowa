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
package gplx.xowa.apps.servers.http;

import gplx.libs.ios.IoConsts;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import gplx.core.envs.Runtime_;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoa_url;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.Xowe_wiki_;
import gplx.xowa.apps.servers.Gxw_html_server;
import gplx.xowa.guis.views.Xog_tab_itm;
import gplx.xowa.specials.Xow_special_meta_;
import gplx.xowa.specials.xowa.errors.Xoerror_special;

public class Http_server_page {
	private final Xoae_app app;
	public Http_server_page(Xoae_app app) {
		this.app = app;
	}
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public Xoa_url Url() {return url;} private Xoa_url url;
	public Xoa_ttl Ttl() {return ttl;} private Xoa_ttl ttl;
	public byte[] Ttl_bry() {return ttl_bry;} private byte[] ttl_bry;
	public Xog_tab_itm Tab() {return tab;} private Xog_tab_itm tab;
	public Xoae_page Page() {return page;} private Xoae_page page;
	public String Html() {return html;} private String html;
	public byte[] Redirect() {return redirect;} private byte[] redirect;
	public static Http_server_page Make(Xoae_app app, Http_data__client data__client, byte[] wiki_domain, byte[] ttl_bry, byte[] qarg, byte retrieve_mode, byte mode, boolean popup_enabled, String popup_mode, String popup_id) {
		Http_server_page page = new Http_server_page(app);
		if (!page.Make_url(wiki_domain, ttl_bry, qarg)) return page; // exit early if xwiki
		page.Make_page(data__client);
		page.Make_html(retrieve_mode, mode, popup_enabled, popup_mode, popup_id);
		return page;
	}
	public boolean Make_url(byte[] wiki_domain, byte[] ttl_bry_arg, byte[] qarg) {
		// get wiki
		wiki = (Xowe_wiki)app.Wiki_mgr().Get_by_or_make_init_y(wiki_domain); // assert init for Main_Page; EX:click zh.w on wiki sidebar; DATE:2015-07-19
		if (!wiki.Installed()) {
			this.ttl = wiki.Ttl_parse(Xow_special_meta_.Itm__error.Ttl_bry());
			this.url = wiki.Utl__url_parser().Parse(Xoerror_special.Make_url__invalidWiki(wiki_domain));
			return true;
		}
		if (Runtime_.Memory_total() > IoConsts.LenGB) Xowe_wiki_.Rls_mem(wiki, true); // release memory at 1 GB; DATE:2015-09-11

		// get url
		// empty title returns main page; EX: "" -> "Main_Page"
		this.ttl_bry = ttl_bry_arg;
		if (BryUtl.IsNullOrEmpty(ttl_bry)) {
			this.ttl_bry = wiki.Props().Main_page();
		}
		// generate ttl of domain/wiki/page; needed for pages with leading slash; EX: "/abcd" -> "en.wikipedia.org/wiki//abcd"; ISSUE#:301; DATE:2018-12-16
		else {
			BryWtr tmp_bfr = wiki.Utl__bfr_mkr().GetM001();
			try {
				tmp_bfr.Add(wiki.Domain_bry()).Add(gplx.xowa.htmls.hrefs.Xoh_href_.Bry__wiki).Add(ttl_bry).AddSafe(qarg);
				this.ttl_bry = tmp_bfr.ToBryAndClear();
			} finally {tmp_bfr.MkrRls();}
		}

		// get url
		this.url = wiki.Utl__url_parser().Parse(ttl_bry);
		if (!BryLni.Eq(url.Wiki_bry(), wiki.Domain_bry())) { // handle xwiki; EX: en.wikipedia.org/wiki/it:Roma; ISSUE#:600; DATE:2019-11-02
			this.redirect = url.To_bry();
			return false;
		}

		// get ttl
		this.ttl = wiki.Ttl_parse(url.To_bry_page_w_anch()); // changed from ttl_bry to page_w_anch; DATE:2017-07-24
		if (ttl == null) { // handle invalid titles like "Earth]"; ISSUE#:480; DATE:2019-06-02
			this.ttl = wiki.Ttl_parse(Xow_special_meta_.Itm__error.Ttl_bry());
			this.url = wiki.Utl__url_parser().Parse(Xoerror_special.Make_url__invalidTitle(ttl_bry));
		}
		return true;
	}
	public void Make_page(Http_data__client data__client) {
		// get the page
		this.tab = Gxw_html_server.Assert_tab2(app, wiki);	// HACK: assert tab exists
		this.page = wiki.Page_mgr().Load_page(url, ttl, tab);
		app.Gui_mgr().Browser_win().Active_page_(page);	// HACK: init gui_mgr's page for output (which server ordinarily doesn't need)
		if (page.Db().Page().Exists_n()) { // if page does not exist, replace with message; else null_ref error; DATE:2014-03-08
			page.Db().Text().Text_bry_(BryUtl.NewA7("'''Page not found.'''"));
			wiki.Parser_mgr().Parse(page, false);
		}
		page.Html_data().Head_mgr().Itm__server().Init_by_http(data__client).Enabled_y_();
	}
	public void Make_html(byte retrieve_mode, byte mode, boolean popup_enabled, String popup_mode, String popup_id) {
		// generate html
		if (popup_enabled) {
			if (StringUtl.Eq(popup_mode, "more"))
				this.html = wiki.Html_mgr().Head_mgr().Popup_mgr().Show_more(popup_id);
			else
				this.html = wiki.Html_mgr().Head_mgr().Popup_mgr().Show_init(popup_id, ttl_bry, ttl_bry);
		}
		else {
			// NOTE: generates HTML, but substitutes xoimg tags for <img>; ISSUE#:686; DATE:2020-06-27
			byte[] page_html = wiki.Html_mgr().Page_wtr_mgr().Gen(page, mode);
			page_html = BryUtlByWtr.ReplaceMany(page_html, app.Fsys_mgr().Root_dir().To_http_file_bry(), Http_server_wkr.Url__fsys);
			this.html = StringUtl.NewU8(page_html); // NOTE: must generate HTML now in order for "wait" and "async_server" to work with text_dbs; DATE:2016-07-10
			switch (retrieve_mode) {
				case File_retrieve_mode.Mode_skip:	// noop
					break;
				case File_retrieve_mode.Mode_async_server:
					app.Gui_mgr().Browser_win().Page__async__bgn(tab);
					break;
				case File_retrieve_mode.Mode_wait:
					gplx.xowa.guis.views.Xog_async_wkr.Async(page, tab.Html_itm());
					break;
			}
			// NOTE: substitutes xoimg tags for actual file; ISSUE#:686; DATE:2020-06-27
			this.html = StringUtl.NewU8(wiki.Html__hdump_mgr().Load_mgr().Parse(page_html, this.page));
		}
	}
}
