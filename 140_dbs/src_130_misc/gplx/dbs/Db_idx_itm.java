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
public class Db_idx_itm {
	public String Xto_sql() {return sql;} private String sql;
	public static Db_idx_itm sql_(String sql) {
		Db_idx_itm rv = new Db_idx_itm();
		rv.sql = sql;
		return rv;
	}
	public static Db_idx_itm[] ary_sql_(String... ary) {
		int len = ary.length;
		Db_idx_itm[] rv = new Db_idx_itm[len];
		for (int i = 0; i < len; i++) {
			rv[i] = sql_(ary[i]);
		}
		return rv;
	}
}
