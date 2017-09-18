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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_xnde_wkr__err_misc_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@After public void term() {fxt.Init_para_n_();}
	@Test  public void Error_br_removed() {
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <th><span>a</span><br/><span>b</span>"
			,	"    </th>"
			,	"  </tr>"
			,	"</table>"
			), String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <th><span>a</span><br/><span>b</span>"
			,	"    </th>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
		fxt.Init_para_n_();
	}
	@Test  public void Div_should_not_pop_past_td() {	// PURPOSE: extra </div> should not close <div> that is outside of <td>; PAGE:en.w:Rome en.w:Ankara
		fxt.Init_para_y_();
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<table>"
			,		"<tr>"
			,			"<td>"
			,				"<div>"									// this is <div> #1
			,					"<table>"
			,						"<tr>"
			,							"<td>"
			,								"<div>"					// this is <div> #2
			,									"<div>"
			,										"a"
			,									"</div>"
			,							"</td>"
			,							"<td>"
			,									"<div>"
			,										"b"
			,									"</div>"
			,								"</div>"				// this </div> was supposed to pop <div> #2, but can't (b/c of HTML rules); however, do not try to pop <div> #1;
			,							"</td>"
			,							"<td>"
			,								"<div>"
			,									"c"
			,								"</div>"
			,							"</td>"
			,						"</tr>"
			,					"</table>"
			,				"</div>"
			,			"</td>"
			,		"</tr>"
			,	"</table>"
			), String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>"
			,	"<div>"
			,	"      <table>"
			,	"        <tr>"
			,	"          <td>"
			,	"<div>"
			,	"<div>"
			,	""
			,	"<p>a"
			,	"</p>"
			,	"</div>"
			,	"</div>"
			,	"          </td>"
			,	"          <td>"
			,	"<div>"
			,	""
			,	"<p>b"
			,	"</p>"
			,	"</div>"
			,	"</div>"				// TIDY.dangling: tidy will correct dangling node; DATE:2014-07-22
			,	"          </td>"
			,	"          <td>"
			,	"<div>"
			,	""
			,	"<p>c"
			,	"</p>"
			,	"</div>"
			,	"          </td>"
			,	"        </tr>"
			,	"      </table>"
			,	"</div>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			));
		fxt.Init_para_n_();
	}
	@Test  public void Xnde_pops() {	// PURPOSE: somehow xnde pops upper nde; PAGE:en.w:Greek government debt crisis; "History of government debt"
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<i>"
			,	"{|"
			,	"|-"
			,	"|<i>a</i>"
			,	"|}"
			,	"</i>"
			), String_.Concat_lines_nl_skip_last
			(	"<i>"
			,	"<table>"
			,	"  <tr>"
			,	"    <td><i>a</i>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	"</i>"
			));
	}
	@Test  public void Err_inline_extension() {
		fxt.Test_parse_page_all_str
			(	"<poem/>"
			,	""
			);
	}
	@Test  public void Xnde_para() {	// PURPOSE: buggy code caused </p> to close everything; keeping test b/c of <p> logic
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"<tr>"
			,	"<td>"
			,	"<div>"
			,	"<p>"
			,	"<span>"
			,	"</span>"
			,	"</p>"
			,	"</div>"
			,	"</td>"
			,	"</tr>"
			,	"</table>"
			), String_.Concat_lines_nl_skip_last
			(	"<table>"
			,	"  <tr>"
			,	"    <td>"
			,	"<div>"
			,	"<p>"
			,	"<span>"
			,	"</span>"
			,	"</p>"
			,	"</div>"
			,	"    </td>"
			,	"  </tr>"
			,	"</table>"
			,	""
			)
			);
	}
	@Test  public void Sup_bug() {	// PURPOSE: occurred at ref of UK; a {{cite web|url=http://www.abc.gov/{{dead link|date=December 2011}}|title=UK}} b
		fxt.Test_parse_page_wiki_str("x <b><sup>y</b> z", "x <b><sup>y</sup></b> z");
	}
	@Test  public void Br_backslash() {	// PURPOSE: allow <br\>; EX:w:Mosquito; [[Acalyptratae|A<br\>c<br\>a<br\>l<br\>y<br\>p<br\>t<br\>r<br\>a<br\>t<br\>a<br\>e]]
		fxt.Test_parse_page_all_str("<br\\>", "<br/>");
	}
	@Test  public void Tt_does_not_repeat() {	// PURPOSE: handle <tt>a<tt>; EX:w:Domain name registry
		fxt.Test_parse_page_all_str("<tt>a<tt>", "<tt>a</tt>");
	}
	@Test  public void Loose_xnde_names() {	// PURPOSE: MW allows <font-> and other variations; EX:w:2012_in_film
		fxt.Test_parse_page_all_str("<font-size='100%'>a</font>", "<font>a</font>");
	}
	@Test   public void Anchor_nested() {
		fxt.Test_parse_page_all_str("b<a>c<a>d [[e]] f", "b&lt;a>c&lt;a>d <a href=\"/wiki/E\">e</a> f");
	}
	@Test   public void Img_should_not_be_xtn() {	// PURPOSE:<img> marked as .xtn; unclosed <img> was escaping rest of text; PAGE:de.w:Wikipedia:Technik/Archiv/2014 DATE:2014-11-06
		fxt.Test_parse_page_all_str("<img>''a''", "&lt;img><i>a</i>");
	}
	@Test   public void Invalid__percent() {	// PURPOSE: invalidate xml tags with %; EX:<ref%s>; PAGE:pl.w:Scynk_nadrzewny; DATE:2016-08-07
		fxt.Test_parse_page_all_str("<b%>a</b>", "&lt;b%&gt;a</b>");	// NOTE: should be literally printed as <b%>, not transformed to <b>
	}
	@Test   public void Meta_link() {	// PURPOSE: meta and link tags should not print; EX:<meta> <link>; PAGE:fr.s:La_Dispute; DATE:2017-05-28
		fxt.Test_parse_page_all_str("<meta /><link />", "");
	}
}
