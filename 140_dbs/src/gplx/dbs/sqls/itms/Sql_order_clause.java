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
public class Sql_order_clause {
	private final List_adp list = List_adp_.new_();
	private Sql_order_fld[] ary;
	public void Flds__add(Sql_order_fld fld) {list.Add(fld);}
	public Sql_order_fld[] Flds() {
		if (ary == null) ary = (Sql_order_fld[])list.To_ary_and_clear(Sql_order_fld.class);
		return ary;
	}
}
