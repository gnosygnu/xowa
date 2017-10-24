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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xot_invk_wkr__transclude__tst {		
	@Before		public void init() {fxt.Reset();} private final    Xop_fxt fxt = new Xop_fxt();
	@After	public void term() {fxt.Init_defn_clear();}
	@Test  public void Basic() {	// PURPOSE: {{:Template:Test}} is same as {{Template:Test}}; EX.WIKT:android; japanese and {{:Template:ja/script}}			
		fxt.Init_defn_add("Test_1", "{{#if:|y|n}}");	// NOTE: must be of form "Test 1"; test_1 will fail
		fxt.Test_parse_tmpl_str("{{:Template:Test 1}}", "n");
	}
	@Test  public void Arguments() { // PURPOSE: transclusion test with arguments
		fxt.Init_page_create("PageToTransclude", "a{{{key}}}c");
		fxt.Test_parse_tmpl_str("some text to make this page longer than transclusion {{:PageToTransclude|key=b}}"	, "some text to make this page longer than transclusion abc");
	}
	@Test  public void Redirect() {		// PURPOSE: StackOverflowError when transcluded sub-page redirects back to root_page; DATE:2014-01-07
		fxt.Init_page_create("Root/Leaf", "#REDIRECT [[Root]]");
		fxt.Init_page_create("Root", "<gallery>A.png|a{{/Leaf}}b</gallery>");		// NOTE: gallery neeeded for XOWA to fail; MW fails if just {{/Leaf}}
		fxt.Test_parse_page("Root", "<gallery>A.png|a{{/Leaf}}b</gallery>");
	}
	@Test  public void Missing__sub_page() {	// PURPOSE: transclusion of a missing page should create a link, not print an empty String; EX: it.u:Dipartimento:Design; DATE:2014-02-12
		fxt.Page_ttl_("Test_Page");
		fxt.Test_parse_tmpl_str("{{/Sub}}", "[[Test_Page/Sub]]");
	}
	@Test  public void Missing__colon_prefix() {// PURPOSE: page with colon_prefix should not add Template: PAGE:en.d:a; DATE:2016-06-24
		fxt.Test_parse_tmpl_str("{{:a}}", "[[:a]]");	// ":a", not "Template:A" or "A"
	}
	@Test  public void Colon_by_safesubst() {	// SUPPORT: page with colon_prefix should not add Template: PAGE:en.d:a; DATE:2016-06-24
		fxt.Init_defn_add("Test_2", "{{safesubst:Template:{{{1}}}}}");
		fxt.Test_parse_tmpl_str("{{Test 2|b}}", "[[Template:b]]");
	}
	@Test  public void Colon_w_template() {		// SUPPORT: page with colon_prefix should not add Template: PAGE:en.d:a; DATE:2016-06-24
		fxt.Init_defn_add("Test_3", "{{:Template:{{{1}}}}}");
		fxt.Test_parse_tmpl_str("{{Test 3|b}}", "[[:Template:b]]");
	}
	@Test  public void Toc() {	// PURPOSE: __TOC__ in transcluded page should be ignored; PAGE:de.w:Game_of_Thrones DATE:2016-11-21
		fxt.Init_page_create("TranscludedToc", "__TOC__\na");
		fxt.Parser().Expand_tmpl(Bry_.new_u8("{{:TranscludedToc}}"));
		Tfds.Eq(false, fxt.Page().Wtxt().Toc().Flag__toc());	// transcluded page is true, but current page should still be false
	}
}
