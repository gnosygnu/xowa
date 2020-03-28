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
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.wikis.pages.*;
public class Xow_portal_mgr_tst {
	@Before public void init() {fxt.Init();} private Xowh_portal_mgr_fxt fxt = new Xowh_portal_mgr_fxt();
	@Test  public void Div_ns_bry() {
		fxt.Test_div_ns_bry("A"			, "/wiki/A;selected;/wiki/Talk:A;xowa_display_none;");
		fxt.Test_div_ns_bry("Talk:A"	, "/wiki/A;;/wiki/Talk:A;selected;");
	}
	@Test  public void Missing_ns_cls() {
		fxt.Test_missing_ns_cls("xowa_display_none");
	}
	@Test  public void Logo() {
		fxt.Portal_mgr().Div_logo_fmtr().Fmt_("~{portal_logo_url}");

		// day-mode
		fxt.Portal_mgr().Init();
		fxt.Test_logo_frag(Bool_.N, "file:///mem/xowa/user/test_user/wiki/en.wikipedia.org/html/logo.png");

		// night-mode: app
		fxt.Test_logo_frag(Bool_.Y, "file:///mem/xowa/bin/any/xowa/html/css/nightmode/logo_night.png");

		// night-mode: wiki
		Io_mgr.Instance.SaveFilStr("mem/xowa/user/test_user/wiki/en.wikipedia.org/html/logo_night.png", "");
		fxt.Portal_mgr().Init();
		fxt.Test_logo_frag(Bool_.Y, "file:///mem/xowa/user/test_user/wiki/en.wikipedia.org/html/logo_night.png");
	}
	@Test  public void Jumpto() {
		fxt.Wiki().Msg_mgr().Set("jumpto", "Jump to:");
		fxt.Wiki().Msg_mgr().Set("jumptonavigation", "navigation");
		fxt.Wiki().Msg_mgr().Set("jumptosearch", "search");
		fxt.Portal_mgr().Init();
		Gftest.Eq__ary__lines(String_.Concat_lines_nl
		( ""
		, "    <div id=\"jump-to-nav\" class=\"mw-jump\">"
		, "    <a class=\"mw-jump-link\" href=\"#mw-head\">Jump to:navigation</a>"
		, "    <a class=\"mw-jump-link\" href=\"#p-search\">Jump to:search</a>"
		, "    </div>"
		), fxt.Portal_mgr().Div_jump_to());
	}
	@Test  public void Div_personal() {
		fxt.Wiki().User().Name_(Bry_.new_a7("anonymous"));
		fxt.Test__div_personal("/wiki/User:Anonymous;anonymous;xowa_display_none;/wiki/User_talk:Anonymous;xowa_display_none;");
	}
	@Test  public void Div_personal__url() {
		fxt.Wiki().User().Name_(Bry_.new_a7("A%"));
		fxt.Test__div_personal("/wiki/User:A%25;A%;xowa_display_none;/wiki/User_talk:A%25;xowa_display_none;");
	}
	@Test  public void Div_ns() {
		fxt.Test__div_ns("A%", "/wiki/A%25;selected;/wiki/Talk:A%25;xowa_display_none;");
	}
	@Test  public void Div_view() { // PURPOSE: ensure that ttl is url-encoded; ISSUE#:572 PAGE:en.w:.07%; DATE:2020-03-28
		fxt.Wiki().Html_mgr().Portal_mgr().Div_view_fmtr().Fmt_("~{portal_view_read_href};~{portal_view_edit_href};~{portal_view_html_href}");
		fxt.Test__div_view(Xopg_view_mode_.Tid__read, "A%", "/wiki/A%25;/wiki/A%25?action=edit;/wiki/A%25?action=html");
	}
}
class Xowh_portal_mgr_fxt {
	private Xow_portal_mgr portal_mgr;
	public void Init() {
		if (app == null) {
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
			wiki.Ns_mgr().Ns_main().Exists_(true);	// needed for ns
			this.portal_mgr = wiki.Html_mgr().Portal_mgr();
			portal_mgr.Init_assert();	// needed for personal
			portal_mgr.Missing_ns_cls_(Bry_.new_a7("xowa_display_none"));
		}
	}	private Xoae_app app;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public void Test_div_ns_bry(String ttl, String expd) {
		Tfds.Eq(expd, String_.new_a7(wiki.Html_mgr().Portal_mgr().Div_ns_bry(wiki.Utl__bfr_mkr(), Xoa_ttl.Parse(wiki, Bry_.new_a7(ttl)), wiki.Ns_mgr())));
	}
	public void Test_missing_ns_cls(String expd) {
		Tfds.Eq(expd, String_.new_a7(wiki.Html_mgr().Portal_mgr().Missing_ns_cls()));
	}
	public Xow_portal_mgr Portal_mgr() {return wiki.Html_mgr().Portal_mgr();}
	public void Test_logo_frag(boolean nightmode, String expd) {
		String actl = String_.new_a7(wiki.Html_mgr().Portal_mgr().Div_logo_bry(nightmode));
		Gftest.Eq__str(expd, actl);
	}
	public void Test__div_personal(String expd) {
		Tfds.Eq(expd, String_.new_a7(wiki.Html_mgr().Portal_mgr().Div_personal_bry(false)));
	}
	public void Test__div_view(byte output_tid, String ttl_str, String expd) {
		Xoa_ttl ttl = wiki.Ttl_parse(Bry_.new_u8(ttl_str));
		Gftest.Eq__ary__lines(expd, String_.new_u8(wiki.Html_mgr().Portal_mgr().Div_view_bry(app.Utl__bfr_mkr(), output_tid, Bry_.Empty, ttl)));
	}
	public void Test__div_ns(String ttl_str, String expd) {
		Xoa_ttl ttl = wiki.Ttl_parse(Bry_.new_u8(ttl_str));
		Gftest.Eq__ary__lines(expd, String_.new_u8(wiki.Html_mgr().Portal_mgr().Div_ns_bry(app.Utl__bfr_mkr(), ttl, wiki.Ns_mgr())));
	}
}
