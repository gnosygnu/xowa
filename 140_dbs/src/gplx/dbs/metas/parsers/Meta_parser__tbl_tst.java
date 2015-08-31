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
package gplx.dbs.metas.parsers; import gplx.*; import gplx.dbs.*; import gplx.dbs.metas.*;
import org.junit.*;
public class Meta_parser__tbl_tst {
	@Before public void init() {fxt.Clear();} private Meta_parser__tbl_fxt fxt = new Meta_parser__tbl_fxt();
	@Test  public void Test_parse() {
		fxt.Test_parse("CREATE TABLE tbl_1 (fld_1 int, fld_2 int)", fxt.Make_tbl("tbl_1", "fld_1", "fld_2"));
	}
	@Test  public void Test_smoke() {
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
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
class Meta_parser__tbl_fxt {
	private final Meta_parser__tbl tbl_parser = new Meta_parser__tbl();
	public void Clear() {}
	public Meta_tbl_itm Make_tbl(String tbl_name, String... fld_names) {
		Meta_tbl_itm rv = new Meta_tbl_itm(tbl_name, "NONE");
		int len = fld_names.length;
		for (int i = 0; i < len; ++i) {
			rv.Flds().Add(new Meta_fld_itm(fld_names[i], new Meta_type_itm(Db_meta_fld.Tid_int, Sqlite_tid.Tid_int, Bry_.new_a7("int"), Int_.Min_value, Int_.Min_value)));
		}
		return rv;
	}
	public void Test_parse(String src, Meta_tbl_itm expd_tbl) {
		Meta_tbl_itm actl_tbl = tbl_parser.Parse(Bry_.new_u8(src));
		Tfds.Eq(expd_tbl.Name(), actl_tbl.Name());
		Tfds.Eq_ary_str(To_str_ary(expd_tbl.Flds()), To_str_ary(actl_tbl.Flds()));
	}
	private static String[] To_str_ary(Meta_fld_mgr fld_mgr) {
		int len = fld_mgr.Len();
		String[] rv = new String[len];
		for (int i = 0; i < len; ++i) {
			rv[i] = fld_mgr.Get_at(i).Name();
		}
		return rv;
	}
}
