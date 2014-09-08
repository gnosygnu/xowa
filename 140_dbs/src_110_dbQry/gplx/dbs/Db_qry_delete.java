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
public class Db_qry_delete implements Db_qry {
	public int Tid() {return Db_qry_.Tid_basic;}
	public String KeyOfDb_qry() {return KeyConst;} public static final String KeyConst = "DELETE";
	public boolean ExecRdrAble() {return false;}
	public String XtoSql() {return Sql_cmd_wtr_.Ansi.XtoSqlQry(this, false);}		
	public int Exec_qry(Db_provider provider) {return provider.Exec_qry(this);}
	public Db_qry_delete Where_add_(String key, int val) {
		Criteria crt = Db_crt_.eq_(key, val);
		where = Sql_where.merge_or_new_(where, crt);
		return this;
	}
	@gplx.Internal protected String BaseTable() {return baseTable;} public Db_qry_delete BaseTable_(String baseTable_) {baseTable = baseTable_; return this;} private String baseTable;
	@gplx.Internal protected Sql_where Where() {return where;} public Db_qry_delete Where_(Criteria crt) {where = Sql_where.new_(crt); return this;} Sql_where where;
	public static Db_qry_delete new_() {return new Db_qry_delete();} Db_qry_delete() {}
	public static Db_qry_delete new_all_(String tbl) {
		Db_qry_delete rv = new Db_qry_delete();
		rv.baseTable = tbl;
		return rv;
	}
}
