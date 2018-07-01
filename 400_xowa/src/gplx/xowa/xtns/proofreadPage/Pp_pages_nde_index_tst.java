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
public class Pp_pages_nde_index_tst {
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
	@Test  public void Repeated() {	// PURPOSE: repeated pages should still show (and not be excluded by recursive logic); DATE:2014-01-01
		fxt.Init_page_create("Page:A/1", "<pages index=\"A\" from=1 to=1 />abc");	// NOTE: recursive call to self
		fxt.Init_page_create("Page:D/1", "d");
		String main_txt = String_.Concat_lines_nl
		(	"<pages index=\"A\" from=1 to=1 />"
		,	"text_0"
		,	"<pages index=\"D\" from=1 to=1/>"
		,	"text_1"
		,	"<pages index=\"D\" from=1 to=1/>"
		); 
		fxt.Test_parse_page_wiki_str(main_txt, String_.Concat_lines_nl
		(	"<p>abc&#32;"
		,	"</p>"
		,	"text_0"
		,	"<p>d&#32;"
		,	"</p>"
		,	"text_1"
		,	"<p>d&#32;"
		,	"</p>"
		));
	}
	@Test  public void Index() {
		fxt.Init_page_create("Index:A", String_.Concat_lines_nl
		(	"[[ignore]]"
		,	"[[Page:A b/1]]"
		,	"[[Page:A b/2]]"
		,	"[[Page:A b/3]]"
		,	"[[Page:A b/4]]"
		,	"[[Page:A b/5]]"
		));
		fxt.Init_page_create("Page:A_b/1", "A_b/1\n");
		fxt.Init_page_create("Page:A_b/2", "A_b/2\n");
		fxt.Init_page_create("Page:A_b/3", "A_b/3\n");
		fxt.Init_page_create("Page:A_b/4", "A_b/4\n");
		fxt.Init_page_create("Page:A_b/5", "A_b/5\n");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from='A b/2' to='A_b/4' />", String_.Concat_lines_nl
		(	"<p>A_b/2"
		,	"&#32;A_b/3"
		,	"&#32;A_b/4"
		,	"&#32;"
		,	"</p>"
		));
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from='A b/2' />", String_.Concat_lines_nl		// to missing
		(	"<p>A_b/2"
		,	"&#32;A_b/3"
		,	"&#32;A_b/4"
		,	"&#32;A_b/5"
		,	"&#32;"
		,	"</p>"
		));
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" to='A b/4' />", String_.Concat_lines_nl		// from missing
		(	"<p>A_b/1"
		,	"&#32;A_b/2"
		,	"&#32;A_b/3"
		,	"&#32;A_b/4"
		,	"&#32;"
		,	"</p>"
		));
	}
	@Test  public void Index_amp_encoded() {	// handle ampersand encoded strings; EX: en.s:Team_Work_Wins!; DATE:2014-01-19
		fxt.Init_page_create("Index:\"A\"", "[[Page:\"A\"]]");
		fxt.Init_page_create("Page:\"A\"", "a");
		fxt.Test_parse_page_wiki_str("<pages index=\"&quot;A&quot;\" from='&quot;A&quot;' />", "<p>a&#32;\n</p>");
	}
	@Test  public void Index_amp_encoded_num() {// handle num-encoded vals; EX: pl.s:Zarządzenie_Nr_11_Ministra_Finansów_z_dnia_21_lipca_2008_r._w_sprawie_ustanowienia_„Dnia_Skarbowości”; DATE:2014-05-07
		fxt.Init_page_create("Index:\"A\"", "[[Page:\"A\"]]");
		fxt.Init_page_create("Page:\"A\"", "a");
		fxt.Test_parse_page_wiki_str("<pages index=\"&#34;A&#34;\" from='&#34;A&#34;' />", "<p>a&#32;\n</p>");
	}
