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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import org.junit.*;
public class Xop_parser__tst {
	@Before public void init() {fxt.Clear();} private Xop_parser__fxt fxt = new Xop_parser__fxt();
	@Test  public void Para_y() {
		fxt.Test_parse_to_html(String_.Concat_lines_nl_skip_last
		( "a"
		, ""
		, "b"
		), true, String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		, ""
		, "<p>b"
		, "</p>"
		, ""
		));
	}
	@Test  public void Para_n() {
		fxt.Test_parse_to_html(String_.Concat_lines_nl_skip_last
		( "a"
		, ""
		, "b"
		), false, String_.Concat_lines_nl_skip_last
		( "a"
		, "b"
		));
	}
}
class Xop_parser__fxt {
	private Xop_fxt fxt = new Xop_fxt();
	private Bry_bfr bfr = Bry_bfr.reset_(255);
	public void Clear() {
		fxt.Reset();
	}
	public void Test_parse_to_html(String raw, boolean para_enabled, String expd)  {
		byte[] raw_bry = Bry_.new_u8(raw);
		fxt.Wiki().Parser_mgr().Main().Parse_text_to_html(bfr, fxt.Page(), para_enabled, raw_bry);
		Tfds.Eq(expd, bfr.To_str_and_clear());
	}
}
