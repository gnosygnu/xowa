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
package gplx.dbs.sqls.itms; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
import org.junit.*; import gplx.core.strings.*; import gplx.dbs.sqls.*;
import gplx.dbs.sqls.wtrs.*;
public class Db_obj_ary_tst {
	@Before public void init() {} private Db_obj_ary_fxt fxt = new Db_obj_ary_fxt();
	@Test  public void Int() {
		fxt.Init_fld("fld_0", Type_adp_.Tid__int).Init_fld("fld_1", Type_adp_.Tid__int).Init_vals(1, 10).Init_vals(2, 20).Test_sql("(fld_0=1 AND fld_1=10) OR (fld_0=2 AND fld_1=20)");
	}
	@Test  public void Str() {
		fxt.Init_fld("fld_0", Type_adp_.Tid__int).Init_fld("fld_1", Type_adp_.Tid__str).Init_vals(1, "a").Init_vals(2, "b").Test_sql("(fld_0=1 AND fld_1='a') OR (fld_0=2 AND fld_1='b')");
	}
}
class Db_obj_ary_fxt {
	private final    Db_obj_ary_crt crt = new Db_obj_ary_crt();
	private final    Sql_wtr_ctx ctx = new Sql_wtr_ctx(false);
	public Db_obj_ary_fxt Init_fld(String name, int tid) {flds_list.Add(new Db_obj_ary_fld(name, tid)); return this;} private List_adp flds_list = List_adp_.New();
	public Db_obj_ary_fxt Init_vals(Object... ary) {vals_list.Add(ary); return this;} private List_adp vals_list = List_adp_.New();
	public Db_obj_ary_fxt Test_sql(String expd) {
		Sql_core_wtr cmd_wtr = (Sql_core_wtr)Sql_qry_wtr_.Basic;
		crt.Flds_((Db_obj_ary_fld[])flds_list.To_ary_and_clear(Db_obj_ary_fld.class));
		crt.Vals_((Object[][])vals_list.To_ary_and_clear(Object[].class));
		Bry_bfr bfr = Bry_bfr_.New();
		cmd_wtr.Where_wtr().Bld_where__db_obj(bfr, ctx, crt);
		Tfds.Eq(expd, bfr.To_str_and_clear());
		return this;
	}
}
