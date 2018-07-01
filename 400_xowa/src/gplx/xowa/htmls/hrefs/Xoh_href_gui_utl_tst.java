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
package gplx.xowa.htmls.hrefs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*;
import gplx.core.primitives.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.guis.views.*;
public class Xoh_href_gui_utl_tst {
	@Before public void init() {fxt.Clear();} private Xoh_href_gui_utl_fxt fxt = new Xoh_href_gui_utl_fxt();
	@Test   public void Extract_href__text() {
		fxt.Test_extract_href("0|"													, "");
		fxt.Test_extract_href("1|selected_text"										, "selected_text");
		fxt.Test_extract_href("2|http://a.org"										, "http://a.org");
	}
	@Test   public void Extract_href__file() {
		fxt.Test_extract_href("2|file:///site/en.wiktionary.org/wiki/Page_1"		, "en.wiktionary.org/wiki/Page_1");
		fxt.Test_extract_href("2|file:///wiki/Page_2"								, "en.wikipedia.org/wiki/Page_2");
		fxt.Test_extract_href("2|file://#anchor"									, "en.wikipedia.org/wiki/Page_0#anchor");
	}
	@Test   public void Extract_href__internal() {
		fxt.Test_extract_href("2|/site/en.wiktionary.org/wiki/Page_1"				, "en.wiktionary.org/wiki/Page_1");
		fxt.Test_extract_href("2|/wiki/Page_2"										, "en.wikipedia.org/wiki/Page_2");
		fxt.Test_extract_href("2|#anchor"											, "en.wikipedia.org/wiki/Page_0#anchor");
	}
	@Test  public void Html_window_vpos_parse() {
		fxt.Test_Html_window_vpos_parse("0|0,1,2", "0", "'0','1','2'");
		fxt.Test_Html_window_vpos_parse("org.eclipse.swt.SWTException: Permission denied for <file://> to get property Selection.rangeCount", null, null);	// check that invalid path doesn't fail; DATE:2014-04-05
	}
	@Test   public void Standardize_xowa_link() {
		fxt.Test_standardize_xowa_link("file:///site/en.wikipedia.org/wiki/A"	, "/site/en.wikipedia.org/wiki/A");
		fxt.Test_standardize_xowa_link("file:///wiki/A"							, "/wiki/A");
		fxt.Test_standardize_xowa_link("file://#A"								, "#A");
	}
}
class Xoh_href_gui_utl_fxt {
	public void Clear() {
		cur_wiki = "en.wikipedia.org";
		cur_page = "Page_0";
	}
	public String Cur_wiki() {return cur_wiki;} public Xoh_href_gui_utl_fxt Cur_wiki_(String v) {cur_wiki = v; return this;} private String cur_wiki;
	public String Cur_page() {return cur_page;} public Xoh_href_gui_utl_fxt Cur_page_(String v) {cur_page = v; return this;} private String cur_page;
	public void Test_extract_href(String text_str, String expd) {
		Tfds.Eq(expd, Xoh_href_gui_utl.Html_extract_text(cur_wiki, cur_page, text_str));
	}
	private String_obj_ref scroll_top = String_obj_ref.null_(), node_path = String_obj_ref.null_();
	public void Test_Html_window_vpos_parse(String raw, String expd_scroll_top, String expd_node_path) {
		scroll_top.Val_null_(); node_path.Val_null_();
		Xog_html_itm.Html_window_vpos_parse(raw, scroll_top, node_path);
		Tfds.Eq(expd_scroll_top, scroll_top.Val(), expd_scroll_top);
		Tfds.Eq(expd_node_path, node_path.Val(), expd_node_path);
	}
	public void Test_standardize_xowa_link(String raw, String expd) {
		Tfds.Eq_str(expd, Xoh_href_gui_utl.Standardize_xowa_link(raw), "standardize");
	}
}
