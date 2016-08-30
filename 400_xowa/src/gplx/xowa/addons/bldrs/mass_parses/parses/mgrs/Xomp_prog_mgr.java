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
	private int progress_interval;
	private int pages_done, pages_total;
	private long time_bgn, time_prv, time_done;		
	private final    Bry_bfr prog_bfr = Bry_bfr_.New();
	public void Init(int pages_total, int progress_interval) {
		this.progress_interval = progress_interval;
		this.time_bgn = this.time_prv = gplx.core.envs.System_.Ticks();
		this.pages_total = pages_total;
	}
	public void Mark_done(int id) {
		synchronized (thread_lock) {
			pages_done += 1;
			if (pages_done % progress_interval == 0) {
				long time_cur = gplx.core.envs.System_.Ticks();
				int pages_left = pages_total - pages_done;
				time_done += (time_cur - time_prv);
				double rate_cur = pages_done / (time_done / Time_span_.Ratio_f_to_s);
				String time_past = gplx.xowa.addons.bldrs.centrals.utils.Time_dhms_.To_str(prog_bfr, (int)((time_cur - time_bgn) / 1000), true, 0);
				String time_left = gplx.xowa.addons.bldrs.centrals.utils.Time_dhms_.To_str(prog_bfr, (int)(pages_left / rate_cur), true, 0);
				Gfo_usr_dlg_.Instance.Prog_many("", "", "done=~{1} left=~{2} rate=~{3} time_past=~{4} time_left=~{5}", id, pages_done, pages_left, (int)rate_cur, time_past, time_left);
				time_prv = time_cur;
			}
		}
	}
}
