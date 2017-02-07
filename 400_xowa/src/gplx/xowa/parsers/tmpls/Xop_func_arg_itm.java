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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.xtns.pfuncs.*;
public class Xop_func_arg_itm {
	public byte[] key;
	public byte[] val;
	public void Set(Bry_bfr bfr, Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Arg_nde_tkn arg) {
		Pf_func_.Eval_arg_or(bfr, ctx, src, caller, self, arg, null);
		key = val = Bry_.Empty;
		int len = bfr.Len(); byte[] bry = bfr.Bfr();
		int bgn = -1, end = -1; boolean mode_is_key = true;
		for (int i = 0; i < len; ++i) {
			byte b = bry[i];
			switch (b) {
				case Byte_ascii.Eq:
					if (mode_is_key) {
						mode_is_key = false;
						key = Bry_.Mid(bry, bgn, end);
						bgn = end = -1;
					}
					break;
				case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr: case Byte_ascii.Space:
					break;
				default:
					if (bgn == -1) bgn = i;
					end = i + 1;
					break;
			}
			if (bgn != -1)	// guard against 'key='
				val = Bry_.Mid(bry, bgn, end);
		}
		bfr.Clear();
	}
}
