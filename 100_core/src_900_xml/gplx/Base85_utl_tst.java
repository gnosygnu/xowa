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
public class Base85_utl_tst {
	@Test  public void Log() {
		tst_Log(            0, 1);
		tst_Log(           84, 1);
		tst_Log(           85, 2);
		tst_Log(         7224, 2);
		tst_Log(         7225, 3);
		tst_Log(       614124, 3);
		tst_Log(       614125, 4);
		tst_Log(     52200624, 4);
		tst_Log(     52200625, 5);
		tst_Log(Int_.MaxValue, 5);
	}	void tst_Log(int val, int expd) {Tfds.Eq(expd, Base85_utl.DigitCount(val));}
	@Test  public void XtoStr() {
		tst_XtoStr(           0, "!");
		tst_XtoStr(          84, "u");
		tst_XtoStr(          85, "\"!");
		tst_XtoStr(        7224, "uu");
		tst_XtoStr(        7225, "\"!!");
		tst_XtoStr(      614124, "uuu");
		tst_XtoStr(      614125, "\"!!!");
		tst_XtoStr(    52200624, "uuuu");
		tst_XtoStr(    52200625, "\"!!!!");
	}
	void tst_XtoStr(int val, String expd) {
		String actl = Base85_utl.XtoStr(val, 0);
		Tfds.Eq(expd, actl);
		Tfds.Eq(val, Base85_utl.XtoIntByStr(expd));
	}
	@Test  public void XtoStrAry() {
		byte[] ary = new byte[9];
		run_XtoStr(ary,      0,     2); // !!#
		run_XtoStr(ary,      3,   173); // !#$
		run_XtoStr(ary,      6, 14709); // #$%
            Tfds.Eq("!!#!#$#$%", String_.new_u8(ary));
	}	void run_XtoStr(byte[] ary, int aryPos, int val) {Base85_utl.XtoStrByAry(val, ary, aryPos, 3);}
}
