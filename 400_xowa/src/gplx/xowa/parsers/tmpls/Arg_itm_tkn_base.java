/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Arg_itm_tkn_base extends Xop_tkn_itm_base implements Arg_itm_tkn {
	public Arg_itm_tkn_base() {} // for mock
	public Arg_itm_tkn_base(int bgn, int end) {this.Tkn_ini_pos(false, bgn, end); dat_bgn = bgn;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_arg_itm;}
	public int Dat_bgn() {return dat_bgn;} private int dat_bgn = -1;
	public int Dat_end() {return dat_end;} public Arg_itm_tkn Dat_end_(int v) {dat_end = v; return this;} private int dat_end = -1;
	public byte[] Dat_to_bry(byte[] src) {return Bry_.Len_eq_0(dat_ary) ? Bry_.Mid(src, dat_bgn, dat_end) : dat_ary;}
	public byte[] Dat_ary() {return dat_ary;} private byte[] dat_ary = Bry_.Empty;
	public Arg_itm_tkn Dat_ary_(byte[] dat_ary) {this.dat_ary = dat_ary; return this;}
	public Arg_itm_tkn Dat_rng_(int bgn, int end) {dat_bgn = bgn; dat_end = end; return this;}
	public boolean Dat_ary_had_subst() {return dat_ary_had_subst;} public void Dat_ary_had_subst_y_() {dat_ary_had_subst = true;} private boolean dat_ary_had_subst = false;
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
