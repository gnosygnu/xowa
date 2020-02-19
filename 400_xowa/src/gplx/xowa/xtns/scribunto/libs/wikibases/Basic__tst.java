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
package gplx.xowa.xtns.scribunto.libs.wikibases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.libs.*;
import org.junit.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*;
import gplx.xowa.xtns.wbases.claims.enums.*;
public class Basic__tst {
	private final    Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	private final    Wdata_wiki_mgr_fxt wdata_fxt = new Wdata_wiki_mgr_fxt();
	@Before public void init() {
		fxt.Clear_for_lib("en.wikipedia.org", "zh-hans");
		lib = fxt.Core().Lib_wikibase().Init();
		wdata_fxt.Init(fxt.Parser_fxt(), false);
		wdata_fxt.Init_lang_fallbacks("zh-hant", "zh-hk");
	}
	@Test  public void IsValidEntityId() {
		IsValidEntityIdCheck(Bool_.Y, "P1");
		IsValidEntityIdCheck(Bool_.Y, "P123");
		IsValidEntityIdCheck(Bool_.Y, "Q1");
		IsValidEntityIdCheck(Bool_.Y, "Q123");
		IsValidEntityIdCheck(Bool_.Y, "A:B:Q123");

		IsValidEntityIdCheck(Bool_.N, "p1");
		IsValidEntityIdCheck(Bool_.N, "q1");
		IsValidEntityIdCheck(Bool_.N, "P");
		IsValidEntityIdCheck(Bool_.N, "P1A");
		IsValidEntityIdCheck(Bool_.N, "P01");
	}
	private void IsValidEntityIdCheck(boolean expd, String val) {
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_wikibase.Invk_isValidEntityId, Object_.Ary(val), expd);
	}
	@Test  public void EntityExists() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2").Add_label("en", "b").Xto_wdoc());
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_wikibase.Invk_entityExists, Object_.Ary("q2"					), true);
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_wikibase.Invk_entityExists, Object_.Ary("Q1"					), false);
	}
	@Test  public void GetEntityId() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc("Q2")
			.Add_sitelink("enwiki", "Earth")
			);

		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getEntityId, Object_.Ary("Earth"							), "Q2");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getEntityId, Object_.Ary("missing_page"					), null);
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getEntityId, Object_.Ary(""								), null); // PAGE:en.w:Water_treader DATE:2018-07-01
	}
	@Test  public void GetReferencedEntityId() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("Q1").Xto_wdoc());
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("Q2").Add_claims(wdata_fxt.Make_claim_entity_qid(1, 1)).Xto_wdoc());
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("Q3").Add_claims(wdata_fxt.Make_claim_entity_qid(1, 2)).Xto_wdoc());
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getReferencedEntityId, Object_.Ary("Q3", "P1", NewToIds("Q1")), "Q1");
	}
	private static Keyval[] NewToIds(String... toIds) {
		int len = toIds.length;
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++)
			rv[i] = Keyval_.int_(i, toIds[i]);
		return rv;
	}
	@Test  public void GetLabel__cur() {// do not get fallback
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2").Add_label("zh-hans", "s").Add_label("zh-hant", "t").Xto_wdoc());
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_wikibase.Invk_getLabel, Object_.Ary("q2"), String_.Concat_lines_nl_skip_last("1=s", "2=zh-hans"));
	}
	@Test  public void GetLabel__fallback_1() { // get 1st fallback
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2").Add_label("zh-hant", "t").Add_label("zh-hk", "h").Xto_wdoc());
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_wikibase.Invk_getLabel, Object_.Ary("q2"), String_.Concat_lines_nl_skip_last("1=t", "2=zh-hant"));
	}
	@Test  public void GetLabel__fallback_2() {// get 2nd fallback
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2").Add_label("zh-hk", "hk").Xto_wdoc());
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_wikibase.Invk_getLabel, Object_.Ary("q2"), String_.Concat_lines_nl_skip_last("1=hk", "2=zh-hk"));
	}
	@Test  public void GetLabel__fallback_en() {// get en
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2").Add_label("en", "lbl_en").Xto_wdoc());
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_wikibase.Invk_getLabel, Object_.Ary("q2"), String_.Concat_lines_nl_skip_last("1=lbl_en", "2=en"));
	}
	@Test  public void GetDescr() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2").Add_description("zh-hans", "s").Add_description("zh-hant", "t").Xto_wdoc());
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getDescription, Object_.Ary("q2"), "s");
	}
	@Test  public void GetSlink() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2").Add_sitelink("enwiki", "a").Xto_wdoc());
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getSiteLinkPageName, Object_.Ary("q2"), "a");
	}
	@Test  public void GetEntity() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2").Add_label("en", "b").Xto_wdoc());
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_wikibase.Invk_getEntity, Object_.Ary("q2", false), String_.Concat_lines_nl_skip_last
		( "1="
		, "  id=q2"
		, "  type=item"
		, "  schemaVersion=2"
		, "  labels="
		, "    en="
		, "      language=en"
		, "      value=b"
		));
	}
	@Test  public void GetEntity_property() {	// PURPOSE: getEntity should be able to convert "p2" to "Property:P2"; EX:es.w:Arnold_Gesell; DATE:2014-02-18
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("Property:p2").Add_label("en", "b").Xto_wdoc());
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_wikibase.Invk_getEntity, Object_.Ary("p2", false), String_.Concat_lines_nl_skip_last
		( "1="
		, "  id=Property:p2"	// only difference from above
		, "  type=property"		// also, type should be "property"; PAGE:ru.w:Викитека:Проект:Викиданные DATE:2016-11-23
		, "  schemaVersion=2"
		, "  datatype=<<NULL>>"
		, "  labels="
		, "    en="
		, "      language=en"
		, "      value=b"
		));
	}
	@Test  public void GetEntity_ws() {  // PURPOSE: trim, b/c some pages will literally pass in "Property:P5\n"; PAGE:de.w:Mailand–Sanremo_2016 ISSUE#:363; DATE:2019-02-12
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2").Add_label("en", "b").Xto_wdoc());
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_wikibase.Invk_getEntity, Object_.Ary(" q2\n\t", false), String_.Concat_lines_nl_skip_last
		( "1="
		, "  id=q2"
		, "  type=item"
		, "  schemaVersion=2"
		, "  labels="
		, "    en="
		, "      language=en"
		, "      value=b"
		));
	}
