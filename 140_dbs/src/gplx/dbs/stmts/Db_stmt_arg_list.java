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
package gplx.dbs.stmts; import gplx.*; import gplx.dbs.*;
public class Db_stmt_arg_list {
	private final    List_adp list = List_adp_.new_();
	public void Clear() {list.Clear();}
	public int Len() {return list.Len();}
	public Db_stmt_arg Get_at(int i) {return (Db_stmt_arg)list.Get_at(i);}
	public Db_stmt_arg_list Crt_int			(String key, int val)		{return Add(Bool_.Y, Dbmeta_fld_tid.Tid__int, key, val);}
	public Db_stmt_arg_list Crt_str_by_bry	(String key, byte[] val)	{return Add(Bool_.Y, Dbmeta_fld_tid.Tid__str, key, String_.new_u8(val));}
	public Db_stmt_arg_list Crt_str			(String key, String val)	{return Add(Bool_.Y, Dbmeta_fld_tid.Tid__str, key, val);}
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
	private static void Fill_crt(Db_stmt stmt, int tid, String key, Object val) {
		switch (tid) {
			case Dbmeta_fld_tid.Tid__bool:			stmt.Crt_bool_as_byte	(key, Bool_.cast(val)); break;
			case Dbmeta_fld_tid.Tid__byte:			stmt.Crt_byte			(key, Byte_.cast(val)); break;
			case Dbmeta_fld_tid.Tid__int:			stmt.Crt_int			(key, Int_.cast(val)); break;
			case Dbmeta_fld_tid.Tid__long:			stmt.Crt_long			(key, Long_.cast(val)); break;
			case Dbmeta_fld_tid.Tid__float:			stmt.Crt_float			(key, Float_.cast(val)); break;
			case Dbmeta_fld_tid.Tid__double:		stmt.Crt_double			(key, Double_.cast(val)); break;
			case Dbmeta_fld_tid.Tid__str:			stmt.Crt_str			(key, String_.cast(val)); break;
			case Dbmeta_fld_tid.Tid__bry:			stmt.Crt_bry			(key, Bry_.cast(val)); break;
			default:								throw Err_.new_unhandled_default(tid);
		}
	}
	private static void Fill_val(Db_stmt stmt, int tid, String key, Object val) {
		switch (tid) {
			case Dbmeta_fld_tid.Tid__bool:			stmt.Val_bool_as_byte	(key, Bool_.cast(val)); break;
			case Dbmeta_fld_tid.Tid__byte:			stmt.Val_byte			(key, Byte_.cast(val)); break;
			case Dbmeta_fld_tid.Tid__int:			stmt.Val_int			(key, Int_.cast(val)); break;
			case Dbmeta_fld_tid.Tid__long:			stmt.Val_long			(key, Long_.cast(val)); break;
			case Dbmeta_fld_tid.Tid__float:			stmt.Val_float			(key, Float_.cast(val)); break;
			case Dbmeta_fld_tid.Tid__double:		stmt.Val_double			(key, Double_.cast(val)); break;
			case Dbmeta_fld_tid.Tid__str:			stmt.Val_str			(key, String_.cast(val)); break;
			case Dbmeta_fld_tid.Tid__bry:			stmt.Val_bry			(key, Bry_.cast(val)); break;
			default:								throw Err_.new_unhandled_default(tid);
		}
	}
}
