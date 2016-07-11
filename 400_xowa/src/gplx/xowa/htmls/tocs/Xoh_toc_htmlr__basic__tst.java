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
package gplx.xowa.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*; import gplx.core.tests.*;
public class Xoh_toc_htmlr__basic__tst {
	@Before public void init() {fxt.Clear();} private final    Xoh_toc_htmlr__basic__fxt fxt = new Xoh_toc_htmlr__basic__fxt();
	@Test   public void D1_S0_S0() {
		fxt.Init__add(2, "a");
		fxt.Init__add(2, "b");
		fxt.Init__add(2, "c");
		fxt.Test__html_itms
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-2\"><a href=\"#b\"><span class=\"tocnumber\">2</span> <span class=\"toctext\">b</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-3\"><a href=\"#c\"><span class=\"tocnumber\">3</span> <span class=\"toctext\">c</span></a>"
		, "    </li>"
		, "  </ul>"
		);
	}
	@Test   public void D1_D1_D1() {
		fxt.Init__add(2, "a");
		fxt.Init__add(3, "a_a");
		fxt.Init__add(4, "a_a_a");
		fxt.Test__html_itms
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    <ul>"
		, "      <li class=\"toclevel-2 tocsection-2\"><a href=\"#a_a\"><span class=\"tocnumber\">1.1</span> <span class=\"toctext\">a_a</span></a>"
		, "      <ul>"
		, "        <li class=\"toclevel-3 tocsection-3\"><a href=\"#a_a_a\"><span class=\"tocnumber\">1.1.1</span> <span class=\"toctext\">a_a_a</span></a>"
		, "        </li>"
		, "      </ul>"
		, "      </li>"
		, "    </ul>"
		, "    </li>"
		, "  </ul>"
		);
	}
	@Test   public void D1_D1_S0_U1() {
		fxt.Init__add(2, "a");
		fxt.Init__add(3, "a_a");
		fxt.Init__add(3, "a_b");
		fxt.Init__add(2, "b");
		fxt.Test__html_itms
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    <ul>"
		, "      <li class=\"toclevel-2 tocsection-2\"><a href=\"#a_a\"><span class=\"tocnumber\">1.1</span> <span class=\"toctext\">a_a</span></a>"
		, "      </li>"
		, "      <li class=\"toclevel-2 tocsection-3\"><a href=\"#a_b\"><span class=\"tocnumber\">1.2</span> <span class=\"toctext\">a_b</span></a>"
		, "      </li>"
		, "    </ul>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-4\"><a href=\"#b\"><span class=\"tocnumber\">2</span> <span class=\"toctext\">b</span></a>"
		, "    </li>"
		, "  </ul>"
		);
	}
	@Test   public void D1_D1_U1_D1() {
		fxt.Init__add(2, "a");
		fxt.Init__add(3, "a_a");
		fxt.Init__add(2, "b");
		fxt.Init__add(3, "b_a");
		fxt.Test__html_itms
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    <ul>"
		, "      <li class=\"toclevel-2 tocsection-2\"><a href=\"#a_a\"><span class=\"tocnumber\">1.1</span> <span class=\"toctext\">a_a</span></a>"
		, "      </li>"
		, "    </ul>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-3\"><a href=\"#b\"><span class=\"tocnumber\">2</span> <span class=\"toctext\">b</span></a>"
		, "    <ul>"
		, "      <li class=\"toclevel-2 tocsection-4\"><a href=\"#b_a\"><span class=\"tocnumber\">2.1</span> <span class=\"toctext\">b_a</span></a>"
		, "      </li>"
		, "    </ul>"
		, "    </li>"
		, "  </ul>"
		);
	}
	@Test   public void D1_D1_D1_U2() {
		fxt.Init__add(2, "a");
		fxt.Init__add(3, "a_a");
		fxt.Init__add(4, "a_a_a");
		fxt.Init__add(2, "b");
		fxt.Test__html_itms
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    <ul>"
		, "      <li class=\"toclevel-2 tocsection-2\"><a href=\"#a_a\"><span class=\"tocnumber\">1.1</span> <span class=\"toctext\">a_a</span></a>"
		, "      <ul>"
		, "        <li class=\"toclevel-3 tocsection-3\"><a href=\"#a_a_a\"><span class=\"tocnumber\">1.1.1</span> <span class=\"toctext\">a_a_a</span></a>"
		, "        </li>"
		, "      </ul>"
		, "      </li>"
		, "    </ul>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-4\"><a href=\"#b\"><span class=\"tocnumber\">2</span> <span class=\"toctext\">b</span></a>"
		, "    </li>"
		, "  </ul>"
		);
	}
	@Test   public void D1_D2_U1_D1() {
		fxt.Init__add(2, "a");
		fxt.Init__add(4, "a_a_a_a");
		fxt.Init__add(3, "a_a_a");
		fxt.Init__add(4, "a_a_a_b");
		fxt.Test__html_itms
		( "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    <ul>"
		, "      <li class=\"toclevel-2 tocsection-2\"><a href=\"#a_a_a_a\"><span class=\"tocnumber\">1.1</span> <span class=\"toctext\">a_a_a_a</span></a>"
		, "      </li>"
		, "      <li class=\"toclevel-2 tocsection-3\"><a href=\"#a_a_a\"><span class=\"tocnumber\">1.2</span> <span class=\"toctext\">a_a_a</span></a>"
		, "      <ul>"
		, "        <li class=\"toclevel-3 tocsection-4\"><a href=\"#a_a_a_b\"><span class=\"tocnumber\">1.2.1</span> <span class=\"toctext\">a_a_a_b</span></a>"
		, "        </li>"
		, "      </ul>"
		, "      </li>"
		, "    </ul>"
		, "    </li>"
		, "  </ul>"
		);
	}
	@Test   public void Div() {
		fxt.Init__init_page("Table of contents", false);
		fxt.Init__add(2, "a");
		fxt.Init__add(2, "b");
		fxt.Init__add(2, "c");
		fxt.Test__html_div
		( "<div id=\"toc\" class=\"toc\">"
		, "  <div id=\"toctitle\">"
		, "    <h2>Table of contents</h2>"
		, "  </div>"
		, "  <ul>"
		, "    <li class=\"toclevel-1 tocsection-1\"><a href=\"#a\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">a</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-2\"><a href=\"#b\"><span class=\"tocnumber\">2</span> <span class=\"toctext\">b</span></a>"
		, "    </li>"
		, "    <li class=\"toclevel-1 tocsection-3\"><a href=\"#c\"><span class=\"tocnumber\">3</span> <span class=\"toctext\">c</span></a>"
		, "    </li>"
		, "  </ul>"
		, "</div>"
		);
	}
}
class Xoh_toc_htmlr__basic__fxt {
	private final    Xoh_toc_wtr wtr = new Xoh_toc_wtr();
	public void Clear() {wtr.Clear();}
	public void Init__add(int hdr_num, String hdr_txt) {wtr.Add(hdr_num, Bry_.new_u8(hdr_txt));}
	public void Init__init_page(String toc_title, boolean page_banner) {wtr.Init(Bry_.new_u8(toc_title), Bry_.Empty);}
	public void Test__html_itms(String... expd_ary) {
		Gftest.Eq__ary(expd_ary, String_.Ary(Bry_split_.Split_lines(wtr.Test__to_html())));
	}
	public void Test__html_div(String... expd_ary) {
		Gftest.Eq__ary(expd_ary, String_.Ary(Bry_split_.Split_lines(wtr.To_html(false))));
	}
}
