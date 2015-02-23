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
package gplx.xowa.xtns.wdatas.pfuncs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import org.junit.*;
public class Wdata_pf_property_tst {
	@Before public void init() {fxt.Init();} Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt();
	@Test   public void String() {
		fxt.Init_links_add("enwiki", "Test_page", "q1");
		fxt.Init_pages_add(fxt.doc_("q1", fxt.Make_claim_str(1, "a")));
		fxt.Test_parse("{{#property:p1}}", "a");
		fxt.Test_parse("{{#property:p2}}", "");
	}
	@Test   public void Entity() {
		fxt.Init_links_add("enwiki", "Test_page", "q1");
		fxt.Init_pages_add(fxt.Wdoc_bldr("q2").Add_label("en", "b").Xto_wdoc());
		fxt.Init_pages_add(fxt.doc_("q1", fxt.Make_claim_entity(1, 2)));
		fxt.Test_parse("{{#property:p1}}", "b");
	}
	@Test   public void Entity_fr() {	// PURPOSE: non-English wiki should default to English label if non-English label not available; DATE:2013-12-19
		fxt.Wiki().Wdata_wiki_lang_(Bry_.new_ascii_("fr"));							// set wiki to French
		fxt.Init_links_add("frwiki", "Test_page", "q1");								// create link for en:Test_page in wikidata
		fxt.Init_pages_add(fxt.doc_("q1", fxt.Make_claim_entity(1, 2)));						// create wdata page Q1 with prop entity reference to Q2
		fxt.Init_pages_add(fxt.Wdoc_bldr("q2").Add_label("en", "b").Xto_wdoc());	// create wdata page Q2 with label in en (not fr)
		fxt.Test_parse("{{#property:p1}}", "b");										// parse; should get en label
	}
	@Test   public void Entity_missing() {	// PURPOSE: wiki may refer to entity that no longer exists; EX: {{#property:p1}} which links to Q1, but p1 links to Q2 and Q2 was deleted; DATE:2014-02-01
		fxt.Init_links_add("enwiki", "Test_page", "q1");								// create link for en:Test_page in wikidata
		fxt.Init_pages_add(fxt.doc_("q1", fxt.Make_claim_entity(1, 2)));						// create wdata page Q1 with prop entity reference to Q2; note that Q2 is not created
		fxt.Test_parse("{{#property:p1}}", "");											// parse; get ""
	}
	@Test   public void Time() {
		fxt.Init_links_add("enwiki", "Test_page", "q1");
		fxt.Init_pages_add(fxt.doc_("q1", fxt.Make_claim_time(1, "2012-01-02 03:04:05")));
		fxt.Test_parse("{{#property:p1}}", "+00000002012-01-02T03:04:05Z");
	}
	@Test   public void Geodata() {
		fxt.Init_links_add("enwiki", "Test_page", "q1");
		fxt.Init_pages_add(fxt.doc_("q1", fxt.Make_claim_geo(1, "6.789", "1.2345")));
		fxt.Test_parse("{{#property:p1}}", "1.2345, 6.789");
	}
	@Test   public void Quantity() {
		fxt.Init_links_add("enwiki", "Test_page", "q1");
		fxt.Init_pages_add(fxt.doc_("q1", fxt.Make_claim_quantity(1, "+1,234", "2", "+1,236", "+1232")));
		fxt.Test_parse("{{#property:p1}}", "1,234±2");
	}
	@Test   public void Monolingualtext() {
		fxt.Init_links_add("enwiki", "Test_page", "q1");
		fxt.Init_pages_add(fxt.doc_("q1", fxt.Make_claim_monolingual(1, "la", "Lorem ipsum dolor sit amet")));
		fxt.Test_parse("{{#property:p1}}", "Lorem ipsum dolor sit amet");
	}
	@Test   public void Novalue() {
		fxt.Init_links_add("enwiki", "Test_page", "q1");
		fxt.Init_pages_add(fxt.doc_("q1", fxt.Make_claim_novalue(1)));
		fxt.Test_parse("{{#property:p1}}", "novalue");
	}
	@Test   public void Somevalue() {
		fxt.Init_links_add("enwiki", "Test_page", "q1");
		fxt.Init_pages_add(fxt.doc_("q1", fxt.Make_claim_somevalue(1)));
		fxt.Test_parse("{{#property:p1}}", "somevalue");
	}
	@Test   public void Multiple() {
		fxt.Init_links_add("enwiki", "Test_page", "q1");
		fxt.Init_pages_add(fxt.doc_("q1", fxt.Make_claim_str(1, "a"), fxt.Make_claim_str(1, "b")));
		fxt.Test_parse("{{#property:p1}}", "a, b");
	}
	@Test   public void Q() {
		fxt.Init_links_add("enwiki", "Test_page", "q2");
		fxt.Init_pages_add(fxt.doc_("q2", fxt.Make_claim_str(1, "a")));
		fxt.Test_parse("{{#property:p1|q=q2}}", "a");
	}
	@Test   public void Of() {
		fxt.Init_links_add("enwiki", "Of_page", "q2");
		fxt.Init_pages_add(fxt.doc_("q2", fxt.Make_claim_str(1, "a")));
		fxt.Test_parse("{{#property:p1|of=Of_page}}", "a");
	}
	@Test   public void Pid_as_name() {
		fxt.Init_links_add("enwiki", "Test_page", "q2");
		fxt.Init_pids_add("en", "astronomic symbol", 1);
		fxt.Init_pages_add(fxt.doc_("q2", fxt.Make_claim_str(1, "a")));
		fxt.Test_parse("{{#property:astronomic symbol}}", "a");
	}
	@Test   public void Empty_arg() {	// PURPOSE: {{#property:p1|}} should not fail / warn; DATE:2013-11-15
		fxt.Init_links_add("enwiki", "Test_page", "q2");
		fxt.Init_pids_add("en", "astronomic symbol", 1);
		fxt.Init_pages_add(fxt.doc_("q2", fxt.Make_claim_str(1, "a")));
		fxt.Test_parse("{{#property:p1|}}", "a");
	}
	@Test  public void Data() {
		Wdata_pf_property_data_fxt fxt = new Wdata_pf_property_data_fxt();
		fxt.Init().Expd_id_int_(1).Test_parse("{{#property:p1}}");
		fxt.Init().Expd_id_int_(1).Expd_q_("q2").Test_parse("{{#property:p1|q=q2}}");
		fxt.Init().Expd_id_int_(1).Expd_of_("Earth").Test_parse("{{#property:p1|of=Earth}}");
	}
	@Test   public void Parse_pid() {
		fxt.Test_parse_pid		("p123"	, 123);		// basic
		fxt.Test_parse_pid		("P123"	, 123);		// uppercase
		fxt.Test_parse_pid_null	("population");		// name test
		fxt.Test_parse_pid_null	("123");			// missing p
		fxt.Test_parse_pid_null	("");				// empty String test
	}
}
class Wdata_pf_property_data_fxt {
	public Wdata_pf_property_data_fxt Init() {
		if (app == null) {
			parser_fxt = new Xop_fxt();
			app = parser_fxt.App();
			wdata_mgr = app.Wiki_mgr().Wdata_mgr();
		}
		Io_mgr._.InitEngine_mem();
		wdata_mgr.Clear();
		parser_fxt.Reset();
		expd_id_int = -1;
		expd_q = expd_of = null;
		return this;
	}	private Xoae_app app; private Wdata_wiki_mgr wdata_mgr; private Xop_fxt parser_fxt;
	public Wdata_pf_property_data_fxt Expd_id_int_(int v) {expd_id_int = v; return this;} private int expd_id_int;
	public Wdata_pf_property_data_fxt Expd_q_(String v) {expd_q = Bry_.new_ascii_(v); return this;} private byte[] expd_q;
	public Wdata_pf_property_data_fxt Expd_of_(String v) {expd_of = Bry_.new_ascii_(v); return this;} private byte[] expd_of;
	public void Test_parse(String raw) {
		Wdata_pf_property_data actl = new Wdata_pf_property_data();
		byte[] raw_bry = Bry_.new_utf8_(raw);
		Xowe_wiki wiki = parser_fxt.Wiki(); Xop_ctx ctx = wiki.Ctx();
		Xop_tkn_mkr tkn_mkr = app.Tkn_mkr();
		Wdata_pf_property pfunc = new Wdata_pf_property();
		Xop_root_tkn root = tkn_mkr.Root(raw_bry);
		wiki.Parser().Parse_text_to_wtxt(root, ctx, tkn_mkr, raw_bry);
		Xot_invk tkn = (Xot_invk)root.Subs_get(0);
		actl.Init_by_parse(ctx, raw_bry, Xot_invk_mock.Null, tkn, pfunc);
		
		if (expd_id_int != -1) Tfds.Eq(expd_id_int, actl.Id_int());
		if (expd_q != null) Tfds.Eq_bry(expd_q, actl.Q());
		if (expd_of != null) Tfds.Eq_bry(expd_of, actl.Of());
	}
}
