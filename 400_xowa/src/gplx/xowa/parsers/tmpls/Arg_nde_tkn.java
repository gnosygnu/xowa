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
public class Arg_nde_tkn extends Xop_tkn_itm_base {
	public Arg_nde_tkn() {}	// for mock
	public Arg_nde_tkn(int arg_idx, int bgn) {this.arg_idx = arg_idx; this.Tkn_ini_pos(false, bgn, -1);} private int arg_idx;
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_arg_nde;}
	public byte Arg_compiled() {return arg_compiled;} public Arg_nde_tkn Arg_compiled_(byte v) {arg_compiled = v; return this;} private byte arg_compiled = Bool_.__byte;
	public int Arg_colon_pos() {return arg_colon_pos;} public Arg_nde_tkn Arg_colon_pos_(int v) {arg_colon_pos = v; return this;} private int arg_colon_pos = -1;
	public Arg_itm_tkn Key_tkn() {return key_tkn;} public Arg_nde_tkn Key_tkn_(Arg_itm_tkn v) {key_tkn = v; return this;} Arg_itm_tkn key_tkn = Arg_itm_tkn_null.Null_arg_itm;
	public Arg_itm_tkn Val_tkn() {return val_tkn;} public Arg_nde_tkn Val_tkn_(Arg_itm_tkn v) {val_tkn = v; return this;} Arg_itm_tkn val_tkn = Arg_itm_tkn_null.Null_arg_itm;
	@gplx.Virtual public boolean KeyTkn_exists() {return key_tkn != Arg_itm_tkn_null.Null_arg_itm;}
	public Xop_tkn_itm Eq_tkn() {return eq_tkn;} public Arg_nde_tkn Eq_tkn_(Xop_tkn_itm v) {eq_tkn = v; return this;} private Xop_tkn_itm eq_tkn = Xop_tkn_null.Null_tkn;
	@Override public void Tmpl_fmt(Xop_ctx ctx, byte[] src, Xot_fmtr fmtr) {fmtr.Reg_arg(ctx, src, arg_idx, this);}
	@Override public void Tmpl_compile(Xop_ctx ctx, byte[] src, Xot_compile_data prep_data) {
		key_tkn.Tmpl_compile(ctx, src, prep_data);
		eq_tkn.Tmpl_compile(ctx, src, prep_data);
		val_tkn.Tmpl_compile(ctx, src, prep_data);
	}
	@Override public boolean Tmpl_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Bry_bfr bfr) {
		key_tkn.Tmpl_evaluate(ctx, src, caller, bfr);
		eq_tkn.Tmpl_evaluate(ctx, src, caller, bfr);
		val_tkn.Tmpl_evaluate(ctx, src, caller, bfr);
		return true;
	}
	public static final    Arg_nde_tkn[] Ary_empty = new Arg_nde_tkn[0];
	public static final    Arg_nde_tkn Null = new Arg_nde_tkn(-1, -1);
}
