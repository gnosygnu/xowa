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
import gplx.criterias.*;
interface Sql_cmd_wtr {
	String XtoSqlQry(Db_qry qry, boolean prepare);
	String XtoSql_insert(Db_qry_insert qry);
	String XtoSql_delete(Db_qry_delete qry);
	String XtoSql_update(Db_qry_update qry);
	String XtoSql_select(Db_qry_select qry);
	void BldWhere(String_bldr sb, Criteria crt);
	void BldValStr(String_bldr sb, Db_arg prm);
}
class Sql_cmd_wtr_ {
	public static final Sql_cmd_wtr Ansi = Sql_cmd_wtr_ansi_.default_();
	public static final Sql_cmd_wtr BackslashSensitive = Sql_cmd_wtr_ansi_.backslash_sensitive_();
}
