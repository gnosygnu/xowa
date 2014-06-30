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
public class AnsiValWtr_tst {
	@Test  public void BldValStr() {
		tst_XtoSqlVal(null, "NULL");
		tst_XtoSqlVal(true, "1");
		tst_XtoSqlVal(false, "0");
		tst_XtoSqlVal(1, "1");
		tst_XtoSqlVal(1.1, "1.1");
		tst_XtoSqlVal("a", "'a'");
		tst_XtoSqlVal("a'b", "'a''b'");
					}
	void tst_XtoSqlVal(Object val, String expd) {
		String_bldr sb = String_bldr_.new_();
		Db_arg prm = new Db_arg("not needed", val);
		valWtr.BldValStr(sb, prm);
		Tfds.Eq(expd, sb.XtoStr());
	}
			Sql_cmd_wtr valWtr = Sql_cmd_wtr_ansi_.default_();
}
