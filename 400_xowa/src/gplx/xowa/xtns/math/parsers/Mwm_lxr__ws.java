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
class Mwm_lxr__ws implements Mwm_lxr {
	public int		Tid() {return Mwm_lxr_.Tid__ws;}
	public int		Make_tkn(Mwm_ctx ctx, Mwm_tkn__root root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		while (true) {
			if (cur_pos == src_len) break;
			if (src[cur_pos] != Byte_ascii.Space)
				break;
			++cur_pos;
		}
		root.Regy__add(Mwm_tkn_.Tid__ws, bgn_pos, cur_pos, null);
		return cur_pos;
	}
}
