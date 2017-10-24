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
public class Xobc_work_task_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_task_id, fld_task_seqn, fld_step_id, fld_cmd_id;
	private final    Db_conn conn;
	public Xobc_work_task_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "xobc_work_task";
		this.fld_task_id			= flds.Add_int_pkey("task_id");
		this.fld_task_seqn			= flds.Add_int("task_seqn");
		this.fld_step_id			= flds.Add_int("step_id");
		this.fld_cmd_id				= flds.Add_int("cmd_id");
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Select_all(Xobc_task_mgr task_mgr, Xobc_task_regy__base todo_regy, Xobc_task_regy__base work_regy) {
		work_regy.Clear();
		Db_rdr rdr = conn.Stmt_select_all(tbl_name, flds).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				int task_id = rdr.Read_int(fld_task_id);
				int task_seqn = rdr.Read_int(fld_task_seqn);
				int step_id = rdr.Read_int(fld_step_id);
				int cmd_id = rdr.Read_int(fld_cmd_id);
				Xobc_task_itm task = (Xobc_task_itm)todo_regy.Get_by(task_id);
				if (task == null) {
					Gfo_log_.Instance.Warn("task exists in work, but not in todo", "task_id", task_id);
					continue;
				}
				work_regy.Add(task);
				todo_regy.Del_by(task_id);
				task.Task_seqn_(task_seqn);
				task.Task_status_(cmd_id == Xobc_cmd__base.Seqn__0 ? gplx.core.progs.Gfo_prog_ui_.Status__init : gplx.core.progs.Gfo_prog_ui_.Status__working);
				task_mgr.Step_mgr().Load(task, step_id, cmd_id);
			}
			work_regy.Sort();
		} finally {rdr.Rls();}
	}
	public void Insert(int task_id, int task_seqn, int step_id, int cmd_id) {
		conn.Stmt_insert(tbl_name, fld_task_id, fld_task_seqn, fld_step_id, fld_cmd_id)
			.Val_int(fld_task_id, task_id).Val_int(fld_task_seqn, task_seqn).Val_int(fld_step_id, step_id).Val_int(fld_cmd_id, cmd_id)
			.Exec_insert();
	}
	public void Update(int task_id, int task_seqn, int step_id, int cmd_id) {
		conn.Stmt_update_exclude(tbl_name, flds, fld_task_id)
			.Val_int(fld_task_seqn, task_seqn).Val_int(fld_step_id, step_id).Val_int(fld_cmd_id, cmd_id).Crt_int(fld_task_id, task_id)
			.Exec_update();
	}
	public void Delete(int task_id) {
		conn.Stmt_delete(tbl_name, fld_task_id).Val_int(fld_task_id, task_id).Exec_insert();
	}
	public void Rls() {}
}
