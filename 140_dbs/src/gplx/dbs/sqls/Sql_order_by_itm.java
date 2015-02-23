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
package gplx.dbs.sqls; import gplx.*; import gplx.dbs.*;
public class Sql_order_by_itm {
	public String Name() {return name;} private String name;
	public boolean Ascending() {return ascending;} private boolean ascending;
	public String XtoSql() {
		String ascString = ascending ? "" : " DESC";
		return name + ascString;
	}
	public static Sql_order_by_itm new_(String name, boolean ascending) {
		Sql_order_by_itm rv = new Sql_order_by_itm();
		rv.name = name; rv.ascending = ascending;
		return rv;
	}	Sql_order_by_itm() {}
}
