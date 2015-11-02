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
import org.junit.*; import gplx.xowa.htmls.core.parsers.*;
public class Xoh_lnki_parse_tst {
	private final Xoh_parser_fxt fxt = new Xoh_parser_fxt();
	@Test   public void Same() {
		fxt.Init__lnki(0, 70, Xoh_lnki_dict_.Type__same, -1, -1, "", "A", "");
		fxt.Test__parse(Xoh_lnki_html__hdump__tst.Html__same);
	}
	@Test   public void Diff() {
		fxt.Init__lnki(0, 70, Xoh_lnki_dict_.Type__diff, -1, -1, "A", "b", "");
		fxt.Test__parse(Xoh_lnki_html__hdump__tst.Html__diff);
	}
	@Test   public void Trail() {
		fxt.Init__lnki(0, 71, Xoh_lnki_dict_.Type__trail, -1, -1, "", "A", "b");
		fxt.Test__parse(Xoh_lnki_html__hdump__tst.Html__trail);
	}
	@Test   public void Recurse() {
//			fxt.Init__lnki(0, 69, Xoh_lnki_dict_.Type__caption_n, -1, -1, "A");
//			fxt.Test__parse("<a href=\"/wiki/A\" id=\"xowa_lnki_2\" title=\"A\">A<a href=\"/wiki/B\" id=\"xowa_lnki_2\" title=\"B\">b</a>c</a>");
	}
}
