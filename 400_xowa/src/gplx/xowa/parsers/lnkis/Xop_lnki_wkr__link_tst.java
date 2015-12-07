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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
import gplx.xowa.langs.cases.*;
public class Xop_lnki_wkr__link_tst {
	@Before public void init() {fxt.Reset(); fxt.Init_para_n_();} private Xop_fxt fxt = new Xop_fxt();
	@Test  public void Link() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=File:B.png|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/File:B.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_blank() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/File:A.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_external() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=http://www.b.org|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"http://www.b.org\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_file_system() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=file:///C/B.png|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"file:///C/B.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_file_ns() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=File:B.png|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/wiki/File:B.png\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_external_relative() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=//fr.wikipedia.org/wiki/|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"https://fr.wikipedia.org/wiki/\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_external_absolute() {
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=http://fr.wikipedia.org/wiki/|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"http://fr.wikipedia.org/wiki/\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_external_double_http() {// PURPOSE.fix: link=http://a.org?b=http://c.org breaks lnki; DATE:2013-02-03
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=//a.org?b=http://c.org]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"https://a.org?b=http://c.org\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_xwiki_relative() {	// NOTE: changed href to return "wiki/" instead of "wiki"; DATE:2013-02-18
		fxt.Init_xwiki_add_user_("fr.wikipedia.org");
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=//fr.wikipedia.org/wiki/|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/site/fr.wikipedia.org/wiki/\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_xwiki_relative_domain_only() {	// lnki_wtr fails if link is only domain; EX: wikimediafoundation.org; [[Image:Wikispecies-logo.png|35px|link=//species.wikimedia.org]]; // NOTE: changed href to return "/wiki/" instead of ""; DATE:2013-02-18
		fxt.Init_xwiki_add_user_("fr.wikipedia.org");
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=//fr.wikipedia.org]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/site/fr.wikipedia.org/wiki/\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_xwiki_absolute() {	// NOTE: changed href to return "wiki/" instead of "wiki"; DATE:2013-02-18
		fxt.Init_xwiki_add_user_("fr.wikipedia.org");
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=http://fr.wikipedia.org/wiki/|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/site/fr.wikipedia.org/wiki/\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_xwiki_absolute_upload() {	// PURPOSE: link to upload.wikimedia.org omits /wiki/; EX: wikimediafoundation.org: [[File:Page1-250px-WMF_AR11_SHIP_spreads_15dec11_72dpi.png|right|125px|border|2010–2011 Annual Report|link=https://upload.wikimedia.org/wikipedia/commons/4/48/WMF_AR11_SHIP_spreads_15dec11_72dpi.pdf]]
		fxt.Init_xwiki_add_user_("commons.wikimedia.org");
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=http://upload.wikimedia.org/wikipedia/commons/7/70/A.png|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/site/commons.wikimedia.org/wiki/File:A.png\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_xwiki_relative_deep() {
		fxt.Init_xwiki_add_user_("fr.wikipedia.org");
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=//fr.wikipedia.org/wiki/A/b|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/site/fr.wikipedia.org/wiki/A/b\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
	}
	@Test  public void Link_xwiki_alias() {	// [[File:Commons-logo.svg|25x25px|link=http://en.wikipedia.org/wiki/commons:Special:Search/Earth|alt=|Search Commons]]
		fxt.Init_xwiki_add_wiki_and_user_("commons", "commons.wikimedia.org");
		fxt.Init_xwiki_add_wiki_and_user_("en.wikipedia.org", "en.wikipedia.org"); // DATE:2015-07-22
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=http://en.wikipedia.org/wiki/commons:B|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/site/commons.wikimedia.org/wiki/B\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
		fxt.Init_xwiki_clear();
	}
	@Test  public void Link_xwiki_alias_sub_page() {	// same as above, but for sub-page; [[File:Commons-logo.svg|25x25px|link=http://en.wikipedia.org/wiki/commons:Special:Search/Earth|alt=|Search Commons]]
		fxt.Init_xwiki_add_wiki_and_user_("commons", "commons.wikimedia.org");
		fxt.Init_xwiki_add_wiki_and_user_("en.wikipedia.org", "en.wikipedia.org");	// DATE:2015-07-22
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=http://en.wikipedia.org/wiki/commons:Special:Search/B|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/site/commons.wikimedia.org/wiki/Special:Search/B\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
		fxt.Init_xwiki_clear();
	}
	@Test  public void Link_xwiki_alias_only() {	// only alias; [[File:Commons-logo.svg|25x25px|link=commons:Special:Search/Earth]]; fictitious example; DATE:2013-02-18
		fxt.Init_xwiki_add_wiki_and_user_("commons", "commons.wikimedia.org");
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=commons:Special:Search/B|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/site/commons.wikimedia.org/wiki/Special:Search/B\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
		fxt.Init_xwiki_clear();
	}
	@Test  public void Link_xwiki_alias_only_colon() {	// only alias, but prepended with ":"; [[File:Wikipedia-logo.svg|40px|link=:w:|Wikipedia]]; DATE:2013-05-06
		fxt.Init_xwiki_add_wiki_and_user_("commons", "commons.wikimedia.org");
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|12x10px|link=:commons:A/B|c]]", String_.Concat_lines_nl_skip_last
		( "<a href=\"/site/commons.wikimedia.org/wiki/A/B\" class=\"image\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"c\" src=\"file:///mem/wiki/repo/trg/thumb/7/0/A.png/12px.png\" width=\"12\" height=\"10\" /></a>"
		));
		fxt.Init_xwiki_clear();
	}
	@Test  public void Encode() {// PURPOSE: encode invalid characters in link; PAGE:en.w:List_of_cultural_heritage_sites_in_Punjab,_Pakistan DATE:2014-07-16
		fxt.Test_parse_page_wiki_str
		( "[[File:A.png|link=//b?c\">|d]]"
		, "<a href=\"https://b?c%22%3E\" rel=\"nofollow\" xowa_title=\"A.png\"><img id=\"xowa_file_img_0\" alt=\"d\" src=\"file:///mem/wiki/repo/trg/orig/7/0/A.png\" width=\"0\" height=\"0\" /></a>"
		);
	}
}
