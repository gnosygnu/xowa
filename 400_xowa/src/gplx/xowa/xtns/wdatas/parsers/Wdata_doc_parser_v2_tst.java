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
package gplx.xowa.xtns.wdatas.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import org.junit.*;
import gplx.json.*; import gplx.xowa.xtns.wdatas.core.*;
public class Wdata_doc_parser_v2_tst {
	@Before public void init() {fxt.Init();} private Wdata_doc_parser_v2_fxt fxt = new Wdata_doc_parser_v2_fxt();
	@Test   public void Entity() {
		fxt.Test_entity("{ 'id':'Q2' }", "q2");
	}
	@Test   public void Sitelink() {
		fxt.Test_sitelinks(String_.Concat_lines_nl_skip_last
		( "{ 'sitelinks':"
		, "  { 'enwiki':"
		, "    { 'site':'enwiki'"
		, "    , 'title':'en_val'"
		, "    , 'badges':"
		, "    [ 'Q10'"
		, "    , 'Q11'"
		, "    , 'Q12'"
		, "    ]"
		, "    }"
		, "  , 'dewiki':"
		, "    { 'site':'dewiki'"
		, "    , 'title':'de_val'"
		, "    , 'badges':"
		, "    [ 'Q2'"
		, "    ]"
		, "    }"
		, "  , 'frwiki':"
		, "    { 'site':'frwiki'"
		, "    , 'title':'fr_val'"
		, "    , 'badges':[]"
		, "    }"
		, "  }"
		, "}"
		)
		, fxt.Make_sitelink("enwiki", "en_val", "Q10", "Q11", "Q12")
		, fxt.Make_sitelink("dewiki", "de_val", "Q2")
		, fxt.Make_sitelink("frwiki", "fr_val")
		);
	}
	@Test   public void Labels() {
		fxt.Test_labels(String_.Concat_lines_nl_skip_last
		( "{ 'labels':"
		, "  { 'en':"
		, "    { 'language':'enwiki'"
		, "    , 'value':'en_val'"
		, "    }"
		, "  , 'de':"
		, "    { 'language':'dewiki'"
		, "    , 'value':'de_val'"
		, "    }"
		, "  , 'fr':"
		, "    { 'language':'frwiki'"
		, "    , 'value':'fr_val'"
		, "    }"
		, "  }"
		, "}"
		)
		, fxt.Make_langval("en", "en_val")
		, fxt.Make_langval("de", "de_val")
		, fxt.Make_langval("fr", "fr_val")
		);
	}
	@Test   public void Descriptions() {
		fxt.Test_descriptions(String_.Concat_lines_nl_skip_last
		( "{ 'descriptions':"
		, "  { 'en':"
		, "    { 'language':'enwiki'"
		, "    , 'value':'en_val'"
		, "    }"
		, "  , 'de':"
		, "    { 'language':'dewiki'"
		, "    , 'value':'de_val'"
		, "    }"
		, "  , 'fr':"
		, "    { 'language':'frwiki'"
		, "    , 'value':'fr_val'"
		, "    }"
		, "  }"
		, "}"
		)
		, fxt.Make_langval("en", "en_val")
		, fxt.Make_langval("de", "de_val")
		, fxt.Make_langval("fr", "fr_val")
		);
	}
	@Test   public void Aliases() {
		fxt.Test_aliases(String_.Concat_lines_nl_skip_last
		( "{ 'aliases':"
		, "  { 'en':"
		, "    ["
		, "      { 'language':'en'"
		, "      , 'value':'en_val_1'"
		, "      }"
		, "    ,"
		, "      { 'language':'en'"
		, "      , 'value':'en_val_2'"
		, "      }"
		, "    ,"
		, "      { 'language':'en'"
		, "      , 'value':'en_val_3'"
		, "      }"
		, "    ]"
		, "  ,"
		, "    'de':"
		, "    ["
		, "      { 'language':'de'"
		, "      , 'value':'de_val_1'"
		, "      }"
		, "    ,"
		, "      { 'language':'de'"
		, "      , 'value':'de_val_2'"
		, "      }"
		, "    ]"
		, "  ,"
		, "    'fr':"
		, "    ["
		, "      { 'language':'fr'"
		, "      , 'value':'fr_val_1'"
		, "      }"
		, "    ]"
		, "  }"
		, "}"
		)
		, fxt.Make_alias("en", "en_val_1", "en_val_2", "en_val_3")
		, fxt.Make_alias("de", "de_val_1", "de_val_2")
		, fxt.Make_alias("fr", "fr_val_1")
		);
	}
	@Test   public void Claims() {
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
		, fxt.Make_claim_str(1, "abc")
		);
	}
	@Test   public void Claims_novalue() {
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
	@Test   public void Claims_data_string() {
		fxt.Test_claims_data(String_.Concat_lines_nl_skip_last
		( "{ 'value':'abc'"
		, ", 'type':'string'"
		, "}"
		)
		, fxt.Make_claim_str(1, "abc")
		);
	}
	@Test   public void Claims_data_entity() {
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
	@Test   public void Claims_data_property() {
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
	@Test   public void Claims_data_monolingualtext() {
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
	@Test   public void Claims_data_globecoordinate() {
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
	@Test   public void Claims_data_quantity() {
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
	@Test   public void Claims_data_time() {
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
	@Test   public void Qualifiers() {
		fxt.Test_qualifiers(String_.Concat_lines_nl_skip_last
		( "{ 'qualifiers':"
		, "  { 'P1':"
		, "    [ "
		, "      { 'snaktype':'value'"
		, "      , 'property':'P1'"
		, "      , 'hash':''"
		, "      , 'datavalue':"
		, "        { 'value':"
		, "        { 'entity-type':'item'"
		, "        , 'numeric-id':11"
		, "        }"
		, "        , 'type':'wikibase-entityid'"
		, "        }"
		, "      }"
		, "    ,"
		, "      { 'snaktype':'value'"
		, "      , 'property':'P1'"
		, "      , 'hash':''"
		, "      , 'datavalue':"
		, "        { 'value':"
		, "        { 'entity-type':'item'"
		, "        , 'numeric-id':12"
		, "        }"
		, "        , 'type':'wikibase-entityid'"
		, "        }"
		, "      }"
		, "    ]"
		, "  ,"
		, "    'P2':"
		, "    [ "
		, "      { 'snaktype':'value'"
		, "      , 'property':'P2'"
		, "      , 'hash':''"
		, "      , 'datavalue':"
		, "        { 'value':"
		, "        { 'entity-type':'item'"
		, "        , 'numeric-id':21"
		, "        }"
		, "        , 'type':'wikibase-entityid'"
		, "        }"
		, "      }"
		, "    ]"
		, "  }"
		, "}"
		), fxt.Make_claim_entity_qid(1, 11), fxt.Make_claim_entity_qid(1, 12), fxt.Make_claim_entity_qid(2, 21)
		);
	}
	@Test   public void Pid_order() {
		fxt.Test_pid_order
		( "{ 'qualifiers-order':['P1', 'P2', 'P3'] }"
		, 1, 2, 3
		);
	}
	@Test   public void References() {
		fxt.Test_references(String_.Concat_lines_nl_skip_last
		( "{ 'references':"
		, "  [ "
		, "    { 'hash':'8e7d51e38606193465d2a1e9d41ba490e06682a6'"
		, "    , 'snaks':"
		, "      { 'P2':"
		, "        [ "
		, "          { 'snaktype':'value'"
		, "          , 'property':'P2'"
		, "          , 'hash':'358e3c0ffa2bfecfe962b39141d99dc2d482110f'"
		, "          , 'datavalue':"
		, "            { 'value':"
		, "              { 'entity-type':'item'"
		, "              , 'numeric-id':21"
		, "              }"
		, "            , 'type':'wikibase-entityid'"
		, "            }"
		, "          }"
		, "        ]"
		, "      , 'P3':"
		, "        [ "
		, "          { 'snaktype':'value'"
		, "          , 'property':'P3'"
		, "          , 'hash':'358e3c0ffa2bfecfe962b39141d99dc2d482110f'"
		, "          , 'datavalue':"
		, "            { 'value':"
		, "              { 'entity-type':'item'"
		, "              , 'numeric-id':31"
		, "              }"
		, "            , 'type':'wikibase-entityid'"
		, "            }"
		, "          }"
		, "        ]"
		, "      }"
		, "    , 'snaks-order':"
		, "      [ 'P2'"
		, "      , 'P3'"
		, "      ]"
		, "    }"
		, "  ]"
		, "}"
		), Int_.Ary(2, 3), fxt.Make_claim_entity_qid(2, 21), fxt.Make_claim_entity_qid(3, 31))
		;
	}
	@Test   public void References_empty() { // PURPOSE:sometimes references can have 0 snaks; return back an empty Wdata_claim_grp_list, not null; PAGE:Птичкин,_Евгений_Николаевич; DATE:2015-02-16
		fxt.Test_references(String_.Concat_lines_nl_skip_last
		( "{ 'references':"
		, "  [ "
		, "    { 'hash':'8e7d51e38606193465d2a1e9d41ba490e06682a6'"
		, "    , 'snaks':[]"
		, "    , 'snaks-order':[]"
		, "    }"
		, "  ]"
		, "}"
		), Int_.Ary_empty)
		;
	}
}
