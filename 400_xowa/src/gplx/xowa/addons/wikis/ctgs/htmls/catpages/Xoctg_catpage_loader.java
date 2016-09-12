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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.addons.wikis.ctgs.dbs.*;
import gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms.*;
class Xoctg_catpage_loader {
	public Xoctg_catpage_ctg Load_by_ttl_or_null(Xow_wiki wiki, Xoa_ttl cat_ttl) {
		// get cat_id for cat_ttl from page_tbl
		Xow_db_mgr db_mgr = wiki.Data__core_mgr();
		Xowd_page_tbl page_tbl = db_mgr.Db__core().Tbl__page();
		Xowd_page_itm page_itm = page_tbl.Select_by_ttl_as_itm_or_null(cat_ttl);
		if (page_itm == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "category does not exist in page table; ttl=~{0}", cat_ttl.Full_db());
			return null;
		}

		// get cat_link db from cat_core_tbl
		Xowd_cat_core_tbl cat_core_tbl = Xodb_cat_db_.Get_cat_core_or_fail(db_mgr);
		int cat_id = page_itm.Id();
		Xowd_category_itm cat_core_itm = cat_core_tbl.Select(cat_id);
		if (cat_core_itm == Xowd_category_itm.Null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "category does not exist in cat_core table; ttl=~{0}", cat_ttl.Full_db());
			return null;
		}

		// load itms from cat_link_db
		Xoctg_catpage_ctg rv = new Xoctg_catpage_ctg(cat_ttl.Page_txt());
		if (cat_core_itm.File_idx() == -1)	// new v3: loop over all cat_link dbs
			Search_cat_core_tbls_for_cat_id(rv, wiki, db_mgr, page_tbl.Conn(), cat_core_tbl.Conn(), cat_id);
		else {								// old v2: use cat_link_db
			Xow_db_file cat_link_db = db_mgr.Dbs__get_by_id_or_fail(cat_core_itm.File_idx());
			Select_by_cat_id(rv, wiki, page_tbl.Conn(), cat_core_tbl.Conn(), cat_link_db.Conn(), cat_id, Sql_for_v2(cat_id));
		}
		rv.Make_itms();
		return rv;
	}
	private static void Search_cat_core_tbls_for_cat_id(Xoctg_catpage_ctg rv, Xow_wiki wiki, Xow_db_mgr db_mgr, Db_conn page_conn, Db_conn cat_core_conn, int cat_id) {
		// loop over each db unless (a) cat_link_db; (b) core_db (if all or few)
		int len = db_mgr.Dbs__len();
		for (int i = 0; i < len; ++i) {
			Xow_db_file db_file = db_mgr.Dbs__get_at(i);
			switch (db_file.Tid()) {
				case Xow_db_file_.Tid__cat_link:	// always use cat_link db
					break;
				case Xow_db_file_.Tid__core:		// only use core if all or few
					if (db_mgr.Props().Layout_text().Tid_is_lot()) 
						continue;
					else
						break;
				default:							// else ignore all other files
					continue;
			}
			Select_by_cat_id(rv, wiki, page_conn, cat_core_conn, db_file.Conn(), cat_id, Sql_for_v3(cat_id));
		}
	}
	private static void Select_by_cat_id(Xoctg_catpage_ctg rv, Xow_wiki wiki, Db_conn page_conn, Db_conn cat_core_conn, Db_conn cat_link_conn, int cat_id, String sql) {
		// prep sql
		Db_attach_mgr attach_mgr = new Db_attach_mgr(cat_link_conn, new Db_attach_itm("page_db", page_conn), new Db_attach_itm("cat_core_db", cat_core_conn));
		sql = attach_mgr.Resolve_sql(sql);
		attach_mgr.Attach();

		// run sql and create itms based on cat_link
		Db_rdr rdr = Db_rdr_.Empty;
		try {
			rdr = cat_link_conn.Stmt_sql(sql).Exec_select__rls_auto();
			while (rdr.Move_next()) {
				Xoa_ttl page_ttl = wiki.Ttl_parse(rdr.Read_int("page_namespace"), rdr.Read_bry_by_str("page_title"));
				Xoctg_catpage_itm itm = new Xoctg_catpage_itm(rdr.Read_int("cl_from"), page_ttl, Bry_.new_u8(rdr.Read_str("cl_sortkey")));
				rv.Grp_by_tid(rdr.Read_byte("cl_type_id")).Itms__add(itm);
			}
		}
		finally {
			rdr.Rls();
			attach_mgr.Detach();
		}
	}
	private static String Sql_for_v3(int cat_id) {
		return String_.Concat_lines_nl_skip_last	// ANSI.Y
		( "SELECT  cl.cl_from"
		, ",       cl.cl_type_id"
		, ",       p.page_namespace"
		, ",       p.page_title"
		, ",       cs.cs_key AS cl_sortkey"
		, "FROM    cat_link cl"
		, "        JOIN <page_db>page p ON cl.cl_from = p.page_id"
		, "        JOIN <cat_core_db>cat_sort cs ON cl.cl_sortkey_id = cs.cs_id"
		, "WHERE   cl.cl_to_id = " + Int_.To_str(cat_id)
		);
	}
	private static String Sql_for_v2(int cat_id) {	// NOTE: main difference is cl_sortkey is on cat_link, not in cat_sort
		return String_.Concat_lines_nl_skip_last	// ANSI.Y
		( "SELECT  cl.cl_from"
		, ",       cl.cl_type_id"
		, ",       p.page_namespace"
		, ",       p.page_title"
		, ",       cl.cl_sortkey"
		, "FROM    cat_link cl"
		, "        JOIN <page_db>page p ON cl.cl_from = p.page_id"
		, "WHERE   cl.cl_to_id = " + Int_.To_str(cat_id)
		);
	}
}
