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
package gplx.xowa.addons.apps.maints.sql_execs.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.maints.*; import gplx.xowa.addons.apps.maints.sql_execs.*;
import gplx.langs.mustaches.*;
public class Xosql_exec_doc implements Mustache_doc_itm {
	private final    String domain, db, sql;
	public Xosql_exec_doc(String domain, String db, String sql) {
		this.domain = domain;
		this.db = db;
		this.sql = sql;
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "domain"))			bfr.Add_str_u8(domain);
		else if	(String_.Eq(key, "db"))				bfr.Add_str_u8(db);
		else if	(String_.Eq(key, "sql"))			bfr.Add_str_u8(sql);
		else										return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		return Mustache_doc_itm_.Ary__empty;
	}

	public static final    Xosql_exec_doc[] Ary_empty = new Xosql_exec_doc[0];
}
