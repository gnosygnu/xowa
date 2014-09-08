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
package gplx.dbs; import gplx.*;
public class Db_stmt_ {
	public static final Db_stmt Null = new Db_stmt_sql();
	public static Db_stmt new_insert_(Db_provider provider, String tbl, String... flds) {
		Db_qry_insert qry = Db_qry_insert.new_().BaseTable_(tbl);
		int len = flds.length;
		for (int i = 0; i < len; i++)
			qry.Arg_obj_(flds[i], null);		
		return provider.Prepare(qry);
	}
	public static Db_stmt new_update_(Db_provider provider, String tbl, String[] where, String... flds) {
		Db_qry_update qry = Db_qry_.update_(tbl, Db_crt_.eq_many_(where));
		int len = flds.length;
		for (int i = 0; i < len; i++)
			qry.Arg_obj_(flds[i], null);
		return provider.Prepare(qry);
	}
	public static Db_stmt new_delete_(Db_provider provider, String tbl, String... where) {
		Db_qry_delete qry = Db_qry_.delete_(tbl, Db_crt_.eq_many_(where));
		return provider.Prepare(qry);
	}
	public static Db_stmt new_select_(Db_provider provider, String tbl, String[] where, String... flds) {
		Db_qry_select qry = Db_qry_.select_cols_(tbl, Db_crt_.eq_many_(where), flds);
		return provider.Prepare(qry);
	}
	public static Db_stmt new_select_in_(Db_provider provider, String tbl, String in_fld, Object[] in_vals, String... flds) {
		Db_qry_select qry = Db_qry_.select_cols_(tbl, Db_crt_.in_(in_fld, in_vals), flds).OrderBy_asc_(in_fld);
		return provider.Prepare(qry);
	}
	public static Db_stmt new_select_all_(Db_provider provider, String tbl) {
		return provider.Prepare(Db_qry_.select_tbl_(tbl));
	}
	public static Db_stmt new_select_as_rdr(Db_provider provider, Db_qry__select_in_tbl qry) {
		return provider.Prepare(qry);
	}
}
