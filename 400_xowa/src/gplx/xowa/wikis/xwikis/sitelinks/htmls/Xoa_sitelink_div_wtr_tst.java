/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.wikis.xwikis.sitelinks.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.wikis.xwikis.sitelinks.*;
import org.junit.*;
public class Xoa_sitelink_div_wtr_tst {
	private final Xoa_sitelink_div_wtr_fxt fxt = new Xoa_sitelink_div_wtr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic() {
		fxt.tst("[[simple:Earth]] [[fr:Terre]] [[es:Tierra]] [[de:Erde]] [[it:Terre]]", String_.Concat_lines_nl_skip_last
		( "<div id=\"xowa-lang\">"
		, "  <h5><a href='javascript:xowa_toggle_visible(\"wikidata-langs\");' style='text-decoration: none !important;'>In other languages<img id='wikidata-langs-toggle-icon' src='file:///mem/xowa/bin/any/xowa/file/app.general/twisty_right.png' title='' /></a> (links: 5) </h5>"
		, "  <div id='wikidata-langs-toggle-elem' style='display:none;'>"
		, "  <h4>grp1</h4>"
		, "  <table style='width: 100%;'>"
		, "    <tr>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>Simple</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"en\" title=\"Earth\" href=\"/site/simple.wikipedia.org/wiki/Earth\">Earth</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>Spanish</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"es\" title=\"Tierra\" href=\"/site/es.wikipedia.org/wiki/Tierra\">Tierra</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>Italian</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"it\" title=\"Terre\" href=\"/site/it.wikipedia.org/wiki/Terre\">Terre</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "    </tr>"
		, "  </table>"
		, "  <h4>grp2</h4>"
		, "  <table style='width: 100%;'>"
		, "    <tr>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>French</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"fr\" title=\"Terre\" href=\"/site/fr.wikipedia.org/wiki/Terre\">Terre</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>German</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"de\" title=\"Erde\" href=\"/site/de.wikipedia.org/wiki/Erde\">Erde</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "      <td/>"
		, "    </tr>"
		, "  </table>"
		, "  </div>"
		, "</div>"
		));
	}
	@Test  public void Empty() {
		fxt.tst("[[simple:]]", String_.Concat_lines_nl_skip_last
		( "<div id=\"xowa-lang\">"
		, "  <h5><a href='javascript:xowa_toggle_visible(\"wikidata-langs\");' style='text-decoration: none !important;'>In other languages<img id='wikidata-langs-toggle-icon' src='file:///mem/xowa/bin/any/xowa/file/app.general/twisty_right.png' title='' /></a> (links: 1) </h5>"
		, "  <div id='wikidata-langs-toggle-elem' style='display:none;'>"
		, "  <h4>grp1</h4>"
		, "  <table style='width: 100%;'>"
		, "    <tr>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>Simple</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"en\" title=\"Test page\" href=\"/site/simple.wikipedia.org/wiki/\">Test page</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "      <td/>"
		, "      <td/>"
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
		, "  <h5><a href='javascript:xowa_toggle_visible(\"wikidata-langs\");' style='text-decoration: none !important;'>In other languages<img id='wikidata-langs-toggle-icon' src='file:///mem/xowa/bin/any/xowa/file/app.general/twisty_right.png' title='' /></a> (links: 1) </h5>"
		, "  <div id='wikidata-langs-toggle-elem' style='display:none;'>"
		, "  <h4>grp1</h4>"
		, "  <table style='width: 100%;'>"
		, "    <tr>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>Chinese</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"zh\" title=\"Earth\" href=\"https://zh.wikipedia.org/wiki/Earth\">Earth</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "      <td/>"
		, "      <td/>"
		, "    </tr>"
		, "  </table>"
		, "  </div>"
		, "</div>"
		));
	}
	@Test  public void Anchor() {// PURPOSE: A#b was not showing anchor "#b"; DATE:2013-10-23
		fxt.tst("[[simple:A#b]]", String_.Concat_lines_nl_skip_last
		( "<div id=\"xowa-lang\">"
		, "  <h5><a href='javascript:xowa_toggle_visible(\"wikidata-langs\");' style='text-decoration: none !important;'>In other languages<img id='wikidata-langs-toggle-icon' src='file:///mem/xowa/bin/any/xowa/file/app.general/twisty_right.png' title='' /></a> (links: 1) </h5>"
		, "  <div id='wikidata-langs-toggle-elem' style='display:none;'>"
		, "  <h4>grp1</h4>"
		, "  <table style='width: 100%;'>"
		, "    <tr>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>Simple</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"en\" title=\"A#b\" href=\"/site/simple.wikipedia.org/wiki/A#b\">A#b</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "      <td/>"
		, "      <td/>"
		, "    </tr>"
		, "  </table>"
		, "  </div>"
		, "</div>"
		));
	}
}
