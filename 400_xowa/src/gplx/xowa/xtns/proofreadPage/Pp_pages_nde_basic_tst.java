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
import org.junit.*; import gplx.xowa.apps.cfgs.*;
public class Pp_pages_nde_basic_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void Init() {
		Io_mgr.Instance.InitEngine_mem();
		fxt.Wiki().Xtn_mgr().Xtn_proofread().Enabled_y_();
		fxt.Wiki().Db_mgr().Load_mgr().Clear(); // must clear; otherwise fails b/c files get deleted, but wiki.data_mgr caches the Xowd_regy_mgr (the .reg file) in memory;
		fxt.Wiki().Ns_mgr().Add_new(Xowc_xtn_pages.Ns_page_id_default, "Page").Add_new(Xowc_xtn_pages.Ns_index_id_default, "Index").Init();
	}
	@After public void term() {
		fxt.Wiki().Cache_mgr().Free_mem__all();
	}
	@Test  public void Basic() {
		fxt.Init_page_create("Page:A/1", "abc");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=1 />", String_.Concat_lines_nl
		(	"<p>abc&#32;"
		,	"</p>"
		,	""
		));
	}
	@Test  public void Multiple() {
		fxt.Init_page_create("Page:A/1", "a");
		fxt.Init_page_create("Page:A/2", "b");
		fxt.Init_page_create("Page:A/3", "c");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=3 />", String_.Concat_lines_nl
		(	"<p>a&#32;b&#32;c&#32;"
		,	"</p>"
		,	""
		));
	}
	@Test  public void Section() {
		fxt.Init_page_create("Page:A/1", "a<section begin=\"sect_0\"/>b");
		fxt.Init_page_create("Page:A/2", "cd");
		fxt.Init_page_create("Page:A/3", "e<section end=\"sect_2\"/>f");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=3 fromsection='sect_0' tosection='sect_2' />", String_.Concat_lines_nl
		(	"<p>b&#32;cd&#32;e&#32;"
		,	"</p>"
		));
	}
	@Test  public void Section__onlyinclude() {
		fxt.Init_page_create("Page:A/1", "a<section begin='sect_0'/>b<section end='sect_0'/>c");
		fxt.Test_parse_page_wiki_str("<pages index='A' from=1 to=1 onlysection='sect_0' />", String_.Concat_lines_nl
		(	"<p>b&#32;"
		,	"</p>"
		));
	}
	@Test  public void Section__onlyinclude_ignores_from_to() {
		fxt.Init_page_create("Page:A/1", "<section begin='sect_a'/>a<section end='sect_a'/><section begin='sect_b'/>b<section end='sect_b'/><section begin='sect_c'/>c<section end='sect_c'/>");
		fxt.Test_parse_page_wiki_str("<pages from=1 index='A' onlysection='sect_b' fromsection='sect_a' tosection='sect_c' />", String_.Concat_lines_nl
		(	"<p>b&#32;"
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
		,	"&#32;<i>d</i>&#32;f&#32;"
		));
	}
	@Test  public void Err_page_ns_doesnt_exist() {
		fxt.Wiki().Ns_mgr().Init().Clear();	// call .Clear() to remove ns for Page / Index
		fxt.Wiki().Cfg_parser().Xtns().Itm_pages().Reset();	// must reset to clear cached valid ns_page from previous tests
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=3 />", "&lt;pages index=&quot;A&quot; from=1 to=3 /&gt;");
		fxt.Wiki().Cfg_parser().Xtns().Itm_pages().Reset();	// must reset to clear cached invalid ns_page for next tests
	}
	@Test  public void Subpage() {	// PURPOSE: [[/Page]] should be relative to current page; EX: Flatland and [[/First World]]; DATE:2013-04-29
		fxt.Init_page_create("Page:A/1", "[[/Sub1|Sub 1]]");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=1 />", String_.Concat_lines_nl
		(	"<p><a href=\"/wiki/Test_page/Sub1\">Sub 1</a>&#32;"	// NOTE: / is relative to Page_name (in this case Test_page)
		,	"</p>"
		));
	}
	@Test  public void Disable() {	// PURPOSE: simulate disabled wiki; PAGE:en.w:Wikipedia:Requests_for_adminship/Phantomsteve DATE:2014-09-08
		fxt.Wiki().Xtn_mgr().Xtn_proofread().Enabled_n_();
		fxt.Init_page_create("Page:A/1", "A");
		fxt.Test_parse_page_wiki_str
		( "<pages index=\"A\" from=1 to=1>a</pages>"
		, "&lt;pages index=&quot;A&quot; from=1 to=1&gt;a"
		);
	}
	@Test  public void Page_has_nl() {	// PURPOSE: parse "to" page, even if it has \n at end; PAGE:en.s:1911_Encyclop�dia_Britannica/Boissier,_Marie_Louis_Antoine_Gaston DATE:2015-04-29
		fxt.Init_page_create("Page:A/1", "abc");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to='1\n' />", String_.Concat_lines_nl
		(	"<p>abc&#32;"
		,	"</p>"
		,	""
		));
	}
	@Test  public void Indicator() {	// PURPOSE: handle indicators; PAGE:en.s:The_Parochial_System_(Wilberforce,_1838); DATE:2015-04-29
		fxt.Init_page_create("Page:A/1", "<indicator name='b'>b</indicator>page_1");
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		( "<indicator name='a'>a</indicator>"
		, "<pages index=\"A\" from=1 to='1\' />"
		), String_.Concat_lines_nl
		(	"<p>page_1&#32;"	// make sure Page:A/1 is transcribed
		,	"</p>"
		,	""
		));
		Tfds.Eq(1, fxt.Page().Html_data().Indicators().Count());		// only 1 indicator, not 2
		Tfds.Eq(true, fxt.Page().Html_data().Indicators().Has("a"));	// indicator should be from wikitext, not <page>
	}
}
