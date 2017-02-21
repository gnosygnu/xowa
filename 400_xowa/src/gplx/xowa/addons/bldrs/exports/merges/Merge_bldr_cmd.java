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
package gplx.xowa.addons.bldrs.exports.merges; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.addons.bldrs.exports.splits.mgrs.*; import gplx.xowa.addons.bldrs.exports.splits.rslts.*;
public class Merge_bldr_cmd extends Xob_cmd__base {
	public Merge_bldr_cmd(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		Io_mgr.Instance.DeleteDirDeep(wiki.Fsys_mgr().Root_dir());
		wiki.Init_assert();
	
		Merge2_mgr merge_mgr = new Merge2_mgr();
		Io_url[] urls = Io_mgr.Instance.QueryDir_fils(Io_url_.new_fil_("C:\\xowa\\wiki\\simple.wikipedia.org\\tmp\\split\\"));
		int i = 0;
		for (Io_url url : urls) {
//				if (++i == 5) break;
			switch (Split_file_tid_.To_tid(url)) {
				case Split_file_tid_.Tid__core:		merge_mgr.Merge_core(wiki, url); break;
				case Split_file_tid_.Tid__data:	
					Db_conn conn = Db_conn_bldr.Instance.Get_or_noop(url);
					Wkr_stats_tbl stats_tbl = new Wkr_stats_tbl(conn);
					Wkr_stats_itm summary_itm = stats_tbl.Select_all__summary();
					Merge_prog_wkr prog_wkr = new Merge_prog_wkr(gplx.core.progs.Gfo_prog_ui_.Always, url.GenNewNameAndExt("merge.checkpoint"), summary_itm.Count, summary_itm.Size);
					merge_mgr.Prog_wkr_(prog_wkr);
					merge_mgr.Merge_data(wiki, url, i); break;
				default:							continue;
			}					
			++i;
		}
	}

	public static final String BLDR_CMD_KEY = "bldr.export.merge";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Merge_bldr_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Merge_bldr_cmd(bldr, wiki);}
}
