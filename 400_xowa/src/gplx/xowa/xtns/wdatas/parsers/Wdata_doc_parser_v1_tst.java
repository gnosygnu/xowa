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
abstract class Wdata_doc_parser_fxt_base {
	protected Wdata_doc_parser parser;
	public void Init() {
		if (parser == null) parser = Make_parser();
	}
	public abstract Wdata_doc_parser Make_parser();
	public Wdata_sitelink_itm Make_sitelink(String site, String name, String... badges) {return new Wdata_sitelink_itm(Bry_.new_utf8_(site), Bry_.new_utf8_(name), Bry_.Ary(badges));}
	public Wdata_langtext_itm Make_langval(String lang, String text) {return new Wdata_langtext_itm(Bry_.new_utf8_(lang), Bry_.new_utf8_(text));}
	public Wdata_alias_itm Make_alias(String lang, String... vals) {return new Wdata_alias_itm(Bry_.new_utf8_(lang), Bry_.Ary(vals));}
	public Wdata_claim_itm_core Make_claim_str(int pid, String val) {return new Wdata_claim_itm_str(pid, Wdata_dict_snak_tid.Tid_value, Bry_.new_utf8_(val));}
	public Wdata_claim_itm_core Make_claim_entity(int pid, int entityId) {return new Wdata_claim_itm_entity(pid, Wdata_dict_snak_tid.Tid_value, Int_.Xto_bry(entityId));}
	public Wdata_claim_itm_core Make_claim_monolingualtext(int pid, String lang, String text) {return new Wdata_claim_itm_monolingualtext(pid, Wdata_dict_snak_tid.Tid_value, Bry_.new_utf8_(lang), Bry_.new_utf8_(text));}
	public Wdata_claim_itm_core Make_claim_globecoordinate(int pid, String lat, String lng, String prc) {return new Wdata_claim_itm_globecoordinate(pid, Wdata_dict_snak_tid.Tid_value, Bry_.new_utf8_(lat), Bry_.new_utf8_(lng), Bry_.new_ascii_("null"), Bry_.new_utf8_(prc), Bry_.new_ascii_("http://www.wikidata.org/entity/Q2"));}
	public Wdata_claim_itm_core Make_claim_quantity(int pid, int val, int unit, int ubound, int lbound) {return new Wdata_claim_itm_quantity(pid, Wdata_dict_snak_tid.Tid_value, Bry_.new_utf8_(Int_.Xto_str(val)), Bry_.new_utf8_(Int_.Xto_str(unit)), Bry_.new_utf8_(Int_.Xto_str(ubound)), Bry_.new_utf8_(Int_.Xto_str(lbound)));}
	public Wdata_claim_itm_core Make_claim_time(int pid, String val) {return new Wdata_claim_itm_time(pid, Wdata_dict_snak_tid.Tid_value, Wdata_doc_bldr.Xto_time(val), Wdata_dict_value_time.Val_timezone_bry, Wdata_dict_value_time.Val_before_bry, Wdata_dict_value_time.Val_after_bry, Wdata_dict_value_time.Val_precision_bry, Wdata_dict_value_time.Val_calendarmodel_bry);}
	public void Test_entity(String raw, String expd)		{Tfds.Eq(expd, String_.new_utf8_(parser.Parse_qid(Json_doc.new_apos_(raw))));}
	public void Test_sitelinks(String raw, Wdata_sitelink_itm... expd) {
		OrderedHash actl_hash = parser.Parse_sitelinks(Q1_bry, Json_doc.new_apos_(raw));
		Tfds.Eq_ary_str((Wdata_sitelink_itm[])actl_hash.XtoAry(Wdata_sitelink_itm.class), expd);
	}
	public void Test_labels(String raw, Wdata_langtext_itm... expd)		{Test_langvals(raw, Bool_.Y, expd);}
	public void Test_descriptions(String raw, Wdata_langtext_itm... expd)	{Test_langvals(raw, Bool_.N, expd);}
	private void Test_langvals(String raw, boolean labels_or_descriptions, Wdata_langtext_itm... expd) {
		OrderedHash actl_hash = parser.Parse_langvals(Q1_bry, Json_doc.new_apos_(raw), labels_or_descriptions);
		Tfds.Eq_ary_str((Wdata_langtext_itm[])actl_hash.XtoAry(Wdata_langtext_itm.class), expd);
	}
	public void Test_aliases(String raw, Wdata_alias_itm... expd) {
		OrderedHash actl_hash = parser.Parse_aliases(Q1_bry, Json_doc.new_apos_(raw));
		Tfds.Eq_ary_str((Wdata_alias_itm[])actl_hash.XtoAry(Wdata_alias_itm.class), expd);
	}
	public void Test_claims(String raw, Wdata_claim_itm_core... expd) {
		OrderedHash actl_hash = parser.Parse_claims(Json_doc.new_apos_(raw));
		ListAdp actl_list = Wdata_claim_grp.Xto_list(actl_hash);
		Tfds.Eq_ary_str((Wdata_claim_itm_core[])actl_list.XtoAry(Wdata_claim_itm_core.class), expd);
	}
	public void Test_claims_data(String raw, Wdata_claim_itm_core expd) {
		Json_doc jdoc = Json_doc.new_apos_(raw);
		Wdata_claim_itm_base actl = parser.Parse_claims_data(1, Wdata_dict_snak_tid.Tid_value, jdoc.Root());
		Tfds.Eq(expd.toString(), actl.toString());
	}
	private static final byte[] Q1_bry = Bry_.new_ascii_("Q1");
}
class Wdata_doc_parser_v1_fxt extends Wdata_doc_parser_fxt_base {
	@Override public Wdata_doc_parser Make_parser() {return new Wdata_doc_parser_v1();}
}
