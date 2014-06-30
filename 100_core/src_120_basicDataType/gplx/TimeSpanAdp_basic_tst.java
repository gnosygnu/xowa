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
package gplx;
import org.junit.*;
public class TimeSpanAdp_basic_tst {
	@Test public void seconds_() {
		TimeSpanAdp expd = TimeSpanAdp_.fracs_(123987);
		TimeSpanAdp actl = TimeSpanAdp_.seconds_(123.987);
		Tfds.Eq(expd, actl);
	}
	@Test public void TotalSecs() {
		TimeSpanAdp val = TimeSpanAdp_.fracs_(1987);
		Tfds.Eq_decimal(DecimalAdp_.parts_(1, 987), val.TotalSecs());
	}
	@Test public void Units() {
		tst_Units("01:02:03.987", 1, 2, 3, 987);
		tst_Units("01:00:03", 1, 0, 3, 0);
		tst_Units("01:00:00.987", 1, 0, 0, 987);
		tst_Units("02:00.987", 0, 2, 0, 987);
	}
	@Test public void Add() {
		TimeSpanAdp val = TimeSpanAdp_.fracs_(3);
		TimeSpanAdp arg = TimeSpanAdp_.fracs_(2);
		TimeSpanAdp expd = TimeSpanAdp_.fracs_(5);
		TimeSpanAdp actl = val.Add(arg);
		Tfds.Eq(expd, actl);
	}
	@Test public void Subtract() {
		TimeSpanAdp val = TimeSpanAdp_.fracs_(3);
		TimeSpanAdp arg = TimeSpanAdp_.fracs_(2);
		TimeSpanAdp expd = TimeSpanAdp_.fracs_(1);
		TimeSpanAdp actl = val.Subtract(arg);
		Tfds.Eq(expd, actl);
	}
	@Test public void Add_unit_identity() {
		tst_AddUnit("00:00:01.000", 0, 0, "00:00:01.000");
	}
	@Test public void Add_unit_basic() {
		tst_AddUnit("01:59:58.987", 0, 1013, "02:00:00.000");
		tst_AddUnit("01:59:58.987", 1, 2, "02:00:00.987");
		tst_AddUnit("01:59:58.987", 2, 1, "02:00:58.987");
		tst_AddUnit("01:59:58.987", 3, 1, "02:59:58.987");
	}
	@Test public void Add_unit_negative() {
		tst_AddUnit("01:00:00.00", 0, -1, "00:59:59.999");
		tst_AddUnit("01:00:00.00", 1, -1, "00:59:59.000");
		tst_AddUnit("01:00:00.00", 2, -1, "00:59:00.000");
		tst_AddUnit("01:00:00.00", 3, -1, "00:00:00.000");
	}
	@Test public void XtoStrUiAbbrv() {
		tst_XtoStrUiAbbrv("01:02:03.004", "1h 2m 3s 4f");
		tst_XtoStrUiAbbrv("00:00:03.004", "3s 4f");
		tst_XtoStrUiAbbrv("00:00:03.000", "3s 0f");
		tst_XtoStrUiAbbrv("11:22:33.444", "11h 22m 33s 444f");
		tst_XtoStrUiAbbrv("00:00:00.000", "0f");
	}	void tst_XtoStrUiAbbrv(String raw, String expd) {Tfds.Eq(expd, TimeSpanAdp_.parse_(raw).XtoStrUiAbbrv());}
	void tst_AddUnit(String valRaw, int unitIdx, int delta, String expdRaw) {
		TimeSpanAdp val = TimeSpanAdp_.parse_(valRaw);
		TimeSpanAdp actl = val.Add_unit(unitIdx, delta);
		Tfds.Eq(TimeSpanAdp_.parse_(expdRaw), actl);
	}
	void tst_Units(String text, int... expd) {
		TimeSpanAdp val = TimeSpanAdp_.parse_(text);
		int hour = 0, min = 0, sec = 0, frac = 0;
		int[] ary = val.Units();
		hour = ary[TimeSpanAdp_.Idx_Hour]; min = ary[TimeSpanAdp_.Idx_Min]; sec = ary[TimeSpanAdp_.Idx_Sec]; frac = ary[TimeSpanAdp_.Idx_Frac];
		Tfds.Eq(expd[0], hour);
		Tfds.Eq(expd[1], min);
		Tfds.Eq(expd[2], sec);
		Tfds.Eq(expd[3], frac);
	}
}
