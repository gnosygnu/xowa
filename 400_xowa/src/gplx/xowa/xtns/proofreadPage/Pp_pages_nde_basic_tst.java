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
public class Pp_pages_nde_basic_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void Init() {
		Io_mgr._.InitEngine_mem();
		fxt.Wiki().Db_mgr().Load_mgr().Clear(); // must clear; otherwise fails b/c files get deleted, but wiki.data_mgr caches the Xowd_regy_mgr (the .reg file) in memory;
		fxt.Wiki().Ns_mgr().Add_new(Xowc_xtn_pages.Ns_page_id_default, "Page").Add_new(Xowc_xtn_pages.Ns_index_id_default, "Index").Init();
	}
	@After public void term() {
		fxt.Wiki().Cache_mgr().Free_mem_all();
	}
	@Test  public void Basic() {
		fxt.Init_page_create("Page:A/1", "abc");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=1 />", String_.Concat_lines_nl
		(	"<p>abc "
		,	"</p>"
		,	""
		));
	}
	@Test  public void Multiple() {
		fxt.Init_page_create("Page:A/1", "a");
		fxt.Init_page_create("Page:A/2", "b");
		fxt.Init_page_create("Page:A/3", "c");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=3 />", String_.Concat_lines_nl
		(	"<p>a b c "
		,	"</p>"
		,	""
		));
	}
	@Test  public void Section() {
		fxt.Init_page_create("Page:A/1", "a<section begin=\"sect_0\"/>b");
		fxt.Init_page_create("Page:A/2", "cd");
		fxt.Init_page_create("Page:A/3", "e<section end=\"sect_2\"/>f");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=3 fromsection='sect_0' tosection='sect_2' />", String_.Concat_lines_nl
		(	"<p>b cd e "
		,	"</p>"
		));
	}
	@Test  public void Section__onlyinclude() {
		fxt.Init_page_create("Page:A/1", "a<section begin='sect_0'/>b<section end='sect_0'/>c");
		fxt.Test_parse_page_wiki_str("<pages index='A' from=1 to=1 onlysection='sect_0' />", String_.Concat_lines_nl
		(	"<p>b "
		,	"</p>"
		));
	}
	@Test  public void Section__onlyinclude_ignores_from_to() {
		fxt.Init_page_create("Page:A/1", "<section begin='sect_a'/>a<section end='sect_a'/><section begin='sect_b'/>b<section end='sect_b'/><section begin='sect_c'/>c<section end='sect_c'/>");
		fxt.Test_parse_page_wiki_str("<pages from=1 index='A' onlysection='sect_b' fromsection='sect_a' tosection='sect_c' />", String_.Concat_lines_nl
		(	"<p>b "
		,	"</p>"
		));
	}
	@Test  public void Noinclude() {
		fxt.Init_page_create("Page:A/1", "<noinclude>a</noinclude>{|\n|b\n|}");
		fxt.Init_page_create("Page:A/2", "<noinclude>c</noinclude>''d''");
		fxt.Init_page_create("Page:A/3", "<noinclude>e</noinclude>f");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=3 />", String_.Concat_lines_nl_skip_last
		(	"<table>"
		,	"  <tr>"
		,	"    <td>b"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		,	" <i>d</i> f "
		));
	}
	@Test  public void Err_page_ns_doesnt_exist() {
		fxt.Wiki().Ns_mgr_(Xow_ns_mgr_.default_());
		fxt.Wiki().Cfg_parser().Xtns().Itm_pages().Reset();	// must reset to clear cached valid ns_page from previous tests
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=3 />", "&lt;pages index=&quot;A&quot; from=1 to=3 /&gt;");
		fxt.Wiki().Cfg_parser().Xtns().Itm_pages().Reset();	// must reset to clear cached invalid ns_page for next tests
	}
	@Test  public void Subpage() {	// PURPOSE: [[/Page]] should be relative to current page; EX: Flatland and [[/First World]]; DATE:2013-04-29
		fxt.Init_page_create("Page:A/1", "[[/Sub1|Sub 1]]");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=1 />", String_.Concat_lines_nl
		(	"<p><a href=\"/wiki/Test_page/Sub1\">Sub 1</a> "	// NOTE: / is relative to Page_name (in this case Test_page)
		,	"</p>"
		));
	}
}
