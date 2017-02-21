/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.dbs.qrys; import gplx.*; import gplx.dbs.*;
import gplx.core.strings.*; import gplx.core.criterias.*;
public class Db_qry__select_in_tbl implements Db_qry {
	public Db_qry__select_in_tbl(String base_table, String[] select_flds, String[] where_flds, String group_by_sql, String having_sql, String order_by_sql, String limit_sql) {
		this.base_table = base_table; this.select_flds = select_flds; this.where_flds = where_flds; this.group_by_sql = group_by_sql; this.having_sql = having_sql; this.order_by_sql = order_by_sql; this.limit_sql = limit_sql;
	}
	public int			Tid() {return Db_qry_.Tid_select_in_tbl;}
	public boolean			Exec_is_rdr() {return true;}
	public String		Base_table() {return base_table;} private final    String base_table;
	public Criteria		Where() {return where;} private Criteria where;
	public void Where_(Criteria v) {this.where = v;}
	public String[]		Select_flds() {return select_flds;} private final    String[] select_flds;
	private final    String[] where_flds;
	public void Where_sql(String_bldr sb) {
		if (where_flds == null) return;
		int where_flds_len = where_flds.length;
		if (where_flds_len == 0) return;
		for (int i = 0; i < where_flds_len; ++i) {
			if (i != 0) sb.Add("AND ");
			sb.Add(where_flds[i]).Add(" = ? ");
		}
	}
	public String Group_by_sql() {return group_by_sql;} private final    String group_by_sql;
	public String Having_sql() {return having_sql;} private final    String having_sql;
	public String Order_by_sql() {return order_by_sql;} public Db_qry__select_in_tbl Order_by_sql_(String v) {order_by_sql = v; return this;} private String order_by_sql;
	public String Limit_sql() {return limit_sql;} private final    String limit_sql;
	public String To_sql__exec(gplx.dbs.sqls.Sql_qry_wtr wtr) {
		synchronized (this) {
			String_bldr sb = String_bldr_.new_();
			sb.Add("SELECT ");
			int select_flds_len = select_flds.length;
			for (int i = 0; i < select_flds_len; ++i) {
				if (i != 0) sb.Add(",");
				sb.Add(select_flds[i]);
			}
			sb.Add(" FROM ").Add(base_table);
			if (where_flds		!= null && where_flds.length != 0) {sb.Add(" WHERE "); Where_sql(sb);}
			if (group_by_sql	!= null) sb.Add(group_by_sql);
			if (having_sql		!= null) sb.Add(having_sql);
			if (order_by_sql	!= null) {sb.Add(" ORDER BY "); sb.Add(order_by_sql);}
			if (limit_sql		!= null) sb.Add(limit_sql);
			return sb.To_str();
		}
	}
	public static Db_qry__select_in_tbl new_(String base_table, String[] where_flds, String[] select_flds, String[] order_flds) {
		String order_by_sql = null;
		if (order_flds != Order_by_null) {
			int len = order_flds.length;
			switch (len) {
				case 0: break;
				case 1: order_by_sql = order_flds[0]; break;
				default:
					Bry_bfr bfr = Bry_bfr_.New();
					for (int i = 0; i < len; ++i) {
						String order_fld = order_flds[i];
						if (i != 0) bfr.Add_byte_comma();
						bfr.Add_str_a7(order_fld);
					}
					order_by_sql = bfr.To_str_and_clear();
					break;
			}
		}
		Db_qry__select_in_tbl rv = new Db_qry__select_in_tbl(base_table, select_flds, where_flds, null, null, order_by_sql, null);
		rv.where = where_flds.length == 0 ? Db_crt_.Wildcard : Db_crt_.eq_many_(where_flds);
		return rv;
	}
	public static Db_qry__select_in_tbl as_(Object obj) {return obj instanceof Db_qry__select_in_tbl ? (Db_qry__select_in_tbl)obj : null;}
	public static final    String[] Where_flds__all = String_.Ary_empty;
	public static final    String[] Order_by_null = null;
}
