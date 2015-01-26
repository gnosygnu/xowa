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
public class Db_stmt_sql_tst {
	@Before public void init() {}
	@Test    public void Basic() {
		Db_stmt_sql stmt = new Db_stmt_sql();
		stmt.Parse(null, "UPDATE tbl_0 SET col_0 = ? WHERE col_1 = ?");
		stmt.Add("1");
		stmt.Add("2");
		Tfds.Eq("UPDATE tbl_0 SET col_0 = 1 WHERE col_1 = 2", stmt.Xto_sql());
	}
}
