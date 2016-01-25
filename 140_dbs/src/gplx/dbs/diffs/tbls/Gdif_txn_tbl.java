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
package gplx.dbs.diffs.tbls; import gplx.*; import gplx.dbs.*; import gplx.dbs.diffs.*;
import gplx.dbs.*;
/*
reg:
id,name,made_by,made_on,meta
0,'Diffs for Simple Wikipedia betwee 2015-11-16 and 2015-12-23',gnosygnu,2015-12-23'dif;simplewiki;20151223;20151116'
cmd:
owner,id,tid,meta
0,0,data_comp_tid,page
0,1,data_comp_tid,cat_core
0,2,data_comp_tid,cat_link
txn:
reg_id,txn_id,cmd_id,owner_txn
0,0,0,-1,page
0,1,-1,-1,cat
0,2,-1,1,mid
0,3,1,2,cat_core
0,4,2,2,cat_link
*/
class Gdif_txn_tbl implements Rls_able {
	private String tbl_name = "gdif_txn";
	private String fld_owner, fld_id;
	private final Dbmeta_fld_list flds = Dbmeta_fld_list.new_();
	private final Db_conn conn; private Db_stmt stmt_insert;
	public Gdif_txn_tbl(Db_conn conn) {
		this.conn = conn;
		fld_owner = flds.Add_int("txn_owner"); fld_id = flds.Add_int("txn_id");
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Dbmeta_tbl_itm.New(tbl_name, flds.To_fld_ary(), Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "main", fld_owner, fld_id)));}
	public void Insert(int txn_owner, int txn_id) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
		.Val_int(fld_owner	, txn_owner)
		.Val_int(fld_id		, txn_id)
		.Exec_insert();
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
}
