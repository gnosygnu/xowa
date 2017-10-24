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
package gplx.xowa.addons.bldrs.files.cksums; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.core.ios.streams.*; import gplx.core.security.*;
import gplx.dbs.*; import gplx.xowa.addons.bldrs.files.cksums.dbs.*;
import gplx.xowa.files.*; import gplx.fsdb.*; import gplx.fsdb.data.*;
public class Xocksum_calc_mgr {
	public void Exec(Xowe_wiki wiki) {
		// get conn variables
		Xocksum_cksum_db db = Xocksum_cksum_db.Get(wiki);
		Db_conn conn = db.Conn();
		Xocksum_cksum_tbl tbl = db.Tbl__cksum();
		conn.Meta_tbl_assert(tbl);
		
		// insert missing items
		tbl.Insert_missing();
		tbl.Create_idx();

		// get updates
		int count = 0;
		Hash_algo md5_algo = Hash_algo_.New__md5();
		List_adp updates = List_adp_.New();
		String cur_date = Datetime_now.Get().XtoStr_gplx();
		Db_stmt select_stmt = tbl.Select_samples_stmt(10000);
		while (true) {
			// get cksum_rows
			Xocksum_cksum_row[] rows = tbl.Select_samples(select_stmt);

			// loop cksum_rows and (a) get bin_data; (b) if md5 diff, then add to updates
			int len = rows.length; if (len == 0) break;
			for (int i = 0; i < len; ++i) {
				Xocksum_cksum_row row = rows[i];
				byte[] bin_bry = Get_bin(wiki, row);
				if (bin_bry == null) {
					Gfo_usr_dlg_.Instance.Prog_many("", "", "null; fil_id=~{0} thm_id=~{1}", row.Fil_id(), row.Thm_id());
					bin_bry = Bry_.Empty;
				}
				row.Bin_size_(bin_bry.length);
				byte[] md5 = md5_algo.Hash_bry_as_bry(bin_bry);
				if (!Bry_.Eq(md5, row.Cksum_val())) {
					row.Cksum_val_(md5);
					updates.Add(row);
				}
			}

			// run updates
			conn.Txn_bgn("cksum_update");
			len = updates.Len();
			for (int i = 0; i < len; ++i) {
				Xocksum_cksum_row row = (Xocksum_cksum_row)updates.Get_at(i);
				tbl.Update(row.Fil_id(), row.Thm_id(), row.Bin_db_id(), row.Bin_size(), row.Cksum_tid(), 0, row.Cksum_val(), cur_date);
				if (++count % 2000 == 0) Gfo_usr_dlg_.Instance.Prog_many("", "", "updating; rows=~{0}", count);
			}
			updates.Clear();
			conn.Txn_end();
		}
		select_stmt.Rls();
	}
	private byte[] Get_bin(Xowe_wiki wiki, Xocksum_cksum_row row) {
		int bin_id = row.Thm_id() == -1 ? row.Fil_id() : row.Thm_id();
		Fsd_bin_itm bin_itm = wiki.File__mnt_mgr().Mnts__get_main().Bin_mgr().Dbs__get_at(row.Bin_db_id()).Select_as_itm(bin_id);
		return bin_itm.Bin_data();
	}
}