//		@Test  public void Index_all() {	// PURPOSE: if from / to not specified, add all titles
//			fxt.Init_page_create("Index:A", String_.Concat_lines_nl
//			(	"[[Page:A b/1]]"
//			,	"[[Page:A b/2]]"
//			));
//			fxt.Init_page_create("Page:A_b/1", "A_b/1\n");
//			fxt.Init_page_create("Page:A_b/2", "A_b/2\n");
//			String main_txt = String_.Concat_lines_nl
//			(	"<pages index=\"A\" />"
//			); 
//			fxt.Test_parse_page_wiki_str(main_txt, String_.Concat_lines_nl
//			(	"<p>A_b/1"
//			,	"A_b/2"
//			,	"</p>"
//			));
//		}
	@Test  public void Section_failed_when_xnde() {	// PURPOSE: section failed to be retrieved if preceding xnde; DATE:2014-01-15
		fxt.Init_page_create("Page:A/1", "<b>a</b><section begin=\"sect_0\"/>b<section end=\"sect_0\"/>");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=1 fromsection='sect_0' tosection='sect_0' />", String_.Concat_lines_nl
		(	"<p>b&#32;"
		,	"</p>"
		));
	}
	@Test  public void Index_to_missing() {	// PURPOSE: if no to, get rest of pages
		fxt.Init_page_create("Index:A", String_.Concat_lines_nl
		(	"[[Page:A b/1]]"
		,	"[[Page:A b/2]]"
		));
		fxt.Init_page_create("Page:A_b/1", "A_b/1\n");
		fxt.Init_page_create("Page:A_b/2", "A_b/2\n");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from='A b/1' />", String_.Concat_lines_nl
		(	"<p>A_b/1"
		,	"&#32;A_b/2"
		,	"&#32;"
		,	"</p>"
		));
	}
	@Test  public void Set_from_to_if_missing() {	// PURPOSE: if no from / to, set from / to variables; note that earlier version of XO was correctly transcluding content, but just not updating from / to variable; fr.s:Constitution_de_la_France_de_1958_(version_initiale); DATE:2014-05-21
		fxt.Init_page_create("MediaWiki:Proofreadpage_header_template", "{{{from}}}-{{{to}}}\n");
		fxt.Init_page_create("Index:A", String_.Concat_lines_nl
		(	"[[Page:A/1]]"
		,	"[[Page:A/2]]"
		));
		fxt.Init_page_create("Page:A/1", "A/1\n");
		fxt.Init_page_create("Page:A/2", "A/2\n");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from='A/1' header='y' />", String_.Concat_lines_nl
		( "<p>0-1"
		, "A/1"
		, "&#32;A/2"
		, "&#32;"
		, "</p>"
		));
	}
	@Test  public void Various() {
		fxt.Init_page_create("Page:A/1", "a");
		fxt.Init_page_create("Page:A/2", "b");
		fxt.Init_page_create("Page:A/3", "c");
		fxt.Init_page_create("Page:A/4", "d");
		fxt.Init_page_create("Page:A/5", "e");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" include='1-2,4' />", "<p>a&#32;b&#32;d&#32;\n</p>\n");						// include
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=5 exclude='3' />", "<p>a&#32;b&#32;d&#32;e&#32;\n</p>\n");		// exclude
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" include=5 from=2 to=4 />", "<p>b&#32;c&#32;d&#32;e&#32;\n</p>\n");			// include should be sorted
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=5 step=2 />", "<p>a&#32;c&#32;e&#32;\n</p>\n");					// step
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=5 step=4 />", "<p>a&#32;e&#32;\n</p>\n");						// step
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=5 step=10 />", "<p>a&#32;\n</p>\n");								// step
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=3 to=3 />", "<p>c&#32;\n</p>\n");										// from = to
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" to=3/>", "<p>&#32;a&#32;b&#32;c&#32;\n</p>\n");							// from omitted
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=3/>", "<p>c&#32;d&#32;e&#32;\n</p>\n");								// to omitted
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from='' to=3 />", "<p>&#32;a&#32;b&#32;c&#32;\n</p>\n");					// from is blank
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=3 to=''/>", "<p>c&#32;d&#32;e&#32;\n</p>\n");							// to is blank
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=3 to='4.' />", "<p>c&#32;d&#32;\n</p>\n");							// allow decimal-like number; PAGE:en.w:Haworth's/Chapter_XIX; DATE:2014-01-19
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=5 exclude=''3' />", "<p>a&#32;b&#32;c&#32;d&#32;e&#32;\n</p>\n");// exclude is invalid; EX:fr.s:Sanguis_martyrum/Première_partie/I DATE:2014-01-18
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" exclude from=1 to=5 />", "<p>a&#32;b&#32;c&#32;d&#32;e&#32;\n</p>\n");		// exclude empty; ru.s:ПБЭ/Гуттен,_Ульрих_фон DATE:2014-02-22
	}
	@Test  public void Ref() {	// PURPOSE: ref on page should show; DATE:2014-01-18
		fxt.Init_page_create("Page:A/1", "a<ref>b</ref>c");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=1 /><references/>", String_.Concat_lines_nl
		( "<p>a<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup>c&#32;"
		, "</p>"
		, "<ol class=\"references\">"
		, "<li id=\"cite_note-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-0\">^</a></span> <span class=\"reference-text\">b</span></li>"
		, "</ol>"
		));
	}
	@Test  public void Tblw() {	// PURPOSE: if page begins with *, {|, etc, , automatically prepend \n (just like templates); DATE:2014-01-23
		fxt.Init_page_create("Page:A/1", "a");
		fxt.Init_page_create("Page:A/2", "* b");
		fxt.Init_page_create("Page:A/3", "c");
		fxt.Test_parse_page_wiki_str("<pages index=\"A\" from=1 to=3 />", String_.Concat_lines_nl
		(	"<p>a&#32;"
		,	"</p>"
			,	""
		,	"<ul>"
		,	"  <li> b&#32;c&#32;"
		,	"  </li>"
		,	"</ul>"
		,	""
		));
	}
	@Test  public void Index_various() {// varying logic depending on whether [[Index:]] has [[Page]] or <pagelist> DATE:2014-01-27
		fxt.Init_page_create("Page:A/0", "A/0");
		fxt.Init_page_create("Page:A/1", "A/1");
		fxt.Init_page_create("Page:A/2", "A/2");
		fxt.Init_page_create("Index:A", "");

		// [[Index:]] has no [[Page:]] links; interpret to=1 as [[Page:A/1]]
		fxt.Wiki().Cache_mgr().Free_mem__all();
		fxt.Init_page_update("Index:A" , String_.Concat_lines_nl
		( "no links"
		));
		fxt.Test_parse_page_wiki_str("<pages index='A' to=1 />", String_.Concat_lines_nl
		( "<p>A/0&#32;A/1&#32;"
		, "</p>"
		));

		// [[Index:]] has [[Page:]] links; interpret to=1 as 1st [[Page:]] in [[Index:]]'s [[Page:]] links
		fxt.Wiki().Cache_mgr().Free_mem__all();
		fxt.Init_page_update("Index:A" , String_.Concat_lines_nl
		( "[[Page:A/0]]"
		));
		fxt.Test_parse_page_wiki_str("<pages index='A' to=1 />", String_.Concat_lines_nl
		( "<p>A/0&#32;"
		, "</p>"
		));

		// [[Index:]] has [[Page:]] links but also <pagelist>; interpret to=1 as [[Page:A/1]]
		fxt.Wiki().Cache_mgr().Free_mem__all();
		fxt.Init_page_update("Index:A" , String_.Concat_lines_nl
		( "[[Page:A/0]]"
		, "<pagelist/>"
		));
		fxt.Test_parse_page_wiki_str("<pages index='A' to=1 />", String_.Concat_lines_nl
		( "<p>A/0&#32;A/1&#32;"
		, "</p>"
		));
	}
}
