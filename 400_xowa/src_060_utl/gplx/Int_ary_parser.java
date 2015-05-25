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
package gplx;
public class Int_ary_parser extends Obj_ary_parser_base {
	Number_parser parser = new Number_parser(); int[] ary; int ary_idx;
	public int[] Parse_ary(String str, byte dlm) {byte[] bry = Bry_.new_u8(str); return Parse_ary(bry, 0, bry.length, dlm);}
	public int[] Parse_ary(byte[] bry, int bgn, int end, byte dlm) {
		Parse_core(bry, bgn, end, dlm, Byte_ascii.Nil);
		return ary;
	}
	@Override protected void Ary_len_(int v) {
		if (v == 0)
			ary = Int_.Ary_empty;
		else {
			ary = new int[v];	// NOTE: always create new array; never reuse;
			ary_idx = 0;
		}
	}
	@Override protected void Parse_itm(byte[] bry, int bgn, int end) {
		parser.Parse(bry, bgn, end); if (parser.Has_err() || parser.Has_frac()) throw Err_.new_fmt_("failed to parse number: {0}", String_.new_u8(bry, bgn, end));
		ary[ary_idx++] = parser.Rv_as_int();
	}
	public static final Int_ary_parser _ = new Int_ary_parser();
}
