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
package gplx.dbs.sqls.wtrs; import gplx.*; import gplx.dbs.*; import gplx.dbs.sqls.*;
import org.junit.*;
public class Sql_select_wtr_tst {
	private final Sql_core_wtr_fxt fxt = new Sql_core_wtr_fxt();
	@Test   public void Offset__automatically_add_limit() {
		fxt.Test__qry(Db_qry_.select_tbl_("tbl").Offset_(1), "SELECT * FROM tbl LIMIT -1 OFFSET 1");
	}
	@Test   public void Offset__do_not_overwrite_limit() {
		fxt.Test__qry(Db_qry_.select_tbl_("tbl").Limit_(20).Offset_(1), "SELECT * FROM tbl LIMIT 20 OFFSET 1");
	}
}
