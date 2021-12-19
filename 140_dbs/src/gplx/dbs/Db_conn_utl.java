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
package gplx.dbs;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url_;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.BoolUtl;
public class Db_conn_utl {
	public static Db_conn Conn__new(String url_rel) {
		Db_conn_bldr.Instance.Reg_default_mem();
		return Db_conn_bldr.Instance.Get_or_new(Io_url_.mem_fil_("mem/" + url_rel)).Conn();
	}
	public static void Tbl__new(Db_conn conn, String tbl, DbmetaFldItm[] flds, Object[]... rows) {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl, flds));
		int rows_len = rows.length;
		Db_stmt stmt = conn.Stmt_insert(tbl, DbmetaFldItm.ToStrAry(flds));
		for (int i = 0; i < rows_len; ++i) {
			Object[] row = rows[i];
			int dat_len = row.length;
			stmt.Clear();
			for (int j = 0; j < dat_len; ++j) {
				DbmetaFldItm fld = flds[j];
				String fld_name = fld.Name();
				Object val = row[j];
				switch (fld.Type().Tid()) {
					case DbmetaFldType.TidBool:		stmt.Val_bool_as_byte	(fld_name, BoolUtl.Cast(val)); break;
					case DbmetaFldType.TidByte:		stmt.Val_byte			(fld_name, ByteUtl.Cast(val)); break;
					case DbmetaFldType.TidInt:		stmt.Val_int			(fld_name, IntUtl.Cast(val)); break;
					case DbmetaFldType.TidLong:		stmt.Val_long			(fld_name, LongUtl.Cast(val)); break;
					case DbmetaFldType.TidFloat:		stmt.Val_float			(fld_name, FloatUtl.Cast(val)); break;
					case DbmetaFldType.TidDouble:	stmt.Val_double			(fld_name, DoubleUtl.Cast(val)); break;
					case DbmetaFldType.TidStr:		stmt.Val_str			(fld_name, StringUtl.Cast(val)); break;
					case DbmetaFldType.TidBry:		stmt.Val_bry			(fld_name, BryUtl.Cast(val)); break;
				}
			}
			stmt.Exec_insert();
		}
	}
	public static void Insert(Db_conn conn, String tbl_name, String[] flds, Object[]... rows) {
		Db_stmt stmt = Db_stmt_.new_insert_(conn, tbl_name, flds);
		int flds_len = flds.length;
		int rows_len = rows.length;
		for (int i = 0; i < rows_len; ++i) {
			Object[] row = rows[i];
			stmt.Clear();
			for (int j = 0; j < flds_len; ++j)
				Db_stmt_.Val_by_obj(stmt, flds[j], row[j]);
			stmt.Exec_insert();
		}
	}
	public static Object[][] Select(Db_conn conn, Db_qry qry) {
		List_adp rv = List_adp_.New();
		Db_rdr rdr = conn.Stmt_new(qry).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				int fld_len = rdr.Fld_len();
				Object[] row = new Object[fld_len];
				for (int i = 0; i < fld_len; ++i)
					row[i] = rdr.Read_at(i);
				rv.Add(row);
			}
		}	finally {rdr.Rls();}
		return (Object[][])rv.ToAryAndClear(Object[].class);
	}
}
