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
package gplx.xowa.xtns.wbases.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import org.junit.*;
import gplx.langs.jsons.*; import gplx.langs.htmls.encoders.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*; import gplx.xowa.xtns.wbases.parsers.*; import gplx.xowa.apps.apis.xowa.html.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.apps.apis.xowa.xtns.*;
public class Wdata_hwtr_mgr_tst {
	@Before public void init() {fxt.init();} private Wdata_hwtr_mgr_fxt fxt = new Wdata_hwtr_mgr_fxt();
	@Test   public void Stub() {}
//		@Test   public void Write_label() {
//			fxt.Test_doc(fxt.Wdoc_bldr()
//			.Add_label("en", "en_label")
//			.Add_label("de", "de_label").Xto_wdoc(), String_.Concat_lines_nl_skip_last
//			( ""
//			, "<h2>Labels</h2>"
//			, ""
//			, "<table class='wikitable'>"
//			, "  <tr>"
//			, "    <th>Language</th>"
//			, "    <th>Label</th>"
//			, "  </tr>"
//			, "  <tr>"
//			, "    <td><code>en</code></td>"
//			, "    <td>en_label</td>"
//			, "  </tr>"
//			, "  <tr>"
//			, "    <td><code>de</code></td>"
//			, "    <td>de_label</td>"
//			, "  </tr>"
//			, "</table>"
//			));
//		}
//		@Test   public void Write_descr() {
//			fxt.Test_doc(fxt.Wdoc_bldr()
//			.Add_description("en", "en_descr")
//			.Add_description("de", "de_descr").Xto_wdoc(), String_.Concat_lines_nl_skip_last
//			( ""
//			, "<h2>Descriptions</h2>"
//			, ""
//			, "<table class='wikitable'>"
//			, "  <tr>"
//			, "    <th>Language</th>"
//			, "    <th>Description</th>"
//			, "  </tr>"
//			, "  <tr>"
//			, "    <td><code>en</code></td>"
//			, "    <td>en_descr</td>"
//			, "  </tr>"
//			, "  <tr>"
//			, "    <td><code>de</code></td>"
//			, "    <td>de_descr</td>"
//			, "  </tr>"
//			, "</table>"
//			));
//		}
//		@Test   public void Write_alias() {
//			fxt.Test_doc(fxt.Wdoc_bldr()
//			.Add_alias("en", "en_1", "en_2")
//			.Add_alias("de", "de_1").Xto_wdoc(), String_.Concat_lines_nl_skip_last
//			( ""
//			, "<h2>Aliases</h2>"
//			, ""
//			, "<table class='wikitable'>"
//			, "  <tr>"
//			, "    <th>Language</th>"
//			, "    <th>Alias</th>"
//			, "  </tr>"
//			, "  <tr>"
//			, "    <td><code>en</code></td>"
//			, "    <td>en_1<br/>en_2</td>"
//			, "  </tr>"
//			, "  <tr>"
//			, "    <td><code>de</code></td>"
//			, "    <td>de_1</td>"
//			, "  </tr>"
//			, "</table>"
//			));
//		}
//		@Test   public void Write_slink_tbl_one() {
//			fxt
//			.Init_resolved_qid(1, "featured article").Init_resolved_qid(2, "good article")
//			.Test_doc(fxt.Wdoc_bldr()			
//			.Add_sitelink("enwiki", "Earth", "Q1", "Q2")
//			.Add_sitelink("dewiki", "Erde")
//			.Add_sitelink("frwiki", "Terre")
//			.Xto_wdoc(), String_.Concat_lines_nl_skip_last
//			( ""
//			, "  <div class='wikibase-sitelinkgrouplistview'>"
//			, "    <div class='wb-listview'>"
//			, "      <div class='wikibase-sitelinkgroupview' data-wb-sitelinks-group='wikipedia'>"
//			, "        <div class='wikibase-sitelinkgroupview-heading-container'>"
//			, "          <h2 class='wb-section-heading wikibase-sitelinkgroupview-heading' dir='auto' id='sitelinks-wikipedia'>Links (Wikipedia)</h2>"
//			, "        </div>"
//			, "        <table class='wikibase-sitelinklistview'>"
//			, "          <colgroup>"
//			, "            <col class='wikibase-sitelinklistview-sitename' />"
//			, "            <col class='wikibase-sitelinklistview-siteid' />"
//			, "            <col class='wikibase-sitelinklistview-link' />"
//			, "          </colgroup>"
//			, "          <thead>"
//			, "            <tr class='wikibase-sitelinklistview-columnheaders'>"
//			, "              <th class='wikibase-sitelinkview-sitename'>Language</th>"
//			, "              <th class='wikibase-sitelinkview-siteid'>Code</th>"
//			, "              <th class='wikibase-sitelinkview-link'>Linked page</th>"
//			, "            </tr>"
//			, "          </thead>"
//			, "          <tbody>"
//			, "            <tr class='wikibase-sitelinkview wikibase-sitelinkview-enwiki' data-wb-siteid='enwiki'>"
//			, "              <td class='wikibase-sitelinkview-sitename wikibase-sitelinkview-sitename-enwiki' lang='en' dir='auto'>English</td>"
//			, "              <td class='wikibase-sitelinkview-siteid wikibase-sitelinkview-siteid-enwiki'>enwiki</td>"
//			, "              <td class='wikibase-sitelinkview-link wikibase-sitelinkview-link-enwiki' lang='en' dir='auto'>"
//			, "                <span class='wikibase-sitelinkview-badges'>"
//			, "                  <span class='wb-badge wb-badge-Q1 wb-badge-featuredarticle' title='featured article'></span>"
//			, "                  <span class='wb-badge wb-badge-Q2 wb-badge-goodarticle' title='good article'></span>"
//			, "                </span>"
//			, "                <span class='wikibase-sitelinkview-page'>"
//			, "                  <a href='https://en.wikipedia.org/wiki/Earth' hreflang='en' dir='auto'>Earth</a>"
//			, "                </span>"
//			, "              </td>"
//			, "            </tr>"
//			, "            <tr class='wikibase-sitelinkview wikibase-sitelinkview-dewiki' data-wb-siteid='dewiki'>"
//			, "              <td class='wikibase-sitelinkview-sitename wikibase-sitelinkview-sitename-dewiki' lang='de' dir='auto'>Deutsch</td>"
//			, "              <td class='wikibase-sitelinkview-siteid wikibase-sitelinkview-siteid-dewiki'>dewiki</td>"
//			, "              <td class='wikibase-sitelinkview-link wikibase-sitelinkview-link-dewiki' lang='de' dir='auto'>"
//			, "                <span class='wikibase-sitelinkview-badges'>"
//			, "                </span>"
//			, "                <span class='wikibase-sitelinkview-page'>"
//			, "                  <a href='https://de.wikipedia.org/wiki/Erde' hreflang='de' dir='auto'>Erde</a>"
//			, "                </span>"
//			, "              </td>"
//			, "            </tr>"
//			, "            <tr class='wikibase-sitelinkview wikibase-sitelinkview-frwiki' data-wb-siteid='frwiki'>"
//			, "              <td class='wikibase-sitelinkview-sitename wikibase-sitelinkview-sitename-frwiki' lang='fr' dir='auto'>Français</td>"
//			, "              <td class='wikibase-sitelinkview-siteid wikibase-sitelinkview-siteid-frwiki'>frwiki</td>"
//			, "              <td class='wikibase-sitelinkview-link wikibase-sitelinkview-link-frwiki' lang='fr' dir='auto'>"
//			, "                <span class='wikibase-sitelinkview-badges'>"
//			, "                </span>"
//			, "                <span class='wikibase-sitelinkview-page'>"
//			, "                  <a href='https://fr.wikipedia.org/wiki/Terre' hreflang='fr' dir='auto'>Terre</a>"
//			, "                </span>"
//			, "              </td>"
//			, "            </tr>"
//			, "          </tbody>"
//			, "        </table>"
//			, "      </div>"
//			, "    </div>"
//			, "  </div>"
//			));
//		}
//		@Test   public void Write_slink_tbl_many() {
//			fxt.Test_doc(fxt.Wdoc_bldr()
//			.Add_sitelink("enwiki"		, "Earth")
//			.Add_sitelink("enwiktionary", "Earth")
//			.Add_sitelink("enwikiquote"	, "Earth")
//			.Xto_wdoc(), String_.Concat_lines_nl_skip_last
//			( ""
//			, "<p>"
//			, "  <div id='toc' class='toc'>"
//			, "    <div id='toctitle'><h2>Contents</h2></div>"
//			, "    <ul>"
//			, "      <li class='toclevel-1 tocsection-1'><a href='#Links_(Wikipedia)'><span class='tocnumber'>1</span> <span class='toctext'>Links (Wikipedia)</span></a></li>"
//			, "      <li class='toclevel-1 tocsection-2'><a href='#Links_(Wiktionary)'><span class='tocnumber'>2</span> <span class='toctext'>Links (Wiktionary)</span></a></li>"
//			, "      <li class='toclevel-1 tocsection-3'><a href='#Links_(Wikiquote)'><span class='tocnumber'>3</span> <span class='toctext'>Links (Wikiquote)</span></a></li>"
//			, "      <li class='toclevel-1 tocsection-4'><a href='#JSON'><span class='tocnumber'>4</span> <span class='toctext'>JSON</span></a></li>"
//			, "    </ul>"
//			, "  </div>"
//			, "</p>"
//			, ""
//			, "<h2>Links (Wikipedia)</h2>"
//			, ""
//			, "<p>"
//			, "  <table class='wikitable'>"
//			, "    <tr>"
//			, "      <th>Site</th>"
//			, "      <th>Link</th>"
//			, "      <th>Badges</th>"
//			, "    </tr>"
//			, "    <tr>"
//			, "      <td><a href='/site/en.wikipedia.org/wiki/'><code>enwiki</code></a></td>"
//			, "      <td><a href='/site/en.wikipedia.org/wiki/Earth'>Earth</a></td>"
//			, "      <td></td>"
//			, "    </tr>"
//			, "  </table>"
//			, "</p>"
//			, ""
//			, "<h2>Links (Wiktionary)</h2>"
//			, ""
//			, "<p>"
//			, "  <table class='wikitable'>"
//			, "    <tr>"
//			, "      <th>Site</th>"
//			, "      <th>Link</th>"
//			, "      <th>Badges</th>"
//			, "    </tr>"
//			, "    <tr>"
//			, "      <td><a href='/site/en.wiktionary.org/wiki/'><code>enwiktionary</code></a></td>"
//			, "      <td><a href='/site/en.wiktionary.org/wiki/Earth'>Earth</a></td>"
//			, "      <td></td>"
//			, "    </tr>"
//			, "  </table>"
//			, "</p>"
//			, ""
//			, "<h2>Links (Wikiquote)</h2>"
//			, ""
//			, "<p>"
//			, "  <table class='wikitable'>"
//			, "    <tr>"
//			, "      <th>Site</th>"
//			, "      <th>Link</th>"
//			, "      <th>Badges</th>"
//			, "    </tr>"
//			, "    <tr>"
//			, "      <td><a href='/site/en.wikiquote.org/wiki/'><code>enwikiquote</code></a></td>"
//			, "      <td><a href='/site/en.wikiquote.org/wiki/Earth'>Earth</a></td>"
//			, "      <td></td>"
//			, "    </tr>"
//			, "  </table>"
//			, "</p>"
//			));
//		}
//		@Test   public void Write_claim() {
//			Wdata_wiki_mgr_fxt mkr = fxt.Wdata_fxt();			
//			fxt
//			.Init_resolved_pid(1, "prop_1")
//			.Test_doc(fxt.Wdoc_bldr()
//			.Add_claims
//			( mkr.Make_claim_string(1, "abc").Qualifiers_(mkr.Make_qualifiers(mkr.Make_qualifiers_grp(2, mkr.Make_claim_string(2, "qual_2"))))
//			).Xto_wdoc(), String_.Concat_lines_nl_skip_last
//			( ""
//			, "<h2>Claims</h2>"
//			, ""
//			, "<table class='wikitable'>"
//			, "  <tr>"
//			, "    <th>Id</th>"
//			, "    <th>Name</th>"
//			, "    <th>Value</th>"
//			, "    <th>References</th>"
//			, "    <th>Qualifiers</th>"
//			, "    <th>Rank</th>"
//			, "  </tr>"
//			, "  <tr>"
//			, "    <td>1</td>"
//			, "    <td>prop_1</td>"
//			, "    <td>abc</td>"
//			, "    <td></td>"
//			, "    <td></td>"
//			, "    <td>normal</td>"
//			, "  </tr>"
//			, "</table>"
//			));
//		}
//		@Test   public void Write_json() {
//			Json_doc jdoc = Json_doc.new_apos_("{ 'node':['val_0', 'val_1'] }");
//			Wdata_doc wdoc = new Wdata_doc(Bry_.Empty, null, jdoc);
//			fxt.Test_json(wdoc, String_.Concat_lines_nl_skip_last
//			( ""
//			, "<h2>JSON</h2>"
//			, ""
//			, "<pre style=\"overflow:auto\">"
//			, "{ \"node\":"
//			, "  [ \"val_0\""
//			, "  , \"val_1\""
//			, "  ]"
//			, "}"
//			, "</pre>"
//			));
//		}
}
class Wdata_hwtr_mgr_fxt {
	private Wdata_hwtr_mgr doc_hwtr; private Ordered_hash resolved_ttls = Ordered_hash_.New_bry();
	public Wdata_wiki_mgr_fxt Wdata_fxt() {return wdata_fxt;} private Wdata_wiki_mgr_fxt wdata_fxt = new Wdata_wiki_mgr_fxt();
	public void init() {
		if (doc_hwtr == null) {
			doc_hwtr = new Wdata_hwtr_mgr();
			Wdata_hwtr_msgs msgs = Wdata_hwtr_msgs.new_en_();
			Xoapi_toggle_mgr toggle_mgr = new Xoapi_toggle_mgr();
			wdata_fxt.Init();
			toggle_mgr.Ctor_by_app(wdata_fxt.App());	// must init, else null error
			doc_hwtr.Init_by_ctor(new Xoapi_wikibase(), wdata_fxt.Wdata_mgr(), new Wdata_lbl_wkr__test(resolved_ttls), Gfo_url_encoder_.Href, toggle_mgr, new Xow_xwiki_mgr(wdata_fxt.Wiki()));
			doc_hwtr.Init_by_lang(wdata_fxt.Wiki().Lang(), msgs);				
		}
		resolved_ttls.Clear();
		doc_hwtr.Lbl_mgr().Clear();
	}
	public Wdata_doc_bldr Wdoc_bldr() {return wdoc_bldr;} private Wdata_doc_bldr wdoc_bldr = new Wdata_doc_bldr();
	public Wdata_hwtr_mgr_fxt Init_resolved_pid(int pid, String lbl) {resolved_ttls.Add(Wdata_lbl_itm.Make_ttl(Bool_.Y, pid), new Wdata_langtext_itm(Bry_.new_a7("en"), Bry_.new_a7(lbl))); return this;}
	public Wdata_hwtr_mgr_fxt Init_resolved_qid(int qid, String lbl) {resolved_ttls.Add(Wdata_lbl_itm.Make_ttl(Bool_.N, qid), new Wdata_langtext_itm(Bry_.new_a7("en"), Bry_.new_a7(lbl))); return this;}
	public void Test_doc(Wdata_doc wdoc, String expd) {
		doc_hwtr.Init_by_wdoc(wdoc);
		byte[] actl = doc_hwtr.Write(wdoc);
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
	}
	public void Test_claim_val(Wbase_claim_base claim, String expd) {			
		doc_hwtr.Init_by_wdoc(wdoc_bldr.Add_claims(claim).Xto_wdoc());
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		Wdata_visitor__html_wtr html_wtr = new Wdata_visitor__html_wtr().Init(tmp_bfr, wdata_fxt.Wdata_mgr(), doc_hwtr.Msgs(), doc_hwtr.Lbl_mgr(), wdata_fxt.Wiki().Lang(), Bry_.Empty);
		claim.Welcome(html_wtr);
		byte[] actl = tmp_bfr.To_bry_and_clear();
		Tfds.Eq(expd, String_.new_u8(actl));
	}
	public void Test_json(Wdata_doc wdoc, String expd) {
		Wdata_fmtr__json fmtr_json = doc_hwtr.Fmtr_json();
		fmtr_json.Init_by_wdoc(wdoc.Jdoc());
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		fmtr_json.Bfr_arg__add(tmp_bfr);
		Tfds.Eq_str_lines(expd, tmp_bfr.To_str_and_clear());
	}
}
class Wdata_lbl_wkr__test implements Wdata_lbl_wkr {
	private Ordered_hash found;
	public Wdata_lbl_wkr__test(Ordered_hash found) {this.found = found;}
	public void Resolve(Wdata_lbl_mgr lbl_mgr, Wdata_lang_sorter sorter) {lbl_mgr.Resolve(found);}
}
