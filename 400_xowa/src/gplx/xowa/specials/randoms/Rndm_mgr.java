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
package gplx.xowa.specials.randoms; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.core.primitives.*; import gplx.core.brys.fmtrs.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
public class Rndm_mgr implements Rls_able {
	private Rndm_core_tbl core_tbl; private final Rndm_core_row core_row = new Rndm_core_row(); 
	private Rndm_range_tbl rng_tbl;
	private Xowd_page_tbl page_tbl; private Db_stmt stmt__page__random;
	public void Init(Db_conn conn, Xowd_page_tbl page_tbl) {
		core_tbl = new Rndm_core_tbl(conn);
		rng_tbl = new Rndm_range_tbl(conn);
		this.page_tbl = page_tbl;
		conn.Rls_reg(this);
	}
	public void Rebuild(int uid, String where_sql, int interval) {
		int total_count = 0; List_adp rng_list = List_adp_.new_();
		Db_conn conn = page_tbl.conn;
		Db_stmt stmt = conn.Stmt_select_order(page_tbl.Tbl_name(), String_.Ary(page_tbl.Fld_page_id()), String_.Ary_empty, page_tbl.Fld_page_id());
		Db_rdr rdr = stmt.Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				++total_count;	// add to total count first so (total_count % interval) is never 0 for total_count = 0
				if (total_count % interval == 0) {
					int cur_page_id = rdr.Read_int(page_tbl.Fld_page_id());
					rng_list.Add(Int_obj_ref.new_(cur_page_id));
				}
			}
		}
		finally {rdr.Rls();}
		conn.Txn_bgn("rndm_rebuild");
		core_tbl.Upsert(uid, where_sql, total_count, interval);
		int rng_ary_len = rng_list.Count();
		rng_tbl.Insert_bgn(uid);
		for (int i = 0; i < rng_ary_len; ++i) {
			Int_obj_ref rng_itm = (Int_obj_ref)rng_list.Get_at(i);
			rng_tbl.Insert_itm(uid, i + 1, rng_itm.Val());
		}
		rng_tbl.Insert_end();
		conn.Txn_end();
	}
	public int Get_rndm_page(int uid) {	// EX: ns=0;type_is_redirect=0
		synchronized (core_row) {core_tbl.Select(core_row, uid);}
		int rndm_num = RandomAdp_.new_().Next(core_row.total);
		int rng_idx = rndm_num / core_row.interval;
		int itm_idx = rndm_num % core_row.interval;
		int page_id_bgn = rng_idx == 0 ? 0 : rng_tbl.Select_or_neg_1(uid, rng_idx);
		int rv = Select_by_offset(core_row.where_sql, page_id_bgn, itm_idx);
		return rv;
	}
	private int Select_by_offset(String where_sql, int page_id_bgn, int offset) {
		Bry_bfr bfr = Bry_bfr.new_();
		where_sql = fmt_where.Fmt_(where_sql).Bld_many_to_str(bfr, page_tbl.Fld_page_ns(), page_tbl.Fld_is_redirect());
		String sql = fmt_sql.Bld_many_to_str(bfr, page_tbl.Tbl_name(), page_tbl.Fld_page_id(), where_sql, page_id_bgn, offset);
		Db_rdr rdr = Db_rdr_.Empty;
		try {
			rdr = page_tbl.conn.Exec_rdr(sql);
			return rdr.Move_next() ? rdr.Read_int(page_tbl.Fld_page_id()) : -1;
		}
		finally {rdr.Rls();}
	}
	public void Rls() {
		stmt__page__random = Db_stmt_.Rls(stmt__page__random);
	}
	private static final Bry_fmt fmt_sql = Bry_fmt.New(String_.Concat_lines_nl_skip_last
	( "SELECT  p.~{page_id}"
	, "FROM    ~{page} p"
	, "WHERE   p.~{~page_id} > ~{page_id_bgn}"
	, "~{where_sql}"
	, "ORDER BY ~{page_id}"
	, "LIMIT 1"
	, "OFFSET  ~{offset};"
	), "page", "page_id", "where_sql", "page_id_bgn", "offset");
	private static final Bry_fmt fmt_where = Bry_fmt.New("", "page_namespace", "page_is_redirect");
}
