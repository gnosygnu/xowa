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
public interface Xop_ctx_wkr {
	void Ctor_ctx(Xop_ctx ctx);
	void Page_bgn(Xop_ctx ctx, Xop_root_tkn root);
	void Page_end(Xop_ctx ctx, Xop_root_tkn root, byte[] src, int src_len);
}
interface Xop_arg_wkr {
	boolean Args_add(Xop_ctx ctx, byte[] src, Xop_tkn_itm tkn, Arg_nde_tkn arg, int arg_idx);
}
class Xop_arg_wkr_ {
	public static final int Typ_lnki = 0, Typ_tmpl = 1, Typ_prm = 2;
}
