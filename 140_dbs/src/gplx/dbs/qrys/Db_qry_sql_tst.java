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
package gplx.dbs.qrys; import gplx.*; import gplx.dbs.*;
import org.junit.*; import gplx.dbs.sqls.*;
public class Db_qry_sql_tst {
	@Before public void init() {fxt.Clear();} private Db_qry_sql_fxt fxt = new Db_qry_sql_fxt();
	@Test  public void Insert() {
		fxt.Test_qry
		( Db_qry_insert.new_("tbl", "k1", "k2", "k3", "k4", "k5", "k6", "k7", "k8", "k9")
		, Object_.Ary(123, Bool_.Y, 1.23d, 123L, 123f, Byte_ascii.Num_1, "123", DateAdp_.parse_iso8561("1981-04-05T14:30:30"), Decimal_adp_.parse("1.23"))
		, "INSERT INTO tbl (k1, k2, k3, k4, k5, k6, k7, k8, k9) VALUES (123, 1, 1.23, 123, 123, 1, '123', '1981-04-05 14:30:30.000', 1.23)"
		);
	}
	@Test  public void Update() {
		fxt.Test_qry
		( Db_qry_update.New("tbl", String_.Ary("k1", "k2"), "k3", "k4")
		, Object_.Ary("v3", "v4", "v1", "v2")
		, "UPDATE tbl SET k3='v3', k4='v4' WHERE (k1 = 'v1' AND k2 = 'v2')"
		);
	}
	@Test  public void Delete() {
		fxt.Test_qry
		( Db_qry_delete.new_("tbl", String_.Ary("k1", "k2"))
		, Object_.Ary("v1", "v2")
		, "DELETE FROM tbl WHERE (k1 = 'v1' AND k2 = 'v2')"
		);
	}
}
class Db_qry_sql_fxt {
	private final    Sql_qry_wtr qry_wtr = Sql_qry_wtr_.New__sqlite();
	public void Clear() {}
	public void Test_qry(Db_qry qry, Object[] vals, String expd) {Tfds.Eq(expd, Db_qry_sql.Gen_sql(qry_wtr, qry, vals));}
}
