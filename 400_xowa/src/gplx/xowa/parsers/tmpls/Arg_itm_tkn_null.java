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
public class Arg_itm_tkn_null extends Xop_tkn_null implements Arg_itm_tkn {	public int Dat_bgn() {return -1;}
	public int Dat_end() {return -1;} public Arg_itm_tkn Dat_end_(int v) {return this;}
	public Arg_itm_tkn Dat_rng_(int bgn, int end) {return this;}
	public Arg_itm_tkn Dat_rng_ary_(byte[] src, int bgn, int end) {return this;}
	public byte[] Dat_ary() {return Bry_.Empty;} public Arg_itm_tkn Dat_ary_(byte[] dat_ary) {return this;}
	public byte[] Dat_to_bry(byte[] src) {return Bry_.Empty;}
	public Arg_itm_tkn Subs_add_ary(Xop_tkn_itm... ary) {return this;}
	public boolean Dat_ary_had_subst() {return false;} public void Dat_ary_had_subst_y_() {}
	public byte Itm_static() {return Bool_.__byte;} public Arg_itm_tkn Itm_static_(boolean v) {return this;}
	public static final Arg_itm_tkn_null Null_arg_itm = new Arg_itm_tkn_null(); Arg_itm_tkn_null() {}
}
