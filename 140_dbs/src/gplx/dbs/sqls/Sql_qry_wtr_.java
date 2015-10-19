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
public class Sql_qry_wtr_ {
	public static Sql_qry_wtr	new_ansi()				{return new Sql_qry_wtr_ansi();}
	public static Sql_qry_wtr	new_escape_backslash()	{return new Sql_qry_wtr_ansi_escape_backslash();}
	public static final Sql_qry_wtr Instance = new Sql_qry_wtr_ansi();
	public static String Gen_placeholder_parameters(Db_qry qry) {return Sql_qry_wtr_.Instance.Xto_str(qry, true);}	// replace arguments with ?; EX: UPDATE a SET b = ? WHERE c = ?;
}
