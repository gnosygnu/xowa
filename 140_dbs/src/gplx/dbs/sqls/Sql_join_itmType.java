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
public class Sql_join_itmType {
	public int Val() {return val;} int val;
	public String Name() {return name;} private String name;
	Sql_join_itmType(int v, String name) {this.val = v; this.name = name;}
	public static final Sql_join_itmType
	  From	= new Sql_join_itmType(0, "FROM")
	, Inner	= new Sql_join_itmType(1, "INNER JOIN")
	, Left	= new Sql_join_itmType(2, "LEFT JOIN")
	, Right	= new Sql_join_itmType(3, "RIGHT JOIN")
	, Outer	= new Sql_join_itmType(4, "OUTER JOIN")
	, Cross	= new Sql_join_itmType(5, "CROSS JOIN")
	;
}
