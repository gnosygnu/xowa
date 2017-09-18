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
public class Wdata_doc_parser_v2__basic__tst {
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
	@Test   public void References_empty() { // PURPOSE:sometimes references can have 0 snaks; return back an empty Wbase_claim_grp_list, not null; PAGE:Птичкин,_Евгений_Николаевич; DATE:2015-02-16
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
