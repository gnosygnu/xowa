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
import gplx.core.criterias.*;
class Sql_core_wtr_fxt {
	private final Sql_core_wtr__sqlite wtr = new Sql_core_wtr__sqlite();
	private final Sql_wtr_ctx ctx = new Sql_wtr_ctx(false);
	private final Bry_bfr bfr = Bry_bfr.new_();
	public Sql_core_wtr_fxt Sql_wtr_(Sql_qry_wtr v) {sql_wtr = v; return this;} private Sql_qry_wtr sql_wtr = Sql_qry_wtr_.Sqlite;
	public void Test__val(Object val, String expd) {
		wtr.Val_wtr().Bld_val(bfr, ctx, val);
		Tfds.Eq_str(expd, bfr.To_str_and_clear());
	}
	public void Test__where(Criteria crt, String... expd) {
		wtr.Where_wtr().Bld_where_elem(bfr, ctx, crt);
		Tfds.Eq_str_lines(String_.Concat_lines_nl_skip_last(expd), bfr.To_str_and_clear());
	}
	public void Test__qry(Db_qry qry, String expd) {
		Tfds.Eq_str_lines(expd, sql_wtr.To_sql_str(qry, Bool_.N));
	}
}
