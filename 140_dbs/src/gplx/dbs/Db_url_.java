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
import gplx.dbs.engines.nulls.*; import gplx.dbs.engines.mems.*; import gplx.dbs.engines.sqlite.*; import gplx.dbs.engines.tdbs.*;
import gplx.dbs.engines.mysql.*; import gplx.dbs.engines.postgres.*;
public class Db_url_ {
	public static final Db_url Null			= Null_url._;
	public static final Db_url Test			= Mysql_url.new_("127.0.0.1", "unit_tests", "root", "mysql7760");
	public static Db_url parse_(String raw)			{return Db_url_pool._.Parse(raw);}
	public static Db_url sqlite_(Io_url url)		{return Sqlite_url.load_(url);}
	public static Db_url tdb_(Io_url url)			{return Tdb_url.new_(url);}
	public static Db_url mem_(String db)			{return Db_url__mem.new_(db);}
	public static final String Key_tdb = Tdb_url.Tid_const;
}
class Db_url_pool {
	private OrderedHash regy = OrderedHash_.new_();
	public Db_url_pool() {
		this.Add(Null_url._).Add(Tdb_url._).Add(Mysql_url._).Add(Postgres_url._).Add(Sqlite_url._);
		this.Add(Db_url__mem.I);
	}
	public Db_url_pool Add(Db_url itm) {regy.AddReplace(itm.Tid(), itm); return this;}
	public Db_url Parse(String raw) {// assume each pair has format of: name=val;
		try {
			GfoMsg m = GfoMsg_.new_parse_("db_url");
			String[] terms = String_.Split(raw, ";");
			String url_tid = "";
			for (String term : terms) {
				if (String_.Len(term) == 0) continue;
				String[] kv = String_.Split(term, "=");
				if (String_.Eq(kv[0], "gplx_key"))
					url_tid = kv[1]; // NOTE: do not add to GfoMsg; will not be part of ApiStr
				else
					m.Add(kv[0], kv[1]);
			}
			Db_url prototype = (Db_url)regy.Fetch(url_tid);
			return prototype.New_self(raw, m);
		}
		catch(Exception exc) {throw Err_.parse_type_exc_(exc, Db_url.class, raw);}
	}
	public static final Db_url_pool _ = new Db_url_pool();
}
