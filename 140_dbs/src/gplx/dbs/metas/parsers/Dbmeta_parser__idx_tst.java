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
public class Dbmeta_parser__idx_tst {
	@Before public void init() {fxt.Clear();} private final Dbmeta_parser__idx_fxt fxt = new Dbmeta_parser__idx_fxt();
	@Test  public void Unique() {fxt.Test_parse("CREATE UNIQUE INDEX idx_1 ON tbl_1 (fld_1, fld_2, fld_3)"	, fxt.Make_idx(Bool_.Y, "idx_1", "tbl_1", "fld_1", "fld_2", "fld_3"));}
	@Test  public void Normal() {fxt.Test_parse("CREATE INDEX idx_1 ON tbl_1 (fld_1, fld_2, fld_3)"			, fxt.Make_idx(Bool_.N, "idx_1", "tbl_1", "fld_1", "fld_2", "fld_3"));}
	@Test  public void Fld_1()  {fxt.Test_parse("CREATE INDEX idx_1 ON tbl_1 (fld_1)"						, fxt.Make_idx(Bool_.N, "idx_1", "tbl_1", "fld_1"));}
}
class Dbmeta_parser__idx_fxt {
	private final Dbmeta_parser__idx parser = new Dbmeta_parser__idx();
	public void Clear() {}
	public Dbmeta_idx_itm Make_idx(boolean unique, String idx_name, String tbl_name, String... fld_names) {return new Dbmeta_idx_itm(unique, tbl_name, idx_name, Dbmeta_idx_itm.To_fld_ary(fld_names));}
	public void Test_parse(String src, Dbmeta_idx_itm expd) {
		Dbmeta_idx_itm actl = parser.Parse(Bry_.new_u8(src));
		Tfds.Eq_bool(expd.Unique(), actl.Unique());
		Tfds.Eq_str(expd.Name(), actl.Name());
		Tfds.Eq_str(expd.Tbl(), actl.Tbl());
		Tfds.Eq_bool(Bool_.Y, Dbmeta_idx_fld.Ary_eq(expd.Flds, actl.Flds));
	}
}
