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
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import org.junit.*; import gplx.core.encoders.*;
public class Xoh_hzip_int__tst {
	private final    Xoh_hzip_int__fxt fxt = new Xoh_hzip_int__fxt();
	@Test   public void Reqd__1() {
		fxt.Test__encode(1,          0,       "!");
		fxt.Test__encode(1,         84,       "u");
		fxt.Test__encode(1,         85,    "{\"!");
		fxt.Test__encode(1,       7225,   "|\"!!");
		fxt.Test__encode(1,     614125,  "}\"!!!");
		fxt.Test__encode(1,   52200625, "~\"!!!!");
	}
	@Test   public void Reqd__2() {
		fxt.Test__encode(2,          0,      "!!");
		fxt.Test__encode(2,         84,      "!u");
		fxt.Test__encode(2,         85,     "\"!");
		fxt.Test__encode(2,       7225,   "|\"!!");
		fxt.Test__encode(2,     614125,  "}\"!!!");
		fxt.Test__encode(2,   52200625, "~\"!!!!");
	}
}
class Xoh_hzip_int__fxt {
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private final    gplx.core.primitives.Int_obj_ref count_ref = gplx.core.primitives.Int_obj_ref.New_neg1();
	public void Test__encode(int reqd, int val, String expd) {
		Gfo_hzip_int_.Encode(reqd, bfr, val);
		byte[] actl = bfr.To_bry_and_clear();
            Tfds.Eq(expd, String_.new_u8(actl));
		Tfds.Eq(val, Gfo_hzip_int_.Decode(reqd, actl, actl.length, 0, count_ref));
	}
}
