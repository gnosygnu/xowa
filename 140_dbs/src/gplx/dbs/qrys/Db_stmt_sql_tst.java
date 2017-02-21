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
import org.junit.*;
public class Db_stmt_sql_tst {
	@Before public void init() {}
	@Test    public void Basic() {
		Db_stmt_sql stmt = new Db_stmt_sql();
		stmt.Parse(null, "UPDATE tbl_0 SET col_0 = ? WHERE col_1 = ?");
		stmt.Add("col_0", "1");
		stmt.Add("col_1", "2");
		Tfds.Eq("UPDATE tbl_0 SET col_0 = 1 WHERE col_1 = 2", stmt.Xto_sql());
	}
}
