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
package gplx.dbs.qrys; import gplx.*; import gplx.dbs.*;
import org.junit.*;
public class Db_qry_sql_tst {
	@Before public void init() {fxt.Clear();} private Db_qry_sql_fxt fxt = new Db_qry_sql_fxt();
	@Test  public void Insert() {
		fxt.Test_qry
		( Db_qry_insert.new_("tbl", "k1", "k2", "k3", "k4", "k5", "k6", "k7", "k8", "k9")
		, Object_.Ary(123, Bool_.Y, 1.23d, 123L, 123f, Byte_ascii.Num_1, "123", DateAdp_.parse_iso8561("1981-04-05T14:30:30"), Decimal_adp_.parse("1.23"))
		, "INSERT INTO tbl (k1, k2, k3, k4, k5, k6, k7, k8, k9) VALUES (123, 1, 1.23, 123, 123, 1, '123', '1981-04-05 14:30:30.000', 1.23)"
		);
	}
	@Test  public void Update() {
		fxt.Test_qry
		( Db_qry_update.new_("tbl", String_.Ary("k1", "k2"), "k3", "k4")
		, Object_.Ary("v3", "v4", "v1", "v2")
		, "UPDATE tbl SET k3='v3', k4='v4' WHERE (k1='v1' AND k2='v2')"
		);
	}
	@Test  public void Delete() {
		fxt.Test_qry
		( Db_qry_delete.new_("tbl", String_.Ary("k1", "k2"))
		, Object_.Ary("v1", "v2")
		, "DELETE FROM tbl WHERE (k1='v1' AND k2='v2')"
		);
	}
}
class Db_qry_sql_fxt {
	public void Clear() {}
	public void Test_qry(Db_qry qry, Object[] vals, String expd) {Tfds.Eq(expd, Db_qry_sql.Gen_sql(qry, vals));}
}
