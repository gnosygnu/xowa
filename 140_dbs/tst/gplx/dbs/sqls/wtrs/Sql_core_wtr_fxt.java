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
package gplx.dbs.sqls.wtrs;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.String_;
import gplx.Tfds;
import gplx.core.criterias.Criteria;
import gplx.dbs.Db_qry;
import gplx.dbs.sqls.SqlQryWtr;
import gplx.dbs.sqls.SqlQryWtrUtl;
import gplx.objects.primitives.BoolUtl;
class Sql_core_wtr_fxt {
	private final Sql_core_wtr__sqlite wtr = new Sql_core_wtr__sqlite();
	private final Sql_wtr_ctx ctx = new Sql_wtr_ctx(false);
	private final Bry_bfr bfr = Bry_bfr_.New();
	public Sql_core_wtr_fxt Sql_wtr_(SqlQryWtr v) {sql_wtr = v; return this;} private SqlQryWtr sql_wtr = SqlQryWtrUtl.NewSqlite();
	public void Test__val(Object val, String expd) {
		wtr.Val_wtr().Bld_val(bfr, ctx, val);
		Tfds.Eq_str(expd, bfr.To_str_and_clear());
	}
	public void Test__where(Criteria crt, String... expd) {
		wtr.Where_wtr().Bld_where_elem(bfr, ctx, crt);
		Tfds.Eq_str_lines(String_.Concat_lines_nl_skip_last(expd), bfr.To_str_and_clear());
	}
	public void Test__qry(Db_qry qry, String expd) {
		Tfds.Eq_str_lines(expd, sql_wtr.ToSqlStr(qry, BoolUtl.N));
	}
}
