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
package gplx.dbs.sqls.wtrs;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.objects.strings.AsciiByte;
import gplx.Err_;
import gplx.Keyval;
import gplx.dbs.Db_qry;
import gplx.dbs.Db_qry_;
import gplx.dbs.qrys.Db_arg;
import gplx.dbs.qrys.Db_qry__select_cmd;
import gplx.dbs.qrys.Db_qry_delete;
import gplx.dbs.qrys.Db_qry_insert;
import gplx.dbs.qrys.Db_qry_sql;
import gplx.dbs.qrys.Db_qry_update;
import gplx.dbs.sqls.SqlQryWtr;
import gplx.dbs.sqls.itms.Sql_select_fld;
public class Sql_core_wtr implements SqlQryWtr {
	private final Bry_bfr bfr = Bry_bfr_.New_w_size(64);
	public byte[] Seq__nl = AsciiByte.SpaceBry;
	public byte Seq__quote = AsciiByte.Apos, Seq__escape = AsciiByte.Backslash;
	public Sql_core_wtr() {
		this.val_wtr = Make__val_wtr();
		this.from_wtr = Make__from_wtr();
		this.where_wtr = Make__where_wtr(this, val_wtr);
		this.select_wtr = Make__select_wtr(this);
		this.schema_wtr = Make__schema_wtr();
	}
	public Sql_schema_wtr Schema_wtr() {return schema_wtr;} private final Sql_schema_wtr schema_wtr;
	public Sql_val_wtr    Val_wtr()    {return val_wtr;} private final Sql_val_wtr val_wtr;
	public Sql_from_wtr   From_wtr()   {return from_wtr;} private final Sql_from_wtr from_wtr;
	public Sql_where_wtr  Where_wtr()  {return where_wtr;} private final Sql_where_wtr where_wtr;
	public Sql_select_wtr Select_wtr() {return select_wtr;} private final Sql_select_wtr select_wtr;
	public String ToSqlStr(Db_qry qry, boolean mode_is_prep) {
		synchronized (bfr) {
			Sql_wtr_ctx ctx = new Sql_wtr_ctx(mode_is_prep);
			switch (qry.Tid()) {
				case Db_qry_.Tid_insert:        return Bld_qry_insert(ctx, (Db_qry_insert)qry);
				case Db_qry_.Tid_delete:        return Bld_qry_delete(ctx, (Db_qry_delete)qry);
				case Db_qry_.Tid_update:        return Bld_qry_update(ctx, (Db_qry_update)qry);
				case Db_qry_.Tid_select_in_tbl:
				case Db_qry_.Tid_select:        select_wtr.Bld_qry_select(bfr, ctx, (Db_qry__select_cmd)qry); return bfr.To_str_and_clear();
				case Db_qry_.Tid_pragma:        return ((gplx.dbs.engines.sqlite.Sqlite_pragma)qry).ToSqlExec(this);
				case Db_qry_.Tid_sql:            return ((Db_qry_sql)qry).ToSqlExec(this);
				default:                        throw Err_.new_unhandled(qry.Tid());
			}
		}
	}
	private String Bld_qry_delete(Sql_wtr_ctx ctx, Db_qry_delete qry) {
		bfr.Add_str_u8_many("DELETE FROM ", qry.BaseTable());
		where_wtr.Bld_where(bfr, ctx, qry.Where());
		return bfr.To_str_and_clear();
	}
	private String Bld_qry_insert(Sql_wtr_ctx ctx, Db_qry_insert qry) {
		if (qry.Select() != null) {
			bfr.Add_str_u8_many("INSERT INTO ", qry.BaseTable(), " (");
			int cols_len = qry.Cols().Len();
			for (int i = 0; i < cols_len; i++) {
				Sql_select_fld fld = qry.Cols().Get_at(i);
				bfr.Add_str_a7(fld.Alias);
				bfr.Add_str_a7(i == cols_len - 1 ? ") " : ", ");
			}
			select_wtr.Bld_qry_select(bfr, ctx, qry.Select());
			return bfr.To_str_and_clear();
		}
		int arg_count = qry.Args().Count(); if (arg_count == 0) throw Err_.new_wo_type("Db_qry_insert has no columns", "base_table", qry.BaseTable());
		int last = arg_count - 1;
		bfr.Add_str_u8_many("INSERT INTO ", qry.BaseTable(), " (");
		for (int i = 0; i < arg_count; i++) {
			Keyval pair = qry.Args().Get_at(i);
			this.Bld_col_name(bfr, pair.Key());
			bfr.Add_str_a7(i == last ? ")" : ", ");
		}
		bfr.Add_str_a7(" VALUES (");
		for (int i = 0; i < arg_count; i++) {
			Keyval pair = qry.Args().Get_at(i);
			Db_arg arg = (Db_arg)pair.Val();
			val_wtr.Bld_val(bfr, ctx, arg.Val);
			bfr.Add_str_a7(i == last ? ")" : ", ");
		}
		return bfr.To_str_and_clear();
	}
	private String Bld_qry_update(Sql_wtr_ctx ctx, Db_qry_update qry) {
		int arg_count = qry.Args().Count(); if (arg_count == 0) throw Err_.new_wo_type("Db_qry_update has no columns", "base_table", qry.BaseTable());
		bfr.Add_str_u8_many("UPDATE ", qry.BaseTable(), " SET ");
		for (int i = 0; i < arg_count; i++) {
			Keyval pair = qry.Args().Get_at(i);
			if (i > 0) bfr.Add_str_a7(", ");
			this.Bld_col_name(bfr, pair.Key());
			bfr.Add_str_a7("=");
			Db_arg arg = (Db_arg)pair.Val();
			val_wtr.Bld_val(bfr, ctx, arg.Val);
		}
		where_wtr.Bld_where(bfr, ctx, qry.Where());
		return bfr.To_str_and_clear();
	}
	public void Bld_col_name(Bry_bfr bfr, String key) {bfr.Add_str_u8(key);}
	protected Sql_val_wtr    Make__val_wtr() {return new Sql_val_wtr();}
	protected Sql_from_wtr   Make__from_wtr() {return new Sql_from_wtr();}
	protected Sql_select_wtr Make__select_wtr(Sql_core_wtr qry_wtr) {return new Sql_select_wtr(qry_wtr);}
	protected Sql_where_wtr  Make__where_wtr(Sql_core_wtr qry_wtr, Sql_val_wtr val_wtr) {return new Sql_where_wtr(qry_wtr, val_wtr);}
	protected Sql_schema_wtr Make__schema_wtr() {return new Sql_schema_wtr();}
}
