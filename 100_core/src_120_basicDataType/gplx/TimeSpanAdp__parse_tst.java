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
public class TimeSpanAdp__parse_tst {
	@Test  public void Zero() {
		tst_Parse("0", 0);
	}
	@Test  public void Milliseconds() {
		tst_Parse("0.987", 987);
		tst_Parse("0.00199", 1);		// do not parse as 199
		tst_Parse("0.1", 100);			// do not parse as 1
	}
	@Test  public void Seconds() {
		tst_Parse("1.987", 1987);
	}
	@Test  public void Minutes() {
		tst_Parse("1:02.987", 62987);
	}
	@Test  public void MinuteSecondOnly() {
		tst_Parse("1:02", 62000);
	}
	@Test  public void Hour() {
		tst_Parse("1:02:03.987", 3723987);
	}
	@Test  public void Negative() {
		tst_Parse("-1:02:03.987", -3723987);
	}
	@Test  public void Loopholes() {
		tst_Parse("001:02", 62000);					// multiple leading zeroes
		tst_Parse("1.2.3.4", 1200);					// ignore all decimals except first
		tst_Parse("60:60.9999", 3660999);			// value does not need to be bounded to limits (except fracs, which is always < 1000)
		tst_Parse(" 01 : 02 : 03 . 987", 3723987);	// whitespace
	}
	void tst_Parse(String text, long expd) {
		TimeSpanAdp val = TimeSpanAdp_.parse_(text);
		Tfds.Eq(expd, val.Fracs());
	}
}
