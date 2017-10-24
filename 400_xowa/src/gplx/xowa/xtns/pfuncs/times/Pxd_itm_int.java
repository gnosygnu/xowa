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
interface Pxd_itm_int_interface extends Pxd_itm {
	int Xto_int_or(int or);
}
class Pxd_itm_int extends Pxd_itm_base implements Pxd_itm_int_interface {
	public Pxd_itm_int(int ary_idx, int digits, int val) {
		this.Ctor(ary_idx); this.digits = digits; this.val = val;
		switch (digits) {
			case 4:					// assume year
				eval_idx = 50;
				break;
			case 2:
			case 1:
			default:
				if (val > 12) 		// either day or year; not month
					eval_idx = 60;
				else
					eval_idx = 70; // day, year, or month
				break;
		}
	}
	@Override public byte Tkn_tid() {return Pxd_itm_.Tid_int;}
	@Override public int Eval_idx() {return eval_idx;} private int eval_idx = 99;
	public int Val() {return val;} public Pxd_itm_int Val_(int v) {val = v; return this;} private int val;
	public boolean Val_is_adj() {return val_is_adj;} public void Val_is_adj_(boolean v) {val_is_adj = v;} private boolean val_is_adj;
	public int Xto_int_or(int or) {return val;}
	public int Digits() {return digits;} private int digits;
	@Override public boolean Time_ini(DateAdpBldr bldr) {
		int seg_idx = this.Seg_idx();
		if (seg_idx == Pxd_itm_base.Seg_idx_skip) return true;
		if (val_is_adj) {
			if (val == 0) return true;	// no adjustment to make
			DateAdp date = bldr.Date();
			switch (seg_idx) {
				case DateAdp_.SegIdx_hour:			date = date.Add_hour(val); break;
				case DateAdp_.SegIdx_minute:		date = date.Add_minute(val); break;
				default:							return true;
			}
			bldr.Date_(date);
		}
		else {
			if (val == 0) {	// 0 means subtract 1; EX:w:Mariyinsky_Palace; DATE:2014-03-25
				DateAdp date = bldr.Date();
				switch (seg_idx) {
					case DateAdp_.SegIdx_month:			date = DateAdp_.seg_(new int[] {date.Year(), 1, date.Day(), date.Hour(), date.Minute(), date.Second(), date.Frac()}).Add_month(-1); bldr.Date_(date); break;
					case DateAdp_.SegIdx_day:			date = DateAdp_.seg_(new int[] {date.Year(), date.Month(), 1, date.Hour(), date.Minute(), date.Second(), date.Frac()}).Add_day(-1); bldr.Date_(date); break;
					default:							return true;
				}
			}
			else {
				if (seg_idx == -1) return false; // PAGE:New_York_City EX: March 14, 2,013; DATE:2016-07-06
				bldr.Seg_set(seg_idx, val);
			}
		}
		return true;
	}		
	@Override public boolean Eval(Pxd_parser tctx) {
		int data_idx = this.Data_idx();
		if (this.Seg_idx() != Pxd_itm_base.Seg_idx_null) return true;			// has seg_idx; already eval'd by something else
		switch (digits) {
			case 5:																		// 5 digits
				switch (data_idx) {
					case 0: Pxd_eval_year.Eval_at_pos_0(tctx, this); break;				// year at pos 0; EX: 01234-02-03
					case 1: tctx.Err_set(Pft_func_time_log.Invalid_date, Bfr_arg_.New_bry(tctx.Src())); return false;		// year at pos 1; invalid; EX: 01-01234-02
					case 2: tctx.Err_set(Pft_func_time_log.Invalid_date, Bfr_arg_.New_bry(tctx.Src())); break;				// year at pos 2; invalid; EX: 01-02-01234
				}
				val = 2000 + (val % 10);												// NOTE: emulate PHP's incorrect behavior with 5 digit years; EX:ca.w:Nicolau_de_Mira; DATE:2014-04-18
				tctx.Seg_idxs_(this, DateAdp_.SegIdx_year);
				break;
			case 4:
				// 4 digits; assume year
				switch (data_idx) {
					// year at pos 0; EX: 2001-02-03
					case 0:		Pxd_eval_year.Eval_at_pos_0(tctx, this); break;
					// year at pos 1; invalid; EX: 02-2003-03
					case 1:		tctx.Err_set(Pft_func_time_log.Invalid_year_mid); return false;
					// year at pos 2; EX: 02-03-2001
					case 2:		Pxd_eval_year.Eval_at_pos_2(tctx, this); break;
					// year at pos 3 or more
					default:
						// if year already exists, ignore; needed else multiple access-date errors in references; PAGE:en.w:Template:Date; en.w:Antipas,_Cotabato; EX:"12 November 2016 2008" DATE:2017-04-01
						if (tctx.Seg_idxs()[DateAdp_.SegIdx_year] != Pxd_itm_base.Seg_idx_null
						) {
							this.Seg_idx_(Seg_idx_skip);
							return true;
						}
						// try to evaluate year at pos n; EX: 04:05 02-03-2001
						else {
							Pxd_eval_year.Eval_at_pos_n(tctx, this);
						}
						break;
				}
				tctx.Seg_idxs_(this, DateAdp_.SegIdx_year);
				break;
			default:
				Pxd_itm[] data_ary = tctx.Data_ary();
				int data_ary_len = data_ary.length;
				Pxd_itm_int cur_itm = Pxd_itm_int_.CastOrNull(data_ary[data_idx]);
				Pxd_itm lhs_itm = data_idx == 0					? null : data_ary[data_idx - 1];
				Pxd_itm rhs_itm = data_idx == data_ary_len - 1	? null : data_ary[data_idx + 1];
				if (	lhs_itm != null && lhs_itm.Seg_idx() == DateAdp_.SegIdx_month	// itm on left is month
					&&	rhs_itm != null && rhs_itm.Seg_idx() == DateAdp_.SegIdx_year	// itm on right is year
					&&	tctx.Seg_idxs()[DateAdp_.SegIdx_day] == -1						// day not yet set; needed for {{#time:Y|July 28 - August 1, 1975}}
					)
					if (!Pxd_eval_seg.Eval_as_d(tctx, cur_itm)) return true;			// cur int should be day; EX:January 1, 2010; PAGE:en.w:Wikipedia:WikiProject_Maine/members; DATE:2014-06-25
				if (val > Month_max) {									// value is not a month; assume day; DATE:2013-03-15
					switch (data_idx) {
						case 0:															// > 12 in slot 0
							if (Match_sym(tctx, Bool_.Y, Pxd_itm_.Tid_dot))			// next sym is dot; assume m.d.y; EX: 22.5.70
								Eval_day_at_pos_0(tctx); 
							else														// next sym is not dot; assume y-m-d; EX: 70-5-22
								Eval_month_at_pos_0(tctx); 
							break;	
						case 1: Eval_month_at_pos_1(tctx); break;						// > 12 in slot 1; assume m.d; EX: 5.22
						case 2:															// > 12 in slot 2
							if (Match_sym(tctx, Bool_.N, Pxd_itm_.Tid_dot))				// prv sym is dot; assume d.m.y; EX: 22.5.70
								Eval_dmy_at_y(tctx); 
							else														// prv sym is not dot; assume m-d-y; EX: 22.5.70
								Eval_month_at_pos_2(tctx);
							break;
						case 4:
							Eval_unknown_at_pos_4(tctx);
							break;
					}
				}
				else {																	// value is either day or month;
					switch (data_idx) {
						case 0: Eval_unknown_at_pos_0(tctx); break;
						case 3: Eval_unknown_at_pos_3(tctx); break;
						case 4: Eval_unknown_at_pos_4(tctx); break;
					}
				}
				break;
		}
		return true;
	}
	public static final int Month_max = 12;		
	private void Eval_unknown_at_pos_3(Pxd_parser tctx) {	// int at pos 4
		if (	tctx.Seg_idxs_chk(DateAdp_.SegIdx_year, DateAdp_.SegIdx_month, DateAdp_.SegIdx_day)	// check that ymd is set
			&&	Match_sym(tctx, false, Pxd_itm_.Tid_dash))												// check that preceding symbol is "-"
			Pxd_eval_seg.Eval_as_h(tctx, this);															// mark as hour; handles strange fmts like November 2, 1991-06; DATE:2013-06-19
	}
	private void Eval_unknown_at_pos_4(Pxd_parser tctx) {
		if (	tctx.Seg_idxs_chk(DateAdp_.SegIdx_year
				, DateAdp_.SegIdx_month, DateAdp_.SegIdx_day, DateAdp_.SegIdx_hour)						// check that ymdh is set
			&&	Match_sym(tctx, false, Pxd_itm_.Tid_dash))												// check that preceding symbol is "-"
			tctx.Seg_idxs_(this, Pxd_itm_base.Seg_idx_skip);											// mark as ignore; handles strange fmts like November 2, 1991-06-19; DATE:2013-06-19
	}
	boolean Match_sym(Pxd_parser tctx, boolean fwd, int sym_tid) {
		int sym_idx = this.Ary_idx() + (fwd ? 1 : -1);
		Pxd_itm[] sym_tkns = tctx.Tkns();
		if (sym_idx < 0 || sym_idx > sym_tkns.length) return false;	// NOTE: was Data_ary_len; DATE:2013-06-19
		Pxd_itm sym_tkn = sym_tkns[sym_idx];
		return sym_tkn == null ? false : sym_tkn.Tkn_tid() == sym_tid; // PAGE:s.w:Byzantine_Empire EX:285 DATE:2016-07-06
	}
	private void Eval_month_at_pos_0(Pxd_parser tctx) {
		Pxd_itm[] data_ary = tctx.Data_ary();
		if (tctx.Data_ary_len() < 2) return;
		Pxd_itm_int itm_1 = Pxd_itm_int_.CastOrNull(data_ary[1]);
		if (itm_1 != null) {
			if (!Pxd_eval_seg.Eval_as_m(tctx, itm_1)) return;
		}
		if (tctx.Data_ary_len() > 2) {
			Pxd_itm_int itm_2 = Pxd_itm_int_.CastOrNull(data_ary[2]);
			if (itm_2 != null) {
				if (!Pxd_eval_seg.Eval_as_d(tctx, itm_2)) return;		
			}
		}
		Pxd_eval_seg.Eval_as_y(tctx, this);
	}
	private void Eval_day_at_pos_0(Pxd_parser tctx) {	// eval 1 as month; 2 as year, 0 as day
		Pxd_itm[] data_ary = tctx.Data_ary();
		if (tctx.Data_ary_len() < 2) return;
		Pxd_itm_int itm_1 = Pxd_itm_int_.CastOrNull(data_ary[1]);
		if (itm_1 != null) {
			if (!Pxd_eval_seg.Eval_as_m(tctx, itm_1)) return;
		}
		if (tctx.Data_ary_len() > 2) {
			Pxd_itm_int itm_2 = Pxd_itm_int_.CastOrNull(data_ary[2]);
			if (itm_2 != null) {
				if (!Pxd_eval_seg.Eval_as_y(tctx, itm_2)) return;		
			}
		}
		Pxd_eval_seg.Eval_as_d(tctx, this);
	}
	private void Eval_dmy_at_y(Pxd_parser tctx) {	// dmy format; cur is y (slot 2) 
		Pxd_itm[] data_ary = tctx.Data_ary();
		if (tctx.Data_ary_len() < 3) return;	// since proc starts at y, assume at least d-m-y (not m-y) 
		Pxd_itm_int itm_1 = Pxd_itm_int_.CastOrNull(data_ary[1]);
		if (itm_1 != null) {
			if (!Pxd_eval_seg.Eval_as_m(tctx, itm_1)) return;
		}
		Pxd_itm_int itm_0 = Pxd_itm_int_.CastOrNull(data_ary[0]);
		if (itm_0 != null) {
			if (!Pxd_eval_seg.Eval_as_y(tctx, itm_0)) return;		
		}
		Pxd_eval_seg.Eval_as_y(tctx, this);
	}
	private void Eval_month_at_pos_1(Pxd_parser tctx) {
		Pxd_itm[] data_ary = tctx.Data_ary();
		Pxd_itm_int itm_0 = Pxd_itm_int_.CastOrNull(data_ary[0]);
		if (itm_0 != null) {
			if (!Pxd_eval_seg.Eval_as_m(tctx, itm_0)) return;
		}
		if (tctx.Data_ary_len() > 2) {
			Pxd_itm_int itm_2 = Pxd_itm_int_.CastOrNull(data_ary[2]);
			if (itm_2 != null) {
				if (!Pxd_eval_seg.Eval_as_y(tctx, itm_2)) return;		
			}
		}
		Pxd_eval_seg.Eval_as_d(tctx, this);
	}
	private void Eval_month_at_pos_2(Pxd_parser tctx) {
		Pxd_itm[] data_ary = tctx.Data_ary();
		Pxd_itm_int itm_0 = Pxd_itm_int_.CastOrNull(data_ary[0]);
		if (itm_0 != null) {
			if (!Pxd_eval_seg.Eval_as_m(tctx, itm_0)) return;
		}
		Pxd_itm_int itm_1 = Pxd_itm_int_.CastOrNull(data_ary[1]);
		if (itm_1 != null) {
			if (!Pxd_eval_seg.Eval_as_d(tctx, itm_1)) return;		
		}
		Pxd_eval_seg.Eval_as_y(tctx, this);
	}
	private void Eval_unknown_at_pos_0(Pxd_parser tctx) {	// NOTE: assumes dmy format
		Pxd_itm[] data_ary = tctx.Data_ary();
		if (tctx.Data_ary_len() < 2) {tctx.Err_set(Pft_func_time_log.Invalid_year, Bfr_arg_.New_int(val)); return;}
		Pxd_itm_int itm_1 = Pxd_itm_int_.CastOrNull(data_ary[1]);
		if (itm_1 != null) {				// if 1st itm to right is number, parse it as month
			if (!Pxd_eval_seg.Eval_as_m(tctx, itm_1)) return;
		}
		if (tctx.Data_ary_len() > 2) {
			Pxd_itm_int itm_2 = Pxd_itm_int_.CastOrNull(data_ary[2]);
			if (itm_2 != null) {				// if 2nd itm to right is number, assume it as year
				if (!Pxd_eval_seg.Eval_as_y(tctx, itm_2)) return;		
			}
		}
		Pxd_eval_seg.Eval_as_d(tctx, this);	// parse current as day (dmy format)
	}
}
class Pxd_itm_int_ {
	public static void Convert_to_rel(Pxd_parser tctx, Pxd_itm_int itm, byte[] unit_name, int seg_idx, int rel_val) {
		int tkn_idx = itm.Ary_idx();
		tctx.Tkns()[tkn_idx] = new Pxd_itm_unit(tkn_idx, unit_name, seg_idx, rel_val);
	}
	public static Pxd_itm_int CastOrNull(Pxd_itm itm) {return itm.Tkn_tid() == Pxd_itm_.Tid_int ? (Pxd_itm_int)itm : null; }
	public static Pxd_itm_int GetNearest(Pxd_itm[] tkns, int tkn_idx, boolean fwd) {
		int adj = 1, end = tkns.length;
		if (!fwd) {
			adj = -1;
			end = -1;
		}
		for (int i = tkn_idx + adj; i != end; i += adj) {
			Pxd_itm itm = tkns[i];
			if (itm == null) return null; // PAGE:s.w:Leonid_Hurwicz EX:"1944, 2004, [[7 August]] [[2007]]"; DATE:2016-07-06
			if (itm.Tkn_tid() == Pxd_itm_.Tid_int) {
				Pxd_itm_int itm_int = (Pxd_itm_int)itm;
				return itm_int.Seg_idx() == -1 ? itm_int : null;
			} 
		}
		return null;
	}
	public static int Read_nearest_as_int_and_skip(Pxd_parser tctx, Pxd_itm[] tkns, int bgn, boolean fwd, int or) {
		int adj = 1, end = tkns.length;
		if (!fwd) {
			adj = -1;
			end = -1;
		}
		Pxd_itm dash_itm = null;
		for (int i = bgn + adj; i != end; i += adj) {
			Pxd_itm itm = tkns[i];
			switch (itm.Tkn_tid()) {
				case Pxd_itm_.Tid_dash:
					dash_itm = itm;	// TODO_OLD: throw error if "--"; {{#time:U|@--1}}
					break;
				case Pxd_itm_.Tid_int:
				case Pxd_itm_.Tid_int_dmy_14:
				case Pxd_itm_.Tid_int_hms_6:
					Pxd_itm_int_interface itm_int = (Pxd_itm_int_interface)itm;
					int itm_int_seg_idx = itm_int.Seg_idx();
					if (itm_int_seg_idx == -1) { // not evaluated
						int factor = 1;
						if (dash_itm != null) {
							tctx.Seg_idxs_((Pxd_itm_base)dash_itm, Pxd_itm_base.Seg_idx_skip, -1);
							factor = -1;
						}
						tctx.Seg_idxs_((Pxd_itm_base)itm, Pxd_itm_base.Seg_idx_skip, -1);
						return itm_int.Xto_int_or(or) * factor;
					}
					break;
			}
		}
		return or;
	}
	public static Pxd_itm_int Get_int_bwd(Pxd_itm[] data_ary, int data_ary_len, int idx) {
		if (idx > -1) {									// make sure idx is > 0
			Pxd_itm rv = data_ary[idx];					// assume caller not passing in anything > ary.len
			if (	rv.Tkn_tid() == Pxd_itm_.Tid_int	// rv is int
				&&	Pxd_itm_.Eval_needed(rv)			// rv needs evaluation
				)
				return (Pxd_itm_int)rv;					// return it
		}
		return null;
	}
	public static Pxd_itm Get_int_fwd(Pxd_itm[] data_ary, int data_ary_len, int idx) {
		if (idx < data_ary_len) {
			Pxd_itm rv = data_ary[idx];
			if (	rv.Tkn_tid() == Pxd_itm_.Tid_int
				&&	rv.Seg_idx() == -1)
				return rv;
		}
		return null;
	}
}
