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
package gplx.xowa.parsers.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.xowa.parsers.xndes.*;
public class Mwh_doc_parser_tst {
	private final Mwh_doc_parser_fxt fxt = new Mwh_doc_parser_fxt();
	@Test   public void Text__basic()				{fxt.Test_parse("abc"				, fxt.Make_txt("abc"));}
	@Test   public void Comment()					{fxt.Test_parse("a<!--b-->c"		, fxt.Make_txt("a"), fxt.Make_comment("<!--b-->"), fxt.Make_txt("c"));}
	@Test   public void Entity()					{fxt.Test_parse("a&nbsp;b"			, fxt.Make_txt("a"), fxt.Make_entity("&nbsp;"), fxt.Make_txt("b"));}
	@Test   public void Fail__inline_eos()			{fxt.Test_parse("a<b/"				, fxt.Make_txt("a<b/"));}
	@Test   public void Fail__unknown()				{fxt.Test_parse("a<bc/>d"			, fxt.Make_txt("a<bc/>d"));}
	@Test   public void Node__inline()				{fxt.Test_parse("a<b/>c"			, fxt.Make_txt("a"), fxt.Make_nde_head("<b/>")	, fxt.Make_txt("c"));}
	@Test   public void Node__pair()				{fxt.Test_parse("a<b>c</b>d"		, fxt.Make_txt("a"), fxt.Make_nde_head("<b>")	, fxt.Make_txt("c"), fxt.Make_nde_tail("</b>"), fxt.Make_txt("d"));}
	@Test   public void Atrs__pair() {
		fxt.Test_parse("<div id='1'>a</div>"
		, fxt.Make_nde_head("<div id='1'>")
		, fxt.Make_txt("a")
		, fxt.Make_nde_tail("</div>"));
	}
	@Test   public void Atrs__inline() {
		fxt.Test_parse("a<div id='1'/>b"
		, fxt.Make_txt("a")
		, fxt.Make_nde_head("<div id='1'/>")
		, fxt.Make_txt("b"));
	}
	@Test   public void Node__single_only()	{
		fxt.Test_parse("<b>a<br>b</b>c"
		, fxt.Make_nde_head("<b>")
		, fxt.Make_txt("a", Xop_xnde_tag_.Tid__b)
		, fxt.Make_nde_head("<br>")
		, fxt.Make_txt("b", Xop_xnde_tag_.Tid__b)	// <b> not <br>
		, fxt.Make_nde_tail("</b>")
		, fxt.Make_txt("c", Xop_xnde_tag_.Tid__null)
		);
	}
	@Test   public void Node__pre()	{
		fxt.Test_parse("<pre>a<div>b</pre>c"
		, fxt.Make_nde_head("<pre>")
		, fxt.Make_txt("a", Xop_xnde_tag_.Tid__pre)
		, fxt.Make_nde_head("<div>")
		, fxt.Make_txt("b", Xop_xnde_tag_.Tid__pre)	// <pre> not <div>
		, fxt.Make_nde_tail("</pre>")
		, fxt.Make_txt("c", Xop_xnde_tag_.Tid__null)
		);
	}
}
