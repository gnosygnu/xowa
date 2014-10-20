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
package gplx.xowa.xtns.proofreadPage; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Pp_pages_nde_recursion_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void Init() {fxt.Init_xtn_pages();}
	@After public void term() {
		fxt.Wiki().Cache_mgr().Free_mem_all();
	}
	@Test  public void Page() {	// PURPOSE: handle recursive calls on page; EX: fr.s:Page:NRF_19.djvu/19; DATE:2014-01-01
		fxt.Init_page_create("Page:A/1", "<pages index=\"A\" from=1 to=1 />abc");	// NOTE: recursive call to self
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=1 />", String_.Concat_lines_nl
		( "<p>abc "
		, "</p>"
		, ""
		));
	}
	@Test  public void Index() {	// PURPOSE: handle recursive calls on index; EX: en.s:Poems_of_Italy:_selections_from_the_Odes_of_Giosue_Carducci/Before_the_Old_Castle_of_Verona; DATE:2014-01-19
		fxt.Init_page_create("Index:A", "<pages index=A/>");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=1 />", "<p> \n</p>");
	}
	@Test  public void MediaWiki_Proofreadpage_header_template() {	// PURPOSE: handle recursive calls through Proofreadpage_header_template; EX: fr.s:L�Enfer_(Barbusse); DATE:2014-05-21
		fxt.Init_page_create("MediaWiki:Proofreadpage_header_template", "<pages index=\"A\" />");	// NOTE: this is just a simulation of fr.s, which calls Module:Header_template which in turn calls preprocess to results in recursion
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" />", "<p>\n</p>");
	}
}
