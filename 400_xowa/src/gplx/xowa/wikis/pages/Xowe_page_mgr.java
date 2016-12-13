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
package gplx.xowa.wikis.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.guis.views.*;
public class Xowe_page_mgr {
	private final    Xowe_wiki wiki;
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public Xowe_page_mgr(Xowe_wiki wiki) {this.wiki = wiki;}
	public Xoae_page Load_page(Xoa_url url, Xoa_ttl ttl, Xog_tab_itm tab) {
		Xoa_app_.Usr_dlg().Log_many("", "", "page.load: url=~{0}", url.To_str());			
		Wait_for_popups(wiki.App());
		Xowe_wiki_.Rls_mem_if_needed(wiki);

		// load page meta; wait_for_popups
		Xoae_page page = wiki.Data_mgr().Load_page_and_parse(url, ttl, wiki.Lang(), tab, false);
		Wait_for_popups(wiki.App());

		// auto-update
		gplx.xowa.addons.wikis.pages.syncs.core.Xosync_read_mgr read_mgr = new gplx.xowa.addons.wikis.pages.syncs.core.Xosync_read_mgr();
		read_mgr.Auto_update(wiki, page, ttl);

		// load page from html_db
		boolean from_html_db = page.Db().Page().Html_db_id() != -1;
		boolean read_from_html_db_preferred = wiki.Html__hdump_mgr().Load_mgr().Read_preferred();
		if (from_html_db) {
			if (read_from_html_db_preferred) {
				wiki.Html__hdump_mgr().Load_mgr().Load_by_edit(page);
				from_html_db = Bry_.Len_gt_0(page.Db().Html().Html_bry());	// NOTE: archive.org has some wtxt_dbs which included page|html_db_id without actual html_dbs; DATE:2016-06-22
			}
			else
				from_html_db = false;
		}

		// load page from wtxt_db; occurs if (a) no html_db_id; (b) option says to use wtxt db; (c) html_db_id exists, but no html_db;
		if (!from_html_db) {
			wiki.Parser_mgr().Parse(page, false);

			// load from html_dbs if no wtxt found and option just marked as not read_preferred
			if (	Bry_.Len_eq_0(page.Db().Text().Text_bry())				// no wtxt found
				&&	!ttl.Ns().Id_is_special()								// skip special
				&&	!read_from_html_db_preferred							// read preferred not marked
				) {
				wiki.Html__hdump_mgr().Load_mgr().Load_by_edit(page);
				from_html_db = Bry_.Len_gt_0(page.Db().Html().Html_bry());	
			}
		}
		page.Html_data().Hdump_exists_(from_html_db);

		// if [[Category]], generate catlinks (subc; page; file)
		if (ttl.Ns().Id_is_ctg()) {
			wiki.Ctg__catpage_mgr().Write_catpage(tmp_bfr, page);
			if (from_html_db)
				page.Db().Html().Html_bry_(Bry_.Add(page.Db().Html().Html_bry(), tmp_bfr.To_bry_and_clear()));
			else
				page.Html_data().Catpage_data_(tmp_bfr.To_bry_and_clear());
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
