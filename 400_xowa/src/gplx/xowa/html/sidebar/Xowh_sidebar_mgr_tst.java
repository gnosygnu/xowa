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
package gplx.xowa.html.sidebar; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import org.junit.*;	
public class Xowh_sidebar_mgr_tst {
	@Before public void init() {fxt.Clear();} private Xowh_sidebar_mgr_fxt fxt = new Xowh_sidebar_mgr_fxt();
	@Test  public void Grp() {
		fxt
		.Init_msg_grp("key", "text", "title")
		.Test_parse("* key"
		, 	fxt.nav_grp_("text", "title"))
		;
	}
	@Test  public void Grp_missing_msg() {
		fxt
		.Test_parse("* key"
		, 	fxt.nav_grp_("key", Null_str))
		;
	}
	@Test  public void Itm() {
		fxt
		.Init_msg_itm("href_key", "main_key", "text", "title", "accesskey", "href")
		.Test_parse
		(	"** href_key|main_key"
		,	fxt.nav_itm_("text", "title", "accesskey", "/wiki/Href"))
		;
	}
	@Test  public void Itm_missing_msg() {
		fxt
		.Test_parse
		(	"** href_key|main_key"
		,	fxt.nav_itm_("main_key", Null_str, Null_str, "/wiki/Href_key")
		)
		;
	}
	@Test  public void Itm_text() {	// PURPOSE: only text msg exists; EX: ** Portal:Contents|contents; no href, accesskey, title
		fxt
		.Init_msg_itm("href_key", "main_key", "text", Null_str, Null_str, Null_str)	// only define msg for text
		.Test_parse
		(	"** href_key|main_key"
		,	fxt.nav_itm_("text", Null_str, Null_str, "/wiki/Href_key"))
		;
	}
	@Test  public void Itm_href_absolute() {
		fxt
		.Test_parse
		(	"** http://a.org|main_key"
		,	fxt.nav_itm_("main_key", Null_str, Null_str, "http://a.org"))
		;
	}
	@Test  public void Itm_href_manual() {
		fxt
		.Test_parse
		(	"** Help:Contents|main_key"
		,	fxt.nav_itm_("main_key", Null_str, Null_str, "/wiki/Help:Contents"))
		;
	}
	@Test  public void Itm_href_xwiki() {
		Xop_fxt.Reg_xwiki_alias(fxt.Wiki(), "c", "commons.wikimedia.org");
		fxt
		.Test_parse
		(	"** c:Help:Contents|main_key"
		,	fxt.nav_itm_("main_key", Null_str, Null_str, "/site/commons.wikimedia.org/wiki/Help:Contents"))
		;
	}
	@Test  public void Itm_err_missing_key() {
		fxt
		.Test_parse
		(	"** no_main_key"
		)
		;
	}
	@Test  public void Itm_ignore() {	// PURPOSE: ignore SEARCH, TOOLBOX, LANGUAGES
		fxt
		.Test_parse(String_.Concat_lines_nl
		(	"** SEARCH"
		,	"** TOOLBOX"
		,	"** LANGUAGES"
		));
	}
	@Test  public void Itm_comment() { // PURPOSE: ignore comment; EX:de.v:MediaWiki:Sidebar; DATE:2014-03-08
		fxt
		.Init_msg_itm("href_key", "main_key", "text", "title", "accesskey", "href")
		.Test_parse
		(	"** href_key<!--a-->|main_key<!--b-->"
		,	fxt.nav_itm_("text", "title", "accesskey", "/wiki/Href"))
		;
	}
	@Test   public void Smoke() {
		fxt
		.Init_msg_grp("navigation", "Grp_0_text", "Grp_0_title")
		.Init_msg_itm("mainpage", "mainpage-description", "Itm_0_text", "Itm_0_title [a]", "a", "Itm_0_href")
		.Init_msg_itm("Portal:Contents", "contents", "Itm_1_text", Null_str, Null_str, Null_str)
		.Test_parse(String_.Concat_lines_nl
		(	"* navigation"
		,	"** mainpage|mainpage-description"
		,	"** Portal:Contents|contents"
		,	"* SEARCH"
		,	"* interaction"
		,	"** helppage|help"
		,	"* TOOLBOX"
		,	"* LANGUAGES"
		)
		, 	fxt.nav_grp_("Grp_0_text", "Grp_0_title").Itms_add
		(		fxt.nav_itm_("Itm_0_text", "Itm_0_title [a]", "a", "/wiki/Itm_0_href")
		,		fxt.nav_itm_("Itm_1_text", Null_str, Null_str, "/wiki/Portal:Contents")
		)
		,	fxt.nav_grp_("interaction", Null_str).Itms_add
		(		fxt.nav_itm_("help", Null_str, Null_str, "/wiki/Helppage")
		));
		fxt.Test_html(String_.Concat_lines_nl
		(	"<div class=\"portal\" id='n-navigation'>"
		,	"  <h3>Grp_0_text</h3>"
		,	"  <div class=\"body\">"
		,	"    <ul>"
		,	"      <li id=\"n-mainpage-description\"><a href=\"/wiki/Itm_0_href\" accesskey=\"a\" title=\"Itm_0_title [a] [a]\">Itm_0_text</a></li>"
		,	"      <li id=\"n-contents\"><a href=\"/wiki/Portal:Contents\" title=\"\">Itm_1_text</a></li>"
		,	"    </ul>"
		,	"  </div>"
		,	"</div>"
		,	"<div class=\"portal\" id='n-interaction'>"
		,	"  <h3>interaction</h3>"
		,	"  <div class=\"body\">"
		,	"    <ul>"
		,	"      <li id=\"n-help\"><a href=\"/wiki/Helppage\" title=\"\">help</a></li>"
		,	"    </ul>"
		,	"  </div>"
		,	"</div>"
		));
	}	private static final String Null_str = "";
	@Test  public void Itm_template_msg() {
		fxt
		.Init_msg_itm("href", "main", null, null, null, "{{ns:Special}}:Random")
		.Test_parse(String_.Concat_lines_nl
		(	"** href|main"
		)
		,	fxt.nav_itm_("main", Null_str, Null_str, "/wiki/Special:Random")
		);
	}
	@Test  public void Itm_template_key() {
		fxt.Test_parse(String_.Concat_lines_nl
		(	"** {{ns:Special}}:Random|main"
		)
		,	fxt.nav_itm_("main", Null_str, Null_str, "/wiki/Special:Random")
		);
	}
	@Test  public void Popups() {
		fxt.Init_popups_enabled_(true)
		.Test_parse(String_.Concat_lines_nl
		(	"* navigation"
		,	"** mainpage|mainpage-description"
		)
		, 	fxt.nav_grp_("navigation", "").Itms_add
		(		fxt.nav_itm_("mainpage-description", Null_str, Null_str, "/wiki/Mainpage")
		)
		);
		fxt.Test_html(String_.Concat_lines_nl
		(	"<div class=\"portal\" id='n-navigation'>"
		,	"  <h3>navigation</h3>"
		,	"  <div class=\"body\">"
		,	"    <ul>"
		,	"      <li id=\"n-mainpage-description\"><a href=\"/wiki/Mainpage\" class='xowa-hover-off' title=\"\">mainpage-description</a></li>"
		,	"    </ul>"
		,	"  </div>"
		,	"</div>"
		));
	}
}
class Xowh_sidebar_mgr_fxt {
	private Xoa_app app; private Xow_wiki wiki; private Xowh_sidebar_mgr sidebar_mgr; private Bry_bfr bfr, comment_bfr;
	public Xowh_sidebar_mgr_fxt Clear() {
//			if (app == null) {
			app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
			sidebar_mgr = wiki.Html_mgr().Portal_mgr().Sidebar_mgr();
			bfr = Bry_bfr.reset_(Io_mgr.Len_kb);
			comment_bfr = Bry_bfr.reset_(Io_mgr.Len_kb);
			Init_popups_enabled_(false);
//			}
		return this;
	}
	public Xow_wiki Wiki() {return wiki;}
	public Xowh_sidebar_itm nav_grp_(String text, String title, Xowh_sidebar_itm... itms) {return new Xowh_sidebar_itm(Xowh_sidebar_itm.Tid_grp).Text_(Bry_.new_ascii_(text)).Title_(Bry_.new_ascii_(title));}
	public Xowh_sidebar_itm nav_itm_(String text, String title, String accesskey, String href) {return new Xowh_sidebar_itm(Xowh_sidebar_itm.Tid_itm).Text_(Bry_.new_ascii_(text)).Title_(Bry_.new_ascii_(title)).Accesskey_(Bry_.new_ascii_(accesskey)).Href_(Bry_.new_ascii_(href));}
	public Xowh_sidebar_mgr_fxt Init_popups_enabled_(boolean v) {app.Api_root().Html().Modules().Popups().Enabled_(v); return this;}
	public Xowh_sidebar_mgr_fxt Init_msg_grp(String key, String text, String title) {
		Init_msg(key, text);
		Init_msg("tooltip-n-" + key, title);
		return this;
	}
	public Xowh_sidebar_mgr_fxt Init_msg_itm(String href_key, String main_key, String text, String title, String accesskey, String href) {
		if (text != null) Init_msg(main_key, text);
		if (href != null) Init_msg(href_key, href);
		if (title != null) Init_msg("tooltip-n-" + main_key, title);
		if (accesskey != null) Init_msg("accesskey-n-" + main_key, accesskey);
		return this;
	}
	public Xowh_sidebar_mgr_fxt Init_msg(String key, String val) {
		Xol_msg_mgr msg_mgr = wiki.Lang().Msg_mgr();
		Xol_msg_itm msg_itm = msg_mgr.Itm_by_key_or_new(Bry_.new_ascii_(key));
		msg_itm.Atrs_set(Bry_.new_ascii_(val), false, String_.Has(val, "{{"));
		return this;
	}
	public void Test_parse(String raw, Xowh_sidebar_itm... expd) {
		sidebar_mgr.Parse(bfr, comment_bfr, Bry_.new_ascii_(raw));
		Tfds.Eq_str_lines(Xto_str(expd), Xto_str_grps(sidebar_mgr));
	}
	public void Test_html(String expd) {
		sidebar_mgr.Bld_html(bfr);
		Tfds.Eq_str_lines(expd, bfr.XtoStrAndClear());
	}
	String Xto_str_grps(Xowh_sidebar_mgr mgr) {
		int len = mgr.Grps_len();
		Xowh_sidebar_itm[] ary = new Xowh_sidebar_itm[len];
		for (int i = 0; i < len; i++)
			ary[i] = mgr.Grps_get_at(i);
		return Xto_str(ary);
	}
	
	String Xto_str(Xowh_sidebar_itm[] ary) {
		int ary_len = ary.length;
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < ary_len; i++)
			sb.Add(Xto_str(ary[i]));
		return sb.XtoStrAndClear();
	}
	String Xto_str(Xowh_sidebar_itm cur) {
		String_bldr sb = String_bldr_.new_();
		boolean tid_is_itm = cur.Tid() == Xowh_sidebar_itm.Tid_itm;
		sb.Add(tid_is_itm ? "itm|" : "grp|");
		sb.Add(cur.Text()).Add("|");
		sb.Add(cur.Title()).Add("|");
		if (tid_is_itm) {
			sb.Add(cur.Accesskey()).Add("|");
			sb.Add(cur.Href()).Add("|");
		}
		sb.Add_char_nl();
		int itms_len = cur.Itms_len();
		for (int i = 0; i< itms_len; i++)
			sb.Add(Xto_str(cur.Itms_get_at(i)));
		return sb.XtoStrAndClear();
	}
}