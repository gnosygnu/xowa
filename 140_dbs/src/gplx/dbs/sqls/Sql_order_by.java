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
public class Sql_order_by {
	public ListAdp Flds() {return flds;} ListAdp flds = ListAdp_.new_();

	public static Sql_order_by new_(Sql_order_by_itm... ary) {
		Sql_order_by rv = new Sql_order_by();
		for (Sql_order_by_itm itm : ary)
			rv.flds.Add(itm);
		return rv;
	}	Sql_order_by() {}
}
