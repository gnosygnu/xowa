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
package gplx.dbs.sqls.itms;
import gplx.dbs.sqls.*;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.TypeIds;
import org.junit.*;
import gplx.dbs.sqls.wtrs.*;
public class Db_obj_ary_tst {
	@Before public void init() {} private Db_obj_ary_fxt fxt = new Db_obj_ary_fxt();
	@Test public void Int() {
		fxt.Init_fld("fld_0", TypeIds.IdInt).Init_fld("fld_1", TypeIds.IdInt).Init_vals(1, 10).Init_vals(2, 20).Test_sql("(fld_0=1 AND fld_1=10) OR (fld_0=2 AND fld_1=20)");
	}
	@Test public void Str() {
		fxt.Init_fld("fld_0", TypeIds.IdInt).Init_fld("fld_1", TypeIds.IdStr).Init_vals(1, "a").Init_vals(2, "b").Test_sql("(fld_0=1 AND fld_1='a') OR (fld_0=2 AND fld_1='b')");
	}
}
class Db_obj_ary_fxt {
	private final Db_obj_ary_crt crt = new Db_obj_ary_crt();
	private final Sql_wtr_ctx ctx = new Sql_wtr_ctx(false);
	public Db_obj_ary_fxt Init_fld(String name, int tid) {flds_list.Add(new Db_obj_ary_fld(name, tid)); return this;} private List_adp flds_list = List_adp_.New();
	public Db_obj_ary_fxt Init_vals(Object... ary) {vals_list.Add(ary); return this;} private List_adp vals_list = List_adp_.New();
	public Db_obj_ary_fxt Test_sql(String expd) {
		Sql_core_wtr cmd_wtr = (Sql_core_wtr)SqlQryWtrUtl.NewBasic();
		crt.Flds_((Db_obj_ary_fld[])flds_list.ToAryAndClear(Db_obj_ary_fld.class));
		crt.Vals_((Object[][])vals_list.ToAryAndClear(Object[].class));
		BryWtr bfr = BryWtr.New();
		cmd_wtr.Where_wtr().Bld_where__db_obj(bfr, ctx, crt);
		GfoTstr.EqObj(expd, bfr.ToStrAndClear());
		return this;
	}
}
