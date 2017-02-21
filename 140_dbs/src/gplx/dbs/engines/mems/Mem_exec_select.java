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
package gplx.dbs.engines.mems; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.core.criterias.*;
import gplx.dbs.qrys.*; import gplx.dbs.sqls.itms.*;
public class Mem_exec_select {
	private final    Mem_engine engine;
	private final    List_adp tmp_where_rows = List_adp_.New();
	public Mem_exec_select(Mem_engine engine) {this.engine = engine;}
	public Db_rdr Select(Mem_stmt stmt) {
		Db_qry stmt_qry = stmt.Qry();
		Mem_tbl tbl = engine.Tbls__get(stmt_qry.Base_table());
		String[] select = null; Criteria where = null;
		Db_qry__select_in_tbl qry = Db_qry__select_in_tbl.as_(stmt_qry);
		Db_qry__select_cmd qry2 = null;
		if (qry == null) {
			qry2 = (Db_qry__select_cmd)stmt_qry;
			select = Mem_exec_.Flds__to_str_ary(qry2.Cols().Flds);
			where = qry2.Where_itm().Root;
		}
		else {
			select = qry.Select_flds();
			where = qry.Where();
		}
		Mem_stmt_args_.Fill(stmt.Stmt_args(), where);
		Mem_row[] tbl_rows = (Mem_row[])tbl.rows.To_ary(Mem_row.class);
		if (qry2 != null) {
			tbl_rows = Mem_exec_.Rows__alias(tbl_rows, qry2.From().Base_tbl.Alias);
			List_adp join_tbls = qry2.From().Tbls;
			int join_tbls_len = join_tbls.Len();
			for (int i = 1; i < join_tbls_len; ++i) {
				Sql_tbl_itm join_tbl = (Sql_tbl_itm)join_tbls.Get_at(i);
				Mem_row[] join_rows = (Mem_row[])engine.Tbls__get(join_tbl.Name).rows.To_ary(Mem_row.class);
				join_rows = Mem_exec_.Rows__alias(join_rows, join_tbl.Alias);
				tbl_rows = Mem_exec_.Rows__join(join_tbl.Join_tid, tbl_rows, join_rows, join_tbl.Alias, join_tbl.Join_flds);
			}
		}			
		Mem_exec_.Where__filter(tmp_where_rows, tbl_rows, stmt, where);
		Mem_row[] rslt_rows = (Mem_row[])tmp_where_rows.To_ary_and_clear(Mem_row.class);
		if (qry2 != null) {
			if (qry2.Order() != null && qry2.Order().Flds().length > 0)
				Array_.Sort(rslt_rows, new Mem_sorter(qry2.Order().Flds()));
			int offset = qry2.Offset();
			if (offset != Db_qry__select_cmd.Offset__disabled) {
				Mem_row[] trg_rows = new Mem_row[rslt_rows.length - offset];
				Array_.Copy_to(rslt_rows, offset, trg_rows, 0, trg_rows.length);
				rslt_rows = trg_rows;
			}
			int limit = qry2.Limit();
			if (limit != Db_qry__select_cmd.Limit__disabled) {
				Mem_row[] trg_rows = new Mem_row[limit];
				Array_.Copy_to(rslt_rows, 0, trg_rows, 0, limit);
				rslt_rows = trg_rows;
			}
			rslt_rows = Mem_exec_.Rows__select_flds(rslt_rows, qry2.Cols());
		}
		return new Mem_rdr(select, rslt_rows);
	}
}
class Mem_sorter implements gplx.core.lists.ComparerAble {
	private final    Sql_order_fld[] flds;
	public Mem_sorter(Sql_order_fld[] flds) {
		this.flds = flds;
	}
	public int compare(Object lhsObj, Object rhsObj) {
		Mem_row lhs = (Mem_row)lhsObj;
		Mem_row rhs = (Mem_row)rhsObj;
		int flds_len = flds.length;
		for (int i = 0; i < flds_len; ++i) {
			Sql_order_fld fld = flds[i];
			Object lhs_val = lhs.Get_by(fld.Name);
			Object rhs_val = rhs.Get_by(fld.Name);
			int comp = CompareAble_.Compare_obj(lhs_val, rhs_val);
			if (comp != CompareAble_.Same) return comp * (fld.Sort == Sql_order_fld.Sort__dsc ? -1 : 1);
		}
		return CompareAble_.Same;
	}
}
class Mem_exec_ {
	public static Mem_row[] Rows__join(int join_tid, Mem_row[] lhs_rows, Mem_row[] rhs_rows, String tbl_alias, Sql_join_fld[] join_flds) {
		int join_flds_len = join_flds.length;
		Bry_bfr bfr = Bry_bfr_.New();
		Hash_adp_bry rhs_hash = Hash_adp_bry.cs();
		int rhs_rows_len = rhs_rows.length;
		for (int i = 0; i < rhs_rows_len; ++i) {
			Mem_row rhs_row = rhs_rows[i];
			byte[] rhs_key = Rows__bld_key(bfr, Bool_.N, tbl_alias, rhs_row, join_flds, join_flds_len);
			List_adp rhs_list = (List_adp)rhs_hash.Get_by_bry(rhs_key);
			if (rhs_list == null) {
				rhs_list = List_adp_.New();
				rhs_hash.Add(rhs_key, rhs_list);
			}
			rhs_list.Add(rhs_row);
		}
		List_adp rv = List_adp_.New();
		int lhs_len = lhs_rows.length;
		for (int i = 0; i < lhs_len; ++i) {
			Mem_row lhs_row = lhs_rows[i];
			byte[] lhs_key = Rows__bld_key(bfr, Bool_.Y, tbl_alias, lhs_row, join_flds, join_flds_len);
			List_adp rhs_list = (List_adp)rhs_hash.Get_by_bry(lhs_key);
			if (rhs_list == null) {
				switch (join_tid) {
					case Sql_tbl_itm.Tid__inner: continue;
					case Sql_tbl_itm.Tid__left: break;
					default: throw Err_.new_unhandled_default(join_tid);
				}
			}
			int rhs_list_len = rhs_list == null ? 1 : rhs_list.Len();
			for (int j = 0; j < rhs_list_len; ++j) {
				Mem_row rhs_row = rhs_list == null ? Mem_row.Null_row : (Mem_row)rhs_list.Get_at(j); 
				Mem_row merged = Rows__merge(lhs_row, rhs_row);
				rv.Add(merged);
			}
		}
		return (Mem_row[])rv.To_ary_and_clear(Mem_row.class);
	}
	public static Mem_row[] Rows__alias(Mem_row[] src_rows, String tbl_alias) {
		int src_rows_len = src_rows.length;
		Mem_row[] rv = new Mem_row[src_rows_len];
		for (int i = 0; i < src_rows_len; ++i) {
			Mem_row src_row = src_rows[i];
			Mem_row trg_row = new Mem_row(); rv[i] = trg_row;
			int src_flds_len = src_row.Len();
			for (int j = 0; j < src_flds_len; ++j) {
				String fld = src_row.Fld_at(j);
				Object val = src_row.Get_at(j);
				trg_row.Add(Sql_select_fld.Bld_tbl_w_fld(tbl_alias, fld), val);
			}
		}
		return rv;
	}
	private static byte[] Rows__bld_key(Bry_bfr bfr, boolean join_is_src, String trg_tbl, Mem_row row, Sql_join_fld[] join_flds, int join_flds_len) {
		for (int i = 0; i < join_flds_len; ++i) {
			if (i != 0) bfr.Add_byte_pipe();
			Sql_join_fld join_fld = join_flds[i];
			Object val = row.Get_by(join_fld.To_fld_sql(join_is_src, trg_tbl));
			bfr.Add_obj(val);
		}
		return bfr.To_bry_and_clear();
	}
	private static Mem_row Rows__merge(Mem_row lhs, Mem_row rhs) {
		Mem_row rv = new Mem_row();
		Rows__copy_to(lhs, rv);
		Rows__copy_to(rhs, rv);
		return rv;
	}
	private static void Rows__copy_to(Mem_row src, Mem_row trg) {
		int src_len = src.Len();
		for (int i = 0; i < src_len; ++i) {
			String fld = src.Fld_at(i);
			Object val = src.Get_by_or_dbnull(fld);
			trg.Add(fld, val);
		}
	}
	public static Mem_row[] Rows__select_flds(Mem_row[] src_rows, Sql_select_clause cols) {
		Sql_select_fld_list select_flds = cols.Flds; int select_flds_len = select_flds.Len();
		int src_rows_len = src_rows.length;
		Mem_row[] rv = new Mem_row[src_rows_len];
		for (int i = 0; i < src_rows_len; ++i) {							// loop over each row
			Mem_row src_row = src_rows[i];
			Mem_row trg_row = new Mem_row(); rv[i] = trg_row;
			for (int j = 0; j < select_flds_len; ++j) {						// loop over each fld
				Sql_select_fld fld = select_flds.Get_at(j);
				if (String_.Eq(fld.Alias, Sql_select_fld.Fld__wildcard)) {	// wildcard; add all cols
					for (int k = 0; k < src_row.Len(); ++k) {
						String key = src_row.Fld_at(k);
						Object val = src_row.Get_by_or_dbnull(key);
						trg_row.Add(key, val);
					}
				}
				else {														// regular field; add it only
					String key = fld.To_fld_key();
					Object val = src_row.Get_by_or_dbnull(key);
					trg_row.Add(fld.To_fld_alias(), val);
				}
			}
		}
		return rv;
	}
	public static String[] Flds__to_str_ary(Sql_select_fld_list flds) {
		int len = flds.Len();
		String[] rv = new String[len];
		for (int i = 0; i < len; ++i)
			rv[i] = flds.Get_at(i).Fld;
		return rv;
	}
	public static void Where__filter(List_adp rv, Mem_row[] rows, Mem_stmt stmt, Criteria crt) {
		rv.Clear();
		int rows_len = rows.length;
		for (int i = 0; i < rows_len; ++i) {
			Mem_row itm = rows[i];
			if (crt.Matches(itm)) 
				rv.Add(itm);
		}
	}
}
