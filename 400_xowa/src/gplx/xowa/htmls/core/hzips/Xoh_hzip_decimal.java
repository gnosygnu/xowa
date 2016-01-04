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
public class Xoh_hzip_decimal {
	private final Gfo_decimal_parser decimal_parser = new Gfo_decimal_parser();
	public boolean Encode(Bry_bfr bfr, byte[] src, int src_bgn, int src_end) {
		if (!decimal_parser.Parse(src, src_bgn, src_end)) return false;
//			if (positive && dot_pos == -1 && num_val < 128) {
//				bfr.Add_byte((byte)(num_val + 128));
//			}
		return true;
/*
0.001000

1:size_is_1
1:exp.neg
6:exp.val
5:exp.val

1:num.neg
7:num.val; 111-127 for extended bits
8:num.val
8:num.val
127
125 2
126 3
127 4
*/
	}
}
