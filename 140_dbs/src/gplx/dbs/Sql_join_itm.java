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
import gplx.core.strings.*;
public class Sql_join_itm {
	public String SrcTbl() {return srcTbl;} public Sql_join_itm SrcTbl_(String v) {srcTbl = v; return this;} private String srcTbl;
	public String SrcFld() {return srcFld;} public Sql_join_itm SrcFld_(String v) {srcFld = v; return this;} private String srcFld;
	public String TrgFld() {return trgFld;} public Sql_join_itm TrgFld_(String v) {trgFld = v; return this;} private String trgFld;
	public String TrgFldOrSrcFld() {return trgFld == null ? srcFld : trgFld;}
	public static Sql_join_itm new_(String trgFld, String srcTbl, String srcFld) {
		Sql_join_itm rv = new Sql_join_itm();
		rv.trgFld = trgFld; rv.srcTbl = srcTbl; rv.srcFld = srcFld;
		return rv;
	}	Sql_join_itm() {}
	public static Sql_join_itm same_(String tbl, String fld) {
		Sql_join_itm rv = new Sql_join_itm();
		rv.trgFld = fld; rv.srcTbl = tbl; rv.srcFld = fld;
		return rv;
	}
}
class Sql_from {
	public ListAdp Tbls() {return tbls;} ListAdp tbls = ListAdp_.new_();
	public Sql_tbl_src BaseTable() {return (Sql_tbl_src)tbls.FetchAt(0);}
	public static Sql_from new_(Sql_tbl_src baseTable) {
		Sql_from rv = new Sql_from();
		rv.tbls.Add(baseTable);
		return rv;
	}	Sql_from() {}
}
class Sql_tbl_src {
	public Sql_join_itmType JoinType() {return type;} public Sql_tbl_src JoinType_(Sql_join_itmType v) {this.type = v; return this;} Sql_join_itmType type = Sql_join_itmType.Inner;
	public ListAdp JoinLinks() {return joinLinks;} ListAdp joinLinks = ListAdp_.new_();
	public String TblName() {return tblName;} public Sql_tbl_src TblName_(String s) {tblName = s; return this;} private String tblName;
	public String Alias() {return alias;} public Sql_tbl_src Alias_(String s) {alias = s; return this;} private String alias;
	public void XtoSql(String_bldr sb) {
		sb.Add_many(tblName, alias == null ? "" : " " + alias);
	}
	public static Sql_tbl_src new_() {return new Sql_tbl_src();} Sql_tbl_src() {}
}
class Sql_join_itmType {
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
