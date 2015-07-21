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
public class Xoa_url_parser_tst {
	private final Xoa_url_parser_fxt fxt = new Xoa_url_parser_fxt();
	@Test  public void Basic() {
		fxt.Run_parse("en.wikipedia.org/wiki/A").Chk_wiki("en.wikipedia.org").Chk_page("A");
	}
	@Test  public void No_wiki() {	// PURPOSE: no "/wiki/"
		fxt.Run_parse("en.wikipedia.org/A").Chk_wiki("en.wikipedia.org").Chk_page("A");
	}
	@Test  public void Commons() {	// PURPOSE: "C" was being picked up as an xwiki to commons; PAGE:no.b:C/Variabler; DATE:2014-10-14
		fxt.Prep_add_xwiki_to_user("c", "commons.wikimedia.org");		// add alias of "c"
		fxt.Run_parse("C/D").Chk_wiki("en.wikipedia.org").Chk_page("C/D");	// should use current wiki (enwiki), not xwiki to commons; also, page should be "C/D", not "D"
	}
	@Test  public void Http_basic() {
		fxt.Run_parse("http://en.wikipedia.org/wiki/A").Chk_wiki("en.wikipedia.org").Chk_page("A");
	}
	@Test  public void Relative() {
		fxt.Run_parse("//en.wikipedia.org/wiki/A").Chk_wiki("en.wikipedia.org").Chk_page("A");
	}
	@Test  public void Name() {
		fxt.Run_parse("A").Chk_wiki("en.wikipedia.org").Chk_page("A");
	}
	@Test  public void Sub_1() {
		fxt.Run_parse("A/b").Chk_wiki("en.wikipedia.org").Chk_page("A/b");
	}
	@Test  public void Sub_2() {
		fxt.Run_parse("A/b/c").Chk_wiki("en.wikipedia.org").Chk_page("A/b/c");
	}
	@Test  public void Sub_3() {
		fxt.Run_parse("en.wikipedia.org/wiki/A/b").Chk_wiki("en.wikipedia.org").Chk_page("A/b");
	}
	@Test  public void Ns_category() {
		fxt.Run_parse("Category:A").Chk_wiki("en.wikipedia.org").Chk_page("Category:A");
	}
	@Test  public void Ns_file() {
		fxt.Run_parse("File:A").Chk_wiki("en.wikipedia.org").Chk_page("File:A");
	}
	@Test  public void Anchor() {
		fxt.Run_parse("A#b").Chk_wiki("en.wikipedia.org").Chk_page("A").Chk_anchor("b");
	}
	@Test  public void Upload() { 
		fxt.Prep_add_xwiki_to_user("commons.wikimedia.org");	// NOTE: need to add xwiki to be able to resolve "/commons/"
		fxt.Run_parse("http://upload.wikimedia.org/wikipedia/commons/a/ab/C.svg").Chk_wiki("commons.wikimedia.org").Chk_page("File:C.svg");
		fxt.Run_parse("http://upload.wikimedia.org/wikipedia/commons/thumb/7/70/A.png/220px-A.png").Chk_wiki("commons.wikimedia.org").Chk_page("File:A.png");
	}
	@Test  public void Parse_lang() {
		fxt.Prep_add_xwiki_to_wiki("fr", "fr.wikipedia.org", "http://fr.wikipedia.org/~{0}");
		fxt.Run_parse("http://en.wikipedia.org/wiki/fr:A").Chk_wiki("fr.wikipedia.org").Chk_page("A");
	}
	@Test  public void Alias_wiki() {
		fxt.Prep_add_xwiki_to_wiki("s", "en.wikisource.org");
		fxt.Run_parse("s:A/b/c").Chk_wiki("en.wikisource.org").Chk_page("A/b/c");
	}
	@Test  public void Xwiki_no_segs() {	// PURPOSE: handle xwiki without full url; EX: "commons:Commons:Media_of_the_day"; DATE:2014-02-19
		fxt.Prep_add_xwiki_to_wiki("s", "en.wikisource.org");
		fxt.Run_parse("s:Project:A").Chk_wiki("en.wikisource.org").Chk_page("Project:A");
	}
	@Test  public void Domain_only() {
		fxt.Prep_add_xwiki_to_user("fr.wikipedia.org");
		fxt.Run_parse("fr.wikipedia.org").Chk_wiki("fr.wikipedia.org").Chk_page("");
	}
	@Test  public void Domain_and_wiki() {
		fxt.Prep_add_xwiki_to_user("fr.wikipedia.org");
		fxt.Run_parse("fr.wikipedia.org/wiki").Chk_wiki("fr.wikipedia.org").Chk_page("");
	}
	@Test  public void Domain_and_wiki_w_http() {
		fxt.Prep_add_xwiki_to_user("fr.wikipedia.org");
		fxt.Run_parse("http://fr.wikipedia.org/wiki").Chk_wiki("fr.wikipedia.org").Chk_page("");
	}		
	@Test  public void Redirect() {
		fxt.Run_parse("A?redirect=no").Chk_wiki("en.wikipedia.org").Chk_page("A");
	}
	@Test  public void Namespace_in_different_wiki() {	// PURPOSE.fix: namespaced titles would default to default_wiki instead of current_wiki
		Xowe_wiki en_s = fxt.Prep_create_wiki("en.wikisource.org");
		fxt.Run_parse(en_s, "Category:A").Chk_wiki("en.wikisource.org").Chk_page("Category:A");
	}		
	@Test  public void Action_is_edit() {
		fxt.Run_parse("A?action=edit").Chk_wiki("en.wikipedia.org").Chk_page("A").Chk_action_is_edit_y();
	}
	@Test  public void Assert_state_cleared() {	// PURPOSE.fix: action_is_edit (et. al.) was not being cleared on parse even though Xoa_url reused; DATE:20121231
		fxt.Run_parse("A?action=edit")	.Chk_action_is_edit_y();
		fxt.Run_parse_reuse("B")		.Chk_action_is_edit_n();
	}
	@Test  public void Query_arg() {	// PURPOSE.fix: query args were not printing out
		fxt.Run_parse("en.wikipedia.org/wiki/Special:Search/Earth?fulltext=yes").Chk_build_str_is_same();
	}
	@Test   public void Anchor_with_slash() {	// PURPOSE: A/b#c/d was not parsing correctly
		fxt.Run_parse("A/b#c/d").Chk_page("A/b").Chk_anchor("c.2Fd");
	}
	@Test  public void Slash() {
		fxt.Run_parse("en.wikipedia.org/wiki//A").Chk_wiki("en.wikipedia.org").Chk_page("/A");
		fxt.Run_parse("en.wikipedia.org/wiki/A//b").Chk_wiki("en.wikipedia.org").Chk_page("A//b");
		fxt.Run_parse("en.wikipedia.org/wiki///A").Chk_wiki("en.wikipedia.org").Chk_page("//A");
	}
	@Test  public void Question_is_page() {
		fxt.Run_parse("A?B").Chk_wiki("en.wikipedia.org").Chk_page("A?B").Chk_anchor(null);
	}
	@Test  public void Question_is_anchor() {
		fxt.Run_parse("A#b?c").Chk_wiki("en.wikipedia.org").Chk_page("A").Chk_anchor("b.3Fc");
	}
	@Test  public void Title_remove_w() {	// PURPOSE: fix /w/ showing up as seg; DATE:2014-05-30
		fxt.Run_parse("http://en.wikipedia.org/w/index.php?title=A").Chk_wiki("en.wikipedia.org").Chk_page("A");
	}
}
class Xoa_url_parser_fxt {
	private final Xoae_app app; private final Xowe_wiki cur_wiki;
	private Xoa_url actl_url;
	public Xoa_url_parser_fxt() {
		this.app = Xoa_app_fxt.app_();
		this.cur_wiki = Prep_create_wiki("en.wikipedia.org");
	}
	public Xowe_wiki Prep_create_wiki(String domain) {
		Xowe_wiki rv = Xoa_app_fxt.wiki_(app, domain);
		Prep_add_xwiki_to_user(domain);
		return rv;
	}
	public Xoa_url_parser_fxt Prep_add_xwiki_to_wiki(String alias, String domain)				{return Prep_xwiki(cur_wiki, alias, domain, null);}
	public Xoa_url_parser_fxt Prep_add_xwiki_to_wiki(String alias, String domain, String fmt)	{return Prep_xwiki(cur_wiki, alias, domain, fmt);}
	public Xoa_url_parser_fxt Prep_add_xwiki_to_user(String domain)								{return Prep_xwiki(app.Usere().Wiki(), domain, domain, null);}
	public Xoa_url_parser_fxt Prep_add_xwiki_to_user(String alias, String domain)				{return Prep_xwiki(app.Usere().Wiki(), alias, domain, null);}
	public Xoa_url_parser_fxt Prep_add_xwiki_to_user(String alias, String domain, String fmt)	{return Prep_xwiki(app.Usere().Wiki(), alias, domain, fmt);}
	private Xoa_url_parser_fxt Prep_xwiki(Xow_wiki wiki, String alias, String domain, String fmt) {
		wiki.Xwiki_mgr().Add_full(Bry_.new_u8(alias), Bry_.new_u8(domain), Bry_.new_u8_safe(fmt));
		return this;
	}
	public Xoa_url_parser_fxt Run_parse(String actl_str) {return Run_parse(cur_wiki, actl_str);}
	public Xoa_url_parser_fxt Run_parse(Xow_wiki wiki, String actl_str) {
		this.actl_url = Xoa_url_parser.Parse_url(app, wiki, actl_str);
		return this;
	}
	public Xoa_url_parser_fxt Run_parse_reuse(String actl_str) {
		byte[] actl_bry = Bry_.new_u8(actl_str);
		Xoa_url_parser.Parse_url(actl_url, app, cur_wiki, actl_bry, 0, actl_bry.length, false);
		return this;
	}
	public Xoa_url_parser_fxt Run_parse_from_url_bar(String raw) {
		this.actl_url = Xoa_url_parser.Parse_from_url_bar(app, cur_wiki, raw);
		return this;
	}
	public Xoa_url_parser_fxt	Chk_wiki(String v) 			{Tfds.Eq_str(v, actl_url.Wiki_bry()	, "wiki"); return this;}
	public Xoa_url_parser_fxt	Chk_page(String v) 			{Tfds.Eq_str(v, actl_url.Page_bry()	, "page"); return this;}
	public Xoa_url_parser_fxt	Chk_anchor(String v) 		{Tfds.Eq_str(v, actl_url.Anchor_bry(), "anch"); return this;}
	public Xoa_url_parser_fxt	Chk_action_is_edit_y() 		{return Chk_action_is_edit_(Bool_.Y);}
	public Xoa_url_parser_fxt	Chk_action_is_edit_n() 		{return Chk_action_is_edit_(Bool_.N);}
	private Xoa_url_parser_fxt	Chk_action_is_edit_(boolean v) {Tfds.Eq_bool(v, actl_url.Action_is_edit(), "action_is_edit"); return this;}
	public Xoa_url_parser_fxt	Chk_to_str(String v) 		{Tfds.Eq_str(v, actl_url.Xto_full_str(), "Xto_full_str"); return this;}
	public Xoa_url_parser_fxt Chk_build_str_is_same() {
		Xoa_url_parser parser = new Xoa_url_parser();
		Tfds.Eq_str(actl_url.Raw(), parser.Build_str(actl_url), "build_str");
		return this;
	}
}
