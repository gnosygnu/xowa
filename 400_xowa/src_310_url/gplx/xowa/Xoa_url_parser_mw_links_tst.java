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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Xoa_url_parser_mw_links_tst {
	@Before public void init() {fxt.Reset();} private Xoa_url_parser_chkr fxt = new Xoa_url_parser_chkr();
	@Test  public void Title_remove_w() {	// PURPOSE: fix /w/ showing up as seg; DATE:2014-05-30
		fxt.Expd_page("A").Expd_wiki("en.wikipedia.org").Test_parse_w_wiki("http://en.wikipedia.org/w/index.php?title=A");
	}
}
