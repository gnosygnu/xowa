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
public class Xobc_done_task_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_task_id, fld_task_seqn;
	private final    Db_conn conn;
	public Xobc_done_task_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "xobc_done_task";
		this.fld_task_id			= flds.Add_int_pkey("task_id");
		this.fld_task_seqn			= flds.Add_int("task_seqn");
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Insert(int task_id, int task_seqn) {
		conn.Stmt_insert(tbl_name, fld_task_id, fld_task_seqn)
			.Val_int(fld_task_id, task_id).Val_int(fld_task_seqn, task_seqn)
			.Exec_insert();
	}
	public void Delete(int task_id) {
		conn.Stmt_delete(tbl_name, fld_task_id)
			.Crt_int(fld_task_id, task_id)
			.Exec_delete();
	}
	public void Select_all(Xobc_task_regy__base todo_regy, Xobc_task_regy__base done_regy) {
		done_regy.Clear();
		Db_rdr rdr = conn.Stmt_select_all(tbl_name, flds).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				int task_id = rdr.Read_int(fld_task_id);
				int task_seqn = rdr.Read_int(fld_task_seqn);
				if (task_seqn == gplx.xowa.addons.bldrs.centrals.dbs.datas.Xobc_task_regy_itm.Seqn__obsolete) continue;	// WORKAROUND: do not show old tasks; should add a status column, but don't want to change schema yet; DATE:2016-11-03
				Xobc_task_itm itm = (Xobc_task_itm)todo_regy.Get_by(task_id);
				if (itm == null) {
					Gfo_log_.Instance.Warn("task exists in done, but not in todo", "task_id", task_id);
					continue;
				}
				done_regy.Add(itm);
				todo_regy.Del_by(task_id);
				itm.Task_seqn_(task_seqn);
			}
		} finally {rdr.Rls();}
	}
	public void Rls() {}
}
