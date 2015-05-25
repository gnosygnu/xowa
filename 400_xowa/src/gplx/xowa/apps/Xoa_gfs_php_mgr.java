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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
import gplx.php.*;
public class Xoa_gfs_php_mgr {
	public static byte[] Xto_php(Bry_bfr bfr, boolean escape_backslash, byte[] src) {
		int len = src.length;
		int pos = 0;
		boolean dirty = false;
		while (pos < len) {
			byte b = src[pos];
			switch (b) {
				case Byte_ascii.Tilde:
					if (!dirty) {
						bfr.Add_mid(src, 0, pos);
						dirty = true;
					}
					pos = Xto_php_swap(bfr, src, len, pos + 1);
					break;
				case Byte_ascii.Backslash: case Byte_ascii.Dollar:
				case Byte_ascii.Apos: case Byte_ascii.Quote:
					if (escape_backslash) {
						if (!dirty) {
							bfr.Add_mid(src, 0, pos);
							dirty = true;
						}
						bfr.Add_byte(Byte_ascii.Backslash);
						bfr.Add_byte(b);
					}
					else {
						if (dirty)
							bfr.Add_byte(b);
					}
					++pos;
					break;
				default:
					if (dirty)
						bfr.Add_byte(b);
					++pos;
					break;
			}
		}
		return dirty ? bfr.Xto_bry_and_clear() : src;
	}
	private static int Xto_php_swap(Bry_bfr bfr, byte[] src, int len, int pos) {
		if (pos >= len) throw Err_.new_("invalid gfs: tilde is last char; src={0}", String_.new_u8(src));
		byte b = src[pos];
		switch (b) {
			case Byte_ascii.Tilde: {	// ~~ -> ~
				bfr.Add_byte(Byte_ascii.Tilde);
				return pos + 1;
			}
			case Byte_ascii.Curly_bgn: {
				int num_bgn = pos + 1;
				int num_end = Bry_finder.Find_fwd_while_num(src, num_bgn, len);	// +1 to position after {
				if (   num_end == Bry_finder.Not_found
					|| num_end == len
					|| src[num_end] != Byte_ascii.Curly_end
					)
					throw Err_.new_("invalid gfs; num_end not found={0}", String_.new_u8(src));
				bfr.Add_byte(Byte_ascii.Dollar);
				int arg_idx = Bry_.Xto_int_or(src, num_bgn, num_end, -1);
				if (arg_idx == -1) {
					throw Err_.new_("invalid int");
				}
				bfr.Add_int_variable(arg_idx + 1);
				return num_end + 1;
			}
			default: {
				throw Err_.new_("invalid gfs; next char after tilde must be either tilde or open brace; src={0}", String_.new_u8(src));
			}
		}
	}
	public static byte[] Xto_gfs(Bry_bfr bfr, byte[] raw) {
		int raw_len = raw.length;
		for (int i = 0; i < raw_len; i++) {
			byte b = raw[i];
			switch (b) {
				case Byte_ascii.Backslash:
					++i;
					if (i < raw_len)
						bfr.Add_byte(raw[i]);
					else
						bfr.Add_byte(Byte_ascii.Backslash);
					break;
				case Byte_ascii.Tilde:
					bfr.Add_byte_repeat(Bry_fmtr.char_escape, 2);	// escape tilde; EX: ~u -> ~~u; DATE:2013-11-11
					break;
				case Byte_ascii.Dollar:
					int end_pos = Php_text_itm_parser.Find_fwd_non_int(raw, i + 1, raw_len);
					int int_val = Bry_.Xto_int_or(raw, i + 1, end_pos, -1);
					bfr.Add_byte(Bry_fmtr.char_escape).Add_byte(Bry_fmtr.char_arg_bgn).Add_int_variable(int_val - 1).Add_byte(Bry_fmtr.char_arg_end);
					i = end_pos - 1;
					break;
				default:
					bfr.Add_byte(b);
					break;
			}
		}
		return bfr.Xto_bry_and_clear();
	}
}
