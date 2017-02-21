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
