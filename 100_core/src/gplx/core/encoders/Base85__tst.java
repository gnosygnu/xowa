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
public class Base85__tst {
	private final Base85__fxt fxt = new Base85__fxt();
	@Test  public void Log() {
		fxt.Test_log(            0, 1);
		fxt.Test_log(           84, 1);
		fxt.Test_log(           85, 2);
		fxt.Test_log(         7224, 2);
		fxt.Test_log(         7225, 3);
		fxt.Test_log(       614124, 3);
		fxt.Test_log(       614125, 4);
		fxt.Test_log(     52200624, 4);
		fxt.Test_log(     52200625, 5);
		fxt.Test_log(Int_.Max_value, 5);
	}
	@Test  public void To_str() {
		fxt.Test_to_str(           0, "!");
		fxt.Test_to_str(          84, "u");
		fxt.Test_to_str(          85, "\"!");
		fxt.Test_to_str(        7224, "uu");
		fxt.Test_to_str(        7225, "\"!!");
		fxt.Test_to_str(      614124, "uuu");
		fxt.Test_to_str(      614125, "\"!!!");
		fxt.Test_to_str(    52200624, "uuuu");
		fxt.Test_to_str(    52200625, "\"!!!!");
	}
	@Test  public void XtoStrAry() {
		byte[] ary = new byte[9];
		fxt.Exec_to_str(ary,      0,     2); // !!#
		fxt.Exec_to_str(ary,      3,   173); // !#$
		fxt.Exec_to_str(ary,      6, 14709); // #$%
            Tfds.Eq("!!#!#$#$%", String_.new_u8(ary));
	}
}
class Base85__fxt {
	public void Test_log(int val, int expd) {Tfds.Eq(expd, Base85_.Bry_len(val));}
	public void Test_to_str(int val, String expd) {
		String actl = Base85_.To_str(val, 0);
		Tfds.Eq(expd, actl);
		Tfds.Eq(val, Base85_.To_int_by_str(expd));
	}
	public void Exec_to_str(byte[] ary, int aryPos, int val) {Base85_.Set_bry(val, ary, aryPos, 3);}
}
