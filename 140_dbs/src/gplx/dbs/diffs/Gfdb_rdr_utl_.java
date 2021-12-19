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
package gplx.dbs.diffs;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_stmt;
import gplx.dbs.DbmetaFldItm;
import gplx.dbs.DbmetaFldList;
import gplx.dbs.DbmetaFldType;
import gplx.types.basics.utls.BryUtl;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class Gfdb_rdr_utl_ {
	public static int Compare(DbmetaFldItm[] flds, int len, Db_rdr lhs_rdr, Db_rdr rhs_rdr) {
		int comp = CompareAbleUtl.Same;
		for (int i = 0; i < len; ++i) {
			DbmetaFldItm fld = flds[i];
			String fld_name = fld.Name();
			int tid = fld.Type().Tid();
			switch (tid) {
				case DbmetaFldType.TidBool:		comp = BoolUtl.Compare	(lhs_rdr.Read_bool_by_byte(fld_name), rhs_rdr.Read_bool_by_byte(fld_name)); break;
				case DbmetaFldType.TidByte:		comp = ByteUtl.Compare	(lhs_rdr.Read_byte(fld_name)		, rhs_rdr.Read_byte(fld_name)); break;
				case DbmetaFldType.TidInt:		comp = IntUtl.Compare		(lhs_rdr.Read_int(fld_name)			, rhs_rdr.Read_int(fld_name)); break;
				case DbmetaFldType.TidLong:		comp = LongUtl.Compare	(lhs_rdr.Read_long(fld_name)		, rhs_rdr.Read_long(fld_name)); break;
				case DbmetaFldType.TidFloat:		comp = FloatUtl.Compare	(lhs_rdr.Read_float(fld_name)		, rhs_rdr.Read_float(fld_name)); break;
				case DbmetaFldType.TidDouble:	comp = DoubleUtl.Compare	(lhs_rdr.Read_double(fld_name)		, rhs_rdr.Read_double(fld_name)); break;
				case DbmetaFldType.TidStr:		comp = StringUtl.Compare	(lhs_rdr.Read_str(fld_name)			, rhs_rdr.Read_str(fld_name)); break;
				case DbmetaFldType.TidBry:		comp = BryUtl.Compare		(lhs_rdr.Read_bry(fld_name)			, rhs_rdr.Read_bry(fld_name)); break;
				default:							throw ErrUtl.NewUnhandled(tid);
			}
			if (comp != CompareAbleUtl.Same) return comp;
		}
		return CompareAbleUtl.Same;
	}
	public static void Stmt_args(Db_stmt stmt, DbmetaFldItm[] flds, int bgn, int end, Db_rdr rdr) {
		for (int i = bgn; i < end; ++i) {
			DbmetaFldItm fld = flds[i];
			String fld_name = fld.Name();
			int tid = fld.Type().Tid();
			switch (tid) {
				case DbmetaFldType.TidBool:		stmt.Val_bool_as_byte	(fld_name, rdr.Read_bool_by_byte(fld_name)); break;
				case DbmetaFldType.TidByte:		stmt.Val_byte			(fld_name, rdr.Read_byte(fld_name)); break;
				case DbmetaFldType.TidInt:		stmt.Val_int			(fld_name, rdr.Read_int(fld_name)); break;
				case DbmetaFldType.TidLong:		stmt.Val_long			(fld_name, rdr.Read_long(fld_name)); break;
				case DbmetaFldType.TidFloat:		stmt.Val_float			(fld_name, rdr.Read_float(fld_name)); break;
				case DbmetaFldType.TidDouble:	stmt.Val_double			(fld_name, rdr.Read_double(fld_name)); break;
				case DbmetaFldType.TidStr:		stmt.Val_str			(fld_name, rdr.Read_str(fld_name)); break;
				case DbmetaFldType.TidBry:		stmt.Val_bry			(fld_name, rdr.Read_bry(fld_name)); break;
				default:							throw ErrUtl.NewUnhandled(tid);
			}
		}
	}
	public static void Stmt_args(Db_stmt stmt, DbmetaFldList flds, int bgn, int end, Db_rdr rdr) {
		for (int i = bgn; i < end; ++i) {
			DbmetaFldItm fld = flds.GetAt(i);
			String fld_name = fld.Name();
			int tid = fld.Type().Tid();
			switch (tid) {
				case DbmetaFldType.TidBool:		stmt.Val_bool_as_byte	(fld_name, rdr.Read_bool_by_byte(fld_name)); break;
				case DbmetaFldType.TidByte:		stmt.Val_byte			(fld_name, rdr.Read_byte(fld_name)); break;
				case DbmetaFldType.TidInt:		stmt.Val_int			(fld_name, rdr.Read_int(fld_name)); break;
				case DbmetaFldType.TidLong:		stmt.Val_long			(fld_name, rdr.Read_long(fld_name)); break;
				case DbmetaFldType.TidFloat:		stmt.Val_float			(fld_name, rdr.Read_float(fld_name)); break;
				case DbmetaFldType.TidDouble:	stmt.Val_double			(fld_name, rdr.Read_double(fld_name)); break;
				case DbmetaFldType.TidStr:		stmt.Val_str			(fld_name, rdr.Read_str(fld_name)); break;
				case DbmetaFldType.TidBry:		stmt.Val_bry			(fld_name, rdr.Read_bry(fld_name)); break;
				default:							throw ErrUtl.NewUnhandled(tid);
			}
		}
	}
}
