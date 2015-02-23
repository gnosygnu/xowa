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
import gplx.dbs.engines.sqlite.*;
public interface Db_conn_bldr_wkr {
	void Clear_for_tests();
	boolean Exists(String type, Object url_obj);
	Db_conn Get(String type, Object url_obj);
	Db_conn New(String type, Object url_obj);
}
class Db_conn_bldr_wkr__sqlite implements Db_conn_bldr_wkr {
	public void Clear_for_tests() {}
	public boolean Exists(String type, Object url_obj) {
		Io_url io_url = (Io_url)url_obj; return Io_mgr._.ExistsFil(io_url);
	}
	public Db_conn Get(String type, Object url_obj) {
		Io_url io_url = (Io_url)url_obj; if (!Io_mgr._.ExistsFil(io_url)) return null;
		Db_url db_url = Db_url_.sqlite_(io_url);
		return Db_conn_pool.I.Get_or_new(db_url);
	}
	public Db_conn New(String type, Object url_obj) {
		Io_url io_url = (Io_url)url_obj;
		Io_mgr._.CreateDirIfAbsent(io_url.OwnerDir());	// must assert that dir exists
		Db_url db_url = Sqlite_url.make_(io_url);
		Db_conn conn = Db_conn_pool.I.Get_or_new(db_url);
		Sqlite_engine_.Pragma_page_size(conn, 4096);
		// conn.Conn_term();	// close conn after PRAGMA adjusted
		return conn;
	}
        public static final Db_conn_bldr_wkr__sqlite I = new Db_conn_bldr_wkr__sqlite(); Db_conn_bldr_wkr__sqlite() {}
}
class Db_conn_bldr_wkr__mem implements Db_conn_bldr_wkr {
	private final HashAdp hash = HashAdp_.new_();
	public void Clear_for_tests() {hash.Clear(); Db_conn_pool.I.Clear();}
	public boolean Exists(String type, Object url_obj) {
		Io_url io_url = (Io_url)url_obj;
		String io_url_str = io_url.Xto_api();
		return hash.Has(io_url_str);
	}
	public Db_conn Get(String type, Object url_obj) {
		Io_url io_url = (Io_url)url_obj;
		String io_url_str = io_url.Xto_api();
		if (!hash.Has(io_url_str)) return null;
		return Db_conn_pool.I.Get_or_new__mem(io_url.Xto_api());
	}
	public Db_conn New(String type, Object url_obj) {
		Io_url io_url = (Io_url)url_obj;
		String io_url_str = io_url.Xto_api();
		hash.Add(io_url_str, io_url_str);
		return Db_conn_pool.I.Get_or_new__mem(io_url.Xto_api());
	}
        public static final Db_conn_bldr_wkr__mem I = new Db_conn_bldr_wkr__mem(); Db_conn_bldr_wkr__mem() {}
}
