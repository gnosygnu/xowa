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
package gplx.xowa.addons.wikis.pages.randoms.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.randoms.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.addons.wikis.pages.randoms.dbs.*;
class Rndm_ns_rebuilder {
	public void Exec(Xow_wiki wiki, int rndm_interval) {
		// get wkr; page_tbl
		Rndm_addon addon = Rndm_addon.Get(wiki);
		Rndm_bldr_wkr wkr = addon.Mgr().New_bldr();
		Xowd_page_tbl page_tbl = wiki.Data__core_mgr().Db__core().Tbl__page();
		String fld_page_id = page_tbl.Fld_page_id();
		Db_conn page_conn = page_tbl.Conn();
		page_conn.Meta_idx_assert(page_tbl.Tbl_name(), "rndm_rebuild", page_tbl.Fld_page_ns(), fld_page_id);

		// loop over ns
		Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
		int len = ns_mgr.Ids_len();
		wkr.Conn().Txn_bgn("rndm");
		for (int i = 0; i < len; ++i) {
			Xow_ns ns = ns_mgr.Ids_get_at(i);
			Gfo_log_.Instance.Prog("reading ns", "ns", ns.Id());
			int page_id_cur = -1;
			wkr.Exec_qry_bgn(Rndm_qry_itm.New_by_ns(wiki, ns.Id()));

			// read pages in ns where page_id > last_page_id
			while (true) {
				Gfo_log_.Instance.Prog("reading pages", "page_id", page_id_cur);
				String sql = String_.Format("SELECT * FROM page WHERE page_namespace = {0} AND page_id > {1} ORDER BY page_id", ns.Id(), page_id_cur); // ANSI.Y
				int rdr_count = 0;
				wkr.Exec_rng_bgn();
				Db_rdr rdr = page_conn.Stmt_sql(sql).Exec_select__rls_auto();
				try {
					// read pages until rndm_interval
					while (rdr.Move_next()) {
						int page_id = rdr.Read_int(fld_page_id);
						wkr.Exec_seq_itm(page_id);
						if (++rdr_count == rndm_interval) {
							page_id_cur = page_id;
							break;
						}
					}
				}
				finally {rdr.Rls();}
				wkr.Exec_rng_end_or_null();
				if (rdr_count != rndm_interval)
					break;
			}
			wkr.Exec_qry_end();
		}
		wkr.Conn().Txn_end();
		page_conn.Meta_idx_delete("page", "rndm_rebuild");
	}
}
