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
package gplx.xowa; import gplx.*;
import org.junit.*; import gplx.xowa.wikis.xwikis.*;
public class Xoa_url_parser_basic_tst {
	@Before public void init() {fxt.Reset();} private Xoa_url_parser_chkr fxt = new Xoa_url_parser_chkr();
	@Test  public void Basic() {
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("A").Test_parse_w_wiki("en.wikipedia.org/wiki/A");
	}
	@Test  public void Abrv() {	// deprecate; no longer needed with shortcuts
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("A").Test_parse_w_wiki("en.wikipedia.org/A");
	}
	@Test  public void Commons() {	// PURPOSE: "C" was being picked up as an xwiki to commons; PAGE:no.b:C/Variabler; DATE:2014-10-14
		fxt.Init_xwiki("c", "commons.wikimedia.org");	// add alias of "C"
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("C/D").Test_parse_w_wiki("C/D");	// should use default wiki of enwiki, not commons; also, page should be "C/D", not "D"
	}
	@Test  public void Http_basic() {
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("A").Test_parse_w_wiki("http://en.wikipedia.org/wiki/A");
	}
	@Test  public void Relative() {
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("A").Test_parse_w_wiki("//en.wikipedia.org/wiki/A");
	}
	@Test  public void Name() {
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("A").Test_parse_w_wiki("A");
	}
	@Test  public void Sub_1() {
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("A/b").Test_parse_w_wiki("A/b");
	}
	@Test  public void Sub_2() {
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("A/b/c").Test_parse_w_wiki("A/b/c");
	}
	@Test  public void Sub_3() {
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("A/b").Test_parse_w_wiki("en.wikipedia.org/wiki/A/b");
	}
	@Test  public void Ns_category() {
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("Category:A").Test_parse_w_wiki("Category:A");
	}
	@Test  public void Ns_file() {
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("File:A").Test_parse_w_wiki("File:A");
	}
	@Test  public void Anchor() {
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("A").Expd_anchor("b").Test_parse_w_wiki("A#b");
	}
	@Test  public void Upload() { 
		fxt.App().User().Wiki().Xwiki_mgr().Add_full("commons.wikimedia.org", "commons.wikimedia.org");
		fxt.Reset().Expd_wiki("commons.wikimedia.org").Expd_page("File:C.svg").Test_parse_w_wiki("http://upload.wikimedia.org/wikipedia/commons/a/ab/C.svg");
		fxt.Reset().Expd_wiki("commons.wikimedia.org").Expd_page("File:A.png").Test_parse_w_wiki("http://upload.wikimedia.org/wikipedia/commons/thumb/7/70/A.png/220px-A.png");
	}
	@Test  public void Parse_lang() {
		Xow_xwiki_mgr xwiki_mgr = fxt.Wiki().Xwiki_mgr();
		xwiki_mgr.Add_full(Bry_.new_ascii_("fr"), Bry_.new_ascii_("fr.wikipedia.org"), Bry_.new_ascii_("http://fr.wikipedia.org/~{0}"));
		fxt.Expd_wiki("fr.wikipedia.org").Expd_page("A").Test_parse_w_wiki("http://en.wikipedia.org/wiki/fr:A");
	}
	@Test  public void Alias_wiki() {
		Xow_xwiki_mgr xwiki_mgr = fxt.Wiki().Xwiki_mgr();
		xwiki_mgr.Add_full(Bry_.new_ascii_("s"), Bry_.new_ascii_("en.wikisource.org"));
		fxt.Expd_wiki("en.wikisource.org").Expd_page("A/b/c").Test_parse_w_wiki("s:A/b/c");
	}
	@Test  public void Xwiki_no_segs() {	// PURPOSE: handle xwiki without full url; EX: "commons:Commons:Media_of_the_day"; DATE:2014-02-19
		Xow_xwiki_mgr xwiki_mgr = fxt.Wiki().Xwiki_mgr();
		xwiki_mgr.Add_full(Bry_.new_ascii_("s"), Bry_.new_ascii_("en.wikisource.org"));
		fxt.Expd_wiki("en.wikisource.org").Expd_page("Project:A").Test_parse_w_wiki("s:Project:A");
	}
	@Test  public void Domain_only() {
		fxt.App().User().Wiki().Xwiki_mgr().Add_full("fr.wikipedia.org", "fr.wikipedia.org");
		fxt.Expd_wiki("fr.wikipedia.org").Expd_page("").Test_parse_w_wiki("fr.wikipedia.org");
	}
	@Test  public void Domain_and_wiki() {
		fxt.App().User().Wiki().Xwiki_mgr().Add_full("fr.wikipedia.org", "fr.wikipedia.org");
		fxt.Expd_wiki("fr.wikipedia.org").Expd_page("").Test_parse_w_wiki("fr.wikipedia.org/wiki");
	}
	@Test  public void Domain_and_wiki_w_http() {
		fxt.App().User().Wiki().Xwiki_mgr().Add_full("fr.wikipedia.org", "fr.wikipedia.org");
		fxt.Expd_wiki("fr.wikipedia.org").Expd_page("").Test_parse_w_wiki("http://fr.wikipedia.org/wiki");
	}		
	@Test  public void Redirect() {
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("A").Test_parse_w_wiki("A?redirect=no");
	}
	@Test  public void Namespace_in_different_wiki() {	// PURPOSE.fix: namespaced titles would default to default_wiki instead of current_wiki
		fxt.Expd_wiki("en.wikisource.org").Expd_page("Category:A").Test_parse_w_wiki(fxt.Wiki_wikisource(), "Category:A");
	}		
	@Test  public void Action_is_edit() {
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("A").Expd_action_is_edit_y().Test_parse_w_wiki("A?action=edit");
	}
	@Test  public void Assert_state_cleared() {	// PURPOSE.fix: action_is_edit (et. al.) was not being cleared on parse even though Xoa_url reused; DATE:20121231
		byte[] raw = Bry_.new_ascii_("A?action=edit");
		Xoa_url url = Xoa_url_parser.Parse_url(fxt.App(), fxt.Wiki(), raw, 0, raw.length, false);
		Tfds.Eq(true, url.Action_is_edit());
		raw = Bry_.new_ascii_("B");
		Xoa_url_parser.Parse_url(url, fxt.App(), fxt.Wiki(), raw, 0, raw.length, false);
		Tfds.Eq(false, url.Action_is_edit());
	}
	@Test  public void Query_arg() {	// PURPOSE.fix: query args were not printing out
		byte[] raw = Bry_.new_ascii_("en.wikipedia.org/wiki/Special:Search/Earth?fulltext=yes");
		Xoa_url url = Xoa_url_parser.Parse_url(fxt.App(), fxt.Wiki(), raw, 0, raw.length, false);
		Xoa_url_parser parser = new Xoa_url_parser();
		Tfds.Eq("en.wikipedia.org/wiki/Special:Search/Earth?fulltext=yes", parser.Build_str(url));
	}
	@Test   public void Anchor_with_slash() {	// PURPOSE: A/b#c/d was not parsing correctly
		fxt.Expd_page("A/b").Expd_anchor("c.2Fd").Test_parse_w_wiki("A/b#c/d");
	}
	@Test  public void Slash() {
		fxt.Reset().Expd_wiki("en.wikipedia.org").Expd_page("/A").Test_parse_w_wiki("en.wikipedia.org/wiki//A");
		fxt.Reset().Expd_wiki("en.wikipedia.org").Expd_page("A//b").Test_parse_w_wiki("en.wikipedia.org/wiki/A//b");
		fxt.Reset().Expd_wiki("en.wikipedia.org").Expd_page("//A").Test_parse_w_wiki("en.wikipedia.org/wiki///A");
	}
	@Test  public void Question_is_page() {
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("A?B").Expd_anchor(null).Test_parse_w_wiki("A?B");
	}
	@Test  public void Question_is_anchor() {
		fxt.Expd_wiki("en.wikipedia.org").Expd_page("A").Expd_anchor("b.3Fc").Test_parse_w_wiki("A#b?c");
	}
}
class Xoa_url_parser_chkr implements Tst_chkr {
	public Xoa_url_parser_chkr Reset() {
		if (app == null) {
			app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_(app, "en.wikipedia.org");
			wiki_wikisource = Xoa_app_fxt.wiki_(app, "en.wikisource.org");
			app.User().Wiki().Xwiki_mgr().Add_full("en.wikipedia.org", "en.wikipedia.org");
			app.User().Wiki().Xwiki_mgr().Add_full("en.wikisource.org", "en.wikisource.org");
		}
		expd_wiki_str = expd_page = expd_anchor = null;
		expd_anchor_is_edit = Bool_.__byte;
		return this;
	}
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public Xowe_wiki Wiki_wikisource() {return wiki_wikisource;} private Xowe_wiki wiki_wikisource;
	public Class<?> TypeOf() {return Xoa_url.class;}
	public Xoa_url_parser_chkr Expd_wiki(String v) 				{this.expd_wiki_str = v; return this;} private String expd_wiki_str;
	public Xoa_url_parser_chkr Expd_page(String v) 				{this.expd_page = v; return this;} private String expd_page;
	public Xoa_url_parser_chkr Expd_anchor(String v) 			{this.expd_anchor = v; return this;} private String expd_anchor;
	public Xoa_url_parser_chkr Expd_action_is_edit_y() 			{this.expd_anchor_is_edit = Bool_.Y_byte; return this;} private byte expd_anchor_is_edit = Bool_.__byte;
	public Xoa_url_parser_chkr Expd_action_is_edit_n() 			{this.expd_anchor_is_edit = Bool_.N_byte; return this;}
	public void Init_xwiki(String alias, String domain) {app.User().Wiki().Xwiki_mgr().Add_full(alias, domain);}
	public int Chk(Tst_mgr mgr, String path, Object actl_obj) {
		Xoa_url actl = (Xoa_url)actl_obj;
		int rv = 0;
		rv += mgr.Tst_val(expd_wiki_str == null, path, "wiki", expd_wiki_str, String_.new_utf8_(actl.Wiki_bry()));
		rv += mgr.Tst_val(expd_page == null, path, "page", expd_page, String_.new_utf8_(actl.Page_bry()));
		rv += mgr.Tst_val(expd_anchor == null, path, "anchor", expd_anchor, String_.new_utf8_(actl.Anchor_bry()));
		rv += mgr.Tst_val(expd_anchor_is_edit == Bool_.__byte, path, "anchor_is_edit", expd_anchor_is_edit == Bool_.Y_byte, actl.Action_is_edit());
		return rv;
	}
	public Xoa_url_parser_chkr Test_parse_from_url_bar(String raw, String expd) {
		Xoa_url actl_url = Xoa_url_parser.Parse_from_url_bar(app, wiki, raw);
		Tfds.Eq(expd, actl_url.Xto_full_str());
		return this;
	}
	public void Test_parse_w_wiki(String raw) {Test_parse_w_wiki(wiki, raw);}
	public void Test_parse_w_wiki(Xowe_wiki w, String raw) {
		Xoa_url url = Xoa_url_parser.Parse_url(app, w, raw);
		Tst_mgr tst_mgr = new Tst_mgr();
		tst_mgr.Tst_obj(this, url);
	}
}
