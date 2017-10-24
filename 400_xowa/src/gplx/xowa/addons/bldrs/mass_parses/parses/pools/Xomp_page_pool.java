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
package gplx.xowa.addons.bldrs.mass_parses.parses.pools; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*;
import gplx.dbs.*; import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
public class Xomp_page_pool {
	private final    Object thread_lock = new Object();
	private final    Xomp_page_pool_loader loader;
	private final    int num_pages_per_wkr;
	private List_adp pool = List_adp_.New(); private int pool_idx = 0, pool_len = 0;
	public Xomp_page_pool(Xomp_page_pool_loader loader, int num_pages_per_wkr) {
		this.loader = loader; this.num_pages_per_wkr = num_pages_per_wkr;
	}
	public boolean Empty() {synchronized (thread_lock) {return empty;}} private boolean empty = false;
	public void Get_next(Xomp_mgr_db mgr_db, String machine_name, List_adp wkr_list) {
		synchronized (thread_lock) {	// LOCK:shared by multiple wkrs
			// all pages read; "empty" marked done by another wkr; return;
			if (empty) return;
			int wkr_end = pool_idx + num_pages_per_wkr;

			// need pages to fulfill request
			if (wkr_end > pool_len) {
				this.pool = loader.Load(mgr_db, machine_name, pool, pool_idx, pool_len);
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
	public void Rls() {
		loader.Conn().Rls_conn();
	}
}
