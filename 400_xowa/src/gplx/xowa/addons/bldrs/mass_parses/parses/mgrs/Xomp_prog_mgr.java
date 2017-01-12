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
package gplx.xowa.addons.bldrs.mass_parses.parses.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*;
public class Xomp_prog_mgr {
	private final    Object thread_lock = new Object();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private int prog_interval, perf_interval;
	private int pages_done, pages_total;
	private long prog_bgn, prog_prv, prog_done, perf_prv;
	private Io_url perf_url;
	public void Init(int pages_total, int prog_interval, int perf_interval, Io_url perf_url) {
		this.pages_total = pages_total;
		this.prog_interval = prog_interval;
		this.perf_interval = perf_interval;
		this.perf_url = perf_url;
		this.prog_bgn = this.prog_prv = this.perf_prv = gplx.core.envs.System_.Ticks();
	}
	public void Mark_done(int id) {
		synchronized (thread_lock) {
			pages_done += 1;
			if (pages_done % prog_interval == 0) {
				long prog_cur = gplx.core.envs.System_.Ticks();
				int pages_left = pages_total - pages_done;
				prog_done += (prog_cur - prog_prv);
				double rate_cur = pages_done / (prog_done / Time_span_.Ratio_f_to_s);
				String time_past = gplx.xowa.addons.bldrs.centrals.utils.Time_dhms_.To_str(tmp_bfr, (int)((prog_cur - prog_bgn) / 1000), true, 0);
				String time_left = gplx.xowa.addons.bldrs.centrals.utils.Time_dhms_.To_str(tmp_bfr, (int)(pages_left / rate_cur), true, 0);
				Gfo_usr_dlg_.Instance.Prog_many("", "", "done=~{1} left=~{2} rate=~{3} time_past=~{4} time_left=~{5}", id, pages_done, pages_left, (int)rate_cur, time_past, time_left);
				prog_prv = prog_cur;
			}
			if (pages_done % perf_interval == 0) {
				// get vars
				long perf_cur = gplx.core.envs.System_.Ticks();
				long perf_diff = (perf_cur - perf_prv);
				this.perf_prv = perf_cur;
				int memory_used = (int)(gplx.core.envs.Runtime_.Memory_used() / Io_mgr.Len_mb);

				// save to file
				tmp_bfr.Add_int_fixed(pages_done, 12).Add_byte_pipe();
				tmp_bfr.Add_int_fixed(memory_used, 6).Add_byte_pipe();
				tmp_bfr.Add_long_variable(perf_diff).Add_byte_nl();
				Io_mgr.Instance.AppendFilBfr(perf_url, tmp_bfr);
			}
		}
	}
}
