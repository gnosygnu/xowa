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
package gplx.dbs.bulks; import gplx.*; import gplx.dbs.*;
import org.junit.*; import gplx.core.tests.*; import gplx.dbs.metas.*;
public class Db_tbl_copy_tst {
	private final    Db_tbl_copy_fxt fxt = new Db_tbl_copy_fxt();
	@Test   public void Basic() {
		fxt.Test__bld_sql(fxt.Make_tbl("tbl_1", Dbmeta_fld_itm.new_int("fld_1"), Dbmeta_fld_itm.new_int("fld_2")), 
		String_.Concat_lines_nl_skip_last
		( "INSERT INTO trg"
		, "(fld_1, fld_2)"
		, "SELECT"
		, " fld_1, fld_2"
		, "FROM <src_db>src"
		));
	}
}
class Db_tbl_copy_fxt {
	private final    Db_tbl_copy mgr = new Db_tbl_copy();
	public Dbmeta_tbl_itm Make_tbl(String name, Dbmeta_fld_itm... flds) {return Dbmeta_tbl_itm.New(name, flds);}
	public void Test__bld_sql(Dbmeta_tbl_itm tbl, String expd) {
		Gftest.Eq__ary__lines(expd, mgr.Bld_sql(tbl, "src", "trg"), "sql");
	}
}
