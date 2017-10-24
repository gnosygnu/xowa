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
import gplx.dbs.engines.*;
import gplx.dbs.qrys.bats.*;
public class Db_conn_pool {	// PURPOSE: cache one connection per connection_string
	private final    Ordered_hash hash = Ordered_hash_.New();
	public Db_batch_mgr Batch_mgr() {return batch_mgr;} private final    Db_batch_mgr batch_mgr = new Db_batch_mgr();
	public void Del(Db_conn_info url)				{hash.Del(url.Db_api());}
	public Db_conn Get_or_new(String s)				{return Get_or_new(Db_conn_info_.parse(s));}
	public Db_conn Get_or_new(Db_conn_info url) {
		Db_conn rv = (Db_conn)hash.Get_by(url.Db_api());
		if (rv == null) {
			Db_engine prime = (Db_engine)prime_hash.Get_by(url.Key()); if (prime == null) Err_.new_wo_type("db engine prototype not found", "key", url.Key());
			Db_engine clone = prime.New_clone(url);
			rv = new Db_conn(clone);
			clone.Batch_mgr().Copy(clone.Tid(), batch_mgr);
			hash.Add(url.Db_api(), rv);
		}
		return rv;
	}
	public void Add_existing(Db_conn conn) {
		hash.Add(conn.Conn_info().Db_api(), conn);
	}
	public void Rls_all() {
		int len = hash.Len();
		Db_conn[] rls_ary = new Db_conn[len];
		for (int i = 0; i < len; ++i)
			rls_ary[i] = (Db_conn)hash.Get_at(i);
		for (int i = 0; i < len; ++i)
			rls_ary[i].Rls_conn();
		hash.Clear();
	}

	private final    Hash_adp prime_hash = Hash_adp_.New();
        public static final    Db_conn_pool Instance = new Db_conn_pool(); Db_conn_pool() {this.Init();}
	public void Primes__add(Db_engine... ary) {	// PUBLIC.DRD:
		for (Db_engine itm : ary)
			prime_hash.Add(itm.Tid(), itm);
	}
	private void Init() {
		this.Primes__add
		( gplx.dbs.engines.noops	.Noop_engine.Instance
		, gplx.dbs.engines.mems		.Mem_engine.Instance
		, gplx.dbs.engines.sqlite	.Sqlite_engine.Instance
		, gplx.dbs.engines.mysql	.Mysql_engine.Instance
		, gplx.dbs.engines.postgres	.Postgres_engine.Instance
		, gplx.dbs.engines.tdbs		.TdbEngine.Instance
		);
	}
}
