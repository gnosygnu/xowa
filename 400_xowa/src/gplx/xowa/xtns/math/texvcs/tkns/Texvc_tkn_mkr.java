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
package gplx.xowa.xtns.math.texvcs.tkns; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*; import gplx.xowa.xtns.math.texvcs.*;
import gplx.xowa.xtns.math.texvcs.funcs.*;
public class Texvc_tkn_mkr {
	private final Texvc_tkn[] regy = new Texvc_tkn[Texvc_tkn_.Tid_len + Texvc_func_itm_.Id_len];
	public void Reg_singleton(int tid, Texvc_tkn tkn) {
		regy[tid] = tkn;
	}
	public Texvc_tkn Get_singleton(Texvc_root root, int tid, int leaf_id, int uid, int bgn, int end) {
		Texvc_tkn singleton = regy[leaf_id];
		return singleton == null ? null : singleton.Init(root, tid, uid, bgn, end);
	}
	public static final int Singleton_id__null = -1;
}