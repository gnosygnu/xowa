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
