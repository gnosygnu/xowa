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
public class Db_connect_ {
	public static final Db_connect Null = Db_connect_null._;
	public static final Db_connect Test = Db_connect_mysql.new_("127.0.0.1", "unit_tests", "root", "mysql7760");
	public static Db_connect parse_(String raw)			{return Db_connect_pool._.Parse(raw);}
	public static Db_connect tdb_(Io_url url)			{return Db_connect_tdb.new_(url);}
	public static Db_connect sqlite_(Io_url url)		{return Db_connect_sqlite.load_(url);}
	public static final String TdbKey = TdbEngine.KeyDef;
}
class Db_connect_pool {
	public Db_connect_pool() {
		this.Add(Db_connect_null._).Add(Db_connect_tdb._).Add(Db_connect_mysql._).Add(Db_connect_postgres._).Add(Db_connect_sqlite._);
	}
	public Db_connect_pool Add(Db_connect prototype) {regy.AddReplace(prototype.Key_of_db_connect(), prototype); return this;}
	public Db_connect Parse(String raw) {// assume each pair has format of: name=val;
		try {
			GfoMsg m = GfoMsg_.new_parse_("dbConnectionString");
			String[] terms = String_.Split(raw, ";");
			String gplxKey = "";
			for (String term : terms) {
				if (String_.Len(term) == 0) continue;
				String[] kv = String_.Split(term, "=");
				if (String_.Eq(kv[0], "gplx_key"))
					gplxKey = kv[1]; // NOTE: do not add to GfoMsg; will not be part of ApiStr
				else
					m.Add(kv[0], kv[1]);
			}
			Db_connect prototype = (Db_connect)regy.Fetch(gplxKey);
			return prototype.Clone_of_db_connect(raw, m);
		}
		catch(Exception exc) {throw Err_.parse_type_exc_(exc, Db_connect.class, raw);}
	}
	OrderedHash regy = OrderedHash_.new_();
	public static final Db_connect_pool _ = new Db_connect_pool();
}
