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
package gplx.xowa.xtns.wdatas; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Wdata_pf_wbreponame_tst {
	@Before public void init() {fxt.Clear();} private Wdata_pf_wbreponame_fxt fxt = new Wdata_pf_wbreponame_fxt();
	@Test   public void Basic() {
		fxt.Test_parse("{{wbreponame}}", "Wikidata");
	}
}
class Wdata_pf_wbreponame_fxt {
	public Wdata_pf_wbreponame_fxt Clear() {
		if (parser_fxt == null) {
			parser_fxt = new Xop_fxt();
		}
		return this;
	}	private Xop_fxt parser_fxt;
	public void Test_parse(String raw, String expd) {
		parser_fxt.Test_html_full_str(raw, expd);
	}
}
