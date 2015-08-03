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
package gplx.xowa.wikis.xwikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*;
public class Xow_lang_mgr_tst {
	private final Xow_lang_mgr_fxt fxt = new Xow_lang_mgr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic() {
		fxt.tst("[[simple:Earth]] [[fr:Terre]] [[es:Tierra]] [[de:Erde]] [[it:Terre]]", String_.Concat_lines_nl_skip_last
		( "<div id=\"xowa-lang\">"
		, "  <h5><a href='javascript:xowa_toggle_visible(\"wikidata-langs\");' style='text-decoration: none !important;'>In other languages<img id='wikidata-langs-toggle-icon' src='file:///mem/xowa/user/test_user/app/img/window/portal/twisty_right.png' title='' /></a> (links: 5) </h5>"
		, "  <div id='wikidata-langs-toggle-elem' style='display:none;'>"
		, "  <h4>grp1</h4>"
		, "  <table style='width: 100%;'>"
		, "    <tr>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>Simple</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"simple\" title=\"Earth\" href=\"/site/simple.wikipedia.org/wiki/Earth\">Earth</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>Spanish</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"es\" title=\"Tierra\" href=\"/site/es.wikipedia.org/wiki/Tierra\">Tierra</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>Italian</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"it\" title=\"Terre\" href=\"/site/it.wikipedia.org/wiki/Terre\">Terre</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "    </tr>"
		, "  </table>"
		, "  <h4>grp2</h4>"
		, "  <table style='width: 100%;'>"
		, "    <tr>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>French</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"fr\" title=\"Terre\" href=\"/site/fr.wikipedia.org/wiki/Terre\">Terre</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>German</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"de\" title=\"Erde\" href=\"/site/de.wikipedia.org/wiki/Erde\">Erde</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "    </tr>"
		, "  </table>"
		, "  </div>"
		, "</div>"
		));
	}
	@Test  public void Empty() {
		fxt.tst("[[simple:]]", String_.Concat_lines_nl_skip_last
		( "<div id=\"xowa-lang\">"
		, "  <h5><a href='javascript:xowa_toggle_visible(\"wikidata-langs\");' style='text-decoration: none !important;'>In other languages<img id='wikidata-langs-toggle-icon' src='file:///mem/xowa/user/test_user/app/img/window/portal/twisty_right.png' title='' /></a> (links: 1) </h5>"
		, "  <div id='wikidata-langs-toggle-elem' style='display:none;'>"
		, "  <h4>grp1</h4>"
		, "  <table style='width: 100%;'>"
		, "    <tr>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>Simple</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"simple\" title=\"Test page\" href=\"/site/simple.wikipedia.org/wiki/\">Test page</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "    </tr>"
		, "  </table>"
		, "  </div>"
		, "</div>"
		));
	}
	@Test  public void Unregistered() {
//			fxt.Wiki().Xwiki_mgr().Add_full(Bry_.new_a7("zh"), Bry_.new_a7("zh.wikipedia.org"), Bry_.new_a7("http://zh.wikipedia.org/~{0}"));
		fxt.tst("[[zh:Earth]]", String_.Concat_lines_nl_skip_last
		( "<div id=\"xowa-lang\">"
		, "  <h5><a href='javascript:xowa_toggle_visible(\"wikidata-langs\");' style='text-decoration: none !important;'>In other languages<img id='wikidata-langs-toggle-icon' src='file:///mem/xowa/user/test_user/app/img/window/portal/twisty_right.png' title='' /></a> (links: 1) </h5>"
		, "  <div id='wikidata-langs-toggle-elem' style='display:none;'>"
		, "  <h4>grp1</h4>"
		, "  <table style='width: 100%;'>"
		, "    <tr>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>Chinese</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"zh\" title=\"Earth\" href=\"https://zh.wikipedia.org/wiki/Earth\">Earth</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "    </tr>"
		, "  </table>"
		, "  </div>"
		, "</div>"
		));
	}
	@Test  public void Anchor() {// PURPOSE: A#b was not showing anchor "#b"; DATE:2013-10-23
		fxt.tst("[[simple:A#b]]", String_.Concat_lines_nl_skip_last
		( "<div id=\"xowa-lang\">"
		, "  <h5><a href='javascript:xowa_toggle_visible(\"wikidata-langs\");' style='text-decoration: none !important;'>In other languages<img id='wikidata-langs-toggle-icon' src='file:///mem/xowa/user/test_user/app/img/window/portal/twisty_right.png' title='' /></a> (links: 1) </h5>"
		, "  <div id='wikidata-langs-toggle-elem' style='display:none;'>"
		, "  <h4>grp1</h4>"
		, "  <table style='width: 100%;'>"
		, "    <tr>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>Simple</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"simple\" title=\"A#b\" href=\"/site/simple.wikipedia.org/wiki/A#b\">A#b</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "    </tr>"
		, "  </table>"
		, "  </div>"
		, "</div>"
		));
	}
}
