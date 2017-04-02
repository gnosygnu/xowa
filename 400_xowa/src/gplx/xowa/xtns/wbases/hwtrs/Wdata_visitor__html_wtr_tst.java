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
package gplx.xowa.xtns.wbases.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import org.junit.*;
import gplx.langs.jsons.*; import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.itms.times.*; import gplx.xowa.xtns.wbases.parsers.*; import gplx.xowa.apps.apis.xowa.html.*;
public class Wdata_visitor__html_wtr_tst {
	@Before public void init() {fxt.init();} private Wdata_hwtr_mgr_fxt fxt = new Wdata_hwtr_mgr_fxt();
	@Test   public void Monolingualtext() {
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_monolingual(1, "en", "Motto")
		, "Motto [en]"
		);
	}
	@Test   public void Time() {
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_time(1, "2001-02-03 04:05:06", Wbase_date.Fmt_ymdhns)
		, "4:05:06 3 Feb 2001"
		);
	}
	@Test   public void Time__julian() {
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_time(1, "2001-02-03 04:05:06", Bry_.Empty, Bry_.new_a7("http://www.wikidata.org/entity/Q1985786"))
		, "4:05:06 3 Feb 2001"
		);
	}
	@Test   public void Quantity_ubound_lbound() {
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_quantity(1, "50", "", "60", "30")
		, "30-60"
		);
	}
	@Test   public void Quantity_same() {
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_quantity(1, "50", "1", "60", "40")
		, "50±10"
		);
	}
	@Test   public void Quantity_frac() {
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_quantity(1, "+0.1234", "1", "+0.1235", "+0.1233")
		, "0.1234±0.0001"
		);
	}
	@Test   public void Entity_qid() {
		fxt
		.Init_resolved_qid(1, "item_1")
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_entity_qid(1, 1)
		, "<a href='/wiki/Q1'>item_1</a>"
		);
	}
	@Test   public void Entity_pid() {
		fxt
		.Init_resolved_pid(1, "item_1")
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_entity_pid(1, 1)
		, "<a href='/wiki/Property:P1'>item_1</a>"
		);
	}
	@Test   public void Globecoordinate() {
		fxt
		.Init_resolved_qid(2, "Earth")
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_geo(1, "51.5072222", "-0.1275", ".000027777", "123", "http://www.wikidata.org/entity/Q2")
		, "0°7&#39;39&#34;S, 51°30&#39;26&#34;E (<a href='/wiki/Q2'>Earth</a>)"
		);
	}
	@Test   public void Globecoordinate__globe__null() {
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_geo(1, "51.5072222", "-0.1275", ".000027777", "null", "")
		, "0°7&#39;39&#34;S, 51°30&#39;26&#34;E"
		);
	}
	@Test   public void Globecoordinate__precision__0() {	// PURPOSE: 0 precision was causing divide by 0 error; PAGE:ru.w:Лысково_(Калужская_область) DATE:2016-11-24
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_geo(1, "51.5072222", "-0.1275", "0", "null", "")
		, "0°6&#39;S, 51°30&#39;E"
		);
	}
}
