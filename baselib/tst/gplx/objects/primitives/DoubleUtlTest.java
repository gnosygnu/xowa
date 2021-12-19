/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.objects.primitives;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.DoubleUtl;
import org.junit.Test;
public class DoubleUtlTest {
	private final DoubleUtlTstr tstr = new DoubleUtlTstr();

	@Test
	public void TrimZeroes() {
		tstr.TestTrimZeroes("12.100"       , "12.1");
		tstr.TestTrimZeroes("12.000"       , "12");
		tstr.TestTrimZeroes("12.001"       , "12.001");
		tstr.TestTrimZeroes("1020.00"      , "1020");
		tstr.TestTrimZeroes("1020.00"      , "1020");
		tstr.TestTrimZeroes("1.200e5"      , "1.2E5");
		tstr.TestTrimZeroes("1.200e-05"    , "1.2E-5");
	}

	@Test
	public void ToStrByPrintF() {
		tstr.TestToStrByPrintF(1d / 2d                 , "0.5");                 // fails with 0.50000000000000
		tstr.TestToStrByPrintF(5d / 100000000000000000d, "5E-17");               // fails with 5.0000000000000e-17
		tstr.TestToStrByPrintF(7538000d / 7773352000d  , "0.00096972322879499"); // fails with 0; ISSUE#:697; DATE:2020-08-11
		tstr.TestToStrByPrintF(56225d   / 7776747000d  , "7.2298867379895E-06"); // fails with 0; ISSUE#:697; DATE:2020-08-11
		tstr.TestToStrByPrintF(35746d   / 7805411000d  , "4.5796435319037E-06"); // fails with 0; ISSUE#:697; DATE:2020-08-11
	}
	@Test public void Xto_str_loose() {
		tstr.TestXtoStrLoose(2449.6000000d        , "2449.6");
		tstr.TestXtoStrLoose(623.700d                , "623.7");
	}
}
class DoubleUtlTstr {
	public void TestToStrByPrintF(double v, String expd) {GfoTstr.Eq(expd, DoubleUtl.ToStrByPrintF(v));}
	public void TestTrimZeroes(String val, String expd) {GfoTstr.Eq(expd, DoubleUtl.TrimZeroes(val));}
	public void TestXtoStrLoose(double v, String expd) {GfoTstr.Eq(expd, DoubleUtl.ToStrLoose(v));}
}
