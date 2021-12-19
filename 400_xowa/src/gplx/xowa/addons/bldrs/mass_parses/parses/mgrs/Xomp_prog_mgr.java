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
package gplx.xowa.addons.bldrs.mass_parses.parses.mgrs;
import gplx.core.envs.SystemUtl;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.libs.files.Io_mgr;
import gplx.libs.ios.IoConsts;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.commons.GfoDateNow;
import gplx.libs.files.Io_url;
import gplx.types.commons.GfoTimeSpanUtl;
import gplx.xowa.wikis.caches.*;
public class Xomp_prog_mgr {
	private final Object thread_lock = new Object();
	private final BryWtr tmp_bfr = BryWtr.New();
	private Xow_page_cache page_cache;
	private int prog_interval, perf_interval;
	private int pages_done, pages_total;
	private long prog_bgn, prog_prv, prog_done, perf_prv;
	private Io_url perf_url;
	public void Init(Xow_page_cache page_cache, int pages_total, int prog_interval, int perf_interval, Io_url perf_url) {
		this.page_cache = page_cache;
		this.pages_total = pages_total;
		this.prog_interval = prog_interval;
		this.perf_interval = perf_interval;
		this.perf_url = perf_url;
		this.prog_bgn = this.prog_prv = this.perf_prv = SystemUtl.Ticks();
		Io_mgr.Instance.DeleteFil(perf_url);
	}
	public void Mark_done(int id) {
		synchronized (thread_lock) {
			pages_done += 1;
			if (pages_done % prog_interval == 0) {
				long prog_cur = SystemUtl.Ticks();
				int pages_left = pages_total - pages_done;
				prog_done += (prog_cur - prog_prv);
				double rate_cur = pages_done / (prog_done / GfoTimeSpanUtl.RatioFracsToSecs);
				String time_past = gplx.xowa.addons.bldrs.centrals.utils.Time_dhms_.To_str(tmp_bfr, (int)((prog_cur - prog_bgn) / 1000), true, 0);
				String time_left = gplx.xowa.addons.bldrs.centrals.utils.Time_dhms_.To_str(tmp_bfr, (int)(pages_left / rate_cur), true, 0);
				Gfo_usr_dlg_.Instance.Prog_many("", "", "done=~{1} left=~{2} rate=~{3} time_past=~{4} time_left=~{5} cache_stats=~{6}", id, pages_done, pages_left, (int)rate_cur, time_past, time_left, page_cache.To_str());
				prog_prv = prog_cur;
			}
			if (pages_done % perf_interval == 0) {
				// get vars
				long perf_cur = SystemUtl.Ticks();
				long perf_diff = (perf_cur - perf_prv);
				this.perf_prv = perf_cur;
				int memory_used = (int)(gplx.core.envs.Runtime_.Memory_used() / IoConsts.LenMB);

				// save to file
				tmp_bfr.AddIntFixed(pages_done, 12).AddBytePipe();
				tmp_bfr.AddIntFixed(memory_used, 6).AddBytePipe();
				tmp_bfr.AddLongVariable(perf_diff).AddBytePipe();
				tmp_bfr.AddDate(GfoDateNow.Get()).AddByteNl();
				Io_mgr.Instance.AppendFilBfr(perf_url, tmp_bfr);
			}
		}
	}
}
