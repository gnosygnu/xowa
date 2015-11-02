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
	public static final String 
	  Html__same		= "<a data-xotype='lnki0' href=\"/wiki/A\" id=\"xowa_lnki_2\" title=\"A\">A</a>"
	, Html__diff		= "<a data-xotype='lnki1' href=\"/wiki/A\" id=\"xowa_lnki_2\" title=\"A\">b</a>"
	, Html__trail		= "<a data-xotype='lnki1' href=\"/wiki/A\" id=\"xowa_lnki_2\" title=\"A\">Ab</a>"
	, Html__xwiki		= "<a data-xotype='lnki0' href=\"/site/en.wiktionary.org/wiki/A\" id=\"xowa_lnki_2\" title=\"A\">wikt:A</a>"
	;
	@Before public void init() {fxt.Clear();}
	@Test   public void Same()		{fxt.Test__html("[[A]]"				, Html__same);}
	@Test   public void Diff()		{fxt.Test__html("[[A|b]]"			, Html__diff);}
	@Test   public void Trail()		{fxt.Test__html("[[A]]b"			, Html__trail);}
	@Test   public void Xwiki()	{
		fxt.Parser_fxt().Init_xwiki_add_wiki_and_user_("wikt", "en.wiktionary.org");
		fxt.Test__html("[[wikt:A]]", Html__xwiki);
	}
}
