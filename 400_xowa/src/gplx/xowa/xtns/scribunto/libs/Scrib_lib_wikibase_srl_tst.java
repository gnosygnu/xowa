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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.langs.jsons.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*; import gplx.xowa.xtns.wbases.parsers.*;	
public class Scrib_lib_wikibase_srl_tst {
	@Before public void init() {fxt.Clear();} private Scrib_lib_wikibase_srl_fxt fxt = new Scrib_lib_wikibase_srl_fxt();
	@Test   public void Label() {
		fxt.Init_label("en", "Earth").Init_label("fr", "Terre").Init_label("de", "Erde");
		fxt.Test
		(	"labels:"
		,	"  en:"
		,	"    language:'en'"
		,	"    value:'Earth'"
		,	"  fr:"
		,	"    language:'fr'"
		,	"    value:'Terre'"
		,	"  de:"
		,	"    language:'de'"
		,	"    value:'Erde'"
		,	""
		);
	}
	@Test   public void Description() {
		fxt.Init_description("en", "Earth").Init_description("fr", "Terre").Init_description("de", "Erde");
		fxt.Test
		(	"descriptions:"
		,	"  en:"
		,	"    language:'en'"
		,	"    value:'Earth'"
		,	"  fr:"
		,	"    language:'fr'"
		,	"    value:'Terre'"
		,	"  de:"
		,	"    language:'de'"
		,	"    value:'Erde'"
		,	""
		);
	}
	@Test   public void Sitelinks() {
		fxt.Init_link("enwiki", "Earth").Init_link("frwiki", "Terre").Init_link("dewiki", "Erde");
		fxt.Test
		(	"sitelinks:"
		,	"  enwiki:"
		,	"    site:'enwiki'"
		,	"    title:'Earth'"
		,	"    badges:"
		,	"  frwiki:"
		,	"    site:'frwiki'"
		,	"    title:'Terre'"
		,	"    badges:"
		,	"  dewiki:"
		,	"    site:'dewiki'"
		,	"    title:'Erde'"
		,	"    badges:"
		,	""
		);
	}
	@Test   public void Sitelinks_both_formats() {	// PURPOSE: check that both formats are serializable; DATE:2014-02-06
		Json_doc jdoc = fxt.Wdata_fxt().App().Utl__json_parser().Parse_by_apos_ary
		(	"{ 'entity':['item',2]"
		,	", 'links':"
		,	"  {"
		,	"    'enwiki':'Earth'"							// old format
		,	"  , 'frwiki':{'name':'Terre','badges':['Q3']}"	// new format
		,	"  }"
		,	"}"
		);
		Wdata_doc wdoc = new Wdata_doc(Bry_.new_a7("q2"), fxt.Wdata_fxt().App().Wiki_mgr().Wdata_mgr(), jdoc);
		fxt.Test
		(	wdoc
		,	"sitelinks:"
		,	"  enwiki:"
		,	"    site:'enwiki'"
		,	"    title:'Earth'"
		,	"    badges:"
		,	"  frwiki:"
		,	"    site:'frwiki'"
		,	"    title:'Terre'"
		,	"    badges:"
		,	"      1:'Q3'"
		,	""
		);
	}
	@Test   public void Aliases() {
		fxt.Init_alias("en", "en_0", "en_1", "en_2").Init_alias("fr", "fr_0").Init_alias("de", "de_0", "de_1");
		fxt.Test
		(	"aliases:"
		,	"  en:"
		,	"    1:"
		,	"      language:'en'"
		,	"      value:'en_0'"
		,	"    2:"
		,	"      language:'en'"
		,	"      value:'en_1'"
		,	"    3:"
		,	"      language:'en'"
		,	"      value:'en_2'"
		,	"  fr:"
		,	"    1:"
		,	"      language:'fr'"
		,	"      value:'fr_0'"
		,	"  de:"
		,	"    1:"
		,	"      language:'de'"
		,	"      value:'de_0'"
		,	"    2:"
		,	"      language:'de'"
		,	"      value:'de_1'"
		,	""
		);
	}
	@Test   public void Claims_str() {
		fxt.Init_prop(fxt.Wdata_fxt().Make_claim_string(2, "Moon"));
		fxt.Test
		(	"claims:"
		,	"  P2:"
		,	"    1:"
		,	"      id:'P2'"
		,	"      mainsnak:"
		,	"        datavalue:"
		,	"          type:'string'"
		,	"          value:'Moon'"
		,	"        property:'P2'"
		,	"        snaktype:'value'"
		,	"        datatype:'string'"
		,	"      rank:'normal'"
		,	"      type:'statement'"        
		,	""
		);
	}
	@Test   public void Claims_somevalue() {	// PURPOSE: changed to not return value-node; PAGE:it.s:Autore:Anonimo DATE:2015-12-06 // somevalue should always return value node; EX:w:Joseph-François_Malgaigne; DATE:2014-04-07;
		fxt.Init_prop(fxt.Wdata_fxt().Make_claim_somevalue(2));
		fxt.Test
		(	"claims:"
		,	"  P2:"
		,	"    1:"
		,	"      id:'P2'"
		,	"      mainsnak:"
		,	"        property:'P2'"
		,	"        snaktype:'somevalue'"
		,	"        datatype:'unknown'"
		,	"      rank:'normal'"
		,	"      type:'statement'"        
		,	""
		);
	}
	@Test   public void Claims_novalue() {	// PURPOSE: novalue must return empty array (no datavalue node in json); PAGE:ru.w:Лимонов,_Эдуард_Вениаминович; DATE:2015-02-16
		fxt.Init_prop(fxt.Wdata_fxt().Make_claim_novalue(2));
		fxt.Test
		(	"claims:"
		,	"  P2:"
		,	"    1:"
		,	"      id:'P2'"
		,	"      mainsnak:"
		,	"        property:'P2'"
		,	"        snaktype:'novalue'"
		,	"        datatype:'unknown'"
		,	"      rank:'normal'"
		,	"      type:'statement'"        
		,	""
		);
	}
	@Test   public void Claims_entity() {
		fxt.Init_prop(fxt.Wdata_fxt().Make_claim_entity_qid(2, 3));
		fxt.Test
		(	"claims:"
		,	"  P2:"
		,	"    1:"
		,	"      id:'P2'"
		,	"      mainsnak:"
		,	"        datavalue:"
		,	"          type:'wikibase-entityid'"
		,	"          value:"
		,	"            entity-type:'item'"
		,	"            numeric-id:'3'"
		,	"            id:'q3'"
		,	"        property:'P2'"
		,	"        snaktype:'value'"
		,	"        datatype:'wikibase-item'"
		,	"      rank:'normal'"
		,	"      type:'statement'"        
		,	""
		);
	}
	@Test   public void Claims_base_0() {	// PURPOSE: test for legacyStyle (aka base_0); used by pl.w:Module:Wikidane; DATE:2014-05-09
		fxt.Init_prop(fxt.Wdata_fxt().Make_claim_entity_qid(2, 3));
		fxt.Test(true
		,	"claims:"
		,	"  P2:"
		,	"    0:"
		,	"      id:'P2'"
		,	"      mainsnak:"
		,	"        datavalue:"
		,	"          type:'wikibase-entityid'"
		,	"          value:"
		,	"            entity-type:'item'"
		,	"            numeric-id:'3'"
		,	"            id:'q3'"
		,	"        property:'P2'"
		,	"        snaktype:'value'"
		,	"        datatype:'wikibase-item'"
		,	"      rank:'normal'"
		,	"      type:'statement'"        
		,	"  p2:"
		,	"    0:"
		,	"      id:'P2'"
		,	"      mainsnak:"
		,	"        datavalue:"
		,	"          type:'wikibase-entityid'"
		,	"          value:"
		,	"            entity-type:'item'"
		,	"            numeric-id:'3'"
		,	"            id:'q3'"
		,	"        property:'P2'"
		,	"        snaktype:'value'"
		,	"        datatype:'wikibase-item'"
		,	"      rank:'normal'"
		,	"      type:'statement'"        
		,	""
		);
	}
	@Test   public void Claims_time() {
		fxt.Init_prop(fxt.Wdata_fxt().Make_claim_time(2, "2001-02-03 04:05:06", 9));
		fxt.Test
		(	"claims:"
		,	"  P2:"
		,	"    1:"
		,	"      id:'P2'"
		,	"      mainsnak:"
		,	"        datavalue:"
		,	"          type:'time'"
		,	"          value:"
		,	"            time:'+00000002001-02-03T04:05:06Z'"
		,	"            precision:'9'"
		,	"            before:'0'"
		,	"            after:'0'"
		,	"            timezone:'0'"
		,	"            calendarmodel:'http://www.wikidata.org/entity/Q1985727'"
		,	"        property:'P2'"
		,	"        snaktype:'value'"
		,	"        datatype:'time'"
		,	"      rank:'normal'"
		,	"      type:'statement'"        
		,	""
		);
	}
	@Test   public void Claims_globecoordinate() {
		fxt.Init_prop(fxt.Wdata_fxt().Make_claim_geo(2, "6.789", "1.2345"));
		fxt.Test
		(	"claims:"
		,	"  P2:"
		,	"    1:"
		,	"      id:'P2'"
		,	"      mainsnak:"
		,	"        datavalue:"
		,	"          type:'globecoordinate'"
		,	"          value:"
		,	"            latitude:'1.2345'"
		,	"            longitude:'6.789'"
		,	"            altitude:null"
		,	"            globe:'http://www.wikidata.org/entity/Q2'"
		,	"            precision:'1.0E-5'"	
		,	"        property:'P2'"
		,	"        snaktype:'value'"
		,	"        datatype:'globe-coordinate'"
		,	"      rank:'normal'"
		,	"      type:'statement'"
		,	""
		);
	}
	@Test   public void Claims_quantity() {
		fxt.Init_prop(fxt.Wdata_fxt().Make_claim_quantity(2, "+1,234", "2", "+1,236", "+1232"));
		fxt.Test
		(	"claims:"
		,	"  P2:"
		,	"    1:"
		,	"      id:'P2'"
		,	"      mainsnak:"
		,	"        datavalue:"
		,	"          type:'quantity'"
		,	"          value:"
		,	"            amount:'1234'"
		,	"            unit:'2'"
		,	"            upperBound:'1236'"
		,	"            lowerBound:'1232'"
		,	"        property:'P2'"
		,	"        snaktype:'value'"
		,	"        datatype:'quantity'"
		,	"      rank:'normal'"
		,	"      type:'statement'"        
		,	""
		);
	}
	@Test   public void Claims_monolingualtext() {// PURPOSE.fix: type was mistakenly "language" instead of "monolingualtext" DATE:2014-10-21
		fxt.Init_prop(fxt.Wdata_fxt().Make_claim_monolingual(2, "en", "en_text"));
		fxt.Test
		(	"claims:"
		,	"  P2:"
		,	"    1:"
		,	"      id:'P2'"
		,	"      mainsnak:"
		,	"        datavalue:"
		,	"          type:'monolingualtext'"
		,	"          value:"
		,	"            text:'en_text'"
		,	"            language:'en'"
		,	"        property:'P2'"
		,	"        snaktype:'value'"
		,	"        datatype:'monolingualtext'"
		,	"      rank:'normal'"
		,	"      type:'statement'"        
		,	""
		);
	}
	@Test   public void Qualifiers() {
		Wdata_wiki_mgr_fxt wdata_fxt = fxt.Wdata_fxt();
		fxt.Init_prop(wdata_fxt.Make_claim_string(2, "Earth").Qualifiers_(wdata_fxt.Make_qualifiers(wdata_fxt.Make_qualifiers_grp(3, wdata_fxt.Make_claim_time(3, "2001-02-03 04:05:06")))));
		fxt.Test
		(	"claims:"
		,	"  P2:"
		,	"    1:"
		,	"      id:'P2'"
		,	"      mainsnak:"
		,	"        datavalue:"
		,	"          type:'string'"
		,	"          value:'Earth'"
		,	"        property:'P2'"
		,	"        snaktype:'value'"
		,	"        datatype:'string'"
		,	"      rank:'normal'"
		,	"      type:'statement'"
		,	"      qualifiers:"
		,	"        P3:"
		,	"          1:"
		,	"            datavalue:"
		,	"              type:'time'"
		,	"              value:"
		,	"                time:'+00000002001-02-03T04:05:06Z'"
		,	"                precision:'14'"
		,	"                before:'0'"
		,	"                after:'0'"
		,	"                timezone:'0'"
		,	"                calendarmodel:'http://www.wikidata.org/entity/Q1985727'"
		,	"            property:'P3'"
		,	"            snaktype:'value'"
		,	"            datatype:'time'"
		,	""
		);
	}
	@Test   public void Claims_time_typed() {
		Wbase_claim_time claim = (Wbase_claim_time)fxt.Wdata_fxt().Make_claim_time(2, "2001-02-03 04:05:06", 9);
		Scrib_lib_wikibase_srl_visitor visitor = new Scrib_lib_wikibase_srl_visitor();
		visitor.Visit_time(claim);
		Keyval keyval = Keyval_find_.Find(true, visitor.Rv(), "value", "timezone");
		Gftest.Eq__str("timezone", keyval.Key());
		Gftest.Eq__int(0, (int)keyval.Val());	// fails when keyval.Val() is String; DATE:2016-10-28
	}
	@Test   public void Claims__commonsMedia() {
		fxt.Wdata_fxt().Wdata_mgr().Prop_mgr().Loader_(Wbase_prop_mgr_loader_.New_mock(Keyval_.new_("P2", "commonsMedia")));
		fxt.Init_prop(fxt.Wdata_fxt().Make_claim_string(2, "abc"));
		fxt.Test
		(	"claims:"
		,	"  P2:"
		,	"    1:"
		,	"      id:'P2'"
		,	"      mainsnak:"
		,	"        datavalue:"
		,	"          type:'string'"
		,	"          value:'abc'"
		,	"        property:'P2'"
		,	"        snaktype:'value'"
		,	"        datatype:'commonsMedia'"
		,	"      rank:'normal'"
		,	"      type:'statement'"        
		,	""
		);
	}
	@Test   public void Type_is_property() {	// PURPOSE: type should be "property"; PAGE:ru.w:Викитека:Проект:Викиданные DATE:2016-11-23
		fxt.Wdata_fxt().Wdata_mgr().Prop_mgr().Loader_(Wbase_prop_mgr_loader_.New_mock(Keyval_.new_("P1", "commonsMedia")));
		fxt.Init_header_enabled_y_();
		fxt.Wdata_fxt().doc_("Property:P1", fxt.Wdata_fxt().Make_claim_string(123, "abc"));
		fxt.Test
		( "id:'Property:P1'"
		, "type:'property'"
		, "schemaVersion:'2'"
		, "datatype:'commonsMedia'"
		, ""
		);
	}
	@Test   public void Type_is_item() {	// PURPOSE: type should be "item"; PAGE:ru.w:Викитека:Проект:Викиданные DATE:2016-11-23
		fxt.Init_header_enabled_y_();
		fxt.Wdata_fxt().doc_("Q2", fxt.Wdata_fxt().Make_claim_string(123, "abc"));
		fxt.Test
		( "id:'Q2'"
		, "type:'item'"
		, "schemaVersion:'2'"
		, ""
		);
	}
	@Test   public void Numeric_id_is_int() {	// PURPOSE: assert that numeric-id is integer, not String, else will fail when comparing directly to integer; PAGE:en.w:Hollywood_Walk_of_Fame DATE:2016-12-17
		Wbase_claim_entity claim = (Wbase_claim_entity)fxt.Wdata_fxt().Make_claim_entity_qid(123, 456);
		Scrib_lib_wikibase_srl_visitor visitor = new Scrib_lib_wikibase_srl_visitor();
		visitor.Visit_entity(claim);
		Keyval keyval = Keyval_find_.Find(true, visitor.Rv(), "value", "numeric-id");
		Gftest.Eq__int(456, (int)keyval.Val());	// NOTE: must be 456 not "456"
	}
}	
class Scrib_lib_wikibase_srl_fxt {
	private Wdata_doc_bldr wdoc_bldr;
	private Wbase_prop_mgr prop_mgr;
	public void Clear() {
		wdata_fxt = new Wdata_wiki_mgr_fxt();
		wdata_fxt.Init();
		wdoc_bldr = wdata_fxt.Wdoc_bldr("q2");
		header_enabled = false;
		this.prop_mgr = wdata_fxt.App().Wiki_mgr().Wdata_mgr().Prop_mgr();
	}
	public Wdata_wiki_mgr_fxt Wdata_fxt() {return wdata_fxt;} private Wdata_wiki_mgr_fxt wdata_fxt;
	private boolean header_enabled;
	public Scrib_lib_wikibase_srl_fxt Init_header_enabled_y_() {header_enabled = true; return this;}
	public Scrib_lib_wikibase_srl_fxt Init_label(String lang, String label) {
		wdoc_bldr.Add_label(lang, label);
		return this;
	}
	public Scrib_lib_wikibase_srl_fxt Init_description(String lang, String description) {
		wdoc_bldr.Add_description(lang, description);
		return this;
	}
	public Scrib_lib_wikibase_srl_fxt Init_link(String xwiki, String val) {
		wdoc_bldr.Add_sitelink(xwiki, val);
		return this;
	}
	public Scrib_lib_wikibase_srl_fxt Init_alias(String lang, String... ary) {
		wdoc_bldr.Add_alias(lang, ary);
		return this;
	}
	public Scrib_lib_wikibase_srl_fxt Init_prop(Wbase_claim_base prop) {wdoc_bldr.Add_claims(prop); return this;}
	public Scrib_lib_wikibase_srl_fxt Test(String... expd) {return Test(false, expd);}
	public Scrib_lib_wikibase_srl_fxt Test(boolean base0, String... expd) {
		Keyval[] actl = Scrib_lib_wikibase_srl.Srl(prop_mgr, wdoc_bldr.Xto_wdoc(), header_enabled, base0);
		Tfds.Eq_ary_str(expd, String_.SplitLines_nl(Xto_str(actl)));
		return this;
	}
	public Scrib_lib_wikibase_srl_fxt Test(Wdata_doc wdoc, String... expd) {return Test(false, wdoc, expd);}
	public Scrib_lib_wikibase_srl_fxt Test(boolean base0, Wdata_doc wdoc, String... expd) {
		Keyval[] actl = Scrib_lib_wikibase_srl.Srl(prop_mgr, wdoc, header_enabled, base0);
		Tfds.Eq_ary_str(expd, String_.SplitLines_nl(Xto_str(actl)));
		return this;
	}
	private String Xto_str(Keyval[] ary) {
		Bry_bfr bfr = Bry_bfr_.New();
		Xto_str(bfr, ary, 0);
		return bfr.To_str_and_clear();
	}
	private void Xto_str(Bry_bfr bfr, Keyval[] ary, int depth) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Keyval kv = ary[i];
			Xto_str(bfr, kv, depth);
		}
	}
	private void Xto_str(Bry_bfr bfr, Keyval kv, int depth) {
		bfr.Add_byte_repeat(Byte_ascii.Space, depth * 2);
		bfr.Add_str_u8(kv.Key()).Add_byte(Byte_ascii.Colon);
		Object kv_val = kv.Val();
		if		(kv_val == null) 							{bfr.Add_str_a7("null").Add_byte_nl(); return;}
		Class<?> kv_val_cls = kv_val.getClass();
		if 	(Type_adp_.Eq(kv_val_cls, Keyval[].class)) 	{bfr.Add_byte_nl(); Xto_str(bfr, (Keyval[])kv_val, depth + 1);}
		else if (Type_adp_.Eq(kv_val_cls, Keyval[].class)) 	{bfr.Add_byte_nl(); Xto_str(bfr, (Keyval)kv_val, depth + 1);}
		else bfr.Add_byte(Byte_ascii.Apos).Add_str_u8(Object_.Xto_str_strict_or_empty(kv_val)).Add_byte(Byte_ascii.Apos).Add_byte_nl();
	}
}
