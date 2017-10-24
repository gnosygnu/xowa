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
package gplx.xowa.addons.bldrs.centrals.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import gplx.dbs.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.bldrs.updates.files.*;
public class Xobc_cmd__fsdb_delete extends Xobc_cmd__base {
	private final    Io_url deletion_db_url;
	public Xobc_cmd__fsdb_delete(Xobc_task_mgr task_mgr, int task_id, int step_id, int cmd_idx, Io_url deletion_db_url) {super(task_mgr, task_id, step_id, cmd_idx);
		this.deletion_db_url = deletion_db_url;
	}
	@Override public String Cmd_type() {return CMD_TYPE;} public static final    String CMD_TYPE = "xowa.fsdb.delete";
	@Override public String Cmd_name() {return "deleting old files";}
	@Override public boolean Cmd_suspendable() {return true;}
	@Override protected void Cmd_exec_hook(Xobc_cmd_ctx ctx) {
		if (!Io_mgr.Instance.ExistsFil(deletion_db_url)) throw Err_.New("deletion db does not exist; file={0}", deletion_db_url.Raw());
		boolean pass = false;
		try {
			new Xodel_exec_mgr().Exec_delete(this, ctx.App().Bldr(), deletion_db_url);
			pass = true;
		}
		catch (Exception e) {
			this.Cmd_exec_err_(Err_.Message_gplx_log(e));
		}
		Gfo_log_.Instance.Info("xobc_cmd task delete", "task_id", this.Task_id(), "step_id", this.Step_id(), "delete_url", deletion_db_url.Raw(), "pass", pass);
	}
	@Override public void Cmd_cleanup() {
		if (Io_mgr.Instance.ExistsFil(deletion_db_url)) 
			new Xodel_exec_mgr().Exec_cleanup(deletion_db_url);
	}
}	
