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
package gplx.xowa.bldrs.sql_dumps;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.utls.StringUtl;
import org.junit.*;
public class Xosql_tbl_parser__tst {
	private final Xosql_tbl_parser__fxt fxt = new Xosql_tbl_parser__fxt();
	@Test public void Unique_key() {
		fxt.Exec__parse(StringUtl.ConcatLinesNl
		( "ignore"
		, "CREATE TABLE tbl_0 ("	
		, "  `fld_2` int,"
		, "  `fld_1` int,"
		, "  `fld_0` int,"
		, "  UNIQUE KEY idx_0 (fld_2)"
		, ") ENGINE;"
		));
		fxt.Test__count(3);
		fxt.Test__get("fld_0",  2);
		fxt.Test__get("fld_1",  1);
		fxt.Test__get("fld_2",  0);
		fxt.Test__get("fld_3", -1);
	}
	@Test public void Primary_key() {
		fxt.Test__extract(StringUtl.ConcatLinesNl
		( "ignore"
		, "CREATE TABLE tbl_0 ("	
		, "  `fld_0` int,"
		, "  PRIMARY KEY idx_0 (fld_2)"
		, ") ENGINE;"
		), StringUtl.ConcatLinesNl
		( "  `fld_0` int,"
		));
	}
	@Test public void Key() {
		fxt.Test__extract(StringUtl.ConcatLinesNl
		( "ignore"
		, "CREATE TABLE tbl_0 ("	
		, "  `fld_0` int,"
		, "  KEY idx_0 (fld_2)"
		, ") ENGINE;"
		), StringUtl.ConcatLinesNl
		( "  `fld_0` int,"
		));
	}
	@Test public void Unique_key__key__primary_key() {
		fxt.Test__extract(StringUtl.ConcatLinesNl
		( "ignore"
		, "CREATE TABLE tbl_0 ("	
		, "  `fld_0` int,"
		, "  UNIQUE KEY idx_0 (fld_2),"
		, "  KEY idx_0 (fld_2),"
		, "  PRIMARY KEY idx_0 (fld_2),"
		, ") ENGINE;"
		), StringUtl.ConcatLinesNl
		( "  `fld_0` int,"
		));
	}
}
class Xosql_tbl_parser__fxt {
	private final Xosql_tbl_parser parser = new Xosql_tbl_parser();
	private Ordered_hash tbl_flds;
	public void Exec__parse(String v) {this.tbl_flds = parser.Parse(BryUtl.NewA7(v));}
	public void Test__count(int expd) {GfoTstr.Eq(expd, tbl_flds.Len());}
	public void Test__get(String key, int expd) {
		Xosql_fld_itm actl_itm = (Xosql_fld_itm)tbl_flds.GetByOrNull(BryUtl.NewU8(key));
		GfoTstr.Eq(expd, actl_itm == null ? BryFind.NotFound : actl_itm.Idx());
	}
	public void Test__extract(String raw, String expd) {
		GfoTstr.EqLines(expd, parser.Extract_flds(BryUtl.NewU8(raw)), "extract");
	}
}
