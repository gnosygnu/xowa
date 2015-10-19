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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*; import gplx.ios.*; import gplx.xowa.wikis.nss.*;
public class Xoa_css_extractor_wiki_tst {
	@Before public void init() {fxt.Clear();} private Xoa_css_extractor_fxt fxt = new Xoa_css_extractor_fxt();
	@Test   public void Css_wiki_generate() {
		fxt.Init_page(Xow_ns_.Id_mediawiki, "Common.css"					, "css_0");
		fxt.Init_page(Xow_ns_.Id_mediawiki, "Vector.css"					, "css_1");
		fxt.Exec_css_wiki_setup();
		fxt.Test_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/xowa_wiki.css", String_.Concat_lines_nl
		(	"/*XOWA:MediaWiki:Common.css*/"
		,	"css_0"
		,	""
		,	"/*XOWA:MediaWiki:Vector.css*/"
		,	"css_1"
		));
	}
	@Test   public void Css_wiki_missing() {
		fxt.Exec_css_wiki_setup();
		fxt.Test_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/xowa_wiki.css", "");
	}
	@Test  public void Css_wiki_tab() {	// PURPOSE: swap out &#09; for xdat files
		fxt.Init_page(Xow_ns_.Id_mediawiki, "Common.css"					, "a&#09;b");
		fxt.Exec_css_wiki_setup();
		fxt.Test_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/xowa_wiki.css", String_.Concat_lines_nl
		(	"/*XOWA:MediaWiki:Common.css*/"
		,	"a\tb"
		));
	}
}
