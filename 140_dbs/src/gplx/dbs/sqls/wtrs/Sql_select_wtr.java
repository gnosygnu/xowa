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
package gplx.dbs.sqls.wtrs; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
import gplx.dbs.qrys.*; import gplx.dbs.sqls.itms.*;
public class Sql_select_wtr {
	private final Sql_core_wtr qry_wtr;
	public Sql_select_wtr(Sql_core_wtr qry_wtr) {this.qry_wtr = qry_wtr;}
	public void Bld_qry_select(Bry_bfr bfr, Sql_wtr_ctx ctx, Db_qry__select_cmd qry) {
		bfr.Add_str_a7("SELECT ");
		if (qry.Cols().Distinct) bfr.Add_str_a7("DISTINCT ");
		Sql_select_fld_list flds = qry.Cols().Flds;
		int flds_len = flds.Len();
		if (flds_len == 0) bfr.Add_str_a7("*");
		for (int i = 0; i < flds_len; i++) {
			Sql_select_fld fld = (Sql_select_fld)flds.Get_at(i);
			if (i > 0) bfr.Add_str_a7(", ");
			qry_wtr.Bld_col_name(bfr, fld.To_fld_sql());
		}
		qry_wtr.From_wtr().Bld_clause_from(bfr, qry.From());
		Bld_indexed_by(bfr, ctx, qry, qry.Indexed_by());
		qry_wtr.Where_wtr().Bld_where(bfr, ctx, qry.Where_itm());
		Bld_select_group_by(bfr, ctx, qry, qry.GroupBy());
		Bld_select_order_by(bfr, ctx, qry, qry.Order());
		Bld_select_limit(bfr, ctx, qry, qry.Limit());
		if (qry.Offset() != Db_qry__select_cmd.Offset__disabled)
			Bld_offset(bfr, ctx, qry, qry.Offset());
	}
	private void Bld_select_group_by(Bry_bfr bfr, Sql_wtr_ctx ctx, Db_qry__select_cmd qry, Sql_group_clause groupBy) {
		if (groupBy == null) return;
		bfr.Add_str_a7(" GROUP BY ");
		for (int i = 0; i < groupBy.Flds().Count(); i++) {
			String item = (String)groupBy.Flds().Get_at(i);
			if (i > 0) bfr.Add_str_a7(", ");
			bfr.Add_str_a7(item);
		}
	}
	private void Bld_select_order_by(Bry_bfr bfr, Sql_wtr_ctx ctx, Db_qry__select_cmd qry, Sql_order_clause orderBy) {
		if (orderBy == null) return;
		bfr.Add_str_a7(" ORDER BY ");
		int len = orderBy.Flds().length;
		for (int i = 0; i < len; ++i) {
			Sql_order_fld item = orderBy.Flds()[i];
			if (i > 0) bfr.Add_str_a7(", ");
			bfr.Add_str_a7(item.To_sql());
		}
	}
	protected void Bld_select_limit(Bry_bfr bfr, Sql_wtr_ctx ctx, Db_qry__select_cmd qry, int limit) {
		if (limit == Db_qry__select_cmd.Limit__disabled) return;
		bfr.Add_str_a7(" LIMIT ").Add_int_variable(limit);
	}
	@gplx.Virtual protected void Bld_offset(Bry_bfr bfr, Sql_wtr_ctx ctx, Db_qry__select_cmd qry, int offset) {
		bfr.Add_str_a7(" OFFSET ").Add_int_variable(offset);
	}
	private void Bld_indexed_by(Bry_bfr bfr, Sql_wtr_ctx ctx, Db_qry__select_cmd qry, String idx_name) {
		if (idx_name == null) return;
		// ( "SELECT {0}, {1}, {2}, {3} FROM {4} INDEXED BY {4}__title WHERE {1} = {5} AND {2} BETWEEN '{6}' AND '{7}' ORDER BY {3} DESC LIMIT {8};"
		bfr.Add_str_a7(" INDEXED BY ").Add_str_a7(idx_name);
	}
}
class Sql_select_wtr_sqlite extends Sql_select_wtr {		public Sql_select_wtr_sqlite(Sql_core_wtr qry_wtr) {super(qry_wtr);}
	@Override protected void Bld_offset(Bry_bfr bfr, Sql_wtr_ctx ctx, Db_qry__select_cmd qry, int offset) {
		if (qry.Limit() == Db_qry__select_cmd.Limit__disabled) Bld_select_limit(bfr, ctx, qry, -1);	// SQLite requires a LIMIT if OFFSET is specified
		super.Bld_offset(bfr, ctx, qry, offset);
	}
}
