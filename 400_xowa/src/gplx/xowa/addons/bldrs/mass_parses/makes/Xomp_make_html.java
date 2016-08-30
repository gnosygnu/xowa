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
import gplx.dbs.*; import gplx.xowa.htmls.core.dbs.*; import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
class Xomp_make_html {
	private final    Int_flag_bldr src_body_flag_bldr = Xowd_html_tbl.Make_body_flag_bldr();
	public void Exec(Xowe_wiki wiki) {
		// init
		Xomp_mgr_db mgr_db = Xomp_mgr_db.New__load(wiki);
		Db_conn mgr_conn = mgr_db.Conn();

		// update wkr_uid; note that this cannot be done in parse_wkr, b/c multiple-writer-errors for xomp.db|page
		int wkrs_len = mgr_db.Wkr_tbl().Select_count();
		for (int i = 0; i < wkrs_len; ++i) {
			Xomp_wkr_db wkr_db = Xomp_wkr_db.New(mgr_db.Dir(), i);
			mgr_db.Page_tbl().Update_wkr_uid(i, wkr_db.Conn());
		}

		// init more
		Xomp_html_db_rdr html_db_rdr = new Xomp_html_db_rdr(wiki);
		Xomp_html_db_wtr html_db_wtr = new Xomp_html_db_wtr(wiki);
		Xowd_html_row src_row = new Xowd_html_row();

		// loop xomp|page and generate html dbs
		String sql = String_.Format("SELECT * FROM xomp_page WHERE html_len != 0 ORDER BY xomp_uid;");	// NOTE: html_len == 0 when page failed
		int count = 0;
		Db_rdr rdr = mgr_conn.Stmt_sql(sql).Exec_select__rls_auto();	// ANSI.Y
		try {
			while (rdr.Move_next()) {
				Make_page(html_db_rdr, rdr, html_db_wtr, src_row);
				if (++count % 10000 == 0)
					Gfo_usr_dlg_.Instance.Prog_many("", "", "xomp.html.insert: db=~{0} count=~{1}", Int_.To_str_pad_bgn_space(html_db_wtr.Cur_db_id(), 3), Int_.To_str_pad_bgn_space(count, 8));
			}
		} finally {rdr.Rls();}

		// cleanup
		mgr_conn.Rls_conn();
		html_db_rdr.Rls();
		html_db_wtr.Rls();
	}
	private void Make_page(Xomp_html_db_rdr html_db_rdr, Db_rdr rdr, Xomp_html_db_wtr html_db_wtr, Xowd_html_row src_row) {
		// get src_row
		int page_id = rdr.Read_int("page_id");
		int html_len = rdr.Read_int("html_len");
		int wkr_id = rdr.Read_int("xomp_wkr_id");
		int ns_id = rdr.Read_int("page_ns");
		html_db_rdr.Rows__get(src_row, wkr_id, page_id);
		src_body_flag_bldr.Decode(src_row.Body_flag());

		// get trg_tbl and write
		Xowd_html_tbl trg_tbl = html_db_wtr.Tbls__get_or_new(ns_id, html_len);
		trg_tbl.Insert(src_row.Page_id(), src_row.Head_flag(), src_body_flag_bldr.Get_as_int(0), src_body_flag_bldr.Get_as_int(1), src_row.Display_ttl(), src_row.Content_sub(), src_row.Sidebar_div(), src_row.Body());
	}
}
