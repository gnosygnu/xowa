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
import gplx.xowa.addons.bldrs.exports.splits.mgrs.*; import gplx.xowa.addons.bldrs.exports.merges.*;
public class Xobc_cmd__wiki_merge extends Xobc_cmd__base {
	private final    String wiki_domain;
	private final    Io_url src_dir;
	private final    Merge2_mgr merge_mgr;
	private final    Merge_prog_wkr prog_wkr;
	private final    int idx_cur;
	public Xobc_cmd__wiki_merge(Xobc_task_mgr task_mgr, int task_id, int step_id, int cmd_idx, Merge2_mgr merge_mgr, String wiki_domain, Io_url src_dir
		, long prog_size_end, int prog_count_end, int idx_cur) {super(task_mgr, task_id, step_id, cmd_idx);
		this.merge_mgr = merge_mgr;
		this.wiki_domain = wiki_domain;
		this.src_dir = src_dir;
		this.prog_wkr = new Merge_prog_wkr(this, src_dir.GenSubFil("merge.checkpoint"), prog_count_end, prog_size_end);
		merge_mgr.Prog_wkr_(prog_wkr);
		this.Prog_data_end_(prog_count_end);
		this.idx_cur = idx_cur;
	}
	@Override public String Cmd_type() {return CMD_TYPE;} public static final    String CMD_TYPE = "xowa.wiki.merge";
	@Override public String Cmd_name() {return "merge";}
	@Override public boolean Cmd_suspendable() {return true;}
	@Override protected void Cmd_exec_hook(Xobc_cmd_ctx ctx) {
		Xow_wiki wiki = ctx.App().Wiki_mgri().Make(Bry_.new_u8(wiki_domain), ctx.App().Fsys_mgr().Wiki_dir().GenSubDir(wiki_domain));
		Io_url[] fils = Io_mgr.Instance.QueryDir_fils(src_dir);
		for (Io_url fil : fils) {
			if (prog_wkr.Checkpoint__skip_fil(fil)) continue;
			switch (Split_file_tid_.To_tid(fil)) {
				case Split_file_tid_.Tid__core: merge_mgr.ctx.Idx_cur_add_(); merge_mgr.Merge_core(wiki, fil); break;
				case Split_file_tid_.Tid__data: merge_mgr.ctx.Idx_cur_add_(); merge_mgr.Merge_data(wiki, fil, idx_cur); break;
			}
		}
		prog_wkr.Checkpoint__delete();
	}
	@Override public void Cmd_cleanup() {
		Io_mgr.Instance.DeleteDirDeep(src_dir.OwnerDir());	// src_dir is "unzip" dir; owner dir is actual archive dir
	}
	@Override protected long Load_checkpoint_hook() {return prog_wkr.Checkpoint__load();}
}	
