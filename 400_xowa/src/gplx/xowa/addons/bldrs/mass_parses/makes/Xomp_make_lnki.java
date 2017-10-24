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
package gplx.xowa.addons.bldrs.mass_parses.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
class Xomp_make_lnki {
	public void Exec(Xowe_wiki wiki, Xomp_make_cmd_cfg cfg, int uid_count) {
		// init
		Xomp_mgr_db src_mgr_db = Xomp_mgr_db.New__load(wiki);

		// make mergers; always add lnki_temp
		List_adp merger_list = List_adp_.New();
		merger_list.Add(new Xomp_make_merger__lnki_temp());
		if (cfg.Merger_wkrs().Has("xnde"))		merger_list.Add(new Xomp_make_merger__xnde());
		Xomp_make_merger[] merger_ary = (Xomp_make_merger[])merger_list.To_ary_and_clear(Xomp_make_merger.class);

		// create ary; add index
		int wkr_count = src_mgr_db.Tbl__wkr().Select_count();
		Xomp_wkr_db[] src_mgr_dbs = new Xomp_wkr_db[wkr_count];
		for (int i = 0; i < wkr_count; ++i) {
			Xomp_wkr_db src_wkr_db = Xomp_wkr_db.New(src_mgr_db.Dir(), i);
			src_mgr_dbs[i] = src_wkr_db;
		}

		// run init
		for (Xomp_make_merger merger : merger_ary)
			merger.Merger__init(wiki, src_mgr_dbs);

		// get max xomp_uid; note that xomp_uid is (a) per page; (b) ordered by page_ns, page_id; (c) starts from 1
		int max_xomp_uid = src_mgr_db.Conn().Exec_select_max_as_int("xomp_page", "xomp_uid", -1);

		// loop over wkrs using range of xomp_uid
		int cur_xomp_uid = -1;
		while (true) {
			// load rows
			Gfo_usr_dlg_.Instance.Prog_many("", "", "merging rows; bgn_uid=~{0} end_uid=~{1}", cur_xomp_uid, cur_xomp_uid + uid_count);
			for (int i = 0; i < wkr_count; ++i) {
				Xomp_wkr_db src_wkr_db = src_mgr_dbs[i];
				for (Xomp_make_merger merger : merger_ary)
					merger.Merger__load(src_mgr_db, src_wkr_db, cur_xomp_uid, cur_xomp_uid + uid_count);
			}

			// save rows
			for (Xomp_make_merger merger : merger_ary)
				merger.Merger__save();

			// NOTE: not ">=" else small wikis will fail with 0 images; EX:cs.q; DATE:2016-09-04
			cur_xomp_uid += uid_count;	// note that this sequentially counts up by uid_count (1000), so inevitable that cur_xomp_uid will exceed wkr_uid_max
			if (cur_xomp_uid > max_xomp_uid) break;	// if max_xomp_uid seen, break; note that ">" necessary because max_xomp_uid may not be in set of wkrs;
		}

		// save rows
		for (Xomp_make_merger merger : merger_ary)
			merger.Merger__term();
		src_mgr_db.Conn().Rls_conn();
	}
}
