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
class Xomp_make_wkr {
	private final    Db_conn mgr_conn;
	private final    Xomp_html_db_wtr html_db_wtr;
	private final    Xomp_html_db_rdr html_db_rdr;
	private final    Int_flag_bldr src_body_flag_bldr = Xowd_html_tbl.Make_body_flag_bldr();
	public Xomp_make_wkr(Xowe_wiki wiki) {
		this.db = Xomp_db_core.New__load(wiki);
		this.mgr_conn = db.Mgr_db().Conn();
		this.html_db_rdr = new Xomp_html_db_rdr(wiki);
		this.html_db_wtr = new Xomp_html_db_wtr(wiki);
	}
	public Xomp_db_core Db() {return db;} private Xomp_db_core db;
	public void Exec() {
		Xowd_html_row src_row = new Xowd_html_row();

		int[] ns_ary = new int[] {0, 4, 14};
		int ns_ary_len = ns_ary.length;
		for (int i = 0; i < ns_ary_len; ++i) {
			int ns_id = ns_ary[i];
			String sql = String_.Format("SELECT * FROM xomp_page WHERE page_ns = {0} AND html_len != 0 ORDER BY page_id;", ns_id);	// NOTE: html_len == 0 when page failed
			int count = 0;
			Db_rdr rdr = mgr_conn.Stmt_sql(sql).Exec_select__rls_auto();	// ANSI.Y
			try {
				while (rdr.Move_next()) {
					Make_page(rdr, src_row, ns_id);
					if (++count % 10000 == 0)
						Gfo_usr_dlg_.Instance.Prog_many("", "", "xomp.html.insert: ns=~{0} db=~{1} count=~{2}", Int_.To_str_pad_bgn_space(ns_id, 3), Int_.To_str_pad_bgn_space(html_db_wtr.Cur_db_id(), 3), Int_.To_str_pad_bgn_space(count, 8));
				}
			} finally {rdr.Rls();}
		}

		this.Rls();
	}
	private void Make_page(Db_rdr rdr, Xowd_html_row src_row, int ns_id) {
		// get src_row
		int page_id = rdr.Read_int("page_id");
		int html_len = rdr.Read_int("html_len");
		int wkr_id = rdr.Read_int("xomp_wkr_id");
		html_db_rdr.Rows__get(src_row, wkr_id, page_id);
		src_body_flag_bldr.Decode(src_row.Body_flag());

		// get trg_tbl and write
		Xowd_html_tbl trg_tbl = html_db_wtr.Tbls__get_or_new(ns_id, html_len);
		trg_tbl.Insert(src_row.Page_id(), src_row.Head_flag(), src_body_flag_bldr.Get_as_int(0), src_body_flag_bldr.Get_as_int(1), src_row.Display_ttl(), src_row.Content_sub(), src_row.Sidebar_div(), src_row.Body());
	}
	private void Rls() {
		mgr_conn.Rls_conn();
		html_db_rdr.Rls();
		html_db_wtr.Rls();
	}
}
