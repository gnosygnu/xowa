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
package gplx.dbs.joins; import gplx.*; import gplx.dbs.*;
import org.junit.*; import gplx.core.gfo_ndes.*; import gplx.dbs.qrys.*; import gplx.dbs.sqls.*; import gplx.core.stores.*;
public abstract class Joins_base_tst {
	protected Db_conn conn;
	@Before public void setup() {
		conn = provider_();
		Db_qry_delete.new_("dbs_crud_ops").Exec_qry(conn);
		Db_qry_delete.new_("dbs_join1").Exec_qry(conn);
	}
	@After public void teardown() {
		conn.Rls_conn();
	}
	protected void InnerJoin_hook() {
		conn.Exec_qry(new Db_qry_insert("dbs_crud_ops").Val_int("id", 0).Val_str("name", "me"));
		conn.Exec_qry(new Db_qry_insert("dbs_crud_ops").Val_int("id", 1).Val_str("name", "you"));
		conn.Exec_qry(new Db_qry_insert("dbs_join1").Val_int("join_id", 0).Val_str("join_data", "data0"));
		conn.Exec_qry(new Db_qry_insert("dbs_join1").Val_int("join_id", 1).Val_str("join_data", "data1"));
		Db_qry__select_cmd select = new Db_qry__select_cmd().From_("dbs_crud_ops").Join_("dbs_join1", "j1", Db_qry_.New_join__join("join_id", "dbs_crud_ops", "id")).Cols_("id", "name", "join_data");

		DataRdr rdr = conn.Exec_qry_as_old_rdr(select);
		GfoNde table = GfoNde_.rdr_(rdr);
		Tfds.Eq(table.Subs().Count(), 2);
		Tfds.Eq(table.Subs().FetchAt_asGfoNde(0).Read("join_data"), "data0");
		Tfds.Eq(table.Subs().FetchAt_asGfoNde(1).Read("join_data"), "data1");
	}
	protected abstract Db_conn provider_();
}
