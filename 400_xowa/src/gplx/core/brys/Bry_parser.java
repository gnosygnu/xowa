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
package gplx.core.brys; import gplx.*; import gplx.core.*;
public class Bry_parser {
	private final gplx.core.primitives.Int_obj_ref pos_ref = gplx.core.primitives.Int_obj_ref.neg1_();
	private byte[] page; private String wkr_name; private int hook_bgn;
	public byte[] Src() {return src;} private byte[] src; 
	public int Src_len() {return src_len;} private int src_len;
	public int Pos() {return pos;} private int pos;
	public void Init_src(byte[] page, byte[] src, int src_len, int pos) {
		this.page = page; this.src = src; this.src_len = src_len; this.pos = pos; 
	}
	public void Init_hook(String wkr_name, int hook_bgn, int hook_end) {
		this.wkr_name = wkr_name; this.hook_bgn = hook_bgn; this.pos = hook_end;
	}
	public int Pos_(int v) {this.pos = v; return pos;}
	public int Pos_add_one() {return Pos_add(1);}
	public int Pos_add(int adj) {
		this.pos += adj;
		return pos;
	}
	public byte Read_byte() {
		byte rv = src[pos];
		++pos;
		return rv;
	}
	public int Read_int_to(byte to_char) {
		int bgn = pos;
		int rv = 0;
		int negative = 1;
		while (pos < src_len) {
			byte b = src[pos++];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					rv = (rv * 10) + (b - Byte_ascii.Num_0);
					break;
				case Byte_ascii.Dash:
					if (negative == -1)		// 2nd negative
						throw Fail("invalid int", String_.new_u8(src, bgn, pos));
					else					// 1st negative
						negative = -1;		// flag negative
					break;
				default: {
					boolean match = b == to_char;
					if (to_char == Byte_ascii.Null) {// hack for Read_int_to_non_num
						--pos;
						match = true;
					}
					if (!match) throw Fail("invalid int", String_.new_u8(src, bgn, pos));
					return rv * negative;
				}
			}
		}
		if (bgn == pos) throw Fail("int is empty", String_.Empty);
		return rv * negative;
	}
	public byte Read_byte_as_a7_int() {
		byte rv = Byte_ascii.To_a7_int(src[pos]);
		++pos;
		return rv;
	}
	public int Read_int_by_base85(int reqd) {
		int rv = gplx.xowa.htmls.core.hzips.Xoh_hzip_int_.Decode(reqd, src, src_len, pos, pos_ref);
		pos = pos_ref.Val();
	 	return rv;
	}
	public boolean Is(byte find) {
		boolean rv = src[pos] == find;
		if (rv) ++pos;	// only advance if match;
		return rv;
	}
	public int Chk(byte[] find) {
		int find_end = pos + find.length;
		if (!Bry_.Match(src, pos, find_end, find)) throw Fail("failed check", String_.new_u8(find));
		pos = find_end;
		return pos;
	}
	public int Chk(byte find) {
		if (src[pos] != find) throw Fail("failed check", Byte_ascii.To_str(find));
		++pos;
		return pos;
	}
	public int Fwd_bgn(byte[] find) {
		int find_pos = Bry_find_.Find_fwd(src, find, pos, src_len);
		if (find_pos == Bry_find_.Not_found) throw Fail("missing", String_.new_u8(find));
		pos = find_pos + find.length;
		return find_pos;
	}
	public int Fwd_bgn(byte find) {
		int find_pos = Bry_find_.Find_fwd(src, find, pos, src_len);
		if (find_pos == Bry_find_.Not_found) throw Fail("missing", Byte_ascii.To_str(find));
		pos = find_pos + 1;
		return find_pos;
	}
	public int Fwd_end(byte[] find) {
		int find_pos = Bry_find_.Find_fwd(src, find, pos, src_len);
		if (find_pos == Bry_find_.Not_found) throw Fail("missing", String_.new_u8(find));
		pos = find_pos + find.length;
		return pos;
	}
	public int Fwd_end(byte find) {
		int find_pos = Bry_find_.Find_fwd(src, find, pos, src_len);
		if (find_pos == Bry_find_.Not_found) throw Fail("missing", Byte_ascii.To_str(find));
		pos = find_pos + 1;
		return pos;
	}
	public int Fwd_while(byte find) {
		this.pos = Bry_find_.Find_fwd_while(src, pos, src_len, find);
		return pos;
	}
	public Err Fail(String msg, String arg) {
		return Err_.new_("Bry_parser", msg, "arg", arg, "page", page, "wkr", wkr_name, "excerpt", Bry_.Mid_by_len_safe(src, hook_bgn, 255));
	}
}
