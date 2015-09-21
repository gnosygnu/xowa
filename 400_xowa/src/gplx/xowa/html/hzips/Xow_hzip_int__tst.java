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
package gplx.xowa.html.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import org.junit.*; import gplx.core.primitives.*;
public class Xow_hzip_int__tst {
	@Before public void init() {fxt.Clear();} private Xow_hzip_int__fxt fxt = new Xow_hzip_int__fxt();
	@Test  public void Srl() {
		fxt.Test_srl(            0,   0);
		fxt.Test_srl(            1,   1,   0);
		fxt.Test_srl(          255, 255,   0);
		fxt.Test_srl(          256,   1,   1,   0);
		fxt.Test_srl(          257,   2,   1,   0);
		fxt.Test_srl(          510, 255,   1,   0);
		fxt.Test_srl(          511,   1,   2,   0);
		fxt.Test_srl(          512,   2,   2,   0);
		fxt.Test_srl(        65280, 255, 255,   0);
		fxt.Test_srl(        65281,   1,   1,   1,   0);
		fxt.Test_srl(     16646655, 255, 255, 255,   0);
		fxt.Test_srl(     16646656,   1,   1,   1,   1);
		fxt.Test_srl(     16646657,   2,   1,   1,   1);
		fxt.Test_srl(Int_.Max_value, 127, 129, 130, 129);
		fxt.Test_srl(           -1, 128, 129, 130, 129);
		fxt.Test_srl(           -2, 129, 129, 130, 129);
	}
}
class Xow_hzip_int__fxt {
	private final Bry_bfr bfr = Bry_bfr.reset_(8); private final Int_obj_ref read = Int_obj_ref.zero_();
	public void Clear() {bfr.Clear();}
	public void Test_srl(int val, int... bytes) {
		Xow_hzip_int_.Save_bin_int_abrv(bfr, val);
		byte[] save = bfr.Xto_bry_and_clear();
		Tfds.Eq_ary(Bry_.new_ints(bytes), save, "save");
		int load = Xow_hzip_int_.Load_bin_int_abrv(save, save.length, 0, read);
		Tfds.Eq(val, load, "load");
		Tfds.Eq(bytes.length, read.Val(), "load_read");
	}
}
