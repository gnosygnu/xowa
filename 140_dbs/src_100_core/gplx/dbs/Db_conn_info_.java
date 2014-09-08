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
public class Db_conn_info_ {
	public static final Db_conn_info Null			= Db_conn_info__null._;
	public static final Db_conn_info Test			= Db_conn_info__mysql.new_("127.0.0.1", "unit_tests", "root", "mysql7760");
	public static Db_conn_info parse_(String raw)		{return Db_conn_info_pool._.Parse(raw);}
	public static Db_conn_info sqlite_(Io_url url)		{return Db_conn_info__sqlite.load_(url);}
	public static Db_conn_info tdb_(Io_url url)			{return Db_conn_info__tdb.new_(url);}
	public static final String Key_tdb = TdbEngine.KeyDef;
}
class Db_conn_info_pool {
	private OrderedHash regy = OrderedHash_.new_();
	public Db_conn_info_pool() {
		this.Add(Db_conn_info__null._).Add(Db_conn_info__tdb._).Add(Db_conn_info__mysql._).Add(Db_conn_info__postgres._).Add(Db_conn_info__sqlite._);
	}
	public Db_conn_info_pool Add(Db_conn_info itm) {regy.AddReplace(itm.Key(), itm); return this;}
	public Db_conn_info Parse(String raw) {// assume each pair has format of: name=val;
		try {
			GfoMsg m = GfoMsg_.new_parse_("db_conn_info");
			String[] terms = String_.Split(raw, ";");
			String conn_info_key = "";
			for (String term : terms) {
				if (String_.Len(term) == 0) continue;
				String[] kv = String_.Split(term, "=");
				if (String_.Eq(kv[0], "gplx_key"))
					conn_info_key = kv[1]; // NOTE: do not add to GfoMsg; will not be part of ApiStr
				else
					m.Add(kv[0], kv[1]);
			}
			Db_conn_info prototype = (Db_conn_info)regy.Fetch(conn_info_key);
			return prototype.Make_new(raw, m);
		}
		catch(Exception exc) {throw Err_.parse_type_exc_(exc, Db_conn_info.class, raw);}
	}
	public static final Db_conn_info_pool _ = new Db_conn_info_pool();
}
