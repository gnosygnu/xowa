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
package gplx.xowa.xtns.wbases.pfuncs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import org.junit.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Wdata_pf_property__basic__tst {
	@Before public void init() {fxt.Init();} private final    Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt();
	@Test   public void String() {
		fxt.Init__docs__add(fxt.Wdoc("Q1")
			.Add_claims(fxt.Make_claim_string(1, "a"))
			.Add_sitelink("enwiki", "Test_page")
			);
		fxt.Test_parse("{{#property:p1}}", "a");
		fxt.Test_parse("{{#property:p2}}", "");
	}
	@Test   public void Entity() {
		fxt.Init__docs__add(fxt.Wdoc("Q2")
			.Add_label("en", "b")
			);
		fxt.Init__docs__add(fxt.Wdoc("Q1")
			.Add_claims(fxt.Make_claim_entity_qid(1, 2))
			.Add_sitelink("enwiki", "Test_page")
			);
		fxt.Test_parse("{{#property:p1}}", "b");
	}
	@Test   public void Entity_fr() {	// PURPOSE: non-English wiki should default to English label if non-English label not available; DATE:2013-12-19
		// set wiki to French
		fxt.Wiki().Wdata_wiki_lang_(Bry_.new_a7("fr"));

		fxt.Init__docs__add(fxt.Wdoc("Q1")
			.Add_claims(fxt.Make_claim_entity_qid(1, 2))
			.Add_sitelink("frwiki", "Test_page")
			);

		// create wdata page Q2 with label in en (not fr)
		fxt.Init__docs__add(fxt.Wdoc("Q2")
			.Add_label("en", "b")
			);

		// parse; should get en label
		fxt.Test_parse("{{#property:p1}}", "b");									
	}
	@Test   public void Entity_missing() {	// PURPOSE: wiki may refer to entity that no longer exists; EX: {{#property:p1}} which links to Q1, but p1 links to Q2 and Q2 was deleted; DATE:2014-02-01
		fxt.Init__docs__add(fxt.Wdoc("Q1")				
			.Add_claims(fxt.Make_claim_entity_qid(1, 2)) // create wdata page Q1 with prop entity reference to Q2; note that Q2 is not created
			.Add_sitelink("enwiki", "Test_page")
			);

		fxt.Test_parse("{{#property:p1}}", "");										// parse; get ""
	}
	@Test   public void Time() {
		fxt.Init__docs__add(fxt.Wdoc("Q1")				
			.Add_claims(fxt.Make_claim_time(1, "2012-01-02 03:04:05"))
			.Add_sitelink("enwiki", "Test_page")
			);

		fxt.Test_parse("{{#property:p1}}", "30405 2 Jan 2012");	// NOTE: format is missing ":" b/c test does not init messages for html_wtr;  DATE:2015-08-03
	}
	@Test   public void Geodata() {
		fxt.Init__docs__add(fxt.Wdoc("Q1")				
			.Add_claims(fxt.Make_claim_geo(1, "6.789", "1.2345"))
			.Add_sitelink("enwiki", "Test_page")
			);

		fxt.Test_parse("{{#property:p1}}", "1°14&#39;4.2&#34;N, 6°47&#39;20.4&#34;E");
	}
	@Test   public void Quantity__plus_minus__y() {
		fxt.Init__docs__add(fxt.Wdoc("Q1")				
			.Add_claims(fxt.Make_claim_quantity(1, "+1234", "1", "+1236", "+1232"))
			.Add_sitelink("enwiki", "Test_page")
			);

		fxt.Test_parse("{{#property:p1}}", "1,234±2");
	}
	@Test   public void Quantity__plus_minus__n() {	// PURPOSE:do not output ± if lbound == val == ubound; PAGE:en.w:Tintinan DATE:2015-08-02
		fxt.Init__docs__add(fxt.Wdoc("Q1")				
			.Add_claims(fxt.Make_claim_quantity(1, "+1234", "1", "+1234", "+1234"))
			.Add_sitelink("enwiki", "Test_page")
			);

		fxt.Test_parse("{{#property:p1}}", "1,234");
	}
	@Test   public void Quantity__range() {	// PURPOSE:do not output ± if lbound == val == ubound; PAGE:en.w:Tintinan DATE:2015-08-02
		fxt.Init__docs__add(fxt.Wdoc("Q1")				
			.Add_claims(fxt.Make_claim_quantity(1, "+1234", "1", "+1236", "+1233"))
			.Add_sitelink("enwiki", "Test_page")
			);

		fxt.Test_parse("{{#property:p1}}", "1,233-1,236");
	}
	@Test   public void Quantity__long() {	// PURPOSE: must cast to long for large numbers; EX:{{#property:P1082}} PAGE:en.w:Earth; DATE:2015-08-02
		fxt.Init__docs__add(fxt.Wdoc("Q1")				
			.Add_claims(fxt.Make_claim_quantity(1, "+4321000000", "1", "4321000000", "4321000000"))
			.Add_sitelink("enwiki", "Test_page")
			);

		fxt.Test_parse("{{#property:p1}}", "4,321,000,000");
	}
	@Test   public void Quantity__unit__entity() {// PURPOSE: get entity name; EX:{{#invoke:Wikidata|getUnits|P2386|FETCH_WIKIDATA}} PAGE:en.w:Arecibo_Observatory; DATE:2016-10-11
		fxt.Init__docs__add(fxt.Wdoc("Q1")				
			.Add_claims(fxt.Make_claim_quantity(1, "+1234", "http://www.wikidata.org/entity/Q2", "+1236", "+1232"))
			.Add_sitelink("enwiki", "Test_page")
			);

		fxt.Init__docs__add(fxt.Wdoc("Q2")				
			.Add_claims(fxt.Make_claim_string(2, "a"))
			.Add_label("en", "meter")
			);

		fxt.Test_parse("{{#property:p1}}", "1,234±2 meter");
	}
	@Test   public void Quantity__decimal() {
		fxt.Init__docs__add(fxt.Wdoc("Q1")				
			.Add_claims(fxt.Make_claim_quantity(1, "+1234.50", "1", "+1236.75", "+1232.25"))
			.Add_sitelink("enwiki", "Test_page")
			);

		fxt.Test_parse("{{#property:p1}}", "1,234.5±2.25");
	}
	@Test   public void Monolingualtext() {
		fxt.Init__docs__add(fxt.Wdoc("Q1")				
			.Add_claims(fxt.Make_claim_monolingual(1, "la", "Lorem ipsum dolor sit amet"))
			.Add_sitelink("enwiki", "Test_page")
			);

		fxt.Test_parse("{{#property:p1}}", "Lorem ipsum dolor sit amet");
	}
	@Test   public void Novalue() {
		fxt.Init__docs__add(fxt.Wdoc("Q1")				
			.Add_claims(fxt.Make_claim_novalue(1))
			.Add_sitelink("enwiki", "Test_page")
			);

		fxt.Test_parse("{{#property:p1}}", "novalue");
	}
	@Test   public void Somevalue() {
		fxt.Init__docs__add(fxt.Wdoc("Q1")				
			.Add_claims(fxt.Make_claim_somevalue(1))
			.Add_sitelink("enwiki", "Test_page")
			);

		fxt.Test_parse("{{#property:p1}}", "somevalue");
	}
	@Test   public void Multiple() {
		fxt.Init__docs__add(fxt.Wdoc("Q1")				
			.Add_claims(fxt.Make_claim_string(1, "a"), fxt.Make_claim_string(1, "b"))
			.Add_sitelink("enwiki", "Test_page")
			);

		fxt.Test_parse("{{#property:p1}}", "a");	// only take first; DATE:2015-08-02
	}
	@Test   public void Q() {
		fxt.Init__docs__add(fxt.Wdoc("Q2")				
			.Add_claims(fxt.Make_claim_string(1, "a"))
			.Add_sitelink("enwiki", "Test_page")
			);

		fxt.Test_parse("{{#property:p1|q=Q2}}", "a");
	}
	@Test   public void Of() {
		fxt.Init__docs__add(fxt.Wdoc("Q2")				
			.Add_claims(fxt.Make_claim_string(1, "a"))
			.Add_sitelink("enwiki", "Of_page")
			);

		fxt.Test_parse("{{#property:p1|of=Of_page}}", "a");
	}
	@Test   public void From() {
		fxt.Init__docs__add(fxt.Wdoc("Property:P2")
			.Add_claims(fxt.Make_claim_string(1, "a"))
			);

		fxt.Test_parse("{{#property:p1|from=P2}}", "a");
		fxt.Test_parse("{{#property:p1|from=}}", "");
		fxt.Test_parse("{{#property:p1| from = P2 }}", "a"); // PURPOSE: trim ws; ISSUE#:361; DATE:2019-02-11
	}
	@Test   public void Pid_as_name() {
		fxt.Init__docs__add(fxt.Wdoc("Q2")
			.Add_claims(fxt.Make_claim_string(1, "a"))
			.Add_sitelink("enwiki", "Test_page")
			);
		fxt.Init_pids_add("en", "astronomic symbol", 1);

		fxt.Test_parse("{{#property:astronomic symbol}}", "a");
	}
	@Test   public void Empty_arg() {	// PURPOSE: {{#property:p1|}} should not fail / warn; DATE:2013-11-15
		fxt.Init__docs__add(fxt.Wdoc("Q2")
			.Add_claims(fxt.Make_claim_string(1, "a"))
			.Add_sitelink("enwiki", "Test_page")
			);
		fxt.Init_pids_add("en", "astronomic symbol", 1);

		fxt.Test_parse("{{#property:p1|}}", "a");
	}
	@Test   public void Parse_pid() {
		fxt.Test_parse_pid		("p123"	, 123);		// basic
		fxt.Test_parse_pid		("P123"	, 123);		// uppercase
		fxt.Test_parse_pid_null	("population");		// name test
		fxt.Test_parse_pid_null	("123");			// missing p
		fxt.Test_parse_pid_null	("");				// empty String test
	}
}
