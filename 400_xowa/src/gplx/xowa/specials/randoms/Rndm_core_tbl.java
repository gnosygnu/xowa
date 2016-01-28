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
public class Rndm_core_tbl implements Rls_able {
	private final String tbl_name = "rndm_core"; private final Dbmeta_fld_list flds = Dbmeta_fld_list.new_();
	private final String fld_rndm_uid, fld_rndm_total, fld_rndm_interval;
	private final Db_conn conn; private Db_stmt stmt_select, stmt_insert;		
	public Rndm_core_tbl(Db_conn conn) {
		this.conn = conn;
		fld_rndm_uid		= flds.Add_int("rndm_uid");
		fld_rndm_total		= flds.Add_int("rndm_total");
		fld_rndm_interval 	= flds.Add_int("rndm_interval");
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_unique_by_name(tbl_name, "core", fld_rndm_uid)));}
	public void Insert(int uid, int total, int interval) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear().Val_int(fld_rndm_uid, uid).Val_int(fld_rndm_total, total).Val_int(interval).Exec_insert();
	}
	public Rndm_core_row Select(int uid) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_rndm_uid);
		Db_rdr rdr = stmt_select.Clear().Val_int(fld_rndm_uid, uid).Exec_select__rls_manual();
		try {
			int total = rdr.Read_int(fld_rndm_total);
			int interval = rdr.Read_int(fld_rndm_interval);
			return new Rndm_core_row(uid, total, interval);
		}	finally {rdr.Rls();}
	}
	public void Rls() {
		stmt_select = Db_stmt_.Rls(stmt_select);
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
}
