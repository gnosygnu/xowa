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
package gplx.xowa; import gplx.*;
import gplx.xowa.files.*;
class Xofo_lnki_parser extends Obj_ary_parser_base {
	Number_parser parser = new Number_parser(); Xofo_lnki[] ary; int ary_idx;		
	public Xofo_lnki[] Parse_ary(byte[] bry, int bgn, int end) {
		Parse_core(bry, bgn, end, Byte_ascii.Semic, Byte_ascii.Nl);
		return ary;
	}
	@Override protected void Ary_len_(int v) {
		if (v == 0)
			ary = Xofo_lnki.Ary_empty;
		else {
			ary = new Xofo_lnki[v];	// NOTE: always create new array; never reuse;
			ary_idx = 0;
		}
	}
	@Override protected void Parse_itm(byte[] bry, int bgn, int end) {// 1,2,3,lnki_upright=12
		Xofo_lnki lnki = new Xofo_lnki();
		ary[ary_idx++] = lnki;
		int fld_bgn = bgn, fld_idx = 0, eq_pos = -1;
		for (int i = bgn; i < end; i++) {
			byte b = bry[i];
			switch (b) {
				case Byte_ascii.Eq: eq_pos = i; break;
				case Byte_ascii.Comma:
					Exec_val(lnki, fld_idx, eq_pos, bry, fld_bgn, i);
					++fld_idx;
					fld_bgn = i + 1;
					break;
			}
		}
		Exec_val(lnki, fld_idx, eq_pos, bry, fld_bgn, end);
	}
	private void Exec_val(Xofo_lnki lnki, int fld_idx, int eq_pos, byte[] bry, int fld_bgn, int i) {
		int fld_val = -1;
		if (fld_idx < 3) {
			fld_val = Bry_.Xto_int_or(bry, fld_bgn, i, Int_.MinValue);
			if (fld_val == Int_.MinValue) throw Exc_.new_("invalid int", "val", String_.new_u8(bry, fld_bgn, i));
			switch (fld_idx) {
				case 0:	lnki.Lnki_type_((byte)fld_val); break;
				case 1:	lnki.Lnki_w_(fld_val); break;
				case 2:	lnki.Lnki_h_(fld_val); break;
				default: throw Exc_.new_unhandled(fld_idx);
			}
		}
		else {
			if		(Bry_.Match(bry, fld_bgn, eq_pos, Xop_lnki_arg_parser.Bry_upright))		{
				double upright = Bry_.XtoDoubleByPos(bry, eq_pos + 1, i);	// +1 to position after eq
				lnki.Lnki_upright_(upright);
			}
			else if (Bry_.Match(bry, fld_bgn, eq_pos, Xop_lnki_arg_parser.Bry_thumbtime))	{
				fld_val = Bry_.Xto_int_or(bry, eq_pos + 1, i, Int_.MinValue);	// +1 to position after eq
				if (fld_val == Int_.MinValue) throw Exc_.new_("invalid int", "val", String_.new_u8(bry, eq_pos + 1, i));
				lnki.Lnki_thumbtime_(Xof_lnki_time.X_int(fld_val));
			}
		}
	}
}
