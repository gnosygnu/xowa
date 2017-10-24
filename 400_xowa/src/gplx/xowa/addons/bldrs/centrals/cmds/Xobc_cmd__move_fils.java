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
public class Xobc_cmd__move_fils extends Xobc_cmd__base {
	private final    Io_url src_dir, trg_dir;
	private final    List_adp trg_fils = List_adp_.New();
	public Xobc_cmd__move_fils(Xobc_task_mgr task_mgr, int task_id, int step_id, int cmd_idx, Io_url src_dir, Io_url trg_dir) {super(task_mgr, task_id, step_id, cmd_idx);
		this.src_dir = src_dir; this.trg_dir = trg_dir;
	}
	@Override public String Cmd_type() {return CMD_TYPE;} public static final    String CMD_TYPE = "xowa.core.move_fil";
	@Override public String Cmd_name() {return "move";}
	@Override protected void Cmd_exec_hook(Xobc_cmd_ctx ctx) {
		Io_url[] src_fils = Io_mgr.Instance.QueryDir_fils(src_dir);
		int len = src_fils.length;
		for (int i = 0; i < len; ++i) {
			Io_url src_fil = src_fils[i];
			if (String_.Eq(src_fil.Ext(), ".md5")) continue;
			Io_url trg_fil = trg_dir.GenSubFil(src_fil.NameAndExt());
			Io_mgr.Instance.MoveFil_args(src_fil, trg_fil, true).Exec();
			trg_fils.Add(trg_fil);
		}
	}
	@Override public void Cmd_cleanup() {
		Io_mgr.Instance.Delete_dir_empty(trg_dir.GenSubDir("tmp"));	// deletes dir only if empty
	}
}
