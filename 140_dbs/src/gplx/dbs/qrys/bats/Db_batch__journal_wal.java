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
package gplx.dbs.qrys.bats; import gplx.*; import gplx.dbs.*; import gplx.dbs.qrys.*;
import gplx.dbs.engines.*; import gplx.dbs.engines.sqlite.*;
public class Db_batch__journal_wal {
	public static void Batch__init(Db_batch_mgr batch_mgr) {
		batch_mgr.Conn_bgn().Add(Db_batch__journal_wal__conn_bgn.Instance);
		batch_mgr.Conn_end().Add(Db_batch__journal_wal__conn_end.Instance);
		batch_mgr.Conn_bgn().Add(Db_batch__journal_wal__txn_end.Instance);
	}
	public static void Batch__term(Db_batch_mgr batch_mgr) {
		batch_mgr.Conn_bgn().Del(Db_batch__journal_wal__conn_bgn.Instance.Key());
		batch_mgr.Conn_end().Del(Db_batch__journal_wal__conn_end.Instance.Key());
		batch_mgr.Txn_end().Del(Db_batch__journal_wal__txn_end.Instance.Key());
	}
}
class Db_batch__journal_wal__conn_bgn implements Db_batch_itm {
	public String Key() {return KEY;} public static final String KEY = "journal_wal.conn_bgn";
	public void Qry_bat__run(Db_engine engine) {
		if (engine.Props().Match(Sqlite_pragma.Const__journal_mode, Sqlite_pragma.Const__journal_mode__off)) return;	// if off, don't enable wal
		if (engine.Props().Match(Sqlite_pragma.Const__journal_mode, Sqlite_pragma.Const__journal_mode__wal)) return;	// if wal, don't enable again
		engine.Props().Add(Sqlite_pragma.Const__journal_mode, Sqlite_pragma.Const__journal_mode__wal);
		engine.Exec_as_obj(Sqlite_pragma.New__journal__wal());
		engine.Exec_as_obj(Sqlite_pragma.New__synchronous__normal());
		engine.Exec_as_obj(Sqlite_pragma.New__wal_autocheckpoint(0));
	}
        public static final    Db_batch__journal_wal__conn_bgn Instance = new Db_batch__journal_wal__conn_bgn(); Db_batch__journal_wal__conn_bgn() {}
}
class Db_batch__journal_wal__conn_end implements Db_batch_itm {
	public String Key() {return KEY;} public static final String KEY = "journal_wal.conn_end";
	public void Qry_bat__run(Db_engine engine) {
		if (!engine.Props().Match(Sqlite_pragma.Const__journal_mode, Sqlite_pragma.Const__journal_mode__wal)) return;	// if in off mode, don't enable wal
		engine.Exec_as_obj(Sqlite_pragma.New__wal_checkpoint__truncate());
		engine.Exec_as_obj(Sqlite_pragma.New__journal__delete());
		engine.Exec_as_obj(Sqlite_pragma.New__synchronous__full());
		engine.Props().Del(Sqlite_pragma.Const__journal_mode);
	}
        public static final    Db_batch__journal_wal__conn_end Instance = new Db_batch__journal_wal__conn_end(); Db_batch__journal_wal__conn_end() {}
}
class Db_batch__journal_wal__txn_end implements Db_batch_itm {
	public String Key() {return KEY;} public static final String KEY = "journal_wal.txn_end";
	public void Qry_bat__run(Db_engine engine) {
		if (!engine.Props().Match(Sqlite_pragma.Const__journal_mode, Sqlite_pragma.Const__journal_mode__wal)) return;	// if in off mode, don't enable wal
		engine.Exec_as_obj(Sqlite_pragma.New__wal_checkpoint__truncate());
	}
        public static final    Db_batch__journal_wal__txn_end Instance = new Db_batch__journal_wal__txn_end(); Db_batch__journal_wal__txn_end() {}
}
