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
public class Random_selection_xnde_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {fxt.Reset();}
	@Test   public void Basic() {
		Random_selection_xnde.Rnd_test = 2;
		fxt.Test__parse__tmpl_to_html(String_.Concat_lines_nl_skip_last
		( "<choose before=\"bgn_''\" after=\"''_end\">"
		, "<option>A</option>"
		, "<option>B</option>"
		, "<option>C</option>"
		, "</choose>"
		), "bgn_<i>B</i>_end");
		Random_selection_xnde.Rnd_test = -1;
	}
	@Test   public void Choicetemplate() {
		Random_selection_xnde.Rnd_test = 2;
		fxt.Init_page_create("Template:Tmpl", "bgn_''{{{1}}}''_end");
		fxt.Test__parse__tmpl_to_html(String_.Concat_lines_nl_skip_last
		( "<choose>"
		, "<option>A</option>"
		, "<option>B</option>"
		, "<option>C</option>"
		, "<choicetemplate>Tmpl</choicetemplate>"
		, "</choose>"
		), "bgn_<i>B</i>_end");
		Random_selection_xnde.Rnd_test = -1;
	}
}
