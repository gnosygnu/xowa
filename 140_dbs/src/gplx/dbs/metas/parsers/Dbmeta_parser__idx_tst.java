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
