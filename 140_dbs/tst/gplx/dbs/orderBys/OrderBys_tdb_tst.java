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
package gplx.dbs.orderBys; import gplx.*; import gplx.dbs.*;
import org.junit.*;
public class OrderBys_tdb_tst extends OrderBys_base_tst {
	@Override protected Db_conn provider_() {return Db_conn_fxt.Tdb("120_dbs_joins.dsv");}
	@Test  public void Basic() {
		Basic_hook();
	}
	@Test  public void SameVals() {
		SameVals_hook();
	}
}
//namespace gplx.dbs.crudOps {
//	import org.junit.*;
//	public class CrudOps_tdb_tst {
//		@Before public void setup() {fx = new CrudOpsFxt(Db_conn_fxt.Tdb("100_dbs_crud_ops.dsv"));} CrudOpsFxt fx;
//		@Test  public void FlushToDisk() {
//			fx.Fx().tst_ExecDml(1, Db_qry_.insert_("dbs_crud_ops").Arg_("id", 2).Arg_("name", "you"));
//			Db_qry_flush.new_("dbs_crud_ops").Exec_qry(fx.Fx().Conn());
//		}
//	}
//}