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
package gplx.core.encoders; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Hex_utl__tst {
	private final    Hex_utl__fxt fxt = new Hex_utl__fxt();
	@Test  public void To_int() {
		fxt.Test__to_int("0"		, 0);
		fxt.Test__to_int("F"		, 15);
		fxt.Test__to_int("0F"		, 15);
		fxt.Test__to_int("10"		, 16);
		fxt.Test__to_int("20"		, 32);
		fxt.Test__to_int("FF"		, 255);
		fxt.Test__to_int("100"		, 256);
		fxt.Test__to_int("0a"		, 10);
		fxt.Test__to_int("7FFFFFFF"	, Int_.Max_value);
		fxt.Test__to_int_bry("100"	, 256);
	}
	@Test  public void To_str() {
		fxt.Test__to_str(0			, "0");
		fxt.Test__to_str(15			, "F");
		fxt.Test__to_str(16			, "10");
		fxt.Test__to_str(32			, "20");
		fxt.Test__to_str(255		, "FF");
		fxt.Test__to_str(Int_.Max_value, "7FFFFFFF");

		fxt.Test__to_str(15, 2		, "0F");
		fxt.Test__to_str(15, 3		, "00F");
	}
	@Test   public void Write() {
		fxt.Test__write("[00000000]", 1, 9,  15, "[0000000F]");
		fxt.Test__write("[00000000]", 1, 9, 255, "[000000FF]");
	}
}
class Hex_utl__fxt {
	public void Test__write(String s, int bgn, int end, int val, String expd) {
		byte[] bry = Bry_.new_a7(s);
		Hex_utl_.Write(bry, bgn, end, val);
		Tfds.Eq(expd, String_.new_a7(bry));
	}
	public void Test__to_int(String raw, int expd) {
		int actl = Hex_utl_.Parse(raw);
		Tfds.Eq(expd, actl);
	}
	public void Test__to_int_bry(String raw, int expd) {Tfds.Eq(expd, Hex_utl_.Parse_or(Bry_.new_a7(raw), -1));}
	public void Test__to_str(int val, String expd) {Test__to_str(val, 0, expd);}
	public void Test__to_str(int val, int pad, String expd) {
		String actl = Hex_utl_.To_str(val, pad);
		Tfds.Eq(expd, actl);
	}
//		public void Test__encode_bry(int val, int pad, String expd) {
//			String actl = Hex_utl_.To_str(val, pad);
//			Tfds.Eq(expd, actl);
//		}
}
