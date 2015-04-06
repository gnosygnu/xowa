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
package gplx.xowa.html.hdumps.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*;
import gplx.dbs.*;
class Xob_link_dump_tbl implements RlsAble {
	public static final String Tbl_name = "link_dump"; private static final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	public static final String
	  Fld_src_page_id		= flds.Add_int("src_page_id")
	, Fld_src_html_uid		= flds.Add_int("src_html_uid")
	, Fld_trg_page_id		= flds.Add_int_dflt("trg_page_id", -1)
	, Fld_trg_ns			= flds.Add_int("trg_ns")
	, Fld_trg_ttl			= flds.Add_str("trg_ttl", 255)
	;		
	private Db_stmt stmt_insert;
	public Xob_link_dump_tbl(Db_conn conn) {
		this.conn = conn;
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final Db_conn conn;
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(Tbl_name, flds));}
	public void Create_idx_1() {
		conn.Ddl_create_idx
		( Db_meta_idx.new_normal_by_tbl(Tbl_name, "src", Fld_src_page_id, Fld_src_html_uid)
		, Db_meta_idx.new_normal_by_tbl(Tbl_name, "trg_temp", Fld_trg_ns, Fld_trg_ttl)
		);
	}
	public void Create_idx_2() {
		conn.Ddl_create_idx
		( Db_meta_idx.new_normal_by_tbl(Tbl_name, "trg", Fld_trg_page_id, Fld_src_page_id, Fld_src_html_uid)
		);			
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public void Insert_bgn() {conn.Txn_bgn(); stmt_insert = conn.Stmt_insert(Tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(int src_page_id, int src_html_uid, int trg_ns, byte[] trg_ttl) {
		stmt_insert.Clear().Val_int(Fld_src_page_id, src_page_id)
			.Val_int(Fld_src_html_uid, src_html_uid).Val_int(Fld_trg_page_id, -1).Val_int(Fld_trg_ns, trg_ns).Val_bry_as_str(Fld_trg_ttl, trg_ttl)
			.Exec_insert();
	}
	public Db_rdr Select_missing() {
		return conn.Stmt_select_order(Tbl_name, flds, String_.Ary(Fld_trg_page_id), Fld_src_page_id, Fld_src_html_uid)
			.Crt_int(Fld_trg_page_id, -1).Exec_select__rls_auto();
	}
	public static Xob_link_dump_tbl Get_or_new(Xow_wiki wiki) {
		Db_conn_bldr_data conn_data = Db_conn_bldr.I.Get_or_new(wiki.Fsys_mgr().Root_dir().GenSubFil("xowa.temp.redlink.sqlite3"));
		Xob_link_dump_tbl rv = new Xob_link_dump_tbl(conn_data.Conn());
		if (conn_data.Created()) rv.Create_tbl();
		return rv;
	}
}
