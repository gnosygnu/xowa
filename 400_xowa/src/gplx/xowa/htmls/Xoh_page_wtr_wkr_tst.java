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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
import org.junit.*;
import gplx.xowa.guis.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.htmls.core.htmls.*;
public class Xoh_page_wtr_wkr_tst {
	@Before public void init() {fxt.Clear();} private Xoh_page_wtr_fxt fxt = new Xoh_page_wtr_fxt();
	@Test  public void Page_name() {
		fxt.Test_page_name_by_ttl("Earth", "Earth");
		fxt.Test_page_name_by_ttl("File:A.png", "File:A.png");
		fxt.Test_page_name_by_ttl("Special:Search/earth?fulltext=y", "Special:Search/earth");
		fxt.Test_page_name_by_ttl("Special:Search/earth", "Special:Search/earth");
		fxt.Test_page_name_by_display("Special:Allpages", "All pages", "All pages");
	}
	@Test  public void Edit() {
		fxt.Test_edit("&#9;", "&amp;#9;\n");	// NOTE: cannot by &#9; or will show up in edit box as "\t" and save as "\t" instead of &#9;
	}
	@Test  public void Css() {
		fxt.App().Html_mgr().Page_mgr().Content_code_fmt().Fmt_("<pre style='overflow:auto'>~{page_text}</pre>");
		fxt.Test_read("MediaWiki:Common.css", ".xowa {}", "<pre style='overflow:auto'>.xowa {}</pre>");
		fxt.App().Html_mgr().Page_mgr().Content_code_fmt().Fmt_("<pre>~{page_text}</pre>");
	}
	@Test  public void Amp_disable() {	// PURPOSE: in js documents; &quot; should be rendered as &quot;, not as "; DATE:2013-11-07
		fxt.Test_read("MediaWiki:Gadget.js", "&quot;", "<pre>&amp;quot;</pre>");
	}
}
class Xoh_page_wtr_fxt {
	public void Clear() {
		if (app == null) {
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
		}
	}	private Bry_bfr tmp_bfr = Bry_bfr_.Reset(255); private Xowe_wiki wiki;
	public Xoae_app App() {return app;} private Xoae_app app; 
	public void Test_page_name_by_display(String ttl, String display, String expd) {
		Tfds.Eq(expd, String_.new_a7(Xoh_page_wtr_wkr_.Bld_page_name(tmp_bfr, Xoa_ttl.Parse(wiki, Bry_.new_a7(ttl)), Bry_.new_a7(display))));
	}
	public void Test_page_name_by_ttl(String raw, String expd) {
		Tfds.Eq(expd, String_.new_a7(Xoh_page_wtr_wkr_.Bld_page_name(tmp_bfr, Xoa_ttl.Parse(wiki, Bry_.new_a7(raw)), null)));
	}
	public void Test_edit(String raw, String expd) {
		wiki.Html_mgr().Page_wtr_mgr().Html_capable_(true);
		Xoae_page page = wiki.Parser_mgr().Ctx().Page();
		page.Db().Text().Text_bry_(Bry_.new_u8(raw));
		Xoh_page_wtr_mgr mgr = wiki.Html_mgr().Page_wtr_mgr();
		Xoh_page_wtr_wkr wkr = mgr.Wkr(Xopg_page_.Tid_edit);
		wkr.Write_body(tmp_bfr, wiki.Parser_mgr().Ctx(), Xoh_wtr_ctx.Basic, page);
		Tfds.Eq(expd, tmp_bfr.To_str_and_clear());
	}
	public void Test_read(String page_name, String page_text, String expd) {
		wiki.Html_mgr().Page_wtr_mgr().Html_capable_(true);
		Xoae_page page = wiki.Parser_mgr().Ctx().Page();
		page.Ttl_(Xoa_ttl.Parse(wiki, Bry_.new_a7(page_name)));
		page.Db().Text().Text_bry_(Bry_.new_u8(page_text));
		Xoh_page_wtr_mgr mgr = wiki.Html_mgr().Page_wtr_mgr();
		Xoh_page_wtr_wkr wkr = mgr.Wkr(Xopg_page_.Tid_read);
		wkr.Write_body(tmp_bfr, wiki.Parser_mgr().Ctx(), Xoh_wtr_ctx.Basic, page);
		Tfds.Eq(expd, tmp_bfr.To_str_and_clear());
	}
}
