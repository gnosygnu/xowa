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
import gplx.core.brys.*;
class Pxd_eval_year {
	public static void Eval_at_pos_0(Pxd_parser tctx, Pxd_itm_int cur) {
		Pxd_itm[] data_ary = tctx.Data_ary();
		if (tctx.Data_ary_len() < 2) return;
		Pxd_itm_int itm_1 = Pxd_itm_int_.CastOrNull(data_ary[1]);
		if (itm_1 != null) {
			if (!Pxd_eval_seg.Eval_as_m(tctx, itm_1)) return;
		}
		if (tctx.Data_ary_len() < 3) return;
		Pxd_itm_int itm_2 = Pxd_itm_int_.CastOrNull(data_ary[2]);
		if (itm_2 != null) {
			if (!Pxd_eval_seg.Eval_as_d(tctx, itm_2)) return;
		}
		if (tctx.Data_ary_len() == 4) {	// handle strange constructions like 2014-03-24-25;
			Pxd_itm_int itm_3 = Pxd_itm_int_.CastOrNull(data_ary[3]);
			if (itm_3 != null) {			// treat 4th number as hour adjustment; EX: 2014-03-24-72 -> 2014-03-26; DATE:2014-03-24
				int itm_3_val = itm_3.Val();
				if (itm_3_val > 99) itm_3_val = 0;	// only adjust if number is between 0 and 99;
				Pxd_itm_int_.Convert_to_rel(tctx, itm_3, Pxd_parser_.Unit_name_hour, DateAdp_.SegIdx_hour, itm_3_val);
			}
		}
	}
	public static void Eval_at_pos_2(Pxd_parser tctx, Pxd_itm_int cur) {
		Pxd_itm[] data_ary = tctx.Data_ary();
		Pxd_itm_int itm_0 = Pxd_itm_int_.CastOrNull(data_ary[0]);
		Pxd_itm_int itm_1 = Pxd_itm_int_.CastOrNull(data_ary[1]);
		if (itm_0 == null || itm_1 == null) return;				// 0 or 1 is not an int;
		if (itm_1.Val() > 13) {
			if (!Pxd_eval_seg.Eval_as_m(tctx, itm_0)) return;
			if (!Pxd_eval_seg.Eval_as_d(tctx, itm_1)) return;						
		}
		else {
			if (!Pxd_eval_seg.Eval_as_d(tctx, itm_0)) return;
			if (!Pxd_eval_seg.Eval_as_m(tctx, itm_1)) return;			
		}
	}
	public static void Eval_at_pos_n(Pxd_parser tctx, Pxd_itm_int cur) {	// where n > 2; EX: 1:23 4.5.2010; PAGE:sk.w:Dr._House; DATE:2014-09-23
		Pxd_itm[] data_ary = tctx.Data_ary(); int data_ary_len = data_ary.length; int data_idx = cur.Data_idx();
		Pxd_itm_int lhs_1 = Pxd_itm_int_.Get_int_bwd(data_ary, data_ary_len, data_idx - 1);
		if (lhs_1 != null) {		// lhs_1 is int; EX:05-2014
			Pxd_itm_int lhs_0 = Pxd_itm_int_.Get_int_bwd(data_ary, data_ary_len, data_idx - 2);
			if (lhs_0 != null) {	// lhs_0 is int; EX:01-05-2014
				if (!Pxd_eval_seg.Eval_as_d(tctx, lhs_0)) return;
				if (!Pxd_eval_seg.Eval_as_m(tctx, lhs_1)) return;
				if (!Pxd_eval_seg.Eval_as_y(tctx, cur  )) return;
			}
		}
	}
	public static final int Month_max = 12;		
}
class Pxd_eval_seg {
	public static boolean Eval_as_y(Pxd_parser tctx, Pxd_itm_int itm) {
		int val = itm.Val();
		switch (itm.Digits()) {
			case 1:
			case 2:
				itm.Val_(val + (val > 69 ? 1900 : 2000));	// assume that 70 refers to 1970 and 69 refers to 2069
				tctx.Seg_idxs_(itm, DateAdp_.SegIdx_year);
				return true;
			case 3:	// NOTE: 3 digit numbers are valid years; MW relies on PHP time parse which always zero-pad numbers; PAGE:en.w:Battle of the Catalaunian Plains; {{#time:Y|June 20, 451}}
			case 4:
				tctx.Seg_idxs_(itm, DateAdp_.SegIdx_year);
				return true;
		}
		tctx.Err_set(Pft_func_time_log.Invalid_year, Bfr_arg_.New_int(val));
		return false;
	}
	public static boolean Eval_as_m(Pxd_parser tctx, Pxd_itm_int itm) {
		int val = itm.Val();
		switch (itm.Digits()) {
			case 1:
			case 2:
				if		(val > -1 && val < 13) {
					tctx.Seg_idxs_(itm, DateAdp_.SegIdx_month);
					return true;
				}
				break;
		}
		tctx.Err_set(Pft_func_time_log.Invalid_month, Bfr_arg_.New_int(val));
		return false;
	}
	public static boolean Eval_as_d(Pxd_parser tctx, Pxd_itm_int itm) {
		int val = itm.Val();
		switch (itm.Digits()) {
			case 1:
			case 2:
				if (val > -1 && val < 32) { 
					tctx.Seg_idxs_(itm, DateAdp_.SegIdx_day);
					return true;
				}
				break;
		}
		tctx.Err_set(Pft_func_time_log.Invalid_day, Bfr_arg_.New_int(val));
		return false;
	}
	public static boolean Eval_as_h(Pxd_parser tctx, Pxd_itm_int itm) {
		int val = itm.Val();
		switch (itm.Digits()) {
			case 1:
			case 2:
				if (val > -1 && val < 25) { 
					tctx.Seg_idxs_(itm, DateAdp_.SegIdx_hour, val);
					return true;
				}
				break;
		}
		tctx.Err_set(Pft_func_time_log.Invalid_hour, Bfr_arg_.New_int(val));
		return false;
	}
	public static boolean Eval_as_n(Pxd_parser tctx, Pxd_itm_int itm) {
		int val = itm.Val();
		switch (itm.Digits()) {
			case 1:
			case 2:
				if (val > -1 && val < 60) { 
					tctx.Seg_idxs_(itm, DateAdp_.SegIdx_minute, val);
					return true;
				}
				break;
		}
		tctx.Err_set(Pft_func_time_log.Invalid_minute, Bfr_arg_.New_int(val));
		return false;
	}
	public static boolean Eval_as_s(Pxd_parser tctx, Pxd_itm_int itm) {
		int val = itm.Val();
		switch (itm.Digits()) {
			case 1:
			case 2:
				if (val > -1 && val < 60) { 
					tctx.Seg_idxs_(itm, DateAdp_.SegIdx_second);
					return true;
				}
				break;
		}
		tctx.Err_set(Pft_func_time_log.Invalid_second, Bfr_arg_.New_int(val));
		return false;
	}
	public static boolean Eval_as_tz_h(Pxd_parser tctx, Pxd_itm_int itm, boolean negative) {
		if (negative) itm.Val_(itm.Val() * -1);
		int val = itm.Val();
		switch (itm.Digits()) {
			case 1:
			case 2:
				if (val > -12 && val < 12) {
					itm.Val_is_adj_(Bool_.Y);
					itm.Seg_idx_(DateAdp_.SegIdx_hour);
					return true;
				}
				break;
		}
		tctx.Err_set(Pft_func_time_log.Invalid_hour, Bfr_arg_.New_int(val));
		return false;
	}
	public static boolean Eval_as_tz_m(Pxd_parser tctx, Pxd_itm_int itm, boolean negative) {
		int val = itm.Val();
		if (negative) val *= -1;
		switch (itm.Digits()) {
			case 1:
			case 2:
				if (val > -60 && val < 60) { 
					itm.Val_is_adj_(Bool_.Y);
					itm.Seg_idx_(DateAdp_.SegIdx_minute);
					return true;
				}
				break;
		}
		tctx.Err_set(Pft_func_time_log.Invalid_minute, Bfr_arg_.New_int(val));
		return false;
	}
	public static byte Eval_as_tz_sym(Pxd_parser tctx, Pxd_itm[] tkns, Pxd_itm_int hour_itm) {
		Pxd_itm sym = Pxd_itm_.Find_bwd__non_ws(tkns, hour_itm.Ary_idx());
		switch (sym.Tkn_tid()) {
			case Pxd_itm_.Tid_sym:
				Pxd_itm_sym sym_itm = (Pxd_itm_sym)sym;
				if (sym_itm.Sym_byte() == Byte_ascii.Plus)
					return Bool_.Y_byte;
				break;
			case Pxd_itm_.Tid_dash: return Bool_.N_byte;
		}
		tctx.Err_set(Pft_func_time_log.Invalid_timezone, Bfr_arg_.New_bry("null"));
		return Bool_.__byte;
	}
}