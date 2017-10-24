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
package gplx.dbs.engines.sqlite; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.dbs.qrys.*;
public class Sqlite_txn_mgr {
	private final    List_adp txn_list = List_adp_.New();
	public Sqlite_txn_mgr(Db_engine engine) {this.engine = engine;} private final    Db_engine engine;
	private boolean pragma_needed = Bool_.Y, txn_started = Bool_.N; // NOTE: txns only support 1 level; SQLite fails when nesting transactions; DATE:2015-03-11
	public void	Txn_bgn(String name) {
		if (String_.Len_eq_0(name)) name = "unnamed";
		if (pragma_needed) {
			pragma_needed = false;
			engine.Exec_as_obj(Db_qry_sql.xtn_("PRAGMA synchronous = OFF;"));
		}
//			Execute(Db_qry_sql.xtn_("PRAGMA ENCODING=\"UTF-8\";"));
//			Execute(Db_qry_sql.xtn_("PRAGMA journal_mode = OFF;"));	// will cause out of memory
//			Execute(Db_qry_sql.xtn_("PRAGMA journal_mode = MEMORY;"));
//			Execute(Db_qry_sql.xtn_("PRAGMA temp_store = MEMORY;"));
//			Execute(Db_qry_sql.xtn_("PRAGMA locking_mode = EXCLUSIVE;"));
//			Execute(Db_qry_sql.xtn_("PRAGMA cache_size=4000;"));	// too many will also cause out of memory
		if (txn_started) {
			engine.Exec_as_obj(Db_qry_sql.xtn_(String_.Format("SAVEPOINT {0};", name)));
		}
		else {
			txn_started = true;
			engine.Exec_as_obj(Db_qry_sql.xtn_("BEGIN TRANSACTION;"));
		}
		txn_list.Add(name);
	}
	public String Txn_end() {
		if (txn_list.Count() == 0) {Gfo_usr_dlg_.Instance.Warn_many("", "", "no txns in stack;"); return "";}
		String txn_last = (String)List_adp_.Pop_last(txn_list);
		if (txn_list.Count() == 0) {// no txns left; commit it
			engine.Exec_as_obj(Db_qry_sql.xtn_("COMMIT TRANSACTION;"));
			txn_started = false;
		}
		else
			engine.Exec_as_obj(Db_qry_sql.xtn_(String_.Format("RELEASE SAVEPOINT {0};", txn_last)));
		return txn_last;
	}
	public void	Txn_cxl() {
		if (txn_list.Count() == 0) {Gfo_usr_dlg_.Instance.Warn_many("", "", "no txns in stack;"); return;}
		String txn_last = (String)List_adp_.Pop_last(txn_list);
		if (txn_list.Count() == 0) {// no txns left; rollback
			engine.Exec_as_obj(Db_qry_sql.xtn_("ROLLBACK TRANSACTION;"));
			txn_started = false;
		}
		else
			engine.Exec_as_obj(Db_qry_sql.xtn_(String_.Format("ROLBACK TRANSACTION TO SAVEPOINT {0};", txn_last)));
	}
	public void	Txn_sav() {
		if (txn_list.Count() == 0) {Gfo_usr_dlg_.Instance.Warn_many("", "", "no txns in stack;"); return;}
		String name = (String)txn_list.Get_at(txn_list.Count() - 1);
		this.Txn_end(); this.Txn_bgn(name);
	}
}
