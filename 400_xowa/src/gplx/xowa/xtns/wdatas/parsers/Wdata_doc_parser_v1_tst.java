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
import gplx.langs.jsons.*; import gplx.xowa.xtns.wdatas.core.*;
public class Wdata_doc_parser_v1_tst {
	@Before public void init() {fxt.Init();} private Wdata_doc_parser_v1_fxt fxt = new Wdata_doc_parser_v1_fxt();
	@Test   public void Entity_v1_1() {
		fxt.Test_entity("{ 'entity':'q1' }", "q1");
	}
	@Test   public void Entity_v1_2() {
		fxt.Test_entity("{ 'entity':['item',1] }", "q1");
	}
	@Test   public void Sitelink_v1_1() {
		fxt.Test_sitelinks(String_.Concat_lines_nl_skip_last
		( "{ 'links':"
		, "  { 'enwiki':'en_val'"
		, "  , 'dewiki':'de_val'"
		, "  , 'frwiki':'fr_val'"
		, "  }"
		, "}"
		)
		, fxt.Make_sitelink("enwiki", "en_val")
		, fxt.Make_sitelink("dewiki", "de_val")
		, fxt.Make_sitelink("frwiki", "fr_val")
		);
	}
	@Test   public void Sitelink_v1_2() {
		fxt.Test_sitelinks(String_.Concat_lines_nl_skip_last
		( "{ 'links':"
		, "  { 'enwiki':"
		, "    { 'name':'en_val'"
		, "    , 'badges':"
		, "    [ 'Q10'"
		, "    , 'Q11'"
		, "    , 'Q12'"
		, "    ]"
		, "    }"
		, "  , 'dewiki':"
		, "    { 'name':'de_val'"
		, "    , 'badges':"
		, "    [ 'Q2'"
		, "    ]"
		, "    }"
		, "  , 'frwiki':"
		, "    { 'name':'fr_val'"
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
		( "{ 'label':"
		, "  { 'en':'en_val'"
		, "  , 'de':'de_val'"
		, "  , 'fr':'fr_val'"
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
		( "{ 'description':"
		, "  { 'en':'en_val'"
		, "  , 'de':'de_val'"
		, "  , 'fr':'fr_val'"
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
		, "    [ 'en_val_1'"
		, "    , 'en_val_2'"
		, "    , 'en_val_3'"
		, "    ]"
		, "  ,"
		, "    'de':"
		, "    [ 'de_val_1'"
		, "    , 'de_val_2'"
		, "    ]"
		, "  ,"
		, "    'fr':"
		, "    [ 'fr_val_1'"
		, "    ]"
		, "  }"
		, "}"
		)
		, fxt.Make_alias("en", "en_val_1", "en_val_2", "en_val_3")
		, fxt.Make_alias("de", "de_val_1", "de_val_2")
		, fxt.Make_alias("fr", "fr_val_1")
		);
	}
	@Test   public void Aliases_alt() {
		fxt.Test_aliases(String_.Concat_lines_nl_skip_last
		( "{ 'aliases':"
		, "  { 'en':"
		, "    { '0':'en_val_1'"
		, "    , '1':'en_val_2'"
		, "    , '2':'en_val_3'"
		, "    }"
		, "  }"
		, "}"
		)
		, fxt.Make_alias("en", "en_val_1", "en_val_2", "en_val_3")
		);
	}
	@Test   public void Claims() {
		fxt.Test_claims(String_.Concat_lines_nl_skip_last
		( "{ 'claims':"
		, "  ["
		, "    { 'm':"
		, "      [ 'value'"
		, "      , 1"
		, "      , 'string'"
		, "      , 'abc'"
		, "      ]"
		, "    , 'q':[]"
		, "    , 'g':'Q2$e8ba1188-4aec-9e37-a75e-f79466c1913e'"
		, "    , 'rank':1"
		, "    , 'refs':[]"
		, "    }"
		, "  ]"
		, "}"
		)
		, fxt.Make_claim_str(1, "abc")
		);
	}
	@Test   public void Claim_bad() {	// wikidata flags several entries as "bad"; https://www.wikidata.org/wiki/Wikidata:Project_chat/Archive/2013/10
		fxt.Test_claims(String_.Concat_lines_nl_skip_last
		(	"{ 'entity':['item',2]"
		,	", 'claims':"
		,	"  ["
		,	"    { 'm':"
		,	"      [ 'value'"
		,	"      , 373"
		,	"      , 'bad'"
		,	"      ,"
		,	"        { 'latitude':1"
		,	"        , 'longitude':2"
		,	"        , 'altitude':null"
		,	"        , 'precision':1"
		,	"        , 'globe':'http:\\/\\/www.wikidata.org\\/entity\\/Q2'"
		,	"        }"
		,	"      ]"
		,	"    }"
		,	"  ]"
		,	"}"
		)
		, fxt.Make_claim_globecoordinate(1, "1", "2", "1")	// assume "bad" is same as globecoordinate; DATE:2014-09-20
		);
	}
}
class Wdata_doc_parser_v1_fxt extends Wdata_doc_parser_fxt_base {
	@Override public Wdata_doc_parser Make_parser() {return new Wdata_doc_parser_v1();}
}
