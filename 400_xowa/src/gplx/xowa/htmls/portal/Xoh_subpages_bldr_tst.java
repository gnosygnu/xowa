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
package gplx.xowa.htmls.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*; import gplx.xowa.wikis.nss.*;
public class Xoh_subpages_bldr_tst {
	private Xoh_subpages_bldr_fxt fxt;
	@Before public void init() {fxt = new Xoh_subpages_bldr_fxt();}
	@Test  public void Basic() {
		fxt.Init__Create_pages("Help:A", "Help:A/B");
		fxt.Test__Parse("Help:A/B/C", String_.Concat_lines_nl_skip_last
		( "<span class=\"subpages\">&lt; <a href=\"/wiki/Help:A\" title=\"Help:A\">Help:A</a>&lrm; | <a href=\"/wiki/Help:A/B\" title=\"Help:A/B\">B</a>"
		, "</span>"
		));
	}
	@Test  public void Underscore_space() {// PURPOSE: convert underscore to space; ISSUE#:308 PAGE:en.v:Computer-aided_design/Software DATE:2018-12-23
		fxt.Init__Create_pages("Help:A_1", "Help:A_1/B_1");
		fxt.Test__Parse("Help:A_1/B_1/C_1", String_.Concat_lines_nl_skip_last
		( "<span class=\"subpages\">&lt; <a href=\"/wiki/Help:A_1\" title=\"Help:A 1\">Help:A 1</a>&lrm; | <a href=\"/wiki/Help:A_1/B_1\" title=\"Help:A 1/B 1\">B 1</a>"
		, "</span>"
		));
	}
	@Test  public void Underscore_quote() { // PURPOSE: escape " as &quot; PAGE:en.s:A_Critical_Examination_of_Dr_G._Birkbeck_Hills_"Johnsonian"_Editions/The_Preface_and_Dedication; ISSUE#:627; DATE:2020-03-10
		fxt.Init__Create_pages("Help:A", "Help:A/\"B\"");
		fxt.Test__Parse("Help:A/\"B\"/C", String_.Concat_lines_nl_skip_last
		( "<span class=\"subpages\">&lt; <a href=\"/wiki/Help:A\" title=\"Help:A\">Help:A</a>&lrm; | <a href=\"/wiki/Help:A/%22B%22\" title=\"Help:A/&quot;B&quot;\">\"B\"</a>"
		, "</span>"
		));
	}
	@Test  public void Skip_page_ns() {
		fxt.Init__Page_ns();
		fxt.Test__Parse("Page:A/B/C", "");
	}
	@Test  public void Missing_pages() { // PURPOSE: missing pages should not show up in subpages; ISSUE#:626; DATE:2019-12-01
		fxt.Init__Create_pages("Help:A", "Help:A/B/C"); // NOTE: "Help:A/B" is missing
		fxt.Test__Parse("Help:A/B/C/D", String_.Concat_lines_nl_skip_last
		( "<span class=\"subpages\">&lt; <a href=\"/wiki/Help:A\" title=\"Help:A\">Help:A</a>&lrm; | <a href=\"/wiki/Help:A/B/C\" title=\"Help:A/B/C\">B/C</a>"
		, "</span>"
		));
	}
	@Test  public void Utf8() { // PURPOSE: do not url-encode non-ascii chars; DATE:2019-12-07
		fxt.Init__Create_pages("Help:A_é", "Help:A_é/1");
		fxt.Test__Parse("Help:A_é/1", String_.Concat_lines_nl_skip_last
		( "<span class=\"subpages\">&lt; <a href=\"/wiki/Help:A_%C3%A9\" title=\"Help:A é\">Help:A é</a>"
		, "</span>"
		));
	}
	@Test  public void Title_case_segments() { // PURPOSE: make sure ns and page_ttl is properly-cased; ISSUE#:626; DATE:2019-12-01
		fxt.Init__Create_pages("Help:A", "Help:A/b");

		/*
		For "help:a/b/c"
		* Upper-case to "Help" b/c namespaces are upper-cased
		* Upper-case to "A" b/c Help ns automatically upper-cases all titles ("a/b" -> "A/b")
		* Do not upper-case "b" b/c path segments are not cased ("b" should not be "B")
		*/
		fxt.Test__Parse("help:a/b/c", String_.Concat_lines_nl_skip_last
		( "<span class=\"subpages\">&lt; <a href=\"/wiki/Help:A\" title=\"Help:A\">Help:A</a>&lrm; | <a href=\"/wiki/Help:A/b\" title=\"Help:A/b\">b</a>"
		, "</span>"
		));
	}
}
class Xoh_subpages_bldr_fxt {
	private final    Xoae_app app;
	private final    Xowe_wiki wiki;
	private final    Xoh_subpages_bldr subpages_bldr = new Xoh_subpages_bldr();
	public Xoh_subpages_bldr_fxt() {
		this.app = Xoa_app_fxt.Make__app__edit();
		this.wiki = Xoa_app_fxt.Make__wiki__edit(app);
		wiki.Ns_mgr().Ids_get_or_null(Xow_ns_.Tid__help).Subpages_enabled_(true);
	}
	public void Init__Page_ns() {
		wiki.Ns_mgr().Add_new(104, "Page");
		wiki.Ns_mgr().Ns_page_id_(104);
	}
	public void Init__Create_pages(String... pages) {
		for (String page : pages) {
			Xop_fxt.Init_page_create_static(wiki, page, page);
		}
	}
	public void Test__Parse(String ttl_str, String expd) {
		byte[] actl = subpages_bldr.Bld(wiki, Xoa_ttl.Parse(wiki, Bry_.new_u8(ttl_str)));
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
	}
}
