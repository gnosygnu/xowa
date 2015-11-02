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
package gplx.xowa.htmls.core.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.dbs.*;
public class Xoh_redlink_tbl implements RlsAble {
	private final String tbl_name = "html_redlink"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private final String fld_page_id, fld_redlink_uids;
	private final Db_conn conn; private Db_stmt stmt_select, stmt_insert, stmt_delete, stmt_update;
	public Xoh_redlink_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_page_id			= flds.Add_int_pkey("page_id");
		this.fld_redlink_uids		= flds.Add_bry("redlink_uids");
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds));}
	public void Insert_bgn() {conn.Txn_bgn("html_redlink__insert"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert(int page_id, byte[] redlink_uids) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear().Val_int(fld_page_id, page_id).Val_bry(fld_redlink_uids, redlink_uids).Exec_insert();
	}
	public void Update(int page_id, byte[] redlink_uids) {
		if (stmt_update == null) stmt_update = conn.Stmt_update_exclude(tbl_name, flds, fld_page_id);
		stmt_update.Clear().Val_bry(fld_redlink_uids, redlink_uids).Crt_int(fld_page_id, page_id).Exec_update();
	}
	public void Delete(int page_id) {
		if (stmt_delete == null) stmt_delete = conn.Stmt_delete(tbl_name, fld_page_id);
		stmt_delete.Clear().Crt_int(fld_page_id, page_id).Exec_delete();
	}
	public byte[] Select_or_null(int page_id) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_page_id);
		Db_rdr rdr = stmt_select.Clear().Crt_int(fld_page_id, page_id).Exec_select__rls_manual();
		try {
			return rdr.Move_next() ? rdr.Read_bry(fld_redlink_uids) : null;
		}
		finally {rdr.Rls();}
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_delete = Db_stmt_.Rls(stmt_delete);
		stmt_select = Db_stmt_.Rls(stmt_select);
		stmt_update = Db_stmt_.Rls(stmt_update);
	}
}
