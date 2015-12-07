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
import org.junit.*;
public class Xoh_hzip_int_tst {
	private final Xoh_hzip_int_fxt fxt = new Xoh_hzip_int_fxt();
	@Test   public void B256__reqd__1() {
		fxt.Init__b256();
		fxt.Test__b256(1,          0		,   0);
		fxt.Test__b256(1,        252		, 252, 252);
		fxt.Test__b256(1,        253		, 252, 253);
		fxt.Test__b256(1,        254		, 252, 254);
		fxt.Test__b256(1,        255		, 252, 255);
		fxt.Test__b256(1,        256		, 253,   1,   0);
		fxt.Test__b256(1,      65535		, 253, 255, 255);
		fxt.Test__b256(1,      65536		, 254,   1,   0,   0);
		fxt.Test__b256(1,   16777216		, 255,   1,   0,   0,   0);
	}
	@Test   public void B256__reqd__2() {
		fxt.Init__b256();
		fxt.Test__b256(2,          0		,   0,   0);
		fxt.Test__b256(2,        252		,   0, 252);
		fxt.Test__b256(2,        253		,   0, 253);
		fxt.Test__b256(2,        254		,   0, 254);
		fxt.Test__b256(2,        255		,   0, 255);
		fxt.Test__b256(2,        256		,   1,   0);
		fxt.Test__b256(2,      64511		, 251, 255);
		fxt.Test__b256(2,      64512		, 253, 252,   0);
		fxt.Test__b256(2,      65535		, 253, 255, 255);
		fxt.Test__b256(2,      65536		, 254,   1,   0,   0);
		fxt.Test__b256(2,   16777216		, 255,   1,   0,   0,   0);
	}
	@Test   public void B085__reqd__1() {
		fxt.Init__b085();
		fxt.Test__b085(1,          0,       "!");
		fxt.Test__b085(1,         84,       "u");
		fxt.Test__b085(1,         85,    "{\"!");
		fxt.Test__b085(1,       7225,   "|\"!!");
		fxt.Test__b085(1,     614125,  "}\"!!!");
		fxt.Test__b085(1,   52200625, "~\"!!!!");
	}
	@Test   public void B085__reqd__2() {
		fxt.Init__b085();
		fxt.Test__b085(2,          0,      "!!");
		fxt.Test__b085(2,         84,      "!u");
		fxt.Test__b085(2,         85,     "\"!");
		fxt.Test__b085(2,       7225,   "|\"!!");
		fxt.Test__b085(2,     614125,  "}\"!!!");
		fxt.Test__b085(2,   52200625, "~\"!!!!");
	}
}
class Xoh_hzip_int_fxt {
	private final Bry_bfr bfr = Bry_bfr.new_();
	private final gplx.core.primitives.Int_obj_ref count_ref = gplx.core.primitives.Int_obj_ref.neg1_();
	private final Xoh_hzip_int hzint = new Xoh_hzip_int();
	public void Init__b256() {hzint.Mode_is_b256_(Bool_.Y);}
	public void Init__b085() {hzint.Mode_is_b256_(Bool_.N);}
	public void Test__b256(int reqd, int val, int... expd_ints) {
		hzint.Encode(reqd, bfr, val);
		byte[] actl = bfr.To_bry_and_clear();
		byte[] expd = Byte_.Ary_by_ints(expd_ints);
		Tfds.Eq_ary(expd, actl, Int_.To_str(val));
		Tfds.Eq(val, hzint.Decode(reqd, actl, actl.length, 0, count_ref));
	}
	public void Test__b085(int reqd, int val, String expd) {
		hzint.Encode(reqd, bfr, val);
		byte[] actl = bfr.To_bry_and_clear();
            Tfds.Eq(expd, String_.new_u8(actl));
		Tfds.Eq(val, hzint.Decode(reqd, actl, actl.length, 0, count_ref));
	}
}
