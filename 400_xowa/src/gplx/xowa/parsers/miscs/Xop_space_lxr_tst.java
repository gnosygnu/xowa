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
public class Xop_space_lxr_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {fxt.Reset();}
	@After public void term() {fxt.Init_para_n_();}
	@Test   public void Toc_basic() {	// PURPOSE: make sure nbsp char is not converted to space; PAGE:en.w:Macedonian–Carthaginian_Treaty; DATE:2014-06-07
		fxt.Init_para_y_();
		fxt.Test_parse_page_all_str("     a", String_.Concat_lines_nl_skip_last	// NOTE: ws is actually nbsp;
		( "<p>     a"	// should be <p> not <pre>
		, "</p>"
		, ""
		));	
	}
}
