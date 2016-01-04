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
package gplx.dbs.diffs; import gplx.*; import gplx.dbs.*;
public class Gfdb_rdr_utl_ {
	public static int Compare(Db_meta_fld[] flds, int len, Db_rdr lhs_rdr, Db_rdr rhs_rdr) {
		int comp = CompareAble_.Same;
		for (int i = 0; i < len; ++i) {
			Db_meta_fld fld = flds[i];
			String fld_name = fld.Name();
			switch (fld.Tid()) {
				case Db_meta_fld.Tid_bool:		comp = Bool_.Compare	(lhs_rdr.Read_bool_by_byte(fld_name), rhs_rdr.Read_bool_by_byte(fld_name)); break;
				case Db_meta_fld.Tid_int:		comp = Int_.Compare		(lhs_rdr.Read_int(fld_name)			, rhs_rdr.Read_int(fld_name)); break;
				case Db_meta_fld.Tid_long:		comp = Long_.Compare	(lhs_rdr.Read_long(fld_name)		, rhs_rdr.Read_long(fld_name)); break;
				case Db_meta_fld.Tid_float:		comp = Float_.Compare	(lhs_rdr.Read_float(fld_name)		, rhs_rdr.Read_float(fld_name)); break;
				case Db_meta_fld.Tid_double:	comp = Double_.Compare	(lhs_rdr.Read_double(fld_name)		, rhs_rdr.Read_double(fld_name)); break;
				case Db_meta_fld.Tid_str:		comp = String_.Compare	(lhs_rdr.Read_str(fld_name)			, rhs_rdr.Read_str(fld_name)); break;
				case Db_meta_fld.Tid_bry:		comp = Bry_.Compare		(lhs_rdr.Read_bry(fld_name)			, rhs_rdr.Read_bry(fld_name)); break;
				default:						throw Err_.new_unhandled(fld.Tid());
			}
			if (comp != CompareAble_.Same) return comp;
		}
		return CompareAble_.Same;
	}
	public static void Stmt_args(Db_stmt stmt, Db_meta_fld[] flds, int len, Db_rdr rdr) {
		for (int i = 0; i < len; ++i) {
			Db_meta_fld fld = flds[i];
			String fld_name = fld.Name();
			switch (fld.Tid()) {
				case Db_meta_fld.Tid_bool:		stmt.Val_bool_as_byte	(fld_name, rdr.Read_bool_by_byte(fld_name)); break;
				case Db_meta_fld.Tid_byte:		stmt.Val_byte			(fld_name, rdr.Read_byte(fld_name)); break;
				case Db_meta_fld.Tid_int:		stmt.Val_int			(fld_name, rdr.Read_int(fld_name)); break;
				case Db_meta_fld.Tid_long:		stmt.Val_long			(fld_name, rdr.Read_long(fld_name)); break;
				case Db_meta_fld.Tid_float:		stmt.Val_float			(fld_name, rdr.Read_float(fld_name)); break;
				case Db_meta_fld.Tid_double:	stmt.Val_double			(fld_name, rdr.Read_double(fld_name)); break;
				case Db_meta_fld.Tid_str:		stmt.Val_str			(fld_name, rdr.Read_str(fld_name)); break;
				case Db_meta_fld.Tid_bry:		stmt.Val_bry			(fld_name, rdr.Read_bry(fld_name)); break;
				default:						throw Err_.new_unhandled(fld.Tid());
			}
		}
	}
}
