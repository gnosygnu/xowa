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
package gplx.xowa.addons.bldrs.centrals.dbs.users; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*; import gplx.xowa.addons.bldrs.centrals.dbs.*;
import gplx.dbs.*;
import gplx.xowa.addons.bldrs.centrals.cmds.*; import gplx.xowa.addons.bldrs.centrals.tasks.*;
public class Xobc_done_step_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_task_id, fld_step_id;
	private final    Db_conn conn;
	public Xobc_done_step_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "xobc_done_step";
		this.fld_task_id			= flds.Add_int("task_id");
		this.fld_step_id			= flds.Add_int("step_id");
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));
		conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "main", fld_task_id, fld_step_id));
	}
	public void Insert(int task_id, int step_id) {
		conn.Stmt_insert(tbl_name, fld_task_id, fld_step_id)
			.Val_int(fld_task_id, task_id).Val_int(fld_step_id, step_id)
			.Exec_insert();
	}
	public Hash_adp Select_all(int task_id) {
		Hash_adp rv = null;
		Db_rdr rdr = conn.Stmt_select(tbl_name, String_.Ary(fld_step_id), fld_task_id).Crt_int(fld_task_id, task_id).Exec_select__rls_auto();
		try {
			if (rdr.Move_next()) {
				if (rv == null) rv = Hash_adp_.New();
				rv.Add_as_key_and_val(rdr.Read_int("step_id"));
			}
		}
		finally {rdr.Rls();}
		return null;
	}
	public void Rls() {}
}
