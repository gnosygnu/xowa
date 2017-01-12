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
package gplx.xowa.parsers.mws.tables; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.mws.*;
import org.junit.*;
public class Xomw_table_wkr__tst {
	private final    Xomw_table_wkr__fxt fxt = new Xomw_table_wkr__fxt();
	@Test  public void Table() {
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "{|"
		, "|-"
		, "|a"
		, "|}"
		), String_.Concat_lines_nl_skip_last
		( "{|"
		, "|-"
		, "|a"
		, "|}"
		));
	}		
}
class Xomw_table_wkr__fxt {
	private final    Xomw_table_wkr wkr = new Xomw_table_wkr();
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		byte[] actl = wkr.Do_table_stuff(src_bry);
		Tfds.Eq_str_lines(expd, String_.new_u8(actl), src_str);
	}
}
