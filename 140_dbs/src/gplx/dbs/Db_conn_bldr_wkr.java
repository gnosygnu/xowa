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
package gplx.dbs; import gplx.*;
import gplx.dbs.engines.sqlite.*; import gplx.dbs.qrys.bats.*;
public interface Db_conn_bldr_wkr {
	void Clear_for_tests();
	boolean Exists(Io_url url);
	Db_conn Get(Io_url url);
	Db_conn New(Io_url url);
}
class Db_conn_bldr_wkr__sqlite implements Db_conn_bldr_wkr {
	public void Clear_for_tests() {}
//		public Db_batch_mgr Batch_mgr() {return batch_mgr;} private final    Db_batch_mgr batch_mgr = new Db_batch_mgr();
	public boolean Exists(Io_url url) {return Io_mgr.Instance.ExistsFil(url);}
	public Db_conn Get(Io_url url) {
		if (!Io_mgr.Instance.ExistsFil(url)) return null;
		Db_conn_info db_url = Db_conn_info_.sqlite_(url);
		return Db_conn_pool.Instance.Get_or_new(db_url);
	}
	public Db_conn New(Io_url url) {
		Io_mgr.Instance.CreateDirIfAbsent(url.OwnerDir());	// must assert that dir exists
		Db_conn_info db_url = Sqlite_conn_info.make_(url);
		Db_conn conn = Db_conn_pool.Instance.Get_or_new(db_url);
		conn.Exec_qry(Sqlite_pragma.New__page_size(4096));
		return conn;
	}
        public static final    Db_conn_bldr_wkr__sqlite Instance = new Db_conn_bldr_wkr__sqlite(); Db_conn_bldr_wkr__sqlite() {}
}
class Db_conn_bldr_wkr__mem implements Db_conn_bldr_wkr {
	private final    Hash_adp hash = Hash_adp_.new_();
	public void Clear_for_tests() {hash.Clear(); Db_conn_pool.Instance.Rls_all();}
	public boolean Exists(Io_url url) {
		String io_url_str = url.Xto_api();
		return hash.Has(io_url_str);
	}
	public Db_conn Get(Io_url url) {
		String io_url_str = url.Xto_api();
		if (!hash.Has(io_url_str)) return null;
		return Get_or_new(url);
	}
	public Db_conn New(Io_url url) {
		String io_url_str = url.Xto_api();
		hash.Add_if_dupe_use_nth(io_url_str, io_url_str);	// NOTE: tests can call New multiple times; don't fail if exists; just overwrite existing entry; DATE:2016-04-21
		return Get_or_new(url);
	}
	private Db_conn Get_or_new(Io_url url) {
		return Db_conn_pool.Instance.Get_or_new(gplx.dbs.engines.mems.Mem_conn_info.new_(url.Xto_api()));
	}
        public static final    Db_conn_bldr_wkr__mem Instance = new Db_conn_bldr_wkr__mem(); Db_conn_bldr_wkr__mem() {}
}
