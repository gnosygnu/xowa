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
public class Xof_meta_thumb_parser extends Obj_ary_parser_base {
	Number_parser num_parser = new Number_parser(); 
	Int_ary_parser int_ary_parser = new Int_ary_parser();
	public Xof_meta_thumb[] Ary() {return ary;} private Xof_meta_thumb[] ary = new Xof_meta_thumb[Ary_max]; static final int Ary_max = 16;
	public int Len() {return ary_idx;} private int ary_idx;
	public void Parse_ary(byte[] bry, int bgn, int end) {super.Parse_core(bry, bgn, end, Byte_ascii.Semic, Byte_ascii.Nil);}
	public Xof_meta_thumb Parse_one(byte[] bry, int bgn, int end) {
		super.Parse_core(bry, bgn, end, Byte_ascii.Semic, Byte_ascii.Nil);
		return ary[0];
	}
	public void Clear() {if (ary.length > Ary_max) ary = new Xof_meta_thumb[Ary_max];}
	@Override protected void Ary_len_(int v) {
		ary_idx = 0;
		if	(v > Ary_max) ary = new Xof_meta_thumb[v];
	}
	@Override protected void Parse_itm(byte[] bry, int bgn, int end) {	// EX: "1:45,40"; "1:45,40:3,4"
		Xof_meta_thumb itm = new Xof_meta_thumb(); boolean height_found = false;
		if (end - 2 < bgn)	throw Err_mgr._.fmt_(GRP_KEY, "invalid_itm", "itm must be at least 2 bytes long: itm=~{0}", String_.new_u8(bry, bgn, end)); // EX: 4,6
		int pos = bgn;
		byte exists_byte = bry[pos];
		switch (exists_byte) {
			case Byte_ascii.Num_0: itm.Exists_(Xof_meta_itm.Exists_n); break;
			case Byte_ascii.Num_1: itm.Exists_(Xof_meta_itm.Exists_y); break;
			case Byte_ascii.Num_2: itm.Exists_(Xof_meta_itm.Exists_unknown); break;
			default: throw Err_mgr._.fmt_(GRP_KEY, "invalid_exists_val", "exists must be 0,1,2: exists=~{0} itm=~{1}", exists_byte, String_.new_u8(bry, bgn, end));
		}
		if (bry[pos + 1] != Dlm_exists) throw Err_mgr._.fmt_(GRP_KEY, "invalid_exists_spr", "question must follow exists: bad_char=~{0} itm=~{1}", bry[pos + 1], String_.new_u8(bry, bgn, end));
		pos += 2;
		int num_bgn = pos;
		while (pos < end) {
			byte b = bry[pos];
			switch (b) {
				case Dlm_width:	// "," found; assume width; note that seek commas will be handled by seek
					itm.Width_(num_parser.Parse(bry, num_bgn, pos).Rv_as_int());
					num_bgn = pos + Int_.Const_dlm_len;
					break;
				case Dlm_seek:
					itm.Height_(num_parser.Parse(bry, num_bgn, pos).Rv_as_int());
					num_bgn = pos + Int_.Const_dlm_len;
					height_found = true;
					itm.Seeks_(int_ary_parser.Parse_ary(bry, num_bgn, end, Byte_ascii.Comma));
					pos = end;
					break;
			}
			++pos;
		}
		if (!height_found)	// handle '1:2,3' as opposed to '1:2,3@4'
			itm.Height_(num_parser.Parse(bry, num_bgn, end).Rv_as_int());
		ary[ary_idx++] = itm;
	}
	static final String GRP_KEY = "xowa.meta.itm.file";
	public static final byte Dlm_exists = Byte_ascii.Question, Dlm_seek = Byte_ascii.At, Dlm_width = Byte_ascii.Comma;
	public static final String Dlm_seek_str = "@";
}
