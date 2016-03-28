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
package gplx.dbs.sqls.wtrs; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
import gplx.dbs.sqls.itms.*;
public class Sql_from_wtr {
	public void Bld_clause_from(Bry_bfr bfr, Sql_from_clause from) {
		List_adp tbls = from.Tbls; int tbls_len = tbls.Count();
		for (int i = 0; i < tbls_len; ++i) {
			Sql_tbl_itm tbl = (Sql_tbl_itm)tbls.Get_at(i);
			bfr.Add_byte_space().Add_str_a7(Bld_join(tbl.Join_tid)).Add_byte_space();
			if (tbl.Db != Sql_tbl_itm.Db__null && tbl.Db_enabled)
				bfr.Add_str_u8(tbl.Db).Add_byte_dot();
			bfr.Add_str_u8(tbl.Name);
			if (tbl.Alias != Sql_tbl_itm.Alias__null)
				bfr.Add_byte_space().Add_str_u8(tbl.Alias);
			String tbl_alias = tbl.Alias == null ? tbl.Name : tbl.Alias;
			Sql_join_fld[] flds = tbl.Join_flds; int flds_len = flds.length;
			for (int j = 0; j < flds_len; ++j) {
				Sql_join_fld join_fld = flds[j];
				bfr.Add_str_a7(j == 0 ? " ON " : " AND ");
				bfr.Add_str_u8(join_fld.Src_tbl).Add_byte_dot().Add_str_u8(join_fld.Src_fld);
				bfr.Add(Bry__join_eq);
				bfr.Add_str_u8(tbl_alias).Add_byte_dot().Add_str_u8(join_fld.Trg_fld);
			}
		}
	}
	private String Bld_join(int tid) {
		switch (tid) {
			case Sql_tbl_itm.Tid__from	: return "FROM";
			case Sql_tbl_itm.Tid__inner	: return "INNER JOIN";
			case Sql_tbl_itm.Tid__left	: return "LEFT JOIN";
			case Sql_tbl_itm.Tid__right	: return "RIGHT JOIN";
			case Sql_tbl_itm.Tid__outer	: return "OUTER JOIN";
			case Sql_tbl_itm.Tid__cross	: return "CROSS JOIN";
			default						: throw Err_.new_unhandled_default(tid);
		}
	}
	private static final byte[] Bry__join_eq = Bry_.new_a7(" = ");
}
