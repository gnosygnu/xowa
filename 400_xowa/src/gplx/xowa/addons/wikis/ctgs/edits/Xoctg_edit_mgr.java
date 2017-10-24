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
package gplx.xowa.addons.wikis.ctgs.edits; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
import gplx.dbs.*;
import gplx.xowa.parsers.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.domains.*;
import gplx.xowa.addons.wikis.ctgs.dbs.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.langs.*;
import gplx.xowa.addons.wikis.directorys.specials.items.bldrs.*;
public class Xoctg_edit_mgr {
	public static void Update(Xowe_wiki wiki, byte[] ttl_bry, int page_id, Xoa_ttl[] ctg_ttls) {
		// get ttl, page, ns_id
		Xoa_ttl ttl = wiki.Ttl_parse(ttl_bry);
		int ns_id = ttl.Ns().Id();
		Xoae_page wpg = Xoae_page.New_edit(wiki, ttl);
		wpg.Db().Page().Id_(page_id);

		// delete old categories
		Delete(wiki, ns_id, page_id);

		// insert new categories
		// get page_tbl
		Xow_db_mgr db_mgr = wiki.Data__core_mgr();
		Xow_db_file core_db = db_mgr.Db__core();
		Xowd_page_tbl page_tbl = core_db.Tbl__page();

		// get cat_core_tbl
		Xowd_cat_core_tbl cat_core_tbl = Xodb_cat_db_.Get_cat_core_or_fail(db_mgr);

		// get last cat_link_tbl; note that
		// * cat_link tbls are organized by page
		// * all old cat_links have been deleted
		// * new catlinks will go into last db
		Xow_db_file last_cat_link_db = db_mgr.Dbs__get_for_create(Xow_db_file_.Tid__cat_link, ns_id);
		Xodb_cat_link_tbl last_cat_link_tbl = new Xodb_cat_link_tbl(last_cat_link_db.Conn());

		// get last text tbl for new categories; EX: [[Category:New]] will create entry in page_tb with text_db_id set to last_text_db
		Xow_db_file last_text_db = db_mgr.Dbs__get_for_create(Xow_db_file_.Tid__text, ns_id);
		Xowd_text_tbl last_text_tbl = new Xowd_text_tbl(last_text_db.Conn(), db_mgr.Props().Schema_is_1(), db_mgr.Props().Zip_tid_text());

		// get some variables for creating cat_link rows
		int timestamp = (int)Datetime_now.Get().Timestamp_unix();
		Xoctg_collation_mgr collation_mgr = wiki.Ctg__catpage_mgr().Collation_mgr();
		Xowd_page_itm tmp_page = new Xowd_page_itm();

		// loop over each category listed on page
		for (Xoa_ttl ctg_ttl : ctg_ttls) {
			// get page_tbl data for sub_cat
			boolean exists = page_tbl.Select_by_ttl(tmp_page, ctg_ttl);
			int ctg_id = tmp_page.Id();

			// create category if it doesn't exist
			if (!exists) {
				// create [[Category]] page
				ctg_id = Xopg_db_mgr.Create
					( page_tbl, last_text_tbl, last_text_db.Id(), core_db.Tbl__ns(), core_db.Tbl__cfg()
					, gplx.xowa.wikis.nss.Xow_ns_.Tid__category, ctg_ttl.Page_db(), Bry_.Empty
					, last_cat_link_db.Id()); // NOTE: new categories go into last cat_link_db

				// create cat_core row
				cat_core_tbl.Insert_bgn();
				cat_core_tbl.Insert_cmd_by_batch(ctg_id, 0, 0, 0, Bool_.N_byte, -1);
				cat_core_tbl.Insert_end();
			}

			// get cat_core_itm
			Xowd_category_itm cat_core_itm = cat_core_tbl.Select(ctg_id);

			// adjust it and save it
			cat_core_itm.Adjust(ns_id, 1);
			cat_core_tbl.Update(cat_core_itm);

			// add to cat_link tbl
			last_cat_link_tbl.Insert_(page_id, ctg_id, Xoa_ctg_mgr.To_tid_by_ns(ns_id), timestamp, collation_mgr.Get_sortkey(wpg.Ttl().Page_db()), wpg.Ttl().Page_db());
		}

		// update page.cat_db_id
		page_tbl.Update__cat_db_id(page_id, last_cat_link_db.Id());
	}
	public static void Delete(Xowe_wiki wiki, int ns_id, int page_id) {
		Xow_db_mgr db_mgr = wiki.Data__core_mgr();
		boolean ns_id_is_category = ns_id == Xow_ns_.Tid__category;

		// get cat_core_tbl
		Xowd_cat_core_tbl cat_core_tbl = Xodb_cat_db_.Get_cat_core_or_fail(db_mgr);

		// get cat_link_tbls
		Xow_db_file[] cat_link_dbs = db_mgr.Dbs__get_ary(Xow_db_file_.Tid__cat_link, ns_id);

		// loop cat_link tbls to find linked categories
		for (Xow_db_file cat_link_db : cat_link_dbs) {
			Xodb_cat_link_tbl cat_link_tbl = new Xodb_cat_link_tbl(cat_link_db.Conn());
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

		// set cat_db_id to -1
		db_mgr.Tbl__page().Update__cat_db_id(page_id, -1);
	}
	public static void Update_page_id(Xow_db_mgr db_mgr, int ns_id, int old_id, int new_id) {
		boolean ns_id_is_category = ns_id == Xow_ns_.Tid__category;

		// get cat_link_tbls
		Xow_db_file[] cat_link_dbs = db_mgr.Dbs__get_ary(Xow_db_file_.Tid__cat_link, ns_id);

		// loop cat_link tbls to find linked categories
		for (Xow_db_file cat_link_db : cat_link_dbs) {
			Xodb_cat_link_tbl cat_link_tbl = new Xodb_cat_link_tbl(cat_link_db.Conn());

			// delete cat_links
			cat_link_tbl.Update_page_id_for_pages(old_id, new_id);
			if (ns_id_is_category)
				cat_link_tbl.Update_page_id_for_cats(old_id, new_id);
		}

		// update cat_core
		if (ns_id_is_category) {
			Xowd_cat_core_tbl cat_core_tbl = Xodb_cat_db_.Get_cat_core_or_fail(db_mgr);
			cat_core_tbl.Update_page_id(old_id, new_id);
		}
	}
}
