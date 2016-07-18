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
package gplx.xowa.addons.bldrs.mass_parses.parses; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*;
class Xomp_page_pool_loader {
	private final    Xow_wiki wiki;
	private final    int num_pages_per_load;
	private final    Db_attach_mgr attach_mgr;
	private int prv_page_id = -1;
	public Xomp_page_pool_loader(Xow_wiki wiki, Db_conn make_conn, int num_pages_per_load) {
		this.wiki = wiki;
		this.make_conn = make_conn;
		this.attach_mgr = new Db_attach_mgr(make_conn);
		this.num_pages_per_load  = num_pages_per_load;
	}
	public Db_conn Conn() {return make_conn;} private final    Db_conn make_conn;
	public int Get_pending_count() {
		Db_rdr rdr = make_conn.Stmt_sql("SELECT Count(*) AS Count_of FROM xomp_page mp WHERE mp.page_status = 0").Exec_select__rls_auto();
		try {
			return rdr.Move_next() ? rdr.Read_int("Count_of") : 0;
		} finally {rdr.Rls();}
	}
	public List_adp Load(List_adp list, int list_idx, int list_len) {
		List_adp rv = List_adp_.New();
		// add remaining pages from old pool to new_pool;
		for (int i = list_idx; i < list_len; ++i) {
			rv.Add((Xomp_page_itm)list.Get_at(i));
		}
		
		// load pages into new pool
		this.Load_from_db(rv);
		return rv;
	}
	private void Load_from_db(List_adp list) {
		// prepare for page_tbl
		String sql = String_.Format(String_.Concat_lines_nl_skip_last	// ANSI.Y
		( "SELECT  mp.page_id"
		, ",       pp.page_namespace"
		, ",       pp.page_title"
		, ",       pp.page_text_db_id"
		, "FROM    xomp_page mp"
		, "        JOIN <page_db>page pp ON mp.page_id = pp.page_id"
		, "WHERE   mp.page_id > {0}"
		, "AND     mp.page_status = 0"
		, "LIMIT   {1}"
		), prv_page_id, num_pages_per_load);
		this.attach_mgr.Conn_others_(new Db_attach_itm("page_db", wiki.Data__core_mgr().Db__core().Conn()));
		sql = attach_mgr.Resolve_sql(sql);

		// run page_tbl
		Xomp_text_db_loader text_db_loader = new Xomp_text_db_loader(wiki);
		attach_mgr.Attach();
		Db_rdr rdr = make_conn.Stmt_sql(sql).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				prv_page_id = rdr.Read_int("page_id");
				int text_db_id = rdr.Read_int("page_text_db_id");
				Xomp_page_itm ppg = new Xomp_page_itm(prv_page_id);
				ppg.Init_by_page
				( rdr.Read_int("page_namespace")
				, rdr.Read_bry_by_str("page_title")
				, text_db_id
				);
				list.Add(ppg);
				text_db_loader.Add(text_db_id, ppg);
			}
		} finally {rdr.Rls();}
		attach_mgr.Detach();

		text_db_loader.Load();
	}
}
