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
package gplx.xowa.xtns.math; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Xof_math_mgr_html_tst {
	@Before public void init() {} private Xop_fxt fxt = new Xop_fxt();
	@Test  public void Escape_lt_gt_mathjax() {	// PURPOSE: escape <>&"; EX:de.w:Vergleich_(Zahlen); DATE:2014-05-10; PAGE:s.w:Matrix_(mathematics) DATE:2014-07-19
		fxt.Test_html_full_str("<math>a<>b</math>", "<span id='xowa_math_txt_0'>a&lt;&gt;b</span>");
	}
	@Test  public void Escape_lt_gt_latex() {
		fxt.App().File_mgr().Math_mgr().Renderer_is_mathjax_(false);
		fxt.Test_html_full_str("<math>a<>b</math>", "<img id='xowa_math_img_0' src='' width='' height=''/><span id='xowa_math_txt_0'>a<>b</span>");
		fxt.App().File_mgr().Math_mgr().Renderer_is_mathjax_(true);
	}
	@Test  public void Amp() { // PURPOSE: assert that amp is not escaped; DATE:2014-07-20
		fxt.Test_html_full_str("<math>a&b</math>", "<span id='xowa_math_txt_0'>a&b</span>");
	}
	@Test  public void Quote() { // PURPOSE: assert that quote is not escaped; DATE:2014-07-20
		fxt.Test_html_full_str("<math>a\"b</math>", "<span id='xowa_math_txt_0'>a\"b</span>");
	}
}
