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
public class Long__tst {
	@Test  public void DigitCount() {
		tst_DigitCount(0, 1);
		tst_DigitCount(1, 1);
		tst_DigitCount(9, 1);
		tst_DigitCount(10, 2);
		tst_DigitCount(100, 3);
		tst_DigitCount(10000, 5);
		tst_DigitCount(100000, 6);
		tst_DigitCount(1000000, 7);
		tst_DigitCount(1000000000, 10);
		tst_DigitCount(10000000000L, 11);
		tst_DigitCount(100000000000L, 12);
		tst_DigitCount(10000000000000000L, 17);
		tst_DigitCount(-1, 2);
	}	void tst_DigitCount(long val, int expd) {Tfds.Eq(expd, Long_.DigitCount(val));}
	@Test  public void Int_merge() {
		tst_Int_merge(123, 456, 528280977864L);
		tst_Int_merge(123, 457, 528280977865L);
	}
	void tst_Int_merge(int hi, int lo, long expd) {
		Tfds.Eq(expd, Long_.Int_merge(hi, lo));
		Tfds.Eq(hi, Long_.Int_split_hi(expd));
		Tfds.Eq(lo, Long_.Int_split_lo(expd));
	}
	@Test  public void parse_or_() {
		parse_or_tst("10000000000", 10000000000L);
	}
	void parse_or_tst(String raw, long expd) {Tfds.Eq(expd, Long_.parse_or_(raw, -1));}
}
