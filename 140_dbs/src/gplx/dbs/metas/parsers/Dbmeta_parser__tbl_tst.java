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
package gplx.dbs.metas.parsers; import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.dbs.DbmetaFldItm;
import gplx.dbs.DbmetaFldType;
import gplx.dbs.Dbmeta_tbl_itm;
import gplx.dbs.metas.Dbmeta_fld_mgr;
import org.junit.Before;
import org.junit.Test;
public class Dbmeta_parser__tbl_tst {
	@Before public void init() {fxt.Clear();} private Dbmeta_parser__tbl_fxt fxt = new Dbmeta_parser__tbl_fxt();
	@Test public void Test_parse() {
		fxt.Test_parse("CREATE TABLE tbl_1 (fld_1 int, fld_2 int)", fxt.Make_tbl("tbl_1", "fld_1", "fld_2"));
	}
	@Test public void Test_smoke() {
		fxt.Test_parse(StringUtl.ConcatLinesNlSkipLast
		( "CREATE TABLE page"
		, "( page_id integer NOT NULL PRIMARY KEY"
		, ", page_namespace integer NOT NULL"
		, ", page_title varchar(255) NOT NULL"
		, ", page_is_redirect integer NOT NULL"
		, ", page_touched varchar(14) NOT NULL"
		, ", page_len integer NOT NULL"
		, ", page_random_int integer NOT NULL"
		, ", page_text_db_id integer NOT NULL"
		, ", page_html_db_id integer NOT NULL DEFAULT -1"
		, ", page_redirect_id integer NOT NULL DEFAULT -1"
		, ");"
		), fxt.Make_tbl("page", "page_id", "page_namespace", "page_title", "page_is_redirect", "page_touched", "page_len", "page_random_int", "page_text_db_id", "page_html_db_id", "page_redirect_id"));
	}
}
class Dbmeta_parser__tbl_fxt {
	private final Dbmeta_parser__tbl tbl_parser = new Dbmeta_parser__tbl();
	public void Clear() {}
	public Dbmeta_tbl_itm Make_tbl(String tbl_name, String... fld_names) {
		int len = fld_names.length;
		DbmetaFldItm[] flds = new DbmetaFldItm[len];
		for (int i = 0; i < len; ++i)
			flds[i] = new DbmetaFldItm(fld_names[i], DbmetaFldType.ItmInt);
		return Dbmeta_tbl_itm.New(tbl_name, flds);
	}
	public void Test_parse(String src, Dbmeta_tbl_itm expd_tbl) {
		Dbmeta_tbl_itm actl_tbl = tbl_parser.Parse(BryUtl.NewU8(src));
		GfoTstr.EqObj(expd_tbl.Name(), actl_tbl.Name());
		GfoTstr.EqLines(To_str_ary(expd_tbl.Flds()), To_str_ary(actl_tbl.Flds()));
	}
	private static String[] To_str_ary(Dbmeta_fld_mgr fld_mgr) {
		int len = fld_mgr.Len();
		String[] rv = new String[len];
		for (int i = 0; i < len; ++i) {
			rv[i] = fld_mgr.Get_at(i).Name();
		}
		return rv;
	}
}
