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
import gplx.core.primitives.*; import gplx.core.encoders.*;
public class Xoh_hzip_int_ {
	public static final int Neg_1_adj = 1;
	public static void Encode(int reqd, Bry_bfr bfr, int val) {
		int bfr_len = bfr.Len();
		int len_in_base85 = Base85_.Bry_len(val);
		boolean abrv = val < Base85_.Pow85[reqd];
		int adj = abrv ? 0 : 1;
		int actl_len = len_in_base85 + adj;
		if (actl_len < reqd) actl_len = reqd;
		bfr.Add_byte_repeat(Byte_ascii.Bang, actl_len);			// fill with 0s; this asserts that there underlying array will be large enough for following write
		byte[] bfr_bry = bfr.Bfr();								// NOTE: set bry reference here b/c Add_byte_repeat may create a new one
		Base85_.Set_bry(val, bfr_bry, bfr_len + adj, reqd);		// calc base85 val for val; EX: 7224 -> "uu"
		if (!abrv) {
			byte base85_byte = Base85_len__2;
			switch (len_in_base85) {
				case 3: base85_byte = Base85_len__3; break;
				case 4: base85_byte = Base85_len__4; break;
				case 5: base85_byte = Base85_len__5; break;
			}
			bfr_bry[bfr_len] = base85_byte;
		}
	}
	public static int Decode(int reqd, byte[] src, int src_len, int src_bgn, Int_obj_ref pos_ref) {
		byte b0 = src[src_bgn];
		int base85_bgn = src_bgn + 1;
		int len_in_base85 = 1;	// default to 1
		switch (b0) {
			case Base85_len__2: len_in_base85 = 2; break;
			case Base85_len__3: len_in_base85 = 3; break;
			case Base85_len__4: len_in_base85 = 4; break;
			case Base85_len__5: len_in_base85 = 5; break;
			default:			--base85_bgn; break;
		}
            if (len_in_base85 < reqd) len_in_base85 = reqd;
		int base85_end = base85_bgn + len_in_base85;
		pos_ref.Val_(base85_end);
		return Base85_.To_int_by_bry(src, base85_bgn, base85_end - 1);
	}
	private static final byte 
	  Base85_len__2 = Byte_ascii.Curly_bgn
	, Base85_len__3 = Byte_ascii.Pipe
	, Base85_len__4 = Byte_ascii.Curly_end
	, Base85_len__5 = Byte_ascii.Tilde;
}
