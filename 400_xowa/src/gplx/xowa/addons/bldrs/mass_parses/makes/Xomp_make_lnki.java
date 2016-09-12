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
import gplx.core.brys.*; import gplx.core.primitives.*;
import gplx.dbs.*; import gplx.xowa.htmls.core.dbs.*; import gplx.xowa.addons.bldrs.mass_parses.dbs.*; import gplx.xowa.addons.bldrs.files.dbs.*;
import gplx.xowa.bldrs.*;
class Xomp_make_lnki {
	public void Exec(Xowe_wiki wiki, int uid_count) {
		// init
		Xomp_mgr_db xomp_db = Xomp_mgr_db.New__load(wiki);

		Xob_db_file make_db = Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir());
		Xob_lnki_temp_tbl lnki_temp_tbl = new Xob_lnki_temp_tbl(make_db.Conn());
		make_db.Conn().Meta_tbl_remake(lnki_temp_tbl);
		lnki_temp_tbl.Insert_bgn();

		// create ary; add index
		int wkr_count = xomp_db.Wkr_tbl().Select_count();
		Xomp_wkr_db[] db_ary = new Xomp_wkr_db[wkr_count];
		for (int i = 0; i < wkr_count; ++i) {
			Xomp_wkr_db wkr_db = Xomp_wkr_db.New(xomp_db.Dir(), i);
			db_ary[i] = wkr_db;
			wkr_db.Conn().Meta_idx_assert("lnki_temp", "lnki_page_id", "lnki_page_id");
		}

		// get max xomp_uid; note that xomp_uid is (a) per page; (b) ordered by page_ns, page_id; (c) starts from 1
		int max_xomp_uid = xomp_db.Conn().Exec_select_max_as_int("xomp_page", "xomp_uid", -1);

		// loop over wkrs using range of xomp_uid
		List_adp rows = List_adp_.New();
		int cur_xomp_uid = -1; Int_obj_ref lnki_id_ref = Int_obj_ref.New_zero();
		while (true) {
			int tmp_xomp_uid_max = -1;	// maximum uid for a grp of wkrs; EX: looping over 8 wkrs with xomp_uid range of 1 - 1000; max xomp_uid may only be 990 b/c pages are missing / failed
			for (int i = 0; i < wkr_count; ++i) {
				Xomp_wkr_db wkr_db = db_ary[i];
				int wkr_uid_max = Load_rows(rows, xomp_db, wkr_db, cur_xomp_uid, cur_xomp_uid + uid_count, lnki_id_ref);
				if (wkr_uid_max > tmp_xomp_uid_max)
					tmp_xomp_uid_max = wkr_uid_max;
			}
			// NOTE: not ">=" else small wikis will fail with 0 images; EX:cs.q; DATE:2016-09-04
			if (tmp_xomp_uid_max > max_xomp_uid || tmp_xomp_uid_max == -1) break;	// if max_xomp_uid seen, break; note that ">" necessary because max_xomp_uid may not be in set of wkrs;
			cur_xomp_uid += uid_count;	// note that this sequentially counts up by uid_count (1000), so inevitable that cur_xomp_uid will exceed wkr_uid_max
			Gfo_usr_dlg_.Instance.Prog_many("", "", "building lnki_temp; cur_xomp_uid=~{0}", cur_xomp_uid);
			Save_rows(rows, lnki_temp_tbl);
		}

		// term
		lnki_temp_tbl.Insert_end();
		xomp_db.Conn().Rls_conn();
		make_db.Conn().Rls_conn();
	}
	private int Load_rows(List_adp rows, Xomp_mgr_db xomp_db, Xomp_wkr_db wkr_db, int uid_bgn, int uid_end, Int_obj_ref lnki_id) {
		// build sql
		Db_attach_mgr attach_mgr = new Db_attach_mgr(xomp_db.Conn());
		attach_mgr.Conn_links_(new Db_attach_itm("wkr_db", wkr_db.Conn()));
		String sql = String_.Format(String_.Concat_lines_nl_skip_last
		( "SELECT  mgr.xomp_uid"
		, ",       wkr.*"
		, ",       wkr.lnki_tier_id, wkr.lnki_page_id, wkr.lnki_ttl, wkr.lnki_commons_ttl, wkr.lnki_ext, wkr.lnki_type, wkr.lnki_src_tid, wkr.lnki_w, wkr.lnki_h, wkr.lnki_upright, wkr.lnki_time, wkr.lnki_page"
		, "FROM    <wkr_db>lnki_temp wkr"
		, "        JOIN xomp_page mgr ON wkr.lnki_page_id = mgr.page_id"
		, "WHERE   mgr.xomp_uid > {0} AND mgr.xomp_uid <= {1}"	// mgr.xomp_uid will sort pages by ns_id, page_id
		)
		, uid_bgn
		, uid_end
		);	
		sql = attach_mgr.Resolve_sql(sql);

		attach_mgr.Attach();
		Db_rdr rdr = xomp_db.Conn().Stmt_sql(sql).Exec_select__rls_auto();	// ANSI.Y
		int rv = -1;
		try {
			while (rdr.Move_next()) {
				rv = rdr.Read_int("xomp_uid");
				Xob_lnki_temp_row row = new Xob_lnki_temp_row();
				row.Load(rdr, lnki_id.Val_add_pre());
				rows.Add(row);
			}
		} finally {rdr.Rls();}
		attach_mgr.Detach();
		return rv;
	}

	private void Save_rows(List_adp rows, Xob_lnki_temp_tbl lnki_temp_tbl) {
		int len = rows.Len();
		for (int i = 0; i < len; ++i) {
			Xob_lnki_temp_row row = (Xob_lnki_temp_row)rows.Get_at(i);
			lnki_temp_tbl.Insert_cmd_by_batch(row.Lnki_tier_id(), row.Lnki_page_id(), row.Lnki_ttl(), row.Lnki_commons_ttl()
				, row.Lnki_ext(), row.Lnki_type(), row.Lnki_src_tid(), row.Lnki_w(), row.Lnki_h(), row.Lnki_upright()
				, row.Lnki_time(), row.Lnki_page());
		}
		rows.Clear();
	}
}
