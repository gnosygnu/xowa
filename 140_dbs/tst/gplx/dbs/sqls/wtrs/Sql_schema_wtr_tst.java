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
public class Sql_schema_wtr_tst {
	@Before public void setup() {} private final Sql_schema_wtr_fxt fxt = new Sql_schema_wtr_fxt();
	@Test public void Idx_unique() {
		fxt.Test_create_idx(Dbmeta_idx_itm.new_unique_by_tbl("tbl_name", "idx_name", "fld_1", "fld_2")
		, "CREATE UNIQUE INDEX IF NOT EXISTS tbl_name__idx_name ON tbl_name (fld_1, fld_2);"
		);
	}
	@Test public void Tbl_basic() {
		DbmetaFldList flds = new DbmetaFldList();
		flds.AddIntPkey("fld_int_pkey");
		flds.AddBool("fld_bool");
		flds.AddShort("fld_short");
		flds.AddInt("fld_int");
		flds.AddLong("fld_long");
		flds.AddFloat("fld_float");
		flds.AddDouble("fld_double");
		flds.AddStr("fld_str", 123);
		flds.AddText("fld_text");
		flds.AddBry("fld_bry");
		fxt.Test_create_tbl(Dbmeta_tbl_itm.New("tbl_name", flds.ToFldAry())
		, String_.Concat_lines_nl_skip_last
		( "CREATE TABLE IF NOT EXISTS tbl_name"
		, "( fld_int_pkey integer NOT NULL PRIMARY KEY"
		, ", fld_bool boolean NOT NULL"
		, ", fld_short smallint NOT NULL"
		, ", fld_int integer NOT NULL"
		, ", fld_long bigint NOT NULL"
		, ", fld_float float NOT NULL"
		, ", fld_double double NOT NULL"
		, ", fld_str varchar(123) NOT NULL"
		, ", fld_text text NOT NULL"
		, ", fld_bry blob NOT NULL"
		, ");"
		));
	}
	@Test public void Tbl_alter_tbl_add() {
		DbmetaFldList flds = new DbmetaFldList();
		flds.AddIntDflt("fld_int", -1);
		flds.AddStrDflt("fld_str", 255, "a");
		fxt.Test_alter_tbl_add("tbl_name", flds.GetByOrNull("fld_int"), "ALTER TABLE tbl_name ADD fld_int integer NOT NULL DEFAULT -1;");
		fxt.Test_alter_tbl_add("tbl_name", flds.GetByOrNull("fld_str"), "ALTER TABLE tbl_name ADD fld_str varchar(255) NOT NULL DEFAULT 'a';");
	}
}
class Sql_schema_wtr_fxt {
	private Sql_schema_wtr sqlbldr = SqlQryWtrUtl.NewSqlite().Schema_wtr();
	public void Test_create_idx(Dbmeta_idx_itm idx, String expd) {Tfds.Eq(expd, sqlbldr.Bld_create_idx(idx));}
	public void Test_create_tbl(Dbmeta_tbl_itm tbl, String expd) {Tfds.Eq_str_lines(expd, sqlbldr.Bld_create_tbl(tbl));}
	public void Test_alter_tbl_add(String tbl, DbmetaFldItm fld, String expd) {Tfds.Eq_str_lines(expd, sqlbldr.Bld_alter_tbl_add(tbl, fld));}
}
