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
import gplx.core.strings.*;
public class Sql_tbl_src {
	public Sql_join_itmType JoinType() {return type;} public Sql_tbl_src JoinType_(Sql_join_itmType v) {this.type = v; return this;} Sql_join_itmType type = Sql_join_itmType.Inner;
	public ListAdp JoinLinks() {return joinLinks;} ListAdp joinLinks = ListAdp_.new_();
	public String TblName() {return tblName;} public Sql_tbl_src TblName_(String s) {tblName = s; return this;} private String tblName;
	public String Alias() {return alias;} public Sql_tbl_src Alias_(String s) {alias = s; return this;} private String alias;
	public void XtoSql(String_bldr sb) {
		sb.Add_many(tblName, alias == null ? "" : " " + alias);
	}
	public static Sql_tbl_src new_() {return new Sql_tbl_src();} Sql_tbl_src() {}
}
