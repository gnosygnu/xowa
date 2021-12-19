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
package gplx.xowa.xtns.scribunto.libs;

import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.commons.GfoDecimal;
import gplx.types.basics.utls.DoubleUtl;
import gplx.types.commons.KeyVal;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_value_type_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_globecoordinate;
import org.junit.Test;

public class Scrib_lib_wikibase_srl_visitor_tst {
	private final Scrib_lib_wikibase_srl_visitor_fxt fxt = new Scrib_lib_wikibase_srl_visitor_fxt();
	@Test public void Geo_null_precision() {
		// 2020-09-03|ISSUE#:792|null precision should default to 0 not 1;PAGE:wd:Q168751
		// 2020-12-14|ISSUE#:792|NOTE: current test is contrary to commit?
		fxt.TestGeoPrecision(1, "null");
	}
	@Test public void CalcPrecision() {
		// 2020-09-25|ISSUE#:792|use longitude to determine precision (contributed by desb42@)
		// precision is non-null -> use it
		fxt.TestCalcPrecision("2.8E-4", "0.0002777777777777778", "-76.62027777777777");

		// precision is null -> precision is number of decimal points
		fxt.TestCalcPrecision("1.0E-4", "null", "-76.1234");

		// precision is null -> precision is number of decimal points but max is 8
		fxt.TestCalcPrecision("1.0E-8", "null", "-76.62027777777777");

		// precision is null -> precision is 1
		// 2020-12-14|ISSUE#:792|NOTE: current test is contrary to commit?
		fxt.TestCalcPrecision("1.0E0", "null", "12");

		// TODO: {{wikidata|property|raw|page=Duke University|coord}} still fails as `36/0/4.00000/N/78/56/20.00000/W`
	}
}
class Scrib_lib_wikibase_srl_visitor_fxt {
	private final Scrib_lib_wikibase_srl_visitor visitor = new Scrib_lib_wikibase_srl_visitor();
	public void TestGeoPrecision(double expd, String prc) {
		String lat = "12";
		String lng = "34";
		visitor.Visit_globecoordinate(new Wbase_claim_globecoordinate(123, Wbase_claim_value_type_.Tid__value, BryUtl.NewA7(lat), BryUtl.NewA7(lng), null, BryUtl.NewU8Safe(prc), null));
		KeyVal[] actl = visitor.Rv();
		KeyVal[] actlGeo = (KeyVal[])actl[1].Val();
		GfoTstr.EqDouble(expd, DoubleUtl.Cast(actlGeo[4].Val()));
	}
	public void TestCalcPrecision(String expd, String prc, String lng) {
		GfoDecimal actl = Scrib_lib_wikibase_srl_visitor.CalcPrecision(BryUtl.NewU8(prc), BryUtl.NewU8(lng));
		GfoTstr.Eq(expd, actl.ToStr("0.0E0"));
	}
}