//		@Test  public void GetEntity__missing() { // PURPOSE: missing entity should return empty kv array; PAGE:de.w:Critérium_International_2016 DATE:2017-12-30
//			fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_wikibase.Invk_getEntity, Object_.Ary("q2", false), String_.Concat_lines_nl_skip_last
//			( "1=" // not ""
//			));
//		}
	@Test  public void RenderSnaks() {
		Keyval[] args = Wbase_snak_utl_.Get_snaks_ary(wdata_fxt, wdata_fxt.Make_claim_monolingual(3, "en", "P3_en"), wdata_fxt.Make_claim_monolingual(3, "de", "P3_de"));
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_wikibase.Invk_renderSnaks, args, "P3_en, P3_de");
	}
	@Test  public void RenderSnak__entity() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("Q3").Add_label("en", "test_label").Xto_wdoc());
		Keyval[] args = Wbase_snak_utl_.Get_snak(wdata_fxt, wdata_fxt.Make_claim_entity_qid(2, 3));
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_wikibase.Invk_renderSnak, args, "test_label");
	}
	@Test  public void RenderSnak__str() {
		Keyval[] args = Wbase_snak_utl_.Get_snak(wdata_fxt, wdata_fxt.Make_claim_string(3, "test_str"));
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_wikibase.Invk_renderSnak, args, "test_str");
	}
	@Test  public void RenderSnak__quantity() {
		Keyval[] args = Wbase_snak_utl_.Get_snak(wdata_fxt, wdata_fxt.Make_claim_quantity(3, "123", "1", "125", "121"));	// NOTE: entity-less units output "1"; EX:wd:Q493409 DATE:2016-11-08
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_wikibase.Invk_renderSnak, args, "123±2");
	}
	@Test  public void RenderSnak__quantity__null_bounds() {	// PURPOSE: handle null lbound / ubound; PAGE:wd.q:183 DATE:2016-12-03
		Keyval[] args = Wbase_snak_utl_.Get_snak(wdata_fxt, wdata_fxt.Make_claim_quantity(3, "123", "1", null, null));
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_wikibase.Invk_renderSnak, args, "123");
	}
	@Test  public void RenderSnak__time() {
		Keyval[] args = Wbase_snak_utl_.Get_snak(wdata_fxt, wdata_fxt.Make_claim_time(3, "2012-01-02 03:04:05"));
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_wikibase.Invk_renderSnak, args, "30405 2 Jan 2012"); // NOTE: format is missing ":" b/c test does not init messages for html_wtr;  DATE:2015-08-03
	}
	@Test  public void RenderSnak__geo() {
		Keyval[] args = Wbase_snak_utl_.Get_snak(wdata_fxt, wdata_fxt.Make_claim_geo(3, "3.4", "1.2"));
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_wikibase.Invk_renderSnak, args, "1°12&#39;0&#34;N, 3°24&#39;0&#34;E (<a href='/wiki/Q2'>http://www.wikidata.org/entity/Q2</a>)");
	}
	@Test  public void RenderSnak__monolingual() {
		Keyval[] args = Wbase_snak_utl_.Get_snak(wdata_fxt, wdata_fxt.Make_claim_monolingual(3, "en", "abc_en"));
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_wikibase.Invk_renderSnak, args, "abc_en");
	}
	@Test  public void RenderSnak__no_value() {
		Keyval[] args = Wbase_snak_utl_.Get_snak(wdata_fxt, new Wbase_claim_string(3, Wbase_claim_value_type_.Tid__novalue, null));
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_wikibase.Invk_renderSnak, args, "");
	}
	@Test  public void RenderSnak__data_value_is_null() {
		Keyval[] args = Wbase_snak_utl_.Get_snak(wdata_fxt, new Wbase_claim_string(3, Wbase_claim_value_type_.Tid__somevalue, null));
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_wikibase.Invk_renderSnak, args, "");
	}
	@Test  public void FormatValues() {
		Keyval[] args = Wbase_snak_utl_.Get_snaks_ary(wdata_fxt, wdata_fxt.Make_claim_monolingual(3, "en", "P3_en"), wdata_fxt.Make_claim_monolingual(3, "de", "P3_de"));
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_wikibase.Invk_formatValues, args, "P3_en, P3_de");
	}
	@Test  public void FormatValue__str() {
		Keyval[] args = Wbase_snak_utl_.Get_snak(wdata_fxt, wdata_fxt.Make_claim_string(3, "test_str"));
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_wikibase.Invk_formatValue, args, "test_str");
	}
	@Test  public void GetEntityUrl() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getEntityUrl, Object_.Ary("Q2"							), "https://www.wikidata.org/wiki/Special:EntityPage/Q2");
	}
	@Test  public void GetSetting() {
		fxt.Test_scrib_proc_obj(lib, Scrib_lib_wikibase.Invk_getSetting, Object_.Ary("allowArbitraryDataAccess"), true); // PAGE:en.w:Beccles DATE:2018-06-27
	}
	@Test  public void GetSetting__siteGlobalID() {
		fxt.Test_scrib_proc_obj(lib, Scrib_lib_wikibase.Invk_getSetting, Object_.Ary("siteGlobalID"), "enwiki");
	}
	@Test  public void IncrementStatsKey() {
		fxt.Test_scrib_proc_obj(lib, Scrib_lib_wikibase.Invk_incrementStatsKey, Object_.Ary("wikibase.client.scribunto.wikibase.getEntityIdForCurrentPage.call"), null);
	}
	@Test  public void GetEntityModuleName() {
		fxt.Test_scrib_proc_obj(lib, Scrib_lib_wikibase.Invk_getEntityModuleName, Object_.Ary("Q123"), "mw.wikibase.entity");
	}
	@Test  public void GetSiteLinkPageName() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2").Add_sitelink("enwiki", "Test_page").Xto_wdoc());
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getSiteLinkPageName, Object_.Ary("q2"), "Test_page");
	}
	@Test  public void GetSiteLinkPageName_wiki() {// ISSUE#:665; PAGE:commons.wikimedia.org/wiki/Category:Paddy_Ashdown; DATE:2020-02-19
		wdata_fxt.Init_xwikis_add("frwiki");
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2")
			.Add_sitelink("enwiki", "q2_enwiki")
			.Add_sitelink("frwiki", "q2_frwiki")
			.Xto_wdoc());
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getSiteLinkPageName, Object_.Ary("q2", "frwiki"), "q2_frwiki"); // fails if q2_enwiki
	}
}
class Wbase_snak_utl_ {
	public static Keyval[] Get_snaks_ary(Wdata_wiki_mgr_fxt wdata_fxt, Wbase_claim_base... ary) {
		Wdata_doc wdoc = wdata_fxt.Wdoc_bldr("q2").Add_claims(ary).Xto_wdoc();
		return Keyval_.Ary(Keyval_.int_(1, Get_snaks(wdata_fxt, wdoc)));
	}
	public static Keyval[] Get_snak(Wdata_wiki_mgr_fxt wdata_fxt, Wbase_claim_base itm) {
		Wdata_doc wdoc = wdata_fxt.Wdoc_bldr("q2").Add_claims(itm).Xto_wdoc();
		Keyval[] snak_props = Get_subs_by_path(Get_snaks(wdata_fxt, wdoc), 0);
		return Keyval_.Ary(Keyval_.int_(1, snak_props));
	}
	private static Keyval[] Get_snaks(Wdata_wiki_mgr_fxt wdata_fxt, Wdata_doc wdoc) {
		Keyval[] wdoc_root = Scrib_lib_wikibase_srl.Srl(wdata_fxt.Wdata_mgr().Prop_mgr(), wdoc, false, false, Bry_.new_u8("Test_page"));
		Keyval[] snaks = Get_subs_by_path(wdoc_root, 0, 0);
		int snaks_len = snaks.length;
		Keyval[] rv = new Keyval[snaks_len];
		for (int i = 0; i < snaks_len; ++i) {
			rv[i] = Keyval_.int_(i + List_adp_.Base1, Get_subs_by_path(snaks, i, 1));
		}
		return rv;
	}
	private static Keyval[] Get_subs_by_path(Keyval[] root, int... levels) {
		int len = levels.length;
		Keyval[] rv = root;
		for (int i = 0; i < len; ++i) {
			int idx = levels[i];
			rv = (Keyval[])rv[idx].Val();
		}
		return rv;
	}
}
