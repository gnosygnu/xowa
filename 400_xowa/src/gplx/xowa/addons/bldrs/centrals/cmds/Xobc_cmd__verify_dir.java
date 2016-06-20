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
import gplx.core.progs.*;
import gplx.core.security.*; import gplx.core.security.files.*;
public class Xobc_cmd__verify_dir extends Xobc_cmd__base {
	private final    Io_url delete_fil, checksum_fil;
	public Xobc_cmd__verify_dir(Xobc_task_mgr task_mgr, int task_id, int step_id, int cmd_idx, Io_url dir_url, String checksum_fil_name, Io_url delete_fil) {super(task_mgr, task_id, step_id, cmd_idx);
		this.delete_fil = delete_fil;
		this.checksum_fil = dir_url.GenSubFil(checksum_fil_name);
	}
	@Override public String Cmd_type() {return CMD_TYPE;} public static final    String CMD_TYPE = "xowa.core.hash_dir";
	@Override public String Cmd_name() {return "verify";}
	@Override public String Cmd_fallback() {return Xobc_cmd__unzip.CMD_TYPE;}

	@Override protected void Cmd_exec_hook(Xobc_cmd_ctx ctx) {
		// parse file
		Cksum_list list = Cksum_list.Parse_by_fil(checksum_fil);
		this.Prog_data_end_(list.Itms_size);

		// verify itms
		Hash_algo algo = Hash_algo_.New_by_tid(list.Type);
		Cksum_itm[] itms = list.Itms;
		int len = itms.length;
		long prog_data_cur = 0;
		for (int i = 0; i < len; ++i) {
			Cksum_itm itm = itms[i];
			gplx.core.ios.streams.IoStream stream = Io_mgr.Instance.OpenStreamRead(itm.File_url);
			byte[] actl_hash = Bry_.Empty;
			this.Prog_data_cur_(prog_data_cur);
			try {actl_hash = algo.Hash_stream_as_bry(this, stream);}
			finally {stream.Rls();}
			prog_data_cur += itm.File_size;
			if (this.Prog_notify_and_chk_if_suspended(prog_data_cur, Prog_data_end())) return;
			if (this.Prog_status() != Gfo_prog_ui_.Status__suspended && !Bry_.Eq(itm.Hash, actl_hash)) {
				this.Cmd_exec_err_(String_.Format("bad hash; file={0} bad={1} good={2}", itm.File_url.Raw(), actl_hash, itm.Hash));
				return;
			}
		}
	}
	@Override public void Cmd_cleanup() {
		Io_mgr.Instance.DeleteFil(checksum_fil);
		Io_mgr.Instance.DeleteFil(delete_fil);
	}
}
