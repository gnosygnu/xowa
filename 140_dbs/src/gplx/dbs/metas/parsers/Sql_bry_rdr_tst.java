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
public class Sql_bry_rdr_tst {
	@Before public void init() {fxt.Clear();} private Sql_bry_rdr_fxt fxt = new Sql_bry_rdr_fxt();
	@Test   public void Skip_ws() {
		fxt.Test_skip_ws("a", 0);				// char
		fxt.Test_skip_ws("\ta", 1);				// tab
		fxt.Test_skip_ws("\na", 1);				// \n
		fxt.Test_skip_ws("\ra", 1);				// \r
		fxt.Test_skip_ws(" a", 1);				// space
		fxt.Test_skip_ws("\t\n\r a", 4);		// multi
		fxt.Test_skip_ws("", 0);				// eos
	}
	@Test   public void Read_sql_identifier() {
		fxt.Test_read_sql_identifier("a", "a");				// one
		fxt.Test_read_sql_identifier("abc_1", "abc_1");		// many
		fxt.Test_read_sql_identifier("[abc_1]", "abc_1");	// bracket
		fxt.Test_read_sql_identifier(" a ", "a");			// ws
		fxt.Test_read_sql_identifier("", null);				// eos
		fxt.Test_read_sql_identifier("!@#", null);			// sym
	}
}
class Sql_bry_rdr_fxt {
	private final Sql_bry_rdr rdr = new Sql_bry_rdr();
	public void Clear() {}
	public void Test_skip_ws(String src, int expd_pos) {
		rdr.Init(Bry_.new_u8(src));
		rdr.Skip_ws();
		Tfds.Eq(expd_pos, rdr.Pos());
	}
	public void Test_read_sql_identifier(String src, String expd) {
		rdr.Init(Bry_.new_u8(src));
		Tfds.Eq(expd, String_.new_u8(rdr.Read_sql_identifier()));
	}
}
