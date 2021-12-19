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
package gplx.xowa.wikis.pages;

import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.core.net.qargs.Gfo_qarg_itm;
import gplx.core.net.qargs.Gfo_qarg_mgr;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoa_app;
import gplx.xowa.Xoa_app_;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoa_url;
import gplx.xowa.Xoa_url_;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.Xowe_wiki_;
import gplx.xowa.addons.wikis.pages.syncs.core.Xosync_read_mgr;
import gplx.xowa.guis.views.Xog_tab_itm;
import gplx.xowa.wikis.data.tbls.Xowd_page_itm;
import gplx.xowa.wikis.pages.dbs.Xopg_db_page;

public class Xowe_page_mgr {
	private final Xowe_wiki wiki;
	private final BryWtr tmp_bfr = BryWtr.New();
	private final Gfo_qarg_mgr tmp_qarg_mgr = new Gfo_qarg_mgr();
	public Xowe_page_mgr(Xowe_wiki wiki) {this.wiki = wiki;}
	public Xosync_read_mgr Sync_mgr() {return read_mgr;} private final Xosync_read_mgr read_mgr = new Xosync_read_mgr();
	public void Init_by_wiki(Xowe_wiki wiki) {
		read_mgr.Init_by_wiki(wiki);
	}

	public Xoae_page Load_page(Xoa_url url, Xoa_ttl ttl, Xog_tab_itm tab) {	// NOTE: called by GUI and HTTP_SERVER; not called by MASS_PARSE
		Xoa_app_.Usr_dlg().Log_many("", "", "page.load: url=~{0}", url.To_str());			
		Wait_for_popups(wiki.App());
		Xowe_wiki_.Rls_mem_if_needed(wiki);

		// handle curid query_arg; EX:en.wikipedia.org/wiki/?curid=303 DATE:2017-02-15
		Gfo_qarg_itm[] qarg_ary = url.Qargs_ary();
		// if qargs exist...
		if (qarg_ary.length > 0) {
			try {
				tmp_qarg_mgr.Init(qarg_ary);
				byte[] curid_bry = tmp_qarg_mgr.Read_bry_or(Xoa_url_.Qarg__curid, null);
				// if "curid" qarg exists....
				if (curid_bry != null) {
					int curid = BryUtl.ToIntOr(curid_bry, -1);
					Xowd_page_itm tmp_page = wiki.Data__core_mgr().Db__core().Tbl__page().Select_by_id_or_null(curid);
					// if curid exists in page tbl...
					if (tmp_page != null) {
						ttl = wiki.Ttl_parse(tmp_page.Ns_id(), tmp_page.Ttl_page_db());
						// handle "home/wiki/?curid=123"; XO automatically changes to "home/wiki/Main_Page?curid=123"; change back to "home/wiki/?curid=123"
						if (url.Page_is_main()) {
							url.Page_bry_(BryUtl.Empty);
						}
					}
				}
			} catch (Exception exc) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to handle cur_id; url=~{0} err=~{1}", url.Raw(), ErrUtl.ToStrLog(exc));
			}
		}

		// load page meta; wait_for_popups
		Xoae_page page = wiki.Data_mgr().Load_page_and_parse(url, ttl, wiki.Lang(), tab, false);
		ttl = page.Ttl();	// note that Load_page_and_parse can redirect ttl; EX: Special:Random -> A; DATE:2017-01-05
		Wait_for_popups(wiki.App());

		// auto-update
		Xoa_ttl redirect_ttl = read_mgr.Auto_update(wiki, page, ttl);
		if (redirect_ttl != null) {
			// page-sync occurred; update ttl to handle any redirection; DATE:2017-05-07
			ttl = redirect_ttl;

			// reload metadata, needed to pick up Html_db_id; DATE:2017-03-13
			page = wiki.Data_mgr().Load_page_and_parse(url, ttl, wiki.Lang(), tab, false);
		}

		// load from html_db
		boolean from_html_db = page.Db().Page().Html_db_id() != Xopg_db_page.HTML_DB_ID_NULL;
		boolean read_from_html_db_preferred = wiki.Html__hdump_mgr().Load_mgr().Read_preferred();
		boolean isCategoryPage = ttl.Ns().Id_is_ctg();
		if (from_html_db) {
			if (read_from_html_db_preferred) {
				wiki.Html__hdump_mgr().Load_mgr().Load_by_xowe(page, !isCategoryPage); // NOTE: if loading for html_db, do not build page_box; will be built below; ISSUE#:722; DATE:2020-05-17
				int html_len = BryUtl.Len(page.Db().Html().Html_bry());
				from_html_db = html_len > 0;	// NOTE: archive.org has some wtxt_dbs which included page|html_db_id without actual html_dbs; DATE:2016-06-22
				Gfo_usr_dlg_.Instance.Log_many("", "", "page_load: loaded html; page=~{0} html_len=~{1}", ttl.Full_db(), html_len);
			}
			else
				from_html_db = false;
		}

		// load from wtxt_db; occurs if (a) no html_db_id; (b) option says to use wtxt db; (c) html_db_id exists, but no html_db;
		if (!from_html_db) {
			wiki.Parser_mgr().Parse(page, false);

			// load from html_db if no wtxt found and option just marked as not read_preferred
			if (	BryUtl.IsNullOrEmpty(page.Db().Text().Text_bry())				// no wtxt found
				&&	!ttl.Ns().Id_is_special()								// skip special
				&&	!read_from_html_db_preferred							// read preferred not marked
				) {
				wiki.Html__hdump_mgr().Load_mgr().Load_by_xowe(page, true);
				from_html_db = BryUtl.IsNotNullOrEmpty(page.Db().Html().Html_bry());
			}
			else {
				Gfo_usr_dlg_.Instance.Log_many("", "", "page_load: loaded wikitext; page=~{0} wikitext_len=~{1}", ttl.Full_db(), page.Db().Text().Text_bry().length);
			}
		}
		page.Html_data().Hdump_exists_(from_html_db);

		// if [[Category]], generate catlinks (subc; page; file)
		if (isCategoryPage) {
			wiki.Ctg__catpage_mgr().Write_catpage(tmp_bfr, page);
			if (from_html_db) {
				wiki.Ctg__pagebox_wtr().Write_pagebox(tmp_bfr, page);
				page.Db().Html().Html_bry_(BryUtl.Add(page.Db().Html().Html_bry(), tmp_bfr.ToBryAndClear()));
			}
			else {
				page.Html_data().Catpage_data_(tmp_bfr.ToBryAndClear());
			}
		}

		return page;
	}
	private static void Wait_for_popups(Xoa_app app) {// HACK: wait for popups to finish, else thread errors due to popups and loader mutating cached items
		if (app.Mode().Tid_is_http()) return;
		int wait_count = 0;
		while (gplx.xowa.htmls.modules.popups.Xow_popup_mgr.Running() && ++wait_count < 100)
			gplx.core.threads.Thread_adp_.Sleep(10);
	}
}
