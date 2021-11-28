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
package gplx.xowa.xtns.pfuncs.times; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.core.brys.*;
class Pxd_itm_unit_relative extends Pxd_itm_base implements Pxd_itm_prototype { // handle "next", "prev", "this"; EX: "next year"
	private final int adj;
	public Pxd_itm_unit_relative(int adj, int ary_idx) {
		this.adj = adj;
		Ctor(ary_idx);
	}
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_unit_relative;}
	@Override public int Eval_idx() {return 5;}
	public Pxd_itm MakeNew(int ary_idx) {return new Pxd_itm_unit_relative(adj, ary_idx);}
	public int Adj() {return adj;}
	@Override public boolean Eval(Pxd_parser state) {
		// find next token: EX: year, month, Sunday, Monday, etc.
		Pxd_itm itm = Pxd_itm_.Find_fwd__non_ws(state.Tkns(), this.Ary_idx() + 1);
		if (itm == null) {
			state.Err_set(Pft_func_time_log.Invalid_date, Bfr_arg_.New_int(adj));
			return false;
		}

		switch (itm.Tkn_tid()) {
			// EX: "next year", "next month", etc.
			case Pxd_itm_.Tid_unit:
				Pxd_itm_unit unit_tkn = (Pxd_itm_unit)itm;
				unit_tkn.Unit_seg_val_(adj);
				break;
			// EX: "next Monday", "next Tuesday", etc.
			case Pxd_itm_.Tid_dow_name:
				Pxd_itm_dow_name day_tkn = (Pxd_itm_dow_name)itm;
				day_tkn.Relative_adj_(adj);
				break;
			default:
				state.Err_set(Pft_func_time_log.Invalid_date, Bfr_arg_.New_int(adj));
				return false;
		}
		return true;
	}

	@Override public boolean Time_ini(Pxd_date_bldr bldr) {
		// noop; date-logic handled by respective unit, month, dow classes; EX: "next month" -> "next" does no date logic; "month" does;
		return true;
	}
	public static final Pxd_itm_unit_relative
	  Next		= new Pxd_itm_unit_relative( 1, 0)
	, Prev		= new Pxd_itm_unit_relative(-1, 0)
	, This		= new Pxd_itm_unit_relative( 0, 0)
	;
}
