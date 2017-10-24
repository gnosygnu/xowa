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
import gplx.core.progs.*; import gplx.core.net.downloads.*;
public class Xobc_cmd__download extends Xobc_cmd__base {
	private final    Xobc_task_mgr task_mgr;
	private final    String src_url; private final    Io_url trg_url;
	private final    long expd_size;
	private final    Http_download_wkr wkr;
	public Xobc_cmd__download(Xobc_task_mgr task_mgr, int task_id, int step_id, int cmd_id, String src_url, Io_url trg_url, long file_size_zip) {super(task_mgr, task_id, step_id, cmd_id);
		this.task_mgr = task_mgr;
		this.src_url = src_url; this.trg_url = trg_url; this.expd_size = file_size_zip;
		this.wkr = Http_download_wkr_.Proto.Make_new();
		this.Prog_data_end_(expd_size);
	}
	@Override public String		Cmd_type()	{return CMD_TYPE;}	public static final    String CMD_TYPE = "xowa.core.download";
	@Override public String		Cmd_name()	{return "download";}
	@Override public boolean		Cmd_suspendable() {return true;}

	@Override protected void Cmd_exec_hook(Xobc_cmd_ctx ctx) {
		int error_wait = 10000, error_tries_max = 6, error_tries_cur = 0;	// retry every 10 seconds for a total of 6 tries (1 min)

		// loop while "server ... " error
		while (true) {
			long trg_size_bgn = Tmp_url_size();
			byte status = wkr.Exec(this, src_url, trg_url, expd_size);
			if (status == Gfo_prog_ui_.Status__fail) {

				// check if server error; note: must not loop if bad size; DATE:2016-09-24
				String fail_msg = wkr.Fail_msg();
				if (String_.Has_at_bgn(fail_msg, Http_download_wkr__jre.Err__server_download_failed)) {

					// check if anything more downloaded; if so, then reset to 0; DATE:2016-09-03
					long trg_size_cur = Tmp_url_size();
					if (trg_size_cur > trg_size_bgn) {
						error_tries_cur = 0;
						trg_size_bgn = trg_size_cur;
					}

					// retry
					if (error_tries_cur++ < error_tries_max) {
						task_mgr.Work_mgr().On_stat(this.Task_id(), String_.Format("connection interrupted: retrying in {0} seconds; attempt {1} of {2}", error_wait / 1000, error_tries_cur, error_tries_max));
						Gfo_usr_dlg_.Instance.Log_many("", "", "xobc_cmd task download interrupted; ~{0} ~{1} ~{2} ~{3}", this.Task_id(), this.Step_id(), trg_url, Io_mgr.Instance.QueryFil(trg_url).Size());
						gplx.core.threads.Thread_adp_.Sleep(error_wait);
						continue;
					}
				}

				// otherewise exit loop
				this.Cmd_exec_err_(fail_msg);
				break;
			}
			else
				break;
		}
		Gfo_log_.Instance.Info("xobc_cmd task download", "task_id", this.Task_id(), "step_id", this.Step_id(), "trg_url", trg_url, "trg_len", Io_mgr.Instance.QueryFil(trg_url).Size());
	}
	private long Tmp_url_size() {
		return wkr.Tmp_url() == null ? 0 : Io_mgr.Instance.QueryFil(wkr.Tmp_url()).Size();	// NOTE: wkr.Tmp_url is null in some extreme exceptions; DATE:2016-09-24
	}
	@Override public void Cmd_cleanup() {
		wkr.Exec_cleanup();
	}
	@Override protected boolean Cmd_fail_resumes() {return true;}

	@Override protected long Load_checkpoint_hook() {
		return wkr.Checkpoint__load_by_trg_fil(trg_url);
	}
}
