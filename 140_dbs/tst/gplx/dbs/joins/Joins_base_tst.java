/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
