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
package gplx.xowa.addons.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
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
		, "  <div id=\"toctitle\" class=\"toctitle\">"
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
	private final    Xoh_toc_mgr wtr = new Xoh_toc_mgr();
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public void Clear() {wtr.Clear();}
	public void Init__add(int hdr_num, String hdr_txt) {wtr.Add(hdr_num, Bry_.new_u8(hdr_txt));}
	public void Init__init_page(String toc_title, boolean page_banner) {wtr.Init(gplx.xowa.htmls.core.htmls.tidy.Xow_tidy_mgr_interface_.Noop, Bry_.new_u8(toc_title), Bry_.Empty);}
	public void Test__html_itms(String... expd_ary) {
		Gftest.Eq__ary(expd_ary, String_.Ary(Bry_split_.Split_lines(wtr.Test__to_html())));
	}
	public void Test__html_div(String... expd_ary) {
		wtr.To_html(bfr, gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx.Basic, false);
		Gftest.Eq__ary(expd_ary, String_.Ary(Bry_split_.Split_lines(bfr.To_bry_and_clear())));
	}
}
