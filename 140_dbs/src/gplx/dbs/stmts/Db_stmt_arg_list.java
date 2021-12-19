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
package gplx.dbs.stmts;
import gplx.dbs.Db_stmt;
import gplx.dbs.DbmetaFldType;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.basics.utls.FloatUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class Db_stmt_arg_list {
	private final List_adp list = List_adp_.New();
	public void Clear() {list.Clear();}
	public int Len() {return list.Len();}
	public Db_stmt_arg Get_at(int i) {return (Db_stmt_arg)list.GetAt(i);}
	public Db_stmt_arg_list Crt_int			(String key, int val)		{return Add(BoolUtl.Y, DbmetaFldType.TidInt, key, val);}
	public Db_stmt_arg_list Crt_str_by_bry	(String key, byte[] val)	{return Add(BoolUtl.Y, DbmetaFldType.TidStr, key, StringUtl.NewU8(val));}
	public Db_stmt_arg_list Crt_str			(String key, String val)	{return Add(BoolUtl.Y, DbmetaFldType.TidStr, key, val);}
	public Db_stmt_arg_list Add(boolean crt, int tid, String key, Object val) {list.Add(new Db_stmt_arg(crt, tid, key, val)); return this;}
	public void Fill(Db_stmt stmt) {
		int len = list.Len();
		for (int i = 0; i < len; ++i) {
			Db_stmt_arg arg = (Db_stmt_arg)list.GetAt(i);
			if (arg.Crt)
				Fill_crt(stmt, arg.Tid, arg.Key, arg.Val);
			else
				Fill_val(stmt, arg.Tid, arg.Key, arg.Val);
		}
		list.Clear();
	}
	public static void Fill_crt(Db_stmt stmt, int tid, String key, Object val) {
		switch (tid) {
			case DbmetaFldType.TidBool:			stmt.Crt_bool_as_byte	(key, BoolUtl.Cast(val)); break;
			case DbmetaFldType.TidByte:			stmt.Crt_byte			(key, ByteUtl.Cast(val)); break;
			case DbmetaFldType.TidInt:			stmt.Crt_int			(key, IntUtl.Cast(val)); break;
			case DbmetaFldType.TidLong:			stmt.Crt_long			(key, LongUtl.Cast(val)); break;
			case DbmetaFldType.TidFloat:			stmt.Crt_float			(key, FloatUtl.Cast(val)); break;
			case DbmetaFldType.TidDouble:		stmt.Crt_double			(key, DoubleUtl.Cast(val)); break;
			case DbmetaFldType.TidStr:			stmt.Crt_str			(key, StringUtl.Cast(val)); break;
			case DbmetaFldType.TidText:			stmt.Crt_text			(key, StringUtl.Cast(val)); break;
			case DbmetaFldType.TidBry:			stmt.Crt_bry			(key, BryUtl.Cast(val)); break;
			default:								throw ErrUtl.NewUnhandled(tid);
		}
	}
	public static void Fill_val(Db_stmt stmt, int tid, String key, Object val) {
		switch (tid) {
			case DbmetaFldType.TidBool:			stmt.Val_bool_as_byte	(key, BoolUtl.Cast(val)); break;
			case DbmetaFldType.TidByte:			stmt.Val_byte			(key, ByteUtl.Cast(val)); break;
			case DbmetaFldType.TidInt:			stmt.Val_int			(key, IntUtl.Cast(val)); break;
			case DbmetaFldType.TidLong:			stmt.Val_long			(key, LongUtl.Cast(val)); break;
			case DbmetaFldType.TidFloat:			stmt.Val_float			(key, FloatUtl.Cast(val)); break;
			case DbmetaFldType.TidDouble:		stmt.Val_double			(key, DoubleUtl.Cast(val)); break;
			case DbmetaFldType.TidStr:			stmt.Val_str			(key, StringUtl.Cast(val)); break;
			case DbmetaFldType.TidText:			stmt.Val_text			(key, StringUtl.Cast(val)); break;
			case DbmetaFldType.TidBry:			stmt.Val_bry			(key, BryUtl.Cast(val)); break;
			default:								throw ErrUtl.NewUnhandled(tid);
		}
	}
}
