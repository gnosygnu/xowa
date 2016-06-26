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
	public Xowe_page_mgr(Xowe_wiki wiki) {this.wiki = wiki;}
	public Xoae_page Load_page(Xoa_url url, Xoa_ttl ttl, Xog_tab_itm tab) {
		Xoa_app_.Usr_dlg().Log_many("", "", "page.load: url=~{0}", url.To_str());			
		Wait_for_popups(wiki.App());
		Xowe_wiki_.Rls_mem_if_needed(wiki);

		// load page meta; wait_for_popups
		Xoae_page page = wiki.Data_mgr().Load_page_and_parse(url, ttl, wiki.Lang(), tab, false);
		boolean hdump_exists = page.Revision_data().Html_db_id() != -1 && wiki.Appe().Api_root().Wiki().Hdump().Read_preferred();
		page.Html_data().Hdump_exists_(hdump_exists);
		Wait_for_popups(wiki.App());

		// load page text
		boolean parse = true;
		if (hdump_exists) {
			wiki.Html__hdump_mgr().Load_mgr().Load_by_edit(page);
			parse = Bry_.Len_eq_0(page.Hdump_data().Body());		// NOTE: need to check if actually empty for archive.org wikis which included html_db_id without html_dbs; DATE:2016-06-22
		}
		if (parse)
			wiki.Parser_mgr().Parse(page, false);
		return page;
	}
	private static void Wait_for_popups(Xoa_app app) {// HACK: wait for popups to finish, else thread errors due to popups and loader mutating cached items
		if (app.Mode().Tid_is_http()) return;
		int wait_count = 0;
		while (gplx.xowa.htmls.modules.popups.Xow_popup_mgr.Running() && ++wait_count < 100)
			gplx.core.threads.Thread_adp_.Sleep(10);
	}
}
