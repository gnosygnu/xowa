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
package gplx.xowa.wikis.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.tdbs.*; import gplx.xowa.wikis.tdbs.hives.*; import gplx.xowa.wikis.tdbs.xdats.*;
public class Xodb_page_rdr__tdb implements Xodb_page_rdr {
	private final    Xow_ns_mgr ns_mgr; private final    Xotdb_fsys_mgr fsys_mgr;
	private int cur_ns_ord = -1; private Xow_ns cur_ns; private Io_url[] cur_file_ary; private Io_url cur_file; private int cur_file_idx = -1;
	private final    Xob_xdat_file cur_xdat_file = new Xob_xdat_file(); private int cur_xdat_len, cur_xdat_idx; private final    Xob_xdat_itm cur_xdat_itm = new Xob_xdat_itm();
	private int page_id = 0;
	public Xodb_page_rdr__tdb(Xowe_wiki wiki) {
		this.ns_mgr = wiki.Ns_mgr(); this.fsys_mgr = wiki.Tdb_fsys_mgr();
	}
	public boolean Move_next() {
		while (true) {
			if (cur_ns == null) {
				++this.cur_ns_ord;
				if (cur_ns_ord >= ns_mgr.Ords_len()) return false;	// no more ns; return false;
				this.cur_ns = ns_mgr.Ords_get_at(cur_ns_ord);
				this.cur_file_ary = Get_page_url_ary(cur_ns);
				if (cur_file_ary == null) {							// ns doesn't have files; try next ns;
					cur_ns = null;
					continue;
				}
				this.cur_file_idx = -1;
				this.cur_file = null;
			}
			if (cur_file == null) {
				++this.cur_file_idx;
				if (cur_file_idx == cur_file_ary.length) {			// no more files in ns; try next ns;
					cur_ns = null;
					continue;
				}
				this.cur_file = cur_file_ary[cur_file_idx];
				byte[] bry = Io_mgr.Instance.LoadFilBry(cur_file);
				cur_xdat_file.Clear().Parse(bry, bry.length, cur_file);
				this.cur_xdat_len = cur_xdat_file.Count();
				this.cur_xdat_idx = -1;
			}
			++this.cur_xdat_idx;
			if (cur_xdat_idx == cur_xdat_len) {						// no more rows in file; try next file
				cur_file = null;
				continue;
			}
			cur_xdat_file.GetAt(cur_xdat_itm, cur_xdat_idx);
			return true;
		}
	}
	public boolean Read(Xowd_page_itm page) {
		boolean rv = Xodb_load_mgr_txt.Load_page_or_false(page, cur_xdat_itm, cur_ns.Id());
		if (!rv)
			Xoa_app_.Usr_dlg().Warn_many("", "", "page_rdr.tdb; unable to read page; ns=~{0}, file=~{1} itm_idx=~{2}", cur_ns.Id(), cur_file.Raw(), cur_xdat_idx);
		// wiki.Db_mgr().Load_mgr().Load_by_ttl(page, cur_ns, page.Ttl_page_db());
		page.Id_(++page_id);
		return rv;
	}
	public void Rls() {}
	private Io_url[] Get_page_url_ary(Xow_ns ns) {
		Io_url reg_url = fsys_mgr.Url_ns_reg(ns.Num_str(), Xotdb_dir_info_.Tid_ttl);
		if (!Io_mgr.Instance.ExistsFil(reg_url)) return null;
		Xowd_regy_mgr reg_mgr = new Xowd_regy_mgr(reg_url);
		Xowd_hive_regy_itm[] file_ary = reg_mgr.Files_ary();
		int len = file_ary.length;
		Io_url[] rv = new Io_url[len];
		for (int i = 0; i < len; ++i) {
			int fil_idx = file_ary[i].Idx();
			rv[i] = fsys_mgr.Url_ns_fil(Xotdb_dir_info_.Tid_page, ns.Id(), fil_idx);
		}
		return rv;
	}
}
