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
package gplx.xowa.xtns.proofreadPage; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Pp_pages_nde_recursion_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void Init() {fxt.Init_xtn_pages();}
	@After public void term() {
		fxt.Wiki().Cache_mgr().Free_mem__all();
	}
	@Test  public void Page() {	// PURPOSE: handle recursive calls on page; EX: fr.s:Page:NRF_19.djvu/19; DATE:2014-01-01
		fxt.Init_page_create("Page:A/1", "<pages index=\"A\" from=1 to=1 />abc");	// NOTE: recursive call to self
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=1 />", String_.Concat_lines_nl
		( "<p>abc&#32;"
		, "</p>"
		, ""
		));
	}
	@Test  public void Index() {	// PURPOSE: handle recursive calls on index; EX: en.s:Poems_of_Italy:_selections_from_the_Odes_of_Giosue_Carducci/Before_the_Old_Castle_of_Verona; DATE:2014-01-19
		fxt.Init_page_create("Index:A", "<pages index=A/>");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=1 />", "<p>&#32;\n</p>");
	}
	@Test  public void MediaWiki_Proofreadpage_header_template() {	// PURPOSE: handle recursive calls through Proofreadpage_header_template; EX: fr.s:L�Enfer_(Barbusse); DATE:2014-05-21
		fxt.Init_page_create("MediaWiki:Proofreadpage_header_template", "<pages index=\"A\" />");	// NOTE: this is just a simulation of fr.s, which calls Module:Header_template which in turn calls preprocess to results in recursion
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" />", "<p>\n</p>");
	}
}
