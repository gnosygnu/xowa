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
package gplx.dbs.sqls.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
public class Sql_order_fld {
	public Sql_order_fld(String tbl, String name, byte sort) {this.Tbl = tbl; this.Name = name; this.Sort = sort;}
	public final String Tbl;
	public final String Name;
	public final byte Sort;
	public String To_sql() {
		String rv = this.Name;
		if (Tbl != null) rv = Tbl + "." + rv;
		switch (Sort) {
			case Sort__asc: rv += " ASC"; break;
			case Sort__dsc: rv += " DESC"; break;
			case Sort__nil: break;
		}
		return rv;
	}

	public static final String Tbl__null = String_.Null;
	public static final byte Sort__asc = Bool_.Y_byte, Sort__dsc = Bool_.N_byte, Sort__nil = Bool_.__byte;
}
