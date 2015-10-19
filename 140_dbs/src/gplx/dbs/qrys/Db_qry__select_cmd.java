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
package gplx.dbs.qrys; import gplx.*; import gplx.dbs.*;
import gplx.core.criterias.*; import gplx.dbs.sqls.*;
public class Db_qry__select_cmd implements Db_qry {
	public int			Tid() {return Db_qry_.Tid_select;}
	public boolean			Exec_is_rdr() {return true;}
	public String		Base_table() {return from.BaseTable().TblName();}
	public String		Xto_sql() {return Sql_qry_wtr_.Instance.Xto_str(this, false);}		
	public String		Xto_sql_prepare() {return Sql_qry_wtr_.Instance.Xto_str(this, true);}		
	public DataRdr		Exec_qry_as_rdr(Db_conn conn) {return conn.Exec_qry_as_rdr(this);}
	public GfoNde ExecRdr_nde(Db_conn conn) {
		DataRdr rdr = DataRdr_.Null;
		try {return GfoNde_.rdr_(Exec_qry_as_rdr(conn));} finally {rdr.Rls();}
	}
	public Object ExecRdr_val(Db_conn conn) {
		DataRdr rdr = Exec_qry_as_rdr(conn);
		try {
			Object rv = null;
			if (rdr.MoveNextPeer()) {
				rv = rdr.Read(cols.Flds().Get_at(0).Fld());	// NOTE: need to access from flds for tdb
			}
			return rv;
		}	finally {rdr.Rls();}
	}
	public static Object Rdr_to_val(DataRdr rdr) {
		try {
			Object rv = null;
			if (rdr.MoveNextPeer()) {
				rv = rdr.ReadAt(0);
			}
			return rv;
		}	finally {rdr.Rls();}
	}

	public Sql_from From() {return from;} Sql_from from;
	public Db_qry__select_cmd From_(String tblName) {return From_(tblName, null);}
	public Db_qry__select_cmd From_(String tblName, String alias) {
		if (from != null) throw Err_.new_wo_type("super table already defined", "from", from.Tbls().Count());
		from = Sql_from.new_(Sql_tbl_src.new_().JoinType_(Sql_join_itmType.From).TblName_(tblName).Alias_(alias));
		return this;
	}
	public Db_qry__select_cmd Join_(String name, String alias, Sql_join_itm... ary) {
		if (from == null) throw Err_.new_wo_type("super table is not defined");
		Sql_tbl_src tbl = Sql_tbl_src.new_().JoinType_(Sql_join_itmType.Inner).TblName_(name).Alias_(alias);
		for (Sql_join_itm itm : ary)
			tbl.JoinLinks().Add(itm);
		from.Tbls().Add(tbl);
		return this;
	}

	public Sql_select Cols() {return cols;} Sql_select cols = Sql_select.All;
	public String[] Cols_ary() {return cols.Flds().To_str_ary();}
	public Db_qry__select_cmd Cols_all_() {return this;}
	public Db_qry__select_cmd Cols_alias_(String expr, String alias) {
		if (cols == Sql_select.All) cols = Sql_select.new_();
		cols.Add(expr, alias);
		return this;
	}
	public Db_qry__select_cmd Cols_(String... ary) {
		if (cols == Sql_select.All) cols = Sql_select.new_();
		for (String itm : ary)
			cols.Add(itm);
		return this;
	}
	public Db_qry__select_cmd Cols_groupBy_max(String fld) {return Cols_groupBy_max(fld, fld);}
	public Db_qry__select_cmd Cols_groupBy_max(String fld, String alias) {
		if (cols == Sql_select.All) cols = Sql_select.new_();
		cols.Add(Sql_select_fld_.new_max(Sql_select_fld_base.Tbl_null, fld, alias));
		return this;
	}
	public Db_qry__select_cmd Cols_groupBy_min(String fld, String alias) {
		if (cols == Sql_select.All) cols = Sql_select.new_();
		cols.Add(Sql_select_fld_.new_min(Sql_select_fld_base.Tbl_null, fld, alias));
		return this;
	}
	public Db_qry__select_cmd Cols_groupBy_count(String fld, String alias) {
		if (cols == Sql_select.All) cols = Sql_select.new_();
		cols.Add(Sql_select_fld_.new_count(Sql_select_fld_base.Tbl_null, fld, alias));
		return this;
	}
	public Db_qry__select_cmd Cols_groupBy_sum(String fld) {return Cols_groupBy_sum(fld, fld);}
	public Db_qry__select_cmd Cols_groupBy_sum(String fld, String alias) {
		if (cols == Sql_select.All) cols = Sql_select.new_();
		cols.Add(Sql_select_fld_.new_sum(Sql_select_fld_base.Tbl_null, fld, alias));
		return this;
	}

	public Criteria Where() {return where;} public Db_qry__select_cmd Where_(Criteria crt) {where = crt; return this;} Criteria where;
	public Sql_order_by OrderBy() {return orderBy;} Sql_order_by orderBy = null;
	public Db_qry__select_cmd OrderBy_(String fieldName, boolean ascending) {
		Sql_order_by_itm item = Sql_order_by_itm.new_(fieldName, ascending);
		orderBy = Sql_order_by.new_(item);
		return this;
	}
	public Db_qry__select_cmd OrderBy_asc_(String fieldName) {return OrderBy_(fieldName, true);}
	public Db_qry__select_cmd OrderBy_many_(String... fldNames) {
		Sql_order_by_itm[] ary = new Sql_order_by_itm[fldNames.length];
		for (int i = 0; i < fldNames.length; i++)
			ary[i] = Sql_order_by_itm.new_(fldNames[i], true);
		orderBy = Sql_order_by.new_(ary);
		return this;
	}
	public Sql_group_by GroupBy() {return groupBy;} Sql_group_by groupBy = null;
	public Db_qry__select_cmd GroupBy_(String... flds) {
		if (groupBy != null) throw Err_.new_wo_type("group by already defined", "group", groupBy);
		groupBy = Sql_group_by.new_(flds);
		return this;
	}
	public String Indexed_by() {return indexed_by;} public Db_qry__select_cmd Indexed_by_(String v) {indexed_by = v; return this;} private String indexed_by;
	public Db_qry__select_cmd Distinct_() {cols.Distinct_set(true); return this;}
	public int Limit() {return limit;} int limit = -1; public static final int Limit_disabled = -1;
	public Db_qry__select_cmd Limit_(int v) {this.limit = v; return this;}

	public static Db_qry__select_cmd new_() {return new Db_qry__select_cmd();} Db_qry__select_cmd() {}
}
