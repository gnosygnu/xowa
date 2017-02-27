/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
		this.fld__wbp_pid			= flds.Add_str_pkey("wbp_pid", 16);			// EX: "p1"; NOTE: String, not int to conform to wbase_pid
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
		// read data
		String pid = String_.Upper(rdr.Read_str(fld__wbp_pid));	// convert "p123" to "P123"; note (a) Scrib.v2 calls as "P123"; (b) db stores as "p123"; (c) XO loads as "P123"; DATE:2016-12-03
		byte datatype_id = (byte)rdr.Read_int(fld__wbp_datatype);

		// convert id to key
		Wbase_claim_type datatype_itm = (Wbase_claim_type)Wbase_claim_type_.Reg.Get_itm_or(datatype_id, null);
		if (datatype_itm == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "wbase:invalid prop datatype_id; pid=~{0} datatype=~{1}", pid, datatype_id);
			datatype_itm = (Wbase_claim_type)Wbase_claim_type_.Itm__string;
		}
		hash.Add(pid, datatype_itm.Key_for_scrib()); // NOTE: must use Key_for_scrib, else multiple invalid-data-type errors in fr.w; DATE:2017-02-26
	}
	public void Rls() {}
}
