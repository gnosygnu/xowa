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
package gplx.xowa.addons.bldrs.mass_parses.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*;
class Xomp_page_pool {
	private final    Object thread_lock = new Object();
	private Xomp_page_pool_loader loader;
	private List_adp pool = List_adp_.New(); private int pool_idx = 0, pool_len = 0;
	private Db_conn make_conn;
	private final    Bry_bfr prog_bfr = Bry_bfr_.New();
	private int pages_done, pages_total;
	private long time_bgn, time_prv, time_done;
	public void Init(Xow_wiki wiki, int num_pages_per_load) {
		this.make_conn = gplx.xowa.bldrs.Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		this.loader = new Xomp_page_pool_loader(wiki, make_conn, num_pages_per_load);
		this.pages_done = 0;
		this.time_bgn = this.time_prv = gplx.core.envs.Env_.TickCount();
		this.pages_total = loader.Get_pending_count();
	}
	public boolean Empty() {return empty;} private boolean empty = false;
	public void Get_next(List_adp wkr_list, int num_pages_per_wkr) {
		synchronized (thread_lock) {
			// pool already marked exhausted by another wkr; return;
			if (empty) return;
			int wkr_end = pool_idx + num_pages_per_wkr;

			// need pages to fulfill request
			if (wkr_end > pool_len) {
				this.pool = loader.Load(pool, pool_idx, pool_len);
				this.pool_idx = 0;
				this.pool_len = pool.Len();
				if (pool_len == 0) {	// no more pages; return;
					empty = true;
					return;
				}
				wkr_end = num_pages_per_wkr;	// recalc wkr_end
			}

			// reset wkr_end; needed for very last set
			if (wkr_end >= pool_len)
				wkr_end = pool_len;

			// add pages to wkr_list
			for (int i = pool_idx; i < wkr_end; ++i) {
				Xomp_page_itm page = (Xomp_page_itm)pool.Get_at(i);
				wkr_list.Add(page);
			}
			pool_idx = wkr_end;
		}
	}
	public void Mark_done(int id) {
		synchronized (thread_lock) {
			pages_done += 1;
			if (pages_done % 1000 == 0) {
				long time_cur = gplx.core.envs.Env_.TickCount();
				int pages_left = pages_total - pages_done;
				time_done += (time_cur - time_prv);
				double rate_cur = pages_done / (time_done / Time_span_.Ratio_f_to_s);
				String time_past = gplx.xowa.addons.bldrs.centrals.utils.Time_dhms_.To_str(prog_bfr, (int)((time_cur - time_bgn) / 1000), true, 0);
				String time_left = gplx.xowa.addons.bldrs.centrals.utils.Time_dhms_.To_str(prog_bfr, (int)(pages_left / rate_cur), true, 0);
				Gfo_usr_dlg_.Instance.Prog_many("", "", "done=~{0} left=~{1} rate=~{2} time_past=~{3} time_left=~{4}", pages_done, pages_left, (int)rate_cur, time_past, time_left);
				time_prv = time_cur;
			}
		}
	}
	public void Rls() {
		make_conn.Rls_conn();
	}
}
