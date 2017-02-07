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
package gplx.xowa.xtns.imaps.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.imaps.*;
import org.junit.*;
import gplx.xowa.htmls.core.htmls.*;
public class Imap_html__hdump__tst {
	@Before public void init() {fxt.Reset(); fxt.Fxt().Hctx_(Xoh_wtr_ctx.Hdump);} private final    Imap_xnde_html_fxt fxt = new Imap_xnde_html_fxt();
	@Test  public void Basic() {
		fxt.Test_html_full_str(String_.Concat_lines_nl_skip_last
		( "<imagemap>"
		, "File:A.png|thumb|100x200px|a1"
		, "circle 0 0 5 [[B|b1]]"
		, "rect 0 0 4 8 [[C|c1]]"
		, "desc none"
		, "</imagemap>"
		), String_.Concat_lines_nl_skip_last
		( "<div class=\"thumb tright\">"
		, "  <div class=\"thumbinner\" style=\"width:100px;\">"	// NOTE:220px is default w for "non-found" thumb; DATE:2014-09-24
		, "    <div id=\"imap_div_0\" class=\"noresize\">"
		, "      <map name=\"imageMap_1_1\">"
		, "        <area href=\"/wiki/B\" shape=\"circle\" coords=\"0,0,5\" alt=\"b1\" title=\"b1\"/>"
		, "        <area href=\"/wiki/C\" shape=\"rect\" coords=\"0,0,4,8\" alt=\"c1\" title=\"c1\"/>"
		, "      </map>"
		, "      <img data-xowa-title=\"A.png\" data-xoimg=\"1|100|200|-1|-1|-1\" src=\"\" width=\"0\" height=\"0\" alt=\"\" usemap=\"#imagemap_1_1\"/>"
		, "    </div>"
		, "    <div class=\"thumbcaption\">"
		,       "<div class=\"magnify\"><a href=\"/wiki/File:A.png\" class=\"internal\" title=\"Enlarge\"></a></div>a1"
		, "    </div>"
		, "  </div>"
		, "</div>"
		));
	}
}
