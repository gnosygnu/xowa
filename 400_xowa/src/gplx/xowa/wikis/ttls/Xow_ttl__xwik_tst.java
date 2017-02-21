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
package gplx.xowa.wikis.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*; import gplx.xowa.wikis.nss.*;
public class Xow_ttl__xwik_tst {
	@Before public void init() {fxt.Reset();} private Xow_ttl_fxt fxt = new Xow_ttl_fxt();
	@Test   public void Known()						{fxt.Init_ttl("fr:a")			.Expd_xwik_txt("fr").Expd_ns_id(Xow_ns_.Tid__main).Expd_page_txt("a").Test();}
	@Test   public void Known_ns()					{fxt.Init_ttl("fr:Help:a")		.Expd_xwik_txt("fr").Expd_ns_id(Xow_ns_.Tid__main).Expd_full_txt("Help:a").Expd_page_txt("Help:a").Test();} // NOTE: Page is "Help:a" b/c ns are unknowable in foreign wiki
	@Test   public void Unknown()					{fxt.Init_ttl("frx:a")			.Expd_xwik_txt("").Expd_ns_id(Xow_ns_.Tid__main).Expd_page_txt("Frx:a").Test();}
	@Test   public void Unknown_ns()				{fxt.Init_ttl("frx:Help:a")		.Expd_xwik_txt("").Expd_ns_id(Xow_ns_.Tid__main).Expd_page_txt("Frx:Help:a").Test();}
	@Test   public void Known_ns_leaf_anch()		{fxt.Init_ttl("fr:Help:a/b/c#d").Expd_xwik_txt("fr").Expd_ns_id(Xow_ns_.Tid__main).Expd_page_txt("Help:a/b/c").Expd_leaf_txt("c").Expd_anch_txt("d").Expd_full_txt("Help:a/b/c").Test();}
	@Test   public void Colon_is_last() {
		fxt.Init_ttl("fr:Help:").Expd_xwik_txt("fr").Expd_page_txt("Help:").Test();
		fxt.Init_ttl("fr:_ _").Expd_xwik_txt("fr").Expd_page_txt("").Test();	// NOTE: fr:_ _ is invalid (resolves to "fr:");
		fxt.Init_ttl("fr:Help:_ _").Expd_xwik_txt("fr").Expd_page_txt("Help:").Test();
	}
	@Test   public void Colon_multiple() {
		fxt.Init_ttl("fr::Help:Test").Expd_xwik_txt("fr").Expd_page_txt(":Help:Test").Test();
		fxt.Init_ttl("fr::Test").Expd_xwik_txt("fr").Expd_page_txt(":Test").Test();
		fxt.Init_ttl(":fr:Test").Expd_xwik_txt("fr").Expd_page_txt("Test").Expd_force_literal_link(1).Test();
		fxt.Init_ttl(":::fr:Test").Expd_xwik_txt("").Expd_page_txt(":fr:Test").Expd_force_literal_link(1).Test();
	}
	@Test   public void Ns_should_precede_xwiki() {// PURPOSE: the "Wikipedia" in "Wikipedia:Main page" should be interpreted as Srch_rslt_cbk, not an alias
		fxt.Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("Wikipedia"), Bry_.new_a7("en.wikipedia.org"));
		fxt.Init_ttl("Wikipedia:Test").Expd_xwik_txt("").Expd_full_txt("Wikipedia:Test").Test();
	}
}