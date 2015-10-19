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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.bldrs.cmds.*;
public class Xob_ns_to_db_mgr {
	private final Xob_ns_to_db_wkr wkr; private final Xowd_db_mgr db_mgr; private final long db_max; private boolean one_file_conn_init = true;
	private final Ordered_hash db_list = Ordered_hash_.New();
	public Xob_ns_to_db_mgr(Xob_ns_to_db_wkr wkr, Xowd_db_mgr db_mgr, long db_max) {
		this.wkr = wkr; this.db_mgr = db_mgr; this.db_max = db_max;
	}
	public Xowd_db_file Get_by_ns(Xob_ns_file_itm ns_file_itm, int data_len) {
		Xowd_db_file rv = null;
		if		(wkr.Db_tid() == Xowd_db_file_.Tid_text			&& db_mgr.Props().Layout_text().Tid_is_all_or_few()) {
			rv = db_mgr.Db__core();
			if (one_file_conn_init) {
				one_file_conn_init = false;
				Init_tbl(rv);
			}
		}
		else if (wkr.Db_tid() == Xowd_db_file_.Tid_html_data	&& db_mgr.Props().Layout_html().Tid_is_all_or_few()) {
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
				rv = db_mgr.Dbs__get_at(db_id);
			long file_len = rv.File_len();
			if (file_len + data_len > db_max) {				// file is "full"
				Term_tbl(rv);
				rv = Init_db(ns_file_itm);
			}
		}
		rv.File_len_add(data_len);
		return rv;
	}
	private Xowd_db_file Init_db(Xob_ns_file_itm ns_file_itm) {
		Xowd_db_file rv = db_mgr.Dbs__make_by_tid(ns_file_itm.Db_file_tid(), Int_.To_str(ns_file_itm.Ns_ids(), "|"), ns_file_itm.Nth_db_idx(), ns_file_itm.Make_file_name());
		ns_file_itm.Nth_db_id_(rv.Id());
		Init_tbl(rv);
		return rv;
	}
	private void Init_tbl(Xowd_db_file db) {
		wkr.Tbl_init(db);
		db_list.Add(db.Id(), db);
	}
	private void Term_tbl(Xowd_db_file db) {
		wkr.Tbl_term(db);
		db_list.Del(db.Id());
	}
	public void Rls_all() {
		Xowd_db_file[] ary = (Xowd_db_file[])db_list.To_ary(Xowd_db_file.class);
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Xowd_db_file db = (Xowd_db_file)ary[i];
			Term_tbl(db); // SQLITE:1_TXN; may call close on db where txn is already closed
		}
	}
	public void Commit() {
		int len = db_list.Count();
		for (int i = 0; i < len; ++i) {
			Xowd_db_file db = (Xowd_db_file)db_list.Get_at(i);
			db.Conn().Txn_sav();
		}
	}
}
