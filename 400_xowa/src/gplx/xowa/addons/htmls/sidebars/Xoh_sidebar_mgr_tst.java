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
package gplx.xowa.addons.htmls.sidebars; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
import org.junit.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
public class Xoh_sidebar_mgr_tst {
	@Before public void init() {fxt.Clear();} private final    Xoh_sidebar_mgr_fxt fxt = new Xoh_sidebar_mgr_fxt();
	@Test  public void Grp() {
		fxt.Init__msg__grp("key", "text", "title");
		fxt.Exec__make("* key");
		fxt.Test__objs(fxt.Make__grp("text", "title"));
	}
	@Test  public void Grp_missing_msg() {
		fxt.Exec__make("* key");
		fxt.Test__objs(fxt.Make__grp("key", Null_str));
	}
	@Test  public void Itm() {
		fxt.Init__msg__itm("href_key", "main_key", "text", "title", "accesskey", "href");
		fxt.Exec__make("** href_key|main_key");
		fxt.Test__objs(fxt.Make__itm("text", "title", "accesskey", "/wiki/Href"));
	}
	@Test  public void Itm_missing_msg() {
		fxt.Exec__make("** href_key|main_key");
		fxt.Test__objs(fxt.Make__itm("main_key", Null_str, Null_str, "/wiki/Href_key"));
	}
	@Test  public void Itm_text() {	// PURPOSE: only text msg exists; EX: ** Portal:Contents|contents; no href, accesskey, title
		fxt.Init__msg__itm("href_key", "main_key", "text", Null_str, Null_str, Null_str);	// only define msg for text
		fxt.Exec__make("** href_key|main_key");
		fxt.Test__objs(fxt.Make__itm("text", Null_str, Null_str, "/wiki/Href_key"));
	}
	@Test  public void Itm_href_absolute() {
		fxt.Exec__make("** http://a.org|main_key");
		fxt.Test__objs(fxt.Make__itm("main_key", Null_str, Null_str, "http://a.org"));
	}
	@Test  public void Itm_href_manual() {
		fxt.Exec__make("** Help:Contents|main_key");
		fxt.Test__objs(fxt.Make__itm("main_key", Null_str, Null_str, "/wiki/Help:Contents"));
	}
	@Test  public void Itm_href_xwiki() {
		Xop_fxt.Reg_xwiki_alias(fxt.Wiki(), "c", "commons.wikimedia.org");
		fxt.Exec__make("** c:Help:Contents|main_key");
		fxt.Test__objs(fxt.Make__itm("main_key", Null_str, Null_str, "/site/commons.wikimedia.org/wiki/Help:Contents"));
	}
	@Test  public void Itm_err_missing_key() {
		fxt.Exec__make("** no_main_key");
		fxt.Test__objs();
	}
	@Test  public void Itm_ignore() {	// PURPOSE: ignore SEARCH, TOOLBOX, LANGUAGES
		fxt.Exec__make
		( "** SEARCH"
		, "** TOOLBOX"
		, "** LANGUAGES"
		);
		fxt.Test__objs();
	}
	@Test  public void Itm_comment() { // PURPOSE: ignore comment; EX:de.v:MediaWiki:Sidebar; DATE:2014-03-08
		fxt.Init__msg__itm("href_key", "main_key", "text", "title", "accesskey", "href");
		fxt.Exec__make("** href_key<!--a-->|main_key<!--b-->");
		fxt.Test__objs(fxt.Make__itm("text", "title", "accesskey", "/wiki/Href"));
	}
	@Test   public void Smoke() {
		fxt.Init__msg__grp("navigation", "Grp_0_text", "Grp_0_title");
		fxt.Init__msg__itm("mainpage", "mainpage-description", "Itm_0_text", "Itm_0_title [a]", "a", "Itm_0_href");
		fxt.Init__msg__itm("Portal:Contents", "contents", "Itm_1_text", Null_str, Null_str, Null_str);
		fxt.Exec__make
		( "* navigation"
		, "** mainpage|mainpage-description"
		, "** Portal:Contents|contents"
		, "* SEARCH"
		, "* interaction"
		, "** helppage|help"
		, "* TOOLBOX"
		, "** TOOLBOXEND"
		, "* LANGUAGES"
		);
		fxt.Test__objs
		( fxt.Make__grp("Grp_0_text", "Grp_0_title").Subs__add
		(   fxt.Make__itm("Itm_0_text", "Itm_0_title [a]", "a", "/wiki/Itm_0_href")
		,   fxt.Make__itm("Itm_1_text", Null_str, Null_str, "/wiki/Portal:Contents")
		)
		, fxt.Make__grp("interaction", Null_str).Subs__add
		(   fxt.Make__itm("help", Null_str, Null_str, "/wiki/Helppage")
		));
		fxt.Test__html
		( "<div class=\"portal\" id=\"n-navigation\">"
		, "  <h3>Grp_0_text</h3>"
		, "  <div class=\"body\">"
		, "    <ul>"
		, "      <li id=\"n-mainpage-description\"><a href=\"/wiki/Itm_0_href\" accesskey=\"a\" title=\"Itm_0_title [a] [a]\">Itm_0_text</a></li>"
		, "      <li id=\"n-contents\"><a href=\"/wiki/Portal:Contents\" title=\"\">Itm_1_text</a></li>"
		, "    </ul>"
		, "  </div>"
		, "</div>"
		, "<div class=\"portal\" id=\"n-interaction\">"
		, "  <h3>interaction</h3>"
		, "  <div class=\"body\">"
		, "    <ul>"
		, "      <li id=\"n-help\"><a href=\"/wiki/Helppage\" title=\"\">help</a></li>"
		, "    </ul>"
		, "  </div>"
		, "</div>"
		);
	}
	@Test  public void Itm_template_msg() {
		fxt.Init__msg__itm("href", "main", null, null, null, "{{ns:Special}}:Random");
		fxt.Exec__make("** href|main");
		fxt.Test__objs(fxt.Make__itm("main", Null_str, Null_str, "/wiki/Special:Random"));
	}
	@Test  public void Itm_template_key() {
		fxt.Exec__make("** {{ns:Special}}:Random|main");
		fxt.Test__objs(fxt.Make__itm("main", Null_str, Null_str, "/wiki/Special:Random"));
	}
	@Test  public void Popups() {
		fxt.Init__popups_enabled(true);
		fxt.Exec__make
		( "* navigation"
		, "** mainpage|mainpage-description"
		);
		fxt.Test__objs
		( fxt.Make__grp("navigation", "").Subs__add
		(   fxt.Make__itm("mainpage-description", Null_str, Null_str, "/wiki/Mainpage")
		));
		fxt.Test__html
		( "<div class=\"portal\" id=\"n-navigation\">"
		, "  <h3>navigation</h3>"
		, "  <div class=\"body\">"
		, "    <ul>"
		, "      <li id=\"n-mainpage-description\"><a href=\"/wiki/Mainpage\" class='xowa-hover-off' title=\"\">mainpage-description</a></li>"
		, "    </ul>"
		, "  </div>"
		, "</div>"
		);
	}
	private static final String Null_str = "";
}
class Xoh_sidebar_mgr_fxt {
	private Xoae_app app; private Xowe_wiki wiki; private Xoh_sidebar_mgr sidebar_mgr; private Bry_bfr bfr;
	public Xoh_sidebar_mgr_fxt Clear() {
		app = Xoa_app_fxt.Make__app__edit();
		wiki = Xoa_app_fxt.Make__wiki__edit(app);
		sidebar_mgr = wiki.Html_mgr().Portal_mgr().Sidebar_mgr();
		bfr = Bry_bfr_.Reset(Io_mgr.Len_kb);
		Init__popups_enabled(false);
		return this;
	}
	public Xowe_wiki Wiki() {return wiki;}
	public Xoh_sidebar_itm Make__grp(String text, String title, Xoh_sidebar_itm... itms) {
		Xoh_sidebar_itm rv = new Xoh_sidebar_itm(Bool_.N, Bry_.new_a7(text), Bry_.new_a7(text), null);
		rv.Init_by_title_and_accesskey(Bry_.new_a7(title), null, null);
		return rv;
	}
	public Xoh_sidebar_itm Make__itm(String text, String title, String accesskey, String href) {
		Xoh_sidebar_itm rv = new Xoh_sidebar_itm(Bool_.Y, Bry_.new_a7(text), Bry_.new_a7(text), Bry_.new_a7(href));
		rv.Init_by_title_and_accesskey(Bry_.new_a7(title), Bry_.new_a7(accesskey), null);
		return rv;
	}
	public Xoh_sidebar_mgr_fxt Init__popups_enabled(boolean v) {
		wiki.Html_mgr().Head_mgr().Popup_mgr().Enabled_(v);
		return this;
	}
	public Xoh_sidebar_mgr_fxt Init__msg__grp(String key, String text, String title) {
		Init_msg(key, text);
		Init_msg("tooltip-n-" + key, title);
		return this;
	}
	public Xoh_sidebar_mgr_fxt Init__msg__itm(String href_key, String main_key, String text, String title, String accesskey, String href) {
		if (text != null) Init_msg(main_key, text);
		if (href != null) Init_msg(href_key, href);
		if (title != null) Init_msg("tooltip-n-" + main_key, title);
		if (accesskey != null) Init_msg("accesskey-n-" + main_key, accesskey);
		return this;
	}
	public Xoh_sidebar_mgr_fxt Init_msg(String key, String val) {
		Xol_msg_mgr msg_mgr = wiki.Lang().Msg_mgr();
		Xol_msg_itm msg_itm = msg_mgr.Itm_by_key_or_new(Bry_.new_a7(key));
		msg_itm.Atrs_set(Bry_.new_a7(val), false, String_.Has(val, "{{"));
		return this;
	}
	public void Exec__make(String... raw) {
		sidebar_mgr.Make(bfr, Bry_.new_u8(String_.Concat_lines_nl_skip_last(raw)));
	}
	public void Test__objs(Xoh_sidebar_itm... expd) {
		Tfds.Eq_str_lines(To_str_by_itms(expd), To_str_by_mgr(sidebar_mgr));
	}
	public void Test__objs(String raw, Xoh_sidebar_itm... expd) {
		Tfds.Eq_str_lines(To_str_by_itms(expd), To_str_by_mgr(sidebar_mgr));
	}
	public void Test__html(String... expd) {
		Tfds.Eq_str_lines(String_.Concat_lines_nl_skip_last(expd), String_.new_u8(sidebar_mgr.Html_bry()));
	}
	private static String To_str_by_mgr(Xoh_sidebar_mgr mgr) {
		List_adp grps = mgr.Grps();
		int len = grps.Len();
		Xoh_sidebar_itm[] ary = new Xoh_sidebar_itm[len];
		for (int i = 0; i < len; i++)
			ary[i] = (Xoh_sidebar_itm)grps.Get_at(i);
		return To_str_by_itms(ary);
	}
	
	private static String To_str_by_itms(Xoh_sidebar_itm[] ary) {
		Bry_bfr bfr = Bry_bfr_.New();
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++)
			To_str_by_itm(bfr, ary[i]);
		return bfr.To_str_and_clear();
	}
	private static void To_str_by_itm(Bry_bfr bfr, Xoh_sidebar_itm cur) {
		boolean tid_is_itm = cur.Tid_is_itm();
		bfr.Add_str_a7(tid_is_itm ? "itm|" : "grp|");
		bfr.Add(cur.Text()).Add_byte_pipe();
		bfr.Add(cur.Title()).Add_byte_pipe();
		if (tid_is_itm) {
			bfr.Add(cur.Accesskey()).Add_byte_pipe();
			bfr.Add(cur.Href()).Add_byte_pipe();
		}
		bfr.Add_byte_nl();

		int len = cur.Subs__len();
		for (int i = 0; i< len; ++i)
			To_str_by_itm(bfr, cur.Subs__get_at(i));
	}
}
