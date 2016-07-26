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
package gplx.xowa.addons.bldrs.mass_parses.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.core.brys.*;
import gplx.dbs.*; import gplx.xowa.htmls.core.dbs.*; import gplx.xowa.addons.bldrs.mass_parses.dbs.*; import gplx.xowa.addons.bldrs.files.dbs.*;
import gplx.xowa.bldrs.*;
class Xomp_make_lnki {
	public void Exec(Xowe_wiki wiki, int uid_count) {
		// init
		Xomp_db_core xomp_db = Xomp_db_core.New__load(wiki);

		Xob_db_file make_db = Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir());
		Xob_lnki_temp_tbl lnki_temp_tbl = new Xob_lnki_temp_tbl(make_db.Conn());
		lnki_temp_tbl.Insert_bgn();

		// add index
		int wkr_count = xomp_db.Wkr_count();
		for (int i = 0; i < wkr_count; ++i) {
			Xomp_wkr_db wkr_db = xomp_db.Wkr_db(Bool_.N, i);
			wkr_db.Conn().Meta_idx_assert("lnki_temp", "lnki_page_id", "lnki_page_id");
		}

		// loop
		List_adp rows = List_adp_.New();
		int uid_bgn = -1;
		while (true) {
			int count = 0;
			for (int i = 0; i < wkr_count; ++i) {
				Xomp_wkr_db wkr_db = xomp_db.Wkr_db(Bool_.N, i);
				count += Load_rows(rows, xomp_db, wkr_db, uid_bgn, uid_bgn + uid_count);
			}
			if (count == 0) break;
			Save_rows(rows, lnki_temp_tbl);
		}

		// term
		lnki_temp_tbl.Insert_end();
		xomp_db.Conn().Rls_conn();
		make_db.Conn().Rls_conn();
	}
	private int Load_rows(List_adp rows, Xomp_db_core xomp_db, Xomp_wkr_db wkr_db, int uid_bgn, int uid_end) {
		// build sql
		Db_attach_mgr attach_mgr = new Db_attach_mgr(xomp_db.Conn());
		attach_mgr.Conn_others_(new Db_attach_itm("wkr_db", wkr_db.Conn()));
		String sql = String_.Format(String_.Concat_lines_nl_skip_last
		( "SELECT  mgr.xomp_uid"
		, ",       wkr.*"
		, "FROM    <wkr_db>lnki_temp wkr"
		, "        JOIN xomp_page mgr ON wkr.lnki_page_id = mgr.page_id"
		, "WHERE   mgr.xomp_uid > {0} AND mgr.xomp_uid <= {1}"
		)
		, uid_bgn
		, uid_end
		);	
		sql = attach_mgr.Resolve_sql(sql);

		attach_mgr.Attach();
		Db_rdr rdr = xomp_db.Conn().Stmt_sql(sql).Exec_select__rls_auto();	// ANSI.Y
		int rv = -1;
		try {
			while (rdr.Move_next()) {
				rv = rdr.Read_int("xomp_uid");
				Xob_lnki_temp_row row = new Xob_lnki_temp_row();
				row.Load(rdr, rv);
			}
		} finally {rdr.Rls();}
		attach_mgr.Detach();
		return rv;
	}

	private void Save_rows(List_adp rows, Xob_lnki_temp_tbl lnki_temp_tbl) {
		int len = rows.Len();
		for (int i = 0; i < len; ++i) {
			Xob_lnki_temp_row row = (Xob_lnki_temp_row)rows.Get_at(i);
			lnki_temp_tbl.Insert_cmd_by_batch(row.Lnki_tier_id(), row.Lnki_page_id(), row.Lnki_ttl(), row.Lnki_commons_ttl()
				, row.Lnki_ext(), row.Lnki_src_tid(), row.Lnki_src_tid(), row.Lnki_w(), row.Lnki_h(), row.Lnki_upright()
				, row.Lnki_time(), row.Lnki_page());
		}
	}
}
