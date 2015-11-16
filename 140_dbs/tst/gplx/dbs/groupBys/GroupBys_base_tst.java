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
package gplx.dbs.groupBys; import gplx.*; import gplx.dbs.*;
import org.junit.*; import gplx.dbs.qrys.*; import gplx.core.gfo_ndes.*;
public abstract class GroupBys_base_tst {
	protected abstract Db_conn provider_();
	protected Db_conn conn;
	@Before public void setup() {
		conn = provider_();
		Db_qry_.delete_tbl_("dbs_group_bys").Exec_qry(conn);
	}
	@After public void teardown() {
		conn.Rls_conn();
	}
	protected void GroupBy_1fld_hook() {
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Arg_("key1", "a").Arg_("val_int", 1));
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Arg_("key1", "a").Arg_("val_int", 2));

		DataRdr rdr = Db_qry_.select_().From_("dbs_group_bys")
			.Cols_("key1")
			.GroupBy_("key1")
			.Exec_qry_as_rdr(conn);
		GfoNde nde = GfoNde_.rdr_(rdr);
		GfoNdeTstr.tst_ValsByCol(nde, "key1", "a");
	}
	protected void GroupBy_2fld_hook() {
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Arg_("key1", "a").Arg_("key2", "b").Arg_("val_int", 1));
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Arg_("key1", "a").Arg_("key2", "b").Arg_("val_int", 2));

		DataRdr rdr = Db_qry_.select_().From_("dbs_group_bys")
			.Cols_("key1", "key2")
			.GroupBy_("key1", "key2")
			.Exec_qry_as_rdr(conn);
		GfoNde nde = GfoNde_.rdr_(rdr);
		GfoNdeTstr.tst_ValsByCol(nde, "key1", "a");
		GfoNdeTstr.tst_ValsByCol(nde, "key2", "b");
	}
	protected void MinMax_hook(boolean min) {
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Arg_("key1", "a").Arg_("val_int", 1));
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Arg_("key1", "a").Arg_("val_int", 2));

		Db_qry__select_cmd qry = Db_qry_.select_().From_("dbs_group_bys")
			.Cols_("key1")
			.GroupBy_("key1");
		int expd = min ? 1 : 2;
		if (min)
			qry.Cols_groupBy_min("val_int", "val_int_func");
		else
			qry.Cols_groupBy_max("val_int", "val_int_func");
		
		DataRdr rdr = qry.Exec_qry_as_rdr(conn);
		GfoNde nde = GfoNde_.rdr_(rdr);
		GfoNdeTstr.tst_ValsByCol(nde, "key1", "a");
		GfoNdeTstr.tst_ValsByCol(nde, "val_int_func", expd);
	}
	protected void Count_hook() {
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Arg_("key1", "a").Arg_("val_int", 10));
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Arg_("key1", "a").Arg_("val_int", 20));

		DataRdr rdr = Db_qry_.select_().From_("dbs_group_bys")
			.Cols_("key1").Cols_groupBy_count("val_int", "val_int_func")
			.GroupBy_("key1")
			.Exec_qry_as_rdr(conn);
		GfoNde nde = GfoNde_.rdr_(rdr);
		GfoNdeTstr.tst_ValsByCol(nde, "key1", "a");
		GfoNdeTstr.tst_ValsByCol(nde, "val_int_func", 2);
	}
	protected void Sum_hook() {
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Arg_("key1", "a").Arg_("val_int", 10));
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Arg_("key1", "a").Arg_("val_int", 20));

		DataRdr rdr = Db_qry_.select_().From_("dbs_group_bys")
			.Cols_("key1").Cols_groupBy_sum("val_int", "val_int_func")
			.GroupBy_("key1")
			.Exec_qry_as_rdr(conn);
		GfoNde nde = GfoNde_.rdr_(rdr);
		GfoNdeTstr.tst_ValsByCol(nde, "key1", "a");
		GfoNdeTstr.tst_ValsByCol(nde, "val_int_func", 30);
	}
}
