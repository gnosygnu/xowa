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
class Rl_dump_tbl {
	public static final String Tbl_name = "rl_dump"; private static final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	public static final String
	  Fld_src_page_id		= flds.Add_int("src_page_id")
	, Fld_src_html_uid		= flds.Add_int("src_html_uid")
	, Fld_trg_page_id		= flds.Add_int_dflt("trg_page_id", -1)
	, Fld_trg_ns			= flds.Add_int("trg_ns")
	, Fld_trg_ttl			= flds.Add_str("trg_ttl", 255)
	;
	private Db_conn conn; private Db_stmt stmt_insert;
	public Db_conn Conn() {return conn;}
	public void Conn_(Db_conn new_conn, boolean created) {
		this.conn = new_conn;
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(Tbl_name, flds);
			conn.Exec_create_tbl_and_idx(meta);
		}
		stmt_insert = null;
	}
	public void Insert(int src_page_id, int src_html_uid, int trg_ns, byte[] trg_ttl) {
		if (stmt_insert == null) stmt_insert = conn.Rls_reg(conn.Stmt_insert(Tbl_name, flds));
		stmt_insert.Clear().Val_int(Fld_src_page_id, src_page_id)
			.Val_int(Fld_src_html_uid, src_html_uid).Val_int(Fld_trg_page_id, -1).Val_int(Fld_trg_ns, trg_ns).Val_bry_as_str(Fld_trg_ttl, trg_ttl)
			.Exec_insert();
	}
	public Db_rdr Select_missing() {
		return conn.Stmt_select_order(Tbl_name, flds, String_.Ary(Fld_trg_page_id), Fld_src_page_id, Fld_src_html_uid)
			.Crt_int(Fld_trg_page_id, -1).Exec_select_as_rdr();
	}
	public static Rl_dump_tbl Get_or_new(Xowe_wiki wiki) {
		Db_conn_bldr_data conn_data = Db_conn_bldr.I.Get_or_new("", wiki.Fsys_mgr().Root_dir().GenSubFil("xowa.link_dump.sqlite3"));
		Rl_dump_tbl rv = new Rl_dump_tbl();
		rv.Conn_(conn_data.Conn(), conn_data.Created());
		return rv;
	}
}
