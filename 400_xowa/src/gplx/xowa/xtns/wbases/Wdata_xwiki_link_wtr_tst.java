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
package gplx.xowa.xtns.wbases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.langs.jsons.*;
import gplx.xowa.wikis.domains.*;
public class Wdata_xwiki_link_wtr_tst {
	@Before public void init() {fxt.Init();} private final    Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt();
	@Test  public void Skip_xwiki_lang_for_self() {	// PURPOSE: list of language links should not include self
		fxt.Init_xwikis_add("en", "fr", "de");
		fxt.Init_qids_add("en", Xow_domain_tid_.Tid__wikipedia, "Q1_en", "Q1");
		fxt.Init__docs__add(fxt.Wdoc_bldr("Q1").Add_sitelink("enwiki", "Q1_en").Add_sitelink("frwiki", "Q1_fr").Add_sitelink("dewiki", "Q1_de").Xto_wdoc());
		fxt.Test_xwiki_links("Q1_en", "Q1_fr", "Q1_de");
	}
	@Test   public void No_external_lang_links__de() {
		fxt.Init_xwikis_add("fr", "de");
		fxt.Init_qids_add("en", Xow_domain_tid_.Tid__wikipedia, "Q1_en", "Q1");
		fxt.Init__docs__add(fxt.Wdoc_bldr("Q1").Add_sitelink("enwiki", "Q1_en").Add_sitelink("frwiki", "Q1_fr").Add_sitelink("dewiki", "Q1_de").Xto_wdoc());
		fxt.Init_external_links_mgr_add("de");
		fxt.Test_xwiki_links("Q1_en", "Q1_de");
		fxt.Init_external_links_mgr_clear();
		fxt.Test_parse_langs("{{noexternallanglinks:de}}", String_.Concat_lines_nl
		( "<div id=\"xowa-lang\">"
		, "  <h5><a href='javascript:xowa_toggle_visible(\"wikidata-langs\");' style='text-decoration: none !important;'>In other languages<img id='wikidata-langs-toggle-icon' src='file:///mem/xowa/bin/any/xowa/file/app.general/twisty_right.png' title='' /></a> (links: 1)  (<a href=\"/site/www.wikidata.org/wiki/Q1\">wikidata</a>)</h5>"
		, "  <div id='wikidata-langs-toggle-elem' style='display:none;'>"
		, "  <h4>grp1</h4>"
		, "  <table style='width: 100%;'>"
		, "    <tr>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>German</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-none'><a hreflang=\"de\" title=\"Q1 de\" href=\"/site/de.wikipedia.org/wiki/Q1 de\">Q1 de</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "      <td/>"
		, "      <td/>"
		, "    </tr>"
		, "  </table>"
		, "  </div>"
		, "</div>"
		));
	}
	@Test   public void Links_w_name_fmt() {	// PURPOSE: wikidata changed links node from "enwiki:A" to "enwiki:{name:A,badges:[]}"; DATE:2013-09-14
		fxt.Init_xwikis_add("en", "fr", "de");
		fxt.Init_qids_add("en", Xow_domain_tid_.Tid__wikipedia, "Q1_en", "Q1");
		Json_doc jdoc = fxt.App().Utl__json_parser().Parse(String_.Concat_lines_nl
		( "{ \"entity\":\"q1\""
		, ", \"links\":"
		, "  { \"dewiki\":\"q1_de\""
		, "  , \"frwiki\":{\"name\":\"q1_fr\",\"badges\":[]}"
		, "  }"
		, "}"
		));
		Wdata_doc wdata_doc = new Wdata_doc(Bry_.new_a7("Q1"), fxt.App().Wiki_mgr().Wdata_mgr(), jdoc);
		fxt.Init__docs__add(wdata_doc);
		fxt.Test_xwiki_links("Q1_en", "q1_de", "q1_fr");
	}
	@Test   public void Same_lang_but_different_domains() {	// PURPOSE: if two entries for same lang, but one is in different domain, use the one for the current wiki  DATE:2014-06-21
		fxt.Init_xwikis_add("en", "fr", "de");
		fxt.Init_qids_add("en", Xow_domain_tid_.Tid__wikipedia, "Q1_en", "Q1");
		Json_doc jdoc = fxt.App().Utl__json_parser().Parse(String_.Concat_lines_nl
		( "{ \"entity\":\"q1\""
		, ", \"links\":"
		, "  { \"dewiki\":\"q1_de\""
		, "  , \"frwiki\":{\"name\":\"q1_fr\",\"badges\":[]}"
		,  "   , \"dewikisource\":\"q1_dewikisource\""		// this should be ignored
		, "  }"
		, "}"
		));
		Wdata_doc wdata_doc = new Wdata_doc(Bry_.new_a7("Q1"), fxt.App().Wiki_mgr().Wdata_mgr(), jdoc);
		fxt.Init__docs__add(wdata_doc);
		fxt.Test_xwiki_links("Q1_en", "q1_de", "q1_fr");
	}
	@Test   public void Badges() {
		fxt.Init_xwikis_add("de", "fr", "pl");
		fxt.Init_qids_add("en", Xow_domain_tid_.Tid__wikipedia, "Q1_en", "Q1");
		fxt.Init__docs__add
		( fxt.Wdoc_bldr("Q1")
		.Add_sitelink("enwiki", "Q1_en")
		.Add_sitelink("dewiki", "Q1_de", "Q17437796")
		.Add_sitelink("frwiki", "Q1_fr", "Q17437798")
		.Add_sitelink("plwiki", "Q1_pl", "Q17559452")
		.Xto_wdoc());
		fxt.Test_parse_langs("", String_.Concat_lines_nl
		( "<div id=\"xowa-lang\">"
		, "  <h5><a href='javascript:xowa_toggle_visible(\"wikidata-langs\");' style='text-decoration: none !important;'>In other languages<img id='wikidata-langs-toggle-icon' src='file:///mem/xowa/bin/any/xowa/file/app.general/twisty_right.png' title='' /></a> (links: 3)  (<a href=\"/site/www.wikidata.org/wiki/Q1\">wikidata</a>)</h5>"
		, "  <div id='wikidata-langs-toggle-elem' style='display:none;'>"
		, "  <h4>grp1</h4>"
		, "  <table style='width: 100%;'>"
		, "    <tr>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>French</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-goodarticle'><a hreflang=\"fr\" title=\"Q1 fr\" href=\"/site/fr.wikipedia.org/wiki/Q1 fr\">Q1 fr</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>German</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-featuredarticle'><a hreflang=\"de\" title=\"Q1 de\" href=\"/site/de.wikipedia.org/wiki/Q1 de\">Q1 de</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "      <td style='width: 10%; padding-bottom: 5px;'>Polish</td><td style='width: 20%; padding-bottom: 5px;'><li class='badge-recommendedarticle'><a hreflang=\"pl\" title=\"Q1 pl\" href=\"/site/pl.wikipedia.org/wiki/Q1 pl\">Q1 pl</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
		, "    </tr>"
		, "  </table>"
		, "  </div>"
		, "</div>"
		));
	}
//		@Test   public void No_wikidata_link() {
//			fxt.Init_xwikis_add("fr", "de");
//			fxt.Test_parse_langs("[[fr:A]]", String_.Concat_lines_nl
//			( "<div id=\"xowa-lang\">"
//			, "  <h5>In other languages</h5>"
//			, "  <h4>grp1</h4>"
//			, "  <ul style='-moz-column-count: 3; list-style:none;'>"
//			, "    <li><span style='display:inline-block; min-width:150px'>French</span><a hreflang=\"fr\" title=\"A\" href=\"/site/fr.wikipedia.org/wiki/A\">A</a></li>"
//			, "    <li><span style='display:inline-block; min-width:150px'>French</span><a hreflang=\"fr\" title=\"A\" href=\"/site/fr.wikipedia.org/wiki/A\">A</a></li>"
//			, "  </ul>"
//			, "</div>"
//			));
//		}
//		@Test   public void No_external_lang_links__sort() {
//			fxt.Init_xwikis_add("de", "fr");
//			fxt.Init_qids_add("en", Xow_domain_tid_.Tid__wikipedia, "Q1_en", "Q1");
//			fxt.Init__docs__add("Q1", fxt.page_bldr_("Q1").Add_sitelink("enwiki", "Q1_en").Add_sitelink("frwiki", "Q1_fr").Add_sitelink("dewiki", "Q1_de").Xto_page_doc());
//			fxt.Init_external_links_mgr_add("*");
//			fxt.Test_xwiki_links("Q1_en", "Q1_de", "Q1_fr");
//			fxt.Init_external_links_mgr_clear();
//			fxt.Test_parse_langs("{{noexternallanglinks:*}}", String_.Concat_lines_nl
//			( "<div id=\"xowa-lang\">"
//			, "  <h5>In other languages (<a href=\"/site/www.wikidata.org/wiki/Q1\">wikidata</a>)</h5>"
//			, "  <h4>grp1</h4>"
//			, "  <ul style='-moz-column-count: 3; list-style:none;'>"
//			, "    <li><span style='display:inline-block; min-width:150px'>German</span><a hreflang=\"de\" title=\"Q1 de\" href=\"/site/de.wikipedia.org/wiki/Q1 de\">Q1 de</a></li>"
//			, "    <li><span style='display:inline-block; min-width:150px'>French</span><a hreflang=\"fr\" title=\"Q1 fr\" href=\"/site/fr.wikipedia.org/wiki/Q1 fr\">Q1 fr</a></li>"
//			, "  </ul>"
//			, "</div>"
//			));
//		}
}
