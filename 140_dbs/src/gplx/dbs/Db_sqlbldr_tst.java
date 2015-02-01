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
package gplx.dbs; import gplx.*;
import org.junit.*;
public class Db_sqlbldr_tst {
	@Before public void setup() {} private final Db_sqlbldr_fxt fxt = new Db_sqlbldr_fxt();
	@Test  public void Idx_unique() {
		fxt.Test_create_idx(Db_meta_idx.new_unique("tbl_name", "idx_name", "fld_1", "fld_2")
		, "CREATE UNIQUE INDEX IF NOT EXISTS tbl_name__idx_name (fld_1, fld_2);"
		);
	}
	@Test  public void Tbl_basic() {
		Db_meta_fld_list flds = Db_meta_fld_list.new_();
		flds.Add_int_pkey("fld_int_pkey");
		flds.Add_bool("fld_bool");
		flds.Add_short("fld_short");
		flds.Add_int("fld_int");
		flds.Add_long("fld_long");
		flds.Add_float("fld_float");
		flds.Add_double("fld_double");
		flds.Add_str("fld_str", 123);
		flds.Add_text("fld_text");
		flds.Add_bry("fld_bry");
		fxt.Test_create_tbl(Db_meta_tbl.new_("tbl_name", flds.To_fld_ary())
		, String_.Concat_lines_nl_skip_last
		( "CREATE TABLE tbl_name"
		, "( fld_int_pkey int NOT NULL PRIMARY KEY"
		, ", fld_bool boolean NOT NULL"
		, ", fld_short smallint NOT NULL"
		, ", fld_int int NOT NULL"
		, ", fld_long bigint NOT NULL"
		, ", fld_float float NOT NULL"
		, ", fld_double double NOT NULL"
		, ", fld_str varchar(123) NOT NULL"
		, ", fld_text text NOT NULL"
		, ", fld_bry blob NOT NULL"
		, ");"
		));
	}
}
class Db_sqlbldr_fxt {
	private Db_sqlbldr__sqlite sqlbldr = new Db_sqlbldr__sqlite();
	public void Test_create_idx(Db_meta_idx idx, String expd) {
		Tfds.Eq(expd, sqlbldr.Bld_create_idx(idx));
	}
	public void Test_create_tbl(Db_meta_tbl tbl, String expd) {
		Tfds.Eq_str_lines(expd, sqlbldr.Bld_create_tbl(tbl));
	}
}
