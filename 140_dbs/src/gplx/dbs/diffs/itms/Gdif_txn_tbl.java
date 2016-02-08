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
package gplx.dbs.diffs.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
public class Gdif_txn_tbl implements Rls_able {
	private String tbl_name = "gdif_txn";
	private String fld_job_id, fld_txn_id, fld_cmd_id, fld_owner_txn;
	private final Dbmeta_fld_list flds = Dbmeta_fld_list.new_();
	private final Db_conn conn; private Db_stmt stmt_insert;
	public Gdif_txn_tbl(Db_conn conn) {
		this.conn = conn;
		fld_job_id = flds.Add_int("job_id"); fld_txn_id = flds.Add_int("txn_id"); fld_cmd_id = flds.Add_int("cmd_id"); fld_owner_txn = flds.Add_int("owner_txn");
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "main", fld_job_id, fld_txn_id)));}
	public Gdif_txn_itm Insert(int job_id, int txn_id, int cmd_id, int owner_txn) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
		.Val_int(fld_job_id		, job_id)
		.Val_int(fld_txn_id		, txn_id)
		.Val_int(fld_cmd_id		, cmd_id)
		.Val_int(fld_owner_txn	, owner_txn)
		.Exec_insert();
		return new Gdif_txn_itm(job_id, txn_id, cmd_id, owner_txn);
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
}
