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
package gplx.xowa.xtns.math; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Xomath_core__tst {
	private final    Xop_fxt fxt = Xop_fxt.New_app_html();
	@Test  public void Basic__latex() {
		Renderer_(false);
		fxt.Test__parse_to_html_mgr("<math>x + y</math>", "<img id='xowa_math_img_0' src='' width='' height=''/><span id='xowa_math_txt_0'>x + y</span>");	// latex has img
	}
	@Test  public void Basic__mathjax() {
		Renderer_(true);
		fxt.Test__parse_to_html_mgr("<math>x + y</math>", "<span id='xowa_math_txt_0'>x + y</span>");	// mathjax has no img
	}
	@Test  public void Escape__mathjax() {	// PURPOSE: escape <>&"; EX:de.w:Vergleich_(Zahlen); DATE:2014-05-10; PAGE:s.w:Matrix_(mathematics) DATE:2014-07-19
		Renderer_(true);
		fxt.Test__parse_to_html_mgr("<math>a<>b</math>", "<span id='xowa_math_txt_0'>a&lt;&gt;b</span>");
	}
	@Test  public void Escape__latex() {
		Renderer_(false);
		fxt.Test__parse_to_html_mgr("<math>a<>b</math>", "<img id='xowa_math_img_0' src='' width='' height=''/><span id='xowa_math_txt_0'>a<>b</span>");
	}
	@Test  public void Amp() { // PURPOSE: assert that amp is not escaped; DATE:2014-07-20
		Renderer_(true);
		fxt.Test__parse_to_html_mgr("<math>a&b</math>", "<span id='xowa_math_txt_0'>a&b</span>");
	}
	@Test  public void Quote() { // PURPOSE: assert that quote is not escaped; DATE:2014-07-20
		Renderer_(true);
		fxt.Test__parse_to_html_mgr("<math>a\"b</math>", "<span id='xowa_math_txt_0'>a\"b</span>");
	}
	@Test   public void Script() {
		Renderer_(false);
		fxt.Test__parse_to_html_mgr("<math><script>alert('fail');</script></math>", "<img id='xowa_math_img_0' src='' width='' height=''/><span id='xowa_math_txt_0'>&lt;script>alert('fail');</script></span>");
	}
	private void Renderer_(boolean mathjax) {
		fxt.Wiki().Parser_mgr().Math__core().Renderer_is_mathjax_(mathjax);
	}
}
