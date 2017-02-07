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
package gplx.xowa.parsers.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_hr_lxr_para_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_y_();} private final Xop_fxt fxt = new Xop_fxt();
	@Test   public void Bos()	{	// PURPOSE: check that bos rendered correctly; DATE:2014-04-18
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "----"
		, "a"
		), String_.Concat_lines_nl_skip_last
		( "<hr/>"
		, ""
		, "<p>a"
		, "</p>"
		));
	}
	@Test   public void Multiple()	{	// PURPOSE.fix: hr disables para for rest of page; ca.b:Xarxes; DATE:2014-04-18
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "a"
		, "----"
		, "b"
		, ""
		, ""
		, "c"
		), String_.Concat_lines_nl_skip_last
		( "<p>a"
		, "</p>"
		, "<hr/>"
		, ""
		, "<p>b"
		, "</p>"
		, ""
		, "<p><br/>"
		, "c"
		, "</p>"
		));
	}
}
