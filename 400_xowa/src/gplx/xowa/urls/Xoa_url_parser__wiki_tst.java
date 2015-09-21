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
package gplx.xowa.urls; import gplx.*; import gplx.xowa.*;
import org.junit.*;
public class Xoa_url_parser__wiki_tst {
	private final Xoa_url_parser_fxt tstr = new Xoa_url_parser_fxt();
	@Test  public void Basic() {
		tstr.Run_parse("en.wikipedia.org/wiki/A").Chk_tid(Xoa_url_.Tid_page).Chk_wiki("en.wikipedia.org").Chk_page("A");
	}
	@Test  public void No_wiki() {	// PURPOSE: no "/wiki/"
		tstr.Run_parse("en.wikipedia.org/A").Chk_wiki("en.wikipedia.org").Chk_page("A");
	}
	@Test  public void Nested() {
		tstr.Run_parse("en.wikipedia.org/wiki/A/b").Chk_wiki("en.wikipedia.org").Chk_page("A/b");
	}
	@Test  public void Slash() {
		tstr.Run_parse("en.wikipedia.org/wiki//A").Chk_wiki("en.wikipedia.org").Chk_page("/A");
		tstr.Run_parse("en.wikipedia.org/wiki/A//b").Chk_wiki("en.wikipedia.org").Chk_page("A//b");
		tstr.Run_parse("en.wikipedia.org/wiki///A").Chk_wiki("en.wikipedia.org").Chk_page("//A");
	}
	@Test  public void Vnt() {
		Xowe_wiki wiki = tstr.Wiki();
		gplx.xowa.parsers.vnts.Xop_vnt_parser_fxt.Vnt_mgr__init(wiki.Lang().Vnt_mgr(), 0, String_.Ary("zh-hans", "zh-hant"));
		tstr.Run_parse("en.wikipedia.org/zh-hans/A").Chk_wiki("en.wikipedia.org").Chk_page("A").Chk_vnt("zh-hans");
	}
}
