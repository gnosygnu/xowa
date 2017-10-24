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
import org.junit.*; import gplx.dbs.qrys.*;
public abstract class OrderBys_base_tst {
	@Before public void setup() {
		conn = provider_();
		fx.Conn_(conn);
		Db_qry_delete.new_("dbs_crud_ops").Exec_qry(conn);
	}	protected Db_conn_fxt fx = new Db_conn_fxt();
	@After public void teardown() {conn.Rls_conn();}
	protected abstract Db_conn provider_(); protected Db_conn conn;		
	protected void Basic_hook() {
		fx.tst_ExecDml(1, new Db_qry_insert("dbs_crud_ops").Val_int("id", 1).Val_str("name", "you"));
		fx.tst_ExecDml(1, new Db_qry_insert("dbs_crud_ops").Val_int("id", 0).Val_str("name", "me"));

		fx.tst_ExecRdr(2, new Db_qry__select_cmd().From_("dbs_crud_ops").Order_("id", true));
		fx.tst_RowAry(0, 0, "me");
		fx.tst_RowAry(1, 1, "you");

		fx.tst_ExecRdr(2, new Db_qry__select_cmd().From_("dbs_crud_ops").Order_("id", false));
		fx.tst_RowAry(0, 1, "you");
		fx.tst_RowAry(1, 0, "me");
	}
	protected void SameVals_hook() {
		fx.tst_ExecDml(1, new Db_qry_insert("dbs_crud_ops").Val_int("id", 0).Val_str("name", "me"));
		fx.tst_ExecDml(1, new Db_qry_insert("dbs_crud_ops").Val_int("id", 0).Val_str("name", "you"));

		fx.tst_ExecRdr(2, new Db_qry__select_cmd().From_("dbs_crud_ops").Order_("id", true));
		fx.tst_RowAry(0, 0, "me");
		fx.tst_RowAry(1, 0, "you");

		fx.tst_ExecRdr(2, new Db_qry__select_cmd().From_("dbs_crud_ops").Order_("id", false));
		fx.tst_RowAry(0, 0, "me");
		fx.tst_RowAry(1, 0, "you");
	}
}
