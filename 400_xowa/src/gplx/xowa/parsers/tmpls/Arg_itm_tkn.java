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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public interface Arg_itm_tkn extends Xop_tkn_itm {
	int Dat_bgn();
	int Dat_end();
	Arg_itm_tkn Dat_end_(int v);
	Arg_itm_tkn Dat_rng_(int bgn, int end);
	Arg_itm_tkn Dat_rng_ary_(byte[] src, int bgn, int end);
	byte[] Dat_ary();
	Arg_itm_tkn Dat_ary_(byte[] dat_ary);
	byte[] Dat_to_bry(byte[] src);
	boolean Dat_ary_had_subst(); void Dat_ary_had_subst_y_();
	byte Itm_static(); Arg_itm_tkn Itm_static_(boolean v);
	Arg_itm_tkn Subs_add_ary(Xop_tkn_itm... ary);
}
