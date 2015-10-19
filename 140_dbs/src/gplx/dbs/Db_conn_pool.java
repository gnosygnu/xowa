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
import gplx.dbs.engines.*; import gplx.dbs.engines.nulls.*; import gplx.dbs.engines.mems.*; import gplx.dbs.engines.tdbs.*;
import gplx.dbs.engines.sqlite.*; import gplx.dbs.engines.mysql.*; import gplx.dbs.engines.postgres.*;
public class Db_conn_pool {
	private final Hash_adp conn_hash = Hash_adp_.new_(); private final Hash_adp engine_hash = Hash_adp_.new_();		
	public void Clear() {conn_hash.Clear();}
	public void Del(Db_conn_info url) {conn_hash.Del(url.Xto_api());}
	public Db_conn Get_or_new__mem(String db)		{return Get_or_new(Db_conn_info__mem.new_(db));}
	public Db_conn Get_or_new__sqlite(Io_url url)	{return Get_or_new(Db_conn_info_.sqlite_(url));}
	public Db_conn Get_or_new(String s)				{return Get_or_new(Db_conn_info_.parse(s));}
	public Db_conn Get_or_new(Db_conn_info url) {
		Db_conn rv = (Db_conn)conn_hash.Get_by(url.Xto_api());
		if (rv == null) {
			Db_engine prime = (Db_engine)engine_hash.Get_by(url.Tid()); if (prime == null) Err_.new_wo_type("db engine prototype not found", "tid", url.Tid());
			Db_engine clone = prime.New_clone(url);
			rv = new Db_conn(clone);
			conn_hash.Add(url.Xto_api(), rv);
		}
		return rv;
	}
	public void Engines__add(Db_engine... ary) {
		for (Db_engine itm : ary)
			engine_hash.Add(itm.Tid(), itm);
	}
        public static final Db_conn_pool Instance = new Db_conn_pool(); Db_conn_pool() {this.Init();}
	private void Init() {
		this.Engines__add(Noop_engine.Instance, TdbEngine.Instance, Mysql_engine.Instance, Postgres_engine.Instance, Sqlite_engine.Instance, Db_engine__mem.Instance);
	}
}
