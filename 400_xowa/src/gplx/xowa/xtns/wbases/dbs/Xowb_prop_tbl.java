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
package gplx.xowa.xtns.wbases.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
import gplx.xowa.xtns.wbases.claims.enums.*;
public class Xowb_prop_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__wbp_pid, fld__wbp_datatype;
	private final    Db_conn conn;
	private Db_stmt stmt_insert;
	public Xowb_prop_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "wbase_prop";
		this.fld__wbp_pid			= flds.Add_str("wbp_pid", 16);				// EX: "p1"; NOTE: String, not int to conform to wbase_pid
		this.fld__wbp_datatype		= flds.Add_int("wbp_datatype");				// EX: 12=commonsMedia; SEE:Wbase_claim_type_
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Insert_bgn() {conn.Txn_bgn("wbase_prop__insert"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(byte[] pid, int datatype) {
		stmt_insert.Clear().Val_bry_as_str(fld__wbp_pid, pid).Val_int(fld__wbp_datatype, datatype).Exec_insert();
	}
	public Ordered_hash Select_all() {
		Ordered_hash rv = Ordered_hash_.New();
		Db_rdr rdr = conn.Stmt_select_all(tbl_name, flds).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				Select_all__add(rv, rdr);
			}
		}
		finally {rdr.Rls();}
		return rv;
	}
	private void Select_all__add(Ordered_hash hash, Db_rdr rdr) {
		byte[] pid = rdr.Read_bry_by_str(fld__wbp_pid);
		byte datatype_id = (byte)rdr.Read_int(fld__wbp_datatype);
		Wbase_enum_itm datatype_itm = Wbase_claim_type_.Reg.Get_itm_or((byte)datatype_id, null);
		if (datatype_itm == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "wbase:invalid prop datatype_id; pid=~{0} datatype=~{1}", pid, datatype_id);
			datatype_itm = Wbase_claim_type_.Itm__string;
		}
		hash.Add(pid, datatype_itm);
	}
	public void Rls() {}
}
