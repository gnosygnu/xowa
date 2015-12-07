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
package gplx.xowa.htmls.core.wkrs.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.makes.tests.*;
public class Xoh_lnki_html__hdump__tst {
	private final Xoh_make_fxt fxt = new Xoh_make_fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Same()		{fxt.Test__html("[[A]]"				, "<a href='/wiki/A' title='A'>A</a>");}
	@Test   public void Diff()		{fxt.Test__html("[[A|b]]"			, "<a href='/wiki/A' title='A'>b</a>");}
	@Test   public void Trail()		{fxt.Test__html("[[A]]b"			, "<a href='/wiki/A' title='A'>Ab</a>");}
	@Test   public void Xwiki()	{
		fxt.Parser_fxt().Init_xwiki_add_wiki_and_user_("wikt", "en.wiktionary.org");
		fxt.Test__html("[[wikt:a]]", "<a href='https://en.wiktionary.org/wiki/a' title='a'>wikt:a</a>");
	}
	@Test   public void Anch()		{fxt.Test__html("[[#a]]"			, "<a href='#a'>#a</a>");}
}
