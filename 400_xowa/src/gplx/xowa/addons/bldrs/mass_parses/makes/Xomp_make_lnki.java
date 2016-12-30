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
			int tmp_xomp_uid_max = -1;	// maximum uid for a grp of wkrs; EX: looping over 8 wkrs with xomp_uid range of 1 - 1000; max xomp_uid may only be 990 b/c pages are missing / failed
			for (int i = 0; i < wkr_count; ++i) {
				Xomp_wkr_db src_wkr_db = src_mgr_dbs[i];
				int wkr_uid_max = tmp_xomp_uid_max;
				for (Xomp_make_merger merger : merger_ary)
					wkr_uid_max = merger.Merger__load(src_mgr_db, src_wkr_db, cur_xomp_uid, cur_xomp_uid + uid_count);
				if (wkr_uid_max > tmp_xomp_uid_max)
					tmp_xomp_uid_max = wkr_uid_max;
			}

			// save rows
			for (Xomp_make_merger merger : merger_ary)
				merger.Merger__save();

			// NOTE: not ">=" else small wikis will fail with 0 images; EX:cs.q; DATE:2016-09-04
			if (tmp_xomp_uid_max > max_xomp_uid || tmp_xomp_uid_max == -1) break;	// if max_xomp_uid seen, break; note that ">" necessary because max_xomp_uid may not be in set of wkrs;
			cur_xomp_uid += uid_count;	// note that this sequentially counts up by uid_count (1000), so inevitable that cur_xomp_uid will exceed wkr_uid_max
		}

		// save rows
		for (Xomp_make_merger merger : merger_ary)
			merger.Merger__term();
		src_mgr_db.Conn().Rls_conn();
	}
}
