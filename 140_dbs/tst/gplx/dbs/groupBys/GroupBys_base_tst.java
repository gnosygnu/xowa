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
package gplx.dbs.groupBys; import gplx.*; import gplx.dbs.*;
import org.junit.*; import gplx.dbs.qrys.*; import gplx.core.gfo_ndes.*; import gplx.core.stores.*;
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
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Val_str("key1", "a").Val_int("val_int", 1));
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Val_str("key1", "a").Val_int("val_int", 2));

		DataRdr rdr = conn.Exec_qry_as_old_rdr
			(Db_qry_.select_().From_("dbs_group_bys")
			.Cols_("key1")
			.GroupBy_("key1"));
		GfoNde nde = GfoNde_.rdr_(rdr);
		GfoNdeTstr.tst_ValsByCol(nde, "key1", "a");
	}
	protected void GroupBy_2fld_hook() {
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Val_str("key1", "a").Val_str("key2", "b").Val_int("val_int", 1));
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Val_str("key1", "a").Val_str("key2", "b").Val_int("val_int", 2));

		DataRdr rdr = conn.Exec_qry_as_old_rdr
			(Db_qry_.select_().From_("dbs_group_bys")
			.Cols_("key1", "key2")
			.GroupBy_("key1", "key2"));
		GfoNde nde = GfoNde_.rdr_(rdr);
		GfoNdeTstr.tst_ValsByCol(nde, "key1", "a");
		GfoNdeTstr.tst_ValsByCol(nde, "key2", "b");
	}
	protected void MinMax_hook(boolean min) {
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Val_str("key1", "a").Val_int("val_int", 1));
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Val_str("key1", "a").Val_int("val_int", 2));

		Db_qry__select_cmd qry = Db_qry_.select_().From_("dbs_group_bys")
			.Cols_("key1")
			.GroupBy_("key1");
		int expd = min ? 1 : 2;
		if (min)
			qry.Cols_groupBy_min("val_int", "val_int_func");
		else
			qry.Cols_groupBy_max("val_int", "val_int_func");
		
		DataRdr rdr = conn.Exec_qry_as_old_rdr(qry);
		GfoNde nde = GfoNde_.rdr_(rdr);
		GfoNdeTstr.tst_ValsByCol(nde, "key1", "a");
		GfoNdeTstr.tst_ValsByCol(nde, "val_int_func", expd);
	}
	protected void Count_hook() {
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Val_str("key1", "a").Val_int("val_int", 10));
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Val_str("key1", "a").Val_int("val_int", 20));

		DataRdr rdr = conn.Exec_qry_as_old_rdr
			(Db_qry_.select_().From_("dbs_group_bys")
			.Cols_("key1").Cols_groupBy_count("val_int", "val_int_func")
			.GroupBy_("key1"));
		GfoNde nde = GfoNde_.rdr_(rdr);
		GfoNdeTstr.tst_ValsByCol(nde, "key1", "a");
		GfoNdeTstr.tst_ValsByCol(nde, "val_int_func", 2);
	}
	protected void Sum_hook() {
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Val_str("key1", "a").Val_int("val_int", 10));
		conn.Exec_qry(Db_qry_.insert_("dbs_group_bys").Val_str("key1", "a").Val_int("val_int", 20));

		DataRdr rdr = conn.Exec_qry_as_old_rdr
			(Db_qry_.select_().From_("dbs_group_bys")
			.Cols_("key1").Cols_groupBy_sum("val_int", "val_int_func")
			.GroupBy_("key1"));
		GfoNde nde = GfoNde_.rdr_(rdr);
		GfoNdeTstr.tst_ValsByCol(nde, "key1", "a");
		GfoNdeTstr.tst_ValsByCol(nde, "val_int_func", 30);
	}
}
