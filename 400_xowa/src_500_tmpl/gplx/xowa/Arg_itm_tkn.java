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
public interface Arg_itm_tkn extends Xop_tkn_itm {
	int Dat_bgn();
	int Dat_end();
	Arg_itm_tkn Dat_end_(int v);
	Arg_itm_tkn Dat_rng_(int bgn, int end);
	Arg_itm_tkn Dat_rng_ary_(byte[] src, int bgn, int end);
	byte[] Dat_ary();
	Arg_itm_tkn Dat_ary_(byte[] dat_ary);
	byte[] Dat_to_bry(byte[] src);
	byte Itm_static(); Arg_itm_tkn Itm_static_(boolean v);
	Arg_itm_tkn Subs_add_ary(Xop_tkn_itm... ary);
}
class Arg_itm_tkn_null extends Xop_tkn_null implements Arg_itm_tkn {	public int Dat_bgn() {return -1;}
	public int Dat_end() {return -1;} public Arg_itm_tkn Dat_end_(int v) {return this;}
	public Arg_itm_tkn Dat_rng_(int bgn, int end) {return this;}
	public Arg_itm_tkn Dat_rng_ary_(byte[] src, int bgn, int end) {return this;}
	public byte[] Dat_ary() {return Bry_.Empty;} public Arg_itm_tkn Dat_ary_(byte[] dat_ary) {return this;}
	public byte[] Dat_to_bry(byte[] src) {return Bry_.Empty;}
	public Arg_itm_tkn Subs_add_ary(Xop_tkn_itm... ary) {return this;}
	public byte Itm_static() {return Bool_.__byte;} public Arg_itm_tkn Itm_static_(boolean v) {return this;}
	public static final Arg_itm_tkn_null Null_arg_itm = new Arg_itm_tkn_null(); Arg_itm_tkn_null() {}
}
class Arg_itm_tkn_base extends Xop_tkn_itm_base implements Arg_itm_tkn {
	public Arg_itm_tkn_base() {} // for mock
	public Arg_itm_tkn_base(int bgn, int end) {this.Tkn_ini_pos(false, bgn, end); dat_bgn = bgn;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_arg_itm;}
	public int Dat_bgn() {return dat_bgn;} private int dat_bgn = -1;
	public int Dat_end() {return dat_end;} public Arg_itm_tkn Dat_end_(int v) {dat_end = v; return this;} private int dat_end = -1;
	public byte[] Dat_to_bry(byte[] src) {return Bry_.Len_eq_0(dat_ary) ? Bry_.Mid(src, dat_bgn, dat_end) : dat_ary;}
	public byte[] Dat_ary() {return dat_ary;} private byte[] dat_ary = Bry_.Empty;
	public Arg_itm_tkn Dat_ary_(byte[] dat_ary) {this.dat_ary = dat_ary; return this;}
	public Arg_itm_tkn Dat_rng_(int bgn, int end) {dat_bgn = bgn; dat_end = end; return this;}
	public Arg_itm_tkn Dat_rng_ary_(byte[] src, int bgn, int end) {
		dat_bgn = bgn; dat_end = end;
		dat_ary = Bry_.Mid(src, bgn, end);
		return this;
	}
	public byte Itm_static() {return itm_static;} public Arg_itm_tkn Itm_static_(boolean v) {itm_static = v ? Bool_.Y_byte : Bool_.N_byte; return this;} private byte itm_static = Bool_.__byte;
	@Override public void Tmpl_compile(Xop_ctx ctx, byte[] src, Xot_compile_data prep_data) {
		int subs_len = this.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Xop_tkn_itm sub = this.Subs_get(i);
			sub.Tmpl_compile(ctx, src, prep_data);
		}
	}
	@Override public boolean Tmpl_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Bry_bfr bfr) {
		if	(dat_ary == Bry_.Empty) {
			int subs_len = this.Subs_len();
			for (int i = 0; i < subs_len; i++)
				this.Subs_get(i).Tmpl_evaluate(ctx, src, caller, bfr);
		}
		else bfr.Add(dat_ary);
		return true;
	}
	public Arg_itm_tkn Subs_add_ary(Xop_tkn_itm... ary) {for (Xop_tkn_itm sub : ary) Subs_add(sub); return this;}
}
