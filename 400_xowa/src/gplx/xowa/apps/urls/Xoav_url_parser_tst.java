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
package gplx.xowa.apps.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import org.junit.*;
public class Xoav_url_parser_tst {		
	@Before public void init() {fxt.Clear();} private final Xoav_url_parser_fxt fxt = new Xoav_url_parser_fxt();
	@Test   public void Page() {
		fxt.Exec_parse_xo_href("http:/wiki/Earth").Test_wiki("en.wikipedia.org").Test_page("Earth");
	}
	@Test   public void Site() {
		fxt.Exec_parse_xo_href("http:/site/en.wikipedia.org/wiki/Earth").Test_wiki("en.wikipedia.org").Test_page("Earth");
	}
}
class Xoav_url_parser_fxt {
	private Xoav_url_parser url_parser = new Xoav_url_parser(); private Xoav_url url = new Xoav_url();
	public void Clear() {
		cur_wiki = Bry_.new_a7("en.wikipedia.org");
		url.Clear();
	}
	public Xoav_url_parser_fxt Init_cur_wiki(String v) {cur_wiki = Bry_.new_u8(v); return this;} private byte[] cur_wiki;
	public Xoav_url_parser_fxt Test_wiki(String v) {Tfds.Eq_bry(Bry_.new_u8(v), url.Wiki_bry()); return this;}
	public Xoav_url_parser_fxt Test_page(String v) {Tfds.Eq_bry(Bry_.new_u8(v), url.Page_bry()); return this;}
	public Xoav_url_parser_fxt Exec_parse_xo_href(String src_str) {
		byte[] src_bry = Bry_.new_u8(src_str);
		url_parser.Parse_xo_href(url, src_bry, cur_wiki);
		return this;
	}
}
