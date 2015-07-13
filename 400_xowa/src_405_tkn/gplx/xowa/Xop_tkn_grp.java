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
public interface Xop_tkn_grp {
	int Subs_len();
	Xop_tkn_itm Subs_get(int i);
	void Subs_add(Xop_tkn_itm sub);
	void Subs_add_grp(Xop_tkn_itm sub, Xop_tkn_grp old_grp, int old_sub_idx);
	void Subs_del_after(int pos_bgn);
	void Subs_clear();
	void Subs_move(Xop_tkn_itm tkn);
	int Subs_src_bgn(int sub_idx);
	int Subs_src_end(int sub_idx);
	void Subs_src_pos_(int sub_idx, int bgn, int end);
	Xop_tkn_itm Immutable_clone(Xop_ctx ctx, Xop_tkn_itm tkn, int sub_idx);
}
class Xop_tkn_grp_ {
	public static final Xop_tkn_grp Null = null;
}
