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
package gplx.texts; import gplx.*;
import org.junit.*;
public class HexDecUtl_tst {
	@Test  public void XtoInt() {
		tst_XtoInt("0", 0);
		tst_XtoInt("F", 15);
		tst_XtoInt("0F", 15);
		tst_XtoInt("10", 16);
		tst_XtoInt("20", 32);
		tst_XtoInt("FF", 255);
		tst_XtoInt("100", 256);
		tst_XtoInt("0a", 10);
		tst_XtoInt("7FFFFFFF", Int_.Max_value);
		tst_XtoInt_bry("100", 256);
	}
	@Test  public void To_str() {
		tst_XtoStr(0, "0");
		tst_XtoStr(15, "F");
		tst_XtoStr(16, "10");
		tst_XtoStr(32, "20");
		tst_XtoStr(255, "FF");
		tst_XtoStr(Int_.Max_value, "7FFFFFFF");

		tst_XtoStr(15, 2, "0F");
		tst_XtoStr(15, 3, "00F");
	}
	@Test   public void Write() {
		tst_Write("[00000000]", 1, 9,  15, "[0000000F]");
		tst_Write("[00000000]", 1, 9, 255, "[000000FF]");
	}
	private void tst_Write(String s, int bgn, int end, int val, String expd) {
		byte[] bry = Bry_.new_a7(s);
		HexDecUtl.Write(bry, bgn, end, val);
		Tfds.Eq(expd, String_.new_a7(bry));
	}
	private void tst_XtoInt(String raw, int expd) {
		int actl = HexDecUtl.parse(raw);
		Tfds.Eq(expd, actl);
	}
	private void tst_XtoInt_bry(String raw, int expd) {Tfds.Eq(expd, HexDecUtl.parse_or(Bry_.new_a7(raw), -1));}
	private void tst_XtoStr(int val, String expd) {tst_XtoStr(val, 0, expd);}
	private void tst_XtoStr(int val, int pad, String expd) {
		String actl = HexDecUtl.To_str(val, pad);
		Tfds.Eq(expd, actl);
	}
}
