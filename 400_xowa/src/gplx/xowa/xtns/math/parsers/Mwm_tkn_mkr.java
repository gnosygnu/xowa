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
class Mwm_tkn_mkr {
	private final Mwm_tkn[] leaf_protos = new Mwm_tkn[Mwm_tkn_.Tid_len];
	public void Reg_leaf(int tid, Mwm_tkn tkn) {
		leaf_protos[tid] = tkn;
	}
	public Mwm_tkn Make_leaf(Mwm_tkn__root root, int tid, int uid, int bgn, int end) {
		Mwm_tkn proto = leaf_protos[tid];
		return proto == null ? null : proto.Init(root, tid, uid, bgn, end);
	}
	public Mwm_tkn Make_func(Mwm_tkn__root root, int uid, int bgn, int end) {
		Mwm_tkn__node rv = new Mwm_tkn__node();
		rv.Init(root, Mwm_tkn_.Tid__fnc, uid, bgn, end);
		return rv;
	}
	public Mwm_tkn Make_curly(Mwm_tkn__root root, int uid, int bgn, int end) {
		Mwm_tkn__node rv = new Mwm_tkn__node();
		rv.Init(root, Mwm_tkn_.Tid__curly, uid, bgn, end);
		return rv;
	}
}