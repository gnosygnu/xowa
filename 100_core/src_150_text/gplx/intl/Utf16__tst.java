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
package gplx.intl; import gplx.*;
import org.junit.*;
public class Utf16__tst {
	private Utf16__fxt fxt = new Utf16__fxt();
	@Test  public void Encode_decode() {
		fxt.Test_encode_decode(162, 194, 162);				// cent
		fxt.Test_encode_decode(8364, 226, 130, 172);		// euro
		fxt.Test_encode_decode(150370, 240, 164, 173, 162);	// example from [[UTF-8]]; should be encoded as two bytes
	}
	@Test  public void Encode_as_bry_by_hex() {
		fxt.Test_Encode_hex_to_bry("00", 0);
		fxt.Test_Encode_hex_to_bry("41", 65);
		fxt.Test_Encode_hex_to_bry("0041", 65);
		fxt.Test_Encode_hex_to_bry("00C0", 195, 128);
	}
	@Test  public void Surrogate() {
		fxt.Test_surrogate(0x64321, 0xD950, 0xDF21);	// example from w:UTF-16
		fxt.Test_surrogate(66643, 55297, 56403);		// example from d:Boomerang
	}
}
class Utf16__fxt {
	private Int_obj_ref hi_ref = Int_obj_ref.neg1_(), lo_ref = Int_obj_ref.neg1_();
	public void Test_encode_decode(int expd_c_int, int... expd_int) {
		byte[] expd = Bry_.ints_(expd_int);
		byte[] bfr = new byte[10];
		int bfr_len = Utf16_.Encode_int(expd_c_int, bfr, 0);
		byte[] actl = Bry_.Mid_by_len(bfr, 0, bfr_len);
		Tfds.Eq_ary(expd, actl);
		int actl_c_int = Utf16_.Decode_to_int(bfr, 0);
		Tfds.Eq(expd_c_int, actl_c_int);
	}
	public void Test_surrogate(int v, int hi, int lo) {
		Tfds.Eq(v, Utf16_.Surrogate_merge((char)hi, (char)lo));
		Utf16_.Surrogate_split(v, hi_ref, lo_ref);
		Tfds.Eq(hi, hi_ref.Val());
		Tfds.Eq(lo, lo_ref.Val());
	}
	public void Test_Encode_hex_to_bry(String raw, int... expd) {
		byte[] actl = Utf16_.Encode_hex_to_bry(raw);
		Tfds.Eq_ary(Byte_.Ary_by_ints(expd), actl);
	}
}
