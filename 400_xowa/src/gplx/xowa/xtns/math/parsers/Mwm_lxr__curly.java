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
class Mwm_lxr__curly_bgn implements Mwm_lxr {
	public int		Tid() {return Mwm_lxr_.Tid__curly_bgn;}
	public int		Make_tkn(Mwm_ctx ctx, Mwm_tkn__root root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int uid = root.Regy__add(Mwm_tkn_.Tid__arg, bgn_pos, cur_pos, new Mwm_tkn__node());
		Mwm_tkn tkn = root.Subs__get_at(uid);
		ctx.Stack().Add(tkn);
		return cur_pos;
	}
}
class Mwm_lxr__curly_end implements Mwm_lxr {
	public int		Tid() {return Mwm_lxr_.Tid__curly_end;}
	public int		Make_tkn(Mwm_ctx ctx, Mwm_tkn__root root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		Mwm_tkn bgn_tkn = ctx.Stack().Pop();
		root.Regy__move(bgn_tkn, null);
		return cur_pos;
	}
}
