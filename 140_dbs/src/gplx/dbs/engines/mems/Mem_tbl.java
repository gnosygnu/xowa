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
import gplx.core.primitives.*; import gplx.core.criterias.*; import gplx.dbs.qrys.*; import gplx.dbs.sqls.itms.*;
import gplx.dbs.metas.*;
public class Mem_tbl {
	private final    List_adp where_rows = List_adp_.New();
	private final    Hash_adp autonum_hash = Hash_adp_.New();		
	public Mem_tbl(Dbmeta_tbl_itm meta) {this.meta = meta;}
	public Dbmeta_tbl_itm Meta() {return meta;} private final    Dbmeta_tbl_itm meta;
	public final    List_adp rows = List_adp_.New(); 
	public int Insert(Mem_stmt stmt) {
		Mem_row itm = new Mem_row();
		Dbmeta_fld_mgr flds = meta.Flds();
		int len = flds.Len();
		for (int i = 0; i < len; ++i) {
			Dbmeta_fld_itm fld = flds.Get_at(i);
			String fld_name = fld.Name();
			Object val = fld.Autonum() ? Autonum_calc(fld_name) : stmt.Args_get_by(fld_name);
			if (val == null) continue; // NOTE: allow Bulk_insert from test to skip filds
			itm.Set_by(fld_name, val);
		}
		rows.Add(itm);
		return 1;
	}
	private int Autonum_calc(String name) {
		Int_obj_ref autonum_itm = (Int_obj_ref)autonum_hash.Get_by(name);
		if (autonum_itm == null) {
			autonum_itm = Int_obj_ref.New(0);
			autonum_hash.Add(name, autonum_itm);
		}
		return autonum_itm.Val_add();
	}
	public int Update(Mem_stmt stmt) {
		Db_qry_update qry = (Db_qry_update)stmt.Qry();
		Criteria where_crt = qry.Where(); if (where_crt == null) where_crt = Criteria_.All;
		Mem_stmt_args_.Fill(stmt.Stmt_args(), where_crt);
		Select_rows_where(where_rows, stmt, where_crt);
		int where_rows_len = where_rows.Count();
		String[] update_cols = qry.Cols_for_update(); int update_cols_len = update_cols.length;
		for (int i = 0; i < where_rows_len; ++i) {
			Mem_row itm = (Mem_row)where_rows.Get_at(i);
			for (int j = 0; j < update_cols_len; ++j)
				itm.Set_by(update_cols[j], stmt.Args_get_at(j));
		}
		return where_rows_len;
	}
	public int Delete(Mem_stmt stmt) {
		Db_qry_delete qry = (Db_qry_delete)stmt.Qry();
		Mem_stmt_args_.Fill(stmt.Stmt_args(), qry.Where());
		Select_rows_where(where_rows, stmt, qry.Where());
		int where_rows_len = where_rows.Count();
		for (int i = 0; i < where_rows_len; ++i) {
			Mem_row itm = (Mem_row)where_rows.Get_at(i);
			rows.Del(itm);
		}
		return where_rows_len;
	}
	public Db_rdr Select(Mem_stmt stmt) {
		String[] select = null; Criteria where = null;
		Db_qry__select_in_tbl qry = Db_qry__select_in_tbl.as_(stmt.Qry());
		if (qry == null) {
			Db_qry__select_cmd qry2 = (Db_qry__select_cmd)stmt.Qry();
			select = To_str_ary(qry2.Cols().Flds);
			where = qry2.Where_itm().Root;
		}
		else {
			select = qry.Select_flds();
			where = qry.Where();
		}
		Mem_stmt_args_.Fill(stmt.Stmt_args(), where);
		Select_rows_where(where_rows, stmt, where);
		return new Mem_rdr(select, (Mem_row[])where_rows.To_ary_and_clear(Mem_row.class));
	}
	private String[] To_str_ary(Sql_select_fld_list flds) {
		int len = flds.Len();
		String[] rv = new String[len];
		for (int i = 0; i < len; ++i)
			rv[i] = flds.Get_at(i).Fld;
		return rv;
	}
	private void Select_rows_where(List_adp rv, Mem_stmt stmt, Criteria crt) {
		rv.Clear();
		int rows_len = rows.Count();
		for (int i = 0; i < rows_len; ++i) {
			Mem_row itm = (Mem_row)rows.Get_at(i);
			if (crt.Matches(itm)) 
				rv.Add(itm);
		}
	}
}
