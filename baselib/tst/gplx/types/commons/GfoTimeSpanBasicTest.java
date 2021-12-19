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
package gplx.types.commons;
import gplx.frameworks.tests.GfoTstr;
import org.junit.Test;
public class GfoTimeSpanBasicTest {
	@Test public void Seconds() {
		GfoTimeSpan expd = GfoTimeSpanUtl.NewFracs(123987);
		GfoTimeSpan actl = GfoTimeSpanUtl.NewSeconds(123.987);
		GfoTstr.EqObj(expd, actl);
	}
	@Test public void TotalSecs() {
		GfoTimeSpan val = GfoTimeSpanUtl.NewFracs(1987);
		GfoTstr.EqObj(GfoDecimalUtl.NewByParts(1, 987), val.TotalSecs());
	}
	@Test public void Units() {
		TestUnits("01:02:03.987", 1, 2, 3, 987);
		TestUnits("01:00:03", 1, 0, 3, 0);
		TestUnits("01:00:00.987", 1, 0, 0, 987);
		TestUnits("02:00.987", 0, 2, 0, 987);
	}
	@Test public void Add() {
		GfoTimeSpan val = GfoTimeSpanUtl.NewFracs(3);
		GfoTimeSpan arg = GfoTimeSpanUtl.NewFracs(2);
		GfoTimeSpan expd = GfoTimeSpanUtl.NewFracs(5);
		GfoTimeSpan actl = val.Add(arg);
		GfoTstr.EqObj(expd, actl);
	}
	@Test public void Subtract() {
		GfoTimeSpan val = GfoTimeSpanUtl.NewFracs(3);
		GfoTimeSpan arg = GfoTimeSpanUtl.NewFracs(2);
		GfoTimeSpan expd = GfoTimeSpanUtl.NewFracs(1);
		GfoTimeSpan actl = val.Subtract(arg);
		GfoTstr.EqObj(expd, actl);
	}
	@Test public void Add_unit_identity() {
		TestAddUnit("00:00:01.000", 0, 0, "00:00:01.000");
	}
	@Test public void Add_unit_basic() {
		TestAddUnit("01:59:58.987", 0, 1013, "02:00:00.000");
		TestAddUnit("01:59:58.987", 1, 2, "02:00:00.987");
		TestAddUnit("01:59:58.987", 2, 1, "02:00:58.987");
		TestAddUnit("01:59:58.987", 3, 1, "02:59:58.987");
	}
	@Test public void Add_unit_negative() {
		TestAddUnit("01:00:00.00", 0, -1, "00:59:59.999");
		TestAddUnit("01:00:00.00", 1, -1, "00:59:59.000");
		TestAddUnit("01:00:00.00", 2, -1, "00:59:00.000");
		TestAddUnit("01:00:00.00", 3, -1, "00:00:00.000");
	}
	@Test public void XtoStrUiAbbrv() {
		TestToStrUiAbbrv("01:02:03.004", "1h 2m 3s 4f");
		TestToStrUiAbbrv("00:00:03.004", "3s 4f");
		TestToStrUiAbbrv("00:00:03.000", "3s 0f");
		TestToStrUiAbbrv("11:22:33.444", "11h 22m 33s 444f");
		TestToStrUiAbbrv("00:00:00.000", "0f");
	}
	private void TestToStrUiAbbrv(String raw, String expd) {GfoTstr.EqObj(expd, GfoTimeSpanUtl.Parse(raw).ToStrUiAbrv());}
	private void TestAddUnit(String valRaw, int unitIdx, int delta, String expdRaw) {
		GfoTimeSpan val = GfoTimeSpanUtl.Parse(valRaw);
		GfoTimeSpan actl = val.AddUnit(unitIdx, delta);
		GfoTstr.EqObj(GfoTimeSpanUtl.Parse(expdRaw), actl);
	}
	private void TestUnits(String text, int... expd) {
		GfoTimeSpan val = GfoTimeSpanUtl.Parse(text);
		int hour = 0, min = 0, sec = 0, frac = 0;
		int[] ary = val.Units();
		hour = ary[GfoTimeSpanUtl.Idx_Hour]; min = ary[GfoTimeSpanUtl.Idx_Min]; sec = ary[GfoTimeSpanUtl.Idx_Sec]; frac = ary[GfoTimeSpanUtl.Idx_Frac];
		GfoTstr.EqObj(expd[0], hour);
		GfoTstr.EqObj(expd[1], min);
		GfoTstr.EqObj(expd[2], sec);
		GfoTstr.EqObj(expd[3], frac);
	}
}
