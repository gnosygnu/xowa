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
public class Db_qry__select_in_tbl implements Db_qry {
	public Db_qry__select_in_tbl(String tbl_name, String[] select_flds, String[] where_flds, String group_by_sql, String having_sql, String order_by_sql, String limit_sql) {
		this.tbl_name = tbl_name; this.select_flds = select_flds; this.where_flds = where_flds; this.group_by_sql = group_by_sql; this.having_sql = having_sql; this.order_by_sql = order_by_sql; this.limit_sql = limit_sql;
	}
	public int Tid() {return Db_qry_.Tid_select_in_tbl;}
	public String Tbl_name() {return tbl_name;} private final String tbl_name;
	public String[] Select_flds() {return select_flds;} private final String[] select_flds;
	public String[] Where_flds() {return where_flds;} private final String[] where_flds;
	public void Where_sql(String_bldr sb) {
		if (where_flds == null) return;
		int where_flds_len = where_flds.length;
		if (where_flds_len == 0) return;
		for (int i = 0; i < where_flds_len; ++i) {
			if (i != 0) sb.Add("AND ");
			sb.Add(where_flds[i]).Add(" = ? ");
		}
	}
	public String Group_by_sql() {return group_by_sql;} private final String group_by_sql;
	public String Having_sql() {return having_sql;} private final String having_sql;
	public String Order_by_sql() {return order_by_sql;} private final String order_by_sql;
	public String Limit_sql() {return limit_sql;} private final String limit_sql;
	public static Db_qry__select_in_tbl new_(String tbl_name, String[] where_flds, String[] select_flds) {return new Db_qry__select_in_tbl(tbl_name, select_flds, where_flds, null, null, null, null);}
	public String KeyOfDb_qry() {return "select_in_tbl";}
	public boolean ExecRdrAble() {return true;}
	public String XtoSql() {return Xto_sql();}
	public String Xto_sql() {
		String_bldr sb = String_bldr_.new_();
		sb.Add("SELECT ");
		int select_flds_len = select_flds.length;
		for (int i = 0; i < select_flds_len; ++i) {
			if (i != 0) sb.Add(",");
			sb.Add(select_flds[i]);
		}
		sb.Add(" FROM ").Add(tbl_name).Add(" ");
		if (where_flds		!= null) {sb.Add("WHERE "); Where_sql(sb);}
		if (group_by_sql	!= null) sb.Add(group_by_sql);
		if (having_sql		!= null) sb.Add(having_sql);
		if (order_by_sql	!= null) sb.Add(order_by_sql);
		if (limit_sql		!= null) sb.Add(limit_sql);
		return sb.XtoStr();
	}
}
