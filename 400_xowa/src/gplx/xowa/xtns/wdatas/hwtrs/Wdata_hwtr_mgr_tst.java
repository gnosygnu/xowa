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
package gplx.xowa.xtns.wdatas.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import org.junit.*;
import gplx.json.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.xtns.wdatas.parsers.*;
public class Wdata_hwtr_mgr_tst {
	@Before public void init() {fxt.init();} private Wdata_hwtr_mgr_fxt fxt = new Wdata_hwtr_mgr_fxt();
	@Test   public void Write_label() {
		fxt.Test_doc(fxt.Wdoc_bldr()
		.Add_label("en", "en_label")
		.Add_label("de", "de_label").Xto_wdoc(), String_.Concat_lines_nl_skip_last
		( ""
		, "<h2>Labels</h2>"
		, ""
		, "<table class='wikitable'>"
		, "  <tr>"
		, "    <th>Language</th>"
		, "    <th>Label</th>"
		, "  </tr>"
		, "  <tr>"
		, "    <td><code>en</code></td>"
		, "    <td>en_label</td>"
		, "  </tr>"
		, "  <tr>"
		, "    <td><code>de</code></td>"
		, "    <td>de_label</td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Write_descr() {
		fxt.Test_doc(fxt.Wdoc_bldr()
		.Add_description("en", "en_descr")
		.Add_description("de", "de_descr").Xto_wdoc(), String_.Concat_lines_nl_skip_last
		( ""
		, "<h2>Descriptions</h2>"
		, ""
		, "<table class='wikitable'>"
		, "  <tr>"
		, "    <th>Language</th>"
		, "    <th>Description</th>"
		, "  </tr>"
		, "  <tr>"
		, "    <td><code>en</code></td>"
		, "    <td>en_descr</td>"
		, "  </tr>"
		, "  <tr>"
		, "    <td><code>de</code></td>"
		, "    <td>de_descr</td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Write_alias() {
		fxt.Test_doc(fxt.Wdoc_bldr()
		.Add_alias("en", "en_1", "en_2")
		.Add_alias("de", "de_1").Xto_wdoc(), String_.Concat_lines_nl_skip_last
		( ""
		, "<h2>Aliases</h2>"
		, ""
		, "<table class='wikitable'>"
		, "  <tr>"
		, "    <th>Language</th>"
		, "    <th>Alias</th>"
		, "  </tr>"
		, "  <tr>"
		, "    <td><code>en</code></td>"
		, "    <td>en_1<br/>en_2</td>"
		, "  </tr>"
		, "  <tr>"
		, "    <td><code>de</code></td>"
		, "    <td>de_1</td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Write_slink() {
		fxt.Test_doc(fxt.Wdoc_bldr()
		.Add_sitelink("enwiki", "Earth")
		.Add_sitelink("dewiki", "Erde").Xto_wdoc(), String_.Concat_lines_nl_skip_last
		( ""
		, "<h2>Links</h2>"
		, ""
		, "<table class='wikitable'>"
		, "  <tr>"
		, "    <th>Site</th>"
		, "    <th>Link</th>"
		, "    <th>Badges</th>"
		, "  </tr>"
		, "  <tr>"
		, "    <td><a href='/site/en.wikipedia.org/wiki/'><code>enwiki</code></a></td>"
		, "    <td><a href='/site/en.wikipedia.org/wiki/Earth'>Earth</a></td>"
		, "  </tr>"
		, "  <tr>"
		, "    <td><a href='/site/de.wikipedia.org/wiki/'><code>dewiki</code></a></td>"
		, "    <td><a href='/site/de.wikipedia.org/wiki/Erde'>Erde</a></td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Write_claim() {
		fxt
		.Init_resolved_pid(1, "prop_1")
		.Test_doc(fxt.Wdoc_bldr()
		.Add_claims
		( fxt.Wdata_fxt().Make_claim_str(1, "abc")
		).Xto_wdoc(), String_.Concat_lines_nl_skip_last
		( ""
		, "<h2>Claims</h2>"
		, ""
		, "<table class='wikitable'>"
		, "  <tr>"
		, "    <th>Id</th>"
		, "    <th>Name</th>"
		, "    <th>Value</th>"
		, "    <th>References</th>"
		, "    <th>Qualifiers</th>"
		, "    <th>Rank</th>"
		, "  </tr>"
		, "  <tr>"
		, "    <td>1</td>"
		, "    <td>prop_1</td>"
		, "    <td>abc</td>"
		, "    <td></td>"
		, "    <td></td>"
		, "    <td>normal</td>"
		, "  </tr>"
		, "</table>"
		));
	}
	@Test   public void Write_claim_monolingualtext() {
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_monolingual(1, "en", "Motto")
		, "[en] Motto"
		);
	}
	@Test   public void Write_claim_time() {
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_time(1, "2001-02-03 04:05:06")
		, "2001-02-03 04:05:06"
		);
	}
	@Test   public void Write_claim_quantity_ubound_lbound() {
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_quantity(1, "50", "units", "60", "30")
		, "50 +10 / -20 units"
		);
	}
	@Test   public void Write_claim_quantity_same() {
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_quantity(1, "50", "units", "60", "40")
		, "50 ±10 units"
		);
	}
	@Test   public void Write_claim_entity() {
		fxt
		.Init_resolved_qid(1, "item_1")
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_entity(1, 1)
		, "item_1"
		);
	}
	@Test   public void Write_json() {
		Json_doc jdoc = Json_doc.new_apos_("{ 'node':['val_0', 'val_1'] }");
		Wdata_doc wdoc = new Wdata_doc(Bry_.Empty, null, jdoc);
		fxt.Test_json(wdoc, String_.Concat_lines_nl_skip_last
		( ""
		, "<h2>JSON</h2>"
		, ""
		, "<span id=\"xowa-wikidata-json\">"
		, "{ \"node\":"
		, "  [ \"val_0\""
		, "  , \"val_1\""
		, "  ]"
		, "}"
		, "</span>"
		));
	}
}
class Wdata_hwtr_mgr_fxt {
	private Wdata_hwtr_mgr doc_hwtr; private OrderedHash resolved_pids = OrderedHash_.new_bry_(), resolved_qids = OrderedHash_.new_bry_();
	public Wdata_wiki_mgr_fxt Wdata_fxt() {return wdata_fxt;} private Wdata_wiki_mgr_fxt wdata_fxt = new Wdata_wiki_mgr_fxt();
	public void init() {
		if (doc_hwtr == null) {
			doc_hwtr = new Wdata_hwtr_mgr();
			Wdata_hwtr_msgs msgs = new Wdata_hwtr_msgs(Make_msgs());
			doc_hwtr.Init_by_ctor(new Json_parser(), new Wdata_lbl_wkr__test(resolved_pids, resolved_qids), Url_encoder.new_html_href_mw_());
			doc_hwtr.Init_by_lang(msgs);				
		}
		resolved_pids.Clear();
		resolved_qids.Clear();
	}
	private byte[][] Make_msgs() {
		return Bry_.Ary
		( "Contents"
		, "Labels", "Language", "Label"
		, "Aliases", "Language", "Alias"
		, "Descriptions", "Language", "Description"
		, "Links"
		, "Links (Wikipedia)", "Links (Wiktionary)", "Links (Wikisource)", "Links (Wikivoyage)"
		, "Links (Wikiquote)", "Links (Wikibooks)", "Links (Wikiversity)", "Links (Wikinews)", "Links (Special wikis)"
		, "Site", "Link", "Badges"
		, "Claims", "Id", "Name", "Value", "References", "Qualifiers", "Rank"
		, "JSON"
		, ",&#32;", "&#32;", "(~{0})"
		, "+", "-", "±"
		, "—", "?"
		, "preferred", "normal", "deprecated"
		, "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
		, "~{0}0s", "~{0}. century", "~{0}. millenium", "~{0}0,000 years", "~{0}00,000 years", "~{0} million years", "~{0}0 million years", "~{0}00 million years", "~{0} billion years"
		, "~{0} BC", "~{0} ago", "in ~{0}", "<sup>jul</sup>"
		, "N", "S", "E", "W"
		, "°", "′", "″"
		, "&nbsp;m"
		);
	}
	public Wdata_doc_bldr Wdoc_bldr() {return wdoc_bldr;} private Wdata_doc_bldr wdoc_bldr = new Wdata_doc_bldr();
	public Wdata_hwtr_mgr_fxt Init_resolved_pid(int pid, String lbl) {resolved_pids.Add(Wdata_lbl_itm.Make_ttl(Bool_.Y, pid), new Wdata_langtext_itm(Bry_.new_ascii_("en"), Bry_.new_ascii_(lbl))); return this;}
	public Wdata_hwtr_mgr_fxt Init_resolved_qid(int qid, String lbl) {resolved_qids.Add(Wdata_lbl_itm.Make_ttl(Bool_.N, qid), new Wdata_langtext_itm(Bry_.new_ascii_("en"), Bry_.new_ascii_(lbl))); return this;}
	public void Test_doc(Wdata_doc wdoc, String expd) {
		doc_hwtr.Init_by_wdoc(wdoc);
		byte[] actl = doc_hwtr.Write(wdoc);
		Tfds.Eq_str_lines(expd, String_.new_utf8_(actl));
	}
	public void Test_claim_val(Wdata_claim_itm_core claim, String expd) {			
		doc_hwtr.Init_by_wdoc(wdoc_bldr.Add_claims(claim).Xto_wdoc());
		byte[] actl = doc_hwtr.Fmtr_claim().Fmtr_row().Calc_val(claim);
		Tfds.Eq(expd, String_.new_utf8_(actl));
	}
	public void Test_json(Wdata_doc wdoc, String expd) {
		Wdata_fmtr__json fmtr_json = doc_hwtr.Fmtr_json();
		fmtr_json.Init_by_wdoc(doc_hwtr.Fmtr_toc(), wdoc.Jdoc());
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		fmtr_json.XferAry(tmp_bfr, 0);
		Tfds.Eq_str_lines(expd, tmp_bfr.XtoStrAndClear());
	}
}
class Wdata_lbl_wkr__test implements Wdata_lbl_wkr {
	private OrderedHash resolved_pids, resolved_qids;
	public Wdata_lbl_wkr__test(OrderedHash resolved_pids, OrderedHash resolved_qids) {this.resolved_pids = resolved_pids; this.resolved_qids = resolved_qids;}
	public void Resolve(Wdata_lbl_list pid_regy, Wdata_lbl_list qid_regy) {
		pid_regy.Resolve(resolved_pids);
		qid_regy.Resolve(resolved_qids);
	}
}
