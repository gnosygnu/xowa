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
package gplx.xowa.addons.wikis.ctgs.edits; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
import gplx.dbs.*;
import gplx.xowa.parsers.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.domains.*;
import gplx.xowa.addons.wikis.ctgs.dbs.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.langs.*;
import gplx.xowa.addons.wikis.directorys.specials.items.bldrs.*;
public class Xoctg_edit_mgr {
	public static void Update(Xowe_wiki wiki, byte[] ttl_bry, int page_id, Xoa_ttl[] ctg_ttls) {
		// only apply to home or other wiki
		if (!(   wiki.Domain_tid() == Xow_domain_tid_.Tid__other
			||   wiki.Domain_tid() == Xow_domain_tid_.Tid__home))
			return;

		// get page
		Xoa_ttl ttl = wiki.Ttl_parse(ttl_bry);
		int ns_id = ttl.Ns().Id();
		Xoae_page wpg = Xoae_page.New_edit(wiki, ttl);
		wpg.Db().Page().Id_(page_id);

		// get page_tbl
		Xow_db_mgr db_mgr = wiki.Data__core_mgr();
		Xowd_page_tbl page_tbl = db_mgr.Db__core().Tbl__page();

		// get cat_core_tbl
		Xowd_cat_core_tbl cat_core_tbl = Xodb_cat_db_.Get_cat_core_or_fail(db_mgr);

		// get cat_link_tbl
		Xodb_cat_link_tbl cat_link_tbl = new Xodb_cat_link_tbl(cat_core_tbl.Conn());
		Xowd_page_itm tmp_page = new Xowd_page_itm();
		db_mgr.Tbl__page().Select_by_id(tmp_page, page_id);

		// delete old categories
		Delete(wiki, ns_id, page_id);

		// get some variables
		int timestamp = (int)Datetime_now.Get().Timestamp_unix();
		Xoctg_collation_mgr collation_mgr = wiki.Ctg__catpage_mgr().Collation_mgr();
		Xow_db_file core_db = db_mgr.Db__core();

		// cat_core:update; for each category, add one to count_page, count_file, or count_subcs
		int ctg_ttls_len = ctg_ttls.length;
		cat_link_tbl.Insert_bgn();
		for (int i = 0; i < ctg_ttls_len; i++) {
			// get cat_core itm for sub_cat
			Xoa_ttl sub_ttl = ctg_ttls[i];
			boolean exists = page_tbl.Select_by_ttl(tmp_page, sub_ttl);

			// create category if it doesn't exist
			int sub_id = tmp_page.Id();
			if (!exists) {
				sub_id = Xopg_db_mgr.Create
					( core_db.Tbl__page(), core_db.Tbl__text(), core_db.Tbl__ns(), core_db.Tbl__cfg()
					, gplx.xowa.wikis.nss.Xow_ns_.Tid__category, sub_ttl.Page_db(), Bry_.Empty, -1);
				cat_core_tbl.Insert_bgn();
				cat_core_tbl.Insert_cmd_by_batch(sub_id, 0, 0, 0, Bool_.N_byte, -1);
				cat_core_tbl.Insert_end();
			}

			Xowd_category_itm sub_core_itm = cat_core_tbl.Select(sub_id);

			// adjust it and save it
			sub_core_itm.Adjust(ns_id, 1);
			cat_core_tbl.Update(sub_core_itm);

			// cat_link:add
			cat_link_tbl.Insert_cmd_by_batch(page_id, sub_id, Xoa_ctg_mgr.To_tid_by_ns(ns_id), timestamp, collation_mgr.Get_sortkey(wpg.Ttl().Page_db()), wpg.Ttl().Page_db());
		}
		cat_link_tbl.Insert_end();

		// update page.cat_db_id
		page_tbl.Update__cat_db_id(page_id, core_db.Id());
	}
	public static void Delete(Xowe_wiki wiki, int ns_id, int page_id) {
		boolean ns_id_is_category = ns_id == Xow_ns_.Tid__category;

		// get cat_core_tbl
		Xowd_cat_core_tbl cat_core_tbl = Xodb_cat_db_.Get_cat_core_or_fail(wiki.Data__core_mgr());

		// get cat_link_tbls
		Xodb_cat_link_tbl[] cat_link_tbls = Xodb_cat_link_tbl.Get_catlink_tbls(wiki.Data__core_mgr());

		// loop cat_link tbls to find linked categories
		for (Xodb_cat_link_tbl cat_link_tbl : cat_link_tbls) {
			Xodb_cat_link_row[] cat_link_rows = cat_link_tbl.Select_by_page_id(page_id);
			// loop linked categories
			for (Xodb_cat_link_row cat_link_row : cat_link_rows) {
				// get cat_core_itm
				Xowd_category_itm sub_core_itm = cat_core_tbl.Select(cat_link_row.Cat_id());

				// subtract one and save it
				sub_core_itm.Adjust(ns_id, -1);
				cat_core_tbl.Update(sub_core_itm);
			}

			// delete cat_links
			cat_link_tbl.Delete_pages(page_id);
			if (ns_id_is_category)
				cat_link_tbl.Delete_cats(page_id);
		}

		// delete cat_core
		if (ns_id_is_category) {
			cat_core_tbl.Delete(page_id);
		}
	}
	public static void Update_page_id(Xowe_wiki wiki, int ns_id, int old_id, int new_id) {
		boolean ns_id_is_category = ns_id == Xow_ns_.Tid__category;

		// get cat_link_tbls
		Xodb_cat_link_tbl[] cat_link_tbls = Xodb_cat_link_tbl.Get_catlink_tbls(wiki.Data__core_mgr());

		// loop cat_link tbls to find linked categories
		for (Xodb_cat_link_tbl cat_link_tbl : cat_link_tbls) {
			// delete cat_links
			cat_link_tbl.Update_page_id_for_pages(old_id, new_id);
			if (ns_id_is_category)
				cat_link_tbl.Update_page_id_for_cats(old_id, new_id);
		}

		// update cat_core
		if (ns_id_is_category) {
			Xowd_cat_core_tbl cat_core_tbl = Xodb_cat_db_.Get_cat_core_or_fail(wiki.Data__core_mgr());
			cat_core_tbl.Update_page_id(old_id, new_id);
		}
	}
}
