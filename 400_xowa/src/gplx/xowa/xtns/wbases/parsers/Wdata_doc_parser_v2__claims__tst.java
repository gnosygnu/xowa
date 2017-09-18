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
package gplx.xowa.xtns.wbases.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import org.junit.*;
import gplx.langs.jsons.*; import gplx.xowa.xtns.wbases.core.*;
public class Wdata_doc_parser_v2__claims__tst {
	@Before public void init() {fxt.Init();} private Wdata_doc_parser_v2_fxt fxt = new Wdata_doc_parser_v2_fxt();
	@Test   public void Full__string() {
		fxt.Test_claims(String_.Concat_lines_nl_skip_last
		( "{ 'claims':"
		, "  { 'P1':"
		, "    ["
		, "      { 'mainsnak':"
		, "        { 'snaktype':'value'"
		, "        , 'property':'P1'"
		, "        , 'hash':'84487fc3f93b4f74ab1cc5a47d78f596f0b49390'"
		, "        , 'datavalue':"
		, "          { 'value':'abc'"
		, "          , 'type':'string'"
		, "          }"
		, "        }"
		, "      , 'type':'statement'"
		, "      , 'id':'Q2$e8ba1188-4aec-9e37-a75e-f79466c1913e'"
		, "      , 'rank':'normal'"
		, "      }"
		, "    ]"
		, "  }"
		, "}"
		)
		, fxt.Make_claim_string(1, "abc")
		);
	}
	@Test   public void Full__novalue() {
		fxt.Test_claims(String_.Concat_lines_nl_skip_last
		( "{ 'claims':"
		, "  { 'P1':"
		, "    ["
		, "      { 'mainsnak':"
		, "        { 'snaktype':'novalue'"
		, "        , 'property':'P1'"
		, "        , 'hash':'84487fc3f93b4f74ab1cc5a47d78f596f0b49390'"
		, "        }"
		, "      }"
		, "    ]"
		, "  }"
		, "}"
		)
		, fxt.Make_claim_novalue(1)
		);
	}
	@Test   public void Data__string() {
		fxt.Test_claims_data(String_.Concat_lines_nl_skip_last
		( "{ 'value':'abc'"
		, ", 'type':'string'"
		, "}"
		)
		, fxt.Make_claim_string(1, "abc")
		);
	}
	@Test   public void Data__item() {
		fxt.Test_claims_data(String_.Concat_lines_nl_skip_last
		( "{ 'value':"
		, "  { 'entity-type':'item'"
		, "  , 'numeric-id':'123'"
		, "  }"
		, ", 'type':'wikibase-entityid'"
		, "}"
		)
		, fxt.Make_claim_entity_qid(1, 123)
		);
	}
	@Test   public void Data__property() {
		fxt.Test_claims_data(String_.Concat_lines_nl_skip_last
		( "{ 'value':"
		, "  { 'entity-type':'property'"
		, "  , 'numeric-id':'398'"
		, "  }"
		, ", 'type':'wikibase-entityid'"
		, "}"
		)
		, fxt.Make_claim_entity_pid(1, 398)
		);
	}
	@Test   public void Data__monolingualtext() {
		fxt.Test_claims_data(String_.Concat_lines_nl_skip_last
		( "{ 'value':"
		, "  { 'text':'en_text'"
		, "  , 'language':'en'"
		, "  }"
		, ", 'type':'monolingualtext'"
		, "}"
		)
		, fxt.Make_claim_monolingualtext(1, "en", "en_text")
		);
	}
	@Test   public void Data__globecoordinate() {
		fxt.Test_claims_data(String_.Concat_lines_nl_skip_last
		( "{ 'value':"
		, "  { 'latitude':1.2"
		, "  , 'longitude':3.4"
		, "  , 'altitude':null"
		, "  , 'precision':0.0002"
		, "  , 'globe':'http:\\/\\/www.wikidata.org\\/entity\\/Q2'"
		, "  }"
		, ", 'type':'globecoordinate'"
		, "}"
		)
		, fxt.Make_claim_globecoordinate(1, "1.2", "3.4", "0.0002")
		);
	}
	@Test   public void Data__quantity() {
		fxt.Test_claims_data(String_.Concat_lines_nl_skip_last
		( "{ 'value':"
		, "  { 'amount':'123'"
		, "  , 'unit':'2'"
		, "  , 'upperBound':'125'"
		, "  , 'lowerBound':'121'"
		, "  }"
		, ", 'type':'quantity'"
		, "}"
		)
		, fxt.Make_claim_quantity(1, 123, 2, 125, 121)
		);
	}
	@Test   public void Data__time() {
		fxt.Test_claims_data(String_.Concat_lines_nl_skip_last
		( "{ 'value':"
		, "  { 'time':'+00000002001-02-03T04:05:06Z'"
		, "  , 'timezone':0"
		, "  , 'before':0"
		, "  , 'after':0"
		, "  , 'precision':11"
		, "  , 'calendarmodel':'http:\\/\\/www.wikidata.org\\/entity\\/Q1985727'"
		, "  }"
		, ", 'type':'time'"
		, "}"
		)
		, fxt.Make_claim_time(1, "2001-02-03 04:05:06")
		);
	}
	@Test   public void Data__url() {	// NOTE:has "String" property-type; EX:wd:Q23548; DATE:2016-07-28
		fxt.Test_claims_data(String_.Concat_lines_nl_skip_last
		( "{ 'value':'http:\\/\\/www.nasa.gov\\/rss\\/dyn\\/breaking_news.rss'"
		, ", 'type':'string'"
		, "}"
		)
		, fxt.Make_claim_string(1, "http://www.nasa.gov/rss/dyn/breaking_news.rss")
		);
	}
	@Test   public void Data__commonsMedia() {	// NOTE:has "String" property-type; EX:wd:Q327162; DATE:2016-07-28
		fxt.Test_claims_data(String_.Concat_lines_nl_skip_last
		( "{ 'value':'Tabliczka E40.svg'"
		, ", 'type':'string'"
		, "}"
		)
		, fxt.Make_claim_string(1, "Tabliczka E40.svg")
		);
	}
	@Test   public void Data__externalid() {	// NOTE:has "String" property-type; EX:wd:Q77177; DATE:2016-07-28
		fxt.Test_claims_data(String_.Concat_lines_nl_skip_last
		( "{ 'value':'000331371'"
		, ", 'type':'string'"
		, "}"
		)
		, fxt.Make_claim_string(1, "000331371")
		);
	}
	@Test   public void Data__math() {	// NOTE:has "String" property-type; EX:wd:Q11518; DATE:2016-07-28
		fxt.Test_claims_data(String_.Concat_lines_nl_skip_last
		( "{ 'value':'a^2+b^2=c^2'"
		, ", 'type':'string'"
		, "}"
		)
		, fxt.Make_claim_string(1, "a^2+b^2=c^2")
		);
	}
	// www.wikidata.org/wiki/Q11518
}
