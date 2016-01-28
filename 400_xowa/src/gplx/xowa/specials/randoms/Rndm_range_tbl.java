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
import gplx.core.ios.*; import gplx.dbs.*; import gplx.dbs.utls.*;
public class Rndm_range_tbl implements Rls_able {
	private final String tbl_name = "rng_range"; private final Dbmeta_fld_list flds = Dbmeta_fld_list.new_();
	private final String fld_rng_uid, fld_rng_idx, fld_rng_page_id;
	private final Db_conn conn; private Db_stmt stmt_select, stmt_insert;		
	public Rndm_range_tbl(Db_conn conn) {
		this.conn = conn;
		fld_rng_uid			= flds.Add_int("rng_uid");
		fld_rng_idx			= flds.Add_int("rng_idx");
		fld_rng_page_id 	= flds.Add_int("rng_page_id");
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_unique_by_name(tbl_name, "core", fld_rng_uid, fld_rng_idx)));}
	public void Insert(int uid, int idx, int page_id) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear().Val_int(fld_rng_uid, uid).Val_int(fld_rng_idx, idx).Val_int(fld_rng_page_id, page_id).Exec_insert();
	}
	public void Select(int type) {
//			if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_rng_uid);
//			Db_rdr rdr = stmt_select.Clear().Val_int(fld_rng_type, type).Exec_select__rls_manual();
//			try {
//				int total = rdr.Read_int(fld_rng_total);
//				int interval = rdr.Read_int(fld_rng_interval);
//				return new Rndm_core_row(type, total, interval);
//			}	finally {rdr.Rls();}
	}
	public void Rls() {
		stmt_select = Db_stmt_.Rls(stmt_select);
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public void Get_random(int uid) {
//			Rndm_core_row core_row = core_tbl.Get(uid);
//			int rnd = RandomAdp_.new_().Next(core_row.total);
//			int idx = rnd / core_row.interval;
//			int off = rnd % core_row.interval;
//			Rndm_rng_row rng_row = rng_tbl.Get(uid, idx);
//			Xowd_page page = page_tbl.Get_by_page_offset(rng_row.page_id_bgn, off);
	}
}
