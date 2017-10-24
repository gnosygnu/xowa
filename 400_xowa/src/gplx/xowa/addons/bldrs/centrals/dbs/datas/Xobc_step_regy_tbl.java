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
package gplx.xowa.addons.bldrs.centrals.dbs.datas; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*; import gplx.xowa.addons.bldrs.centrals.dbs.*;
import gplx.dbs.*;
import gplx.xowa.addons.bldrs.centrals.cmds.*; import gplx.xowa.addons.bldrs.centrals.tasks.*;
public class Xobc_step_regy_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_step_id, fld_step_type;
	private final    Db_conn conn; private Db_stmt insert_stmt;
	public Xobc_step_regy_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "step_regy";
		this.fld_step_id			= flds.Add_int_pkey("step_id");
		this.fld_step_type			= flds.Add_int("step_type");
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public int Select_type(int step_id) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_step_id).Crt_int(fld_step_id, step_id).Exec_select__rls_auto();
		try {
			if (rdr.Move_next())
				return rdr.Read_int(fld_step_type);
			else
				throw Err_.new_("", "bldr.central:could not find step_type", "step_id", step_id);
		}
		finally {rdr.Rls();}
	}
	public void Insert(int step_id, int step_type) {
		if (insert_stmt == null) insert_stmt = conn.Stmt_insert(tbl_name, flds);
		insert_stmt.Clear().Val_int(fld_step_id, step_id).Val_int(fld_step_type, step_type)
			.Exec_insert();
	}
	public void Delete(int step_id) {
		conn.Stmt_delete(tbl_name, fld_step_id).Crt_int(fld_step_id, step_id).Exec_delete();
	}
	public void Rls() {
		insert_stmt = Db_stmt_.Rls(insert_stmt);
	}
}
