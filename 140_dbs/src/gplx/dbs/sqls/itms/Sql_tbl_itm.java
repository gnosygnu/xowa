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
public class Sql_tbl_itm {
	public Sql_tbl_itm(int join_tid, String db, String name, String alias, Sql_join_itm[] join_flds) {
		this.Join_tid = join_tid;
		this.Db = db;
		this.Name = name;
		this.Alias = alias;
		this.Join_flds = join_flds;
	}
	public final int Join_tid;
	public final String Db;
	public final String Name;
	public final String Alias;
	public final Sql_join_itm[] Join_flds;

	public static final String Alias__null = String_.Null;
	public static final String Db__null = String_.Null;
	public static final int
	  Tid__from		= 0 // "FROM"
	, Tid__inner	= 1 // "INNER JOIN"
	, Tid__left		= 2 // "LEFT JOIN"
	, Tid__right	= 3 // "RIGHT JOIN"
	, Tid__outer	= 4	// "OUTER JOIN"
	, Tid__cross	= 5 // "CROSS JOIN"
	;
}
