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
