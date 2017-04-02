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
import org.junit.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*;
public class Scrib_lib_wikibase_tst {
	private final    Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	private final    Wdata_wiki_mgr_fxt wdata_fxt = new Wdata_wiki_mgr_fxt();
	@Before public void init() {
		fxt.Clear_for_lib("en.wikipedia.org", "zh-hans");
		lib = fxt.Core().Lib_wikibase().Init();
		wdata_fxt.Init(fxt.Parser_fxt(), false);
		wdata_fxt.Init_lang_fallbacks("zh-hant", "zh-hk");
	}
	// @Test  public void GetGlobalSiteId() {
	//	fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getGlobalSiteId, Object_.Ary_empty, "enwiki");
	// }
	@Test  public void GetEntityId() {
		wdata_fxt.Init_links_add("enwiki", "Earth", "q2");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getEntityId, Object_.Ary("Earth"							), "q2");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getEntityId, Object_.Ary("missing_page"					), "");
	}
	@Test  public void GetLabel__cur() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2").Add_label("zh-hans", "s").Add_label("zh-hant", "t").Xto_wdoc());
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getLabel, Object_.Ary("q2"), "s");		// do not get fallback
	}
	@Test  public void GetLabel__fallback_1() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2").Add_label("zh-hant", "t").Add_label("zh-hk", "h").Xto_wdoc());
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getLabel, Object_.Ary("q2"), "t");		// get 1st fallback
	}
	@Test  public void GetLabel__fallback_2() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2").Add_label("zh-hk", "hk").Xto_wdoc());
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getLabel, Object_.Ary("q2"), "hk");	// get 2nd fallback
	}
	@Test  public void GetLabel__fallback_en() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2").Add_label("en", "en").Xto_wdoc());
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_getLabel, Object_.Ary("q2"), "en");	// get en
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
	@Test  public void ResolvePropertyId() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("Property:p2").Add_label("zh-hans", "prop_a").Xto_wdoc());
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_resolvePropertyId, Object_.Ary("p2"), "prop_a");
	}
	@Test  public void RenderSnaks() {
		Keyval[] args = Wbase_snak_utl_.Get_snaks_ary(wdata_fxt, wdata_fxt.Make_claim_monolingual(3, "en", "P3_en"), wdata_fxt.Make_claim_monolingual(3, "de", "P3_de"));
		fxt.Test__proc__kvps__flat(lib, Scrib_lib_wikibase.Invk_renderSnaks, args, "P3_en, P3_de");
	}
	@Test  public void RenderSnak__entity() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q3").Add_label("en", "test_label").Xto_wdoc());
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
		Keyval[] wdoc_root = Scrib_lib_wikibase_srl.Srl(wdata_fxt.Wdata_mgr().Prop_mgr(), wdoc, false, false);
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
