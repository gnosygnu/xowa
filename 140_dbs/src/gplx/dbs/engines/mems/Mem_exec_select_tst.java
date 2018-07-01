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
package gplx.dbs.engines.mems; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import org.junit.*; import gplx.dbs.sqls.itms.*;
public class Mem_exec_select_tst {
	private final Mem_db_fxt__single fxt = new Mem_db_fxt__single();
	@Test    public void Basic() {
		fxt.Exec__create_tbl("tbl_1", "fld_1");
		fxt.Exec__insert("tbl_1"
		, String_.Ary("a_1")
		, String_.Ary("a_2")
		, String_.Ary("a_0")
		);

		// select all
		fxt.Test__select(Db_qry_.select_().From_("tbl_1").Cols_all_()
		, String_.Ary("a_1")
		, String_.Ary("a_2")
		, String_.Ary("a_0")
		);

		// order by
		fxt.Test__select(Db_qry_.select_().From_("tbl_1").Cols_all_().Order_("fld_1", Bool_.N)
		, String_.Ary("a_2")
		, String_.Ary("a_1")
		, String_.Ary("a_0")
		);

		// offset
		fxt.Test__select(Db_qry_.select_().From_("tbl_1").Cols_all_().Offset_(1)
		, String_.Ary("a_2")
		, String_.Ary("a_0")
		);

		// limit
		fxt.Test__select(Db_qry_.select_().From_("tbl_1").Cols_all_().Limit_(2)
		, String_.Ary("a_1")
		, String_.Ary("a_2")
		);
	}
	@Test    public void Join__single() {
		fxt.Exec__create_tbl("tbl_1", "fld_a", "fld_1a");
		fxt.Exec__create_tbl("tbl_2", "fld_a", "fld_2a");
		fxt.Exec__insert("tbl_1"
		, String_.Ary("a_0", "1a_0")
		, String_.Ary("a_1", "1a_1")
		, String_.Ary("a_2", "1a_2")
		);
		fxt.Exec__insert("tbl_2"
		, String_.Ary("a_0", "2a_0")
		, String_.Ary("a_2", "2a_2")
		);

		// inner join
		fxt.Test__select(Db_qry_.select_()
		.Cols_w_tbl_("t1", "fld_a", "fld_1a").Cols_w_tbl_("t2", "fld_2a")
		.From_("tbl_1", "t1")
		.	Join_("tbl_2", "t2", Db_qry_.New_join__join("fld_a", "t1", "fld_a"))
		, String_.Ary("a_0", "1a_0", "2a_0")
		, String_.Ary("a_2", "1a_2", "2a_2")
		);

		// left join
		fxt.Test__select(Db_qry_.select_()
		.Cols_w_tbl_("t1", "fld_a", "fld_1a").Cols_w_tbl_("t2", "fld_2a")
		.From_("tbl_1", "t1")
		.	Join_(Sql_tbl_itm.Tid__left, Sql_tbl_itm.Db__null, "tbl_2", "t2", Db_qry_.New_join__join("fld_a", "t1", "fld_a"))
		, String_.Ary("a_0", "1a_0", "2a_0")
		, String_.Ary("a_1", "1a_1", Db_null.Null_str)
		, String_.Ary("a_2", "1a_2", "2a_2")
		);
	}
	@Test    public void Join__many() {
		fxt.Exec__create_tbl("tbl_1", "fld_a", "fld_1a");
		fxt.Exec__create_tbl("tbl_2", "fld_a", "fld_2a");
		fxt.Exec__insert("tbl_1"
		, String_.Ary("a_0", "1a_0")
		, String_.Ary("a_1", "1a_1")
		);
		fxt.Exec__insert("tbl_2"
		, String_.Ary("a_0", "2a_0")
		, String_.Ary("a_0", "2a_1")
		);

		// inner join
		fxt.Test__select(Db_qry_.select_()
		.Cols_w_tbl_("t1", "fld_a", "fld_1a").Cols_w_tbl_("t2", "fld_2a")
		.From_("tbl_1", "t1")
		.	Join_("tbl_2", "t2", Db_qry_.New_join__join("fld_a", "t1", "fld_a"))
		, String_.Ary("a_0", "1a_0", "2a_0")
		, String_.Ary("a_0", "1a_0", "2a_1")
		);

		// left join
		fxt.Test__select(Db_qry_.select_()
		.Cols_w_tbl_("t1", "fld_a", "fld_1a").Cols_w_tbl_("t2", "fld_2a")
		.From_("tbl_1", "t1")
		.	Join_(Sql_tbl_itm.Tid__left, Sql_tbl_itm.Db__null, "tbl_2", "t2", Db_qry_.New_join__join("fld_a", "t1", "fld_a"))
		, String_.Ary("a_0", "1a_0", "2a_0")
		, String_.Ary("a_0", "1a_0", "2a_1")
		, String_.Ary("a_1", "1a_1", Db_null.Null_str)
		);
	}
}
class Mem_db_fxt__single {
	private final Mem_db_fxt mem_fxt;
	private final Db_conn conn;
	public Mem_db_fxt__single() {
		this.mem_fxt = new Mem_db_fxt();
		this.conn = mem_fxt.Make_conn("mem/test.db");
	}
	public void Exec__create_tbl	(String tbl, String... fld_names)		{mem_fxt.Exec__create_tbl(conn, tbl, fld_names);}
	public void Exec__insert		(String tbl, String[]... rows)		{mem_fxt.Exec__insert(conn, tbl, rows);}
	public void Test__select		(Db_qry qry, String[]... expd)		{mem_fxt.Test__select(conn, qry, expd);}
}
