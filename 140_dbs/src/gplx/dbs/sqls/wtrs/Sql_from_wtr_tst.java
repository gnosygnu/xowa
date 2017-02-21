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
public class Sql_from_wtr_tst {
	private final Sql_core_wtr_fxt fxt = new Sql_core_wtr_fxt();
	@Test   public void Abrv() {
		fxt.Test__qry(Db_qry_.select_().Cols_all_().From_("tbl", "t"), "SELECT * FROM tbl t");
	}
	@Test   public void Db() {
		fxt.Test__qry(Db_qry_.select_().Cols_all_().From_("db", "tbl", "t"), "SELECT * FROM db.tbl t");
	}
	@Test   public void Join() {
		fxt.Test__qry
		( Db_qry_.select_().Cols_all_().From_("src", "s").Join_("trg", "t", Db_qry_.New_join__join("trg_id", "s", "src_id"))
		, "SELECT * FROM src s INNER JOIN trg t ON s.src_id = t.trg_id");
	}
}
