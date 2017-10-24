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
import gplx.core.ios.zips.*; import gplx.core.progs.*;
public class Xobc_cmd__unzip extends Xobc_cmd__base {
	private final    Io_url src_fil, trg_dir;
	private final    Io_zip_decompress_cmd wkr;
	private final    List_adp trg_fils = List_adp_.New();
	public Xobc_cmd__unzip(Xobc_task_mgr task_mgr, int task_id, int step_id, int cmd_id, Io_url src_fil, Io_url trg_dir, long prog_data_end) {super(task_mgr, task_id, step_id, cmd_id);
		this.src_fil = src_fil; this.trg_dir = trg_dir;
		this.Prog_data_end_(prog_data_end);
		this.wkr = Io_zip_decompress_cmd_.Proto.Make_new();
	}
	@Override public String		Cmd_type() {return CMD_TYPE;} public static final    String CMD_TYPE = "xowa.core.ios.zips.zip_unzip";
	@Override public String		Cmd_name() {return "unzip";}
	@Override public boolean		Cmd_suspendable() {return true;}
	@Override public String Cmd_fallback() {return Xobc_cmd__verify_fil.CMD_TYPE;}	// if unzip fails, backtrack to verify; if verify fails, it'll backtrack to download; DATE:2016-07-25

	@Override protected void Cmd_exec_hook(Xobc_cmd_ctx ctx) {
		if (wkr.Exec(this, src_fil, trg_dir, trg_fils) == Gfo_prog_ui_.Status__fail)
			this.Cmd_exec_err_(wkr.Fail_msg());
	}
	@Override public void Cmd_cleanup() {
		wkr.Exec_cleanup();
		Io_mgr.Instance.DeleteFil(src_fil);
		Io_mgr.Instance.DeleteDirDeep(trg_dir);
	}
	@Override protected long Load_checkpoint_hook() {
		return wkr.Checkpoint__load_by_src_fil(src_fil);
	}
}
