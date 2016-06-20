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
