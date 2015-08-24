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
package gplx.xowa.xtns.math.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*;
class Mwm_lxr__backslash implements Mwm_lxr {
	public int		Tid() {return Mwm_lxr_.Tid__backslash;}
	public int		Make_tkn(Mwm_ctx ctx, Mwm_tkn__root root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		boolean loop = true;
		while (loop) {
			if (cur_pos == src_len) break;
			byte b = src[cur_pos];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
				case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
				case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
				case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
				case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
				case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
				case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
				case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
				case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
				case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
				case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
				case Byte_ascii.Underline:
					++cur_pos;
					break;
				default:
					loop = false;
					break;
			}
		}
		root.Regy__add(Mwm_tkn_.Tid__func, bgn_pos, cur_pos, new Mwm_tkn__node());
		return cur_pos;
	}
}
