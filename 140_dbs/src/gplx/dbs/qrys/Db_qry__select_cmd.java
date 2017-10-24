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
import gplx.core.criterias.*;
import gplx.dbs.sqls.*; import gplx.dbs.sqls.itms.*; 
public class Db_qry__select_cmd implements Db_qry {
	public int				Tid()				{return Db_qry_.Tid_select;}
	public boolean				Exec_is_rdr()		{return Bool_.Y;}
	public String			Base_table()		{return from.Base_tbl.Name;}
	public Sql_from_clause		From()				{return from;} private Sql_from_clause from;
	public Db_qry__select_cmd From_(String tbl) {return From_(tbl, Sql_tbl_itm.Alias__null);}
	public Db_qry__select_cmd From_(String name, String alias) {return From_(Sql_tbl_itm.Db__null, name, alias);}
	public Db_qry__select_cmd From_(String db, String tbl, String alias) {
		if (from != null) throw Err_.new_("sql_qry", "super table already defined", "from", from.Base_tbl.Name);
		from = new Sql_from_clause(new Sql_tbl_itm(Sql_tbl_itm.Tid__from, db, tbl, alias, Sql_join_fld.Ary__empty));
		return this;
	}
	public Db_qry__select_cmd Join_(String name, String alias, Sql_join_fld... join_flds)				{return Join_(Sql_tbl_itm.Tid__inner, Sql_tbl_itm.Db__null	, name, alias, join_flds);}
	public Db_qry__select_cmd Join_(String db, String name, String alias, Sql_join_fld... join_flds)	{return Join_(Sql_tbl_itm.Tid__inner, db					, name, alias, join_flds);}
	public Db_qry__select_cmd Join_(int join_tid, String db, String name, String alias, Sql_join_fld... join_flds) {
		if (from == null) throw Err_.new_("sql_qry", "super table is not defined");
		from.Tbls.Add(new Sql_tbl_itm(join_tid, db, name, alias, join_flds));
		return this;
	}
	public Sql_select_clause Cols() {return cols;} private Sql_select_clause cols = new Sql_select_clause();
	public Db_qry__select_cmd Distinct_() {cols.Distinct = true; return this;}
	public Db_qry__select_cmd Cols_all_() {cols.Flds.Clear().Add(Sql_select_fld.Wildcard); return this;}
	public Db_qry__select_cmd Cols_(String... ary) {return Cols_w_tbl_(Sql_select_fld.Tbl__null, ary);}
	public Db_qry__select_cmd Cols_w_tbl_(String tbl, String... ary) {
		for (String itm : ary)
			cols.Flds.Add(Sql_select_fld.New_fld(tbl, itm, itm));
		return this;
	}
	public Db_qry__select_cmd Cols_w_alias_(String expr, String alias) {
		cols.Flds.Add(Sql_select_fld.New_fld(Sql_select_fld.Tbl__null, expr, alias));
		return this;
	}
	public Sql_where_clause Where_itm() {return where_itm;} private Sql_where_clause where_itm = Sql_where_clause.All;
	public Db_qry__select_cmd Where_(Criteria root) {
		if (where_itm == Sql_where_clause.All) where_itm = new Sql_where_clause();
		where_itm.Root = root;
		return this;
	}
	public Db_qry__select_cmd Where_and(Criteria crt) {
		if (where_itm == Sql_where_clause.All) throw Err_.new_("sql_qry", "where is not defined");
		where_itm.Root = Criteria_.And(where_itm.Root, crt);
		return this;
	}
	public Sql_order_clause Order() {return order;} private Sql_order_clause order = null;
	public Db_qry__select_cmd Order_asc_(String fld) {return Order_(fld, Bool_.Y);}
	public Db_qry__select_cmd Order_(String fld)				{return Order_(Sql_order_fld.Tbl__null, fld, Bool_.Y);}
	public Db_qry__select_cmd Order_(String fld, boolean asc)		{return Order_(Sql_order_fld.Tbl__null, fld, asc);}
	public Db_qry__select_cmd Order_(String tbl, String fld)	{return Order_(tbl, fld, Bool_.Y);}
	public Db_qry__select_cmd Order_(String tbl, String fld, boolean asc) {
		if (order == null) order = new Sql_order_clause();
		Sql_order_fld item = new Sql_order_fld(tbl, fld, asc ? Sql_order_fld.Sort__nil : Sql_order_fld.Sort__dsc);
		order.Flds__add(item);
		return this;
	}
	public Db_qry__select_cmd Order_asc_many_(String... flds) {
		if (order == null) order = new Sql_order_clause();
		int flds_len = flds.length;
		for (int i = 0; i < flds_len; ++i)
			order.Flds__add(new Sql_order_fld(Sql_order_fld.Tbl__null, flds[i], Sql_order_fld.Sort__nil));
		return this;
	}
	public int Limit() {return limit;} public Db_qry__select_cmd Limit_(int v) {this.limit = v; return this;} private int limit = Limit__disabled; public static final int Limit__disabled = Int_.Min_value;
	public int Offset() {return offset;} public Db_qry__select_cmd Offset_(int v) {this.offset = v; return this;} private int offset = Offset__disabled; public static final int Offset__disabled = Int_.Min_value;
	public String Indexed_by() {return indexed_by;} public Db_qry__select_cmd Indexed_by_(String v) {indexed_by = v; return this;} private String indexed_by;

	public Sql_group_clause GroupBy() {return groupBy;} private Sql_group_clause groupBy = null;
	public Db_qry__select_cmd GroupBy_(String... flds) {
		if (groupBy != null) throw Err_.new_("sql_qry", "group by already defined", "group", groupBy);
		groupBy = Sql_group_clause.new_(flds);
		return this;
	}
	public Db_qry__select_cmd Cols_groupBy_max(String fld) {return Cols_groupBy_max(fld, fld);}
	public Db_qry__select_cmd Cols_groupBy_max(String fld, String alias) {
		cols.Flds.Add(Sql_select_fld.New_max(Sql_select_fld.Tbl__null, fld, alias));
		return this;
	}
	public Db_qry__select_cmd Cols_groupBy_min(String fld, String alias) {
		cols.Flds.Add(Sql_select_fld.New_min(Sql_select_fld.Tbl__null, fld, alias));
		return this;
	}
	public Db_qry__select_cmd Cols_groupBy_count(String fld, String alias) {
		cols.Flds.Add(Sql_select_fld.New_count(Sql_select_fld.Tbl__null, fld, alias));
		return this;
	}
	public Db_qry__select_cmd Cols_groupBy_sum(String fld) {return Cols_groupBy_sum(fld, fld);}
	public Db_qry__select_cmd Cols_groupBy_sum(String fld, String alias) {
		cols.Flds.Add(Sql_select_fld.New_sum(Sql_select_fld.Tbl__null, fld, alias));
		return this;
	}
	
	public String		To_sql__exec(gplx.dbs.sqls.Sql_qry_wtr wtr)		{return wtr.To_sql_str(this, Bool_.N);}
	public String		To_sql__prep(gplx.dbs.sqls.Sql_qry_wtr wtr)		{return wtr.To_sql_str(this, Bool_.Y);}		
}
