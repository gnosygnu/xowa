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
public class Xow_url_parser__wiki_tst {
	private final    Xow_url_parser_fxt tstr = new Xow_url_parser_fxt();
	@Test  public void Basic() {
		tstr.Exec__parse("en.wikipedia.org/wiki/A").Test__tid(Xoa_url_.Tid_page).Test__wiki("en.wikipedia.org").Test__page("A");
	}
	@Test  public void No_wiki() {	// PURPOSE: no "/wiki/"
		tstr.Exec__parse("en.wikipedia.org/A").Test__wiki("en.wikipedia.org").Test__page("A");
	}
	@Test  public void Nested() {
		tstr.Exec__parse("en.wikipedia.org/wiki/A/b").Test__wiki("en.wikipedia.org").Test__page("A/b");
	}
	@Test  public void Slash() {
		tstr.Exec__parse("en.wikipedia.org/wiki//A").Test__wiki("en.wikipedia.org").Test__page("/A");
		tstr.Exec__parse("en.wikipedia.org/wiki/A//b").Test__wiki("en.wikipedia.org").Test__page("A//b");
		tstr.Exec__parse("en.wikipedia.org/wiki///A").Test__wiki("en.wikipedia.org").Test__page("//A");
	}
	@Test  public void Vnt() {
		Xowe_wiki wiki = tstr.Wiki();
		gplx.xowa.langs.vnts.Xol_vnt_regy_fxt.Init__vnt_mgr(wiki.Lang().Vnt_mgr(), 0, String_.Ary("zh-hans", "zh-hant"));
		tstr.Exec__parse("en.wikipedia.org/zh-hans/A").Test__wiki("en.wikipedia.org").Test__page("A").Test__vnt("zh-hans");
	}
}
