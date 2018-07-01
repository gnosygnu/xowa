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
package gplx.xowa.xtns.wbases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.core.tests.*; import gplx.xowa.xtns.wbases.imports.*;
public class Wdata_wiki_mgr_tst {
	private final    Wdata_prop_val_visitor__fxt fxt = new Wdata_prop_val_visitor__fxt();
	@Test  public void Basic() {
		Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt().Init();
		fxt.Init_links_add("enwiki", "Q1", "Q1_en");
		fxt.Test_link("Q1", "Q1_en");
		fxt.Test_link("Q2", null);
	}
	@Test  public void Case_sensitive() {	// PURPOSE: wikidata lkp should be case_sensitive; a vs A DATE:2013-09-03
		Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt().Init();
		fxt.Init_links_add("enwiki", "Page", "Page_data");
		fxt.Test_link("Page", "Page_data");
		fxt.Test_link("PAGE", null);
	}
	@Test  public void Non_canonical_ns() {	// PURPOSE: handle wikidata entries in non-canonical ns; EX:ukwikisource and Author; PAGE:uk.s:Автор:Богдан_Гаврилишин DATE:2014-07-23
		Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt().Init();
		Xowe_wiki wiki = fxt.Wiki();
		wiki.Ns_mgr().Add_new(124, "Test_ns");
		fxt.Init_links_add("enwiki", "000", "Test_ns:Test_page", "pass");	// NOTE: wdata will save to "000" ns, b/c "124" ns is not canonical
		Xoa_ttl ttl = Xoa_ttl.Parse(fxt.Wiki(), 124, Bry_.new_a7("Test_page"));
		fxt.Test_link(ttl, "pass");
	}
	@Test   public void Write_json_as_html() {
		Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt().Init();
		fxt.Test_write_json_as_html("{'a':'b','c':['d','e'],'f':{'g':'<h>'}}", String_.Concat_lines_nl_skip_last
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
	@Test  public void Normalize_for_decimal() {
		fxt.Test__normalize_for_decimal("1234"		, "1234");	// basic
		fxt.Test__normalize_for_decimal("+1234"		, "1234");	// plus
		fxt.Test__normalize_for_decimal("1,234"		, "1234");	// comma
		fxt.Test__normalize_for_decimal("+1,234"	, "1234");	// both
	}
	@Test  public void Write_quantity_null() {	// handle missing lbound / ubound; DATE:2016-12-03
		Wdata_wiki_mgr_fxt fxt = new Wdata_wiki_mgr_fxt().Init();

		Bry_bfr bfr = Bry_bfr_.New();
		Wdata_prop_val_visitor.Write_quantity(bfr, fxt.Wdata_mgr(), fxt.Wiki().Lang(), Bry_.new_a7("123"), null, null, null);
		Gftest.Eq__str("123", bfr.To_bry_and_clear());
	}
}
class Wdata_prop_val_visitor__fxt {
	public void Test__normalize_for_decimal(String raw, String expd) {
		Gftest.Eq__str(expd, Wdata_prop_val_visitor.Normalize_for_decimal(Bry_.new_u8(raw)), raw);
	}
}
