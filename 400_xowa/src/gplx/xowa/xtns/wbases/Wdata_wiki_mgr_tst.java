/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.wbases;

import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.xtns.wbases.hwtrs.Wdata_hwtr_msgs;
import gplx.xowa.xtns.wbases.hwtrs.Wdata_lbl_mgr;
import org.junit.Test;

public class Wdata_wiki_mgr_tst {
	private final Wdata_prop_val_visitor__fxt fxt = new Wdata_prop_val_visitor__fxt();
	@Test public void Basic() {
		Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt().Init();
		fxt.Init__docs__add(fxt.Wdoc("Q1")
			.Add_sitelink("enwiki", "Q1_en")
			);

		fxt.Test_link("Q1_en" , "Q1");
		fxt.Test_link("Q1_nil", null);
	}
	@Test public void Case_sensitive() {	// PURPOSE: wikidata lkp should be case_sensitive; a vs A DATE:2013-09-03
		Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt().Init();
		fxt.Init__docs__add(fxt.Wdoc("Q1")
			.Add_sitelink("enwiki", "Q1_EN")
			);

		fxt.Test_link("Q1_EN", "Q1");
		fxt.Test_link("q1_en", null);
	}
	@Test public void Non_canonical_ns() {	// PURPOSE: handle wikidata entries in non-canonical ns; EX:ukwikisource and Author; PAGE:uk.s:Автор:Богдан_Гаврилишин DATE:2014-07-23
		Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt().Init();
		Xowe_wiki wiki = fxt.Wiki();
		wiki.Ns_mgr().Add_new(124, "Test_ns");
		fxt.Init__docs__add(fxt.Wdoc("Q1")
			.Add_sitelink("enwiki", "Test_ns:Test_page")
			);

		fxt.Test_link(Xoa_ttl.Parse(fxt.Wiki(), 124, BryUtl.NewA7("Test_page")), "Q1"); // NOTE: wdata will save to "000" ns, b/c "124" ns is not canonical
	}
	@Test public void Write_json_as_html() {
		Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt().Init();
		fxt.Test_write_json_as_html("{'a':'b','c':['d','e'],'f':{'g':'<h>'}}", StringUtl.ConcatLinesNlSkipLast
		(	"<span id=\"xowa-wikidata-json\">"
		,	"{ \"a\":\"b\""
		,	", \"c\":"
		,	"  [ \"d\""
		,	"  , \"e\""
		,	"  ]"
		,	", \"f\":"
		,	"  { \"g\":\"&lt;h&gt;\""
		,	"  }"
		,	"}"
		,	"</span>"
		));
	}
	@Test public void Normalize_for_decimal() {
		fxt.Test__normalize_for_decimal("1234"   , "1234");	// basic
		fxt.Test__normalize_for_decimal("+1234"  , "1234");	// plus
		fxt.Test__normalize_for_decimal("1,234"  , "1234");	// comma
		fxt.Test__normalize_for_decimal("+1,234" , "1234");	// both
	}
	@Test public void Write_quantity_null() {	// handle missing lbound / ubound; DATE:2016-12-03
		Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt().Init();

		BryWtr bfr = BryWtr.New();
		Wdata_prop_val_visitor.Write_quantity(bfr, fxt.Wdata_mgr(), fxt.Wiki().Lang(), BryUtl.NewA7("123"), null, null, null);
		GfoTstr.Eq("123", bfr.ToBryAndClear());
	}
	@Test public void Geo() {
		// null precision
		fxt.TestGeo("39°57&#39;42&#34;N, 83°0&#39;7&#34;W", "39.96177", "-83.00196", "null");

		// 1/60 precision
		fxt.TestGeo("39°58&#39;0&#34;N, 83°0&#39;0&#34;W", "39.96177", "-83.00196", "0.0166666667");

		// 1/3600 precision
		fxt.TestGeo("39°57&#39;42&#34;N, 83°0&#39;7&#34;W", "39.96177", "-83.00196", "0.0002777778");

		// 2020-09-06|ISSUE#:792|fails if 1 digit precision; EX: 42.4 instead of 42.37
		fxt.TestGeo("39°57&#39;42.37&#34;N, 83°0&#39;7.06&#34;W", "39.96177", "-83.00196", "1.0e-5");
	}
}
class Wdata_prop_val_visitor__fxt {
	public void Test__normalize_for_decimal(String raw, String expd) {
		GfoTstr.Eq(expd, Wdata_prop_val_visitor.Normalize_for_decimal(BryUtl.NewU8(raw)), raw);
	}
	public void TestGeo(String expd, String lat, String lng, String prc) {TestGeo(expd, lat, lng, prc, null);}
	public void TestGeo(String expd, String lat, String lng, String prc, String glb) {
		boolean wikidata_page = glb != null;
		Wdata_lbl_mgr lbl_mgr = new Wdata_lbl_mgr();
		Wdata_hwtr_msgs msgs = Wdata_hwtr_msgs.new_en_();
		BryWtr bfr = BryWtr.New();

		Wdata_prop_val_visitor.Write_geo(wikidata_page, bfr, lbl_mgr, msgs, BryUtl.NewU8(lat), BryUtl.NewU8(lng), null, BryUtl.NewU8(prc), BryUtl.NewU8Safe(glb));
		GfoTstr.Eq(expd, bfr.ToStrAndClear());
	}
}
