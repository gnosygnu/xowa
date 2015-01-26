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
package gplx.xowa.dbs.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
import gplx.dbs.*;
public class Xodb_wdata_qids_tbl {
	public void Purge(Db_conn p) {p.Exec_qry(Db_qry_.delete_tbl_(Tbl_name));}
	public Db_stmt Insert_stmt(Db_conn p) {return Db_stmt_.new_insert_(p, Tbl_name, Fld_wq_src_wiki, Fld_wq_src_ns, Fld_wq_src_ttl, Fld_wq_trg_ttl);}
	public void Insert(Db_stmt stmt, byte[] src_wiki, int src_ns, byte[] src_ttl, byte[] trg_ttl) {
		stmt.Clear().Val_bry_as_str(src_wiki).Val_int(src_ns).Val_bry_as_str(src_ttl).Val_bry_as_str(trg_ttl).Exec_insert();
	}
	public byte[] Select_qid(Db_conn p, byte[] src_wiki, byte[] src_ns, byte[] src_ttl) {
		Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = Db_stmt_.new_select_(p, Tbl_name, String_.Ary(Fld_wq_src_wiki, Fld_wq_src_ns, Fld_wq_src_ttl), Fld_wq_trg_ttl);
			String rv = (String)stmt.Val_bry_as_str(src_wiki).Val_int(Bry_.Xto_int(src_ns)).Val_bry_as_str(src_ttl).Exec_select_val();
			return rv == null ? null : Bry_.new_utf8_(rv);
		} finally {stmt.Rls();}
	}
	public static final String Tbl_name = "wdata_qids", Fld_wq_src_wiki = "wq_src_wiki", Fld_wq_src_ns = "wq_src_ns", Fld_wq_src_ttl = "wq_src_ttl", Fld_wq_trg_ttl = "wq_trg_ttl";
}
