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
import gplx.dbs.engines.noops.*; import gplx.dbs.engines.mems.*; import gplx.dbs.engines.sqlite.*; import gplx.dbs.engines.tdbs.*;
import gplx.dbs.engines.mysql.*; import gplx.dbs.engines.postgres.*;
public class Db_conn_info_ {
	public static final    Db_conn_info Null			= Noop_conn_info.Instance;
	public static final    Db_conn_info Test			= Mysql_conn_info.new_("127.0.0.1", "unit_tests", "root", "mysql7760");
	public static Db_conn_info parse(String raw)		{return Db_conn_info_pool.Instance.Parse(raw);}
	public static Db_conn_info sqlite_(Io_url url)		{return Sqlite_conn_info.load_(url);}
	public static Db_conn_info tdb_(Io_url url)			{return Tdb_conn_info.new_(url);}
	public static Db_conn_info mem_(String db)			{return Mem_conn_info.new_(db);}
	public static final    String Key_tdb = Tdb_conn_info.Tid_const;
	public static Io_url To_url(Db_conn_info cs) {
		if		(String_.Eq(cs.Key(), Sqlite_conn_info.Key_const))		return ((Sqlite_conn_info)cs).Url();
		else if (String_.Eq(cs.Key(), Mem_conn_info.Instance.Key()))	return Io_url_.mem_fil_("mem/" + ((Mem_conn_info)cs).Database());
		else throw Err_.new_unhandled_default(cs.Key());
	}
}
class Db_conn_info_pool {
	private final    Ordered_hash regy = Ordered_hash_.New();
	public Db_conn_info_pool() {
		this.Add(Noop_conn_info.Instance).Add(Tdb_conn_info.Instance).Add(Mysql_conn_info.Instance).Add(Postgres_conn_info.Instance).Add(Sqlite_conn_info.Instance);
		this.Add(Mem_conn_info.Instance);
	}
	public Db_conn_info_pool Add(Db_conn_info itm) {regy.Add_if_dupe_use_nth(itm.Key(), itm); return this;}
	public Db_conn_info Parse(String raw) {// assume each pair has format of: name=val;
		try {
			Keyval_hash hash = new Keyval_hash();
			String[] terms = String_.Split(raw, ";");
			String url_tid = "";
			for (String term : terms) {
				if (String_.Len(term) == 0) continue;
				String[] kv = String_.Split(term, "=");
				if (String_.Eq(kv[0], "gplx_key"))
					url_tid = kv[1]; // NOTE: do not add to GfoMsg; will not be part of ApiStr
				else
					hash.Add(kv[0], kv[1]);
			}
			Db_conn_info prototype = (Db_conn_info)regy.Get_by(url_tid);
			return prototype.New_self(raw, hash);
		}
		catch(Exception exc) {throw Err_.new_parse_exc(exc, Db_conn_info.class, raw);}
	}
	public Db_conn_info Parse_or_sqlite_or_fail(String raw) {// assume each pair has format of: name=val;
		Keyval_hash hash = new Keyval_hash();
		String[] kvps = String_.Split(raw, ";");
		String cs_tid = null;
		int kvps_len = kvps.length; 
		for (int i = 0; i < kvps_len; ++i) {
			String kvp_str = kvps[i];
			if (String_.Len(kvp_str) == 0) continue;	// ignore empty; EX: "data source=/db.sqlite;;"
			String[] kvp = String_.Split(kvp_str, "=");
			String key = kvp[0], val = kvp[1];
			if (String_.Eq(key, "gplx_key"))
				cs_tid = val;	// NOTE: do not add to GfoMsg; will not be part of ApiStr
			else
				hash.Add(key, val);
		}
		if (cs_tid == null) {	// gplx_key not found; try url as sqlite; EX: "/db.sqlite"
			Io_url sqlite_url = null;
			try {sqlite_url = Io_url_.new_any_(raw);}
			catch (Exception exc) {throw Err_.new_exc(exc, "dbs", "invalid connection String", "raw", raw);}
			hash.Clear();
			cs_tid = Sqlite_conn_info.Key_const;
			hash.Add(Sqlite_conn_info.Cs__data_source, sqlite_url.Raw());
			hash.Add(Sqlite_conn_info.Cs__version	, Sqlite_conn_info.Cs__version__3);
		}
		Db_conn_info prototype = (Db_conn_info)regy.Get_by(cs_tid);
		return prototype.New_self(raw, hash);
	}
	public static final    Db_conn_info_pool Instance = new Db_conn_info_pool();
}
