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
package gplx.dbs.stmts; import gplx.*; import gplx.dbs.*;
public class Db_stmt_arg_list {
	private final List_adp list = List_adp_.New();
	public void Clear() {list.Clear();}
	public int Len() {return list.Len();}
	public Db_stmt_arg Get_at(int i) {return (Db_stmt_arg)list.Get_at(i);}
	public Db_stmt_arg_list Crt_int			(String key, int val)		{return Add(Bool_.Y, DbmetaFldType.TidInt, key, val);}
	public Db_stmt_arg_list Crt_str_by_bry	(String key, byte[] val)	{return Add(Bool_.Y, DbmetaFldType.TidStr, key, String_.new_u8(val));}
	public Db_stmt_arg_list Crt_str			(String key, String val)	{return Add(Bool_.Y, DbmetaFldType.TidStr, key, val);}
	public Db_stmt_arg_list Add(boolean crt, int tid, String key, Object val) {list.Add(new Db_stmt_arg(crt, tid, key, val)); return this;}
	public void Fill(Db_stmt stmt) {
		int len = list.Len();
		for (int i = 0; i < len; ++i) {
			Db_stmt_arg arg = (Db_stmt_arg)list.Get_at(i);
			if (arg.Crt)
				Fill_crt(stmt, arg.Tid, arg.Key, arg.Val);
			else
				Fill_val(stmt, arg.Tid, arg.Key, arg.Val);
		}
		list.Clear();
	}
	public static void Fill_crt(Db_stmt stmt, int tid, String key, Object val) {
		switch (tid) {
			case DbmetaFldType.TidBool:			stmt.Crt_bool_as_byte	(key, Bool_.Cast(val)); break;
			case DbmetaFldType.TidByte:			stmt.Crt_byte			(key, Byte_.Cast(val)); break;
			case DbmetaFldType.TidInt:			stmt.Crt_int			(key, Int_.Cast(val)); break;
			case DbmetaFldType.TidLong:			stmt.Crt_long			(key, Long_.cast(val)); break;
			case DbmetaFldType.TidFloat:			stmt.Crt_float			(key, Float_.cast(val)); break;
			case DbmetaFldType.TidDouble:		stmt.Crt_double			(key, Double_.cast(val)); break;
			case DbmetaFldType.TidStr:			stmt.Crt_str			(key, String_.cast(val)); break;
			case DbmetaFldType.TidText:			stmt.Crt_text			(key, String_.cast(val)); break;
			case DbmetaFldType.TidBry:			stmt.Crt_bry			(key, Bry_.cast(val)); break;
			default:								throw Err_.new_unhandled_default(tid);
		}
	}
	public static void Fill_val(Db_stmt stmt, int tid, String key, Object val) {
		switch (tid) {
			case DbmetaFldType.TidBool:			stmt.Val_bool_as_byte	(key, Bool_.Cast(val)); break;
			case DbmetaFldType.TidByte:			stmt.Val_byte			(key, Byte_.Cast(val)); break;
			case DbmetaFldType.TidInt:			stmt.Val_int			(key, Int_.Cast(val)); break;
			case DbmetaFldType.TidLong:			stmt.Val_long			(key, Long_.cast(val)); break;
			case DbmetaFldType.TidFloat:			stmt.Val_float			(key, Float_.cast(val)); break;
			case DbmetaFldType.TidDouble:		stmt.Val_double			(key, Double_.cast(val)); break;
			case DbmetaFldType.TidStr:			stmt.Val_str			(key, String_.cast(val)); break;
			case DbmetaFldType.TidText:			stmt.Val_text			(key, String_.cast(val)); break;
			case DbmetaFldType.TidBry:			stmt.Val_bry			(key, Bry_.cast(val)); break;
			default:								throw Err_.new_unhandled_default(tid);
		}
	}
}
