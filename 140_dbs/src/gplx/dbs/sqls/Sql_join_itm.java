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
