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
import org.junit.*; import gplx.core.strings.*; import gplx.dbs.sqls.*;
public class Db_obj_ary_tst {
	@Before public void init() {} private Db_obj_ary_fxt fxt = new Db_obj_ary_fxt();
	@Test  public void Int() {
		fxt.Init_fld("fld_0", ClassAdp_.Tid_int).Init_fld("fld_1", ClassAdp_.Tid_int).Init_vals(1, 10).Init_vals(2, 20).Test_sql("(fld_0=1 AND fld_1=10) OR (fld_0=2 AND fld_1=20)");
	}
	@Test  public void Str() {
		fxt.Init_fld("fld_0", ClassAdp_.Tid_int).Init_fld("fld_1", ClassAdp_.Tid_str).Init_vals(1, "a").Init_vals(2, "b").Test_sql("(fld_0=1 AND fld_1='a') OR (fld_0=2 AND fld_1='b')");
	}
}
class Db_obj_ary_fxt {
	private Db_obj_ary_crt crt = new Db_obj_ary_crt();
	public Db_obj_ary_fxt Init_fld(String name, byte tid) {flds_list.Add(new Db_fld(name, tid)); return this;} private ListAdp flds_list = ListAdp_.new_();
	public Db_obj_ary_fxt Init_vals(Object... ary) {vals_list.Add(ary); return this;} private ListAdp vals_list = ListAdp_.new_();
	public Db_obj_ary_fxt Test_sql(String expd) {
		Sql_qry_wtr_ansi cmd_wtr = (Sql_qry_wtr_ansi)Sql_qry_wtr_.I;
		String_bldr sb = String_bldr_.new_();
		crt.Flds_((Db_fld[])flds_list.Xto_ary_and_clear(Db_fld.class));
		crt.Vals_((Object[][])vals_list.Xto_ary_and_clear(Object[].class));
		cmd_wtr.Append_db_obj_ary(sb, crt);
		Tfds.Eq(expd, sb.Xto_str_and_clear());
		return this;
	}
}
