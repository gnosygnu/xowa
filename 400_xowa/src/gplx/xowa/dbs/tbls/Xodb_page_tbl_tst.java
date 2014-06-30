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
package gplx.xowa.dbs.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
import org.junit.*; import gplx.xowa.bldrs.*; import gplx.xowa.ctgs.*; import gplx.dbs.*; import gplx.xowa.dbs.tbls.*;
public class Xodb_page_tbl_tst {
	private Xodb_page_tbl_fxt fxt = new Xodb_page_tbl_fxt();
	@Test  public void Find_search_end() {
		fxt.Test_find_search_end("ab", "ac");
		fxt.Test_find_search_end("ab%", "ac%");
	}
}
class Xodb_page_tbl_fxt {
	public void Test_find_search_end(String val, String expd) {Tfds.Eq(expd, String_.new_utf8_(Xodb_page_tbl.Find_search_end(Bry_.new_utf8_(val))));}
}
