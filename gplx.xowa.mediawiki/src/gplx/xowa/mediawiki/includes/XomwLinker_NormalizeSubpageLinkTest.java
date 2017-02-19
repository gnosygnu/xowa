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
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
import org.junit.*; import gplx.core.tests.*;
public class XomwLinker_NormalizeSubpageLinkTest {
	private final    XomwLinker_NormalizeSubpageLinkFxt fxt = new XomwLinker_NormalizeSubpageLinkFxt();
	@Test  public void None()                {fxt.Test__normalize_subpage_link("A/B/C"          , "Z"             , ""    , "Z"          , "");}
	@Test  public void Hash()                {fxt.Test__normalize_subpage_link("A/B/C"          , "/Y#Z"          , ""    , "A/B/C/Y#Z"  , "/Y#Z");}
	@Test  public void Slash__basic()        {fxt.Test__normalize_subpage_link("A/B/C"          , "/Z"            , ""    , "A/B/C/Z"    , "/Z");}
	@Test  public void Slash__slash()        {fxt.Test__normalize_subpage_link("A/B/C"          , "/Z/"           , ""    , "A/B/C/Z"    , "Z");}
	@Test  public void Dot2__empty()         {fxt.Test__normalize_subpage_link("A/B/C"          , "../"           , ""    , "A/B"        , "");}
	@Test  public void Dot2__many()          {fxt.Test__normalize_subpage_link("A/B/C"          , "../../Z"       , "z1"  , "A/Z"        , "z1");}
	@Test  public void Dot2__trailing()      {fxt.Test__normalize_subpage_link("A/B/C"          , "../../Z/"      , ""    , "A/Z"        , "Z");}
}
class XomwLinker_NormalizeSubpageLinkFxt {
	private final    XomwLinker mgr = new XomwLinker(new gplx.xowa.mediawiki.includes.linkers.Xomw_link_renderer(new XomwSanitizer()));
	private final    XomwLinker_NormalizeSubpageLink normalize_subpage_link = new XomwLinker_NormalizeSubpageLink();
	public void Test__normalize_subpage_link(String page_title_str, String link, String text, String expd_link, String expd_text) {
		mgr.normalizeSubpageLink(normalize_subpage_link, XomwTitle.newFromText(Bry_.new_u8(page_title_str)), Bry_.new_u8(link), Bry_.new_u8(text));
		Gftest.Eq__str(expd_link, String_.new_u8(normalize_subpage_link.link));
		Gftest.Eq__str(expd_text, String_.new_u8(normalize_subpage_link.text));
	}
}
