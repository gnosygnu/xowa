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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
class Pxd_itm_colon extends Pxd_itm_base {
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_colon;}
	@Override public int Eval_idx() {return 20;}
	@Override public void Eval(Pxd_parser state) {
		int colon_count = ++state.Colon_count;
		Pxd_itm[] tkns = state.Tkns();
		Pxd_itm_int itm_int = null;
		switch (colon_count) {
			case 1:			// hh:mm
				itm_int = Pxd_itm_int_.GetNearest(tkns, this.Ary_idx(), Bool_.N);
				if (itm_int == null) {state.Err_set(Pft_func_time_log.Invalid_hour, Bry_fmtr_arg_.bry_("null")); return;}
				if (!Pxd_eval_seg.Eval_as_h(state, itm_int)) return;
				itm_int = Pxd_itm_int_.GetNearest(tkns, this.Ary_idx(), true);
				if (!Pxd_eval_seg.Eval_as_n(state, itm_int)) return;
				break;
			case 2:			// :ss
				itm_int = Pxd_itm_int_.GetNearest(tkns, this.Ary_idx(), true);
				if (!Pxd_eval_seg.Eval_as_s(state, itm_int)) return;
				break;
			case 3:			// +hh:mm; DATE:2014-08-26
				itm_int = Pxd_itm_int_.GetNearest(tkns, this.Ary_idx(), Bool_.N);
				if (itm_int == null) {state.Err_set(Pft_func_time_log.Invalid_timezone, Bry_fmtr_arg_.bry_("null")); return;}
				byte tz_positive_val = Pxd_eval_seg.Eval_as_tz_sym(state, tkns, itm_int);
				if (tz_positive_val == Bool_.__byte) return;
				boolean tz_negative = tz_positive_val == Bool_.N_byte;
				if (!Pxd_eval_seg.Eval_as_tz_h(state, itm_int, tz_negative)) return;
				itm_int = Pxd_itm_int_.GetNearest(tkns, this.Ary_idx(), Bool_.Y);
				if (itm_int == null) {state.Err_set(Pft_func_time_log.Invalid_timezone, Bry_fmtr_arg_.bry_("null")); return;}
				if (tz_negative) itm_int.Val_(itm_int.Val() * -1);
				if (!Pxd_eval_seg.Eval_as_tz_m(state, itm_int, tz_negative)) return;
				break;
		} 
	}
	public Pxd_itm_colon(int ary_idx) {this.Ctor(ary_idx);}
}
class Pxd_itm_null extends Pxd_itm_base {
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_null;}
	@Override public int Eval_idx() {return 99;}
	public static final Pxd_itm_null _ = new Pxd_itm_null(); 
}
class Pxd_itm_dash extends Pxd_itm_base {
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_dash;}
	@Override public int Eval_idx() {return 99;}
	public Pxd_itm_dash(int ary_idx) {this.Ctor(ary_idx);}
}
class Pxd_itm_dot extends Pxd_itm_base {
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_dot;}
	@Override public int Eval_idx() {return 99;}
	public Pxd_itm_dot(int ary_idx) {this.Ctor(ary_idx);}
}
class Pxd_itm_ws extends Pxd_itm_base {
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_ws;}
	@Override public int Eval_idx() {return 99;}
	public Pxd_itm_ws(int ary_idx) {this.Ctor(ary_idx);}
}
class Pxd_itm_slash extends Pxd_itm_base {
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_slash;}
	@Override public int Eval_idx() {return 99;}
	public Pxd_itm_slash(int ary_idx) {this.Ctor(ary_idx);}
}
class Pxd_itm_sym extends Pxd_itm_base {
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_sym;} 
	@Override public int Eval_idx() {return 99;}
	@Override public void Time_ini(DateAdpBldr bldr) {}
	public byte Sym_byte() {return sym_byte;} private byte sym_byte;
	public Pxd_itm_sym(int ary_idx, byte val) {this.Ctor(ary_idx); this.sym_byte = val;}
}
class Pxd_itm_int_dmy_14 extends Pxd_itm_base implements Pxd_itm_int_interface {
	public Pxd_itm_int_dmy_14(int ary_idx, byte[] src, int digits) {this.Ctor(ary_idx); this.src = src; this.digits = digits;} private byte[] src; int digits;
	public int Xto_int_or(int or) {return Bry_.To_int_or(src, or);}
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_int_dmy_14;}
	@Override public int Eval_idx() {return eval_idx;} private int eval_idx = 20;
	@Override public void Time_ini(DateAdpBldr bldr) {
		if (this.Seg_idx() != Pxd_itm_base.Seg_idx_null) return;						// has seg_idx; already eval'd by something else
		bldr.Seg_set(DateAdp_.SegIdx_year	, Bry_.To_int_or(src,  0,  4, 0));
		bldr.Seg_set(DateAdp_.SegIdx_month	, Bry_.To_int_or(src,  4,  6, 0));
		if (digits > 6) {
			bldr.Seg_set(DateAdp_.SegIdx_day	, Bry_.To_int_or(src,  6,  8, 0));
			if (digits > 8) {
				bldr.Seg_set(DateAdp_.SegIdx_hour	, Bry_.To_int_or(src,  8, 10, 0));
				if (digits > 10) {
					bldr.Seg_set(DateAdp_.SegIdx_minute	, Bry_.To_int_or(src, 10, 12, 0));
					if (digits > 12)
						bldr.Seg_set(DateAdp_.SegIdx_second	, Bry_.To_int_or(src, 12, 14, 0));
				}
			}
		}
	}
}
class Pxd_itm_int_mhs_6 extends Pxd_itm_base implements Pxd_itm_int_interface {
	public Pxd_itm_int_mhs_6(int ary_idx, byte[] src) {this.Ctor(ary_idx); this.src = src;} private byte[] src;
	public int Xto_int_or(int or) {return Bry_.To_int_or(src, or);}
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_int_hms_6;}
	@Override public int Eval_idx() {return eval_idx;} private int eval_idx = 20;
	@Override public void Time_ini(DateAdpBldr bldr) {
		if (this.Seg_idx() != Pxd_itm_base.Seg_idx_null) return;						// has seg_idx; already eval'd by something else
		bldr.Seg_set(DateAdp_.SegIdx_hour	, Bry_.To_int_or(src, 0,  2, 0));
		bldr.Seg_set(DateAdp_.SegIdx_minute	, Bry_.To_int_or(src, 2,  4, 0));
		bldr.Seg_set(DateAdp_.SegIdx_second	, Bry_.To_int_or(src, 4,  6, 0));
	}
}
class Pxd_itm_sorter implements gplx.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Pxd_itm lhs = (Pxd_itm)lhsObj;
		Pxd_itm rhs = (Pxd_itm)rhsObj;
		return Int_.Compare(lhs.Eval_idx(), rhs.Eval_idx());
	}
	public static final Pxd_itm_sorter _ = new Pxd_itm_sorter();
	public static Pxd_itm[] XtoAryAndSort(Pxd_itm[] src, int src_len) {
		Pxd_itm[] rv = new Pxd_itm[src_len];
		for (int i = 0; i < src_len; i++)
			rv[i] = src[i];
		Array_.Sort(rv, Pxd_itm_sorter._);
		return rv;
	}
}