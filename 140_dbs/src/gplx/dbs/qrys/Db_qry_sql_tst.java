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
package gplx.dbs.qrys;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDateUtl;
import gplx.types.commons.GfoDecimalUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.dbs.Db_qry;
import gplx.dbs.sqls.SqlQryWtr;
import gplx.dbs.sqls.SqlQryWtrUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
import org.junit.Before;
import org.junit.Test;
public class Db_qry_sql_tst {
	@Before public void init() {fxt.Clear();} private Db_qry_sql_fxt fxt = new Db_qry_sql_fxt();
	@Test public void Insert() {
		fxt.Test_qry
		( Db_qry_insert.new_("tbl", "k1", "k2", "k3", "k4", "k5", "k6", "k7", "k8", "k9")
		, ObjectUtl.Ary(123, BoolUtl.Y, 1.23d, 123L, 123f, AsciiByte.Num1, "123", GfoDateUtl.ParseIso8561("1981-04-05T14:30:30"), GfoDecimalUtl.Parse("1.23"))
		, "INSERT INTO tbl (k1, k2, k3, k4, k5, k6, k7, k8, k9) VALUES (123, 1, 1.23, 123, 123, 1, '123', '1981-04-05 14:30:30.000', 1.23)"
		);
	}
	@Test public void Update() {
		fxt.Test_qry
		( Db_qry_update.New("tbl", StringUtl.Ary("k1", "k2"), "k3", "k4")
		, ObjectUtl.Ary("v3", "v4", "v1", "v2")
		, "UPDATE tbl SET k3='v3', k4='v4' WHERE (k1 = 'v1' AND k2 = 'v2')"
		);
	}
	@Test public void Delete() {
		fxt.Test_qry
		( Db_qry_delete.new_("tbl", StringUtl.Ary("k1", "k2"))
		, ObjectUtl.Ary("v1", "v2")
		, "DELETE FROM tbl WHERE (k1 = 'v1' AND k2 = 'v2')"
		);
	}
}
class Db_qry_sql_fxt {
	private final SqlQryWtr qry_wtr = SqlQryWtrUtl.NewSqlite();
	public void Clear() {}
	public void Test_qry(Db_qry qry, Object[] vals, String expd) {GfoTstr.EqObj(expd, Db_qry_sql.Gen_sql(qry_wtr, qry, vals));}
}
