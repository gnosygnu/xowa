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
package gplx.xowa.xtns.lst; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Lst_pfunc_lst_tst {		
	@Before public void init() {fxt.Clear();} private Lst_pfunc_lst_fxt fxt = new Lst_pfunc_lst_fxt();
	@Test  public void Bgn_only() {
		fxt.Clear().Page_txt_("a<section begin=key0/>val0<section end=key0/> b").Test_lst("{{#lst:section_test|key0}}", "val0");
	}
	@Test  public void Multiple() {
		fxt.Clear().Page_txt_("a<section begin=key0/>val00<section end=key0/> b<section begin=key0/> val01<section end=key0/> c").Test_lst("{{#lst:section_test|key0}}", "val00 val01");
	}
	@Test  public void Range() {
		fxt.Clear().Page_txt_("a<section begin=key0/>val0<section end=key0/> b<section begin=key1/> val1<section end=key1/> c<section begin=key2/> val2<section end=key2/> d")
			.Test_lst("{{#lst:section_test|key0|key2}}", "val0 b val1 c val2");
	}
	@Test  public void Nest() {
		fxt.Clear().Page_txt_("<section begin=key0/>val0<section begin=key00/> val00<section end=key00/><section end=key0/>").Test_lst("{{#lst:section_test|key0}}", "val0 val00");
	}
	@Test  public void Wikitext() {	// PURPOSE: assert section is expanded to html
		fxt.Clear().Page_txt_("a<section begin=key0/>''val0''<section end=key0/> b").Test_lst("{{#lst:section_test|key0}}", "<i>val0</i>");
	}
	@Test  public void Refs_ignored() {	// PURPOSE: assert that nearby refs are ignored
		fxt.Clear().Page_txt_("a<section begin=key0/>val0<ref>ref1</ref><section end=key0/> b  <ref>ref2</ref>").Test_lst("{{#lst:section_test|key0}}<references/>", String_.Concat_lines_nl
		(	"val0<sup id=\"cite_ref-0\" class=\"reference\"><a href=\"#cite_note-0\">[1]</a></sup><ol class=\"references\">"
		,	"<li id=\"cite_note-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-0\">^</a></span> <span class=\"reference-text\">ref1</span></li>"
		,	"</ol>"
		));
	}
	@Test  public void Missing_bgn_end() {
		fxt.Page_txt_("a<section bgn=key0/> b<section end=key0/> c");
		fxt.Clear().Test_lst("{{#lst:section_test}}", "a b c");
	}
	@Test  public void Missing_bgn() {
		fxt.Page_txt_("a<section bgn=key0/> b<section end=key0/> c");
		fxt.Clear().Test_lst("{{#lst:section_test||key0}}", "a b");
	}
	@Test  public void Missing_end() {
		fxt.Page_txt_("a <section begin=key0/>val0<section end=key1/> b");
		fxt.Clear().Test_lst("{{#lst:section_test|key0}}", "val0 b");	// end is missing; read to end;
	}
	@Test  public void Missing_end_noinclude() {	// EX: de.wikisource.org/wiki/Versuch_einer_mokscha-mordwinischen_Grammatik/Mokscha-Texte; Seite:Ahlqvist_Forschungen_auf_dem_Gebiete_der_ural-altaischen_Sprachen_I.pdf/111
		fxt.Page_txt_("a <section begin=key0/>val0<section end=key1/> b<noinclude>c</noinclude>");
		fxt.Clear().Test_lst("{{#lst:section_test|key0}}", "val0 b");	// end is missing; ignore noinclude
	}
	@Test  public void Missing_bgn_dupe() {
		fxt.Page_txt_("a <section begin=key0/>val0<section end=key0/> b<section begin=key1/>val1<section end=key0/>");
		fxt.Clear().Test_lst("{{#lst:section_test|key0}}", "val0");
	}
	@Test  public void Nowiki() {	// PURPOSE.fix: <nowiki> was creating incorrect sections; DATE:2013-07-11
		fxt.Clear().Page_txt_("a<nowiki>''c''</nowiki><section begin=key0/>val0<section end=key0/> b").Test_lst("{{#lst:section_test|key0}}", "val0");
	}
	@Test  public void Fullpagename() {	// PURPOSE.fix: lst creates its own ctx; make sure ctx has same page_name of calling page (Test page) not default (Main page); DATE:2013-07-11
		fxt.Clear().Page_txt_("a <section begin=key0/>{{FULLPAGENAME}}<section end=key0/> b").Test_lst("{{#lst:section_test|key0}}", "Test page");
	}
	@Test  public void Nested_forbid_recursion() {	// PURPOSE: forbid recursive calls; DATE:2014-02-09
		fxt.Fxt().Init_page_create("Sub_0", "<section begin=key_0 />a<section end=key_0 />{{#lst:Sub_0|key_0}}");	// NOTE: recursive call to self
		fxt.Fxt().Test_parse_page_all_str("{{#lst:Sub_0|key_0}}", "a");
	}
	@Test  public void Nested_allow() {	// PURPOSE: allow nested calls; DATE:2014-02-09
		fxt.Fxt().Init_page_create("Template:Sub_1", "<section begin=key_1 />b<section end=key_1 />");
		fxt.Fxt().Init_page_create("Sub_0", "<section begin=key_0 />a{{Sub_1}}<section end=key_0 />");
		fxt.Fxt().Test_parse_page_all_str("{{#lst:Sub_0|key_0}}", "ab");
	}
	@Test  public void Nested_recursion() {	// PURPOSE: allow nested calls; it.s:Main_Page; DATE:2014-02-09
		fxt.Fxt().Init_page_create("Sub_1", "<section begin=key_0 />b<section end=key_0 />");
		fxt.Fxt().Init_page_create("Template:Sub_1", "{{#section:Sub_1|key_0}}");
		fxt.Fxt().Init_page_create("Sub_0", "<section begin=key_0 />a{{Sub_1}}<section end=key_0 />");
		fxt.Fxt().Test_parse_page_all_str("{{#section:Sub_0|key_0}}", "ab");
	}
	@Test  public void Nested__ref() {				// PURPOSE: handle tags; PAGE:it.s:La_Secchia_rapita/Canto_primo DATE:2015-12-02
		fxt.Fxt().Init_page_create("Template:TagTemplate", "<ref>xyz</ref>");
		fxt.Fxt().Init_page_create("PoemPage", "<poem>A{{TagTemplate}}B</poem>");
		fxt.Fxt().Test_parse_page_all_str("{{#section:PoemPage}}<references/>", String_.Replace(String_.Concat_lines_nl_skip_last
		( "<div class='poem'>"
		, "<p>"
		, "A<sup id='cite_ref-0' class='reference'><a href='#cite_note-0'>[1]</a></sup>B"
		, "</p>"
		, "</div><ol class='references'>"
		, "<li id='cite_note-0'><span class='mw-cite-backlink'><a href='#cite_ref-0'>^</a></span> <span class='reference-text'>xyz</span></li>"
		, "</ol>"
		, ""
		), "'", "\""));
	}
	@Test  public void Nested__ref_poem() {				// PURPOSE: handle tags; PAGE:it.s:La_Secchia_rapita/Canto_primo DATE:2015-12-02
		fxt.Fxt().Init_page_create("Template:TagTemplate", "{{#tag:ref|abc<poem>def</poem>xyz}}");
		fxt.Fxt().Init_page_create("PoemPage", String_.Concat_lines_nl_skip_last
		( "<poem>A{{TagTemplate}}"
		, "    B"
		, "</poem>"
		));
		fxt.Fxt().Test_parse_page_all_str("{{#section:PoemPage}}<references/>", String_.Replace(String_.Concat_lines_nl_skip_last
		( "<div class='poem'>"
		, "<p>"
		, "A<sup id='cite_ref-0' class='reference'><a href='#cite_note-0'>[1]</a></sup><br/>"
		, "&#160;&#160;&#160;&#160;B"
		, "</p>"
		, "</div><ol class='references'>"
		, "<li id='cite_note-0'><span class='mw-cite-backlink'><a href='#cite_ref-0'>^</a></span> <span class='reference-text'>abc<div class='poem'>"
		, "<p>"
		, "def"
		, "</p>"
		, "</div>xyz</span></li>"
		, "</ol>"
		, ""
		), "'", "\""));
	}
}
class Lst_pfunc_lst_fxt {
	public Lst_pfunc_lst_fxt Clear() {
		if (fxt == null) fxt = new Xop_fxt();
		fxt.Reset();
		fxt.Wiki().Cache_mgr().Free_mem__all();
		Io_mgr.Instance.InitEngine_mem();
		return this;
	}
	public Xop_fxt Fxt() {return fxt;} private Xop_fxt fxt;
	public Lst_pfunc_lst_fxt Page_txt_(String v) {page_txt = v; return this;} private String page_txt;
	public void Test_lst(String func, String expd) {
		fxt.Init_page_create("section_test", page_txt);
		fxt.Test_parse_page_all_str(func, expd);
	}
}
