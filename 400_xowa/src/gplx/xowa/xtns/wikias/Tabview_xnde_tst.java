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
package gplx.xowa.xtns.wikias; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Tabview_xnde_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {fxt.Reset();}
	@Test   public void Basic() {
		fxt.Init_page_create("A", "''a''");
		fxt.Init_page_create("B", "''b''");
		fxt.Test__parse__tmpl_to_html(String_.Concat_lines_nl_skip_last
		( "<tabview id='test'>"
		, "A|tab1"
		, "B|tab2||true"
		, "</tabview>"
		), String_.Concat_lines_nl_skip_last
		( "<div id=\"tabber-test\" class=\"tabber\">"
		,   "<div class=\"tabbertab\" title=\"tab1\">"
		,     "<p><i>a</i></p>"
		,   "</div>"
		,   "<div class=\"tabbertab\" title=\"tab2\" data-active=\"true\">"
		,     "<p><i>b</i></p>"
		,   "</div>"
		, "</div>"
		));
	}
}
