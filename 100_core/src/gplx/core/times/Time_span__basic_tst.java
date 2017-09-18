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
package gplx.core.times; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Time_span__basic_tst {
	@Test  public void seconds_() {
		Time_span expd = Time_span_.fracs_(123987);
		Time_span actl = Time_span_.seconds_(123.987);
		Tfds.Eq(expd, actl);
	}
	@Test  public void TotalSecs() {
		Time_span val = Time_span_.fracs_(1987);
		Tfds.Eq_decimal(Decimal_adp_.parts_(1, 987), val.Total_secs());
	}
	@Test  public void Units() {
		tst_Units("01:02:03.987", 1, 2, 3, 987);
		tst_Units("01:00:03", 1, 0, 3, 0);
		tst_Units("01:00:00.987", 1, 0, 0, 987);
		tst_Units("02:00.987", 0, 2, 0, 987);
	}
	@Test  public void Add() {
		Time_span val = Time_span_.fracs_(3);
		Time_span arg = Time_span_.fracs_(2);
		Time_span expd = Time_span_.fracs_(5);
		Time_span actl = val.Add(arg);
		Tfds.Eq(expd, actl);
	}
	@Test  public void Subtract() {
		Time_span val = Time_span_.fracs_(3);
		Time_span arg = Time_span_.fracs_(2);
		Time_span expd = Time_span_.fracs_(1);
		Time_span actl = val.Subtract(arg);
		Tfds.Eq(expd, actl);
	}
	@Test  public void Add_unit_identity() {
		tst_AddUnit("00:00:01.000", 0, 0, "00:00:01.000");
	}
	@Test  public void Add_unit_basic() {
		tst_AddUnit("01:59:58.987", 0, 1013, "02:00:00.000");
		tst_AddUnit("01:59:58.987", 1, 2, "02:00:00.987");
		tst_AddUnit("01:59:58.987", 2, 1, "02:00:58.987");
		tst_AddUnit("01:59:58.987", 3, 1, "02:59:58.987");
	}
	@Test  public void Add_unit_negative() {
		tst_AddUnit("01:00:00.00", 0, -1, "00:59:59.999");
		tst_AddUnit("01:00:00.00", 1, -1, "00:59:59.000");
		tst_AddUnit("01:00:00.00", 2, -1, "00:59:00.000");
		tst_AddUnit("01:00:00.00", 3, -1, "00:00:00.000");
	}
	@Test  public void XtoStrUiAbbrv() {
		tst_XtoStrUiAbbrv("01:02:03.004", "1h 2m 3s 4f");
		tst_XtoStrUiAbbrv("00:00:03.004", "3s 4f");
		tst_XtoStrUiAbbrv("00:00:03.000", "3s 0f");
		tst_XtoStrUiAbbrv("11:22:33.444", "11h 22m 33s 444f");
		tst_XtoStrUiAbbrv("00:00:00.000", "0f");
	}	void tst_XtoStrUiAbbrv(String raw, String expd) {Tfds.Eq(expd, Time_span_.parse(raw).XtoStrUiAbbrv());}
	void tst_AddUnit(String valRaw, int unitIdx, int delta, String expdRaw) {
		Time_span val = Time_span_.parse(valRaw);
		Time_span actl = val.Add_unit(unitIdx, delta);
		Tfds.Eq(Time_span_.parse(expdRaw), actl);
	}
	void tst_Units(String text, int... expd) {
		Time_span val = Time_span_.parse(text);
		int hour = 0, min = 0, sec = 0, frac = 0;
		int[] ary = val.Units();
		hour = ary[Time_span_.Idx_Hour]; min = ary[Time_span_.Idx_Min]; sec = ary[Time_span_.Idx_Sec]; frac = ary[Time_span_.Idx_Frac];
		Tfds.Eq(expd[0], hour);
		Tfds.Eq(expd[1], min);
		Tfds.Eq(expd[2], sec);
		Tfds.Eq(expd[3], frac);
	}
}
