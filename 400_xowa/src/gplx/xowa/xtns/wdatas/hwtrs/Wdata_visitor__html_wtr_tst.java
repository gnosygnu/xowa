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
package gplx.xowa.xtns.wdatas.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import org.junit.*;
import gplx.core.json.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.xtns.wdatas.parsers.*; import gplx.xowa.apis.xowa.html.*;
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
		( fxt.Wdata_fxt().Make_claim_time(1, "2001-02-03 04:05:06", Wdata_date.Fmt_ymdhns)
		, "4:05:06 3 Feb 2001"
		);
	}
	@Test   public void Quantity_ubound_lbound() {
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_quantity(1, "50", "units", "60", "30")
		, "50 +10 / -20 units"
		);
	}
	@Test   public void Quantity_same() {
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_quantity(1, "50", "units", "60", "40")
		, "50 ±10 units"
		);
	}
	@Test   public void Quantity_frac() {
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_quantity(1, "+0.1234", "units", "+0.1235", "+0.1233")
		, "+0.1234 ±0.0001 units"
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
		, "51° 30' 26&quot; N, 0° 7' 39&quot; W (<a href='/wiki/Q2'>Earth</a>)"
		);
	}
	@Test   public void Globecoordinate_null() {
		fxt
		.Test_claim_val
		( fxt.Wdata_fxt().Make_claim_geo(1, "51.5072222", "-0.1275", ".000027777", "null", "")
		, "51° 30' 26&quot; N, 0° 7' 39&quot; W"
		);
	}
}
