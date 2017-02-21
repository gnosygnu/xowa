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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.bldrs.cmds.*;
public class Xob_ns_to_db_mgr {
	private final    Xob_ns_to_db_wkr wkr; private final    Xow_db_mgr db_mgr; private final    long db_max; private boolean one_file_conn_init = true;
	private final    Ordered_hash db_list = Ordered_hash_.New();
	public Xob_ns_to_db_mgr(Xob_ns_to_db_wkr wkr, Xow_db_mgr db_mgr, long db_max) {
		this.wkr = wkr; this.db_mgr = db_mgr; this.db_max = db_max;
	}
	public Xow_db_file Get_by_ns(Xob_ns_file_itm ns_file_itm, int data_len) {
		Xow_db_file rv = null;
		if		(db_mgr.Props().Layout_text().Tid_is_all()) {
			rv = db_mgr.Db__core();
			if (one_file_conn_init) {
				one_file_conn_init = false;
				Init_tbl(rv);
			}
		}
		else if (wkr.Db_tid() == Xow_db_file_.Tid__html_data	&& db_mgr.Props().Layout_html().Tid_is_all_or_few()) {
			if (one_file_conn_init) {
				one_file_conn_init = false;
				rv = db_mgr.Dbs__make_by_tid(wkr.Db_tid());
				Init_tbl(rv);
			}
			else
				rv = db_mgr.Db__html();
		}
		else {
			int db_id = ns_file_itm.Nth_db_id();
			if (db_id == Xob_ns_file_itm.Nth_db_id_null)	// ns not assigned yet to db
				rv = Init_db(ns_file_itm);
			else
				rv = db_mgr.Dbs__get_by_id_or_fail(db_id);
			long file_len = rv.File_len();
			if (file_len + data_len > db_max) {				// file is "full"
				Term_tbl(rv);
				rv = Init_db(ns_file_itm);
			}
		}
		rv.File_len_add(data_len);
		return rv;
	}
	private Xow_db_file Init_db(Xob_ns_file_itm ns_file_itm) {
		Xow_db_file rv = db_mgr.Dbs__make_by_tid(ns_file_itm.Db_file_tid(), Int_.To_str(ns_file_itm.Ns_ids(), "|"), ns_file_itm.Nth_db_idx(), ns_file_itm.Make_file_name());
		ns_file_itm.Nth_db_id_(rv.Id());
		Init_tbl(rv);
		return rv;
	}
	private void Init_tbl(Xow_db_file db) {
		wkr.Tbl_init(db);
		db_list.Add(db.Id(), db);
	}
	private void Term_tbl(Xow_db_file db) {
		wkr.Tbl_term(db);
		db_list.Del(db.Id());
	}
	public void Rls_all() {
		Xow_db_file[] ary = (Xow_db_file[])db_list.To_ary(Xow_db_file.class);
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xow_db_file db = (Xow_db_file)ary[i];
			Term_tbl(db); // SQLITE:1_TXN; may call close on db where txn is already closed
		}
	}
	public void Commit() {
		int len = db_list.Count();
		for (int i = 0; i < len; ++i) {
			Xow_db_file db = (Xow_db_file)db_list.Get_at(i);
			db.Conn().Txn_sav();
		}
	}
}
