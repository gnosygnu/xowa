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
package gplx.xowa.hdumps.srls; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import org.junit.*;
public class Hpg_srl_itm__tst {
	@Before public void init() {fxt.Clear();} private Hpg_srl_itm__fxt fxt = new Hpg_srl_itm__fxt();
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
		fxt.Test_srl(Int_.MaxValue, 127, 129, 130, 129);
		fxt.Test_srl(           -1, 128, 129, 130, 129);
		fxt.Test_srl(           -2, 129, 129, 130, 129);
	}
}
class Hpg_srl_itm__fxt {
	private Bry_bfr bfr = Bry_bfr.reset_(8); private Int_obj_ref read = Int_obj_ref.zero_();
	public void Clear() {bfr.Clear();}
	public void Test_srl(int val, int... bytes) {
		Hpg_srl_itm_.Save_bin_int_abrv(bfr, val);
		byte[] save = bfr.Xto_bry_and_clear();
		Tfds.Eq_ary(Bry_.ints_(bytes), save, "save");
		int load = Hpg_srl_itm_.Load_bin_int_abrv(save, save.length, 0, read);
		Tfds.Eq(val, load, "load");
		Tfds.Eq(bytes.length, read.Val(), "load_read");
	}
}
