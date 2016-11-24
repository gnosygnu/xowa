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
public class Db_conn_bldr {
	private final    Object thread_lock = new Object();	// LOCK:synchronized else random failures in Schema_mgr due to diff conn pointing to same db; DATE:2016-07-12
	private Db_conn_bldr_wkr wkr;
	public void Reg_default_sqlite()	{wkr = Db_conn_bldr_wkr__sqlite.Instance; wkr.Clear_for_tests();}
	public void Reg_default_mem()		{wkr = Db_conn_bldr_wkr__mem.Instance; wkr.Clear_for_tests();}
	public boolean Exists(Io_url url) {synchronized (thread_lock) {return wkr.Exists(url);}}
	public Db_conn Get(Io_url url) {synchronized (thread_lock) {return wkr.Get(url);}}
	public Db_conn New(Io_url url) {synchronized (thread_lock) {return wkr.New(url);}}
	public Db_conn_bldr_data Get_or_new(Io_url url) {
		synchronized (thread_lock) {
			boolean exists = wkr.Exists(url);
			Db_conn conn = exists ? Get(url) : New(url);
			return new Db_conn_bldr_data(conn, exists);
		}
	}
	public Db_conn Get_or_noop(Io_url url) {
		synchronized (thread_lock) {
			Db_conn rv = wkr.Get(url);
			return rv == null ? Db_conn_.Noop : rv;
		}
	}
	public Db_conn Get_or_autocreate(boolean autocreate, Io_url url) {
		synchronized (thread_lock) {
			boolean exists = wkr.Exists(url);
			if (exists) return Get(url);
			if (autocreate) return New(url);
			else throw Err_.new_("dbs", "db does not exist", "url", url.Raw());
		}
	}
	public Db_conn Get_or_fail(Io_url url) {
		Db_conn rv = Get(url);
		if (rv == Db_conn_.Noop) throw Err_.new_wo_type("connection is null; file does not exist: file={0}", "file", url.Raw());
		return rv;
	}
        public static final    Db_conn_bldr Instance = new Db_conn_bldr(); Db_conn_bldr() {}
}
