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
package gplx.xowa.addons.bldrs.hdumps.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.hdumps.*;
import gplx.dbs.*;
class Dumpdiff_log_tbl implements Db_tbl {		
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__page_id, fld__cur_snip, fld__prv_snip;
	private Db_stmt stmt__insert;
	public Dumpdiff_log_tbl(Db_conn conn) {
		this.conn = conn;
		flds.Add_int_pkey("uid");
		this.fld__page_id = flds.Add_int("page_id");
		this.fld__cur_snip = flds.Add_str("cur_snip", 1024);
		this.fld__prv_snip = flds.Add_str("prv_snip", 1024);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name = "diff_log";
	public Db_conn Conn() {return conn;} private final    Db_conn conn;
	public void Create_tbl() {
		conn.Meta_tbl_remake(Dbmeta_tbl_itm.New(tbl_name, flds, Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, fld__page_id, fld__page_id)));
	}
	public void Insert_bgn() {
		stmt__insert = conn.Stmt_insert(tbl_name, fld__page_id, fld__cur_snip, fld__prv_snip);
		conn.Txn_bgn("diff_log");
	}
	public void Insert_by_batch(int page_id, byte[] prv_snip, byte[] cur_snip) {
		stmt__insert.Clear().Val_int(fld__page_id, page_id).Val_bry_as_str(fld__cur_snip, cur_snip).Val_bry_as_str(fld__prv_snip, prv_snip).Exec_insert();
	}
	public void Insert_end() {
		conn.Txn_end();
		stmt__insert.Rls();
	}
	public void Rls() {}

	public static Dumpdiff_log_tbl New(Xowe_wiki wiki) {
		Db_conn conn = Db_conn_bldr.Instance.Get_or_autocreate(true, wiki.Fsys_mgr().Root_dir().GenSubFil("xowa.diff.sqlite3"));
		Dumpdiff_log_tbl rv = new Dumpdiff_log_tbl(conn);
		conn.Meta_tbl_remake(rv);
		return rv;
	}
}
